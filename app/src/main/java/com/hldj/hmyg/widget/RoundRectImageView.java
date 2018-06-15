package com.hldj.hmyg.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 带白色边框 的圆角 矩形 图片显示控件  找不到就自己写
 */

public class RoundRectImageView extends AppCompatImageView {

    private Paint paint;
    private Bitmap borderIMage;
    private RectF r1;
    private Path path;

    public RoundRectImageView(Context context) {
        super(context);
        init();
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public RoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
//        borderIMage = BitmapFactory.decodeResource(getResources(), R.drawable.zheng_white);
//新建矩形r1
        r1 = new RectF();
        r1.left = 0;
        r1.right = getMeasuredWidth();
        r1.top = 0;
        r1.bottom = getMeasuredHeight();

        setPadding(6,3,6,3);


        /**
         *   p.setStyle(Paint.Style.FILL);//充满
         p.setColor(Color.LTGRAY);
         p.setAntiAlias(true);// 设置画笔的锯齿效果
         canvas.drawText("画圆角矩形:", 10, 260, p);
         RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
         canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径

         */

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r1.right = getMeasuredWidth();
        r1.bottom = getMeasuredHeight();
//        canvas.drawRoundRect(r1, 20, 20, paint);
        canvas.drawRoundRect(r1, 18, 18, paint);

    }
}
