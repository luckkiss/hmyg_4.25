package me.imid.swipebacklayout.lib.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.MyOptionItemView;

public class NeedSwipeBackActivity extends SwipeBackBActivity {

    protected NeedSwipeBackActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.views = new SparseArray<View>();


        D.e("======当前界面classname========" + this.getClass().getName());

//		if (Build.VERSION.SDK_INT >= 23) {
        mActivity = this;
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


    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected String getSpS(String key) {
        return MyApplication.Userinfo.getString(key, "");
    }

    protected void putSpS(String key, String value) {
        SharedPreferences.Editor editor = MyApplication.Userinfo.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected void putSpB(String key, boolean value) {
        SharedPreferences.Editor editor = MyApplication.Userinfo.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean getSpB(String key) {
        return MyApplication.Userinfo.getBoolean(key, false);
    }

/**
 *    @SuppressWarnings("unchecked") public <T extends View> T getView(int viewId) {
View view = views.get(viewId);
if (view == null) {
view = convertView.findViewById(viewId);
views.put(viewId, view);
}
return (T) view;
}
 */


    /**
     * Views indexed with their IDs
     */
    private SparseArray<View> views;

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public void retry() {

    }

    public void setText(View tv, CharSequence str) {
        if (tv instanceof TextView)
        {
            ((TextView) tv).setText(str);
        }else if (tv instanceof MyOptionItemView)
        {
            ((MyOptionItemView) tv).setRightText(str.toString());
        }
    }

    public String getText(TextView tv) {
        return TextUtils.isEmpty(tv.getText()) ? "" : tv.getText().toString();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

}
