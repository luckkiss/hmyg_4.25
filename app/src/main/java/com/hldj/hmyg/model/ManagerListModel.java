package com.hldj.hmyg.model;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/6/11 0011.
 */

public class ManagerListModel extends BasePresenter implements ManagerListContract.Model {
    @Override
    public void getDatas(String page, String status, String searchKey, ResultCallBack callBack) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("status", status);
        params.put("pageSize", 10 + "");
        params.put("pageIndex", page);
        params.put("searchKey", searchKey);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/manage/list", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {

                BPageGsonBean bPageGsonBean = GsonUtil.formateJson2Bean(json, BPageGsonBean.class);
                D.e("==========json=============" + json);

                if (bPageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    callBack.onSuccess(bPageGsonBean);
                } else {
                    callBack.onFailure(null, 0, bPageGsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                callBack.onFailure(t, errorNo, strMsg);
                super.onFailure(t, errorNo, strMsg);
            }
        });


    }

    public void getCounts(ResultCallBack resultCallBack) {


        AjaxCallBack acallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {

                    D.e("=json====" + json);

                    CountTypeGsonBean gsonBean = GsonUtil.formateJson2Bean(json, CountTypeGsonBean.class);


                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        resultCallBack.onSuccess(gsonBean);
                    } else {
                        //失败
                        resultCallBack.onSuccess(gsonBean.msg);
                    }
                } catch (Exception e) {
                    ToastUtil.showShortToast("获取数据失败" + e.getMessage());
                    resultCallBack.onFailure(null, -1, e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                ToastUtil.showShortToast("网络错误，数据请求失败");
                resultCallBack.onFailure(t, errorNo, strMsg);
                super.onFailure(t, errorNo, strMsg);
            }
        };


        doRequest("admin/seedling/statusCount", true, acallBack);


    }

    @Override
    public void doDelete(String id, ResultCallBack callBack) {
        putParams("ids", id);
        AjaxCallBack acallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {
                    D.e("=json====" + json);
                    SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                    if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        callBack.onSuccess(true);
                    } else {
                        //失败
                        callBack.onFailure(null,0,simpleGsonBean.msg);
                    }
                } catch (Exception e) {
                    ToastUtil.showShortToast("获取数据失败" + e.getMessage());
                    callBack.onFailure(null, -1, e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                ToastUtil.showShortToast("网络错误，数据请求失败");
                resultCallBack.onFailure(t, errorNo, strMsg);
                super.onFailure(t, errorNo, strMsg);
            }
        };


        doRequest("admin/seedling/doDel", true, acallBack);
    }




}
