package com.avl.cagApp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.avl.cagApp.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imgItem;
    TextView txtItem;
    View _itemView;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        _itemView = itemView;
        imgItem = itemView.findViewById(R.id.imgDisplay);
        txtItem = itemView.findViewById(R.id.txtDisplay);
    }

    public View getView() { return _itemView; }
}
