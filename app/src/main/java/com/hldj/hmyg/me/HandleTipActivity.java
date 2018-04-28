package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.BeReportActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 处理提示 界面
 */

public class HandleTipActivity extends BaseMVPActivity implements View.OnClickListener {

    //    int item_layout_id = R.layout.item_invite_friend_list;
    int item_layout_id = R.layout.list_item_message;

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

                helper.setText(R.id.tv_name, item);
                helper.setText(R.id.tv_createDateStr, "2017-09-07");
                helper.setText(R.id.tv_message, "您的苗木资源(m002346)已被客服审核");

                helper.convertView.setOnClickListener(v -> BeReportActivity.start(mActivity));


            }
        });
        recycler.getAdapter().addData("被撤回");
        recycler.getAdapter().addData("已到期");
        recycler.getAdapter().addData("被举报");


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, HandleTipActivity.class));
    }

    @Override
    public String setTitle() {
        return "消息列表";
    }

}
