package com.hldj.hmyg.P;

import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class BPresenter extends BasePresenter {


    public BPresenter getDatas(String url, boolean isAddHead) {

        AjaxCallBack callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {
                    BPageGsonBean gsonBean = GsonUtil.formateJson2Bean(json, BPageGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        resultCallBack.onSuccess(gsonBean.data.page.data);
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
                resultCallBack.onFailure(t,errorNo,strMsg);
                super.onFailure(t, errorNo, strMsg);
            }
        };
        return (BPresenter) doRequest(url, isAddHead, callBack);
    }




}
