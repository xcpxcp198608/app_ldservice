package com.px.common.utils;

import android.app.Application;
import android.content.Context;

import com.px.common.crash.CrashHandler;

/**
 * common application
 */

public class CommonApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.init("----px----");
//        CrashHandler.getInstance().init();
    }

}
