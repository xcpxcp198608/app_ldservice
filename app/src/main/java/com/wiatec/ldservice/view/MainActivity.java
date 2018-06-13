package com.wiatec.ldservice.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.px.common.constant.CommonApplication;
import com.px.common.image.ImageMaster;
import com.px.common.utils.AppUtil;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.databinding.ActivityMainBinding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.manager.AppDownloadManager;
import com.wiatec.ldservice.model.UserContentResolver;
import com.wiatec.ldservice.pojo.ImageInfo;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.presenter.MainPresenter;
import com.wiatec.ldservice.service.AIDLService;
import com.wiatec.ldservice.service.LDService;

@Route(path = Constant.route.main)
public class MainActivity extends BaseActivity<MainPresenter> implements IMain {

    private ActivityMainBinding binding;
    private int userLevel = 1;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnEvent(new OnEventListener());
        try{
            userLevel = Integer.parseInt(UserContentResolver.get(Constant.key.SP_USER_LEVEL));
        }catch(Exception e){
            userLevel = 1;
        }
        if(userLevel >= 3){
            binding.ibtNet.setVisibility(View.GONE);
            binding.llMain.setPadding(250, 40 , 250, 40);
        }
        binding.tvVersion.setText(AppUtil.getVersionName(this.getPackageName()));
    }

    public class OnEventListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.ibtNet:
                    showAdVideoActivity();
                    break;
                case R.id.ibtBvision:
                    showBvisionActivity();
                    break;
                case R.id.ibtVip:
                    showResourcesActivity();
                    break;
                case R.id.ibtApps:
                    showAppsActivity();
                    break;
            }
        }
    }

    private void showAdVideoActivity(){
        if(AppUtil.isInstalled(Constant.package_name.net_resources)) {
            ARouter.getInstance()
                    .build(Constant.route.ad_video)
                    .withString(AdVideoActivity.KEY_PACKAGE_NAME, Constant.package_name.net_resources)
                    .navigation();
        }else{
            presenter.loadNetResourceAppInfo(Constant.package_name.net_resources);
        }
    }

    private void showBvisionActivity(){
        ARouter.getInstance()
                .build(Constant.route.bvision)
                .navigation();
    }



    private void showAppsActivity(){
        ARouter.getInstance()
                .build(Constant.route.apps)
                .navigation();
    }

    private void showResourcesActivity(){
        if(userLevel < 3){
            boolean isExperience = Boolean.parseBoolean(UserContentResolver.get(Constant.key.SP_IS_EXPERIENCE));
            if(isExperience){
                showExperienceNotice();
            }else{
                showAdNotice();
            }
        }else {
            ARouter.getInstance()
                    .build(Constant.route.resources)
                    .navigation();
        }
    }

    private void showExperienceNotice(){
        MaterialDialog dialog = new MaterialDialog.Builder(CommonApplication.context)
                .title(getString(R.string.notice))
                .content(getString(R.string.experience_notice_message))
                .positiveText(getString(R.string.confirm))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ARouter.getInstance()
                                .build(Constant.route.resources)
                                .navigation();
                        dialog.dismiss();
                    }
                })
                .build();
        Window window = dialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }
        dialog.show();
    }

    private void showAdNotice(){
        MaterialDialog dialog = new MaterialDialog.Builder(CommonApplication.context)
                .title(getString(R.string.notice))
                .content(getString(R.string.ad_notice_message))
                .positiveText(getString(R.string.confirm))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        Window window = dialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.ibtBvision.requestFocus();
        presenter.loadAdImage();
    }

    @Override
    public void onLoadResourceAppByPackageName(boolean execute, ResourceAppInfo resourceAppInfo) {
        if(execute) {
            AppDownloadManager appDownloadManager = new AppDownloadManager();
            appDownloadManager.showUpgradeDialog(resourceAppInfo, ", Press confirm to install, " +
                    "After you have successfully install the Net Resource,  " +
                    "please make sure run ACCCESS3.0 from your applications to install the latest resource pack.");
        }
    }

    @Override
    public void onLoadImage(boolean execute, ImageInfo imageInfo) {
        if(execute){
            ImageMaster.load(imageInfo.getUrl(), binding.ivAd);
        }
    }

    private void runService() {
        Intent intent = new Intent(this, LDService.class);
        intent.setAction("com.wiatec.ldservice.LD_SERVICE");
        startService(intent);
        Intent intent1 = new Intent(this, AIDLService.class);
        intent1.setAction("com.wiatec.ldservice.AIDL_SERVICE");
        startService(intent1);
    }

}
