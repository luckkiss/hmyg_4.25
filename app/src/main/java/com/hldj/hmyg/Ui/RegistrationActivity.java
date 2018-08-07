package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;

/**
 * 签到界面
 */

public class RegistrationActivity extends BaseMVPActivity {


    @Override
    public void initView() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {

        return R.layout.activity_registration_activity;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, RegistrationActivity.class));

    }

    @Override
    public String setTitle() {
        return "签到";
    }
}
