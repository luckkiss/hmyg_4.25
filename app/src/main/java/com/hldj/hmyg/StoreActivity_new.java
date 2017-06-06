package com.hldj.hmyg;

import android.annotation.SuppressLint;

import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.Rx.JumpUtil;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.model.StoreModel;
import com.hldj.hmyg.presenter.StorePresenter;
import com.hy.utils.ToastUtil;

@SuppressLint("Override")
public class StoreActivity_new extends BaseMVPActivity<StorePresenter, StoreModel> implements JumpUtil.JumpInterface {

    @Override
    public void initView() {
        ToastUtil.showShortToast("hhhhhhhhhhhh");

        CoreRecyclerView store_recycle = (CoreRecyclerView) findViewById(R.id.store_recycle);
        store_recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_cjgg) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });

        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");
        store_recycle.getAdapter().addData("1123456");



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


}
