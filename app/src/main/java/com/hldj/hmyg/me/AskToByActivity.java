package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseLazyFragment;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.me.fragments.AskToByFragment;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserActivity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

/**
 * 我的求购界面
 */

public class AskToByActivity extends BaseMVPActivity implements View.OnClickListener {
    private static final String TAG = "AskToByActivity";

    private static final int content_view_id = R.layout.activity_ask_to_by;

    private static final int item_layout_id = R.layout.item_buy_for_user;

    @ViewInject(id = R.id.viewpager)
    ViewPager viewpager;

    @ViewInject(id = R.id.fab)
    ImageView fab;

    @ViewInject(id = R.id.tab_head)
    TabLayout tabLayout;


    BaseLazyFragment fragment1;
    BaseLazyFragment fragment2;

    private ArrayList<String> list_title = new ArrayList<String>() {{
//        add("采购中");
        add("求购中");
        add("已结束");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(AskToByFragment.newInstance(false));
//        add(new ProgramFragment2());
        add(AskToByFragment.newInstance(true));
    }};


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        fab.setOnClickListener(v -> {
            PublishForUserActivity.start2Activity(mActivity);
        });

        refreshCount();

        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        viewpager.setAdapter(mFragmentPagerAdapter_tabLayout);
//        viewpager.addOnPageChangeListener(this);
//        viewpager.setOffscreenPageLimit(2);
        mFragmentPagerAdapter_tabLayout.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewpager);
//        ((RadioGroup) getView(R.id.radio_group_title)).setOnCheckedChangeListener(this);
//        getView(R.id.toolbar_left_icon).setOnClickListener(view -> finish());


//        recycler.init(new BaseQuickAdapter<String, BaseViewHolder>(item_layout_id) {
//            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//
//            }
//        });
//        recycler.getAdapter().addData("dafda");
//        recycler.getAdapter().addData("dafda");
//        recycler.getAdapter().addData("dafda");
//        recycler.getAdapter().addData("dafda");


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return content_view_id;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, AskToByActivity.class));
    }

    @Override
    public String setTitle() {
        return "我的求购";
    }


    public void refreshCount() {

        new BasePresenter()
                .doRequest("admin/userPurchase/countStatus", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

//                        ToastUtil.showLongToast(gsonBean.getData().closeCount + "<--close  ---> open" + gsonBean.getData().openCount);

                        tabLayout.getTabAt(0).setText(String.format("求购中  (%d)", gsonBean.getData().openCount));
                        tabLayout.getTabAt(1).setText(String.format("已结束  (%d)", gsonBean.getData().closeCount));



                    }
                });


    }
}
