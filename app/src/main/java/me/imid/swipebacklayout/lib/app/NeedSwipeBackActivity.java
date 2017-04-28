package me.imid.swipebacklayout.lib.app;

import android.os.Bundle;
import android.text.TextUtils;

import com.hldj.hmyg.R;
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
}
