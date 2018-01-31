package com.mmteams91.test.moviesearch.screens.findmovies;

import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.Screen;
import com.mmteams91.test.moviesearch.screens.base.BasePresenter;

import java.util.List;

/**
 * Created by Михаил on 29.01.2018.
 */

public interface FindMoviesContract {
    interface Presenter extends BasePresenter<View> {

        void onLastItemBind();

        void loadMovies(String query);

        String getLanguage();

    }

    interface View extends Screen<Presenter> {
        void addMovies(List<FindMovieDto> movies);

        String getKeyboardLocale();

        void clearPrevResult();

        int getDisplayWidth();

        int getMovieContainerPadding();
    }
}
