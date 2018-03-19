package com.hldj.hmyg.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hldj.hmyg.R;


/**
 * 身份证 验证 view
 */

public class StepView extends View {

    private static final String TAG = "StepView";

    private Paint greenPaint;
    private Paint grayPaint;

    private int mTextSize = 15;
    private int mStrokenWidth =  3;

    private Paint cycleGreenPaint;
    private Paint cycleGrayPaint;

    private int mMainColor;

    private int banjin = -1;
    private int centerY;/* y 中轴大小  */
    private int centerX;/* x 中轴大小  */

    /*步骤1 圆  的 →点*/
    private Point point_step1_right;
    /*步骤1 圆心  点*/
    private Point point_step1_center = new Point();


    private Point point_step1_center_step2;

    private Point point_step2_left;
    private Point point_step2_center = new Point();
    private Point point_step2_right;


    private Point point_step2_center_step3;

    private Point point_step3_left;
    private Point point_step3_center = new Point();
    private int step = 0;
    private Rect rect;
    private Rect innerRect;
    private Rect innerRect1;
    private Paint paint1;
    private Paint paint2;
    private Rect innerRect2;


    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttrs(context, attrs);
        initPaints();

    }

    private void initPaints() {
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(mStrokenWidth);
        greenPaint.setColor(mMainColor);


        grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        grayPaint.setStyle(Paint.Style.STROKE);
        grayPaint.setStrokeWidth(mStrokenWidth);
        grayPaint.setColor(Color.GRAY);


        cycleGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cycleGreenPaint.setStyle(Paint.Style.STROKE);
        cycleGreenPaint.setStrokeWidth(mStrokenWidth);
        cycleGreenPaint.setColor(mMainColor);

        cycleGrayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cycleGrayPaint.setStyle(Paint.Style.STROKE);
        cycleGrayPaint.setStrokeWidth(mStrokenWidth);
        cycleGrayPaint.setColor(Color.GRAY);


    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StepView);


        step = a.getInteger(R.styleable.StepView_xx_step, 1);
        mMainColor = a.getColor(R.styleable.StepView_xx_main_color, Color.GREEN);
//      mTextSize = a.getColor(R.styleable.StepView_textSize, );
        mTextSize = a.getDimensionPixelSize(R.styleable.StepView_xx_textSize, mTextSize);

        a.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//      banjin = getMeasuredHeight() / 3 ;
        banjin = MeasureSpec.getSize(heightMeasureSpec) / 5;

        centerY = getMeasuredHeight() / 3;
        centerX = getMeasuredWidth() / 2;


        point_step1_center.x = centerX / 4;
        point_step1_center.y = centerY;

        point_step2_center.x = centerX;
        point_step2_center.y = centerY;

        point_step3_center.x = centerX * 3 / 4 + centerX;
        point_step3_center.y = centerY;

        rect = new Rect();
        greenPaint.getTextBounds("1", 0, 1, rect);
        Log.i(TAG, "onMeasure: ");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        greenPaint.setTextSize(mTextSize);
        grayPaint.setTextSize(mTextSize);

        cycleGreenPaint.setTextSize(mTextSize);
        cycleGrayPaint.setTextSize(mTextSize);


        grayPaint.setStyle(Paint.Style.FILL);
        greenPaint.setStyle(Paint.Style.FILL);


        if (step == 1) {
            doStep1(canvas, cycleGreenPaint, greenPaint);
            doStep2(canvas, cycleGrayPaint, grayPaint);
            doStep3(canvas, cycleGrayPaint, grayPaint);
        } else if (step == 2) {
            doStep1(canvas, cycleGreenPaint, greenPaint);
            doStep2(canvas, cycleGreenPaint, greenPaint);
            doStep3(canvas, cycleGrayPaint, grayPaint);
        } else if (step == 3) {
            doStep1(canvas, cycleGreenPaint, greenPaint);
            doStep2(canvas, cycleGreenPaint, greenPaint);
            doStep3(canvas, cycleGreenPaint, greenPaint);
        } else if (step == 0) {
            doStep1(canvas, cycleGrayPaint, grayPaint);
            doStep2(canvas, cycleGrayPaint, grayPaint);
            doStep3(canvas, cycleGrayPaint, grayPaint);
        }








        /* 画 线  1 -----      2 */
//        canvas.drawLine(point_step1_center.x + banjin,
//                centerY,
//                centerX - banjin
//                ,
//                centerY
//                ,
//                greenPaint);











        /* 画线  1 -- --  2  -- --  3 */
//        canvas.drawLine(centerX + banjin,
//                centerY,
//                centerX / 2 + centerX - banjin
//                ,
//                centerY
//                ,
//                grayPaint);


//         /* 画线3 */
//        canvas.drawLine(centerX + banjin,
//                centerY,
//                centerX / 2 + centerX - banjin
//                ,
//                centerY
//                ,
//                grayPaint);


    }


    private void doStep1(Canvas canvas, Paint cyclePaint, Paint paint) {

         /* 画 圆  1   */
        canvas.drawCircle(point_step1_center.x, point_step1_center.y, banjin, cyclePaint);

        innerRect = new Rect();
        paint.getTextBounds("1", 0, 1, innerRect);
        canvas.drawText("1", point_step1_center.x - innerRect.width(), point_step1_center.y + innerRect.width(), paint);

        innerRect1 = new Rect();
        paint1 = new Paint(paint);
        paint1.setTextSize(mTextSize);
        paint1.getTextBounds("发起认证", 0, 4, innerRect1);

        canvas.drawText("发起认证", point_step1_center.x - innerRect1.width() / 2, point_step1_center.y + innerRect1.height() + banjin + 10, paint1);

//        canvas.drawRect((innerRect1.left + 50), innerRect1.top + 50, innerRect1.right + 50, innerRect1.bottom + 50, cycleGreenPaint);


          /*0 -  0.5  */
        drawLineByState(canvas
                , paint
                , new Point(point_step1_center.x + banjin, centerY)
                , new Point((point_step1_center.x + point_step2_center.x) / 2, centerY)
                , 1
        );
    }

    private void doStep2(Canvas canvas, Paint cyclePaint, Paint paint) {
         /* 画 圆  2   */
        canvas.drawCircle(point_step2_center.x, point_step2_center.y, banjin, cyclePaint);

        canvas.drawText("2", point_step2_center.x - innerRect.width(), point_step2_center.y + innerRect.width(), paint);
        innerRect2 = new Rect(innerRect1);

        paint1.setColor(paint.getColor());
        paint1.getTextBounds("审核中", 0, 3, innerRect2);
        canvas.drawText("审核中", point_step2_center.x - innerRect2.width() / 2, point_step2_center.y + innerRect2.height() + banjin + 10, paint1);


        /* 0.5  1   */
        drawLineByState(canvas
                , paint
                , new Point((point_step1_center.x + point_step2_center.x) / 2, centerY)
                , new Point(point_step2_center.x - banjin, centerY)
                , 2
        );

        /* 1 -- 1.5   */
        drawLineByState(canvas
                , paint
                , new Point(centerX + banjin, centerY)
                , new Point((point_step2_center.x + point_step3_center.x) / 2, centerY)
                , 3
        );

    }

    private void doStep3(Canvas canvas, Paint cyclePaint, Paint paint) {
         /* 画 圆  3   */
        canvas.drawCircle(point_step3_center.x, point_step3_center.y, banjin, cyclePaint);

        canvas.drawText("3", point_step3_center.x - innerRect.width(), point_step3_center.y + innerRect.width(), paint);
        paint1.setColor(paint.getColor());
        canvas.drawText("认证完成", point_step3_center.x - innerRect1.width() / 2, point_step3_center.y + innerRect1.height() + banjin + 10, paint1);


          /* 1.5 --  2   */
        drawLineByState(canvas
                , paint
                , new Point((point_step2_center.x + point_step3_center.x) / 2, centerY)
                , new Point(point_step3_center.x - banjin, centerY)
                , 4
        );
    }

    /**
     * @param canvas
     * @param paint
     * @param start
     * @param end
     * @param lineNum line 的编号    1 --  4
     */
    private void drawLineByState(Canvas canvas, Paint paint, Point start, Point end, int lineNum) {

        /* 根据step 来  选择画笔 */


        canvas.drawLine(start.x,
                centerY,
                end.x,
                centerY,
                paint);


    }


    public void setStep(int step) {
        if (this.step == step) return;
        this.step = step;
        invalidate();
    }

}
