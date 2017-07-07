package com.hldj.hmyg.model;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.ProgramPageGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseIndexGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public class ProgramPurchaseModel extends BasePresenter implements ProgramPurchaseContract.Model {
    @Override
    public void getDatas(String page, String purchaseId, String searchKey, ResultCallBack callBack) {

        putParams("pageSize", "10");
        putParams("pageIndex", page);
        putParams("purchaseId", purchaseId);
        putParams("searchKey", searchKey);

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("==========json=============" + json);
                try {
                    ProgramPageGsonBean pageGsonBean = GsonUtil.formateJson2Bean(json, ProgramPageGsonBean.class);
                    if (pageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        callBack.onSuccess(pageGsonBean);
                    } else {
                        callBack.onFailure(null, 0, pageGsonBean.msg);
                    }
                } catch (Exception e) {
                    callBack.onFailure(null, 0, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                callBack.onFailure(t, errorNo, strMsg);
                super.onFailure(t, errorNo, strMsg);
            }

        };

//        doRequest("admin/project/list", true, ajaxCallBack);
        doRequest("admin/project/purchaseItemlist", true, ajaxCallBack);

    }


    public void getIndexDatas(ResultCallBack resultCallBack, String purchaseId) {
        putParams("purchaseId", purchaseId);
        AjaxCallBack acallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {
                    D.e("=json====" + json);
                    ProgramPurchaseIndexGsonBean gsonBean = GsonUtil.formateJson2Bean(json, ProgramPurchaseIndexGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        gsonBean.data.purchase.showQuote = gsonBean.data.showQuote;
                        gsonBean.data.purchase.servicePoint = gsonBean.data.servicePoint;
                        gsonBean.data.purchase.lastTime = gsonBean.data.lastTime;
                        resultCallBack.onSuccess(gsonBean.data.purchase);
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
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        };


        doRequest("admin/project/purchaseItemIndex", true, acallBack);


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
