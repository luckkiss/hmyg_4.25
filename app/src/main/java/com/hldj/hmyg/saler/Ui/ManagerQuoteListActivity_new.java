package com.hldj.hmyg.saler.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.ManagerQuoteListAdapter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserQuote;
import com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.LoginUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.lang.reflect.Type;
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

    CoreRecyclerView coreRecyclerView;

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


//        ((TextView) findViewById(R.id.toolbar_title)).setText("我的报价");


//        StatusBarUtil.setTranslucent(this,122);
//        setContentView(R.layout.activity_manager_quote_list_new);
//        addStartBar();
//        setStatusBarColor(Color.GREEN);

        LoadingLayout loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
//        loadingLayout.setStatus(loadingLayout.Loading);
//        loadingLayout.show
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        coreRecyclerView = (CoreRecyclerView) findViewById(R.id.recycle);

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


        setTextOrHinden(R.id.rb_title_left, View.VISIBLE, "平台采购", v -> {
            mTabLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.VISIBLE);

        });

        setTextOrHinden(R.id.rb_title_center, View.GONE, "", null);

        setTextOrHinden(R.id.rb_title_right, View.VISIBLE, "用户求购", v -> {
            mTabLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
        });


        initRecycleView(coreRecyclerView);

        mTabLayout.setVisibility(initLeft ? View.VISIBLE : View.GONE);
        loadingLayout.setVisibility(initLeft ? View.VISIBLE : View.GONE);
        RadioButton rb_title_right = getView(R.id.rb_title_right);
        rb_title_right.setChecked(!initLeft);

    }

    private void initRecycleView(CoreRecyclerView coreRecyclerView) {


        coreRecyclerView.init(new BaseQuickAdapter<UserQuote, BaseViewHolder>(R.layout.item_ask_to_by) {
            @Override
            protected void convert(BaseViewHolder helper, UserQuote item) {
                helper.setVisible(R.id.linearLayout, false);
                doConvert(helper, item);
            }
        })
                .setDefaultEmptyView(true, "立即求购", retry -> {
//                    ToastUtil.showLongToast("go to publish");
                    BuyForUserActivity.start2Activity(mActivity);
                })
                .openRefresh().openLoadMore(20, this::requestData);

        coreRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 15);
//                view.setBackgroundColor(Color.RED);
                parent.setBackgroundColor(getResources().getColor(R.color.gray_bg_ed));
            }
        });


        coreRecyclerView.onRefresh();
    }

    private void requestData(int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserQuote>>>>() {
        }.getType();

        new BasePresenter()
                .putParams("pageSize", "20")
                .putParams("pageIndex", "" + page)
                .doRequest("admin/userQuote/list", new HandlerAjaxCallBackPage<UserQuote>(mActivity, type, UserQuote.class) {
                    @Override
                    public void onRealSuccess(List<UserQuote> userQuotes) {
                        coreRecyclerView.getAdapter().addData(userQuotes);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        coreRecyclerView.selfRefresh(false);
                    }
                });


    }

    private void doConvert(BaseViewHolder helper, UserQuote item) {
        int layid = R.layout.item_ask_to_by;
        Log.i("doConvert", "doConvert: " + item.toString());

        helper
                .setText(R.id.title, item.attrData.name )
                .setText(R.id.shuliang, FUtil.$_zero(item.attrData.count + " " + item.attrData.unitTypeName))
                .setText(R.id.qubaojia, "")
                .setText(R.id.price, "¥" + item.price + "/" + item.attrData.unitTypeName)
                .setText(R.id.space_text, item.attrData.specText)
                .setText(R.id.city, "用苗地:" + item.attrData.cityName)
                .setText(R.id.update_time, "结束时间:" + item.createDate);

        helper
                .convertView.setOnClickListener(v -> {
            PublishForUserDetailActivity.start2Activity(mActivity, item.purchaseId, "");
        });

        // UserQuote{id='ae7b275efdd545ca8b047cde75b3a947', sellerId='2876f7e0f51c4153aadc603b661fedfa',
        // purchaseId='2a3d420100284c27a90ac5d56270adc9', price='123.00', priceType='shangche',
        // ciCode='1403', cityName='山西 阳泉', cityBean=null, remarks='4564', images=null,
        // imagesJson=[], isExclude=null, priceTypeName='上车价',
        // closeDateStr='null', attrData=com.hldj.hmyg.saler.bean.UserQuote$AttrData@a49554e}


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


    public static boolean initLeft = true;

    public static void start2Activity(Context context) {

        if (!LoginUtil.toLogin((Activity) context)) {
            return;
        }
        context.startActivity(new Intent(context, ManagerQuoteListActivity_new.class));
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public void setTextOrHinden(@IdRes int id, int visible, String text, View.OnClickListener onClickListener) {

        RadioButton radioButton = getView(id);

        radioButton.setVisibility(visible);

        radioButton.setText(text);


        radioButton.setOnClickListener(onClickListener);


    }
}
