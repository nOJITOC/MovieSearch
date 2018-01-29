package com.mmteams91.test.moviesearch.screens.findmovies;

import com.mmteams91.test.moviesearch.data.network.RestApi;

import javax.inject.Inject;

/**
 * Created by Михаил on 29.01.2018.
 */

public class FindMoviesPresenter implements FindMoviesContract.Presenter {
    private FindMoviesContract.View view;
    private RestApi api;

    @Inject
    public FindMoviesPresenter(RestApi api) {
        this.api = api;
    }

    @Override
    public void takeView(FindMoviesContract.View view) {
        this.view = view;

    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
