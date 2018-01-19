package com.wiatec.ldservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.px.common.utils.Logger;


/**
 * Created by patrick on 21/07/2017.
 * create time : 2:09 PM
 */

public class AIDLService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(getClass().getSimpleName() + " start...");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        startService(new Intent(this, AIDLService.class));
        super.onDestroy();
    }
}
