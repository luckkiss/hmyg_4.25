package com.hldj.hmyg.buyer.P;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PurchaseDeatilP {


    ResultCallBack resultCallBack;

    public PurchaseDeatilP(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
    }

    /**
     * 获取 根据id 获取详情数据
     *
     * @param id
     * @return
     */
    public PurchaseDeatilP getDatasMgLst(String id) {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        params.put("userId", MyApplication.getUserBean().id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/detail", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {

//                      ManagerQuoteItemDetailGsonBean gsonBean = GsonUtil.formateJson2Bean(json, ManagerQuoteItemDetailGsonBean.class);
                        D.e("======onSuccess========54101356f7114c8286cac1e69b58a138" + json);

                        SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);

                        if (saveSeedingGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                            resultCallBack.onSuccess(saveSeedingGsonBean);
                        } else {
                            ToastUtil.showShortToast(saveSeedingGsonBean.getMsg());
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        ToastUtil.showShortToast("网络错误，请稍后重试");
                        super.onFailure(t, errorNo, strMsg);
                    }
                });

        return this;
    }
    //admin/purchase/detail

    public PurchaseDeatilP getDatas(String id) {
        D.e("=采购单=" + id);
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        boolean isLogin = MyApplication.Userinfo.getBoolean("isLogin", false);
        D.e("===islogin===" + isLogin);
        AjaxParams params = new AjaxParams();

        if (isLogin) {
            params.put("userId", MyApplication.Userinfo.getString("id", ""));
        }
        params.put("id", id);

        AjaxCallBack<String> ajaxCallBack = new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String json) {
                D.e("======onSuccess========54101356f7114c8286cac1e69b58a138" + json);


                SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);


                resultCallBack.onSuccess(saveSeedingGsonBean);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        };

        finalHttp.post(GetServerUrl.getUrl() + "purchase/itemDetail", params, ajaxCallBack);

        return this;
    }
    //admin/purchase/detail

    public PurchaseDeatilP quoteCommit(final Context context, PurchaseDetailActivity.uploadBean bean) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        paramsPut(params, ConstantParams.id, bean.id);
        paramsPut(params, ConstantParams.cityCode, bean.cityCode);
        paramsPut(params, ConstantParams.price, bean.price);
        paramsPut(params, ConstantParams.diameter, bean.diameter);
        paramsPut(params, ConstantParams.dbh, bean.dbh);
        paramsPut(params, ConstantParams.height, bean.height);
        paramsPut(params, ConstantParams.crown, bean.crown);
        paramsPut(params, ConstantParams.offbarHeight, bean.offbarHeight);
        paramsPut(params, ConstantParams.length, bean.length);
        paramsPut(params, ConstantParams.count, bean.count);
        paramsPut(params, ConstantParams.diameterType, bean.diameterType);
        paramsPut(params, ConstantParams.remarks, bean.remarks);
        paramsPut(params, ConstantParams.imagesData, bean.imagesData);
        paramsPut(params, ConstantParams.purchaseId, bean.purchaseId);
        paramsPut(params, ConstantParams.purchaseItemId, bean.purchaseItemId);
        paramsPut(params, ConstantParams.plantType, bean.plantType);


        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/save", params,
                new AjaxCallBack<String>() {

                    @Override
                    public void onSuccess(String t) {
                        try {
                            D.e("=========json=======" + t);
//                            Type beanType = new TypeToken<SimpleGsonBean_new>() { }.getType();
                            Type beanType = new TypeToken<SimpleGsonBean_new<PurchaseItemBean_new>>() {
                            }.getType();

                            SimpleGsonBean_new gsonBean_new = GsonUtil.formateJson2Bean(t, beanType);
//                            ToastUtil.showShortToast(gsonBean_new.data.purchaseItem.toString());
//                            SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(t, SaveSeedingGsonBean.class);
                            if (gsonBean_new.isSucceed()) {
                                if (gsonBean_new.data != null && gsonBean_new.data.purchaseItem != null) {
                                    PurchaseItemBean_new itemBean_new = (PurchaseItemBean_new) gsonBean_new.data.purchaseItem;
                                    Log.e("onSuccess", "onSuccess: " + gsonBean_new.data.purchaseItem + " itemBean_new= " + itemBean_new.toString());
                                    resultCallBack.onSuccess(gsonBean_new.data.purchaseItem);
                                } else {
                                    resultCallBack.onSuccess(null);
                                }

                            } else {
                                ToastUtil.showShortToast(gsonBean_new.msg);
//                                Toast.makeText(MyApplication.getInstance(), saveSeedingGsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            D.e("===网络失错误==" + e.getMessage());
                            ToastUtil.showShortToast("网络错误");
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                        super.onFailure(t, errorNo, strMsg);
                    }

                });


        return this;
    }

    private void paramsPut(AjaxParams params, String key, String values) {
        if (TextUtils.isEmpty(values)) {
            D.e("==参数==" + key + "  值为空");
        } else {
            params.put(key, values);
        }


    }


    /*报价删除*/
    public void quoteDdel(String sellerQuoteJson_id) {
        // TODO Auto-generated method stub

        if ("".equals(sellerQuoteJson_id)) {
            ToastUtil.showShortToast("删除失败，报价id为空");
            return;
        }
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);

        AjaxParams params = new AjaxParams();
        params.put("id", sellerQuoteJson_id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/del", params,
                new AjaxCallBack<String>() {

                    @Override
                    public void onSuccess(String json) {
                        // TODO Auto-generated method stub
                        try {

                            Type beanType = new TypeToken<SimpleGsonBean_new<PurchaseItemBean_new>>() {
                            }.getType();

                            SimpleGsonBean_new gsonBean_new = GsonUtil.formateJson2Bean(json, beanType);
//                            SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);
                            if (gsonBean_new.isSucceed()) {

                                if (gsonBean_new.data!=null && gsonBean_new.data.purchaseItem != null) {
                                    PurchaseItemBean_new itemBean_new = (PurchaseItemBean_new) gsonBean_new.data.purchaseItem;
                                    Log.e("onSuccess", "onSuccess: " + gsonBean_new.data.purchaseItem + " itemBean_new= " + itemBean_new.toString());
                                    resultCallBack.onSuccess(gsonBean_new.data.purchaseItem);
                                }else
                                {
                                    resultCallBack.onSuccess(null);
                                }


                            } else {
                                ToastUtil.showShortToast("删除失败" + gsonBean_new.msg);
                                onFailure(null, -1, gsonBean_new.msg);
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            ToastUtil.showShortToast("网络错误,删除失败");

                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        ToastUtil.showShortToast(R.string.error_net);
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }


//    id

}
