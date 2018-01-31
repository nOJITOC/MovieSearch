package com.mmteams91.test.moviesearch.screens.showmovie;

import android.util.Log;

import com.mmteams91.test.moviesearch.data.managers.DataManager;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.Genre;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class ShowMoviePresenter implements ShowMovieContract.Presenter {

    private static final String TAG = "ShowMoviePresenter";
    private String language;
    private DataManager dataManager;
    private ShowMovieContract.View view;
    private FindMovieDto preview;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ShowMoviePresenter(DataManager dataManager, FindMovieDto movie, String language) {
        this.dataManager = dataManager;
        this.preview = movie;
        this.language = language;
    }

    @Override
    public void onCreateView() {
        showPreview();
        compositeDisposable.add(dataManager.loadMovie(preview.getId(), language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMovie, throwable -> {
                    Log.e(TAG, "accept: ", throwable);
                    if (throwable instanceof HttpException)
                        Log.e(TAG, "takeView: " + ((HttpException) throwable).response().raw());
                }));
    }

    private void showPreview() {
        String posterSize = dataManager.getPosterSize();
        String baseUrl = dataManager.getBaseImageUrl();
        view.showTitle(preview.getTitle());
        view.showPoster(baseUrl + posterSize + preview.getPosterPath());
    }

    @Override
    public void takeView(ShowMovieContract.View view) {
        this.view = view;
    }

    private void showMovie(MovieDto movieDto) {

        String title = "";
        if (movieDto.getTitle() != null)
            title += movieDto.getTitle();
        if (movieDto.getOriginalTitle() != null) {
            if (title.isEmpty())
                title = movieDto.getOriginalTitle();
            if (!title.isEmpty() && !title.equals(movieDto.getOriginalTitle()))
                title += " / " + movieDto.getOriginalTitle();
        }
        String posterSize = "original";
        String baseUrl = dataManager.getBaseImageUrl();
        view.showTitle(title);
        if (movieDto.getPosterPath() != null && !movieDto.getPosterPath().isEmpty())
            view.showPoster(baseUrl + posterSize + movieDto.getPosterPath());
        List<DataWithLabelAdapter.Item> info = convertToInfoList(movieDto);
        view.showInfo(info);
    }

    private List<DataWithLabelAdapter.Item> convertToInfoList(MovieDto movieDto) {
        List<DataWithLabelAdapter.Item> info = new ArrayList<>();
        if (stringValidate(movieDto.getOverview()))
            info.add(new DataWithLabelAdapter.Item("Описание", movieDto.getOverview()));
        if (stringValidate(movieDto.getReleaseDate()))
            info.add(new DataWithLabelAdapter.Item("Дата выхода", movieDto.getReleaseDate()));
        if (listValidate(movieDto.getGenres())) {
            String genres = "";
            for (Genre genre : movieDto.getGenres()) {
                if (!genres.isEmpty())
                    genres += ", ";
                genres += genre.getName();
            }
            info.add(new DataWithLabelAdapter.Item("Жанр", genres));
        }
        if (listValidate(movieDto.getProductionCountries())) {
            String countries = "";
            for (MovieDto.ProductionCountry country : movieDto.getProductionCountries()) {
                if (!countries.isEmpty())
                    countries += ", ";
                countries += country.getName();
            }
            info.add(new DataWithLabelAdapter.Item("Страна", countries));
        }
        if (listValidate(movieDto.getProductionCompanies())) {
            String companies = "";
            for (MovieDto.ProductionCompany company : movieDto.getProductionCompanies()) {
                if (!companies.isEmpty())
                    companies += ", ";
                companies += company.getName();
            }
            info.add(new DataWithLabelAdapter.Item("Киностудия", companies));
        }
        return info;
    }

    private boolean stringValidate(String string) {
        return checkNotNull(string) && !checkIsEmpty(string);
    }

    private boolean listValidate(List list) {
        return checkNotNull(list) && !checkIsEmpty(list);
    }

    private boolean checkIsEmpty(List list) {
        return list.isEmpty();
    }

    private boolean checkIsEmpty(String string) {
        return string.isEmpty();
    }

    private boolean checkNotNull(Object object) {
        return object != null;
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
        this.view = null;
    }
}
