package com.hldj.hmyg.saler.P;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.saler.M.PurchaseListGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/5/12.
 */

public class PurchasePyMapPresenter {


    ResultCallBack resultCallBack;
    private AjaxParams ajaxParams;

    public AjaxParams getAjaxParams() {
        if (null == ajaxParams) {
            ajaxParams = new AjaxParams();
        }
        return ajaxParams;
    }

    //初始化清空
    public PurchasePyMapPresenter() {
        ajaxParams = null;
    }

    public PurchasePyMapPresenter addResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
        return this;
    }

    public PurchasePyMapPresenter requestDatas(String path) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        finalHttp.post(GetServerUrl.getUrl() + "purchase/purchaseList", getAjaxParams(),
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            String json = t.toString();
                            PurchaseListGsonBean purchaseListGsonBean = GsonUtil.formateJson2Bean(json, PurchaseListGsonBean.class);
                            if (!purchaseListGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                                ToastUtil.showShortToast("数据加载失败！");
                                return;
                            } else {
                                resultCallBack.onSuccess(purchaseListGsonBean.data.page.data);
                            }
                        } catch (Exception e) {
                            resultCallBack.onSuccess(null);
                        }


                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        ToastUtil.showShortToast("网络错误，数据请求失败");
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

        return this;
    }


    /**
     * 先传参数
     *
     * @param key
     * @param value
     * @return
     */
    public PurchasePyMapPresenter putParams(String key, String value) {
        if (value != null) {
            getAjaxParams().put(key, value);
        } else {
            D.e(key + "   的值为空" + value);
        }

        return this;
    }


}
