package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.me.fragments.MomentHistoryFragment;
import com.hldj.hmyg.me.fragments.SeedlingHistoryFragment;
import com.hldj.hmyg.me.fragments.UserPurchaseHistoryFragment;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

/**
 * 我的足迹 界面
 */

public class HistoryActivity extends BaseMVPActivity implements View.OnClickListener {


    int item_layout_id = R.layout.item_invite_friend_list;
    int item_layout_cycle_id = R.layout.item_invite_friend_list;

    @ViewInject(id = R.id.tabLayout)
    TabLayout tabLayout;

    @ViewInject(id = R.id.toolbar_right_text, click = "onClick")
    TextView toolbar_right_text;


    @Override
    public int bindLayoutID() {
        return R.layout.activity_history;
    }

    @ViewInject(id = R.id.viewpager)
    ViewPager viewpager;


    private ArrayList<String> list_title = new ArrayList<String>() {{
//        add("采购中");
        add("苗木信息");
        add("苗木圈");
        add("用户求购");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new SeedlingHistoryFragment());
        add(new MomentHistoryFragment());
        add(new UserPurchaseHistoryFragment());
    }};


    @ViewInject(id = R.id.tijia, click = "onClick")
    TextView tijia;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tijia:
                AddContactActivity.start(mActivity);
                break;
            case R.id.toolbar_right_text:
//                ToastUtil.showLongToast("清空");
//                ToastUtil.showLongToast("" + viewpager.getCurrentItem());
                BaseRecycleViewFragment baseRecycleViewFragment = (BaseRecycleViewFragment) list_fragment.get(viewpager.getCurrentItem());
                if (baseRecycleViewFragment instanceof IFootMarkEmpty) {
                    ((IFootMarkEmpty) baseRecycleViewFragment).doEmpty();
                }
                break;


        }

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_text.setText("清空");


        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        viewpager.setAdapter(mFragmentPagerAdapter_tabLayout);
//        viewpager.addOnPageChangeListener(null);

        mFragmentPagerAdapter_tabLayout.notifyDataSetChanged();


        tabLayout.setupWithViewPager(viewpager);

//        recycler.init(new BaseQuickAdapter<String, BaseViewHolder>(item_layout_id) {
//            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//
//            }
//        });

//        recycler.getRecyclerView().addItemDecoration(
//                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
//                    @Override
//                    public String getGroupName(int position) {
//
//                        return recycler.getAdapter().getItem(position) + "";
//                    }
//
//                    @Override
//                    public View getGroupView(int position) {
////                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
////                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
////                        TextView tv = view.findViewById(android.R.id.text1);
////                        tv.setText(recycler.getAdapter().getItem(position) + "");
//
//                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
//                        TextView textView = view.findViewById(R.id.text1);
//                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
//                        textView.setText("3333");
//
//                        return view;
//                    }
//                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());
//
//        recycler.getAdapter().addData("2018-04-20");
//        recycler.getAdapter().addData("2018-04-20");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-22");
//        recycler.getAdapter().addData("2018-04-23");
//        recycler.getAdapter().addData("2018-04-23");
//        recycler.getAdapter().addData("2018-04-23");
//        recycler.getAdapter().addData("2018-04-23");
//        recycler.getAdapter().addData("2018-04-23");
//        recycler.getAdapter().addData("2018-04-23");


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


    @Override
    protected void onStart() {
        super.onStart();

        requestCounts();
    }

    public void requestCounts() {
        new BasePresenter()
                .doRequest("admin/footmark/footCount", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        D.i("onRealSuccess:-------- " + gsonBean.msg);


                        if (gsonBean.getData().typeList != null && gsonBean.getData().typeList.size() == 3) {
                            tabLayout.getTabAt(0).setText(String.format("苗木信息(%s)", gsonBean.getData().typeList.get(0).count));
                            tabLayout.getTabAt(1).setText(String.format("苗木圈(%s)", gsonBean.getData().typeList.get(1).count));
                            tabLayout.getTabAt(2).setText(String.format("用户求购(%s)", gsonBean.getData().typeList.get(2).count));
                        }


                    }
                });


    }

}
