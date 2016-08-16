package com.cookpad.android.marketapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cookpad.android.marketapp.R;
import com.cookpad.android.marketapp.api.MarketServiceHolder;
import com.cookpad.android.marketapp.databinding.CellCartBinding;
import com.cookpad.android.marketapp.databinding.CellRecommendBinding;
import com.cookpad.android.marketapp.model.CartItem;
import com.cookpad.android.marketapp.model.Item;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by takahiro-tomita on 2016/08/16.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CartItem citem = items.get(position);
        final int item_id = citem.itemId;
        final int quantity = citem.count;
        final Context context = holder.binding.getRoot().getContext();

        MarketServiceHolder.get()
                .getItemById(item_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Item>() {
                    @Override
                    public void call(Item item) {
                        holder.binding.itemPrice.setText(item.getPrice() + "円");
                        Glide.with(context).load(item.getImageUrl()).into(holder.binding.itemThumbnail);
                        holder.binding.itemQuantity.setText(quantity + "個");
                        holder.binding.itemName.setText(item.getName());
                    }
                });

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickItem(citem, view);
                }
            }
        });
    }

    public void add(CartItem item){
        items.add(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface ClickListener {
        void onClickItem(CartItem item, View view);
    }
    private ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CellCartBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
