package com.wiatec.ldservice.model;


import java.util.List;

/**
 * Created by patrick on 16/01/2018.
 * create time : 9:16 AM
 */

public interface ResultListLoader<T> {

    void load(OnLoadListener<T> onLoadListener);
    public interface OnLoadListener<T>{
        void onSuccess(boolean execute, List<T> list);
        void onFailure(String e);
    }
}
