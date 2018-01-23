package com.wiatec.ldservice.view;

import com.wiatec.ldservice.pojo.ResourceAppInfo;

/**
 * Created by patrick on 18/01/2018.
 * create time : 9:57 AM
 */

public interface IMain extends IAdImage {
    void onLoadResourceAppByPackageName(boolean execute, ResourceAppInfo resourceAppInfo);
}
