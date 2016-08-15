package com.cookpad.android.marketapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.api.OrmaHolder;
import com.cookpad.android.marketapp.databinding.ActivityDetailBinding;
import com.cookpad.android.marketapp.model.CartItem;
import com.cookpad.android.marketapp.model.Item;
import com.cookpad.android.marketapp.model.OrmaDatabase;

import java.util.List;

import rx.AsyncEmitter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    public static String EXTRA_ITEM_ID = "extra_item_id";

    private Item targetItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        final Context context = binding.getRoot().getContext();

        Intent intent = getIntent();
        int item_id = intent.getIntExtra(EXTRA_ITEM_ID, -1);

        if (item_id != -1) {
            MarketServiceHolder.get()
                    .getItemById(item_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Item>() {
                        @Override
                        public void call(Item item) {
                            setTitle(item.getName());
                            binding.itemDescription.setText(item.getDescription());
                            binding.itemPrice.setText(item.getPrice() + "円");
                            Glide.with(context).load(item.getImageUrl()).into(binding.itemThumbnail);

                            targetItem = item;
                        }
                    });
        }

        // Add cart
        binding.buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addCart(1)) {
                    Toast.makeText(DetailActivity.this, "カートに追加されました", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean addCart(final int quanity) {
        if (targetItem == null) return false;

        final OrmaDatabase orma = OrmaHolder.get(DetailActivity.this);
        Observable.fromAsync(new Action1<AsyncEmitter<CartItem>>() {
            @Override
            public void call(AsyncEmitter<CartItem> emitter) {
                CartItem cartItem = CartItem.createCartItem(targetItem, quanity); // CartItemを何かしらの方法で作る
                orma.insertIntoCartItem(cartItem);
                emitter.onNext(cartItem);
                emitter.onCompleted();
            }
        }, AsyncEmitter.BackpressureMode.NONE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CartItem>() {
                    @Override
                    public void call(CartItem item) {
                    }
                });

        return true;
    }

    public static Intent createIntent(Context context, int itemId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

}
