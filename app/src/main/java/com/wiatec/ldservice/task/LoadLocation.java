package com.wiatec.ldservice.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.px.common.utils.SPUtil;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.pojo.LocationInfo;

import java.io.IOException;

/**
 * Created by patrick on 21/07/2017.
 * create time : 10:15 AM
 */

public class LoadLocation implements Runnable {

    @Override
    public void run() {
        load();
    }

    private void load() {
        if(!NetUtil.isConnected()){
            load();
        }else {
            HttpMaster.get("http://ip-api.com/json")
                    .enqueue(new StringListener() {
                        @Override
                        public void onSuccess(String s) throws IOException {
                            LocationInfo l = new Gson().fromJson(s,
                                    new TypeToken<LocationInfo>() {
                                    }.getType());
                            if (l != null) {
                                SPUtil.put("country", l.getCountry());
                                SPUtil.put("countryCode", l.getCountryCode());
                                SPUtil.put("timezone", l.getTimezone());
                                SPUtil.put("regionName", l.getRegionName());
                                SPUtil.put("city", l.getCity());
                                SPUtil.put("lat", l.getLat());
                                SPUtil.put("lon", l.getLon());
                                Application.getExecutorService().execute(new LoadWeather());
                            }
                        }

                        @Override
                        public void onFailure(String e) {
                            Logger.d(e);
                        }
                    });
        }
    }
}
