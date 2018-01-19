package com.wiatec.ldservice.pay;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.http.pojo.ResultInfo;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.model.ResultLoader;

import java.io.IOException;

/**
 * Created by patrick on 06/11/2017.
 * create time : 4:24 PM
 */

public class PayProvider {

    public void payVerify(String payerName, int publisherId, String paymentId,
                           final ResultLoader.OnLoadListener<PayResultInfo> onLoadListener){
        String url = Constant.url.blive_base + "pay/verify/" + payerName + "/" + publisherId;
        if(!TextUtils.isEmpty(paymentId)){
            url = url + "/" + paymentId;
        }
        HttpMaster.post(url)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        ResultInfo<PayResultInfo> resultInfo = new Gson().fromJson(s,
                                new TypeToken<ResultInfo<PayResultInfo>>(){}.getType());
                        if(resultInfo == null){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, resultInfo.getData());
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                        onLoadListener.onSuccess(false, null);
                    }
                });
    }
}
