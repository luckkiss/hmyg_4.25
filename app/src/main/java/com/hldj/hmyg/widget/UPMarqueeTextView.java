package com.hldj.hmyg.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.ArrayRes;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hldj.hmyg.R;

/**
 * 上翻 切换文字控件
 */

@SuppressLint("AppCompatCustomView")
public class UPMarqueeTextView extends TextView {


    private Animation fadeInAnimation, fadeOutAnimation;//切换文本动画

    private int duration = 2500;//切换时间,默认3秒

    private final int DEFAULT_DURATION = 2500;

    private CharSequence[] textContents = {"aa", "bb", "cc", "dd", "ee", "ff", "gg"};//文本内容

    private int textPosition = 0;//当前显示的文本角标

    private boolean isShown;//是否可见

    public UPMarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);

    }

    public UPMarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UPMarqueeTextView(Context context) {
        this(context, null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isShown = true;
    }

    @Override
    protected void onDetachedFromWindow() {//涉及动画，需要在detach方法停止不然会造成内存泄漏
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 设置文本内容
     *
     * @param texts
     */
    public void setTexts(String... texts) {
        if (texts.length < 1) {
            throw new IllegalArgumentException("This TextView's contents' length must more than 1");
        }
        textContents = texts;
    }

    /**
     * 设置文本内容
     *
     * @param resourceId
     */
    public void setTexts(@ArrayRes int resourceId) {
        String[] texts = getContext().getResources().getStringArray(resourceId);
        if (texts != null) {
            if (texts.length < 1) {
                throw new IllegalArgumentException("This TextView's contents' length must more than 1");
            }
            textContents = texts;
        }
    }

    /**
     * 设置持续时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        if (duration < 1) {
            throw new IllegalArgumentException("duration must more than 1");
        }
        this.duration = duration;
    }

    /**
     * 获取设置的文本内容
     *
     * @return
     */
    public CharSequence[] getTextContents() {
        return textContents;
    }

    /**
     * 开始
     */
    public void start(String string) {

//        if (isAniming)
//        {
//            return;
//        }
//        setText(textContents[textPosition]);
        setText(string);
        startAnimation(fadeInAnimation);
        postDelayed(animRunnable, duration);
    }

    /**
     * 停止
     */
    private void stop() {
        if (getAnimation() != null) {
            getAnimation().cancel();
        }
        isShown = false;
        removeCallbacks(animRunnable);
    }


    public boolean isAniming = false ;
    private final Runnable animRunnable = new Runnable() {
        @Override
        public void run() {
            isAniming= true ;
            startAnimation(fadeOutAnimation);
            getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isAniming = false ;
                    if (isShown) {
                        textPosition = (textPosition == (textContents.length - 1)) ? 0 : (textPosition + 1);

                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    };
}
