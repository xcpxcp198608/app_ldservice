package com.wiatec.ldservice.model;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.px.common.constant.CommonApplication;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.AppInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by patrick on 2018/5/21.
 * create time : 3:15 PM
 */

public class AppsProvider implements ResultListLoader<AppInfo>{

    @Override
    public void load(OnLoadListener<AppInfo> onLoadListener) {
        List<AppInfo> list = null;
        PackageManager packageManager = CommonApplication.context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = packageManager.queryIntentActivities(intent ,0);
        Iterator<ResolveInfo> iterator = null;
        if(localList != null) {
            iterator = localList.iterator();
            list = new ArrayList<>();
        }
//        installedAppDao.deleteAll();
        while (true) {
            if(!iterator.hasNext()){
                break;
            }
            ResolveInfo resolveInfo = iterator.next();
            String packageName = resolveInfo.activityInfo.packageName;
            if(!"com.wiatec.btv_launcher".equals(packageName) &&
                    !"com.android.tv.settings".equals(packageName)&&
                    !"com.euroandroid.xbox".equals(packageName)&&
                    !"com.explusalpha.Snes9xPlus".equals(packageName)&&
                    !"com.koushikdutta.superuser".equals(packageName)&&
                    !"com.droidlogic.appinstall".equals(packageName)&&
                    !Constant.package_name.market.equals(packageName)&&
                    !Constant.package_name.dianshijia3.equals(packageName)&&
                    !Constant.package_name.tvplus.equals(packageName)&&
                    !Constant.package_name.bplay.equals(packageName)&&
                    !Constant.package_name.live_net.equals(packageName)&&
                    !Constant.package_name.show_box.equals(packageName)&&
                    !Constant.package_name.tv_house.equals(packageName)&&
                    !Constant.package_name.mx_player.equals(packageName)&&
                    !Constant.package_name.terrarium_tv.equals(packageName)&&
                    !Constant.package_name.popcom.equals(packageName)&&
                    !Constant.package_name.lddream.equals(packageName)&&
                    !Constant.package_name.ldservice.equals(packageName)&&
                    !Constant.package_name.live_net_new.equals(packageName)&&
                    !Constant.package_name.bluetooth_remote.equals(packageName)&&
                    !Constant.package_name.factory_test.equals(packageName)&&
                    !Constant.package_name.remote_control.equals(packageName)&&
                    !Constant.package_name.read_log.equals(packageName)&&
                    !Constant.package_name.input_method.equals(packageName)&&
                    !Constant.package_name.web_view.equals(packageName)&&
                    !Constant.package_name.clock.equals(packageName)&&
                    !Constant.package_name.live_play.equals(packageName)&&
                    !Constant.package_name.mobdro.equals(packageName)&&
                    !Constant.package_name.morpheustv.equals(packageName)&&
                    !Constant.package_name.nitrotv.equals(packageName)&&
                    !Constant.package_name.btv.equals(packageName)){

                AppInfo appInfo = new AppInfo();
                appInfo.setLabel(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setPackageName(packageName);
                //通过包名过滤不需要显示的app
                list.add(appInfo);
            }
        }
        if(list != null && list.size() > 0){
            onLoadListener.onSuccess(true, list);
        }else{
            onLoadListener.onSuccess(false, null);
        }
    }
}
