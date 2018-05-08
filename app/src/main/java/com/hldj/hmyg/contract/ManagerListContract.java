package com.hldj.hmyg.contract;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.StatusCountBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBeanData;

/**
 * // Todo
 */

public interface ManagerListContract {
    interface Model extends BaseModel {
        void getDatas(String page, String storeId, String nurseryId, String type, String status, String searchKey, ResultCallBack callBack);

        void getToDoListData(ResultCallBack callBack, String... args);

        void getCounts(ResultCallBack callBack);

        void getCounts2(ResultCallBack callBack, String nurseryId);

        void getTodoStatusCount(ResultCallBack callBack, String... args);


        void doDelete(String id, ResultCallBack callBack);
    }

    interface View extends BaseView {
        void initXRecycle(BPageGsonBean gsonBean);

        void initCounts(CountTypeGsonBean gsonBean);

        void initCounts2(SimpleGsonBeanData<StatusCountBean> gsonBean);

        void initTodoStatusCount(SimpleGsonBean gsonBean);

        void onDeled(boolean bo);
    }

    abstract class Presenter<M extends BaseModel, V extends BaseView> extends BasePresenter<M, V> {

        public abstract void onStart();

        public abstract void getData(String page, String storeId, String nurseryId, String type, String status, String searchKey);


        //        public abstract void getPendingData();
        public abstract void getToDoListData(ResultCallBack callBack, String... args);

        public abstract void getTodoStatusCount(ResultCallBack callBack, String... args);

        public abstract void getCounts();

        public abstract void getCounts2(String nurseryId);

        public abstract void doDelete(String id);


    }
}
