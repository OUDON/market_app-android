package com.cookpad.android.marketapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cookpad.android.marketapp.R;
import com.cookpad.android.marketapp.databinding.CellRecommendBinding;
import com.cookpad.android.marketapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */
public class RecommendAdapter extends RecyclerView.Adapter< RecommendAdapter.ViewHolder> {
    private List<Item> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.binding.itemName.setText(item.getName());
        holder.binding.itemPrice.setText(item.getPrice() + "円");

        Context context = holder.binding.getRoot().getContext();
        Glide.with(context).load(item.getImageUrl()).into(holder.binding.itemThumbnail);


        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickItem(item, view);
                }
            }
        });
    }

    public void add(Item item){
        items.add(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface ClickListener {
        void onClickItem(Item item, View view);
    }
    private ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CellRecommendBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
