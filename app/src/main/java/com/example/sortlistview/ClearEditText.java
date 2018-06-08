package com.example.sortlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.hldj.hmyg.R;

public class ClearEditText extends AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    private Drawable mDrawableLeft;
    private Bitmap leftImage;

    private float drleftWidth = -1;
    private float drLeftHeight = -1;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
        initAttr(context, attrs);
        init();
    }


    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(context, attrs);
        init();


    }

    private void initAttr(Context context, AttributeSet attrs) {


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


    private void init() {


        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];


        if (mClearDrawable == null) {
            mClearDrawable = getResources()
                    .getDrawable(R.mipmap.search_delete);
//                    .getDrawable(R.drawable.clear_icon);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth() * 2 / 3, mClearDrawable.getIntrinsicHeight() * 2 / 3);
//        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());


        mDrawableLeft = getCompoundDrawables()[0];
        if (mDrawableLeft != null && drleftWidth != -1) {
            mDrawableLeft.setBounds(0, 0, ((int) drleftWidth), (int) drLeftHeight);
        }


        Log.i("ClearEditText", "drleftWidth = " + drleftWidth + "  drLeftHeight = " + drLeftHeight);
//        ToastUtil.showLongToast("drleftWidth = " + drleftWidth + "  drLeftHeight = " + drLeftHeight);

        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);


    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }


}
