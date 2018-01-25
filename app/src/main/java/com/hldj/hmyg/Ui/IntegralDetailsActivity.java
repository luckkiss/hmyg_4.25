package com.hldj.hmyg.Ui;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;

/**
 * 积分详情界面
 */

public class IntegralDetailsActivity extends BaseMVPActivity {
    @Override
    public void initView() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_integral_details;
    }
}
