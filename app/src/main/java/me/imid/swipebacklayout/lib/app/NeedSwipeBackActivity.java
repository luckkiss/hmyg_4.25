package me.imid.swipebacklayout.lib.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.util.D;

public class NeedSwipeBackActivity extends SwipeBackBActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        D.e("======当前界面classname========" + this.getClass().getName());

//		if (Build.VERSION.SDK_INT >= 23) {
        setSwipeBackEnable(true);
//		}
    }




    /**
     * 打开activity 动画
     */
    public void overridePendingTransition_open() {
        //进
        //100  ---   0
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_left_out);
        //出
        //0  --- -100
    }

    /**
     * 关闭activity 动画
     */


    public void overridePendingTransition_back() {
        //进
        //-100  ---   0
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        //出
        //0  --- 100
    }


    public static String strFilter(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }


    /**
     * 添加状态栏
     */

    protected void initStatusBar() {
//        LinearLayout rootView = (LinearLayout) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        View statusBar = new View(this);
        //改变颜色时避免重复添加statusBarView
        if (contentView != null && contentView.getMeasuredHeight() == getStatusBarHeight(this)) {
            statusBar.setBackgroundColor(Color.RED);
            return;
        }
//        StatusBarUtil.setTransparent();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));

        statusBar.setLayoutParams(layoutParams);

        contentView.addView(statusBar, 0);

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 改变状态栏颜色
     *
     * @param color
     */

    protected void setStatusBarColor(int color) {

        View rootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        rootView.setBackgroundColor(color);

    }


    public void addStartBar() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                initStatusBar();
                setStatusBarColor(MyApplication.getInstance().getResources().getColor(R.color.main_color));
            }
        } catch (Exception e) {
            D.e("===设置失败===");
        }
    }


}
