package com.wiatec.ldservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.remote_play.RemotePlayTask;

/**
 * ld service
 * load location info, weather info
 * download images , recommended apk
 */

public class LDService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(getClass().getSimpleName() + " start...");
        executeNetWorkTask();
        executeRecycleTask();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        startService(new Intent(this, LDService.class));
        super.onDestroy();
    }

    private void executeNetWorkTask(){
        if(!NetUtil.isConnected()){
            executeNetWorkTask();
            return;
        }
//        Application.getExecutorService().execute(new LoadLocation());
//        Application.getExecutorService().execute(new DownloadImage());
    }

    private void executeRecycleTask(){
//        Application.getExecutorService().execute(new RemotePlayTask());
    }
}
