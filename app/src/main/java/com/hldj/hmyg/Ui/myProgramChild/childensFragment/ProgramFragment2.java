package com.hldj.hmyg.Ui.myProgramChild.childensFragment;

import android.os.Handler;
import android.view.View;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

/**
 * 装车列表
 */

public class ProgramFragment2 extends BaseFragment implements View.OnClickListener {

    private CoreRecyclerView coreRecyclerView;

    @Override
    protected void initView(View rootView) {
        initListener(rootView);

        LoadingLayout loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loading_program2);
        loadingLayout.setStatus(LoadingLayout.Loading);
        coreRecyclerView = (CoreRecyclerView) rootView.findViewById(R.id.recycle_program2);
        coreRecyclerView.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_program_two) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        }, false);
        coreRecyclerView.getAdapter().addData("132465");
        coreRecyclerView.getAdapter().addData("132465");
        coreRecyclerView.getAdapter().addData("132465");
        coreRecyclerView.getAdapter().addData("132465");
        coreRecyclerView.getAdapter().addData("132465");
        coreRecyclerView.getAdapter().addData("132465");


        new Handler().postDelayed(() -> {
            loadingLayout.setStatus(LoadingLayout.Success);
        }, 3000);

    }


    public void initListener(View rootView) {
        rootView.findViewById(R.id.tv_yes_get).setOnClickListener(this);
        rootView.findViewById(R.id.tv_no_get).setOnClickListener(this);
    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_program2;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_no_get) {
            //未收货
            ToastUtil.showShortToast("未收货");
        } else if (view.getId() == R.id.tv_yes_get) {
            ToastUtil.showShortToast("已收货");
        }


    }
}
