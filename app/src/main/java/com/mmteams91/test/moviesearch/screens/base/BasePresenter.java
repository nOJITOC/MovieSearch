package com.mmteams91.test.moviesearch.screens.base;

/**
 * Created by Михаил on 29.01.2018.
 */

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
