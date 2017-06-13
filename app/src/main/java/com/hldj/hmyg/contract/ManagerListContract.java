package com.hldj.hmyg.contract;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;

/**
 * Created by Administrator on 2017/6/11 0011.
 */

public interface ManagerListContract {
    interface Model extends BaseModel {
        void getDatas(String page ,String status ,String searchKey,ResultCallBack callBack);
        void getCounts(ResultCallBack callBack);
        void doDelete(String id ,ResultCallBack callBack);
    }

    interface View extends BaseView {
        void initXRecycle(BPageGsonBean gsonBean);
        void initCounts(CountTypeGsonBean gsonBean);
        void onDeled(boolean bo);
    }

    public abstract static class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData(String page , String status,String searchKey  );


        public abstract void getCounts();

        public abstract void doDelete(String id);


    }
}
