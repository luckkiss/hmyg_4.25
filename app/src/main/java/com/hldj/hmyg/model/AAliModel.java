package com.hldj.hmyg.model;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.contract.AAliContract;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class AAliModel implements AAliContract.Model {

    @Override
    public void requestData(ResultCallBack resultCallBack) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("latitude", MyApplication.Userinfo.getString("latitude", ""));
        params.put("longitude", MyApplication.Userinfo.getString("longitude", ""));

        finalHttp.post(GetServerUrl.getUrl() + "index", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                resultCallBack.onSuccess(json);
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        });
    }
}
