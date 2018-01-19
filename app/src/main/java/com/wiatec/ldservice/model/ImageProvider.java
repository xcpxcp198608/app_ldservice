package com.wiatec.ldservice.model;

import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.pojo.ImageInfo;

import java.io.File;
import java.util.Random;

/**
 * Created by patrick on 18/01/2018.
 * create time : 10:00 AM
 */

public class ImageProvider implements ResultLoader<ImageInfo>{

    @Override
    public void load(OnLoadListener<ImageInfo> onLoadListener) {
        File file = new File(Application.AD_IMAGE_PATH);
        if(!file.exists()) return;
        File[] imageFiles = file.listFiles();
        if(imageFiles != null && imageFiles.length > 0){
            int random = new Random().nextInt(imageFiles.length);
            File file1 = imageFiles[random];
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setUrl(file1.getAbsolutePath());
            onLoadListener.onSuccess(true, imageInfo);
        }
    }
}
