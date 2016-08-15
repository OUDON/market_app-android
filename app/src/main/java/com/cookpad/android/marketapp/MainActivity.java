package com.cookpad.android.marketapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.cookpad.android.marketapp.adapter.RecommendAdapter;
import com.cookpad.android.marketapp.databinding.ActivityMainBinding;
import com.cookpad.android.marketapp.databinding.CellRecommendBinding;
import com.cookpad.android.marketapp.model.Item;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecommendAdapter adapter = new RecommendAdapter();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ダミーデータ
        adapter.add(new Item(0, "Orange", 1000));
        adapter.add(new Item(1, "Apple", 1200));
        adapter.add(new Item(2, "Banana", 1500));

        // RecommendAdapterに更新イベントを送る
        adapter.notifyDataSetChanged();
    }
}
