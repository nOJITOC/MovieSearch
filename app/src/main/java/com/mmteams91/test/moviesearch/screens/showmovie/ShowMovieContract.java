package com.mmteams91.test.moviesearch.screens.showmovie;

import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.base.BasePresenter;
import com.mmteams91.test.moviesearch.screens.base.BaseView;

import java.util.Collection;

/**
 * Created by User New on 30.01.2018.
 */

public class ShowMovieContract {
    interface Presenter extends BasePresenter<View> {

        void onCreateView();
    }

    interface View extends BaseView<MainContract.Presenter> {

        void showTitle(String title);
        void showPoster(String posterUri);
        void showInfo(Collection<DataWithLabelAdapter.Item> info);

        FindMovieDto getClickedMovieDto();

        String getLanguage();
    }
}
