package com.cookpad.android.marketapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityMainBinding;
import com.cookpad.android.marketapp.databinding.FragmentRecyclerBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class RecommendFragment extends Fragment {
    private FragmentRecyclerBinding binding;

    //レイアウト定義をセットする
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("RecommendFragment", "onViewCreated");

        super.onViewCreated(view, savedInstanceState);
        final RecommendAdapter adapter = new RecommendAdapter();

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter.setClickListener(new RecommendAdapter.ClickListener() {
            @Override
            public void onClickItem(Item item, View view) {
                Intent intent = DetailActivity.createIntent(getActivity(), item.getId());
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
                        for (Item item : items) {
                            adapter.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
