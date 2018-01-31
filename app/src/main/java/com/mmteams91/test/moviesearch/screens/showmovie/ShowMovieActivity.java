package com.mmteams91.test.moviesearch.screens.showmovie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class ShowMovieActivity extends DaggerAppCompatActivity implements ShowMovieContract.View {
    @Inject
    ShowMovieContract.Presenter presenter;
    private ImageView poster;
    private TextView title;
    private RecyclerView infoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        poster = findViewById(R.id.image);
        title = findViewById(R.id.title);
        infoContainer = findViewById(R.id.info_container);
        presenter.takeView(this);
        presenter.onCreateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showPreview(FindMovieDto preview) {
        title.setText(preview.getTitle());
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/original" + preview.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(poster);
    }

    @Override
    public void showMovie(MovieDto movieDto) {

    }

    @Override
    public FindMovieDto getClickedMovieDto() {
        return getIntent().getParcelableExtra(FindMoviesActivity.MOVIE_PREVIEW_KEY);
    }

    @Override
    public String getLanguage() {
        String language = getIntent().getStringExtra(FindMoviesActivity.LANGUAGE_KEY);
        return (language == null || language.isEmpty()) ? "ru-RU" : language;
    }
}
