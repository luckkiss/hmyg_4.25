package com.hldj.hmyg.saler.P;

import android.text.TextUtils;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 *  写给rx java 的  数据请求接口    支持将json 返回
 */

public  class BaseRxPresenter extends Observable<String> {
    protected ResultCallBack resultCallBack;
    protected AjaxParams ajaxParams;

    public AjaxParams getAjaxParams() {
        if (null == ajaxParams) {
            ajaxParams = new AjaxParams();
        }
        return ajaxParams;
    }

    public void resetAjaxParams() {
        ajaxParams = null;
    }

    //初始化清空
    public BaseRxPresenter() {
        ajaxParams = null;
    }

    @Override
    protected void subscribeActual(Observer<? super java.lang.String> observer) {
            //会在 subscribe 之后 回调
    }

    public BaseRxPresenter addResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
        return this;
    }


    public BaseRxPresenter doRequest(String path, boolean isAddHeaders, AjaxCallBack callBack) {
        {
            FinalHttp finalHttp = new FinalHttp();
            GetServerUrl.addHeaders(finalHttp, isAddHeaders);
            finalHttp.post(GetServerUrl.getUrl() + path, getAjaxParams(), callBack);
            resetAjaxParams();
            return this;

        }
    }


    /**
     * 先传参数
     *
     * @param key
     * @param value
     * @return
     */
    public BaseRxPresenter putParams(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            getAjaxParams().put(key, value);
            D.e(key + " <=========>" + value);
        } else {
            D.e(key + "为空");
        }

        return this;
    }


    /**
     * 通过对象  反射出 key 与value  进行 请求 网络
     *
     * @param t
     * @return
     */
    public BaseRxPresenter putParams(Object t) {
        ajaxParams = null;//清空参数

        if (t == null) {
            throw new NullPointerException("对象不能为空");
        }
        D.e("======请求的对象======" + t.toString() + "  --->" + t.getClass().getName());
        Class cla = (Class) t.getClass();
        Field[] f = cla.getDeclaredFields();
        //获取字段名
        for (int i = 0; i < f.length; i++) {
            try {
                if (f[i].get(t) != null && !TextUtils.isEmpty(f[i].get(t).toString())) {
                    putParams(f[i].getName(), f[i].get(t).toString());
                } else {
                    putParams(f[i].getName(), "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        D.e("====请求网络的参数====params=======" + getAjaxParams().toString());
        return this;
    }


}
