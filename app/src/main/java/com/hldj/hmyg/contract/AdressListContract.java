package com.hldj.hmyg.contract;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public interface AdressListContract {
    interface Model extends BaseModel {
        void getData(ResultCallBack resultCallBack, Serializable object);

        void deleteAddr(ResultCallBack resultCallBack, String addID);

        void changeAddr(ResultCallBack resultCallBack, String addID);
    }

    interface View extends BaseView {
        void initRecycle(List<AddressBean> been);
//        void showErrir(Throwable tr , int erCode ,String erMsg);

        void OnDeleteAddr(boolean bo);

        void OnChangeAddrAddr(boolean bo);

        @Override
        void showErrir(String erMst);
    }

    public abstract static class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData(Serializable serializable);

        public abstract void deleteAddr(String addID);

        public abstract void changeAddr(String addID);

    }
}
