package com.hldj.hmyg.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.hldj.hmyg.R;


/**
 *  呵呵
 *
 */

@SuppressLint("AppCompatCustomView")
public class DrawableCenterText extends TextView {


    public DrawableCenterText(Context context) {
        super(context);

    }

    public DrawableCenterText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public DrawableCenterText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private Drawable mDrawableLeft;
    private float drleftWidth = -1;
    private float drLeftHeight = -1;

    private void initView(Context context, AttributeSet attrs) {

        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mDrawableLeft = getCompoundDrawables()[0];


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ClearEditText_clear_left_width) {
                drleftWidth = typedArray.getDimension(attr, -1);
//                leftImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
            } else if (attr == R.styleable.ClearEditText_clear_left_height) {
                drLeftHeight = typedArray.getDimension(attr, -1);
            }

        }

        typedArray.recycle();
//        ClearEditText


    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (null != drawables) {
            Drawable drawableLeft = drawables[0];
            //文字宽度
            float textWidth = getPaint().measureText(getText().toString());
            if (null != drawableLeft) {
                setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                //内容区域
                float contentWidth = 0;
                if (drleftWidth != -1) {
                    contentWidth = textWidth + getCompoundDrawablePadding() + drleftWidth;
                } else {
                    contentWidth = textWidth + getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
                }

                Log.i("onDraw", "getCompoundDrawablePadding() =  " + getCompoundDrawablePadding() + "  drleftWidth= " + drleftWidth +" height = "+ drLeftHeight);
                //向X轴的正方向平移
                canvas.save();
                canvas.translate((getMeasuredWidth() - contentWidth) / 2, 0);


                if (drleftWidth != -1) {
                    mDrawableLeft.setBounds((int) ((getMeasuredWidth() - contentWidth) / 2), (int) ((getMeasuredHeight() - drLeftHeight) / 2), ((int) drleftWidth) +(int) ((getMeasuredWidth() - contentWidth) / 2), (int) ((getMeasuredHeight() + drLeftHeight) / 2));

                    canvas.restore();
                    /**
                     *
                     rect1.right = getWidth() - getPaddingRight();
                     rect1.left = getWidth() - getPaddingRight() - rightImage.getWidth();

                     Log.i("width", "onDraw: " + rightImage.getWidth());
                     //            ToastUtil.showLongToast(rightImage.getWidth() + "");
                     //            rect.left = rect.right - rightImage.getWidth();
                     rect1.top = 0 + mHeight / 2 - rightImage.getHeight() / 2;
                     ////            rect.top = mHeight / 3 - 3 ;
                     rect1.bottom = 0 + mHeight / 2 + rightImage.getHeight() / 2;
                     */
                }

            }
            Drawable drawableRight = drawables[2];


            if (null != drawableRight) {
                setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                //内容区域
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableRight.getIntrinsicWidth();
                //向X轴的正方向平移
                canvas.translate(-(getWidth() - contentWidth) / 2, 0);
            }

        }


        super.onDraw(canvas);
//        canvas.drawText("hello - world ", 0, (int) ((getHeight() - drLeftHeight) / 2), getPaint());


    }


    public Drawable getmDrawableLeft() {
        return mDrawableLeft;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
}