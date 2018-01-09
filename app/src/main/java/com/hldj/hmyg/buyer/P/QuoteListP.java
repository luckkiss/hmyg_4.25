package com.hldj.hmyg.buyer.P;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.buyer.M.QuoteGsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/4/25.
 */

public class QuoteListP {


    ResultCallBack<QuoteGsonBean> resultCallBack;

    public QuoteListP(ResultCallBack<QuoteGsonBean> resultCallBack) {
        this.resultCallBack = resultCallBack;
    }


    //admin/quote/detail

    /**
     * if (self.userInfoSingle.user_id) {
     * paramdic=@{@"id":purchaseMallModel.purchaseMallModelId,@"userId":self.userInfoSingle.user_id};
     * }else{
     * paramdic=@{@"id":purchaseMallModel.purchaseMallModelId};
     * }
     *
     * @param id
     * @return
     */

    public QuoteListP getDatas(String id) {
        D.e("=采购单=" + id);
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("purchaseItemId", id);
//        boolean isLogin = MyApplication.Userinfo.getBoolean("isLogin", false);
//        D.e("===islogin===" + isLogin);
//        if (isLogin) {
//            params.put("userId", MyApplication.Userinfo.getString("id", ""));
//        }
//purchaseItemId

        AjaxCallBack<String> ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("======onSuccess========54101356f7114c8286cac1e69b58a138" + json);

                QuoteGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(json, QuoteGsonBean.class);


                if (saveSeedingGsonBean.code.equals("1")) {
                    resultCallBack.onSuccess(saveSeedingGsonBean);
                } else {

//                    ToastUtil.showLongToast(saveSeedingGsonBean.msg);
                    resultCallBack.onFailure(new Throwable(saveSeedingGsonBean.msg), Integer.parseInt(saveSeedingGsonBean.code), saveSeedingGsonBean.msg);
                }


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                D.e("==失败==");
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        };

        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/showQuote", params, ajaxCallBack);

        return this;
    }


//    id

}
