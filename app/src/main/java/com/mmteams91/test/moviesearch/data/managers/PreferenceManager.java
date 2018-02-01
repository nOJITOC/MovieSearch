package com.mmteams91.test.moviesearch.data.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.mmteams91.test.moviesearch.utils.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by User New on 30.01.2018.
 */
@Singleton
public class PreferenceManager {

    private static final String PREF_NAME = "com.mmteams91.test.moviesearch.prefs";
    private static final String POSTER_WIDTH_KEY = "POSTER_WIDTH_KEY";
    private static final String BASE_IMAGE_URL_KEY = "BASE_IMAGE_URL_KEY";
    private SharedPreferences preferences;

    @Inject
    PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    private String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void savePosterSize(String width) {
        putString(POSTER_WIDTH_KEY, width);
    }

    public String getPosterSize() {
        return getString(POSTER_WIDTH_KEY, "original");
    }

    public void saveBaseImageUrl(String baseImageUrl){
        putString(BASE_IMAGE_URL_KEY,baseImageUrl);
    }

    public String getBaseImageUrl(){
        return getString(BASE_IMAGE_URL_KEY, Constants.DEFAULT_BASE_IMAGE_URL);
    }


}
