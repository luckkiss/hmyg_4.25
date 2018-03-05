package com.hldj.hmyg.model;

import android.util.Log;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.saler.P.BasePresenter;

import net.tsz.afinal.http.AjaxCallBack;

import java.io.Serializable;

/**
 * Created by luocaca on 2017/6/22
 */

public class StoreModel extends BasePresenter implements StoreContract.Model {


    @Override
    public void getData(ResultCallBack resultCallBack, Serializable serializable) {

        putParams(serializable);
        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                resultCallBack.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        };


//        ToastUtil.showShortToast("执行在线程---"+ );
        doRequest("seedling/list", false, ajaxCallBack);


    }

    @Override
    public void getIndexData(ResultCallBack resultCallBack, String string) {

        putParams("id", string);
        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                resultCallBack.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, 0, strMsg);
            }
        };
        Log.i("===3", "subscribe: "+ Thread.currentThread().getName() );
        doRequest("store/index", false, ajaxCallBack);
    }


    /**
     *  try {
     StoreGsonBean storeGsonBean = GsonUtil.formateJson2Bean(json, StoreGsonBean.class);
     if (storeGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
     resultCallBack.onSuccess(storeGsonBean.data);
     } else {
     resultCallBack.onFailure(null, -1, storeGsonBean.msg);
     }
     } catch (Exception e) {
     D.e("==报错了===" + e.getMessage());
     resultCallBack.onFailure(e, 0, e.getMessage());
     }
     */
    /**
     *   AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
    @Override public void onSuccess(String json) {
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

    @Override public void onFailure(Throwable t, int errorNo, String strMsg) {
    rslBack.onFailure(t, errorNo, strMsg);
    }
    };
     //       putParams("pageIndex", strs[0] + "");
     //        putParams("pageSize", strs[1] + "");
     //        putParams("type", strs[2] + "");
     //        putParams("searchKey", strs[3] + "");
     putParams(strs);

     doRequest("admin/nursery/list", true, ajaxCallBack);
     */

    //    private void initData() {
//        BasePresenter bPresenter = new BPresenter()
//                .putParams(getQueryBean())//传一个对象进去
//                .addResultCallBack(new ResultCallBack<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>() {
//                    @Override
//                    public void onSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> pageBean) {
//                        recyclerView1.selfRefresh(false);
//                        D.e("==============");
//                        recyclerView1.getAdapter().addData(pageBean);
//
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        D.e("==============");
//                        recyclerView1.selfRefresh(false);
//                    }
//                });
//        ((BPresenter) bPresenter).getDatas("seedling/list", false);
//    }
}
