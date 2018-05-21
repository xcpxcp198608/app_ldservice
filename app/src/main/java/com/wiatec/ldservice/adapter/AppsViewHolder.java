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

public class AppsViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivIcon;
    public TextView tvLabel;
    public TextView btUninstall;

    public AppsViewHolder(View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.ivIcon);
        tvLabel = itemView.findViewById(R.id.tvLabel);
        btUninstall = itemView.findViewById(R.id.btUninstall);
    }
}
