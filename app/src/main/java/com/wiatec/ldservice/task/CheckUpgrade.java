package com.wiatec.ldservice.task;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.constant.CommonApplication;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.DownloadListener;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.DownloadInfo;
import com.px.common.utils.AppUtil;
import com.px.common.utils.EmojiToast;
import com.px.common.utils.FileUtil;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.UpgradeInfo;

import java.io.IOException;

/**
 * Created by patrick on 29/09/2017.
 * create time : 9:35 AM
 */

public class CheckUpgrade implements Runnable {

    private static volatile CheckUpgrade instance;
    private boolean isShow = false;

    private CheckUpgrade(){

    }

    public static CheckUpgrade getInstance(){
        if(instance == null){
            synchronized (CheckUpgrade.class){
                if(instance ==null){
                    instance = new CheckUpgrade();
                }
            }
        }
        return instance;
    }


    @Override
    public void run() {
        check();
    }

    private void check(){
        if(!NetUtil.isConnected()){
            check();
        }else {
            HttpMaster.get(Constant.url.upgrade)
                    .enqueue(new StringListener() {
                        @Override
                        public void onSuccess(String s) throws IOException {
                            try {
                                if (TextUtils.isEmpty(s)) return;
                                UpgradeInfo upgradeInfo = new Gson().fromJson(s,
                                        new TypeToken<UpgradeInfo>() {
                                        }.getType());
                                if (upgradeInfo == null) return;
                                if (AppUtil.isNeedUpgrade(upgradeInfo.getCode())) {
                                    if(!isShow) {
                                        showUpgradeDialog(upgradeInfo);
                                    }
                                } else {
                                    FileUtil.delete(Application.APK_PATH, CommonApplication.context.getPackageName() + ".apk");
                                }
                            } catch (Exception e) {
                                Logger.d(e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(String e) {

                        }
                    });
        }
    }

    private void showUpgradeDialog(final UpgradeInfo upgradeInfo) {
        isShow = true;
        MaterialDialog dialog = new MaterialDialog.Builder(CommonApplication.context)
                .cancelable(false)
                .title(CommonApplication.context.getString(R.string.app_name))
                .content(upgradeInfo.getInfo())
                .positiveText("Confirm")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showUpgradeProcessDialog(upgradeInfo);
                    }
                })
                .build();
        Window window = dialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    private void showUpgradeProcessDialog(UpgradeInfo upgradeInfo){
        final ProgressDialog progressDialog = new ProgressDialog(CommonApplication.context);
        progressDialog.setTitle(CommonApplication.context.getString(R.string.download_title));
        progressDialog.setMessage(CommonApplication.context.getString(R.string.download_message));
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Window window = progressDialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        progressDialog.show();
        HttpMaster.download(CommonApplication.context)
                .url(upgradeInfo.getUrl())
                .path(Application.APK_PATH)
                .name(CommonApplication.context.getPackageName()+".apk")
                .startDownload(new DownloadListener() {
                    @Override
                    public void onPending(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onStart(DownloadInfo downloadInfo) {
                        progressDialog.setProgress(downloadInfo.getProgress());
                    }

                    @Override
                    public void onPause(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {
                        progressDialog.setProgress(downloadInfo.getProgress());
                    }

                    @Override
                    public void onFinished(DownloadInfo downloadInfo) {
                        isShow = false;
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        if(AppUtil.isApkCanInstall(Application.APK_PATH, downloadInfo.getName())){
                            AppUtil.installApk(Application.APK_PATH, downloadInfo.getName(), "");
                        }else{
                            FileUtil.delete(Application.APK_PATH, downloadInfo.getName());
                            EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                        }
                    }

                    @Override
                    public void onCancel(DownloadInfo downloadInfo) {
                        isShow = false;
                        progressDialog.dismiss();
                        EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                    }

                    @Override
                    public void onError(DownloadInfo downloadInfo) {
                        isShow = false;
                        progressDialog.dismiss();
                        EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                    }
                });
    }

}
