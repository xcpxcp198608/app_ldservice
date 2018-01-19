package com.wiatec.ldservice.presenter;


import com.wiatec.ldservice.model.ChannelType1Provider;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.pojo.ChannelType1Info;
import com.wiatec.ldservice.view.ChannelType1;

import java.util.List;

/**
 * Created by patrick on 14/08/2017.
 * create time : 2:10 PM
 */

public class ChannelType1Presenter extends BasePresenter<ChannelType1> {

    private ChannelType1 channelType1;
    private ChannelType1Provider channelType1Provider;

    public ChannelType1Presenter(ChannelType1 channelType1) {
        this.channelType1 = channelType1;
        channelType1Provider = new ChannelType1Provider();
    }

    public void loadChannelType1(String type){
        channelType1Provider.load(type , new ResultListWithParamLoader.OnLoadListener<ChannelType1Info>() {

            @Override
            public void onSuccess(boolean execute, List<ChannelType1Info> list) {
                channelType1.loadChannelType1(execute, list);

            }

            @Override
            public void onFailure(String e) {

            }
        });
    }

}
