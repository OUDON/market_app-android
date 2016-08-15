package com.cookpad.android.marketapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookpad.android.marketapp.adapter.CategoriesAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityCategoryBinding;
import com.cookpad.android.marketapp.databinding.FragmentRecyclerBinding;
import com.cookpad.android.marketapp.model.Category;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class CategoryFragment extends Fragment {
    private FragmentRecyclerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        final CategoriesAdapter adapter = new CategoriesAdapter();
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recycler);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // カテゴリの詳細へ
        adapter.setClickListener(new CategoriesAdapter.ClickListener() {
            @Override
            public void onClickItem(Category category, View view) {
                Intent intent = CategoryDetailActivity.createIntent(getActivity(), category.getId());
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
