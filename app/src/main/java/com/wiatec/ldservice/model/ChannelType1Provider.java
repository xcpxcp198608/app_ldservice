package com.wiatec.ldservice.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ListListener;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelInfo;
import com.wiatec.ldservice.pojo.ChannelType1Info;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 14/08/2017.
 * create time : 2:07 PM
 */

public class ChannelType1Provider implements ResultListWithParamLoader<ChannelType1Info> {

    @Override
    public void load(String param, final OnLoadListener<ChannelType1Info> onLoadListener) {
        HttpMaster.get(Constant.url.channel_type1 + param + Constant.url.token)
                .enqueue(new ResultListener<ChannelType1Info>(ChannelType1Info.class) {
                    @Override
                    public void onSuccess(ResultInfo<ChannelType1Info> resultInfo) throws Exception {
                        if(resultInfo.getCode() != 200){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        List<ChannelType1Info> list = resultInfo.getDataList();
                        if(list != null && list.size() > 0){
                            onLoadListener.onSuccess(true, list);
                        }else{
                            onLoadListener.onSuccess(false, null);
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onSuccess(false, null);
                        Logger.e(e);

                    }
                });
    }
}
