package com.wiatec.ldservice.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelInfo;
import com.wiatec.ldservice.pojo.LiveChannelInfo;
import com.wiatec.ldservice.sql.FavoriteChannelDao;
import com.wiatec.ldservice.sql.HistoryChannelDao;
import com.wiatec.ldservice.utils.SystemUtils;

import java.io.IOException;
import java.util.List;

/**
 * channel provider
 */

public class ChannelProvider {

    public void load(String type, final ResultListWithParamLoader.OnLoadListener<ChannelInfo> onLoadListener) {
        String url = Constant.url.channel;
        if("LP".equals(type)){
            url = Constant.url.lp_channel;
        }
        HttpMaster.get(url + type + Constant.url.token)
                .enqueue(new ResultListener<ChannelInfo>(ChannelInfo.class) {
                    @Override
                    public void onSuccess(ResultInfo<ChannelInfo> resultInfo) throws Exception {
                        if(resultInfo.getCode() != 200){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        List<ChannelInfo> list = resultInfo.getDataList();
                        if(list == null || list.size() <= 0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        for(int i = 0; i < list.size(); i ++){
                            ChannelInfo channelInfo = list.get(i);
                            if(channelInfo.isLockInBasic()){
                                if(!SystemUtils.isLatestRom()){
                                    list.remove(i);
                                }
                            }
                        }
                        onLoadListener.onSuccess(true, list);
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onSuccess(false, null);
                        Logger.e(e);
                    }
                });
    }

    public void loadLiveChannel(final ResultListLoader.OnLoadListener<LiveChannelInfo> onLoadListener){
        HttpMaster.get(Constant.url.blive_channels)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        List<LiveChannelInfo> liveChannelInfoList = new Gson().fromJson(s,
                                new TypeToken<List<LiveChannelInfo>>(){}.getType());
                        if(liveChannelInfoList == null || liveChannelInfoList.size() <= 0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, liveChannelInfoList);
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onSuccess(false, null);
                    }
                });
    }

    public void loadFavorite(ResultListLoader.OnLoadListener<ChannelInfo> onLoadListener) {
        FavoriteChannelDao favoriteChannelDao = FavoriteChannelDao.getInstance();
        List<ChannelInfo> channelInfoList = favoriteChannelDao.queryAll();
        if(channelInfoList == null || channelInfoList.size() <= 0){
            onLoadListener.onSuccess(false, null);
            return;
        }
        onLoadListener.onSuccess(true, channelInfoList);
    }

    public void loadHistory(ResultListLoader.OnLoadListener<ChannelInfo> onLoadListener) {
        try{
            HistoryChannelDao historyChannelDao = HistoryChannelDao.getInstance();
            historyChannelDao.delete();
            List<ChannelInfo> channelInfoList = historyChannelDao.queryAll();
            if(channelInfoList == null || channelInfoList.size() <= 0){
                onLoadListener.onSuccess(false, null);
                return;
            }
            onLoadListener.onSuccess(true, channelInfoList);
        }catch(Exception e){
            Logger.d(e.getMessage());
            onLoadListener.onSuccess(false, null);
        }
    }

    public void loadSearch(String key, final ResultListLoader.OnLoadListener<ChannelInfo> onLoadListener) {
        if(TextUtils.isEmpty(key)){
            onLoadListener.onSuccess(false, null);
            return;
        }
        HttpMaster.get(Constant.url.channel_search + key + Constant.url.token)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        ResultInfo<ChannelInfo> resultInfo = new Gson().fromJson(s,
                                new TypeToken<ResultInfo<ChannelInfo>>(){}.getType());
                        if(resultInfo == null){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        List<ChannelInfo> channelInfoList = resultInfo.getDataList();
                        if(channelInfoList == null || channelInfoList.size() <= 0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, channelInfoList);
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onSuccess(false, null);
                    }
                });
    }
}
