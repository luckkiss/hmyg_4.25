package com.hldj.hmyg.model;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.ProgramPageGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseIndexGsonBean;
import com.hldj.hmyg.M.QuoteUserGroup;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public class ProgramPurchaseModel extends BasePresenter implements ProgramPurchaseContract.Model {

    /*可采用报价权限*/
    private boolean tureQuote;


    /* 获取供应商  请求接口  */
    @Override
    public void getDatasGys(String page, String proId, String sellerId, String states, ResultCallBack callBack) {

        putParams(ConstantParams.pageSize, "10");
        putParams(ConstantParams.pageIndex, page);
        putParams("purchaseId", proId);
        putParams("status", states);

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("==========json=============" + GsonUtil.formatJson2String(json));
                try {

//                    SimpleGsonBean_new<List<QuoteUserGroup>> pageGsonBean = GsonUtil.formateJson2List(json, List<QuoteUserGroup>);
//                    Type userType = new TypeToken<List<QuoteUserGroup>>(){}.getType();

                    Type beanType = new TypeToken<SimpleGsonBean_new<List<QuoteUserGroup>>>() {
                    }.getType();

//                    Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
//                    }.getType();
//                    SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);


                    SimpleGsonBean_new<List<QuoteUserGroup>> pageGsonBean = GsonUtil.formateJson2Bean(json, beanType);


                    if (pageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        callBack.onSuccess(pageGsonBean.data.quoteList);
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
        doRequest("admin/purchase/groupBySeller", true, ajaxCallBack);
//        doRequest("admin/project/purchaseSupply", true, ajaxCallBack);


    }


    @Override
    public void getDatas(String page, String purchaseId, String sellerId, String status, ResultCallBack callBack) {
        putParams("pageSize", "10");
        putParams("pageIndex", page);
        putParams("purchaseId", purchaseId);
//        putParams("searchKey", searchKey);
        putParams("sellerId", sellerId);
        putParams("status", status);
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

//        doRequest("admin/project/list", true, ajaxCallBack);+
        if (TextUtils.isEmpty(sellerId)) {
            doRequest("admin/purchaseItem/listByProjectPurchase", true, ajaxCallBack);

        } else {
            doRequest("admin/purchaseItem/listByPurchaseSeller", true, ajaxCallBack);

        }


    }


    @Override
    public void getIndexDatas(ResultCallBack resultCallBack, String purchaseId, String status) {
        putParams("purchaseId", purchaseId);
        putParams("status", status);
        AjaxCallBack acallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {
                    D.e("=json====\n" + GsonUtil.formatJson2String(json));
                    ProgramPurchaseIndexGsonBean gsonBean = GsonUtil.formateJson2Bean(json, ProgramPurchaseIndexGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        tureQuote = gsonBean.data.hasChoosePermission;
                        /*----*/
                        gsonBean.data.purchase.tureQuote = gsonBean.data.hasChoosePermission;
                        gsonBean.data.purchase.showQuote = gsonBean.data.showQuote;
                        gsonBean.data.purchase.status = gsonBean.data.status;
                        gsonBean.data.purchase.statusName = gsonBean.data.statusName;
                        gsonBean.data.purchase.servicePoint = gsonBean.data.servicePoint;
                        gsonBean.data.purchase.isOpen = gsonBean.data.isOpen;
                        gsonBean.data.purchase.lastTime = gsonBean.data.lastTime;
                        resultCallBack.onSuccess(gsonBean.data);
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


        doRequest("admin/purchase/detailForConsumer", true, acallBack);


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
