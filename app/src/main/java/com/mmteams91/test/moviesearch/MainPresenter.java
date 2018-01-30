package com.mmteams91.test.moviesearch;

import com.mmteams91.test.moviesearch.data.managers.PreferenceManager;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;

import javax.inject.Inject;

/**
 * Created by User New on 30.01.2018.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private PreferenceManager preferenceManager;
    private FindMovieDto currentMovie;
    private String language;

    @Inject
    public MainPresenter(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    @Override
    public void setMovie(FindMovieDto movie, String language) {
        this.currentMovie = movie;
        this.language = language;
        view.showMovieInfo(movie, language);
    }

    @Override
    public FindMovieDto getClickedMovieRes() {
        return currentMovie;
    }

    @Override
    public void takeView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
