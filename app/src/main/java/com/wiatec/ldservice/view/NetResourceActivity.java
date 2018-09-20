package com.wiatec.ldservice.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.animator.Zoom;
import com.px.common.constant.CommonApplication;
import com.px.common.utils.AppUtil;
import com.px.common.utils.EmojiToast;
import com.px.common.utils.SysUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.adapter.ResourcesAppAdapter;
import com.wiatec.ldservice.databinding.ActivityNetResourceBinding;
import com.wiatec.ldservice.databinding.ActivityResourcesBinding;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.manager.AppDownloadManager;
import com.wiatec.ldservice.model.UserContentResolver;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.pojo.UpgradeInfo;
import com.wiatec.ldservice.presenter.ResourcesPresenter;
import com.wiatec.ldservice.task.TokenTask;
import com.wiatec.ldservice.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;


@Route(path = Constant.route.net_resources)
public class NetResourceActivity extends AppCompatActivity {

    private ActivityNetResourceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_net_resource);
        ResourceAppInfo resourceAppInfo = new ResourceAppInfo();
        resourceAppInfo.setPackageName(Constant.package_name.net_resources);
        resourceAppInfo.setIcon("https://s1.ax1x.com/2018/01/27/pOeBEF.png");
        resourceAppInfo.setLabel("NetResource");
        ResourceAppInfo resourceAppInfo1 = new ResourceAppInfo();
        resourceAppInfo1.setPackageName("LP");
        resourceAppInfo1.setIcon("https://s1.ax1x.com/2018/01/27/pOeBEF.png");
        resourceAppInfo1.setLabel("Live TV");
        ResourceAppInfo resourceAppInfo2 = new ResourceAppInfo();
        resourceAppInfo2.setPackageName("com.remote.server");
        resourceAppInfo2.setIcon("https://s1.ax1x.com/2018/05/23/CRBpNT.png");
        resourceAppInfo2.setLabel("B·Keymo AIR");
        List<ResourceAppInfo> list = new ArrayList<>();
        list.add(resourceAppInfo2);
        list.add(resourceAppInfo);
        list.add(resourceAppInfo1);
        showResourcesApp(list);
    }

    private void showResourcesApp(final List<ResourceAppInfo> list){
        ResourcesAppAdapter resourcesAppAdapter = new ResourcesAppAdapter(list);
        binding.rcvResourcesApp.setAdapter(resourcesAppAdapter);
        binding.rcvResourcesApp.setLayoutManager(new GridLayoutManager(this, 3));
        resourcesAppAdapter.setZoom(false);
        resourcesAppAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ResourceAppInfo resourceAppInfo = list.get(position);
                switch (position){
                    case 0:
                        AppUtil.launchApp(NetResourceActivity.this, resourceAppInfo.getPackageName());
                        break;
                    case 1:
                        AppUtil.launchApp(NetResourceActivity.this, resourceAppInfo.getPackageName());
                        break;
                    case 2:
                        if(!SystemUtils.is20250920Rom()){
                            showUpgradeRomDialog();
                            return;
                        }
                        Application.getExecutorService().execute(new TokenTask());
                        Intent intent = new Intent(NetResourceActivity.this, ChannelActivity.class);
                        intent.putExtra(Constant.key.channel_type, resourceAppInfo.getPackageName());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
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


    private void showUpgradeRomDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(CommonApplication.context)
                .title("Notice")
                .content("Please update the ROM to the latest version")
                .positiveText("Confirm")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AppUtil.launchApp(NetResourceActivity.this, "com.droidlogic.otaupgrade");
                    }
                })
                .build();
        Window window = dialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }
}