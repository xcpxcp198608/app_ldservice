package com.wiatec.ldservice.presenter;

import com.px.common.utils.Logger;
import com.wiatec.ldservice.model.AppsProvider;
import com.wiatec.ldservice.model.ResourceAppProvider;
import com.wiatec.ldservice.model.ResultListLoader;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.pojo.AppInfo;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.view.IApps;
import com.wiatec.ldservice.view.IResources;

import java.util.List;

/**
 * Created by patrick on 15/01/2018.
 * create time : 6:09 PM
 */

public class AppsPresenter extends BasePresenter<IResources> {

    private IApps iApps;
    private AppsProvider appsProvider;

    public AppsPresenter(IApps iApps) {
        this.iApps = iApps;
        appsProvider = new AppsProvider();
    }

    public void loadApps(){
        appsProvider.load(new ResultListLoader.OnLoadListener<AppInfo>() {
            @Override
            public void onSuccess(boolean execute, List<AppInfo> list) {
                iApps.onLoadApps(execute, list);
            }

            @Override
            public void onFailure(String e) {
                Logger.d(e);
            }
        });
    }
}
