package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.child.NewsFragment;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.lqr.optionitemview.OptionItemView;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NewsActivity extends NeedSwipeBackActivity {


    private ViewHolder viewHolder;//通过geviewholder 来获取

    public ViewHolder getViewHolder() {
        if (viewHolder == null) {
            viewHolder = new ViewHolder(this);
        }
        return viewHolder;
    }


    FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout;
    private ArrayList<String> list_title = new ArrayList<String>() {{
        add("公司新闻");
        add("行业资讯");
        add("股东资讯");
        add("平台公告");
        add("股东资讯");
        add("公司新闻");
        add("行业资讯");
        add("股东资讯");

    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());
        add(new NewsFragment());

    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
    }

    private void initView() {

//        loadingLayout.setStatus(loadingLayout.Loading);
//        loadingLayout.show

        getViewHolder().news_vp_content.setOffscreenPageLimit(3);
        mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        getViewHolder().news_vp_content.setAdapter(mFragmentPagerAdapter_tabLayout);
        getViewHolder().news_tb_layout.setupWithViewPager(getViewHolder().news_vp_content);

    }

    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, NewsActivity.class));
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
