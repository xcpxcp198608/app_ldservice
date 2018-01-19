package com.wiatec.ldservice.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by patrick on 15/01/2018.
 * create time : 6:01 PM
 */

public abstract class BasePresenter<V> {

    private WeakReference<V> weakReference;

    public void attachView(V v){
        weakReference = new WeakReference<V>(v);
    }

    public void detachView(){
        if(weakReference != null){
            weakReference.clear();
            weakReference = null;
        }
    }

    protected  V getView (){
        return weakReference.get();
    }
}
