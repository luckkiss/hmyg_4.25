package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.noticeChild.NoticeFragment;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.lqr.optionitemview.OptionItemView;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;



/**
 * Created by Administrator on 2017/5/22.
 */

public class NoticeActivity extends NeedSwipeBackActivity {


    private ViewHolder viewHolder;//通过geviewholder 来获取

    public ViewHolder getViewHolder() {
        if (viewHolder == null) {
            viewHolder = new ViewHolder(this);
        }
        return viewHolder;
    }


    FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout;
    private ArrayList<String> list_title = new ArrayList<String>() {{
        add("成交公告1");
        add("成交公告2");
        add("成交公告3");
        add("成交公告4");
        add("成交公告5");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new NoticeFragment());
        add(new NoticeFragment());
        add(new NoticeFragment());
        add(new NoticeFragment());
        add(new NoticeFragment());
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        requestData();//网络请求 ，请求到数据源 初始化数据

        initView();
    }

    private void requestData() {
//        initView();

    }

    private void initView() {

//        loadingLayout.setStatus(loadingLayout.Loading);
//        loadingLayout.show
        getViewHolder().news_title.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                finish();
            }

            @Override
            public void centerOnClick() {
            }

            @Override
            public void rightOnClick() {
                ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName());
            }
        });

        getViewHolder().news_vp_content.setOffscreenPageLimit(2);
//        getViewHolder().news_tb_layout.setSelectedTabIndicatorHeight(0);
        mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        getViewHolder().news_vp_content.setAdapter(mFragmentPagerAdapter_tabLayout);
        getViewHolder().news_tb_layout.setupWithViewPager(getViewHolder().news_vp_content);
    }

    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, NoticeActivity.class));
    }


    private class ViewHolder {
        public OptionItemView news_title; // title
        public TabLayout news_tb_layout; // tablayout
        public ViewPager news_vp_content; // webview

        public ViewHolder(Activity rootView) {
            this.news_title = (OptionItemView) rootView.findViewById(R.id.news_title);
            this.news_tb_layout = (TabLayout) rootView.findViewById(R.id.news_tb_layout);
            this.news_vp_content = (ViewPager) rootView.findViewById(R.id.news_vp_content);
        }

    }


}
