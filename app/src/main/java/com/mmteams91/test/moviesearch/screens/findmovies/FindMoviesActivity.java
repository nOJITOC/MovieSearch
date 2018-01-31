package com.mmteams91.test.moviesearch.screens.findmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ImageView;

import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Михаил on 31.01.2018.
 */

public class FindMoviesActivity extends DaggerAppCompatActivity implements FindMoviesContract.View {

    private static final String TAG = "FindMoviesActivity";
    public static final String MOVIE_PREVIEW_KEY = "MOVIE_PREVIEW_KEY";
    public static final String LANGUAGE_KEY = "LANGUAGE_KEY";
    private static final String POSTER_NAME = "poster";
    @Inject
    FindMoviesContract.Presenter presenter;
    private RecyclerView moviesContainer;
    private SearchView searchView;
    private FindMoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_movie);
        presenter.takeView(this);
        prepareMoviesContainer();
        prepareSearchView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    private void prepareSearchView() {
        searchView = findViewById(R.id.search);
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
    }

    private void prepareMoviesContainer() {
        moviesContainer = findViewById(R.id.movie_container);
        adapter = new FindMoviesAdapter();
        moviesContainer.setAdapter(adapter);
        moviesContainer.addItemDecoration(new GridSpacingItemDecoration(3, 32));
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        moviesContainer.setLayoutManager(new GridLayoutManager(this, 3));

        adapter.setOnLastItemBindListener(() -> presenter.onLastItemBind());
        adapter.setOnItemClickListener((view, item) -> {
            ImageView poster = (ImageView) ((ViewGroup) view).getChildAt(0);
            ViewCompat.setTransitionName(poster, POSTER_NAME);
            showMovie(poster, item, presenter.getLanguage());
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

    public void showMovie(View view, FindMovieDto movie, String language) {
        Log.e(TAG, "showMovieInfo: " + movie.getTitle());
        Intent intent = new Intent(this, ShowMovieActivity.class);
        intent.putExtra(MOVIE_PREVIEW_KEY, movie);
        intent.putExtra(LANGUAGE_KEY, language);
        Pair<View, String> p1 = new Pair<>(view, POSTER_NAME);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1);
        startActivity(intent, options.toBundle());
    }
}
