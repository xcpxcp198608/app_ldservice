package com.wiatec.ldservice.presenter;

import com.px.common.utils.Logger;
import com.wiatec.ldservice.model.ImageProvider;
import com.wiatec.ldservice.model.ResourceAppProvider;
import com.wiatec.ldservice.model.ResultLoader;
import com.wiatec.ldservice.pojo.ImageInfo;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.view.IMain;

/**
 * Created by patrick on 18/01/2018.
 * create time : 9:58 AM
 */

public class MainPresenter extends BasePresenter<IMain> {

    private IMain iMain;
    private ImageProvider imageProvider;
    private ResourceAppProvider resourceAppProvider;

    public MainPresenter(IMain iMain) {
        this.iMain = iMain;
        imageProvider = new ImageProvider();
        resourceAppProvider = new ResourceAppProvider();
    }

    public void loadAdImage(){
        imageProvider.load(new ResultLoader.OnLoadListener<ImageInfo>() {
            @Override
            public void onSuccess(boolean execute, ImageInfo imageInfo) {
                iMain.onLoadImage(execute, imageInfo);
            }

            @Override
            public void onFailure(String e) {
                Logger.e(e);
            }
        });
    }

    public void loadNetResourceAppInfo(String packageName){
        resourceAppProvider.loadByPackageName(packageName, new ResultLoader.OnLoadListener<ResourceAppInfo>() {
            @Override
            public void onSuccess(boolean execute, ResourceAppInfo resourceAppInfo) {
                iMain.onLoadResourceAppByPackageName(execute, resourceAppInfo);
            }

            @Override
            public void onFailure(String e) {
                Logger.e(e);
            }
        });
    }
}
