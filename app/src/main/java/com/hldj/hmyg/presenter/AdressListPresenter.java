package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.contract.AdressListContract;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class AdressListPresenter extends AdressListContract.Presenter {
    @Override
    public void onStart() {

        if (context instanceof BaseMVPActivity) {
            ((BaseMVPActivity) context).showLoading();
        }

    }

    @Override
    public void getData(Serializable serializable) {
        mModel.getData(new ResultCallBack<List<AddressBean>>() {
            @Override
            public void onSuccess(List<AddressBean> been) {
                mView.initRecycle(been);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, serializable);

    }

    @Override
    public void deleteAddr(String addID) {
        mModel.deleteAddr(new ResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean boo) {
                mView.OnDeleteAddr(boo);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, addID);



    }

    @Override
    public void changeAddr(String addID) {
        mModel.changeAddr(new ResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean boo) {
                mView.OnChangeAddrAddr(boo);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, addID);

    }
}
