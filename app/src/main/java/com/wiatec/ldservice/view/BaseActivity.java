package com.wiatec.ldservice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wiatec.ldservice.presenter.BasePresenter;

/**
 * Created by patrick on 15/01/2018.
 * create time : 6:02 PM
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    protected abstract T createPresenter();
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
