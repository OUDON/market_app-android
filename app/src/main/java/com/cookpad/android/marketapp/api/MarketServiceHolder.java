package com.cookpad.android.marketapp.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class MarketServiceHolder {

    private static MarketService INSTANCE;

    public static MarketService get() {
        if (INSTANCE == null) {
            INSTANCE = createInstance();
        }
        return INSTANCE;
    }

    private static MarketService createInstance() {
        return retrofit().create(MarketService.class);
    }

    private static Retrofit retrofit() {
        final String ENDPOINT = "http://192.168.120.128:3000/";
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}