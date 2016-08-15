package com.cookpad.android.marketapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.cookpad.android.marketapp.adapter.CategoryDetailAdapter;
import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityCategoryDetailBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CategoryDetailActivity extends AppCompatActivity {
    private ActivityCategoryDetailBinding binding;
    public final static String EXTRA_CATEGORY_ID = "extra_category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        final CategoryDetailAdapter adapter = new CategoryDetailAdapter();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_detail);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        adapter.setClickListener(new RecommendAdapter.ClickListener() {
            @Override
            public void onClickItem(Item item, View view) {
                Intent intent = DetailActivity.createIntent(CategoryDetailActivity.this, item.getId());
                startActivity(intent);
            }
        });*/

        Intent intent = getIntent();
        final int category_id = intent.getIntExtra(EXTRA_CATEGORY_ID, -1);
        if (category_id != -1) {
            MarketServiceHolder.get()
                    .categolizedItems(category_id)
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
        }

        // RecommendAdapterに更新イベントを送る
        adapter.notifyDataSetChanged();
    }

    public static Intent createIntent(Context context, int categoryId) {
        Intent intent = new Intent(context, CategoryDetailActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
