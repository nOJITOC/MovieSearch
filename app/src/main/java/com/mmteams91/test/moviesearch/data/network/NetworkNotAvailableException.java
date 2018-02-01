package com.mmteams91.test.moviesearch.data.network;

/**
 * Created by User New on 01.02.2018.
 */

public class NetworkNotAvailableException extends RuntimeException{
    public NetworkNotAvailableException() {
        super("Интернет недоступен");
    }
}
