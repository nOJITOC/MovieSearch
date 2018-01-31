package com.mmteams91.test.moviesearch.screens.showmovie;

import android.content.res.Resources;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmteams91.test.moviesearch.utils.UiUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by User New on 31.01.2018.
 */

public class DataWithLabelAdapter extends RecyclerView.Adapter<DataWithLabelAdapter.ViewHolder> {

    List<Item> items;

    public DataWithLabelAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout container = createItemView(parent);
        return new ViewHolder(container);
    }

    @NonNull
    private LinearLayout createItemView(ViewGroup parent) {
        LinearLayout container = new LinearLayout(parent.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        return container;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.label.setText(item.label);
        holder.data.setText(item.data);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(Collection<Item> newItems) {
        int start = items.size();
        int count = newItems.size();
        items.addAll(newItems);
        notifyItemRangeInserted(start, count);
    }

    public static class Item {
        final String label;
        final String data;

        public Item(String label, String data) {
            this.label = label;
            this.data = data;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView data;

        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            Resources res = itemView.getResources();
            label = new TextView(itemView.getContext());
            label.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            label.setMaxLines(2);
            label.setEllipsize(TextUtils.TruncateAt.END);
            label.setTextSize(Dimension.SP, 12);
            int labelTextColor = res.getColor(android.support.v7.appcompat.R.color.material_grey_600);
            label.setTextColor(labelTextColor);
            itemView.addView(label);
            data = new TextView(itemView.getContext());
            data.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            data.setTextSize(Dimension.SP, 16);
            int dataTextColor = res.getColor(android.support.v7.appcompat.R.color.material_grey_800);
            data.setTextColor(dataTextColor);
            int dataPadding = UiUtils.dpToPx(4);
            data.setPadding(dataPadding, dataPadding, dataPadding, dataPadding);
            itemView.addView(data);
        }
    }
}
