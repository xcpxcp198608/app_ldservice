package com.wiatec.ldservice.adapter;

import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.image.ImageMaster;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.pojo.LiveChannelInfo;

import java.util.List;

/**
 * channel adapter
 */

public class LiveChannelAdapter extends BaseRecycleAdapter<LiveChannelViewHolder> {

    private List<LiveChannelInfo> liveChannelInfoList;

    public LiveChannelAdapter(List<LiveChannelInfo> liveChannelInfoList) {
        this.liveChannelInfoList = liveChannelInfoList;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_rcv_live_channel;
    }

    @Override
    protected LiveChannelViewHolder createHolder(View view) {
        return new LiveChannelViewHolder(view);
    }

    @Override
    protected void bindHolder(final LiveChannelViewHolder holder, final int position) {
        LiveChannelInfo liveChannelInfo = liveChannelInfoList.get(position);
        holder.textView.setText(liveChannelInfo.getTitle());
        if(liveChannelInfo.getPrice() > 0){
            holder.tvPrice.setText("$"+liveChannelInfo.getPrice());
            holder.tvPrice.setVisibility(View.VISIBLE);
        }
        ImageMaster.load(liveChannelInfo.getPreview(), holder.imageView, R.drawable.img_hold,
                R.drawable.img_hold);
    }

    @Override
    public int getItemCounts() {
        return liveChannelInfoList.size();
    }
}
