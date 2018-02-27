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

public class MyImageViewShowVideo extends ImageView {


    private Bitmap bitmap;
    Paint drawBitmapPaint;


    public MyImageViewShowVideo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initdr();
    }

    private void initdr() {
//        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_play_circle_outline_black_24dp);
        bitmap = getBitmap(getContext(), R.drawable.ic_play_circle_outline_black_24dp);

        //高度和宽度一样
        drawBitmapPaint = new Paint();
        drawBitmapPaint.setColor(Color.WHITE);
    }

    public MyImageViewShowVideo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @SuppressWarnings("unused")


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


//        int childWidthSize = getMeasuredWidth();


//        int childHeightSize = getMeasuredHeight();


        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }

    private static final String TAG = "MyImageView";

    //ic_play_circle_outline_black_24dp
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//      int bitmapWidth = bitmap.getWidth();
        Log.i(TAG, "onDraw: " + bitmap);
        canvas.drawBitmap(bitmap, getMeasuredWidth() / 2 - bitmap.getHeight() / 2, getMeasuredHeight() / 2 - bitmap.getHeight() / 2, drawBitmapPaint);
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
     Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
     if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
     drawable = (DrawableCompat.wrap(drawable)).mutate();
     }

     Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
     drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
     Canvas canvas = new Canvas(bitmap);
     drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
     drawable.draw(canvas);

     return bitmap;
     }
     */
}
