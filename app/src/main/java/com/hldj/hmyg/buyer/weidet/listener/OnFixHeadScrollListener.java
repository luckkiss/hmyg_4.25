package com.hldj.hmyg.buyer.weidet.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 滚动监听  实现recycleview  滚动悬停效果
 */

public class OnFixHeadScrollListener extends RecyclerView.OnScrollListener {


    private View fixHead;
    private int mFixHeight;

    private LinearLayoutManager layoutManager;

    private int mCurrentPosition = 0;

    public OnFixHeadScrollListener(View view, LinearLayoutManager layoutManager) {
        fixHead = view;
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // 每次滚动状态 改变的时候 都获取顶部 布局的高度
        mFixHeight = fixHead.getHeight();

    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        // 获取指定位置的 item 的item
        View view = layoutManager.findViewByPosition(mCurrentPosition + 1);

        if (view == null) {
            return;
        }

        // 开始重叠起来  并  向上顶的时候
        if (view.getTop() <= mFixHeight) {
            /**
             * 判断出顶部的顶部布局与上边距的距离,如果<=顶部布局的距离
             * 那么就让把顶部布局的Y轴距离向上偏移,已达到向上滚动的效果
             */

            fixHead.setY(-(mFixHeight - view.getTop()));


        } else {
            /**
             * 直接让顶部布局到顶部
             */
            fixHead.setY(0);
        }


        /**
         * 获取最上面完全可见 item 位置。判断是否第0位
         * 如果不是第0位，则让顶部布局置顶
         */

        if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
            mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
            fixHead.setY(0);
            //让顶部布局显示哪个图片
        }


    }


}
