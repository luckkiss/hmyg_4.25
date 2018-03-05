package com.hldj.hmyg.contract;

import android.support.v4.view.ViewPager;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.QueryBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.bean.StoreGsonBean;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public interface StoreContract {

    interface View extends BaseView {
        void initStoreData(List<BPageGsonBean.DatabeanX.Pagebean.Databean> bPageGsonBean);
        void initIndexBean(StoreGsonBean.DataBean indexBean);
        QueryBean getQueryBean ();
        String getStoreID();
        ViewPager getViewPager();

    }

    interface Model extends BaseModel {
        void getData(ResultCallBack resultCallBack ,Serializable serializable);
        void getIndexData(ResultCallBack resultCallBack ,String string);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData();

        public abstract Observable getIndexData();


    }
}
