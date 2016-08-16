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

import com.cookpad.android.marketapp.R;
import com.cookpad.android.marketapp.adapter.CartAdapter;
import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.api.OrmaHolder;
import com.cookpad.android.marketapp.databinding.FragmentRecyclerBinding;
import com.cookpad.android.marketapp.model.CartItem;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Takahiro on 2016/08/16.
 */
public class CartFragment extends Fragment {
    private FragmentRecyclerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final CartAdapter adapter = new CartAdapter();

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        OrmaHolder.get(getContext())
                .selectFromCartItem()
                .executeAsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Action1<List<CartItem>>() {
                    @Override
                    public void call(List<CartItem> items) {
                        for (CartItem item : items) {
                            adapter.add(item);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}