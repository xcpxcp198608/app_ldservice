package com.wiatec.ldservice.remote_apk;


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

public class RemoteAppTask implements Runnable {

    private static Map<String, RemoteAppInfo> apkInfoMap;
    private static Set<String> downloadingApkSet;

    private static volatile RemoteAppTask instance;

    private RemoteAppTask(){
        apkInfoMap = new ConcurrentHashMap<>();
        downloadingApkSet = new HashSet<>();
    }

    public static RemoteAppTask getInstance(){
        if(instance == null){
            synchronized (RemoteAppTask.class){
                if(instance ==null){
                    instance = new RemoteAppTask();
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
            HttpMaster.get(Constant.url.remote_apk)
                    .enqueue(new ResultListener<RemoteAppInfo>(RemoteAppInfo.class) {
                        @Override
                        public void onFailure(String e) {
                            Logger.e(e);
                        }

                        @Override
                        public void onSuccess(ResultInfo<RemoteAppInfo> resultInfo) throws Exception {
                            if(resultInfo.getCode() == 200){
                                List<RemoteAppInfo> list = resultInfo.getDataList();
                                if (list == null || list.size() <= 0) return;
                                for (RemoteAppInfo apkInfo : list) {
                                    if (!AppUtil.isInstalled(apkInfo.getPackageName()) ||
                                            AppUtil.isLastVersion(apkInfo.getPackageName(), apkInfo.getCode())) {
                                        Logger.d(apkInfo.getPackageName() + " is need download and install");
                                        if (!apkInfoMap.containsKey(apkInfo.getPackageName())) {
                                            apkInfoMap.put(apkInfo.getPackageName(), apkInfo);
                                        }
                                    }
                                }
                                handleDownload();
                            }
                        }
                    });
        }
    }

    private void handleDownload() {
        if(apkInfoMap.isEmpty()) return;
        for(Map.Entry<String, RemoteAppInfo> entry: apkInfoMap.entrySet()){
            if(!downloadingApkSet.contains(entry.getKey())) {
                download(entry.getValue());
                downloadingApkSet.add(entry.getKey());
            }else{
                Logger.d(entry.getKey() + " already in downloading...");
            }
        }
    }


    private void download(final RemoteAppInfo apkInfo) {
        FileUtil.delete(Application.APK_PATH, apkInfo.getName());
        HttpMaster.download(CommonApplication.context)
                .name(apkInfo.getName())
                .url(apkInfo.getUrl())
                .path(Application.APK_PATH)
                .startDownload(new DownloadListener() {
                    @Override
                    public void onPending(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onStart(DownloadInfo downloadInfo) {
                        Logger.d(apkInfo.getPackageName() + " start downloading...");
                    }

                    @Override
                    public void onPause(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onFinished(DownloadInfo downloadInfo) {
                        if(AppUtil.isApkCanInstall(Application.APK_PATH, downloadInfo.getName())){
                            if(AppUtil.isInstalled(apkInfo.getPackageName())){
                                Logger.d(apkInfo.getPackageName() + " apk installed, delete old version");
                                execCommand("pm", "uninstall", apkInfo.getPackageName());
                            }
                            Logger.d(apkInfo.getPackageName() + " apk installing");
                            String result = execCommand("pm","install","-f",
                                    Application.APK_PATH +"/"+apkInfo.getName());
                            if(result.contains("Success")){
                                Logger.d(apkInfo.getPackageName() + " apk install successfully!!!");
                                FileUtil.delete(Application.APK_PATH, apkInfo.getName());
                            }else{
                                Logger.d(apkInfo.getPackageName() + " apk install failure");
                            }
                        }
                        apkInfoMap.remove(apkInfo.getPackageName());
                        downloadingApkSet.remove(apkInfo.getPackageName());
                    }

                    @Override
                    public void onCancel(DownloadInfo downloadInfo) {
                        apkInfoMap.remove(apkInfo.getPackageName());
                        downloadingApkSet.remove(apkInfo.getPackageName());
                    }

                    @Override
                    public void onError(DownloadInfo downloadInfo) {
                        apkInfoMap.remove(apkInfo.getPackageName());
                        downloadingApkSet.remove(apkInfo.getPackageName());
                        Logger.e(downloadInfo.getMessage());
                    }
                });
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
