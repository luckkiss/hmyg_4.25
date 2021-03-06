package com.hldj.hmyg.model;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.contract.MyProgramContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public class MyProgramModel extends BasePresenter implements MyProgramContract.Model {
    @Override
    public void getDatas(String page, String searchKey, ResultCallBack callBack) {
        putParams("pageSize", 10 + "");
        putParams("pageIndex", page);
        putParams("searchKey", searchKey);

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("==========json=============" + json);
                MyProgramGsonBean bPageGsonBean = GsonUtil.formateJson2Bean(json, MyProgramGsonBean.class);


                if (bPageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    callBack.onSuccess(bPageGsonBean);
                } else {
                    callBack.onFailure(null, 0, bPageGsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                callBack.onFailure(t, errorNo, "无网络连接，请检查您的网络···");

            }

        };

        doRequest("admin/project/list", true, ajaxCallBack);


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
                    resultCallBack.onFailure(null, -1, e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, errorNo, "无网络连接，请检查您的网络···");
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
                        callBack.onFailure(null, 0, simpleGsonBean.msg);
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
