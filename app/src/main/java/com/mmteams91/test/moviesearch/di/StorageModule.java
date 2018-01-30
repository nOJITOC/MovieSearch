package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.data.managers.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;

/**
 * Created by User New on 30.01.2018.
 */
@Module
abstract class StorageModule {
    @Singleton
    abstract PreferenceManager preferenceManager();
}
