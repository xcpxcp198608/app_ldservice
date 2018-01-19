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

    void loadChannel(boolean execute, List<ChannelInfo> channelInfoList);
    void loadLiveChannel(boolean execute, List<LiveChannelInfo> liveChannelInfoList);
    void loadFavorite(boolean execute, List<ChannelInfo> channelInfoList);
    void loadHistory(boolean execute, List<ChannelInfo> channelInfoList);
    void loadSearch(boolean execute, List<ChannelInfo> channelInfoList);
    void onPayVerify(boolean execute, PayResultInfo payResultInfo);
}
