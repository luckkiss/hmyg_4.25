package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.contract.MyProgramContract;
import com.hldj.hmyg.model.MyProgramGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hy.utils.ToastUtil;

/**
 * Created by Administrator on 2017/6/11 0011.
 */

public class MyProgramPresenter extends MyProgramContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getData(String page,  String searchKey) {

        mModel.getDatas(page, searchKey, new ResultCallBack<MyProgramGsonBean>() {
            @Override
            public void onSuccess(MyProgramGsonBean gsonBean) {
                if (gsonBean.code.equals(ConstantState.SUCCEED_CODE))
                {
                    mView.initXRecycle(gsonBean.data.page.data);
                }else {
                    mView.showErrir(gsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
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
