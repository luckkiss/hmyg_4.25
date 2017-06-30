package com.hldj.hmyg.contract;

import android.view.ViewGroup;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.model.MyProgramGsonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/11 0011.
 */

public interface MyProgramContract {
    interface Model extends BaseModel {
        void getDatas(String page, String searchKey, ResultCallBack callBack);
        void getCounts(ResultCallBack callBack);
        void doDelete(String id, ResultCallBack callBack);
    }

    interface View extends BaseView {
        void initXRecycle(List<MyProgramGsonBean.DataBeanX.PageBean.DataBean> gsonBean);
        void initCounts(CountTypeGsonBean gsonBean);
        void onDeled(boolean bo);
        String getSearchText();
        ViewGroup getContentView();
    }

    public abstract static class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData(String page ,String searchKey  );


        public abstract void getCounts();

        public abstract void doDelete(String id);


    }
}
