package com.wiatec.ldservice.view;

import com.px.common.http.pojo.ResultInfo;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

import java.util.List;


/**
 * Created by patrick on 15/01/2018.
 * create time : 6:10 PM
 */

public interface IResources {

    void onResourcesAppLoaded(boolean execute, List<ResourceAppInfo> list);
}
