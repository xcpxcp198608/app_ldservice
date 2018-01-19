package com.wiatec.ldservice.model;

import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

import java.util.List;

/**
 * Created by patrick on 15/01/2018.
 * create time : 6:15 PM
 */

public class ResourceAppProvider implements ResultListWithParamLoader<ResourceAppInfo> {
    
    @Override
    public void load(String param, final OnLoadListener<ResourceAppInfo> onLoadListener) {
        HttpMaster.get(Constant.url.resource_apk + param)
                .enqueue(new ResultListener<ResourceAppInfo>(ResourceAppInfo.class) {
                    @Override
                    public void onFailure(String e) {
                        Logger.e(e);
                    }

                    @Override
                    public void onSuccess(ResultInfo<ResourceAppInfo> resultInfo) throws Exception {
                        if(resultInfo.getCode() !=200){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        List<ResourceAppInfo> list = resultInfo.getDataList();
                        if(list == null || list.size() <=0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, list);
                    }
                });

    }
}
