package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.SettingActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 弹窗activity
 */

public class LoginOutDialogActivity extends BaseMVPActivity {
    private static final String TAG = "DialogActivity";


    @ViewInject(id = R.id.ok)
    TextView ok;

    @Override
    protected void initListener() {
        ok.setOnClickListener(v -> {
            finish();

        });
    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        win.setAttributes(lp);


    }


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_dialog_login_out;
    }


    public static void start(Activity activity)

    {
        activity.startActivity(new Intent(activity, LoginOutDialogActivity.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        SettingActivity.exit2Home
                (mActivity, MyApplication.Userinfo.edit(), true);
    }

    /*一比报价接口*/


}
