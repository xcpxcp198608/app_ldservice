package com.wiatec.ldservice.model;


import com.px.common.http.pojo.ResultInfo;

import java.util.List;

/**
 * Created by patrick on 16/01/2018.
 * create time : 9:16 AM
 */

public interface ResultListWithParamLoader<T> {

    void load(String param, OnLoadListener<T> onLoadListener);
    public interface OnLoadListener<T>{
        void onSuccess(boolean execute, List<T> list);
        void onFailure(String e);
    }
}
