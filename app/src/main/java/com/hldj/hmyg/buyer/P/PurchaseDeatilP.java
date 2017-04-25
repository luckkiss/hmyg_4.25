package com.hldj.hmyg.buyer.P;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

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


//    id

}
