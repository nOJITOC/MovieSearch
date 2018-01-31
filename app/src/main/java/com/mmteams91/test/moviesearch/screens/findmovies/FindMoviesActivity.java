package com.mmteams91.test.moviesearch.screens.findmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieActivity;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieScope;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Михаил on 31.01.2018.
 */
@ShowMovieScope
public class FindMoviesActivity extends DaggerAppCompatActivity implements FindMoviesContract.View {

    private static final String TAG = "FindMoviesActivity";
    public static final String MOVIE_PREVIEW_KEY = "MOVIE_PREVIEW_KEY";
    public static final String LANGUAGE_KEY = "LANGUAGE_KEY";
    private static final String POSTER_NAME = "poster";
    private static final String TITLE_NAME = "title";
    @Inject
    FindMoviesContract.Presenter presenter;
    private RecyclerView moviesContainer;
    private FindMoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onDestroy: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_movie);
        presenter.takeView(this);
        prepareToolbar();
        prepareMoviesContainer(presenter.getImageUri());
    }

    private void prepareToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.find_movie_title);

    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
        presenter.dropView();
    }

    private void prepareMoviesContainer(String imageUri) {
        moviesContainer = findViewById(R.id.movie_container);
        adapter = new FindMoviesAdapter(imageUri);
        moviesContainer.setAdapter(adapter);
        int itemSpacing = getResources().getDimensionPixelSize(R.dimen.margin_item_find_movie);
        moviesContainer.addItemDecoration(new GridSpacingItemDecoration(3, itemSpacing));
        adapter.setOnLastItemBindListener(() -> presenter.onLastItemBind());
        adapter.setOnItemClickListener((view, item) -> {
            ImageView poster = (ImageView) ((ViewGroup) view).getChildAt(0);
            TextView title = (TextView) ((ViewGroup) view).getChildAt(1);
            showMovie(poster, title, item, presenter.getLanguage());
        });
    }

    @Override
    public void addMovies(List<FindMovieDto> movies) {
        adapter.addItems(movies);
    }

    @Override
    public String getKeyboardLocale() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();

        String locale;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = ims.getLanguageTag();
        } else locale = ims.getLocale();
        return locale;
    }

    @Override
    public void clearPrevResult() {
        adapter.clear();
    }

    @Override
    public int getDisplayWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public int getMovieContainerPadding() {
        return moviesContainer.getPaddingLeft() + moviesContainer.getPaddingRight();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.loadMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void showMovie(ImageView poster, View view, FindMovieDto movie, String language) {
        Log.e(TAG, "showMovieInfo: " + movie.getTitle());
        Intent intent = new Intent(this, ShowMovieActivity.class);
        intent.putExtra(MOVIE_PREVIEW_KEY, movie);
        intent.putExtra(LANGUAGE_KEY, language);
        Pair<View, String> p1 = new Pair<>(poster, POSTER_NAME);
        Pair<View, String> p2 = new Pair<>(view, TITLE_NAME);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2);
        startActivity(intent, options.toBundle());
    }
}
