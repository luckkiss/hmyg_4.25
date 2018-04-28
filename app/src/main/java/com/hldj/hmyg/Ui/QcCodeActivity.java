package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SharePopupWindow;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 实名认证界面
 */

public class QcCodeActivity extends BaseMVPActivity implements View.OnClickListener {

    @ViewInject(id = R.id.toolbar_right_icon, click = "onClick")
    ImageView toolbar_right_icon;

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_qc_code;
    }


    public static void start(Activity mActivity, int state, String failedMsg) {
        Intent intent = new Intent(mActivity, QcCodeActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("failedMsg", failedMsg);
        mActivity.startActivity(intent);
    }

    @Override
    public String setTitle() {
        return "店铺二维码";
    }

    @Override
    public void initView() {
        /* 右上分享按钮 显示并 设定分享按钮图片 */
        FinalActivity.initInjectedView(mActivity);
        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_icon.setImageResource(R.drawable.fenxiang);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_right_icon:

                ToastUtil.showLongToast("share");
                share();

                break;


        }

    }

    private void share() {

        ComonShareDialogFragment.ShareBean shareBean = new ComonShareDialogFragment.ShareBean();

        shareBean.title = "某某某的店铺";

        shareBean.text = "shareContent";
        shareBean.pageUrl = "shareUrl";
        shareBean.imgUrl = "headImage";
        shareBean.text = "shareContent";


        SharePopupWindow window = new SharePopupWindow(mActivity, shareBean);
        window.showAsDropDown(toolbar_right_icon);


    }
}
