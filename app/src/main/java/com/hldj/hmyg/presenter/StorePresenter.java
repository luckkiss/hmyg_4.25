package com.hldj.hmyg.presenter;

import com.hldj.hmyg.contract.StoreContract;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class StorePresenter extends StoreContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String code) {


    }

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
