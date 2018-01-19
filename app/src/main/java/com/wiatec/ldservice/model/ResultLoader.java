package com.wiatec.ldservice.model;


import com.px.common.http.pojo.ResultInfo;

/**
 * Created by patrick on 16/01/2018.
 * create time : 9:16 AM
 */

public interface ResultLoader<T> {

    void load(OnLoadListener<T> onLoadListener);
    public interface OnLoadListener<T>{
        void onSuccess(boolean execute, T t);
        void onFailure(String e);
    }
}
