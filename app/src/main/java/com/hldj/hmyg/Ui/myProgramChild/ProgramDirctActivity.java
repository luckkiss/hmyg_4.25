package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.childensFragment.ProgramFragment1;
import com.hldj.hmyg.Ui.myProgramChild.childensFragment.ProgramFragment3;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.util.D;

import java.util.ArrayList;


/**
 * 我的项目 -----  直购界面 protocol
 */

public class ProgramDirctActivity extends BaseMVPActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_dirct;
    }

    private ArrayList<String> list_title = new ArrayList<String>() {{
//        add("采购中");
        add("待采购");
        add("历史采购");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new ProgramFragment1());
//        add(new ProgramFragment2());
        add(new ProgramFragment3());
    }};


    @Override
    public void initView() {
        getView(R.id.rb_title_center).setVisibility(View.GONE);
        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        getVpContent().setAdapter(mFragmentPagerAdapter_tabLayout);
        getVpContent().addOnPageChangeListener(this);
        getVpContent().setOffscreenPageLimit(2);
        mFragmentPagerAdapter_tabLayout.notifyDataSetChanged();
        ((RadioGroup) getView(R.id.radio_group_title)).setOnCheckedChangeListener(this);
        getView(R.id.toolbar_left_icon).setOnClickListener(view -> finish());

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
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }


    @Keep
    private static final String PRO_ID = "project_id";

    public String getExtralID() {
        String pro_id = getIntent().getExtras().getString(PRO_ID);
        if (TextUtils.isEmpty(pro_id)) {
            return "";
        }
        D.e("=======PRO_ID===========" + pro_id);
        return pro_id;
    }

    public static void start(Activity mActivity, String project_id) {
        Intent intent = new Intent(mActivity, ProgramDirctActivity.class);
        intent.putExtra(PRO_ID, project_id);
        mActivity.startActivity(intent);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ((RadioButton) getView(R.id.rb_title_left)).setChecked(true);
                break;
            case 1:
                ((RadioButton) getView(R.id.rb_title_right)).setChecked(true);
                break;
            case 2:
                ((RadioButton) getView(R.id.rb_title_right)).setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
        switch (id) {
            case R.id.rb_title_left:
                getVpContent().setCurrentItem(0);
                break;
            case R.id.rb_title_center:
                getVpContent().setCurrentItem(1);
                break;
            case R.id.rb_title_right:
                getVpContent().setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
