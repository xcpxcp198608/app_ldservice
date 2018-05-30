package com.wiatec.ldservice.instance;

import com.alibaba.android.arouter.launcher.ARouter;
import com.px.common.constant.CommonApplication;
import com.wiatec.ldservice.pojo.ChannelInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by patrick on 20/07/2017.
 * create time : 6:09 PM
 */

public class Application extends CommonApplication {

    private static ExecutorService executorService;
    public static String AD_IMAGE_PATH;
    public static String APK_PATH;

    private static List<ChannelInfo> channelInfoList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        executorService = Executors.newCachedThreadPool();
        AD_IMAGE_PATH = getExternalFilesDir("ad_images").getAbsolutePath();
        APK_PATH = getExternalFilesDir("apk").getAbsolutePath();
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static List<ChannelInfo> getChannelInfoList() {
        return channelInfoList;
    }

    public static void setChannelInfoList(List<ChannelInfo> channelInfoList) {
        Application.channelInfoList = channelInfoList;
    }
}
