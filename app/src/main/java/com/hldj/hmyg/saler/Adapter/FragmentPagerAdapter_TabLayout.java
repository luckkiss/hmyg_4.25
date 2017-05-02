package com.hldj.hmyg.saler.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2.
 */

public class FragmentPagerAdapter_TabLayout extends FragmentPagerAdapter{

    private ArrayList<String> list_title;
    private ArrayList<Fragment> list_fragment;

    public FragmentPagerAdapter_TabLayout(FragmentManager fm, ArrayList<String> list_title, ArrayList<Fragment> list_fragment) {
        super(fm);
        this.list_title= list_title;
        this.list_fragment= list_fragment;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list_fragment.get(position);
    }
    @Override
    public int getCount() {
        return list_fragment.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position);
    }
}
