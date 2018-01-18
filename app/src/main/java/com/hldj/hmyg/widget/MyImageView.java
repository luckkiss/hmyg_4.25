package com.hldj.hmyg.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 正方形的  ImageView
 */

public class MyImageView extends ImageView {


    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressWarnings("unused")


    @Override


    protected void
    onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


//        int childWidthSize = getMeasuredWidth();


//        int childHeightSize = getMeasuredHeight();

        //高度和宽度一样

        super.onMeasure(widthMeasureSpec,  widthMeasureSpec);

    }


}
