package com.hldj.hmyg.saler.P;

import android.text.TextUtils;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/5/12.
 */

public abstract class BasePresenter {


    protected ResultCallBack resultCallBack;
    protected AjaxParams ajaxParams;

    public AjaxParams getAjaxParams() {
        if (null == ajaxParams) {
            ajaxParams = new AjaxParams();
        }
        return ajaxParams;
    }

    //初始化清空
    public BasePresenter() {
        ajaxParams = null;
    }

    public BasePresenter addResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
        return this;
    }


    protected BasePresenter doRequest(String path, boolean isAddHeaders, AjaxCallBack callBack) {
        {
            FinalHttp finalHttp = new FinalHttp();
            GetServerUrl.addHeaders(finalHttp, isAddHeaders);
            finalHttp.post(GetServerUrl.getUrl() + path, getAjaxParams(), callBack);
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
    public BasePresenter putParams(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            getAjaxParams().put(key, value);
            D.e(key + " <=========>" + value);
        } else {
            D.e(key + "为空");
        }

        return this;
    }


}
