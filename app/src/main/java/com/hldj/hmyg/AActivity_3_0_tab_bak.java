//package com.hldj.hmyg;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Keep;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.widget.NestedScrollView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.view.ViewTreeObserver;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.CheckedTextView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.autoscrollview.adapter.ImagePagerAdapter;
//import com.autoscrollview.widget.AutoScrollViewPager;
//import com.autoscrollview.widget.indicator.CirclePageIndicator;
//import com.coorchice.library.SuperTextView;
//import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
//import com.hldj.hmyg.M.BProduceAdapt;
//import com.hldj.hmyg.M.IndexGsonBean;
//import com.hldj.hmyg.Ui.NewsActivity;
//import com.hldj.hmyg.Ui.NoticeActivity_detail;
//import com.hldj.hmyg.Ui.friend.child.PublishActivity;
//import com.hldj.hmyg.application.Data;
//import com.hldj.hmyg.application.MyApplication;
//import com.hldj.hmyg.application.StateBarUtil;
//import com.hldj.hmyg.base.GlobBaseAdapter;
//import com.hldj.hmyg.base.ViewHolders;
//import com.hldj.hmyg.bean.ABanner;
//import com.hldj.hmyg.bean.ArticleBean;
//import com.hldj.hmyg.bean.HomeStore;
//import com.hldj.hmyg.bean.SimpleGsonBean;
//import com.hldj.hmyg.bean.Type;
//import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
//import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
//import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
//import com.hldj.hmyg.buyer.weidet.SwipeViewHeader;
//import com.hldj.hmyg.me.AskToByActivity;
//import com.hldj.hmyg.presenter.AActivityPresenter;
//import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
//import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
//import com.hldj.hmyg.saler.bean.UserPurchase;
//import com.hldj.hmyg.saler.purchase.PurchasePyMapActivity;
//import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserActivity;
//import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
//import com.hldj.hmyg.util.ConstantState;
//import com.hldj.hmyg.util.D;
//import com.hldj.hmyg.util.FUtil;
//import com.hldj.hmyg.util.GsonUtil;
//import com.hldj.hmyg.widget.UPMarqueeView;
//import com.hldj.hmyg.widget.swipeview.MySwipeRefreshLayout;
//import com.hy.utils.GetServerUrl;
//import com.hy.utils.JsonGetInfo;
//import com.hy.utils.ToastUtil;
//import com.tencent.bugly.crashreport.CrashReport;
//import com.white.utils.ScreenUtil;
//import com.white.utils.StringUtil;
//import com.yangfuhai.asimplecachedemo.lib.ACache;
//
//import net.tsz.afinal.FinalHttp;
//import net.tsz.afinal.http.AjaxCallBack;
//import net.tsz.afinal.http.AjaxParams;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import aom.xingguo.huang.banner.MyFragment;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//import static com.hldj.hmyg.R.id.home_title_first;
//import static com.hldj.hmyg.R.id.home_title_qiu_gou;
//
//
///**
// * change a list hellow world
// */
//@Keep
//public class AActivity_3_0_tab_bak extends FragmentActivity implements OnClickListener {
//
//    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
//    private ArrayList<ABanner> aBanners = new ArrayList<ABanner>();// 底部图片轮播 集合
//    ArrayList<HomeStore> url0s = new ArrayList<HomeStore>();
//    private ImagePagerAdapter imagePagerAdapter;
//    private CirclePageIndicator indicator;
//    private AutoScrollViewPager viewPager;
//    private ListView lv_00;
//    private ListView lv_qiu_gou;
//    private ImageView iv_msg;
//    private NestedScrollView scrollView;
//    private Button toTopBtn;// 返回顶部的按钮
//    private ImageView iv_publish;//  发布按钮
//    private int scrollY = 0;// 标记上次滑动位置
//    private View contentView;
//    private final String TAG = "test";
//    private ACache mCache;
//
//
//    private TabLayout tablayout;
//    private TabLayout tablayout1;
//
//    int[] location = new int[2];
//    int[] location2 = new int[2];
//    int[] location3 = new int[2];
//    private TabLayout.OnTabSelectedListener onTabSelectedListener;
//
//
//    long startTime;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        startTime = System.currentTimeMillis();
//        Log.i(TAG, "setContentView start: " + startTime);
//        setContentView(R.layout.activity_a_3_0);
//        Log.i(TAG, "setContentView  end  " + (System.currentTimeMillis() - startTime));
//
//
//
//        Log.i(TAG, "cache add tablayout  start  " + (System.currentTimeMillis() - startTime));
//        mCache = ACache.get(this);
//
//        tablayout = findViewById(R.id.tablayout);
//        tablayout1 = findViewById(R.id.tablayout1);
//
//        tablayout.getTabAt(0).setCustomView(getTabView(0));
//        tablayout.getTabAt(1).setCustomView(getTabView(1));
//        tablayout.getTabAt(2).setCustomView(getTabView(2));
//        tablayout1.getTabAt(0).setCustomView(getTabView(0));
//        tablayout1.getTabAt(1).setCustomView(getTabView(1));
//        tablayout1.getTabAt(2).setCustomView(getTabView(2));
//
//        Log.i(TAG, "cache add tablayout  end  " + (System.currentTimeMillis() - startTime));
//
//        Log.i(TAG, "init views  start  " + (System.currentTimeMillis() - startTime));
////      ToastUtil.showShortToast("bugly 热更新生效");
//        viewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
//        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        indicator.setAlpha((float) 0.6);
////        absviewPager = (AbSlidingPlayView) findViewById(R.id.viewPager_menu);
////        // 设置播放方式为顺序播放
////        absviewPager.setPlayType(1);
////        // 设置播放间隔时间
////        absviewPager.setSleepTime(7500);
//        iv_msg = (ImageView) findViewById(R.id.iv_a_msg);
////		relativeLayout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
////		iv_Capture = (ImageView) findViewById(iv_Capture);
////        iv_home_merchants = (ImageView) findViewById(R.id.iv_home_merchants);
////        iv_home_preferential = (ImageView) findViewById(R.id.iv_home_preferential);
////        iv_fenlei = (ImageView) findViewById(R.id.iv_fenlei);
//
//        lv_00 = (ListView) findViewById(R.id.lv_00);
//        lv_qiu_gou = (ListView) findViewById(R.id.lv_qiu_gou);
//        lv_00.setDivider(null);
//        lv_qiu_gou.setDivider(null);
//        scrollView = (NestedScrollView) findViewById(R.id.rotate_header_scroll_view);
//        if (contentView == null) {
//            contentView = scrollView.getChildAt(0);
//        }
//        toTopBtn = (Button) findViewById(R.id.top_btn);
//        iv_publish = (ImageView) findViewById(R.id.iv_publish_home);
//        toTopBtn.setOnClickListener(this);
//        iv_publish.setOnClickListener(this);
//
//        LayoutParams l_params = new RelativeLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        WindowManager wm = this.getWindowManager();
//        l_params.height = (int) (wm.getDefaultDisplay().getWidth() * 1 / 2);
//        viewPager.setLayoutParams(l_params);
//
//
//        //                tablayout1.getTabAt(tab.getPosition()).select();
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        if (!tablayout1.getTabAt(tab.getPosition()).isSelected()) {
////                            tablayout1.getTabAt(tab.getPosition()).select();
////                        }
////                    }
////                }, 20);
////                ToastUtil.showShortToast("click");
//// 909 - 278  ↑ 滚
////                scrollView.smoothScrollTo(0,scrollView.getScrollY()-  location3[1]  );
//        onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
////                ToastUtil.showShortToast(findViewById(R.id.left1) + "" + "" + tab.getText() + tab.getPosition());
//
//
//                if (tab.getPosition() == 0) {
//                    findViewById(R.id.home_title_first).getLocationOnScreen(location3);
//                } else if (tab.getPosition() == 1) {
//                    findViewById(R.id.home_title_qiu_gou).getLocationOnScreen(location3);
//                } else if (tab.getPosition() == 2) {
//                    findViewById(R.id.home_title_second).getLocationOnScreen(location3);
//                }
//
//
////                tablayout1.getTabAt(tab.getPosition()).select();
//
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                if (!tablayout1.getTabAt(tab.getPosition()).isSelected()) {
////                    tablayout1.getTabAt(tab.getPosition()).select();
////                    tablayout.getTabAt(tab.getPosition()).select();
////                }
//
//                tablayout1.getTabAt(tab.getPosition()).select();
//                tablayout.getTabAt(tab.getPosition()).select();
//
//
//                changeColor(tab.getPosition());
//
////                    }
////                }, 20);
//
//
////                ToastUtil.showShortToast("click");
//                Log.i(TAG, "onClick: " + location3[1]);
//                // 909 - 278  ↑ 滚
////                scrollView.smoothScrollTo(0,scrollView.getScrollY()-  location3[1]  );
//                scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
////                tab.getCustomView().findViewById(R.id.content).setSelected(true);
//
//                CheckedTextView checkedTextView = tab.getCustomView().findViewById(R.id.content);
//
////                tablayout.getChildAt(tab.getPosition()).setSelected(true);
////                tablayout1.getChildAt(tab.getPosition()).setSelected(true);
//
////                checkedTextView.setChecked(true);
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
////                CheckedTextView checkedTextView = tab.getCustomView().findViewById(R.id.content);
//////                checkedTextView.setChecked(false);
////
////                TextView textView = tablayout.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.content);
////                TextView textView1 = tablayout1.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.content);
////
////                if (textView != null) {
////                    textView.setTextColor(getResources().getColor(R.color.text_color666));
////                    textView1.setTextColor(getResources().getColor(R.color.text_color666));
////                }
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        };
//        initView();
//
//        Log.i(TAG, "init views  end  " + (System.currentTimeMillis() - startTime));
//
//
//        if (mCache.getAsString("index") != null && !"".equals(mCache.getAsString("index"))) {
//            LoadCache(mCache.getAsString("index"));
//        }
//        requestData();
////        initData();
////		iv_Capture.setOnClickListener(this);
//        iv_msg.setOnClickListener(this);
////		relativeLayout2.setOnClickListener(this);
//        findViewById(R.id.tv_a_search).setOnClickListener(this);
//
//        initSwipe();
//        setListAtMost();
//
//
////        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//
//        // 默认1 监听
//        tablayout1.addOnTabSelectedListener(onTabSelectedListener);
////        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//
////        findViewById(R.id.left).setOnClickListener(v -> ToastUtil.showShortToast("left"));
////        findViewById(R.id.center).setOnClickListener(v -> ToastUtil.showShortToast("center"));
////        findViewById(R.id.right).setOnClickListener(v -> ToastUtil.showShortToast("right"));
////
////
////        findViewById(R.id.left1).setOnClickListener(v -> ToastUtil.showShortToast("left1"));
////        findViewById(R.id.center1).setOnClickListener(v -> ToastUtil.showShortToast("center1"));
////        findViewById(R.id.right1).setOnClickListener(v -> ToastUtil.showShortToast("right1"));
//
////        tablayout1.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ToastUtil.showShortToast("click");
////                findViewById(R.id.home_title_second).getLocationOnScreen(location3);
////                Log.i(TAG, "onClick: " + location3[1]);
////
////                // 909 - 278  ↑ 滚
//////                scrollView.smoothScrollTo(0,scrollView.getScrollY()-  location3[1]  );
////                scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
////            }
////        });
//
////        tablayout.setOnClickListener(v -> {
////            ToastUtil.showShortToast("click");
////            findViewById(R.id.home_title_first).getLocationOnScreen(location3);
////            Log.i(TAG, "onClick: " + location3[1]);
////
////            // 909 - 278  ↑ 滚
//////                scrollView.smoothScrollTo(0,scrollView.getScrollY()-  location3[1]  );
////            scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
////
////        });
//        Log.i(TAG, "oncreate  end  " + (System.currentTimeMillis() - startTime));
//
//    }
//
//
//    int[] location_0 = new int[2];
//    int[] location_1 = new int[2];
//
//    // 滚动时执行
//    private void attachTablayout2View(TabLayout tablayout, TabLayout tablayout1, View attarchView, View attarchView1, int pos) {
//        if (pos == 1) {
//            attarchView.getLocationOnScreen(location_0);
//            attarchView1.getLocationOnScreen(location_1);
/////  scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
//
//            int y = scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight();
//
//
////            Log.i(TAG, "y -- >  " + y + "  " + location_0[1]);
//
//
//            if (location_0[1] <= location[1] + tablayout.getHeight()) {
//
//
//                if (location_1[1] <= location[1] + tablayout.getHeight()) {
////                    Log.i(TAG, "小于: ");
//
//                    tablayout.clearOnTabSelectedListeners();
//                    tablayout1.clearOnTabSelectedListeners();
//
//                    tablayout.getTabAt(2).select();
//                    tablayout1.getTabAt(2).select();
//                    changeColor(2);
//
//
//                    if (tablayout.getVisibility() == View.VISIBLE) {
//                        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                    } else {
//                        tablayout1.addOnTabSelectedListener(onTabSelectedListener);
//                    }
//                } else {
////                    Log.i(TAG, "小于: ");
//
//                    tablayout.clearOnTabSelectedListeners();
//                    tablayout1.clearOnTabSelectedListeners();
//
//                    tablayout.getTabAt(1).select();
//                    tablayout1.getTabAt(1).select();
//                    changeColor(1);
//
//                    if (tablayout.getVisibility() == View.VISIBLE) {
//                        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                    } else {
//                        tablayout1.addOnTabSelectedListener(onTabSelectedListener);
//                    }
//
//                }
//
//
//            } else {
//                tablayout.clearOnTabSelectedListeners();
//                tablayout1.clearOnTabSelectedListeners();
//
//
//                tablayout.getTabAt(0).select();
//                tablayout1.getTabAt(0).select();
//                changeColor(0);
//                if (tablayout.getVisibility() == View.VISIBLE) {
//                    tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                } else {
//                    tablayout1.addOnTabSelectedListener(onTabSelectedListener);
//                }
//
////                Log.e(TAG, "大于: ");
//            }
//
////            if (location_0[])
//
//        } else if (pos == 2) {
//
//            attarchView.getLocationOnScreen(location_0);
/////  scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
//
//            int y = scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight();
//
//
////            Log.i(TAG, "y -- >  " + y + "  " + location_0[1]);
//
//            if (location_0[1] <= location[1] + tablayout.getHeight()) {
//
////                Log.i(TAG, "小于: ");
//
//                tablayout.clearOnTabSelectedListeners();
//                tablayout1.clearOnTabSelectedListeners();
//
//                tablayout.getTabAt(2).select();
//                tablayout1.getTabAt(2).select();
//
//                tablayout.addOnTabSelectedListener(onTabSelectedListener);
//
//
//            } else {
//                tablayout.clearOnTabSelectedListeners();
//                tablayout1.clearOnTabSelectedListeners();
//
//
//                tablayout.getTabAt(1).select();
//                tablayout1.getTabAt(1).select();
//
//                tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                Log.e(TAG, "大于: ");
//            }
//
////            attarchView.getLocationOnScreen(location_1);
///////  scrollView.scrollTo(0, scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight());
////
////            int y =  scrollView.getScrollY() + location3[1] - location[1] - tablayout.getHeight() ;
////            if (location_1[1] <= y)
////            {
////                tablayout.getTabAt(2).select();
////            }else {
////                tablayout.getTabAt(1).select();
////            }
//        }
//
//
//    }
//
//    private void initSwipe() {
//        MySwipeRefreshLayout swipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swipe_main);
//        swipeRefreshLayout.setLoadmoreEnable(false);
////        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
//
//        SwipeViewHeader swipeViewHeader = new SwipeViewHeader(AActivity_3_0_tab_bak.this);
//        swipeRefreshLayout.setHeaderView(swipeViewHeader);
//        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayout.SHSOnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestData();
//                D.e("==requestData==");
//
//                /**
//                 * Observable.just(event)
//                 .filter(event1 -> event.isOnline)
//                 .map((Function<OnlineEvent, Object>) event12 -> event12.isOnline)
//                 .subscribeOn(Schedulers.io())
//                 .observeOn(AndroidSchedulers.mainThread())
//                 .timeout(500, TimeUnit.MILLISECONDS)
//                 .subscribe((b) -> {
//                 loadHeadImage(getSpB("isLogin"));//加载头像
//                 setRealName(getSpS("userName"), getSpS("realName"));
//                 });
//                 */
//
//                Observable
//                        .timer(1500, TimeUnit.MILLISECONDS)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(delay -> {
//                                    {
//                                        D.e("==delay==" + delay);
//                                        swipeRefreshLayout.finishRefresh();
//                                        swipeViewHeader.setState(3);
//                                    }
//                                }
//                        );
//
//            }
//
//            @Override
//            public void onLoading() {
//            }
//
//            @Override
//            public void onRefreshPulStateChange(float parent, int state) {
//                switch (state) {
//                    case MySwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
////                mViewHeader.setLoaderViewText("下拉刷新");
//                        swipeViewHeader.setState(0);
//                        break;
//                    case MySwipeRefreshLayout.OVER_TRIGGER_POINT:
////                swipeRefreshLayout.setLoaderViewText("松开刷新");
//                        swipeViewHeader.setState(1);
//                        break;
//                    case MySwipeRefreshLayout.START:
////                swipeRefreshLayout.setLoaderViewText("正在刷新");
//                        swipeViewHeader.setState(2);
//                        break;
//                }
//            }
//
//            @Override
//            public void onLoadmorePullStateChange(float v, int i) {
//            }
//        });
////        swipeRefreshLayout.setOnRefreshListener(() ->
////                {
////                    D.e("==refresh==");
////
//////                    onCreate(null);
////                    swipeRefreshLayout.setRefreshing(false);
////                }
////        );
//    }
//
//
//    List<ArticleBean> data = new ArrayList<>();
//    List<View> views = new ArrayList<>();
//
//
//    /**
//     * holder.convertView.setBackgroundColor(Color.WHITE);
//     * UPMarqueeView mUPMarqueeView = holder.getView(upview1);
//     * //设置滚动的单个布局
//     * LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_cjgg, null);
//     * <p>
//     * <p>
//     * mUPMarqueeView.setViews(getViewsByDatas(mActivity,getmIndexGsonBean().data.articleList));
//     *
//     * @param articleList
//     */
//
//    private void initArticles(List<ArticleBean> articleList) {
////        data.addAll(articleList);
////        setView();//设置数据
//        UPMarqueeView upview1 = (UPMarqueeView) findViewById(R.id.upview1);
//        upview1.setViews(getViewsByDatas(AActivity_3_0_tab_bak.this, articleList));
//        /**
//         * 设置item_view的监听
//         */
////        upview1.setOnItemClickListener((position, view) -> ToastUtil.showShortToast("==点击了==" + position + " 个 item"));
//    }
//
//
//    /**
//     * 初始化 今日头条
//     */
//    private void initView() {
//        //商城管理  苗木商城
//        findViewById(R.id.stv_home_1).setOnClickListener(v -> {
////            if (isLogin()) ManagerListActivity_new.start2Activity(AActivity_3_0.this);
////            PurchasePyMapActivity.start2Activity(AActivity_3_0.this);
//            MainActivity.toB();
//
//        });
//        // 发布求购
//        findViewById(R.id.stv_home_2).setOnClickListener(v -> {
////            ToastUtil.showLongToast(" 用户求购 ");
////            BuyForUserActivity.stat2Activity(AActivity_3_0.this);
//            PublishForUserActivity.start2Activity(AActivity_3_0_tab_bak.this);
//
//        });
//        //我的求购
//        findViewById(R.id.stv_home_3).setOnClickListener(v ->
////                NoticeActivity.start2Activity(AActivity_3_0.this)
//                        AskToByActivity.start(AActivity_3_0_tab_bak.this)
//        );
//
//
//        //苗圃管理
//        findViewById(R.id.stv_home_11).setOnClickListener(v -> {
////            if (isLogin()) ManagerListActivity_new.start2Activity(AActivity_3_0.this);
////            PurchasePyMapActivity.start2Activity(AActivity_3_0.this);
//            MainActivity.toD();
//
//        });
//
//        // 发布求购
//        findViewById(R.id.stv_home_22).setOnClickListener(v -> {
////            ToastUtil.showLongToast(" 用户求购 ");
////            BuyForUserActivity.stat2Activity(AActivity_3_0.this);
////            PublishForUserActivity.start2Activity(AActivity_3_0.this);
//            PurchasePyMapActivity.start2Activity(AActivity_3_0_tab_bak.this, null);
//
//
//        });
//        //我的求购
//        findViewById(R.id.stv_home_33).setOnClickListener(v ->
//                {
//                    ManagerQuoteListActivity_new.initLeft = false;
//                    ManagerQuoteListActivity_new.start2Activity(AActivity_3_0_tab_bak.this);
//                }
//        );
//
//
//        findViewById(R.id.iv_home_left).setOnClickListener(v -> NewsActivity.start2Activity(AActivity_3_0_tab_bak.this));
////        findViewById(R.id.iv_home_left).setOnClickListener(v -> NoticeActivity.start2Activity(AActivity_3_0.this));
//        //新闻资讯
////        findViewById(R.id.stv_home_4).setOnClickListener(v -> NewsActivity.start2Activity(AActivity_3_0.this));
//        //采购
//        findViewById(home_title_first).setOnClickListener(v -> PurchasePyMapActivity.start2Activity(AActivity_3_0_tab_bak.this, true));
//        findViewById(home_title_qiu_gou).setOnClickListener(v -> PurchasePyMapActivity.start2Activity(AActivity_3_0_tab_bak.this, false));
//        //苗木商城 更多
//        findViewById(R.id.home_title_second).setOnClickListener(v -> MainActivity.toB());
//        //热门商家
////        findViewById(R.id.home_title_third).setOnClickListener(v -> ToastUtil.showShortToast("更多热门商家正在开发中..."));
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                    Log.i(TAG, "onScrollChange: onScrollChangeonScrollChangeonScrollChange");
//                    tablayout.getLocationOnScreen(location);
//                    tablayout1.getLocationOnScreen(location2);
//                    if (location2[1] <= 168) {
//                        tablayout.setVisibility(View.VISIBLE);
//                        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                        tablayout1.clearOnTabSelectedListeners();
//                    } else {
//                        tablayout.setVisibility(View.GONE);
//                        tablayout.clearOnTabSelectedListeners();
//                        tablayout1.addOnTabSelectedListener(onTabSelectedListener);
//                    }
//                    attachTablayout2View(tablayout, tablayout1, findViewById(R.id.home_title_qiu_gou), findViewById(R.id.home_title_second), 1);
//                }
//            });
//        } else {
//            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                @Override
//                public void onScrollChanged() {
////                Log.i(TAG, "onScrollChange: onScrollChangeonScrollChangeonScrollChange");
//                    tablayout.getLocationOnScreen(location);
//                    tablayout1.getLocationOnScreen(location2);
////                D.w("tablayout.loaction()  --- > " + location[1] + "  y =" + location[1]);
////                D.i("tablayout1.loaction()  --- > " + location2[1] + "  y =" + location2[1]);//168  --  248
//
//                    if (location2[1] <= 168) {
//                        tablayout.setVisibility(View.VISIBLE);
//                        tablayout.addOnTabSelectedListener(onTabSelectedListener);
//                        tablayout1.clearOnTabSelectedListeners();
////                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tablayout.getLayoutParams();
////                    lp.setMargins(30, lp.topMargin - 10, 22, 10);
////                    tablayout.setLayoutParams(lp);
//                    } else {
//
//                        tablayout.setVisibility(View.GONE);
//                        tablayout.clearOnTabSelectedListeners();
//                        tablayout1.addOnTabSelectedListener(onTabSelectedListener);
////                    tablayout.setY(10);
////                    setLayout(tablayout,0,-10);
//                    }
//
//
//                    attachTablayout2View(tablayout, tablayout1, findViewById(R.id.home_title_qiu_gou), findViewById(R.id.home_title_second), 1);
////                attachTablayout2View(tablayout, tablayout1, findViewById(R.id.home_title_second), 2);
////                attachTablayout2View(tablayout, tablayout1, findViewById(R.id.home_title_second), 2);
//
//
//                }
//            });
//
//        }
//
//        // 滚动时触发事件  必定是 tablay 有接口
////        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
////            @Override
////            public void onScrollChanged() {
////
////            }
////
////
//////            @Override
//////            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//////
//////            }
////
////        });
//
//        // http://blog.csdn.net/jiangwei0910410003/article/details/17024287
//        /******************** 监听ScrollView滑动停止 *****************************/
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            private int lastY = 0;
//            private int touchEventId = -9983761;
//            Handler handler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    View scroller = (View) msg.obj;
//                    if (msg.what == touchEventId) {
//                        if (lastY == scroller.getScrollY()) {
//                            handleStop(scroller);
//                        } else {
//                            handler.sendMessageDelayed(handler.obtainMessage(
//                                    touchEventId, scroller), 5);
//                            lastY = scroller.getScrollY();
//                        }
//                    }
//                }
//            };
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    handler.sendMessageDelayed(
//                            handler.obtainMessage(touchEventId, v), 5);
//                }
//
//
//                return false;
//            }
//
//            /**
//             * ScrollView 停止
//             *
//             * @param view
//             */
//
//            private void handleStop(Object view) {
//
//                Log.i(TAG, "handleStop");
//                NestedScrollView scroller = (NestedScrollView) view;
//                scrollY = scroller.getScrollY();
//
//                doOnBorderListener();
//            }
//        });
//        /***********************************************************/
//
//
////        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
////            @Override
////            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                Log.i(TAG, "scrollY: " + scrollY + "  oldScrollY = " + oldScrollY);//scrollY: 421  oldScrollY = 410 ↑
////                Log.i(TAG, "scrollY: " + scrollY + "  oldScrollY = " + oldScrollY);//scrollY: 1099  oldScrollY = 1101 ↓
////
////                if (scrollY - oldScrollY > 0)
////                    doOnBorderListener();
////
////            }
////        });
//
//
//    }
//
//
//    /*
//   * 设置控件所在的位置YY，并且不改变宽高，
//   * XY为绝对位置
//   */
//    public static void setLayout(View view, int x, int y) {
//        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
//        margin.setMargins(x, y, x + margin.width, y + margin.height);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
//        view.setLayoutParams(layoutParams);
//    }
//
//    private boolean isLogin() {
//        if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {//没有登录跳转到登录界面
//            LoginActivity.start2Activity(this);
//            return false;
//
//        } else {
//            return true;
//        }
//
//    }
//
//    /**
//     * ScrollView 的顶部，底部判断：
//     * http://www.trinea.cn/android/on-bottom-load-more-scrollview-impl/
//     * <p>
//     * 其中getChildAt表示得到ScrollView的child View， 因为ScrollView只允许一个child
//     * view，所以contentView.getMeasuredHeight()表示得到子View的高度,
//     * getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。
//     * 当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
//     */
//    private void doOnBorderListener() {
//        Log.i(TAG, ScreenUtil.getScreenViewBottomHeight(scrollView) + "  " + scrollView.getScrollY() + " " + ScreenUtil.getScreenHeight(AActivity_3_0_tab_bak.this));
//
//        // 底部判断
//        if (contentView != null && contentView.getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
//            toTopBtn.setVisibility(View.VISIBLE);
//            Log.i(TAG, "bottom");
//            if (myFragment0 != null)
//                myFragment0.setAutoChange(true);
//        }
//        // 顶部判断
//        else if (scrollView.getScrollY() == 0) {
//            if (myFragment0 != null) myFragment0.setAutoChange(false);
//            toTopBtn.setVisibility(View.GONE);
//            Log.i(TAG, "top");
//        } else if (scrollView.getScrollY() > 500) {
//            toTopBtn.setVisibility(View.VISIBLE);
//            if (myFragment0 != null)
//                myFragment0.setAutoChange(false);
//            Log.i(TAG, "test");
//        }
//
//    }
//
//    private void initData() {
//
//
//        requestData();//请求数据
//
//    }
//
//    private MyFragment myFragment0;
//    private Type type;
//
//    public void requestData() {
//        FinalHttp finalHttp = new FinalHttp();
//        GetServerUrl.addHeaders(finalHttp, false);
//        AjaxParams params = new AjaxParams();
//        params.put("latitude", MyApplication.Userinfo.getString("latitude", ""));
//        params.put("longitude", MyApplication.Userinfo.getString("longitude", ""));
//        finalHttp.post(GetServerUrl.getUrl() + "index", params,
//                new AjaxCallBack<Object>() {
//                    @Override
//                    public void onSuccess(Object t) {
//                        D.e("json= \n" + t);
//                        mCache.remove("index");
//                        mCache.put("index", t.toString());
////                        LoadCache(t.toString());
//                        LoadCache(t.toString());
//                        super.onSuccess(t);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo,
//                                          String strMsg) {
//                        // TODO Auto-generated method stub
//                        if (mCache.getAsString("index") != null
//                                && !"".equals(mCache.getAsString("index"))) {
//                            LoadCache(mCache.getAsString("index"));
//                        }
//                        Toast.makeText(AActivity_3_0_tab_bak.this, R.string.error_net,
//                                Toast.LENGTH_SHORT).show();
//                        super.onFailure(t, errorNo, strMsg);
//                    }
//
//
//                });
//    }
//
//    private void LoadCache(String t) {
//        if (t == null) {
//            ToastUtil.showShortToast("数据请求失败");
//            return;
//        }
//
//        // TODO Auto-generated method stub
//        IndexGsonBean indexGsonBean = null;
//        try {
//            indexGsonBean = GsonUtil.formateJson2Bean(t, IndexGsonBean.class);
//        } catch (Exception e) {
//            CrashReport.postCatchedException(e);  // bugly会将这个throwable上报
//            ToastUtil.showShortToast("数据解析失败");
//            e.printStackTrace();
//        }
//        if (indexGsonBean != null && indexGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
//            initNewList(indexGsonBean);//初始化 采购列表
//            initArticles(indexGsonBean.data.articleList);//初始化  头条新闻
//        }
//        datas.clear();
//        aBanners.clear();
//        try {
//            JSONObject jsonObject = new JSONObject(t);
//            String code = JsonGetInfo.getJsonString(jsonObject,
//                    "code");
//            String msg = JsonGetInfo.getJsonString(jsonObject,
//                    "msg");
//            if (!"".equals(msg)) {
//            }
//            if ("1".equals(code)) {
//
//                JSONArray bannerList = JsonGetInfo
//                        .getJsonArray(JsonGetInfo
//                                .getJSONObject(jsonObject,
//                                        "data"), "bannerList");
//                // 广告
//                // JSONArray bannerList = jsonObject
//                // .getJSONObject("data").getJSONArray(
//                // "bannerList");
//                for (int i = 0; i < bannerList.length(); i++) {
//                    JSONObject jsonObject2 = bannerList
//                            .getJSONObject(i);
//                    HashMap<String, Object> hMap = new HashMap<String, Object>();
//                    hMap.put("isNewRecord", JsonGetInfo
//                            .getJsonBoolean(jsonObject2,
//                                    "isNewRecord"));
//                    hMap.put("delFlag", JsonGetInfo
//                            .getJsonString(jsonObject2,
//                                    "delFlag"));
//                    hMap.put("url", JsonGetInfo.getJsonString(
//                            JsonGetInfo.getJSONObject(
//                                    jsonObject2, "imageJson"),
//                            "url"));
//                    hMap.put("ossThumbnailImagePath",
//                            JsonGetInfo.getJsonString(
//                                    JsonGetInfo.getJSONObject(
//                                            jsonObject2,
//                                            "imageJson"),
//                                    "ossThumbnailImagePath"));
//                    hMap.put("ossMediumImagePath", JsonGetInfo
//                            .getJsonString(JsonGetInfo
//                                            .getJSONObject(jsonObject2,
//                                                    "imageJson"),
//                                    "ossMediumImagePath"));
//                    hMap.put("ossLargeImagePath", JsonGetInfo
//                            .getJsonString(JsonGetInfo
//                                            .getJSONObject(jsonObject2,
//                                                    "imageJson"),
//                                    "ossLargeImagePath"));
//                    hMap.put("href", JsonGetInfo.getJsonString(
//                            jsonObject2, "href"));
//                    hMap.put("name", JsonGetInfo.getJsonString(
//                            jsonObject2, "name"));
//
//                    ABanner aBanner = new ABanner();
//                    aBanner.setNewRecord(JsonGetInfo
//                            .getJsonBoolean(jsonObject2,
//                                    "isNewRecord"));
//                    aBanner.setUrl(JsonGetInfo.getJsonString(
//                            JsonGetInfo.getJSONObject(
//                                    jsonObject2, "imageJson"),
//                            "url"));
//                    aBanner.setOssThumbnailImagePath(JsonGetInfo
//                            .getJsonString(JsonGetInfo
//                                            .getJSONObject(jsonObject2,
//                                                    "imageJson"),
//                                    "ossThumbnailImagePath"));
//                    aBanner.setOssLargeImagePath(JsonGetInfo
//                            .getJsonString(JsonGetInfo
//                                            .getJSONObject(jsonObject2,
//                                                    "imageJson"),
//                                    "ossLargeImagePath"));
//                    aBanner.setOssMediumImagePath(JsonGetInfo
//                            .getJsonString(JsonGetInfo
//                                            .getJSONObject(jsonObject2,
//                                                    "imageJson"),
//                                    "ossMediumImagePath"));
//                    datas.add(hMap);
//                    aBanners.add(aBanner);
//                }
//                if (datas.size() > 0) {
//                    initViewPager();
//                    // initAbsViewPager();
//                }
//
//
//                // 商铺
//                // JSONArray storeList =
//                // jsonObject.getJSONObject(
//                // "data").getJSONArray("storeList");
//                JSONArray storeList = JsonGetInfo.getJsonArray(
//                        JsonGetInfo.getJSONObject(jsonObject,
//                                "data"), "storeList");
//                url0s.clear();
//                for (int i = 0; i < storeList.length(); i++) {
//                    JSONObject jsonObject2 = storeList
//                            .getJSONObject(i);
//                    HomeStore a_first_product = new HomeStore(
//                            JsonGetInfo.getJsonString(
//                                    jsonObject2, "id"),
//                            JsonGetInfo.getJsonString(
//                                    jsonObject2, "code"),
//                            JsonGetInfo.getJsonString(
//                                    jsonObject2, "logoUrl"),
//                            JsonGetInfo.getJsonString(
//                                    jsonObject2, "id"),
//                            JsonGetInfo.getJsonString(
//                                    jsonObject2, "name"));
//                    url0s.add(a_first_product);
//                }
//                FragmentTransaction ft = getSupportFragmentManager()
//                        .beginTransaction();
//                if (url0s.size() > 0) {
//                    if (url0s.size() % MyFragment.Num != 0) {
//                        int array = MyFragment.Num
//                                - url0s.size() % MyFragment.Num;
//                        for (int i = 0; i < array; i++) {
//                            HomeStore a_first_product = new HomeStore("", "", "", "", "");
//                            url0s.add(a_first_product);
//                        }
//                    }
//                    myFragment0 = new MyFragment();
//                    myFragment0.setUrls(url0s);
//                    ft.add(R.id.con0, myFragment0);
////                    myFragment0.setAutoChange(true);
////                                    iv_home_merchants  .setVisibility(View.VISIBLE);
//                }
//                ft.commitAllowingStateLoss();
//
//
//            }
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            CrashReport.postCatchedException(e);  // bugly会将这个throwable上报
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 新增的2个数据列表
//     *
//     * @param indexGsonBean
//     */
//    private void initNewList(IndexGsonBean indexGsonBean) {
//        try {//采购项目
//
//            View view = findViewById(R.id.ll_caigou_parent);
////
//            if (indexGsonBean.data.purchaseList.size() != 0) {
//                view.setVisibility(View.VISIBLE);
//                PurchaseListAdapter adapter = new PurchaseListAdapter(AActivity_3_0_tab_bak.this, indexGsonBean.data.purchaseList, R.layout.list_item_purchase_list_new);
//                lv_00.setAdapter(adapter);
//                D.e("VISIBLE");
//            } else {
//                view.setVisibility(View.VISIBLE);
//                ((ViewGroup) findViewById(R.id.home_title_first).getParent()).setVisibility(View.VISIBLE);
//                D.e("GONE");
//            }
//
//        } catch (Exception e) {
//            findViewById(R.id.ll_caigou_parent).setVisibility(View.VISIBLE);
//            D.e("=============没有采购列表，或者采购数据异常===============");
//            e.printStackTrace();
//        }
//
//        try {//采购项目
//
//            View view = findViewById(R.id.ll_qiu_gou_parent);
////
//            if (indexGsonBean.data.userPurchaseList.size() != 0) {
////            if (true) {
//                view.setVisibility(View.VISIBLE);
////                PurchaseListAdapter adapter = new PurchaseListAdapter(AActivity_3_0.this, indexGsonBean.data.purchaseList, R.layout.item_buy_for_user);
//
////                List<UserPurchase> ars = new ArrayList<>();
////                ars.add("13213");
////                ars.add("13213");
////                ars.add("13213");
//                lv_qiu_gou.setAdapter(new GlobBaseAdapter<UserPurchase>(AActivity_3_0_tab_bak.this, indexGsonBean.data.userPurchaseList, R.layout.item_buy_for_user) {
//                    @Override
//                    public void setConverView(ViewHolders myViewHolder, UserPurchase s, int position) {
//                        Log.i(TAG, ": " + s.name);
//
//                        doConvert(myViewHolder, s, AActivity_3_0_tab_bak.this);
//
//                    }
//                });
//                D.e("VISIBLE");
//            } else {
//                view.setVisibility(View.VISIBLE);
//                ((ViewGroup) findViewById(R.id.home_title_first).getParent()).setVisibility(View.VISIBLE);
//                D.e("GONE");
//            }
//
//        } catch (Exception e) {
//            findViewById(R.id.ll_caigou_parent).setVisibility(View.VISIBLE);
//            D.e("=============没有采购列表，或者采购数据异常===============");
//            e.printStackTrace();
//        }
//
//
//        try {
//            if (indexGsonBean.data.seedlingList.size() != 0) {
//                findViewById(R.id.ll_tuijian_parent).setVisibility(View.VISIBLE);
//                BProduceAdapt bProduceAdapt = new BProduceAdapt(AActivity_3_0_tab_bak.this, indexGsonBean.data.seedlingList, R.layout.list_view_seedling_new);
//                ((ListView) findViewById(R.id.lv_00_store)).setAdapter(bProduceAdapt);
//            } else {
//                findViewById(R.id.ll_tuijian_parent).setVisibility(View.VISIBLE);
//            }
//
//        } catch (Exception e) {
//            findViewById(R.id.ll_tuijian_parent).setVisibility(View.VISIBLE);
//            D.e("=============没有推荐列表，或者数据异常===============");
//
//            e.printStackTrace();
//        }
//
//        {  // 根据list 显示 title  并且设置点击事件
//            TextView home_title_first = (TextView) findViewById(R.id.home_title_first);
//            TextView home_title_qiu_gou = (TextView) findViewById(R.id.home_title_qiu_gou);
//            TextView home_title_second = (TextView) findViewById(R.id.home_title_second);
//            TextView home_title_third = (TextView) findViewById(R.id.home_title_third);
//            TextView tv_titles[] = new TextView[]{home_title_first, home_title_qiu_gou, home_title_second, home_title_third};
//
//            View v1 = findViewById(R.id.home_title_first);
//            View v1_qiugou = findViewById(R.id.home_title_qiu_gou);
//            View v2 = findViewById(R.id.home_title_second);
//            View v3 = findViewById(R.id.home_title_third);
//            View views[] = new View[]{v1, v1_qiugou, v2, v3};
//
//            try {
//                autoSetTitles(tv_titles, indexGsonBean.data.titleList, views);
//            } catch (Exception e) {
//                D.e("=============没有title 列表===============");
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//
//    /**
//     * 初始化 顶部
//     */
//    private void initViewPager() {
//
//        if (imagePagerAdapter != null) {
//            imagePagerAdapter.notifyDataSetChanged();
//            return;
//        }
//        imagePagerAdapter = new ImagePagerAdapter(AActivity_3_0_tab_bak.this, datas);
//        viewPager.setAdapter(imagePagerAdapter);
//        indicator.setViewPager(viewPager);
//        // imagePagerAdapter.notifyDataSetChanged();
//        // indicator.setRadius(5);
//        indicator.setOrientation(LinearLayout.HORIZONTAL);
//        // indicator.setStrokeWidth(5);
//        // indicator.setSnap(true);
//        viewPager.setInterval(3500);
//        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
//        viewPager
//                .setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);
//        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
//        viewPager.setCycle(true);
//        viewPager.setBorderAnimation(true);
//        viewPager.startAutoScroll();
//    }
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.top_btn:
//                scrollView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        scrollView.fullScroll(ScrollView.FOCUS_UP);
//                        scrollView.smoothScrollTo(0, 0);
//                        scrollY = 0;
//                    }
//                });
//                toTopBtn.setVisibility(View.GONE);
//                break;
//            case R.id.iv_publish_home:
//
//                Log.i(TAG, "-----首页发布----- onClick: iv_publish");
//
////                ToastUtil.showLongToast("首页发布");
//
//
//                publish(AActivity_3_0_tab_bak.this);
//
//
//                break;
//
//            case R.id.iv_a_msg:
//
//
//                AActivityPresenter.isShowRead = false;
////              DialogActivitySecond.start2Activity(this,"8e5aa65a2c374de99662dcf6e7e399a9",new PurchaseItemBean_new());
//
//
//                // TODO Auto-generated method stub
////                Intent intent1 = new Intent(AActivity_3_0.this,
////                        StorePurchaseListActivitySecond.class);
////                intent1.putExtra("purchaseFormId", "2bb0859e7d094314a1a162a82a4fa408");
//////                intent1.putExtra("purchaseFormId", "8e5aa65a2c374de99662dcf6e7e399a9");
//////                intent.putExtra("title", item.num);
////                startActivity(intent1);
//
//
////                DialogActivity.start(this);
//
//
//                if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {//没有登录跳转到登录界面
//                    LoginActivity.start2Activity(this);
//                    return;
//                }
//                Intent toMessageListActivity = new Intent(AActivity_3_0_tab_bak.this,
//                        MessageListActivity.class);
//                startActivity(toMessageListActivity);
//                getParent().overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_right);
////
////
////                D.w("=================发送自定义推送消息 start===================");
////
////
//////                JpushUtil.sendCustommPush();
////
////
////                D.w("=================发送自定义推送消息 end===================");
//
//                break;
//
//            case R.id.tv_a_search://搜索按钮 --- new
//                //// TODO: 2017/4/10  需要添加 共享动画
////                Intent intent = new Intent(AActivity_3_0.this, PurchaseSearchListActivity.class);
////                intent.putExtra("from", "AActivity");
////                startActivityForResult(intent, 1);
//                PurchaseSearchListActivity.start(AActivity_3_0_tab_bak.this, PurchaseSearchListActivity.FROM_HOME);
//
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        if (resultCode == 2) {
//            String decodeResult = data.getStringExtra("decodeResult");
//            // Toast.makeText(AActivity.this,
//            // "此功能预留暂不开放，解析成功，结果为：" + decodeResult, Toast.LENGTH_SHORT)
//            // .show();
//            if (StringUtil.isHttpUrlPicPath(decodeResult)) {
//                Intent toWebActivity3 = new Intent(AActivity_3_0_tab_bak.this,
//                        WebActivity.class);
//                toWebActivity3.putExtra("title", "标题");
//                toWebActivity3.putExtra("url", decodeResult);
//                startActivityForResult(toWebActivity3, 1);
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        D.e("====onResume====");
////        setListAtMost();
//        scrollView.postDelayed(() -> {
//            scrollView.scrollTo(0, scrollY);
////            scrollView.smoothScrollBy(0, scrollY);
////            ToastUtil.showShortToast("smoothY=" + currenY);
//        }, 30);
//
//
//        setStatusBars();
//
//
//        StorePurchaseListActivity.shouldShow = true;
//
//        Log.i(TAG, "AActivityPresenter.isShowRead: " + AActivityPresenter.isShowRead);
//        if (!AActivityPresenter.isShowRead) {
//            AActivityPresenter.requestUnReadCount(new HandlerAjaxCallBack() {
//                @Override
//                public void onRealSuccess(SimpleGsonBean gsonBean) {
////                    ToastUtil.showLongToast(gsonBean.msg + "   unReadCount=" + gsonBean.getData().unReadCount);
//                    AActivityPresenter.isShowRead = (gsonBean.getData().unReadCount != 0);
//                    iv_msg.setSelected(AActivityPresenter.isShowRead);
//                    Log.i(TAG, "AActivityPresenter.isShowRead : " + AActivityPresenter.isShowRead);
//                }
//            });
//        }
//    }
//
//
////    int currenY = 0;
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        D.e("====onPause====");
////        ToastUtil.showShortToast("currentY=" + currenY);
////        currenY = scrollView.getScrollY();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        D.e("====onDestroy====");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        D.e("====onRestart====");
//    }
//
//    public void setListAtMost() {
//        setListViewHeightBasedOnChildren((ListView) findViewById(R.id.lv_00_store));
//        setListViewHeightBasedOnChildren((ListView) findViewById(R.id.lv_00));
//    }
//
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
//
//
//    public void autoSetTitles(TextView[] tvs, List<IndexGsonBean.TitleBean> list, View[] views) {
//        if (list.size() == 0) {
//            //all gone
//            for (int i = 0; i < tvs.length; i++) {
//                ((ViewGroup) tvs[i].getParent()).setVisibility(View.GONE);
//            }
//
//        } else {
//            for (int i = 0; i < tvs.length; i++) {
//                tvs[i].setText("  ▎" + list.get(i).title);
////              ((ViewGroup) tvs[i].getParent()).setVisibility(View.VISIBLE);
//
//                if (list.get(i).isClick) {
//                    ((SuperTextView) views[i]).setShowState(true);
//                } else {
//                    ((SuperTextView) views[i]).setShowState(false);
////                    views[i].setVisibility(View.GONE);
//                }
//
//            }
//
//
//        }
//    }
//
//
//    public static List<View> getViewsByDatas(Activity aActivity, List<ArticleBean> data) {
//        List<View> views = new ArrayList<>();
//        for (int i = 0; i < data.size(); i = i + 1) {
//            final int position = i;
//            //设置滚动的单个布局
//            LinearLayout moreView = (LinearLayout) LayoutInflater.from(aActivity).inflate(R.layout.item_home_cjgg, null);
//            //初始化布局的控件
//            SuperTextView tv1 = (SuperTextView) moreView.findViewById(R.id.tv_taggle1);
//            SuperTextView tv2 = (SuperTextView) moreView.findViewById(R.id.tv_taggle2);
//            SuperTextView tv3 = (SuperTextView) moreView.findViewById(R.id.tv_taggle3);
//            /**
//             * 设置监听
//             */
//            moreView.findViewById(R.id.tv_taggle1).setOnClickListener(view -> {
//                String url = Data.getNotices_and_news_url_only_by_id(data.get(position).id);
//                D.e("url=" + url);
//                NoticeActivity_detail.start2Activity(aActivity, url);
//            });
//            /**
//             * 设置监听
//             */
//            moreView.findViewById(R.id.tv_taggle2).setOnClickListener(view -> {
//                String url = Data.getNotices_and_news_url_only_by_id(data.get(position + 1).id);
//                D.e("url=" + url);
//                NoticeActivity_detail.start2Activity(aActivity, url);
//            });
//            //进行对控件赋值
//            tv1.setText(data.get(i).title);
//
//            if (data.get(i).isNew) {
//                tv1.setShowState(data.get(i).isNew);
//                tv1.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
//            } else {
//                tv1.setPadding(10, 0, 0, 0);
//                tv1.setShowState(data.get(i).isNew);
//            }
//
//            if (data.size() > i + 1) {
//                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
//                tv2.setText(data.get(i + 1).title);
//
//                if (data.get(i + 1).isNew) {
//                    tv2.setShowState(true);
//                    tv2.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
//                } else {
//                    tv2.setPadding(10, 0, 0, 0);
//                    tv2.setShowState(false);
//                }
//            } else {
//                moreView.findViewById(R.id.tv_taggle2).setVisibility(View.GONE);
//            }
//
////            if (data.size() > i + 2) {
////
////                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
////                tv3.setText(data.get(i + 2).title);
////
////                if (data.get(i + 2).isNew) {
////                    tv3.setShowState(true);
////                    tv3.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
////                } else {
////                    tv3.setPadding(10, 0, 0, 0);
////                    tv3.setShowState(false);
////                }
////
////            }
//
////            else {
////                moreView.findViewById(R.id.tv_taggle3).setVisibility(View.GONE);
////            }
//
//
//            //添加到循环滚动数组里面去
//            views.add(moreView);
//        }
//
//        return views;
//    }
//
//    private void setStatusBars() {
//        try {
//            StateBarUtil.setStatusTranslater(MainActivity.instance, true);
//            StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
//            StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }
//
//
//    /*  发布  按钮   选择发布 苗木圈  发布 供应   */
//    private void publish(Activity mActivity) {
//
//
//        if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {
//            Intent intent = new Intent(mActivity, LoginActivity.class);
//            startActivityForResult(intent, 4);
//            ToastUtil.showLongToast("请先登录^_^哦");
//            Log.i(TAG, "是否登录");
//            return;
//        }
//        Log.i(TAG, "是否登录" + MyApplication.Userinfo.getBoolean("isLogin", false));
////https://www.jianshu.com/p/4c5fafe08fa7
//
//        Log.i(TAG, "-----首页发布-- publish(Activity mActivity)--- ");
//        CommonDialogFragment1.newInstance(context -> {
//            if (context == null) {
//                context = mActivity;
//            }
//            Dialog dialog1 = new Dialog(context);
//            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog1.setContentView(R.layout.item_home_type);
//            dialog1.findViewById(R.id.iv_left).setOnClickListener(v1 -> {
////                            ToastUtil.showLongToast("left");
//                dialog1.dismiss();
////                PublishActivity.start(mActivity, PublishActivity.ALL);
////                SaveSeedlingActivity.start2Activity(AActivity_3_0.this);
//                MainActivity.toD();
//
//            });
//            dialog1.findViewById(R.id.iv_center).setOnClickListener(v1 -> {
////                            ToastUtil.showLongToast("left");
//                dialog1.dismiss();
////                PublishActivity.start(mActivity, PublishActivity.ALL);
//                PublishForUserActivity.start2Activity(AActivity_3_0_tab_bak.this);
//            });
//            dialog1.findViewById(R.id.iv_right).setOnClickListener(v1 -> {
////                            ToastUtil.showLongToast("right");
//                dialog1.dismiss();
//                PublishActivity.start(mActivity, PublishActivity.ALL);
//            });
//
//            return dialog1;
//        }, true).show(getSupportFragmentManager(), TAG);
//
//    }
//
//
//    public static void doConvert(ViewHolders helper, UserPurchase item, AActivity_3_0_tab_bak mActivity) {
//
////        helper.getConvertView().setOnClickListener(v -> {
//        helper.getConvertView().setOnClickListener(v -> PublishForUserDetailActivity.start2Activity(mActivity, item.id, item.ownerId));
////
////        });
//
//
//        helper.setText(R.id.title, FUtil.$_zero(item.name + ""));
//
//
//        helper.setText(R.id.shuliang, FUtil.$_zero(item.count + "/" + item.unitTypeName));
//
////        helper.setText(R.id.shuliang, FUtil.$_zero(item.count + ""));
//
//        if (TextUtils.isEmpty(FUtil.$_zero_2_null(item.quoteCountJson))) {
//            helper.setText(R.id.qubaojia, "暂无报价");
//            helper.setTextColorRes(R.id.qubaojia, R.color.text_colorccc);
//        } else {
//            helper.setText(R.id.qubaojia, FUtil.$(item.quoteCountJson) + "条报价");
//            helper.setTextColorRes(R.id.qubaojia, R.color.main_color);
//        }
//        helper.setText(R.id.space_text, item.specText);
//        helper.setText(R.id.city, "用苗地:" + item.cityName);
//        helper.setText(R.id.update_time, "结束时间:" + item.closeDateStr + "");
//
//        if (helper.getView(R.id.state) != null)
////         helper.getView(R.id.state).setVisibility(item.attrData.isUserQuoted? View.VISIBLE:View.GONE);
//            helper.setVisible(R.id.state, item.attrData.isUserQuoted);
//        //                    this.rootView = rootView;
////                    this.title = (TextView) rootView.findViewById(R.id.title);
////                    this.shuliang = (TextView) rootView.findViewById(R.id.shuliang);
////                    this.qubaojia = (TextView) rootView.findViewById(R.id.qubaojia);
////                    this.space_text = (TextView) rootView.findViewById(R.id.space_text);
////                    this.city = (TextView) rootView.findViewById(R.id.city);
////                    this.update_time = (TextView) rootView.findViewById(R.id.update_time);
////                    this.bao_jia_num = (TextView) rootView.findViewById(R.id.bao_jia_num);
////                    this.state = (ImageView) rootView.findViewById(R.id.state);
//
//    }
//
//
//    public View getTabView(int position) {
//        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.ic);
//        if (position == 0)
//            imageView.setImageResource(R.drawable.ptzc_selecter);
//        if (position == 1)
//            imageView.setImageResource(R.drawable.grqg_selecter);
//        if (position == 2)
//            imageView.setImageResource(R.drawable.mmtj_selecter);
//
//        TextView txt_title = (TextView) view.findViewById(R.id.content);
//
//
//        if (position == 0) {
//            txt_title.setText("平台直采");
//            txt_title.setTextColor(getResources().getColor(R.color.main_color));
//
//        }
//        if (position == 1) {
//            txt_title.setText("个人求购");
//            txt_title.setTextColor(getResources().getColor(R.color.text_color666));
//        }
//        if (position == 2) {
//            txt_title.setText("苗木推荐");
//            txt_title.setTextColor(getResources().getColor(R.color.text_color666));
//
//        }
//
//        return view;
//    }
//
//
//    public void changeColor(int pos) {
//
//        for (int i = 0; i < 3; i++) {
//            if (pos == i) {
//                TextView textView = tablayout.getTabAt(i).getCustomView().findViewById(R.id.content);
//                TextView textView1 = tablayout1.getTabAt(i).getCustomView().findViewById(R.id.content);
//                if (textView != null) {
//                    textView.setTextColor(getResources().getColor(R.color.main_color));
//                    textView1.setTextColor(getResources().getColor(R.color.main_color));
//                }
//            } else {
//                TextView textView = tablayout.getTabAt(i).getCustomView().findViewById(R.id.content);
//                TextView textView1 = tablayout1.getTabAt(i).getCustomView().findViewById(R.id.content);
//                if (textView != null) {
//                    textView.setTextColor(getResources().getColor(R.color.text_color666));
//                    textView1.setTextColor(getResources().getColor(R.color.text_color666));
//                }
//            }
//        }
//
//
//    }
//}
