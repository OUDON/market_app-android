package com.cookpad.android.marketapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        // Navigation Drawer
        String[] drawer_strs = {"おすすめ", "カテゴリー", "カートを表示"};
         binding.leftDrawer.setAdapter((new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawer_strs)));
         binding.leftDrawer.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                // Show Recommend
                transaction.replace(R.id.fragment_container, new RecommendFragment());
                break;
            case 1:
                transaction.replace(R.id.fragment_container, new CategoryFragment());
                break;
            case 2:
                transaction.replace(R.id.fragment_container, new CartFragment());
                break;
        }
        transaction.commit();
        binding.drawerLayout.closeDrawer(binding.leftDrawer);
    }
}
