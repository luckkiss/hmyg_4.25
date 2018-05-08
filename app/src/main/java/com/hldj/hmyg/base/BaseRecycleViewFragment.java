package com.hldj.hmyg.base;

import android.view.View;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 懒加载  fragment
 */

public abstract class BaseRecycleViewFragment<T> extends BaseLazyFragment {


    public CoreRecyclerView mCoreRecyclerView;

    private int pageSize = 20;

    @Override
    protected void onFragmentVisibleChange(boolean b) {

    }

    @Override
    protected void onFragmentFirstVisible() {

        mCoreRecyclerView = mRootView.findViewById(R.id.recycle);

        mCoreRecyclerView.init(new BaseQuickAdapter<T, BaseViewHolder>(bindRecycleItemId()) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {

                doConvert(helper, item, mActivity);

            }
        })
                .openRefresh()
                .openLoadMore(pageSize, page -> {
                    doRefreshRecycle(page + "");
                });
        onRecycleViewInited(mCoreRecyclerView);

        mCoreRecyclerView.onRefresh();


    }

    @Override
    protected void initView(View rootView) {


    }

    protected abstract void doRefreshRecycle(String page);

    /* 初始化完毕  可以在此处 对  recycle  进行   添加别的属性 */
    protected abstract void onRecycleViewInited(CoreRecyclerView coreRecyclerView);


    /* 执行recycle   绘制 */
    protected abstract void doConvert(BaseViewHolder helper, T item, NeedSwipeBackActivity mActivity);


    /* 绑定 recycle   item  先定制一个  后期可以多个*/
    public abstract int bindRecycleItemId();

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_base_recycle_view;
    }


}
