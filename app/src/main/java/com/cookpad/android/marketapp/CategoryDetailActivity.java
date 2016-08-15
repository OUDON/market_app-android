package com.cookpad.android.marketapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CategoryDetailActivity extends AppCompatActivity {
    public final static String EXTRA_CATEGORY_ID = "extra_category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
    }

    public static Intent createIntent(Context context, int categoryId) {
        Intent intent = new Intent(context, CategoryDetailActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
