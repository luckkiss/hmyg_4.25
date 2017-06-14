package com.hldj.hmyg.model;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.AddressBeanGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.contract.AdressListContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.io.Serializable;

import static com.hldj.hmyg.util.GsonUtil.formateJson2Bean;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class AdressListModel extends BasePresenter implements AdressListContract.Model {

    @Override
    public void getData(ResultCallBack rslBack, Serializable strs) {

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("=======json==========" + json);
                AddressBeanGsonBean addressBeanGsonBean = formateJson2Bean(json, AddressBeanGsonBean.class);
                try {
                    if (addressBeanGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        rslBack.onSuccess(addressBeanGsonBean.data.page.data);
                    } else {
                        rslBack.onFailure(null, 0, addressBeanGsonBean.msg);
                    }
                } catch (Exception e) {
                    D.e("==报错了===" + e.getMessage());
                    rslBack.onFailure(null, 0, addressBeanGsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                rslBack.onFailure(t, errorNo, strMsg);
            }
        };
//       putParams("pageIndex", strs[0] + "");
//        putParams("pageSize", strs[1] + "");
//        putParams("type", strs[2] + "");
//        putParams("searchKey", strs[3] + "");
        putParams(strs);

        doRequest("admin/nursery/list", true, ajaxCallBack);


//        finalHttp.post(GetServerUrl.getUrl() + "admin/nursery/list", params,


    }

    @Override
    public void deleteAddr(ResultCallBack resultCallBack, String addID) {

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {

                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    resultCallBack.onSuccess(true);
                } else {
                    resultCallBack.onSuccess(false);
                    resultCallBack.onFailure(null, 0, simpleGsonBean.msg);
                }
            }
        };
        putParams("id", addID);
        doRequest("admin/nursery/delete", true, ajaxCallBack);

    }

    @Override
    public void changeAddr(ResultCallBack resultCallBack, String addID) {
        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {

                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    resultCallBack.onSuccess(true);
                } else {
                    resultCallBack.onSuccess(false);
                    resultCallBack.onFailure(null, 0, simpleGsonBean.msg);
                }

            }
        };
        putParams("id", addID);
        doRequest("admin/nursery/setDefault", true, ajaxCallBack);
    }


//    admin/nursery/setDefault


}
