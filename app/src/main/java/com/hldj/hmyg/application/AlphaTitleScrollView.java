package com.hldj.hmyg.application;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class AlphaTitleScrollView extends ScrollView {

    public static final String TAG = "AlphaTitleScrollView";
    private int mSlop;

//    private View headView;

    public AlphaTitleScrollView(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AlphaTitleScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public AlphaTitleScrollView(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        // mSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        mSlop = 25;
//        Log.i(TAG, mSlop + "");
    }


    private LinearLayout toolbar;
    OnStateChange stateChange;

    /**
     * @param toolbar  头部布局
     * @param headView 标题
     */
    public void setTitleAndHead(LinearLayout toolbar, View headView, OnStateChange stateChange) {
        this.toolbar = toolbar;
        this.stateChange = stateChange;
        toolbar.getBackground().mutate().setAlpha(0);
//        toolbar.setAlpha(0);
//        ViewGroup.LayoutParams params =headView.getLayoutParams();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//
//            params.height = MyApplication.dp2px(MyApplication.getInstance(),24) ;
//            headView.setLayoutParams(params);
////            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,5);
////
////            headView.setLayoutParams(params);
////        this.headView = headView;
//
//        }else{
//            params.height = 0 ;
//        }
//        headView.setLayoutParams(params);

    }

    public interface OnStateChange {
        void onShow();

        void onHiden();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.i(TAG, mSlop + "");
        float headHeight = 500
                - toolbar.getMeasuredHeight();
        int alpha = (int) (((float) t / headHeight) * 255);
        if (alpha >= 255)
            alpha = 255;
        if (alpha <= mSlop)
            alpha = 0;
//        toolbar.getBackground().setAlpha(alpha);

        if (alpha >= 200) {//大于100  出现
            stateChange.onShow();
        }
        if (alpha < 150) {// 隐藏
            stateChange.onHiden();
        }
        toolbar.getBackground().mutate().setAlpha(alpha);
//        headView.getBackground().setAlpha(alpha);
//        toolbar.setBackgroundColor(android.R.color.black);

        super.onScrollChanged(l, t, oldl, oldt);
    }


    public void resetHeadAlpha() {
        toolbar.getBackground().mutate().setAlpha(1);
        toolbar = null;
    }
}
