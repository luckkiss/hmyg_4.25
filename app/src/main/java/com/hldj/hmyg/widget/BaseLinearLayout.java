package com.hldj.hmyg.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 */

public abstract class BaseLinearLayout<T> extends LinearLayout {


    protected Context context;
    private View viewRoot;

    public BaseLinearLayout(Context context) {
        super(context);
        this.context = context;
        initView();

    }

    public BaseLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public BaseLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }


    protected BaseLinearLayout initView() {

        viewRoot = inflate(context, setContextView(), this);
        initViewHolder(viewRoot);

        return this;

    }

    protected void setText(TextView tv_text, String s) {
        tv_text.setText(s);
    }

    protected abstract int setContextView();


    public abstract BaseLinearLayout initViewHolder(View viewRoot);

    public abstract BaseLinearLayout setDatas(T t);


    public BaseLinearLayout addText(int resId, String msg) {

        return this;
    }

    public BaseLinearLayout addOnclick(int resId, OnClickListener onClickListener) {

        viewRoot.findViewById(resId).setOnClickListener(onClickListener);

        return this;
    }



}
