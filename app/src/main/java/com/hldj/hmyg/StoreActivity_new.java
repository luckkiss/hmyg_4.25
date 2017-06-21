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

@SuppressLint("Override")
public class StoreActivity_new extends BaseMVPActivity<StorePresenter, StoreModel> implements StoreContract.View {

    @Override
    public void initView() {

        getView(R.id.btn_back).setOnClickListener(view -> finish());//后退

        CoreRecyclerView store_recycle = (CoreRecyclerView) findViewById(R.id.store_recycle);
        store_recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_cjgg) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });

        store_recycle.getAdapter().addData("1123456");


        mPresenter.getData(code);










    }

    @Override
    public void initVH() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_store_mvp;
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

    }



    @Override
    public void showErrir(String erMst) {

    }
}
