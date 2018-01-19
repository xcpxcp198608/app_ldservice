package com.wiatec.ldservice.remote_play;

import android.content.Context;
import android.content.Intent;

import com.px.common.utils.AppUtil;
import com.wiatec.ldservice.instance.Constant;

/**
 * Created by patrick on 27/09/2017.
 * create time : 11:23 AM
 */

public class RemotePlayManager {

    public static void launchPlay(Context context, String url, int duration){
        if(AppUtil.isInstalled(Constant.package_name.live_play)) {
            Intent intent = new Intent();
            intent.setClassName(Constant.package_name.live_play, Constant.activity.automatic_play);
            intent.setAction(Constant.activity.automatic_play);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.key.key_duration, duration);
            intent.putExtra(Constant.key.key_url, url);
            context.startActivity(intent);
        }
    }
}
