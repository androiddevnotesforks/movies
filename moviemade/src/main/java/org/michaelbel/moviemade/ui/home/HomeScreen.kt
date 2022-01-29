package org.michaelbel.moviemade.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.insets.systemBarsPadding
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.app.data.model.MovieResponse
import org.michaelbel.moviemade.ui.ROUTE_SETTINGS

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val listState: LazyListState = rememberLazyListState()
    val movies: LazyPagingItems<MovieResponse> = viewModel.moviesStateFlow
        .collectAsLazyPagingItems()

    Scaffold(
        topBar = { Toolbar(navController) }
    ) {
        Content(
            navController,
            listState,
            movies
        )
    }
}

@Composable
private fun Toolbar(
    navController: NavController
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_home)
            )
        },
        modifier = Modifier.systemBarsPadding(),
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(ROUTE_SETTINGS) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            ) {
                Image(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

@Composable
private fun Content(
    navController: NavController,
    listState: LazyListState,
    movies: LazyPagingItems<MovieResponse>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 2.dp),
            state = listState
        ) {
            items(movies) { movieItem ->
                Log.e("2580", "movie item = $movieItem")
                movieItem?.let { movie ->
                    MovieListItem(
                        movie = movie,
                        onClick = {
                            navController.navigate("movie?movieTitle=${movie.title}")
                        },
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
            movies.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingListItem() }
                    }
                    is LoadState.Error -> {
                        item { ErrorListItem(onRetryClick = { retry() }) }
                    }
                    else -> { /* not implemented */ }
                }
            }
        }
    }
}