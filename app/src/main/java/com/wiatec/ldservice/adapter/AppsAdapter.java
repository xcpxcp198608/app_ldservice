package com.wiatec.ldservice.adapter;

import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.image.ImageMaster;
import com.px.common.utils.AppUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.pojo.AppInfo;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

import java.util.List;

/**
 * Created by patrick on 14/01/2018.
 * create time : 10:10 PM
 */

public class AppsAdapter extends BaseRecycleAdapter<AppsViewHolder> {

    private List<AppInfo> list;
    private OnUninstallListener onUninstallListener;

    public AppsAdapter(List<AppInfo> list) {
        this.list = list;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_rcv_apps;
    }

    @Override
    protected AppsViewHolder createHolder(View view) {
        return new AppsViewHolder(view);
    }

    @Override
    protected void bindHolder(AppsViewHolder holder, final int position) {
        final AppInfo appInfo = list.get(position);
        holder.ivIcon.setImageDrawable(AppUtil.getIcon(appInfo.getPackageName()));
        holder.tvLabel.setText(appInfo.getLabel());
        holder.btUninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onUninstallListener != null) {
                    onUninstallListener.onUninstall(list, appInfo, position);
                }
            }
        });
    }

    @Override
    public int getItemCounts() {
        return list.size();
    }

    public interface OnUninstallListener{
        void onUninstall(List<AppInfo> appInfoList, AppInfo appInfo, int position);
    }

    public void setOnUninstallListener(OnUninstallListener onUninstallListener) {
        this.onUninstallListener = onUninstallListener;
    }
}
