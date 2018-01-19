package com.wiatec.ldservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wiatec.ldservice.R;


/**
 * ChannelTypeViewHolder
 */

public class PlayChannelViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public PlayChannelViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);

    }
}
