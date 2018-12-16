package org.michaelbel.moviemade.ui.modules.recommendations;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;
import org.michaelbel.moviemade.utils.RxUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RecommendationsMoviesPresenter extends MvpPresenter<RecommendationsMvp> {

    private int page;
    private Disposable disposable;
    private Disposable disposable2;

    @Inject MOVIES service;

    RecommendationsMoviesPresenter() {
        Moviemade.getAppComponent().injest(this);
    }

    void getRecommendations(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        disposable = service.getRecommendations(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            });
    }

    void getRecommendationsNext(int movieId) {
        page++;
        disposable2 = service.getRecommendations(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                List<Movie> results = new ArrayList<>(response.getMovies());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().setMovies(results);
            }, e -> {
                getViewState().setError(EmptyViewMode.MODE_NO_MOVIES);
                e.printStackTrace();
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.INSTANCE.unsubscribe(disposable);
        RxUtil.INSTANCE.unsubscribe(disposable2);
    }
}