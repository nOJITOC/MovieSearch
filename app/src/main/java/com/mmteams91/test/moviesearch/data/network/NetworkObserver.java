package com.mmteams91.test.moviesearch.data.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Михаил on 30.01.2018.
 */

public abstract class NetworkObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
