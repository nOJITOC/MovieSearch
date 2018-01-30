package com.mmteams91.test.moviesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.di.ActivityScope;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesFragment;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.support.DaggerAppCompatActivity;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View{
    @Inject
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showMovieInfo(MovieDto movie) {

    }

    @Override
    public void showMovies() {
        addFragment(FindMoviesFragment.create());
    }

    @Override
    public void addFragment(Fragment fragment) {
       checkNotNull(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,fragment,fragment.getTag());
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @dagger.Module
    public static abstract class Module {
        @ActivityScope
        @Binds abstract MainContract.Presenter mainPresenter(MainPresenter presenter);
    }
}
