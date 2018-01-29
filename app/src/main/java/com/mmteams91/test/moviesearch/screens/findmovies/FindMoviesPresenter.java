package com.mmteams91.test.moviesearch.screens.findmovies;

/**
 * Created by Михаил on 29.01.2018.
 */

public class FindMoviesPresenter implements FindMoviesContract.Presenter {
    private FindMoviesContract.View view;

    @Override
    public void takeView(FindMoviesContract.View view) {
        this.view = view;

    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
