package com.hldj.hmyg.contract;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public interface StoreContract {

    public interface View extends BaseView {
        void initStoreData(String json);

        void showError(Throwable r ,int errorCode,String errorMsg);
    }

    public interface Model extends BaseModel {
        void getData(ResultCallBack resultCallBack);
    }

    public abstract static class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData(String url);


    }
}
