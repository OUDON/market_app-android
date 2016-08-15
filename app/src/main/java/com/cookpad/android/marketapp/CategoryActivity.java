package com.cookpad.android.marketapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.cookpad.android.marketapp.adapter.CategoriesAdapter;
import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityCategoryBinding;
import com.cookpad.android.marketapp.databinding.ActivityMainBinding;
import com.cookpad.android.marketapp.model.Category;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final CategoriesAdapter adapter = new CategoriesAdapter();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // カテゴリの詳細へ
        adapter.setClickListener(new CategoriesAdapter.ClickListener() {
            @Override
            public void onClickItem(Category category, View view) {
                Intent intent = CategoryDetailActivity.createIntent(CategoryActivity.this, category.getId());
                startActivity(intent);
            }
        });

        MarketServiceHolder.get()
                .categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Category>>() {
                    @Override
                    public void call(List<Category> categories) {
                        for (Category category : categories) {
                            adapter.add(category);
                        }
                    }
                });

        // RecommendAdapterに更新イベントを送る
        adapter.notifyDataSetChanged();
    }
}
