package com.wiatec.ldservice.manager;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.DownloadListener;
import com.px.common.http.pojo.DownloadInfo;
import com.px.common.utils.AppUtil;
import com.px.common.utils.CommonApplication;
import com.px.common.utils.EmojiToast;
import com.px.common.utils.FileUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

/**
 * Created by patrick on 19/01/2018.
 * create time : 5:57 PM
 */

public class AppDownloadManager {

    public void showUpgradeDialog(final ResourceAppInfo resourceAppInfo, String message) {
        MaterialDialog dialog = new MaterialDialog.Builder(CommonApplication.context)
                .title("Download")
                .content(resourceAppInfo.getLabel() + " " + message)
                .positiveText("Confirm")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showUpgradeProcessDialog(resourceAppInfo);
                        dialog.dismiss();
                    }
                })
                .negativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        Window window = dialog.getWindow();
        if(window != null) {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }


    private void showUpgradeProcessDialog(ResourceAppInfo resourceAppInfo){
        FileUtil.delete(Application.APK_PATH, resourceAppInfo.getLabel()+".apk");
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
                .url(resourceAppInfo.getUrl())
                .path(Application.APK_PATH)
                .name(resourceAppInfo.getLabel()+".apk")
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
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        if(AppUtil.isApkCanInstall(Application.APK_PATH, downloadInfo.getName())){
                            AppUtil.installApk(Application.APK_PATH, downloadInfo.getName(), "com.wiatec.ldservice.fileprovider");
                        }else{
                            FileUtil.delete(Application.APK_PATH, downloadInfo.getName());
                            EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                        }
                    }

                    @Override
                    public void onCancel(DownloadInfo downloadInfo) {
                        progressDialog.dismiss();
                        EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                    }

                    @Override
                    public void onError(DownloadInfo downloadInfo) {
                        progressDialog.dismiss();
                        EmojiToast.show(CommonApplication.context.getString(R.string.install_error), EmojiToast.EMOJI_SAD);
                    }
                });
    }
}
