package org.michaelbel.moviemade.presentation.features.main

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.michaelbel.core.playcore.InAppUpdate
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.MovieResponse
import org.michaelbel.domain.repository.MoviesRepository
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.presentation.features.main.adapter.UiAction
import org.michaelbel.moviemade.presentation.features.main.adapter.UiState
import java.util.*

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted val list: String?,
    private val repository: MoviesRepository,
    private val inAppUpdate: InAppUpdate
): ViewModel() {

    /**
     * Stream of immutable states representative of the UI.
     */
    val state: StateFlow<UiState>

    val pagingDataFlow: Flow<PagingData<MovieResponse>>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val accept: (UiAction) -> Unit

    var updateAvailableMessage =  MutableSharedFlow<Boolean>()
    var updateDownloadedMessage =  MutableSharedFlow<Boolean>()

    init {
        inAppUpdate.onUpdateAvailableListener = { updateAvailable ->
            viewModelScope.launch {
                updateAvailableMessage.emit(updateAvailable)
            }
        }

        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = Movie.NOW_PLAYING)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = Movie.NOW_PLAYING)) }

        pagingDataFlow = searches
            .flatMapLatest { searchRepo() }
            .cachedIn(viewModelScope)

        state = searches
            .flatMapLatest { search ->
                combine(
                    queriesScrolled,
                    searchRepo(),
                    ::Pair
                )
                    // Each unique PagingData should be submitted once, take the latest from
                    // queriesScrolled
                    .distinctUntilChangedBy { it.second }
                    .map { (scroll, pagingData) ->
                        UiState(
                            list = search.query,
                            hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    fun snackBarActionUpdateClicked(activity: Activity) {
        snackBarUpdateDismissed()
        inAppUpdate.startUpdateFlow(activity)
    }

    fun completeUpdate() {}

    fun snackBarUpdateDismissed() {
        viewModelScope.launch {
            updateAvailableMessage.emit(false)
        }
    }

    fun test() {
        viewModelScope.launch {
            updateAvailableMessage.emit(true)
        }
    }

    private fun searchRepo(): Flow<PagingData<MovieResponse>> =
        repository.moviesStream(Movie.NOW_PLAYING, BuildConfig.TMDB_API_KEY, Locale.getDefault().language)
            .cachedIn(viewModelScope)

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted handle: SavedStateHandle,
            list: String?
        ): MainViewModel
    }
}

private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val LAST_SEARCH_LIST: String = "last_search_query"
const val DEFAULT_QUERY = "Android"