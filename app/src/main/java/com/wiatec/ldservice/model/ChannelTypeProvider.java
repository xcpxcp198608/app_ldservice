package com.wiatec.ldservice.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelTypeInfo;

import java.io.IOException;
import java.util.List;


/**
 * channel type data model
 */

public class ChannelTypeProvider implements ResultListWithParamLoader<ChannelTypeInfo>{

    @Override
    public void load(String type, final ResultListWithParamLoader.OnLoadListener<ChannelTypeInfo> onLoadListener) {
        HttpMaster.get(Constant.url.channel_type + type + Constant.url.token)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        ResultInfo<ChannelTypeInfo> resultInfo = new Gson().fromJson(s,
                                new TypeToken<ResultInfo<ChannelTypeInfo>>(){}.getType());
                        if(resultInfo == null){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        List<ChannelTypeInfo> channelTypeInfoList = resultInfo.getDataList();
                        if(channelTypeInfoList == null || channelTypeInfoList.size() <= 0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, channelTypeInfoList);
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.e(e);
                        onLoadListener.onSuccess(false, null);
                    }
                });
    }
}
