package com.mmteams91.test.moviesearch.screens.findmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FindMoviesFragment extends DaggerFragment implements FindMoviesContract.View {
    @Inject
    FindMoviesContract.Presenter presenter;
    private RecyclerView moviesContainer;
    private SearchView searchView;
    private FindMoviesAdapter adapter;

    public FindMoviesFragment() {
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

    public static FindMoviesFragment create() {
        return new FindMoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_movies, container, false);
        moviesContainer = view.findViewById(R.id.movie_container);
        adapter = new FindMoviesAdapter();
        moviesContainer.setAdapter(adapter);
        adapter.setOnLastItemBindListener(() -> presenter.onLastItemBind());
        adapter.setOnItemClickListener(item -> presenter.onMovieClick(item));
        searchView = view.findViewById(R.id.search);
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

        return view;
    }

    @Override
    public void clearPrevResult() {
        adapter.clear();
    }

    @Override
    public void addMovies(List<FindMovieDto> movies) {
        adapter.addItems(movies);
    }

    @Override
    public String getKeyboardLocale() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();

        String locale;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = ims.getLanguageTag();
        } else locale = ims.getLocale();
        return locale;
    }
}
