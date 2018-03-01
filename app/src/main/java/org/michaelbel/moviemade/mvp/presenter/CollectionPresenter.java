package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.service.COLLECTIONS;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.utils.DateUtils;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

@InjectViewState
public class CollectionPresenter extends MvpPresenter<MvpResultsView> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadMovies(int collectionId) {
        if (collectionId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        COLLECTIONS service = ApiFactory.getRetrofit2().create(COLLECTIONS.class);
        Observable<Collection> observable = service.getDetails(collectionId, Url.TMDB_API_KEY, Url.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<Collection>() {
            @Override
            public void onNext(Collection collection) {
                List<TmdbObject> results = new ArrayList<>(collection.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void movieFavoritesChange(Movie m) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.id;
            newMovie.title = m.title;
            newMovie.posterPath = m.posterPath;
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.releaseDate);
            //newMovie.originalTitle = m.originalTitle;
            //newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(m.originalLanguage);
            newMovie.overview = m.overview;
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            //newMovie.adult = m.adult;
            //newMovie.backdropPath = m.backdropPath;
            //newMovie.budget = AndroidUtils.formatCurrency(m.budget);
            //newMovie.revenue = AndroidUtils.formatCurrency(m.revenue);
            //newMovie.status = m.status;
            //newMovie.tagline = m.tagline;
            //newMovie.imdbId = m.imdbId;
            //newMovie.homepage = m.homepage;
            //newMovie.popularity = m.popularity;
            //newMovie.video = m.video;
            //newMovie.runtime = AndroidUtils.formatRuntime(m.runtime);
            newMovie.voteAverage = m.voteAverage;
            newMovie.voteCount = m.voteCount;
            newMovie.favorite = true;

            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
            movieRealm.favorite = !movie.favorite;
            realm.commitTransaction();
        }
    }

    public void movieWatchlistChange(Movie m) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.id;
            newMovie.title = m.title;
            newMovie.posterPath = m.posterPath;
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.releaseDate);
            //newMovie.originalTitle = m.originalTitle;
            //newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(m.originalLanguage);
            newMovie.overview = m.overview;
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            //newMovie.adult = m.adult;
            //newMovie.backdropPath = m.backdropPath;
            //newMovie.budget = AndroidUtils.formatCurrency(m.budget);
            //newMovie.revenue = AndroidUtils.formatCurrency(m.revenue);
            //newMovie.status = m.status;
            //newMovie.tagline = m.tagline;
            //newMovie.imdbId = m.imdbId;
            //newMovie.homepage = m.homepage;
            //newMovie.popularity = m.popularity;
            //newMovie.video = m.video;
            //newMovie.runtime = AndroidUtils.formatRuntime(m.runtime);
            newMovie.voteAverage = m.voteAverage;
            newMovie.voteCount = m.voteCount;
            newMovie.watching = true;

            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
            movieRealm.watching = !movie.watching;
            realm.commitTransaction();
        }
    }

    public boolean isMovieFavorite(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null && movie.favorite;
    }

    public boolean isMovieWatchlist(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null && movie.watching;
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}