package com.wiatec.ldservice.presenter;


import com.px.common.http.pojo.ResultInfo;
import com.wiatec.ldservice.model.ChannelProvider;
import com.wiatec.ldservice.model.ResultListLoader;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.model.ResultLoader;
import com.wiatec.ldservice.pay.PayProvider;
import com.wiatec.ldservice.pay.PayResultInfo;
import com.wiatec.ldservice.pojo.ChannelInfo;
import com.wiatec.ldservice.pojo.LiveChannelInfo;
import com.wiatec.ldservice.view.IChannel;

import java.util.List;

/**
 * channel presenter
 */

public class ChannelPresenter extends BasePresenter {

    private IChannel channel;
    ChannelProvider channelProvider;
    PayProvider payProvider;

    public ChannelPresenter(IChannel channel) {
        this.channel = channel;
        channelProvider = new ChannelProvider();
        payProvider = new PayProvider();
    }

    //调用model - channelLoadService 获取需要的Image文件
    public void loadChannel(String type){
        if(channelProvider != null){
            channelProvider.load(type, new ResultListWithParamLoader.OnLoadListener<ChannelInfo>() {
                @Override
                public void onSuccess(boolean execute, List<ChannelInfo> list) {
                    channel.loadChannel(execute, list);
                }

                @Override
                public void onFailure(String e) {

                }

            });
        }
    }

    public void loadLiveChannel(){
        if(channelProvider != null){
            channelProvider.loadLiveChannel(new ResultListLoader.OnLoadListener<LiveChannelInfo>() {

                @Override
                public void onSuccess(boolean execute, List<LiveChannelInfo> list) {

                    channel.loadLiveChannel(execute, list);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

    public void loadFavorite(){
        if(channelProvider != null){
            channelProvider.loadFavorite(new ResultListLoader.OnLoadListener<ChannelInfo>() {

                @Override
                public void onSuccess(boolean execute, List<ChannelInfo> list) {
                    channel.loadFavorite(execute, list);
                }

                @Override
                public void onFailure(String e) {

                }

            });
        }
    }

    public void loadHistory(){
        if(channelProvider != null){
            channelProvider.loadHistory(new ResultListLoader.OnLoadListener<ChannelInfo>() {

                @Override
                public void onSuccess(boolean execute, List<ChannelInfo> list) {
                    channel.loadHistory(execute, list);
                }

                @Override
                public void onFailure(String e) {

                }

            });
        }
    }

    public void loadSearch(String key){
        if(channelProvider != null){
            channelProvider.loadSearch(key, new ResultListLoader.OnLoadListener<ChannelInfo>() {

                @Override
                public void onSuccess(boolean execute, List<ChannelInfo> list) {
                    channel.loadSearch(execute, list);
                }

                @Override
                public void onFailure(String e) {

                }

            });
        }
    }

    public void verifyPay(String payerName, int publisherId, String paymentId){
        if(payProvider != null){
            payProvider.payVerify(payerName, publisherId, paymentId, new ResultLoader.OnLoadListener<PayResultInfo>() {

                @Override
                public void onSuccess(boolean execute, PayResultInfo payResultInfo) {
                    channel.onPayVerify(execute, payResultInfo);
                }

                @Override
                public void onFailure(String e) {

                }

            });
        }
    }
}
