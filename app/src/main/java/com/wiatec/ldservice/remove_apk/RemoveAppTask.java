package com.wiatec.ldservice.remove_apk;


import com.px.common.http.HttpMaster;
import com.px.common.http.listener.DownloadListener;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.pojo.DownloadInfo;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.AppUtil;
import com.px.common.utils.CommonApplication;
import com.px.common.utils.FileUtil;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.remote_apk.RemoteAppInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by patrick on 21/07/2017.
 * create time : 11:08 AM
 */

public class RemoveAppTask implements Runnable {

    private static volatile RemoveAppTask instance;

    private RemoveAppTask(){
    }

    public static RemoveAppTask getInstance(){
        if(instance == null){
            synchronized (RemoveAppTask.class){
                if(instance ==null){
                    instance = new RemoveAppTask();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        load();
    }

    private void load() {
        if(!NetUtil.isConnected()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Logger.e(e.getMessage());
            }
            load();
        }else {
            HttpMaster.get(Constant.url.remove_apk)
                    .enqueue(new ResultListener<RemoveAppInfo>(RemoveAppInfo.class) {
                        @Override
                        public void onFailure(String e) {
                            Logger.e(e);
                        }

                        @Override
                        public void onSuccess(ResultInfo<RemoveAppInfo> resultInfo) throws Exception {
                            if(resultInfo.getCode() == 200){
                                List<RemoveAppInfo> list = resultInfo.getDataList();
                                if (list == null || list.size() <= 0) return;
                                Logger.d(list.toString());
                                for (RemoveAppInfo apkInfo : list) {
                                    if (AppUtil.isInstalled(apkInfo.getPackageName())){
                                        if(AppUtil.getVersionCode(apkInfo.getPackageName()) < apkInfo.getCode()){
                                            execCommand("pm", "uninstall", apkInfo.getPackageName());
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private static String execCommand(String ...command)  {
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        String result;

        try {
            process = new ProcessBuilder().command(command).start();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int read = -1;
            errIs=process.getErrorStream();
            while((read=errIs.read()) != -1){
                byteArrayOutputStream.write(read);
            }
            inIs=process.getInputStream();
            while((read=inIs.read())!=-1){
                byteArrayOutputStream.write(read);
            }
            result=new String(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            Logger.e(e.getMessage());
            result = e.getMessage();
        }finally {
            try {
                if(inIs != null) inIs.close();
                if(errIs != null) errIs.close();
                if(process != null) process.destroy();
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
        return result;
    }
}
