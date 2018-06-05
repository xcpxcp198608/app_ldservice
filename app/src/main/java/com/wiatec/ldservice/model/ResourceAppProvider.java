package com.wiatec.ldservice.model;

import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ResourceAppInfo;
import com.wiatec.ldservice.sql.ResourceAppDao;

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
                        if(onLoadListener != null) {
                            onLoadListener.onSuccess(false, null);
                        }
                    }

                    @Override
                    public void onSuccess(ResultInfo<ResourceAppInfo> resultInfo) throws Exception {
                        if(resultInfo.getCode() !=200){
                            if(onLoadListener != null) {
                                onLoadListener.onSuccess(false, null);
                            }
                            return;
                        }
                        final List<ResourceAppInfo> list = resultInfo.getDataList();
                        if(list == null || list.size() <=0){
                            if(onLoadListener != null) {
                                onLoadListener.onSuccess(false, null);
                            }
                            return;
                        }
                        if(onLoadListener != null) {
                            onLoadListener.onSuccess(true, list);
                        }
                        Application.getExecutorService().execute(new Runnable() {
                            @Override
                            public void run() {
                                ResourceAppDao resourceAppDao = ResourceAppDao.getInstance();
                                resourceAppDao.deleteAll();
                                resourceAppDao.batchInsert(list);
                            }
                        });
                    }
                });

    }

    public void loadFromLocal(OnLoadListener<ResourceAppInfo> onLoadListener){
        ResourceAppDao resourceAppDao = ResourceAppDao.getInstance();
        List<ResourceAppInfo> list = resourceAppDao.queryAll();
        if(list == null || list.size() <=0){
            if(onLoadListener != null) {
                onLoadListener.onSuccess(false, null);
            }
            return;
        }
        if(onLoadListener != null) {
            onLoadListener.onSuccess(true, list);
        }
    }

    public void loadByPackageName(String param, final ResultLoader.OnLoadListener<ResourceAppInfo> onLoadListener){
        HttpMaster.get(Constant.url.resource_apk_by_package_name)
                .param("packageName", param)
                .enqueue(new ResultListener<ResourceAppInfo>(ResourceAppInfo.class) {
                    @Override
                    public void onFailure(String e) {
                        Logger.e(e);
                        onLoadListener.onSuccess(false, null);
                    }

                    @Override
                    public void onSuccess(ResultInfo<ResourceAppInfo> resultInfo) throws Exception {
                        if(resultInfo.getCode() !=200){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        ResourceAppInfo resourceAppInfo = resultInfo.getData();
                        if(resourceAppInfo == null){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, resourceAppInfo);
                    }
                });
    }
}
