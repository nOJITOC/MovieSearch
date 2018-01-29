package com.mmteams91.test.moviesearch.screens.findmovies;

import com.mmteams91.test.moviesearch.screens.base.BasePresenter;
import com.mmteams91.test.moviesearch.screens.base.BaseView;

/**
 * Created by Михаил on 29.01.2018.
 */

public interface FindMoviesContract {
    interface Presenter extends BasePresenter<View> {

    }

    interface View extends BaseView<Presenter> {

    }
}
