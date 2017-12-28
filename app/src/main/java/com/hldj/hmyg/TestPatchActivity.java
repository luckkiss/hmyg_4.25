package com.hldj.hmyg;

import android.content.Context;
import android.content.Intent;

import com.hldj.hmyg.base.BaseMVPActivity;
import com.hy.utils.ToastUtil;


public class TestPatchActivity extends BaseMVPActivity {


    @Override
    public void initView() {
        ToastUtil.showLongToast("测试activity能否被补丁");
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_add_friends;
    }



    public static void start2Activity(Context aa) {
        aa.startActivity(new Intent(aa, TestPatchActivity.class));
    }


}
