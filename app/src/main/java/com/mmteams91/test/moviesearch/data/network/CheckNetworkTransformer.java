package com.mmteams91.test.moviesearch.data.network;

import com.mmteams91.test.moviesearch.utils.NetworkStatusChecker;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User New on 01.02.2018.
 */

public class CheckNetworkTransformer<R> implements ObservableTransformer<R, R> {
    NetworkStatusChecker networkStatusChecker;

    public CheckNetworkTransformer(NetworkStatusChecker networkStatusChecker) {
        this.networkStatusChecker = networkStatusChecker;
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return networkStatusChecker.isNetworkAvailable()
                .switchMap(aBoolean -> {
                    if (aBoolean)
                        return upstream;
                    else return Observable.error(new NetworkNotAvailableException());
                }).subscribeOn(Schedulers.io());
    }
}
