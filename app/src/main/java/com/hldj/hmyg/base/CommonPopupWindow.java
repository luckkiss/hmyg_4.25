package com.hldj.hmyg.base;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hldj.hmyg.R;


/**
 * 版权：公司 版权所有
 * <p>
 * 作者：罗擦擦
 * <p>
 * 版本：
 * <p>
 * 创建日期：2017/8/9 0009
 * <p>
 * 描述：大傻出品避暑精品
 * 建造者模式初体验 ，参考dagger2 commponent 的自动生成代码
 */

public class CommonPopupWindow extends PopupWindow {

    public static int TYPE_WHITE_UP = R.drawable.up;
    public static int TYPE_WHITE_DOWN = R.drawable.down;
    public static int TYPE_BLACK_UP = R.drawable.bg_popupwindow;
    public static int TYPE_BLACK_UP_NEW = R.drawable.bg_popupwindow_new;
    public static int TYPE_WHITE_UP_RIGHT_TOP = R.drawable.bg_white_up_right_top;
    public static int trans = R.drawable.trans_bg;


    Builder mBuilder;

    protected CommonPopupWindow(Builder builder) {
        initWithBuilder(builder);
    }


    public void simpleShow(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        //↓
        setBackgroundDrawable(mBuilder.mContext.getResources().getDrawable(mBuilder.bgType));// 设置背景图片，不能在布局中设置，要通过代码来设置

        showAsDropDown(v);

    }


    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     *
     * @param v
     */
    public void showUp2(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Log.i("showUp2", "showUp2: " + location[0]);
        Log.i("showUp2", "showUp2: " + location[1]);
        //在控件上方显示

        if (location[1] > 500) {
            //↑
//            TYPE_WHITE_DOWN
            setBackgroundDrawable(mBuilder.mContext.getResources().getDrawable(TYPE_WHITE_DOWN));// 设置背景图片，不能在布局中设置，要通过代码来设置
//            showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - mBuilder.width / 2, location[1] - mBuilder.height + 10);
//            showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + this.getWidth() / 2) - mBuilder.width / 2, location[1] - mBuilder.height + 10);
//            showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] + this.getHeight());

//          //加载PopupWindow的布局
//          View view = View.inflate(this, R.layout.popwindow, null);
////测量布局的大小
            this.getContentView().measure(0, 0);
////将布局大小设置为PopupWindow的宽高
            PopupWindow popWindow = new PopupWindow(this.getContentView(), this.getContentView().getMeasuredWidth(), this.getContentView().getMeasuredHeight(), true);
            popWindow.getHeight();
            popWindow.getWidth();

            Log.i("h", " popWindow.getH: " + popWindow.getHeight() + "   this.getContentView()" + this.getContentView().getWidth());
            Log.i("w", " popWindow.getWidth(): " + popWindow.getWidth() + "   this.getContentView()" + this.getContentView().getHeight());

            Log.i("w", "showUp2: " + this.getWidth());
            Log.i("h", "showUp2: " + this.getHeight());
            showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popWindow.getWidth() / 2, (int) (location[1] - (popWindow.getHeight() * 1.2) + 10));


        } else {
            //↓
            setBackgroundDrawable(mBuilder.mContext.getResources().getDrawable(TYPE_WHITE_UP));// 设置背景图片，不能在布局中设置，要通过代码来设置

            showAsDropDown(v);
        }


    }

    private void initWithBuilder(Builder builder) {
        mBuilder = builder;
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
            builder.listener.covertView(rootView, this);
        }
        setBackgroundDrawable(builder.mContext.getResources().getDrawable(builder.bgType));// 设置背景图片，不能在布局中设置，要通过代码来设置
    }

    public static Builder builder(Context mContext) {
        return new Builder(mContext);
    }


    public static final class Builder {

        private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int layoutId = 0;
        private int bgType = TYPE_BLACK_UP;
        private Context mContext;
        private OnCovertViewListener listener;

        // 设置可以获得焦点
        private boolean isFocusable = true;

        private boolean isTouchable = true;

        // 设置弹窗外可点击
        private boolean isOutsideTouchable = true;


        public Builder(Context context) {
            this.mContext = context;
        }

        public CommonPopupWindow build() {

            if (layoutId == 0) {
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

        public Builder setBgType(int bgType) {
            this.bgType = bgType;
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
        void covertView(View viewRoot, CommonPopupWindow popupWindow);
    }


}
