package com.wiatec.ldservice.presenter;

import com.px.common.utils.Logger;
import com.wiatec.ldservice.model.ChannelTypeProvider;
import com.wiatec.ldservice.model.ResultListLoader;
import com.wiatec.ldservice.model.ResultListWithParamLoader;
import com.wiatec.ldservice.pojo.ChannelTypeInfo;
import com.wiatec.ldservice.pojo.LDFamInfo;
import com.wiatec.ldservice.provider.BVisionProvider;
import com.wiatec.ldservice.view.IBVision;

import java.util.List;

/**
 * Created by patrick on 18/01/2018.
 * create time : 9:25 AM
 */

public class BVisionPresenter extends BasePresenter<IBVision> {

    private IBVision ibVision;
    private BVisionProvider bVisionProvider;
    private ChannelTypeProvider channelTypeProvider;

    public BVisionPresenter(IBVision ibVision) {
        this.ibVision = ibVision;
        bVisionProvider = new BVisionProvider();
        channelTypeProvider = new ChannelTypeProvider();
    }

    public void loadChannelType(String type){
        channelTypeProvider.load(type, new ResultListWithParamLoader.OnLoadListener<ChannelTypeInfo>() {
            @Override
            public void onSuccess(boolean execute, List<ChannelTypeInfo> list) {
                ibVision.onLoadChannelType(execute, list);
            }

            @Override
            public void onFailure(String e) {
                Logger.e(e);
            }
        });
    }

    public void loadLDFam(){
        bVisionProvider.load(new ResultListLoader.OnLoadListener<LDFamInfo>() {
            @Override
            public void onSuccess(boolean execute, List<LDFamInfo> list) {
                ibVision.onLoadLDFam(execute, list);
            }

            @Override
            public void onFailure(String e) {
                Logger.e(e);
            }
        });
    }
}
