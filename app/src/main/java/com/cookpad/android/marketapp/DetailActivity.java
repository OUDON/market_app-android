package com.cookpad.android.marketapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.ActivityDetailBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    public static String EXTRA_ITEM_ID = "extra_item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        final Context context = binding.getRoot().getContext();

        Intent intent = getIntent();
        int item_id = intent.getIntExtra(EXTRA_ITEM_ID, -1);

        Log.d("MarketApp", "item_id : " + item_id);
        if (item_id != -1) {
            MarketServiceHolder.get()
                    .getItemById(item_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Item>() {
                        @Override
                        public void call(Item item) {
                            // Log.d("AppMarket", "==================");
                            binding.itemName.setText(item.getName());
                            binding.itemDescription.setText(item.getDescription());
                            binding.itemPrice.setText(item.getPrice() + "円");
                            Glide.with(context).load(item.getImageUrl()).into(binding.itemThumbnail);
                        }
                    });
        }
    }


    public static Intent createIntent(Context context, int itemId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

}
