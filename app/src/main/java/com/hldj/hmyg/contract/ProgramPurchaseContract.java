package com.hldj.hmyg.contract;

import android.view.ViewGroup;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseExpanBean;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.saler.M.PurchaseBean;

import java.util.List;

/**
 * Created by 罗擦擦 on 2017/6/11 0011.
 */

public interface ProgramPurchaseContract {
    interface Model extends BaseModel {
        void getDatas(String page, String searchKey, ResultCallBack callBack);

        void getHeadDatas(ResultCallBack callBack,String purchaseId);
        void doDelete(String id, ResultCallBack callBack);
    }

    interface View extends BaseView {
        void initXRecycle(List<ProgramPurchaseExpanBean> gsonBean);
        void initCounts(CountTypeGsonBean gsonBean);
        void onDeled(boolean bo);
        void initHeadDatas(PurchaseBean purchaseBean);
        String getSearchText();
        ViewGroup getContentView();
    }

    public abstract static class Presenter extends BasePresenter<Model, View> {

        public abstract void onStart();

        public abstract void getData(String page ,String searchKey  );


        public abstract void getIndexDatas(String purchaseId);

        public abstract void doDelete(String id);


    }
}
