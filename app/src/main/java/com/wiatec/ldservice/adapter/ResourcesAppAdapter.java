package com.wiatec.ldservice.adapter;

import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.image.ImageMaster;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

import java.util.List;

/**
 * Created by patrick on 14/01/2018.
 * create time : 10:10 PM
 */

public class ResourcesAppAdapter extends BaseRecycleAdapter<ResourcesAppViewHolder> {

    private List<ResourceAppInfo> list;

    public ResourcesAppAdapter(List<ResourceAppInfo> list) {
        this.list = list;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_resources_app;
    }

    @Override
    protected ResourcesAppViewHolder createHolder(View view) {
        return new ResourcesAppViewHolder(view);
    }

    @Override
    protected void bindHolder(ResourcesAppViewHolder holder, int position) {
        ResourceAppInfo resourceAppInfo = list.get(position);
        ImageMaster.load(resourceAppInfo.getIcon(), holder.ivIcon, R.mipmap.ic_launcher);
        holder.tvLable.setText(resourceAppInfo.getLabel());
    }

    @Override
    public int getItemCounts() {
        return list.size();
    }
}
