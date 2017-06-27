package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.childensFragment.ProgramFragment1;
import com.hldj.hmyg.Ui.myProgramChild.childensFragment.ProgramFragment2;
import com.hldj.hmyg.Ui.myProgramChild.childensFragment.ProgramFragment3;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;

import java.util.ArrayList;

/**
 * 我的项目 -----  直购界面
 */

public class ProgramDirctActivity extends BaseMVPActivity implements View.OnClickListener {

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_dirct;
    }

    private ArrayList<String> list_title = new ArrayList<String>() {{
        add("采购中");
        add("待采购");
        add("历史采购");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new ProgramFragment1());
        add(new ProgramFragment2());
        add(new ProgramFragment3());
    }};


    @Override
    public void initView() {
        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        getVpContent().setAdapter(mFragmentPagerAdapter_tabLayout);

    }

    private ViewPager getVpContent() {
        return getView(R.id.vp_content);
    }


    @Override
    public void initVH() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public String setTitle() {
        return "采购单";
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    public static void start(Activity mActivity)
    {
        mActivity.startActivity(new Intent(mActivity,ProgramDirctActivity.class));
    }


}
