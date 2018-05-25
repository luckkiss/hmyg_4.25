package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.search.IConsumerSearch;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.consumerFragments.ConsumerFragment1;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.enums.PurchaseStatus;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;


/**
 * Created by luocaca on 2017/6/27 0027.
 * 我的项目  ---- 项目列表  采购选标
 */

public class MyProgramActivity extends BaseMVPActivity implements IConsumerSearch {
    private static final String TAG = "MyProgramActivity";
    private CoreRecyclerView recyclerView;
    private String search_key = "";
    @ViewInject(id = R.id.viewpager)
    private ViewPager viewpager;

    @ViewInject(id = R.id.sptv_program_do_search, click = "search")
    private View sptv_program_do_search;

    @ViewInject(id = R.id.et_program_serach_text)
    private EditText searchEdit;

    @ViewInject(id = R.id.tablayout)
    private TabLayout tablayout;

    private ArrayList<String> list_title = new ArrayList<String>() {{
//        add("报价单");
        add("已开标");
        add("报价中");
        add("已结束");
    }};

    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
//        add(new ProgramFragment1());
        add(ConsumerFragment1.newInstance(PurchaseStatus.expired.enumValue));
        add(ConsumerFragment1.newInstance(PurchaseStatus.published.enumValue));
        add(ConsumerFragment1.newInstance(PurchaseStatus.finished.enumValue));
    }};


    public void search(View view) {
//        doRefreshOneFragment(0);
//        doRefreshOneFragment(1);
//        doRefreshOneFragment(2);
        for (int i = 0; i < list_fragment.size(); i++) {
            if (i == tablayout.getSelectedTabPosition()) {
                doRefreshOneFragment(i);
            } else {
                setVisibleToRefresh(i);
            }
        }

        doRefreshCount();
    }

    @Override
    public int bindLayoutID() {//step 1
        return R.layout.activity_my_program;
    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        viewpager.setAdapter(mFragmentPagerAdapter_tabLayout);
        viewpager.setOffscreenPageLimit(3);
        mFragmentPagerAdapter_tabLayout.notifyDataSetChanged();
        tablayout.setupWithViewPager(viewpager);

        doRefreshCount();
        initSearchHint();


    }

    private void initSearchHint() {
        searchEdit.setHint("请输入项目名称或采购单编号");
    }

    @Override
    public void showErrir(String erMst) {
        recyclerView.setNoData(erMst);
        hindLoading();
    }


    public String getSearchText() {

        return ((EditText) getView(R.id.et_program_serach_text)).getText() + "";
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, MyProgramActivity.class));
    }

    @Override
    public String setTitle() {
        return "采购选标";
    }


    @Override
    public String getSearchKey() {
        return getSearchText();
    }

    @Override
    public void doRefreshCount() {
        new BasePresenter()
                .putParams(ConstantParams.searchKey, getSearchKey())
                .doRequest("admin/purchase/getStatusCount", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

//                        ToastUtil.showLongToast(gsonBean.msg);
/**
 * 	"statusCount":{
 "expired":1,
 "finished":0,
 "published":1
 }
 */
                        for (int i = 0; i < 3; i++) {

                            if (i == 0)
                                tablayout.getTabAt(i).setText(String.format("%s\n已开标", gsonBean.getData().statusCount.get(PurchaseStatus.expired.enumValue)));

                            if (i == 1)
                                tablayout.getTabAt(i).setText(String.format("%s\n报价中", gsonBean.getData().statusCount.get(PurchaseStatus.published.enumValue)));

                            if (i == 2)
                                tablayout.getTabAt(i).setText(String.format("%s\n已结束", gsonBean.getData().statusCount.get(PurchaseStatus.finished.enumValue)));

                        }


                    }
                });


    }

    @Override
    public void doRefreshOneFragment(int pos) {
        if (list_fragment != null && list_fragment.size() == 3) {
            Fragment fragment = list_fragment.get(pos);
            if (fragment instanceof ConsumerFragment1 && ((ConsumerFragment1) fragment).mCoreRecyclerView != null) {
//                ((ConsumerFragment1) fragment).isVisibleMustRefresh = true;
                ((ConsumerFragment1) fragment).mCoreRecyclerView.onRefresh();
            }
        }
    }


    public void setVisibleToRefresh(int pos) {
        if (list_fragment != null && list_fragment.size() == 3) {
            Fragment fragment = list_fragment.get(pos);
//            if (fragment instanceof ConsumerFragment1 && ((ConsumerFragment1) fragment).mCoreRecyclerView != null) {
            ((ConsumerFragment1) fragment).isVisibleMustRefresh = true;
//                ((ConsumerFragment1) fragment).mCoreRecyclerView.onRefresh();
//            }
        }

    }

}
