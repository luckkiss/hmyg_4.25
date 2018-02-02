package com.hldj.hmyg.presenter;

import android.util.Log;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class StorePresenter extends StoreContract.Presenter {

    public int count;

    @Override
    public void onStart() {

    }


    @Override
    public Observable<String> getIndexData() {
        Log.i("===1", "subscribe: " + Thread.currentThread().getName());
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                Log.i("===2", "subscribe: " + Thread.currentThread().getName());
                mModel.getIndexData(new ResultCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {

                        StoreGsonBean storeGsonBean = GsonUtil.formateJson2Bean(json, StoreGsonBean.class);
                        if (storeGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                            mView.initIndexBean(storeGsonBean.data);

                            e.onNext(storeGsonBean.data.owner.id);

                            count = storeGsonBean.data.momentsCount;

                        } else {
                            mView.showErrir(storeGsonBean.msg);
                        }

                        mView.hindLoading();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        mView.showErrir(strMsg);
                        mView.hindLoading();
                    }
                }, mView.getStoreID());
            }
        })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
//                .subscribeOn(Schedulers.io());


//        mView.showLoading();


    }


    // 通过 view 层 获取 数据
    @Override
    public void getData() {
        mView.showLoading();
        mModel.getData(new ResultCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                BPageGsonBean bPageGsonBean = GsonUtil.formateJson2Bean(json, BPageGsonBean.class);
                if (bPageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    mView.initStoreData(bPageGsonBean.data.page.data);
                } else {
                    mView.showErrir(bPageGsonBean.msg);
                }

                mView.hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                mView.showErrir(strMsg);
                mView.hindLoading();
            }
        }, mView.getQueryBean());

    }

    public Observable<String> getDataRx(Serializable serializable) {

        return (Observable<String>) Observable.just("hellowWord")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                        mModel.getData(new ResultCallBack() {
                            @Override
                            public void onSuccess(Object o) {

                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                mView.showErrir(strMsg);
                            }
                        }, serializable);
                    }
                });


        /**
         *   Observable.create(new ObservableOnSubscribe<String>() {
        @Override public void subscribe(ObservableEmitter<String> e) throws Exception {


        }
        });
         */
    }


//    private void initData() {
//        BasePresenter bPresenter = new BPresenter()
//                .putParams(getQueryBean())//传一个对象进去
//                .addResultCallBack(new ResultCallBack<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>() {
//                    @Override
//                    public void onSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> pageBean) {
//                        recyclerView1.selfRefresh(false);
//                        D.e("==============");
//                        recyclerView1.getAdapter().addData(pageBean);
//
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        D.e("==============");
//                        recyclerView1.selfRefresh(false);
//                    }
//                });
//        ((BPresenter) bPresenter).getDatas("seedling/list", false);
//    }

    private void storeIndex(String code) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", code);
        finalHttp.post(GetServerUrl.getUrl() + "store/index", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onStart() {

                        super.onStart();
                    }

                    @Override
                    public void onSuccess(Object t) {

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {

                    }

                });

    }


}
