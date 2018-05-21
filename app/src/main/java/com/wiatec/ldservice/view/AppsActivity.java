package com.wiatec.ldservice.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.adapter.AppsAdapter;
import com.wiatec.ldservice.databinding.ActivityAppsBinding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.AppInfo;
import com.wiatec.ldservice.presenter.AppsPresenter;
import com.wiatec.ldservice.utils.SystemUtils;

import java.util.List;

@Route(path = Constant.route.apps)
public class AppsActivity extends BaseActivity<AppsPresenter> implements IApps, AppsAdapter.OnUninstallListener  {

    private ActivityAppsBinding binding;
    private AppsAdapter appsAdapter;

    @Override
    protected AppsPresenter createPresenter() {
        return new AppsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apps);
        presenter.loadApps();
    }

    @Override
    public void onLoadApps(boolean execute, List<AppInfo> appInfoList) {
        if(execute && appInfoList!= null && appInfoList.size() > 0){
            if(appsAdapter == null){
                appsAdapter = new AppsAdapter(appInfoList);
            }else{
                appsAdapter.notifyDataSetChanged();
            }
            binding.rcvApps.setAdapter(appsAdapter);
            binding.rcvApps.setLayoutManager(new LinearLayoutManager(this));
            appsAdapter.setOnUninstallListener(this);
        }
    }

    @Override
    public void onUninstall(List<AppInfo> appInfoList, AppInfo appInfo, int position) {
        binding.pbLoading.setVisibility(View.VISIBLE);
        SystemUtils.uninstall(appInfo.getPackageName());
        appInfoList.remove(position);
        appsAdapter.notifyItemRemoved(position);
        appsAdapter.notifyItemRangeChanged(position, appInfoList.size() - position);
        binding.pbLoading.setVisibility(View.GONE);
    }
}
