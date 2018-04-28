package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.lqr.optionitemview.OptionItemView;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.List;

/**
 * 邀请好友  界面   分享链接邀请好友
 */

public class InviteFriendActivity extends BaseMVPActivity {


    CoreRecyclerView recycle;

    List<IntegralBean> integralBeens;

    OptionItemView top;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_invite_friend;
    }


    @Override
    public void initView() {

    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, InviteFriendActivity.class));
    }


    public void requestDatas(AjaxCallBack callBack) {
//        new BasePresenter()
//                .doRequest("userPoint/list", true, callBack);
        new BasePresenter()
                .doRequest("admin/userPoint/index", true, callBack);
    }


    @Override
    public String setTitle() {
        return "分享链接邀请好友";
    }
}
