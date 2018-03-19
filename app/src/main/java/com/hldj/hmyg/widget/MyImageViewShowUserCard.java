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

import com.hldj.hmyg.R;

/**
 * 正方形的  ImageView
 */

public class MyImageViewShowUserCard extends android.support.v7.widget.AppCompatImageView {


    private Bitmap bitmap;
    Paint drawBitmapPaint;


    public boolean isAgain = false;

    public MyImageViewShowUserCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    private void initdr() {
//        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_play_circle_outline_black_24dp);

//        Bitmap bitmap = new ColorDrawable( R.color.popWinBgColor).;

        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        bitmap.eraseColor(Color.parseColor("#66000000"));//填充颜色

//        bitmap = getBitmap(getContext(), R.color.popWinBgColor);

        //高度和宽度一样
        drawBitmapPaint = new Paint();
        drawBitmapPaint.setColor(Color.BLUE);
    }

    public MyImageViewShowUserCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @SuppressWarnings("unused")


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int childWidthSize = getMeasuredWidth();


//        int childHeightSize = getMeasuredHeight();


        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        initdr();

    }

    private static final String TAG = "MyImageView";

    //ic_play_circle_outline_black_24dp
    @Override
    protected void onDraw(Canvas canvas) {


        if (isAgain) {
            canvas.drawBitmap(bitmap, 0, 0, drawBitmapPaint);
        }

        super.onDraw(canvas);
//      int bitmapWidth = bitmap.getWidth();
        Log.i(TAG, "onDraw: " + bitmap);


    }

    public int dp(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }


    /**
     * public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
     * Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
     * if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
     * drawable = (DrawableCompat.wrap(drawable)).mutate();
     * }
     * <p>
     * Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
     * drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
     * Canvas canvas = new Canvas(bitmap);
     * drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
     * drawable.draw(canvas);
     * <p>
     * return bitmap;
     * }
     */

    /* 没有认证的时候  显示的  图层   只显示  src  图片    */
    public void showUp() {
        this.isAgain = false;
        invalidate();

        setBackground(null);

        this.setImageResource(R.drawable.add_image_icon_big_eq);

        setPadding(0, 0, 0, 0);

    }


    /* 背景图片  由外部 设置  已经加载的图片 */
    public void showUpAgain() {
        /* 添加半透明   的  遮盖 */
        this.isAgain = true;

        /* 设置 重新上传图标   */
        this.setImageResource(R.drawable.chongxinshangchuan);


        invalidate();


        setPadding(dp(16), dp(16), dp(16), dp(16));


    }


}
