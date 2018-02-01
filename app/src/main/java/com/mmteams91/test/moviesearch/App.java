package com.mmteams91.test.moviesearch;

import com.mmteams91.test.moviesearch.di.DaggerAppComponent;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by User New on 29.01.2018.
 */

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .retrofit(makeRetrofit())
                .build();
    }
    private Retrofit makeRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(AppConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.READ_TIMEOUT,TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    private Moshi createMoshi() {
        return new Moshi.Builder()
                .build();
    }
}
