package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.AppConfig;
import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public class NetworkModule {


    RestApi makeRest(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(AppConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.READ_TIMEOUT,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(RestApi.class);
    }

    private Moshi createMoshi() {
        return new Moshi.Builder()
                .build();
    }
}
