package com.wiatec.ldservice.provider;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.common.http.HttpMaster;
import com.px.common.http.listener.StringListener;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.model.ResultListLoader;
import com.wiatec.ldservice.pojo.LDFamInfo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * channel provider
 */

public class BVisionProvider implements ResultListLoader<LDFamInfo> {

    @Override
    public void load(final ResultListLoader.OnLoadListener<LDFamInfo> onLoadListener) {
        HttpMaster.get(Constant.url.ld_fam)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        List<LDFamInfo> ldFamInfoList = new Gson().fromJson(s,
                                new TypeToken<List<LDFamInfo>>(){}.getType());
                        if(ldFamInfoList == null || ldFamInfoList.size() <= 0){
                            onLoadListener.onSuccess(false, null);
                            return;
                        }
                        onLoadListener.onSuccess(true, ldFamInfoList);
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onSuccess(false, null);
                    }
                });
    }
}
