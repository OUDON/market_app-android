package com.cookpad.android.marketapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookpad.android.marketapp.adapter.CategoryDetailAdapter;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.FragmentRecyclerBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class CategoryDetailFragment extends Fragment {
    private FragmentRecyclerBinding binding;
    private static final String ARGS_CATEGORY_ID = "category_id";
    private final static int COLUMN_NUM = 3;

    //レイアウト定義をセットする
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("CategoryDetailFragment", "=============== onViewCreated() ===============");

        final CategoryDetailAdapter adapter = new CategoryDetailAdapter();
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recycler);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_NUM));

        adapter.setClickListener(new CategoryDetailAdapter.ClickListener() {
            @Override
            public void onClickItem(Item item, View view) {
                Intent intent = DetailActivity.createIntent(getActivity(), item.getId());
                startActivity(intent);
            }
        });

        Bundle args = getArguments();
        final int category_id = args.getInt(ARGS_CATEGORY_ID, -1);

        if (category_id != -1) {
            MarketServiceHolder.get()
                    .categolizedItems(category_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Item>>() {
                        @Override
                        public void call(List<Item> items) {
                            for (Item item : items) {
                                adapter.add(item);
                            }
                        }
                    });
        }

        // RecommendAdapterに更新イベントを送る
        adapter.notifyDataSetChanged();
    }

    public static CategoryDetailFragment newInstance(int categoryId) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

}
