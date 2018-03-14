package com.hldj.hmyg.saler.Ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.ManagerQuoteListAdapter;
import com.hldj.hmyg.util.D;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;

public class ManagerQuoteListActivity_new extends NeedSwipeBackActivity {
    private XListView xListView;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    boolean getdata; // 避免刷新多出数据
    private ManagerQuoteListAdapter listAdapter;

    private int pageSize = 20;
    private int pageIndex = 0;
    private String status = "";
    private View mainView;

    // 报价管理列表

    TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout;
    private ArrayList<String> list_title = new ArrayList<String>() {{
        add("全部");
        add("选标中");
        add("已中标");
        add("未中标");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new Fragment1());
        add(new Fragment1_ing());
        add(new Fragment2());
        add(new Fragment3());
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        D.e("====");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_quote_list_new);

        ((TextView) findViewById(R.id.toolbar_title)).setText("我的报价");

//        StatusBarUtil.setTranslucent(this,122);
//        setContentView(R.layout.activity_manager_quote_list_new);
//        addStartBar();
//        setStatusBarColor(Color.GREEN);

        LoadingLayout loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
//        loadingLayout.setStatus(loadingLayout.Loading);
//        loadingLayout.show
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_manager);
        mViewPager.setOffscreenPageLimit(3);


        mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        mViewPager.setAdapter(mFragmentPagerAdapter_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);


        findViewById(R.id.toolbar_left_icon).setOnClickListener(v -> {
            finish();
        });

//        loadingLayout.setStatus(LoadingLayout.Loading);//表示展示加载界面
//        loadingLayout.setStatus(LoadingLayout.Empty);//表示展示无数据界面
//        loadingLayout.setStatus(LoadingLayout.Error);//表示展示加载错误界面
//        loadingLayout.setStatus(LoadingLayout.No_Network);表示无网络连接界面
//        loadingLayout.setStatus(LoadingLayout.Success);表示加载成功，
//        会使得loadingLayout界面消失
//                展示正常应用应该展示的界面


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment == null)
                    continue;
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, ManagerQuoteListActivity_new.class));
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }
}
