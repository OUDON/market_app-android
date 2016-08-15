package com.cookpad.android.marketapp.api;

import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public interface MarketService {
    @GET("items/recommended.json")
    Observable<List<Item>> recommendItems();

    @GET("items/{id}.json")
    Observable<Item> getItemById(@Path("id") int id);
}
