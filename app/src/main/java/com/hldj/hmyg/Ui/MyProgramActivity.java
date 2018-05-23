package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.search.ISearch;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.consumerFragments.ConsumerFragment1;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.enums.PurchaseStatus;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.MyProgramContract;
import com.hldj.hmyg.model.MyProgramGsonBean;
import com.hldj.hmyg.model.MyProgramModel;
import com.hldj.hmyg.presenter.MyProgramPresenter;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by luocaca on 2017/6/27 0027.
 * 我的项目  ---- 项目列表  采购选标
 */

public class MyProgramActivity extends BaseMVPActivity<MyProgramPresenter, MyProgramModel> implements MyProgramContract.View, ISearch {
    private static final String TAG = "MyProgramActivity";
    private CoreRecyclerView recyclerView;
    private String search_key = "";
    @ViewInject(id = R.id.viewpager)
    private ViewPager viewpager;

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


    }

    @Override
    public void showErrir(String erMst) {
        recyclerView.setNoData(erMst);
        hindLoading();
    }

    @Override
    public void initVH() {
        getView(R.id.sptv_program_do_search).setOnClickListener(view -> {
            D.e("==========根据条件搜索===============");
            search_key = getSearchText();
            recyclerView.onRefresh();
        });
    }

    @Override
    public void initXRecycle(List<MyProgramGsonBean.DataBeanX.PageBean.DataBean> gsonBean) {
        recyclerView.getAdapter().addData(gsonBean);
        recyclerView.selfRefresh(false);
        if (recyclerView.getAdapter().getData().size() == 0) {
            recyclerView.setDefaultEmptyView();
        }
        hindLoading();
    }

    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {

    }


    @Override
    public void onDeled(boolean bo) {

    }

    @Override
    public String getSearchText() {

        return ((EditText) getView(R.id.et_program_serach_text)).getText() + "";
    }

    @Override
    public ViewGroup getContentView() {
        return getView(R.id.my_program_content);
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
                                tablayout.getTabAt(i).setText(String.format("已开标\n (%s)", gsonBean.getData().statusCount.get(PurchaseStatus.expired.enumValue)));

                            if (i == 1)
                                tablayout.getTabAt(i).setText(String.format("报价中\n (%s)", gsonBean.getData().statusCount.get(PurchaseStatus.published.enumValue)));

                            if (i == 2)
                                tablayout.getTabAt(i).setText(String.format("已结束\n (%s)", gsonBean.getData().statusCount.get(PurchaseStatus.finished.enumValue)));

                        }


                    }
                });


    }
}
