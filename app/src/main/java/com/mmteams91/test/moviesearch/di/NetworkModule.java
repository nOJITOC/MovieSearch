package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.data.network.RestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class NetworkModule {

    @Singleton
    @Provides
    static RestApi makeRest(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }
}
