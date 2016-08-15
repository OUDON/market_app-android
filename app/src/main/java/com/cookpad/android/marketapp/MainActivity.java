package com.cookpad.android.marketapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityMainBinding;
import com.cookpad.android.marketapp.databinding.CellRecommendBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final RecommendAdapter adapter = new RecommendAdapter();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setClickListener(new RecommendAdapter.ClickListener() {
            @Override
            public void onClickItem(Item item, View view) {
                Intent intent = DetailActivity.createIntent(MainActivity.this, item.getId());
                startActivity(intent);
            }
        });

        MarketServiceHolder.get()
                .recommendItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        Log.d("MarketApp", String.valueOf(items.size()));
                        for (Item item : items) {
                            adapter.add(item);
                        }
                    }
                });

        // RecommendAdapterに更新イベントを送る
        adapter.notifyDataSetChanged();
    }
}
