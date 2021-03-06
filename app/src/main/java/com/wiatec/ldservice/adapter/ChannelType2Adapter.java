package com.wiatec.ldservice.adapter;

import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.image.ImageMaster;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.pojo.ChannelType2Info;

import java.util.List;

/**
 * channel adapter
 */

public class ChannelType2Adapter extends BaseRecycleAdapter<ChannelType2ViewHolder> {

    private List<ChannelType2Info> channelType2InfoList;

    public ChannelType2Adapter(List<ChannelType2Info> channelType2InfoList) {
        this.channelType2InfoList = channelType2InfoList;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_rcv_channel;
    }

    @Override
    protected ChannelType2ViewHolder createHolder(View view) {
        return new ChannelType2ViewHolder(view);
    }

    @Override
    protected void bindHolder(final ChannelType2ViewHolder holder, final int position) {
        ChannelType2Info channelType2Info = channelType2InfoList.get(position);
        holder.textView.setText(channelType2Info.getName());
        ImageMaster.load(channelType2Info.getUrl(), holder.imageView, R.drawable.img_hold,
                R.drawable.img_hold);
    }

    @Override
    public int getItemCounts() {
        return channelType2InfoList.size();
    }
}
