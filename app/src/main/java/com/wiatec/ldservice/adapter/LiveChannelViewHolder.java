package com.wiatec.ldservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiatec.ldservice.R;


/**
 * Channel ViewHolder
 */

public class LiveChannelViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;
    public TextView tvType;
    public TextView tvPrice;

    public LiveChannelViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
        tvType = itemView.findViewById(R.id.tvType);
        tvPrice = itemView.findViewById(R.id.tvPrice);

    }
}
