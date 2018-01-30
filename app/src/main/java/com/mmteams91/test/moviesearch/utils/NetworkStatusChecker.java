package com.mmteams91.test.moviesearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import io.reactivex.Observable;


public class NetworkStatusChecker {


    private final ConnectivityManager connectivityManager;

    @Inject
    public NetworkStatusChecker(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public Observable<Boolean> isNetworkAvailableReactive() {
        return Observable.just(isNetworkAvailable());
    }
}
