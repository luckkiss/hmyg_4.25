package com.hldj.hmyg.buyer.weidet.recyclerviewpager.measure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.hldj.hmyg.buyer.weidet.recyclerviewpager.RecyclerViewPager;

/**
 * Created by Administrator on 2017/5/5.
 */

public class MeasureRecycleView extends RecyclerViewPager {
    public MeasureRecycleView(Context context) {
        super(context);
    }

    public MeasureRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();

        setNestedScrollingEnabled(false);
    }
}
