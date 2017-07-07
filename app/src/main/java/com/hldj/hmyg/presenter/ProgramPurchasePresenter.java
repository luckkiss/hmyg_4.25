package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.ProgramPageGsonBean;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hy.utils.ToastUtil;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public class ProgramPurchasePresenter extends ProgramPurchaseContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String page, String purchaseId ,String searchKey) {

        mModel.getDatas(page, purchaseId ,searchKey, new ResultCallBack<ProgramPageGsonBean>() {
            @Override
            public void onSuccess(ProgramPageGsonBean gsonBean) {
                mView.initXRecycle(gsonBean.data.page.data);
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        });
    }

    @Override
    public void getIndexDatas(String purchaseId) {
        mModel.getIndexDatas(new ResultCallBack<PurchaseBean>() {
            @Override
            public void onSuccess(PurchaseBean gsonBean) {
                mView.initHeadDatas(gsonBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
            }
        }, purchaseId);
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
