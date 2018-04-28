package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 我的关注 界面
 */

public class BeAttentionActivity extends BaseMVPActivity implements View.OnClickListener {

    int item_layout_id = R.layout.item_invite_friend_list;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_be_attention;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;

    @ViewInject(id = R.id.tijia, click = "onClick")
    TextView tijia;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tijia:
                AddContactActivity.start(mActivity);
                break;
        }

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        recycler.init(new BaseQuickAdapter<String, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, BeAttentionActivity.class));
    }

    @Override
    public String setTitle() {
        return "被关注记录";
    }

}
