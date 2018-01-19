package com.wiatec.ldservice.view;


import com.px.common.http.pojo.ResultInfo;
import com.wiatec.ldservice.pay.PayResultInfo;
import com.wiatec.ldservice.pojo.ChannelInfo;
import com.wiatec.ldservice.pojo.LiveChannelInfo;

import java.util.List;

/**
 * channel
 */

public interface IChannel extends IAdImage {

    void onLoadChannel(boolean execute, List<ChannelInfo> channelInfoList);
    void onLoadLiveChannel(boolean execute, List<LiveChannelInfo> liveChannelInfoList);
    void onLoadFavorite(boolean execute, List<ChannelInfo> channelInfoList);
    void onLoadHistory(boolean execute, List<ChannelInfo> channelInfoList);
    void onLoadSearch(boolean execute, List<ChannelInfo> channelInfoList);
    void onPayVerify(boolean execute, PayResultInfo payResultInfo);
}
