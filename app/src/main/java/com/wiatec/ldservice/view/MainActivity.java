package com.wiatec.ldservice.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.px.common.image.ImageMaster;
import com.px.common.utils.AppUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.databinding.ActivityMainBinding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ImageInfo;
import com.wiatec.ldservice.presenter.MainPresenter;
import com.wiatec.ldservice.service.AIDLService;
import com.wiatec.ldservice.service.LDService;

@Route(path = Constant.route.main)
public class MainActivity extends BaseActivity<MainPresenter> implements IMain {

    private ActivityMainBinding binding;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnEvent(new OnEventListener());
        binding.ibtBvision.requestFocus();
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
                    showResourcesActivity(ResourcesActivity.TYPE_VIP);
                    break;
            }
        }
    }

    private void showAdVideoActivity(){
        ARouter.getInstance()
                .build(Constant.route.ad_video)
                .withString(AdVideoActivity.KEY_PACKAGE_NAME, Constant.package_name.net_resources)
                .navigation();
    }

    private void showBvisionActivity(){
        ARouter.getInstance()
                .build(Constant.route.bvision)
                .navigation();
    }

    private void showResourcesActivity(int resourcesAppType){
        ARouter.getInstance()
                .build(Constant.route.resources)
                .withInt(ResourcesActivity.INTENT_KEY, resourcesAppType)
                .navigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadAdImage();
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
