package com.wiatec.ldservice.view;

import com.wiatec.ldservice.pojo.ChannelTypeInfo;
import com.wiatec.ldservice.pojo.LDFamInfo;

import java.util.List;

/**
 * Created by patrick on 18/01/2018.
 * create time : 9:23 AM
 */

public interface IBVision extends IAdImage {

    void onLoadLDFam(boolean execute, List<LDFamInfo> ldFamInfoList);
    void onLoadChannelType(boolean execute, List<ChannelTypeInfo> channelTypeInfoList);
}