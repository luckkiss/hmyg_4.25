package com.hldj.hmyg.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 基础 RelativeLayout  集成后  直接写   id  进行自定义布局
 */

public abstract class BaseRelativeLayout<T> extends RelativeLayout {


    protected Context context;
    protected View viewRoot;

    public BaseRelativeLayout(Context context) {
        super(context);
        this.context = context;
        initView();

    }

    public BaseRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public BaseRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }


    protected BaseRelativeLayout initView() {

        viewRoot = inflate(context, setContextView(), this);
        initViewHolder(viewRoot);

        return this;

    }

    protected void setText(TextView tv_text, String s) {
        tv_text.setText(s);
    }

    protected abstract int setContextView();


    public abstract BaseRelativeLayout initViewHolder(View viewRoot);

    public abstract BaseRelativeLayout setDatas(T t);


    public BaseRelativeLayout addText(int resId, String msg) {

        return this;
    }

    public BaseRelativeLayout addOnclick(int resId, OnClickListener onClickListener) {

        viewRoot.findViewById(resId).setOnClickListener(onClickListener);

        return this;
    }


}
