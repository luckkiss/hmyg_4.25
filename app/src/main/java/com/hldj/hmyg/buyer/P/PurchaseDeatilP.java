package com.hldj.hmyg.buyer.P;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
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

/**
 * Created by Administrator on 2017/4/25.
 */

public class PurchaseDeatilP {


    ResultCallBack<SaveSeedingGsonBean> resultCallBack;

    public PurchaseDeatilP(ResultCallBack<SaveSeedingGsonBean> resultCallBack) {
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
                            SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(t, SaveSeedingGsonBean.class);
                            if (saveSeedingGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                                resultCallBack.onSuccess(saveSeedingGsonBean);
                            } else {
                                ToastUtil.showShortToast(saveSeedingGsonBean.getMsg());
                                Toast.makeText(MyApplication.getInstance(), saveSeedingGsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            D.e("===网络失错误==");
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

                            SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);
                            if ("1".equals(saveSeedingGsonBean.getCode())) {
                                resultCallBack.onSuccess(saveSeedingGsonBean);
                            } else {

                                ToastUtil.showShortToast("删除失败"+saveSeedingGsonBean.getMsg());
                                onFailure(null,-1,saveSeedingGsonBean.getMsg());
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
