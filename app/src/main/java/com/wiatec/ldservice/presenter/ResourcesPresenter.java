package com.wiatec.ldservice.presenter;

import com.wiatec.ldservice.model.ResourceAppProvider;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.view.IResources;

import java.util.List;

/**
 * Created by patrick on 15/01/2018.
 * create time : 6:09 PM
 */

public class ResourcesPresenter extends BasePresenter<IResources> {

    private IResources iResources;
    private ResourceAppProvider resourceApp;

    public ResourcesPresenter(IResources iResources) {
        this.iResources = iResources;
        resourceApp = new ResourceAppProvider();
    }

    public void loadResourcesApp(int userLevel){
        resourceApp.load(userLevel+"", new ResultListWithParamLoader.OnLoadListener<ResourceAppInfo>() {
            @Override
            public void onSuccess(boolean execute, List<ResourceAppInfo> list) {
                iResources.onResourcesAppLoaded(execute, list);
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }
}
