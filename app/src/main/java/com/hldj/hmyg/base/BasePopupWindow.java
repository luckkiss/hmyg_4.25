package com.hldj.hmyg.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hldj.hmyg.R;

/**
 * create by 罗擦擦   建造者模式初体验 ，参考dagger2 commponent 的自动生成代码
 */

public class BasePopupWindow extends PopupWindow {


    private BasePopupWindow(Builder builder) {
        initWithBuilder(builder);
    }

    private void initWithBuilder(Builder builder) {

        if (builder.layoutId == 0) {
            throw new NullPointerException("布局id 必须设置,不然没有界面显示");
        }

        View rootView = LayoutInflater.from(builder.mContext).inflate(builder.layoutId, null);
        this.setContentView(rootView);
        this.setWidth(builder.width);
        this.setHeight(builder.height);

        // 设置可以获得焦点
        this.setFocusable(builder.isFocusable);
        // 设置弹窗内可点击
        this.setTouchable(builder.isTouchable);
        // 设置弹窗外可点击
        this.setOutsideTouchable(builder.isOutsideTouchable);

        if (builder.listener != null) {
            builder.listener.covertView(rootView);
        }
        setBackgroundDrawable(builder.mContext.getResources().getDrawable(R.drawable.bg_popupwindow));// 设置背景图片，不能在布局中设置，要通过代码来设置
    }

    public static Builder builder(Context mContext) {
        return new Builder(mContext);
    }


    public static final class Builder {

        private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int layoutId = 0;
        private Context mContext;
        private OnCovertViewListener listener;

        // 设置可以获得焦点
        private boolean isFocusable = true;

        private boolean isTouchable = true;

        // 设置弹窗外可点击
        private boolean isOutsideTouchable = true;


        private Builder(Context context) {
            this.mContext = context;
        }

        public BasePopupWindow build() {

            return new BasePopupWindow(this);
        }

        public Builder setWidth(int w) {
            this.width = w;
            return this;
        }

        public Builder setHeight(int h) {
            this.height = h;
            return this;
        }


        public void setOutsideTouchable(boolean outsideTouchable) {
            isOutsideTouchable = outsideTouchable;
        }

        public void setTouchable(boolean touchable) {
            isTouchable = touchable;
        }

        public void setFocusable(boolean focusable) {
            isFocusable = focusable;
        }

        public void setCovertViewListener(OnCovertViewListener listener) {
            this.listener = listener;
        }
    }


    public interface OnCovertViewListener {
        void covertView(View viewRoot);
    }

}
