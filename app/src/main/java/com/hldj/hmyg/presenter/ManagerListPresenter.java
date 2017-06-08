package com.hldj.hmyg.presenter;

import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class ManagerListPresenter extends BasePresenter{



    public ManagerListPresenter getStatusCount(){

        AjaxCallBack callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                try {

                    D.e("=json===="+json);

                    CountTypeGsonBean gsonBean = GsonUtil.formateJson2Bean(json, CountTypeGsonBean.class);



                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        resultCallBack.onSuccess(gsonBean.data.countMap);
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
        return (ManagerListPresenter) doRequest("admin/seedling/statusCount", true, callBack);




    }








}
