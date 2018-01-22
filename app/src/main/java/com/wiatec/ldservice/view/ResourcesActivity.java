package com.wiatec.ldservice.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.animator.Zoom;
import com.px.common.utils.AppUtil;
import com.px.common.utils.EmojiToast;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.adapter.ResourcesAppAdapter;
import com.wiatec.ldservice.databinding.ActivityResourcesBinding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.manager.AppDownloadManager;
import com.wiatec.ldservice.model.UserContentResolver;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.presenter.ResourcesPresenter;

import java.util.List;

@Route(path = Constant.route.resources)
public class ResourcesActivity extends BaseActivity<ResourcesPresenter> implements IResources {

    private ActivityResourcesBinding binding;
    private int userLevel = 1;

    @Override
    protected ResourcesPresenter createPresenter() {
        return new ResourcesPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resources);
        try {
            userLevel = Integer.parseInt(UserContentResolver.get(Constant.key.SP_USER_LEVEL));
        }catch (Exception e){
            userLevel = 1;
        }
        presenter.loadResourcesApp(userLevel);
    }

    @Override
    public void onResourcesAppLoaded(boolean execute, List<ResourceAppInfo> list) {
        binding.pbLoading.setVisibility(View.GONE);
        if (execute) {
            showResourcesApp(list);
        }else{
            showLoadError();
        }
    }

    private void showResourcesApp(final List<ResourceAppInfo> list){
        binding.llError.setVisibility(View.GONE);
        ResourcesAppAdapter resourcesAppAdapter = new ResourcesAppAdapter(list);
        binding.rcvResourcesApp.setAdapter(resourcesAppAdapter);
        binding.rcvResourcesApp.setLayoutManager(new GridLayoutManager(this, 5));
        resourcesAppAdapter.setZoom(false);
        resourcesAppAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ResourceAppInfo resourceAppInfo = list.get(position);
                if(!AppUtil.isInstalled(resourceAppInfo.getPackageName())) {
                    installOrUpdateApp(resourceAppInfo, "Press confirm to install");
                    return;
                }
                if(AppUtil.isLastVersion(resourceAppInfo.getPackageName(), resourceAppInfo.getVersionCode())){
                    installOrUpdateApp(resourceAppInfo, " has a new version, press confirm to update");
                    return;
                }
                AppUtil.launchApp(ResourcesActivity.this, resourceAppInfo.getPackageName());
            }
        });
        resourcesAppAdapter.setOnItemFocusListener(new BaseRecycleAdapter.OnItemFocusListener() {
            @Override
            public void onFocus(View view, int position, boolean hasFocus) {
                if(hasFocus){
                    Zoom.zoomIn10to12(view);
                    view.setSelected(true);
                }else{
                    Zoom.zoomIn12to10(view);
                    view.setSelected(false);
                }
            }
        });
    }

    private void installOrUpdateApp(ResourceAppInfo resourceAppInfo, String message){
        AppDownloadManager appDownloadManager = new AppDownloadManager();
        appDownloadManager.showUpgradeDialog(resourceAppInfo, message);
    }

    private void showLoadError(){
        binding.llError.setVisibility(View.VISIBLE);
        binding.btReload.requestFocus();
        binding.btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadResourcesApp(userLevel);
            }
        });
    }

}
