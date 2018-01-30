package com.mmteams91.test.moviesearch;

import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;

import javax.inject.Inject;

/**
 * Created by User New on 30.01.2018.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void setMovie(MovieDto movie) {
        // TODO: 30.01.2018 implement
    }

    @Override
    public void takeView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
