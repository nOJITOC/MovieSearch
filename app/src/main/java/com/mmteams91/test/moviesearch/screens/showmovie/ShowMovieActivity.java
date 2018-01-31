package com.mmteams91.test.moviesearch.screens.showmovie;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesActivity;

import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class ShowMovieActivity extends DaggerAppCompatActivity implements ShowMovieContract.View {
    private static final String TAG = "ShowMovieActivity";
    @Inject
    ShowMovieContract.Presenter presenter;
    private ImageView poster;
    private TextView title;
    private RecyclerView infoContainer;
    private DataWithLabelAdapter adapter;
    private Toolbar toolbar;
    private Resources localizedResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        prepareToolbar();
        poster = findViewById(R.id.image);
        title = findViewById(R.id.title);
        infoContainer = findViewById(R.id.info_container);
        adapter = new DataWithLabelAdapter();
        infoContainer.setAdapter(adapter);
        infoContainer.addItemDecoration(new InfoSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_info)));
        infoContainer.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        presenter.takeView(this);
        presenter.onCreateView();
    }

    private void prepareToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public String getLocalizedString(@StringRes int stringId) {
        return getLocalizedResources().getString(stringId);
    }

    private Resources getLocalizedResources() {
        if (localizedResources == null) {
            Locale locale = getLocale(getLanguage());
            Configuration configuration = new Configuration(getResources().getConfiguration());
            configuration.setLocale(locale);
            localizedResources = createConfigurationContext(configuration).getResources();
        }
        return localizedResources;
    }

    private Locale getLocale(String language) {
        String[] split = language.split("\\W");
        if (split.length == 0)
            return new Locale("ru");
        else
            return new Locale(split[0]);
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        this.title.setText(title);
    }

    @Override
    public void showPoster(String posterUri) {
        Drawable drawable = poster.getDrawable() == null ? getResources().getDrawable(R.drawable.placeholder) : poster.getDrawable();
        Glide.with(this)
                .load(posterUri)
                .placeholder(drawable)
                .error(drawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(poster);
    }

    @Override
    public void showInfo(Collection<DataWithLabelAdapter.Item> info) {
        adapter.addItems(info);
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
