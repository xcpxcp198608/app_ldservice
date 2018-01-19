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
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.presenter.ResourcesPresenter;

import java.util.List;

@Route(path = Constant.route.resources)
public class ResourcesActivity extends BaseActivity<ResourcesPresenter> implements IResources {

    public static final String INTENT_KEY = "ResourcesAppType";
    public static final int TYPE_RESOURCES = 0;
    public static final int TYPE_VIP = 1;
    private ActivityResourcesBinding binding;
    private int resourcesAppType;

    @Override
    protected ResourcesPresenter createPresenter() {
        return new ResourcesPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resources);
        resourcesAppType = getIntent().getIntExtra(INTENT_KEY, TYPE_RESOURCES);
        presenter.loadResourcesApp(resourcesAppType);
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
        ResourcesAppAdapter resourcesAppAdapter = new ResourcesAppAdapter(list);
        binding.rcvResourcesApp.setAdapter(resourcesAppAdapter);
        binding.rcvResourcesApp.setLayoutManager(new GridLayoutManager(this, 5));
        resourcesAppAdapter.setZoom(false);
        resourcesAppAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ResourceAppInfo resourceAppInfo = list.get(position);
                if(!AppUtil.isInstalled(resourceAppInfo.getPackageName())) {
                    EmojiToast.showLong("the app no installed", EmojiToast.EMOJI_SMILE);
                    AppUtil.launchApp(ResourcesActivity.this, Constant.package_name.market);
                    return;
                }
                if(AppUtil.isLastVersion(resourceAppInfo.getPackageName(), resourceAppInfo.getVersionCode())){
                    EmojiToast.showLong("the app have new version", EmojiToast.EMOJI_SMILE);
                    AppUtil.launchApp(ResourcesActivity.this, Constant.package_name.market);
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

    private void showLoadError(){
        binding.llError.setVisibility(View.VISIBLE);
        binding.btReload.requestFocus();
        binding.btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadResourcesApp(resourcesAppType);
            }
        });
    }

}
