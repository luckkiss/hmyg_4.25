package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.StatusCountBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBeanData;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.model.ManagerListModel;
import com.hy.utils.ToastUtil;

/**
 *
 */

public class ManagerListPresenter extends ManagerListContract.Presenter<ManagerListModel, ManagerListContract.View> {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String page, String storeId, String nurseryId, String type, String status, String searchKey) {

        mModel.getDatas(page, storeId, nurseryId, type, status, searchKey, new ResultCallBack<BPageGsonBean>() {
            @Override
            public void onSuccess(BPageGsonBean gsonBean) {

                mView.initXRecycle(gsonBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        });
    }

    @Override
    public void getToDoListData(ResultCallBack callBack, String... args) {
        mModel.getToDoListData(new ResultCallBack<BPageGsonBean>() {
            @Override
            public void onSuccess(BPageGsonBean gsonBean) {

                mView.initXRecycle(gsonBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, args);


    }

    @Override
    public void getTodoStatusCount(ResultCallBack callBack, String... args) {
        mModel.getTodoStatusCount(new ResultCallBack<SimpleGsonBean>() {
            @Override
            public void onSuccess(SimpleGsonBean gsonBean) {

                mView.initTodoStatusCount(gsonBean);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }, args);


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
    public void getCounts2(String nurseryId) {
        mModel.getCounts2(new ResultCallBack<SimpleGsonBeanData<StatusCountBean>>() {
            @Override
            public void onSuccess(SimpleGsonBeanData<StatusCountBean> gsonBean) {


                mView.initCounts2(gsonBean);


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }, nurseryId);
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
                ToastUtil.showShortToast("删除失败：" + strMsg);
                mView.onDeled(false);
            }
        });
    }
}
