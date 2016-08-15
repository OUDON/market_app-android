package com.cookpad.android.marketapp.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class Item {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    @SerializedName("image_url")
    private String imageUrl;

    public Item(int id, String name, String description, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {return description; }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() { return imageUrl; }
}
