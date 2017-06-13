package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.contract.AAliContract;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class AAliPresenter extends AAliContract.Presenter {

    @Override
    public void onStart() {
        //请求开始 这里做一些初始化。和准备工作
        D.e("=======onStart==========");
    }

    @Override
    public void getData(String url) {
        //此处请求aactivity  的数据
        mModel.requestData(new ResultCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                mView.initRecycleView(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir("错误类型：" + t.getMessage() + " 错误代码：" + errorNo + " 错误原因 ：" + strMsg);
            }
        });


    }


    public void requestData() {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("latitude", MyApplication.Userinfo.getString("latitude", ""));
        params.put("longitude",
                MyApplication.Userinfo.getString("longitude", ""));
        finalHttp.post(GetServerUrl.getUrl() + "index", params,
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {

                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                        super.onFailure(t, errorNo, strMsg);
                    }


                });
    }


}
