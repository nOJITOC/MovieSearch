package com.mmteams91.test.moviesearch;


import android.support.v4.app.Fragment;

import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.screens.base.BasePresenter;
import com.mmteams91.test.moviesearch.screens.base.BaseView;

/**
 * Created by User New on 30.01.2018.
 */

public interface MainContract {
    interface Presenter extends BasePresenter<View>{
        void setMovie(MovieDto movie);
    }

    interface View extends BaseView<Presenter>{
        void showMovieInfo(MovieDto movie);
        void showMovies();
        void addFragment(Fragment fragment);
    }
}
