package com.mmteams91.test.moviesearch.screens.showmovie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.di.ScreenScope;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Provides;
import dagger.android.support.DaggerFragment;

/**
 * Created by User New on 30.01.2018.
 */

public class ShowMovieFragment extends DaggerFragment implements ShowMovieContract.View {

    private static final String LANGUAGE_KEY = "LANGUAGE_KEY";
    @Inject
    ShowMovieContract.Presenter presenter;
    private TextView title;
    private ImageView image;

    public static ShowMovieFragment create(String language) {
        ShowMovieFragment fragment = new ShowMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LANGUAGE_KEY, language);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_movie, container, false);
        title = view.findViewById(R.id.title);
        image = view.findViewById(R.id.image);
        return view;
    }


    @Override
    public void showPreview(FindMovieDto preview) {
        title.setText(preview.getTitle());
        Glide.with(getContext())
                .load("http://image.tmdb.org/t/p/w500" + preview.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }

    @Override
    public void showMovie(MovieDto movieDto) {

    }

    @dagger.Module
    public abstract static class Module {
        @Provides
        @ScreenScope
        static FindMovieDto provideMovieRes(MainContract.Presenter findMoviesPresenter) {
            return findMoviesPresenter.getClickedMovieRes();
        }

        @Provides
        @ScreenScope
        static String provideLanguage(MainContract.Presenter findMoviesPresenter) {
            return findMoviesPresenter.getLanguage();
        }

        @Binds
        @ScreenScope
        abstract ShowMovieContract.Presenter presenter(ShowMoviePresenter presenter);

    }

}
