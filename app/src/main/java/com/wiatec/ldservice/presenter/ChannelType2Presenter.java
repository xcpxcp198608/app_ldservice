package com.wiatec.ldservice.presenter;


import com.wiatec.ldservice.model.ChannelType2Provider;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.pojo.ChannelType2Info;
import com.wiatec.ldservice.view.ChannelType2;

import java.util.List;

/**
 * Created by patrick on 14/08/2017.
 * create time : 2:10 PM
 */

public class ChannelType2Presenter extends BasePresenter<ChannelType2> {

    private ChannelType2 channelType2;
    private ChannelType2Provider channelType2Provider;

    public ChannelType2Presenter(ChannelType2 channelType2) {
        this.channelType2 = channelType2;
        channelType2Provider = new ChannelType2Provider();
    }

    public void loadChannelType2(String type){
        channelType2Provider.load(type , new ResultListWithParamLoader.OnLoadListener<ChannelType2Info>() {

            @Override
            public void onSuccess(boolean execute, List<ChannelType2Info> list) {
                channelType2.loadChannelType2(execute, list);

            }

            @Override
            public void onFailure(String e) {

            }

        });
    }

}
