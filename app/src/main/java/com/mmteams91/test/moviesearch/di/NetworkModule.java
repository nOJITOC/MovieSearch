package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.utils.NetworkStatusChecker;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class NetworkModule {
    @Singleton
    @Binds
    abstract NetworkStatusChecker provideNetworkChecker(NetworkStatusChecker checker);

    @Singleton
    @Provides
    static RestApi makeRest(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }
}
