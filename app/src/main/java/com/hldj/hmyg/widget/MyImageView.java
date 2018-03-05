package com.hldj.hmyg.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.hldj.hmyg.R;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


//        int childWidthSize = getMeasuredWidth();


//        int childHeightSize = getMeasuredHeight();


        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }




}
