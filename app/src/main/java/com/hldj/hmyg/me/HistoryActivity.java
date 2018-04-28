package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 我的足迹 界面
 */

public class HistoryActivity extends BaseMVPActivity implements View.OnClickListener {


    int item_layout_id = R.layout.item_invite_friend_list;
    int item_layout_cycle_id = R.layout.item_invite_friend_list;

    @ViewInject(id = R.id.tabLayout)
    TabLayout tabLayout;

    @ViewInject(id = R.id.toolbar_right_text)
    TextView toolbar_right_text;


    @Override
    public int bindLayoutID() {
        return R.layout.activity_history;
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

        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_text.setText("清空");


        recycler.init(new BaseQuickAdapter<String, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });

        recycler.getRecyclerView().addItemDecoration(
                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {

                        return recycler.getAdapter().getItem(position) + "";
                    }

                    @Override
                    public View getGroupView(int position) {
//                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
//                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
//                        TextView tv = view.findViewById(android.R.id.text1);
//                        tv.setText(recycler.getAdapter().getItem(position) + "");

                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
                        TextView textView =  view.findViewById(R.id.text1);
                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
                        textView.setText("3333");

                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)  ).build());

        recycler.getAdapter().addData("2018-04-20");
        recycler.getAdapter().addData("2018-04-20");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-22");
        recycler.getAdapter().addData("2018-04-23");
        recycler.getAdapter().addData("2018-04-23");
        recycler.getAdapter().addData("2018-04-23");
        recycler.getAdapter().addData("2018-04-23");
        recycler.getAdapter().addData("2018-04-23");
        recycler.getAdapter().addData("2018-04-23");


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, HistoryActivity.class));
    }

    @Override
    public String setTitle() {
        return "我的足迹";
    }

}
