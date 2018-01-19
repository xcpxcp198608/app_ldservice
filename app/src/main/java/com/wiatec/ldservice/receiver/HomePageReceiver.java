package com.wiatec.ldservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.remote_apk.DownloadRemoteApk;

/**
 * Created by patrick on 11/01/2018.
 * create time : 2:52 PM
 */

public class HomePageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.wiatec.ldservice.receiver.HomePageReceiver".equals(intent.getAction())){
            Application.getExecutorService().execute(DownloadRemoteApk.getInstance());
//            Application.getExecutorService().execute(CheckUpgrade.getInstance());
        }
    }
}
