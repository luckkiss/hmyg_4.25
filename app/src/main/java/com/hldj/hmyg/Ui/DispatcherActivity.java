package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.base.BaseWebViewActivity;

/**
 * 调度专员界面
 */

public class DispatcherActivity extends BaseWebViewActivity {


    public static void start(Activity mActivity) {

        mActivity.startActivity(new Intent(mActivity, DispatcherActivity.class));

    }

    @Override
    public String setTitle() {
        return "调度专员";
    }
}
