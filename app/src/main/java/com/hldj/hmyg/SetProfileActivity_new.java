package com.hldj.hmyg;

import android.content.Intent;

import com.hldj.hmyg.base.BaseMVPActivity;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 个人资料  界面
 */
public class SetProfileActivity_new extends BaseMVPActivity {


    @Override
    public void initView() {

    }

    @Override
    public void initVH() {

    }

    @Override
    public String bindTitle() {
        return "个人资料";
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_save_profile_new;
    }

    public static void start2ActivitySet(NeedSwipeBackActivity mActivity, int i) {
        mActivity.startActivity(new Intent(mActivity, SetProfileActivity_new.class));
    }
}
