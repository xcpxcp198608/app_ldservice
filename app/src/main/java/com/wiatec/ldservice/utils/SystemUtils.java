package com.wiatec.ldservice.utils;

import android.os.Build;

import com.px.common.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by patrick on 2018/5/18.
 * create time : 3:48 PM
 */

public class SystemUtils {

    public static boolean isLatestRom(){
        long buildTime = Build.TIME;
        long targetTime = 1747238400000L;//2025/5/15 00:00:00
        return buildTime > targetTime;
    }

    public static boolean is20250920Rom(){
        long buildTime = Build.TIME;
        long targetTime = 1758297600000L;//2025/9/20 00:00:00
        return buildTime >= targetTime;
    }

    public static boolean uninstall(String packageName){
        String result = execCommand("pm", "uninstall", packageName);
        return result.contains("Success");
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
