package com.hldj.hmyg.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 正方形的  ImageView
 */

public class ShangjiImageView extends ImageView {

//     step 0 绘制 title   我的商机
    // step 1 绘制  text1
    // step 2 绘制  黑色 0.3 透明度 黑色背景
    // step 3 绘制  白色  文字 与 白色向右 图标  与 点击事件


    private String text1 = "为您匹配30 条求购";//为您匹配30 条求购
    private String text2 = "红花草 200株 > ";

    public ShangjiImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public ShangjiImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


}
