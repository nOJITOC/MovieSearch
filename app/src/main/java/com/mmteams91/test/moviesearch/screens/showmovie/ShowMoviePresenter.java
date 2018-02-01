package com.mmteams91.test.moviesearch.screens.showmovie;

import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.managers.DataManager;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.data.network.dto.Genre;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

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
                .subscribe(this::showMovie, throwable -> view.showError(throwable)));
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
        if (stringValidate(movieDto.getTagline()))
            view.showSubTitle(movieDto.getTagline());
        view.showTitle(title);
        if (movieDto.getPosterPath() != null && !movieDto.getPosterPath().isEmpty())
            view.showPoster(baseUrl + posterSize + movieDto.getPosterPath());
        List<DataWithLabelAdapter.Item> info = convertToInfoList(movieDto);
        view.showInfo(info);
    }

    private List<DataWithLabelAdapter.Item> convertToInfoList(MovieDto movieDto) {

        List<DataWithLabelAdapter.Item> info = new ArrayList<>();
        if (stringValidate(movieDto.getOverview()))
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.overview), movieDto.getOverview()));
        if (listValidate(movieDto.getGenres())) {
            String genres = "";
            for (Genre genre : movieDto.getGenres()) {
                if (!genres.isEmpty())
                    genres += ", ";
                genres += genre.getName();
            }
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.genre), genres));
        }
        if (listValidate(movieDto.getProductionCountries())) {
            String countries = "";
            for (MovieDto.ProductionCountry country : movieDto.getProductionCountries()) {
                if (!countries.isEmpty())
                    countries += ", ";
                countries += country.getName();
            }
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.country), countries));
        }
        if (listValidate(movieDto.getProductionCompanies())) {
            String companies = "";
            for (MovieDto.ProductionCompany company : movieDto.getProductionCompanies()) {
                if (!companies.isEmpty())
                    companies += ", ";
                companies += company.getName();
            }
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.company), companies));
        }
        if (stringValidate(movieDto.getReleaseDate()))
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.release_date), movieDto.getReleaseDate()));
        if (checkNotNull(movieDto.getRuntime())) {
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.runtime), String.valueOf(movieDto.getRuntime())));
        }
        if (checkNotNull(movieDto.getBudget())) {
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.budget), String.valueOf(movieDto.getBudget())));
        }
        if (checkNotNull(movieDto.getRevenue())) {
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.revenue), String.valueOf(movieDto.getRevenue())));
        }
        if (listValidate(movieDto.getSpokenLanguages())) {
            String languages = "";
            for (MovieDto.SpokenLanguage spokenLanguage : movieDto.getSpokenLanguages()) {
                if (!languages.isEmpty())
                    languages += ", ";
                languages += spokenLanguage.getName();
            }
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.language), languages));
        }
        if (checkNotNull(movieDto.getVoteAverage())) {
            String vote = String.valueOf(movieDto.getVoteAverage());
            if (checkNotNull(movieDto.getVoteCount())) {
                vote += "/" + movieDto.getVoteCount();
            }
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.vote), vote));
        }
        if (stringValidate(movieDto.getHomepage())) {
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.homepage), movieDto.getHomepage()));
        }
        if (checkNotNull(movieDto.getPopularity())) {
            info.add(new DataWithLabelAdapter.Item(view.getLocalizedString(R.string.popularity), String.valueOf(movieDto.getPopularity())));
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
        compositeDisposable.clear();
        this.view = null;
    }
}
