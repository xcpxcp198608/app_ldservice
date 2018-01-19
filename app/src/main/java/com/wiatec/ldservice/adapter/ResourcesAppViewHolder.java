package com.wiatec.ldservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiatec.ldservice.R;

/**
 * Created by patrick on 14/01/2018.
 * create time : 10:10 PM
 */

public class ResourcesAppViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivIcon;
    public TextView tvLable;

    public ResourcesAppViewHolder(View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.iv_icon);
        tvLable = itemView.findViewById(R.id.tv_label);
    }
}
