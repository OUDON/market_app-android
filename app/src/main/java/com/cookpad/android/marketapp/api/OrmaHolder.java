package com.cookpad.android.marketapp.api;

import android.content.Context;

import com.cookpad.android.marketapp.model.OrmaDatabase;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class OrmaHolder {

    static OrmaDatabase INSTANCE;

    public static OrmaDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = createInstance(context);
        }
        return INSTANCE;
    }

    private static OrmaDatabase createInstance(Context context) {
        return OrmaDatabase.builder(context).build();
    }
}