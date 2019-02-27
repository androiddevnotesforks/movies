package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.core.utils.EmptyViewMode

interface KeywordContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun getMovies(keywordId: Int)
        fun getMoviesNext(keywordId: Int)
    }

    interface Repository {
        fun getMovies(keywordId: Int, page: Int) : Observable<MoviesResponse>
    }
}