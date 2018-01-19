package com.wiatec.ldservice.task;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.utils.Logger;
import com.px.common.utils.NetUtil;
import com.px.common.utils.SPUtil;
import com.wiatec.ldservice.pojo.WeatherInfo;

import java.io.IOException;

/**
 * Created by patrick on 21/07/2017.
 * create time : 10:33 AM
 */

public class LoadWeather implements Runnable {

    @Override
    public void run() {
        String city = (String) SPUtil.get("city", "");
        if(!TextUtils.isEmpty(city)) {
            load(city);
        }
    }

    private void load(String city) {
        if(!NetUtil.isConnected()){
            load(city);
        }else {
            String apiKey = "c0c69463f12ddb77b388fe9fac994407";
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + apiKey;
            HttpMaster.get(url)
                    .enqueue(new StringListener() {
                        @Override
                        public void onSuccess(String s) throws IOException {
                            WeatherInfo weatherInfo = new Gson().fromJson(s,
                                    new TypeToken<WeatherInfo>() {
                                    }.getType());
                            SPUtil.put("temperature", weatherInfo.getMain().getTemp() + "");
                            SPUtil.put("weatherIcon", weatherInfo.getWeather().get(0).getIcon());
                        }

                        @Override
                        public void onFailure(String e) {
                            Logger.d(e);
                        }
                    });
        }
    }
}
