package com.wiatec.ldservice.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.constant.CommonApplication;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ListListener;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelType1Info;
import com.wiatec.ldservice.pojo.ImageInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 21/07/2017.
 * create time : 10:49 AM
 */

public class DownloadAdImage implements Runnable {
    @Override
    public void run() {
        download(Constant.url.ad_image, Application.AD_IMAGE_PATH);
    }

    private void download(String url, final String path) {
        if(!NetUtil.isConnected()){
            download(url, path);
        }else {
            HttpMaster.get(url)
                    .enqueue(new ResultListener<ImageInfo>(ImageInfo.class) {
                        @Override
                        public void onSuccess(ResultInfo<ImageInfo> resultInfo) throws Exception {
                            if(resultInfo.getCode() != 200){
                                return;
                            }
                            List<ImageInfo> list = resultInfo.getDataList();
                            List<String> nameList = new ArrayList<>();
                            if (list != null && list.size() > 0) {
                                for (ImageInfo i : list) {
                                    HttpMaster.download(CommonApplication.context)
                                            .name(i.getName())
                                            .url(i.getUrl())
                                            .path(path)
                                            .startDownload(null);
                                    nameList.add(i.getName());
                                }
                                deleteOldImage(nameList, path);
                            }
                        }

                        @Override
                        public void onFailure(String e) {
                            Logger.e(e);
                        }
                    });
        }
    }

    private void deleteOldImage(List<String> nameList, String path){
        File file = new File(path);
        if(file.exists()){
            File[] files = file.listFiles();
            if(files.length > 0){
                for(File f: files){
                    if(!nameList.contains(f.getName())){
                        f.delete();
                    }
                }
            }
        }
    }
}
