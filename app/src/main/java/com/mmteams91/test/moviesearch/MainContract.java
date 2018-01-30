package com.mmteams91.test.moviesearch;


import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.base.BasePresenter;
import com.mmteams91.test.moviesearch.screens.base.BaseView;

/**
 * Created by User New on 30.01.2018.
 */

public interface MainContract {
    interface Presenter extends BasePresenter<View>{
        void setMovie(FindMovieDto movie, String language);

        FindMovieDto getClickedMovieRes();

        String getLanguage();
    }

    interface View extends BaseView<Presenter>{
        void showMovieInfo(FindMovieDto movie, String language);
        void showMovies();
    }
}
