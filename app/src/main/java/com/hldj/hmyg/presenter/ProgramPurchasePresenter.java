package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.ProgramPageGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseIndexGsonBean;
import com.hldj.hmyg.M.QuoteUserGroup;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hy.utils.ToastUtil;

import java.util.List;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public class ProgramPurchasePresenter extends ProgramPurchaseContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String page, String purchaseId, String sellerId) {

//        mModel.getDatas(page, purchaseId, sellerId, new ResultCallBack<ProgramPageGsonBean>() {
//            @Override
//            public void onSuccess(ProgramPageGsonBean gsonBean) {
//                mView.initXRecycle(gsonBean.data.list);
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                mView.showErrir(strMsg);
//            }
//        });
    }

    @Override
    public void getData(String page, String purchaseId, String sellerId, String state) {

        mModel.getDatas(page, purchaseId, sellerId,state ,new ResultCallBack<ProgramPageGsonBean>() {
            @Override
            public void onSuccess(ProgramPageGsonBean gsonBean) {
                mView.initXRecycle(gsonBean.data.list);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        });
    }


    @Override
    public void getDatasGys(String page, String proId, String searchKey,String status) {
        mModel.getDatasGys(page, proId, searchKey,status, new ResultCallBack<List<QuoteUserGroup>>() {
            @Override
            public void onSuccess(List<QuoteUserGroup> userGroups) {
                mView.initXRecycleGys(userGroups);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        });
    }

    @Override
    public void getIndexDatas(String purchaseId,String status) {
        mModel.getIndexDatas(new ResultCallBack<ProgramPurchaseIndexGsonBean.DataBean>() {
            @Override
            public void onSuccess(ProgramPurchaseIndexGsonBean.DataBean dataBean) {
                mView.initHeadDatas(dataBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, purchaseId,status);
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
