package com.avl.cagApp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avl.cagApp.R;
import com.avl.cagApp.model.DisplayOutputItem;

import java.util.List;

public class DisplayOutputAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<DisplayOutputItem> displayOutputItems;
    private IDisplayOutputListener listener;

    public DisplayOutputAdapter(List<DisplayOutputItem> displayOutputItems, IDisplayOutputListener listener) {
        this.displayOutputItems = displayOutputItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_output, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DisplayOutputItem item = displayOutputItems.get(position);
        holder.imgItem.setImageResource(item.getImgResId());
        holder.txtItem.setText(item.getDisplayName());
        holder.getView().setOnClickListener(view -> {
            this.listener.onDisplayOutputItemClick(item);
        });

    }

    @Override
    public int getItemCount() {
        return displayOutputItems.size();
    }
}
