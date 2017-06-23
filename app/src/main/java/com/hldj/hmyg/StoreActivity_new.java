package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.model.StoreModel;
import com.hldj.hmyg.presenter.StorePresenter;
import com.hy.utils.ToastUtil;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

@SuppressLint("Override")
public class StoreActivity_new extends BaseMVPActivity<StorePresenter, StoreModel> implements StoreContract.View {


    @Override
    public int bindLayoutID() {
        return R.layout.activity_store_mvp;
    }

    @Override
    public void initView() {

        getView(R.id.btn_back).setOnClickListener(view -> finish());//后退

        CoreRecyclerView store_recycle = (CoreRecyclerView) findViewById(R.id.store_recycle);
        store_recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_cjgg) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        }, true).openLoadMore(getQueryBean().pageSize, page -> {
            getQueryBean().pageIndex = page + "";
            mPresenter.getData(getQueryBean());
//            store_recycle.onRefresh();
        });


        store_recycle.getAdapter().addData("1123456");


    }

    @Override
    public void initVH() {
        // 使用rx java  尝试嵌套 请求 网络

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    /**
     * 定义上传搜索对象
     */
    private QueryBean queryBean;

    private QueryBean getQueryBean() {
        if (queryBean == null) {
            queryBean = new QueryBean();
        }
        return queryBean;
    }

    private class QueryBean implements Serializable {
        public String ownerId = "";
        public String orderBy = "";
        public String plantTypes = "";
        public String firstSeedlingTypeId = "";
        public int pageSize = 10;
        public String pageIndex = "0";
    }


    String code = "";//请求商店 地id

    public void getExtral() {
        code = getIntent().getStringExtra("code");
    }

    /**
     * @param context
     * @param code    商店的  id
     */
    public static void start2Activity(Context context, String code) {
        Intent intent = new Intent(context, StoreActivity_new.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }


    @Override
    public void initStoreData(String json) {
        ToastUtil.showShortToast(json);
    }


    @Override
    public void showErrir(String erMst) {
        ToastUtil.showShortToast(erMst);
    }









}
