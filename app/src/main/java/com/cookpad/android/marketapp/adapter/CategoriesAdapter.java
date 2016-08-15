package com.cookpad.android.marketapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cookpad.android.marketapp.R;
import com.cookpad.android.marketapp.databinding.CellCategoryBinding;
import com.cookpad.android.marketapp.databinding.CellRecommendBinding;
import com.cookpad.android.marketapp.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> categories = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category item = categories.get(position);
        Context context = holder.binding.getRoot().getContext();

        holder.binding.categoryName.setText(item.getName());
        Glide.with(context).load(item.getImageUrl()).into(holder.binding.categoryThumbnail);


        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickItem(item, view);
                }
            }
        });
    }

    public void add(Category category){
        categories.add(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface ClickListener {
        void onClickItem(Category category, View view);
    }
    private ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CellCategoryBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}