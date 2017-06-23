package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.contract.StoreContract;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class StorePresenter extends StoreContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(Serializable serializable) {

        mModel.getData(new ResultCallBack() {
            @Override
            public void onSuccess(Object o) {
                    mView.initStoreData(o.toString());
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                    mView.showErrir(strMsg);
            }
        }, serializable);

    }

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

    private void storeIndex(String code) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", code);
        finalHttp.post(GetServerUrl.getUrl() + "store/index", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onStart() {

                        super.onStart();
                    }

                    @Override
                    public void onSuccess(Object t) {

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {

                    }

                });

    }


}
