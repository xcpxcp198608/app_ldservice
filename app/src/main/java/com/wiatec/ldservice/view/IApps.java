package com.wiatec.ldservice.view;

import com.wiatec.ldservice.pojo.AppInfo;

import java.util.List;

/**
 * Created by patrick on 2018/5/21.
 * create time : 3:26 PM
 */

public interface IApps {

    void onLoadApps(boolean execute, List<AppInfo> appInfoList);
}
