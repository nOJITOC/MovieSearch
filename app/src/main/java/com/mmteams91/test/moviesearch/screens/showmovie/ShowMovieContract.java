package com.mmteams91.test.moviesearch.screens.showmovie;

import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.screens.base.BasePresenter;
import com.mmteams91.test.moviesearch.screens.base.BaseView;

/**
 * Created by User New on 30.01.2018.
 */

public class ShowMovieContract {
    interface Presenter extends BasePresenter<View> {

    }

    interface View extends BaseView<MainContract.Presenter> {

        void showPreview(FindMovieDto preview);

        void showMovie(MovieDto movieDto);
    }
}
