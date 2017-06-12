package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hy.utils.ToastUtil;

/**
 * Created by Administrator on 2017/6/11 0011.
 */

public class ManagerListPresenter extends ManagerListContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String page, String status, String searchKey) {

        mModel.getDatas(page, status, searchKey, new ResultCallBack<BPageGsonBean>() {
            @Override
            public void onSuccess(BPageGsonBean gsonBean) {

                mView.initXRecycle(gsonBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showError(strMsg);
            }
        });
    }

    @Override
    public void getCounts() {
        mModel.getCounts(new ResultCallBack<CountTypeGsonBean>() {
            @Override
            public void onSuccess(CountTypeGsonBean gsonBean) {


                mView.initCounts(gsonBean);


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        });
    }

    @Override
    public void doDelete(String id) {
        mModel.doDelete(id, new ResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean b) {
                mView.onDeled(b);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                ToastUtil.showShortToast("删除失败："+strMsg);
                mView.onDeled(false);
            }
        });
    }
}
