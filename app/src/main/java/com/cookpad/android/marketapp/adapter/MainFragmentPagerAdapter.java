package com.cookpad.android.marketapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cookpad.android.marketapp.CategoryFragment;
import com.cookpad.android.marketapp.RecommendFragment;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecommendFragment();
           default:
                return new CategoryFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "おすすめ";
            case 1:
                return "カテゴリー";
        }
        return "";
    }

}
