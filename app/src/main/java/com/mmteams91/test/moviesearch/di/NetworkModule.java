package com.mmteams91.test.moviesearch.di;

import android.content.Context;

import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.utils.NetworkStatusChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public class NetworkModule {
    @Singleton
    @Provides
    NetworkStatusChecker provideNetworkChecker(Context context) {
        return new NetworkStatusChecker(context);
    }

    @Singleton
    @Provides
    RestApi makeRest(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }
}
