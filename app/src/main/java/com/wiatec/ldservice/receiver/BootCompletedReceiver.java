package com.wiatec.ldservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wiatec.ldservice.service.AIDLService;
import com.wiatec.ldservice.service.LDService;

/**
 * Created by patrick on 20/07/2017.
 * create time : 5:51 PM
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

            Intent intent1 = new Intent();
            intent1.setAction("com.wiatec.ldservice.LD_SERVICE");
            intent1.setPackage("com.wiatec.ldservice");
            context.startService(intent1);

            Intent intent2 = new Intent();
            intent2.setAction("com.wiatec.ldservice.AIDL_SERVICE");
            intent2.setPackage("com.wiatec.ldservice");
            context.startService(intent2);
        }
    }
}
