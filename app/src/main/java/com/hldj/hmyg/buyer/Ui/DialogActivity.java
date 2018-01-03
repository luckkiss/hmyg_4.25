package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hy.utils.ToastUtil;

/**
 * 弹窗activity
 */

public class DialogActivity extends FragmentActivity {
    private static final String TAG = "DialogActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        win.setAttributes(lp);

        findViewById(R.id.city).setOnClickListener(this::city);
        findViewById(R.id.close_title).setOnClickListener(v -> finish());

    }


    public void city(View view) {
        CityWheelDialogF.instance()
                .isShowCity(true)
                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                    @Override
                    public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                        ToastUtil.showLongToast(childBeans.fullName);
                    }

                    @Override
                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                    }
                })
                .show(getSupportFragmentManager(), TAG);
    }


    public static void start(Activity activity)

    {
        activity.startActivity(new Intent(activity, DialogActivity.class));

    }


}
