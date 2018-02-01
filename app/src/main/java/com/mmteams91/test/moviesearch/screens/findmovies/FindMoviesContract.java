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

        String getImageUri();

        void onResume();
    }

    interface View extends Screen<Presenter> {

        String getKeyboardLocale();

        int getDisplayWidth();

        int getMovieContainerPadding();

        void setMoviesDtoContainer(List<FindMovieDto> findMovies);

        void showMovies(int startIndex, int count);

        void setQueryString(String query);
    }
}
