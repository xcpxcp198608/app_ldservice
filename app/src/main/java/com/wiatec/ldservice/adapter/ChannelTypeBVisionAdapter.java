package com.wiatec.ldservice.adapter;

import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.image.ImageMaster;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.pojo.ChannelTypeInfo;

import java.util.List;

/**
 * channel type adapter
 */

public class ChannelTypeBVisionAdapter extends BaseRecycleAdapter<ChannelTypeViewHolder> {

    private List<ChannelTypeInfo> channelTypeInfoList;

    public ChannelTypeBVisionAdapter(List<ChannelTypeInfo> channelTypeInfoList) {
        this.channelTypeInfoList = channelTypeInfoList;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_rcv_channel_type_bvision;
    }

    @Override
    protected ChannelTypeViewHolder createHolder(View view) {
        return new ChannelTypeViewHolder(view);
    }

    @Override
    protected void bindHolder(ChannelTypeViewHolder holder, int position) {
        ChannelTypeInfo channelTypeInfo = channelTypeInfoList.get(position);
        holder.textView.setText(channelTypeInfo.getName());
        ImageMaster.load(channelTypeInfo.getIcon(),holder.imageView, R.drawable.bg_button_holder ,
                R.drawable.bg_button_holder);
    }

    @Override
    public int getItemCounts() {
        return channelTypeInfoList.size();
    }
}
