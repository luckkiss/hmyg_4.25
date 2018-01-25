package com.hldj.hmyg.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class IntegralItemRelative extends BaseRelativeLayout {


    private OnClickListener rightClick;

    public IntegralItemRelative(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setContextView() {
        return R.layout.activity_integral_item;
    }

    @Override
    public BaseRelativeLayout initViewHolder(View viewRoot) {
        return null;
    }

    @Override
    public BaseRelativeLayout setDatas(Object o) {
        return null;
    }


    public void setLeftIcon(@DrawableRes int res) {
        getHolder().left_icon.setImageResource(res);
    }

    public void setTopText(String str) {
        getHolder().tv_top.setText(str);
    }

    public void setBottomText(String str) {
        getHolder().tv_bottom.setText(str);
    }

    public void setRightTextStates(boolean flag, String status) {
//        getHolder().tv_right_text.setChecked(flag);
        if (flag) {
            //true   --- >  表示选中，灰色  不可点击
            getHolder().tv_right_text.setText("已完成");
            //red_btn_bg_color
            getHolder().tv_right_text.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.red_btn_bg_color));
            getHolder().tv_right_text.setCompoundDrawables(null, null, null, null);
//            getHolder().tv_right_text.setChecked(true);
            if (rightClick != null) {
                getHolder().tv_right_text.setOnClickListener(null);
            }
        } else {
            getHolder().tv_right_text.setText(status);
//            getHolder().tv_right_text.setChecked(false);
            getHolder().tv_right_text.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.text_color999));
            if (rightClick != null) {
                getHolder().tv_right_text.setOnClickListener(rightClick);
            }
        }
    }


    private ViewHolder mHolder;


    public ViewHolder getHolder() {
        if (mHolder == null) {
            mHolder = new ViewHolder(viewRoot);
        }
        return mHolder;
    }


    private class ViewHolder {
        public View rootView;
        public ImageView left_icon;
        public TextView tv_top;
        public TextView tv_bottom;
        public TextView tv_right_text;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.left_icon = (ImageView) rootView.findViewById(R.id.left_icon);
            this.tv_top = (TextView) rootView.findViewById(R.id.tv_top);
            this.tv_bottom = (TextView) rootView.findViewById(R.id.tv_bottom);
            this.tv_right_text = (TextView) rootView.findViewById(R.id.tv_right_text);
        }

    }
}
