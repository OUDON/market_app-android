package com.cookpad.android.marketapp;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cookpad.android.marketapp.adapter.MainFragmentPagerAdapter;
import com.cookpad.android.marketapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RecommendFragment());
        transaction.commit();

        /**
        FragmentManager manager = getFragmentManager();
        final MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(manager);
        **/
    }
}
