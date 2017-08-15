package com.hldj.hmyg.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hldj.hmyg.R;


/**
 *
 * 版权：公司 版权所有
 *
 * 作者：罗擦擦
 *
 * 版本：
 *
 * 创建日期：2017/8/9 0009
 *
 * 描述：大傻出品避暑精品
 * 建造者模式初体验 ，参考dagger2 commponent 的自动生成代码
 *
 */

public class CommonPopupWindow extends PopupWindow {


    protected CommonPopupWindow(Builder builder) {
        initWithBuilder(builder);
    }

    private void initWithBuilder(Builder builder) {

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

        public CommonPopupWindow build() {

            if ( layoutId == 0) {
                throw new NullPointerException("布局id 必须设置,不然没有界面显示");
            }

            return new CommonPopupWindow(this);
        }


        /**
         * 传入  布局id
         *
         * @param layoutId
         * @return
         */
        public Builder bindLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder setWidthDp(int w) {
            this.width = dp2px(mContext, w);
            return this;
        }

        public Builder setHeightDp(int h) {
            this.height = dp2px(mContext, h);
            return this;
        }

        public Builder setWidthPx(int w) {
            this.width = w;
            return this;
        }



        public Builder setHeightPx(int h) {
            this.height = h;
            return this;
        }


        public Builder setOutsideTouchable(boolean outsideTouchable) {
            isOutsideTouchable = outsideTouchable;
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            isTouchable = touchable;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            isFocusable = focusable;
            return this;
        }

        public Builder setCovertViewListener(OnCovertViewListener listener) {
            this.listener = listener;
            return this;
        }

        private int dp2px(Context context, int dp) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }
    }


    public interface OnCovertViewListener {
        void covertView(View viewRoot);
    }


}
