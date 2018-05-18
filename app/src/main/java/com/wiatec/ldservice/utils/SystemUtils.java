package com.wiatec.ldservice.utils;

import android.os.Build;

import com.px.common.utils.Logger;

/**
 * Created by patrick on 2018/5/18.
 * create time : 3:48 PM
 */

public class SystemUtils {

    public static boolean isLatestRom(){
        long buildTime = Build.TIME;
        Logger.d("buildTime: " + buildTime);
        long targetTime = 1747238400000L;//2025/5/15 00:00:00
        return buildTime > targetTime;
    }
}
