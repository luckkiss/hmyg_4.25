package com.hldj.hmyg.base.Rx;

import android.content.Context;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public abstract class BasePresenter<E, T> {

    public Context context;
    public E mModel;
    public T mView;



    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }


    public abstract void onStart();



}
