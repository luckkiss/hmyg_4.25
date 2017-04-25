package com.hldj.hmyg.CallBack;

/**
 * 自定json 回调接口
 * Created by Administrator on 2017/4/14.
 */

public interface ResultCallBack<T> {

    void onSuccess(T t);

    void onFailure(Throwable t, int errorNo, String strMsg);
}
