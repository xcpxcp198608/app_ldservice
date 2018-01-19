package com.wiatec.ldservice.remote_play;

import com.px.common.http.HttpMaster;
import com.px.common.http.listener.ResultListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.CommonApplication;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.px.common.utils.SysUtil;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ControlPlayInfo;

/**
 * Created by patrick on 27/09/2017.
 * create time : 11:25 AM
 */

public class RemotePlayTask implements Runnable {

    private static final int SLEEP_TIME = 5000;

    @Override
    public void run() {
        start();
    }

    private void start() {
        while (true) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                Logger.e(e.getMessage());
            }
            if (NetUtil.isConnected()) {
                load();
            }
        }
    }

    private void load() {
        HttpMaster.post(Constant.url.automatic_play)
                .param("mac", SysUtil.getEthernetMac())
                .enqueue(new ResultListener<ControlPlayInfo>(ControlPlayInfo.class) {
                    @Override
                    public void onSuccess(ResultInfo<ControlPlayInfo> resultInfo) throws Exception {
                        try{
                            if(resultInfo == null || resultInfo.getCode() != 200){
                                return;
                            }
                            ControlPlayInfo controlPlayInfo = resultInfo.getData();
                            if(controlPlayInfo == null) return;
                            if(controlPlayInfo.isPlay()) {
                                RemotePlayManager.launchPlay(CommonApplication.context,
                                        controlPlayInfo.getUrl(),
                                        controlPlayInfo.getDuration());
                            }
                        }catch(Exception e){
                            Logger.e(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.e(e);
                    }
                });
    }
}
