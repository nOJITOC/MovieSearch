package com.mmteams91.test.moviesearch.screens.showmovie;

import android.util.Log;

import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.di.ScreenScope;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.HttpException;

public class ShowMoviePresenter implements ShowMovieContract.Presenter {

    private static final String TAG = "ShowMoviePresenter";
    private String language;
    private RestApi api;
    private ShowMovieContract.View view;
    private FindMovieDto preview;

    @Inject
    public ShowMoviePresenter(RestApi api, FindMovieDto movie, String language) {
        this.api = api;
        this.preview = movie;
        this.language = language;
    }

    @Override
    public void onCreateView() {
        view.showPreview(preview);
        api.getMovie(preview.getId(), language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::prepareInfo, throwable -> {
                    Log.e(TAG, "accept: ", throwable);
                    if (throwable instanceof HttpException)
                        Log.e(TAG, "takeView: " + ((HttpException) throwable).response().raw());
                });
    }

    @Override
    public void takeView(ShowMovieContract.View view) {
        this.view = view;
    }

    private void prepareInfo(MovieDto movieDto) {
        Map<String, String> movieInfo = new HashMap<>();
        // TODO: 30.01.2018 implement 

    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
