package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.R;

/**
 * 实名认证界面
 */

public class AuthenticationCompanyActivity extends AuthenticationActivity {


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_authentication_company;
    }


    public static void start(Activity mActivity, int state, String failedMsg) {
        Intent intent = new Intent(mActivity, AuthenticationCompanyActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("failedMsg", failedMsg);
        mActivity.startActivity(intent);
    }

    @Override
    public String setTitle() {
        return "企业认证";
    }
}
