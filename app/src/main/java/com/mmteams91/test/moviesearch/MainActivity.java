package com.mmteams91.test.moviesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.di.ScreenScope;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesFragment;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {
    private static final String TAG = "MainActivity";
    @Inject
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMovies();
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
    public void showMovieInfo(FindMovieDto movie, String language) {
        Log.e(TAG, "showMovieInfo: " + movie.getTitle());
        addFragment(ShowMovieFragment.create(language));
    }

    private void addFragment(Fragment fragment) {
        checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showMovies() {
        showFragment(FindMoviesFragment.create());
    }

    private void showFragment(Fragment fragment) {
        checkNotNull(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, fragment, fragment.getTag());
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @dagger.Module
    public static abstract class Module {
        @ScreenScope
        @ContributesAndroidInjector(modules = ShowMovieFragment.Module.class)
        abstract ShowMovieFragment showMovieFragment();
        @Singleton
        @Binds
        abstract MainContract.Presenter mainPresenter(MainPresenter presenter);
    }
}
