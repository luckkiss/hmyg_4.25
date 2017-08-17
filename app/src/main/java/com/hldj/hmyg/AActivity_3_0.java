package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.autoscrollview.adapter.ImagePagerAdapter;
import com.autoscrollview.widget.AutoScrollViewPager;
import com.autoscrollview.widget.indicator.CirclePageIndicator;
import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.M.IndexGsonBean;
import com.hldj.hmyg.Ui.NewsActivity;
import com.hldj.hmyg.Ui.NoticeActivity;
import com.hldj.hmyg.Ui.NoticeActivity_detail;
import com.hldj.hmyg.adapter.TypeAdapter;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.ABanner;
import com.hldj.hmyg.bean.ArticleBean;
import com.hldj.hmyg.bean.HomeFunction;
import com.hldj.hmyg.bean.HomeStore;
import com.hldj.hmyg.bean.Type;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.buyer.weidet.SwipeViewHeader;
import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
import com.hldj.hmyg.saler.purchase.PurchasePyMapActivity;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.NotProguard;
import com.hldj.hmyg.widget.MySwipeRefreshLayout;
import com.hldj.hmyg.widget.UPMarqueeView;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;
import com.javis.ab.view.AbSlidingPlayView;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;
import com.white.utils.ScreenUtil;
import com.white.utils.StringUtil;
import com.yangfuhai.asimplecachedemo.lib.ACache;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aom.xingguo.huang.banner.MyFragment;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.hwang.library.widgit.SmartRefreshLayout;


/**
 * change a list hellow world
 */
@NotProguard
@SuppressLint("NewApi")
public class AActivity_3_0 extends FragmentActivity implements OnClickListener {

    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
    private ArrayList<ABanner> aBanners = new ArrayList<ABanner>();// 底部图片轮播 集合
    private ArrayList<Type> gd_datas = new ArrayList<Type>();
    private ArrayList<Type> gd_home_pay_datas = new ArrayList<Type>();
    private ArrayList<HomeFunction> home_functions = new ArrayList<HomeFunction>();
    ArrayList<HomeStore> url0s = new ArrayList<HomeStore>();
    private ArrayList<HashMap<String, Object>> lv_datas = new ArrayList<HashMap<String, Object>>();
    private ImagePagerAdapter imagePagerAdapter;
    private CirclePageIndicator indicator;
    private AutoScrollViewPager viewPager;
    private GridView gd_00;
    private GridView gd_01;
    private GridView gd;
    private ListView lv_00;
    //	private ImageView iv_Capture;//扫描二维码
    private ImageView iv_msg;
    private DrawerLayout dl_content;
    //    private ImageView iv_home_merchants;//热门商家
//    private ImageView iv_home_preferential;
    private RelativeLayout relativeLayout2;//
    private PtrClassicFrameLayout mPtrFrame;
    private NestedScrollView scrollView;
    private Button toTopBtn;// 返回顶部的按钮
    private int scrollY = 0;// 标记上次滑动位置
    private View contentView;
    private final String TAG = "test";
    private ImageView iv_fuwu;
    //    private ImageView iv_fenlei;
    private LinearLayout ll_fenlei;
    private ACache mCache;
    private TypeAdapter myadapter;
    private AbSlidingPlayView absviewPager; // 底部viewpager 轮播控件。。。封装好。
    private ArrayList allListView;
    private SmartRefreshLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_3_0);
        mCache = ACache.get(this);


//      ToastUtil.showShortToast("bugly 热更新生效");
        viewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setAlpha((float) 0.6);
//        absviewPager = (AbSlidingPlayView) findViewById(R.id.viewPager_menu);
//        // 设置播放方式为顺序播放
//        absviewPager.setPlayType(1);
//        // 设置播放间隔时间
//        absviewPager.setSleepTime(7500);
        iv_msg = (ImageView) findViewById(R.id.iv_a_msg);
//		relativeLayout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
//		iv_Capture = (ImageView) findViewById(iv_Capture);
//        iv_home_merchants = (ImageView) findViewById(R.id.iv_home_merchants);
//        iv_home_preferential = (ImageView) findViewById(R.id.iv_home_preferential);
        iv_fuwu = (ImageView) findViewById(R.id.iv_fuwu);
//        iv_fenlei = (ImageView) findViewById(R.id.iv_fenlei);
        gd = (GridView) findViewById(R.id.gd);
        gd_01 = (GridView) findViewById(R.id.gd_01);
        gd_00 = (GridView) findViewById(R.id.gd_00);
        lv_00 = (ListView) findViewById(R.id.lv_00);
        ll_fenlei = (LinearLayout) findViewById(R.id.ll_fenlei);
        lv_00.setDivider(null);
        scrollView = (NestedScrollView) findViewById(R.id.rotate_header_scroll_view);
        if (contentView == null) {
            contentView = scrollView.getChildAt(0);
        }
        toTopBtn = (Button) findViewById(R.id.top_btn);
        toTopBtn.setOnClickListener(this);

        LayoutParams l_params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        WindowManager wm = this.getWindowManager();
        l_params.height = wm.getDefaultDisplay().getWidth() * 1 / 2;
        viewPager.setLayoutParams(l_params);
        initView();

        if (mCache.getAsString("index") != null && !"".equals(mCache.getAsString("index"))) {
            LoadCache(mCache.getAsString("index"));
        }
        requestData();
//        initData();
//		iv_Capture.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
//		relativeLayout2.setOnClickListener(this);
        findViewById(R.id.tv_a_search).setOnClickListener(this);

        initSwipe();

    }

    private void initSwipe() {
        MySwipeRefreshLayout swipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swipe_main);
        swipeRefreshLayout.setLoadmoreEnable(false);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

        SwipeViewHeader swipeViewHeader = new SwipeViewHeader(AActivity_3_0.this);
        swipeRefreshLayout.setHeaderView(swipeViewHeader);
        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
                D.e("==requestData==");

                /**
                 * Observable.just(event)
                 .filter(event1 -> event.isOnline)
                 .map((Function<OnlineEvent, Object>) event12 -> event12.isOnline)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .timeout(500, TimeUnit.MILLISECONDS)
                 .subscribe((b) -> {
                 loadHeadImage(getSpB("isLogin"));//加载头像
                 setRealName(getSpS("userName"), getSpS("realName"));
                 });
                 */

                Observable
                        .timer(1500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(delay -> {
                                    {
                                        D.e("==delay==" + delay);
                                        swipeRefreshLayout.finishRefresh();
                                        swipeViewHeader.setState(3);
                                    }
                                }
                        );

            }

            @Override
            public void onLoading() {
            }

            @Override
            public void onRefreshPulStateChange(float parent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
//                mViewHeader.setLoaderViewText("下拉刷新");
                        swipeViewHeader.setState(0);
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
//                swipeRefreshLayout.setLoaderViewText("松开刷新");
                        swipeViewHeader.setState(1);
                        break;
                    case SHSwipeRefreshLayout.START:
//                swipeRefreshLayout.setLoaderViewText("正在刷新");
                        swipeViewHeader.setState(2);
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float v, int i) {
            }
        });
//        swipeRefreshLayout.setOnRefreshListener(() ->
//                {
//                    D.e("==refresh==");
//
////                    onCreate(null);
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//        );
    }


    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_home_cjgg, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv_taggle1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv_taggle2);

            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle1).setOnClickListener(view -> {
                String url = GetServerUrl.getHtmlUrl() + "article/detail/" + data.get(position).id + ".html?isHeader=true";
                D.e("url=" + url);
                // static String API_01 = "http://api.hmeg.cn/";
                NoticeActivity_detail.start2Activity(AActivity_3_0.this, url);
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle2).setOnClickListener(view -> {
                String url = GetServerUrl.getHtmlUrl() + "article/detail/" + data.get(position + 1).id + ".html?isHeader=true";
                D.e("url=" + url);
                NoticeActivity_detail.start2Activity(AActivity_3_0.this, url);
            });
            //进行对控件赋值
            tv1.setText(data.get(i).title);
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).title);
            } else {
                moreView.findViewById(R.id.tv_taggle2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    List<ArticleBean> data = new ArrayList<>();
    List<View> views = new ArrayList<>();


    /**
     * holder.convertView.setBackgroundColor(Color.WHITE);
     * UPMarqueeView mUPMarqueeView = holder.getView(upview1);
     * //设置滚动的单个布局
     * LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_cjgg, null);
     * <p>
     * <p>
     * mUPMarqueeView.setViews(getViewsByDatas(mActivity,getmIndexGsonBean().data.articleList));
     *
     * @param articleList
     */

    private void initArticles(List<ArticleBean> articleList) {
//        data.addAll(articleList);
//        setView();//设置数据
        UPMarqueeView upview1 = (UPMarqueeView) findViewById(R.id.upview1);
        upview1.setViews(AActivity_3_0_alibaba.getViewsByDatas(AActivity_3_0.this, articleList));
        /**
         * 设置item_view的监听
         */
//        upview1.setOnItemClickListener((position, view) -> ToastUtil.showShortToast("==点击了==" + position + " 个 item"));
    }


    /**
     * 初始化 今日头条
     */
    private void initView() {
        //商城管理
        findViewById(R.id.stv_home_1).setOnClickListener(v -> {
            if (isLogin()) ManagerListActivity_new.start2Activity(AActivity_3_0.this);
        });
        //快速报价
        findViewById(R.id.stv_home_2).setOnClickListener(v -> PurchasePyMapActivity.start2Activity(AActivity_3_0.this));
        ////成交公告
        findViewById(R.id.stv_home_3).setOnClickListener(v -> NoticeActivity.start2Activity(AActivity_3_0.this));
        findViewById(R.id.iv_home_left).setOnClickListener(v -> NoticeActivity.start2Activity(AActivity_3_0.this));
        //新闻资讯
        findViewById(R.id.stv_home_4).setOnClickListener(v -> NewsActivity.start2Activity(AActivity_3_0.this));
        //采购
        findViewById(R.id.home_title_first).setOnClickListener(v -> PurchasePyMapActivity.start2Activity(AActivity_3_0.this));
        //苗木商城 更多
        findViewById(R.id.home_title_second).setOnClickListener(v -> MainActivity.toB());
        //热门商家
        findViewById(R.id.home_title_third).setOnClickListener(v -> ToastUtil.showShortToast("更多热门商家正在开发中..."));


        // http://blog.csdn.net/jiangwei0910410003/article/details/17024287
        /******************** 监听ScrollView滑动停止 *****************************/
        scrollView.setOnTouchListener(new OnTouchListener() {
            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;
                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            handleStop(scroller);
                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(
                                    touchEventId, scroller), 5);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(
                            handler.obtainMessage(touchEventId, v), 5);
                }
                return false;
            }

            /**
             * ScrollView 停止
             *
             * @param view
             */
            private void handleStop(Object view) {

                Log.i(TAG, "handleStop");
                NestedScrollView scroller = (NestedScrollView) view;
                scrollY = scroller.getScrollY();

                doOnBorderListener();
            }
        });
        /***********************************************************/

    }

    private boolean isLogin() {
        if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {//没有登录跳转到登录界面
            LoginActivity.start2Activity(this);
            return false;

        } else {
            return true;
        }

    }

    /**
     * ScrollView 的顶部，底部判断：
     * http://www.trinea.cn/android/on-bottom-load-more-scrollview-impl/
     * <p>
     * 其中getChildAt表示得到ScrollView的child View， 因为ScrollView只允许一个child
     * view，所以contentView.getMeasuredHeight()表示得到子View的高度,
     * getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。
     * 当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
     */
    private void doOnBorderListener() {
        Log.i(TAG, ScreenUtil.getScreenViewBottomHeight(scrollView) + "  " + scrollView.getScrollY() + " " + ScreenUtil.getScreenHeight(AActivity_3_0.this));

        // 底部判断
        if (contentView != null && contentView.getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
            toTopBtn.setVisibility(View.VISIBLE);
            Log.i(TAG, "bottom");
            if (myFragment0 != null)
                myFragment0.setAutoChange(true);
        }
        // 顶部判断
        else if (scrollView.getScrollY() == 0) {
            if (myFragment0 != null) myFragment0.setAutoChange(false);
            toTopBtn.setVisibility(View.GONE);
            Log.i(TAG, "top");
        } else if (scrollView.getScrollY() > 30) {
            toTopBtn.setVisibility(View.VISIBLE);
            if (myFragment0 != null)
                myFragment0.setAutoChange(false);
            Log.i(TAG, "test");
        }

    }

    private void initData() {


        requestData();//请求数据

    }

    private MyFragment myFragment0;
    private Type type;

    public void requestData() {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("latitude", MyApplication.Userinfo.getString("latitude", ""));
        params.put("longitude",
                MyApplication.Userinfo.getString("longitude", ""));
        finalHttp.post(GetServerUrl.getUrl() + "index", params,
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        D.e("json= \n" + t);
                        mCache.remove("index");
                        mCache.put("index", t.toString());
                        LoadCache(t.toString());
                        LoadCache(mCache.getAsString("index"));
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        if (mCache.getAsString("index") != null
                                && !"".equals(mCache.getAsString("index"))) {
                            LoadCache(mCache.getAsString("index"));
                        }
                        Toast.makeText(AActivity_3_0.this, R.string.error_net,
                                Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }


                });
    }

    private void LoadCache(String t) {
        if (t == null) {
            ToastUtil.showShortToast("数据请求失败");
            return;
        }

        // TODO Auto-generated method stub
        IndexGsonBean indexGsonBean = GsonUtil.formateJson2Bean(t, IndexGsonBean.class);


        if (indexGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
            initNewList(indexGsonBean);//初始化 采购列表
            initArticles(indexGsonBean.data.articleList);//初始化  头条新闻
        }
        datas.clear();
        aBanners.clear();
        try {
            JSONObject jsonObject = new JSONObject(t);
            String code = JsonGetInfo.getJsonString(jsonObject,
                    "code");
            String msg = JsonGetInfo.getJsonString(jsonObject,
                    "msg");
            if (!"".equals(msg)) {
            }
            if ("1".equals(code)) {

                JSONArray bannerList = JsonGetInfo
                        .getJsonArray(JsonGetInfo
                                .getJSONObject(jsonObject,
                                        "data"), "bannerList");
                // 广告
                // JSONArray bannerList = jsonObject
                // .getJSONObject("data").getJSONArray(
                // "bannerList");
                for (int i = 0; i < bannerList.length(); i++) {
                    JSONObject jsonObject2 = bannerList
                            .getJSONObject(i);
                    HashMap<String, Object> hMap = new HashMap<String, Object>();
                    hMap.put("isNewRecord", JsonGetInfo
                            .getJsonBoolean(jsonObject2,
                                    "isNewRecord"));
                    hMap.put("delFlag", JsonGetInfo
                            .getJsonString(jsonObject2,
                                    "delFlag"));
                    hMap.put("url", JsonGetInfo.getJsonString(
                            JsonGetInfo.getJSONObject(
                                    jsonObject2, "imageJson"),
                            "url"));
                    hMap.put("ossThumbnailImagePath",
                            JsonGetInfo.getJsonString(
                                    JsonGetInfo.getJSONObject(
                                            jsonObject2,
                                            "imageJson"),
                                    "ossThumbnailImagePath"));
                    hMap.put("ossMediumImagePath", JsonGetInfo
                            .getJsonString(JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "imageJson"),
                                    "ossMediumImagePath"));
                    hMap.put("ossLargeImagePath", JsonGetInfo
                            .getJsonString(JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "imageJson"),
                                    "ossLargeImagePath"));
                    hMap.put("href", JsonGetInfo.getJsonString(
                            jsonObject2, "href"));
                    hMap.put("name", JsonGetInfo.getJsonString(
                            jsonObject2, "name"));

                    ABanner aBanner = new ABanner();
                    aBanner.setNewRecord(JsonGetInfo
                            .getJsonBoolean(jsonObject2,
                                    "isNewRecord"));
                    aBanner.setUrl(JsonGetInfo.getJsonString(
                            JsonGetInfo.getJSONObject(
                                    jsonObject2, "imageJson"),
                            "url"));
                    aBanner.setOssThumbnailImagePath(JsonGetInfo
                            .getJsonString(JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "imageJson"),
                                    "ossThumbnailImagePath"));
                    aBanner.setOssLargeImagePath(JsonGetInfo
                            .getJsonString(JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "imageJson"),
                                    "ossLargeImagePath"));
                    aBanner.setOssMediumImagePath(JsonGetInfo
                            .getJsonString(JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "imageJson"),
                                    "ossMediumImagePath"));
                    datas.add(hMap);
                    aBanners.add(aBanner);
                }
                if (datas.size() > 0) {
                    initViewPager();
                    // initAbsViewPager();
                }
                if (aBanners.size() > 0) {
                }

                // 分类
                // JSONArray seedlingTypeList = jsonObject
                // .getJSONObject("data").getJSONArray(
                // "seedlingTypeList");
                JSONArray seedlingTypeList = JsonGetInfo
                        .getJsonArray(JsonGetInfo
                                        .getJSONObject(jsonObject,
                                                "data"),
                                "seedlingTypeList");
                if (seedlingTypeList.length() > 0) {
                    gd_datas.clear();
                    if (myadapter != null) {
                        myadapter.notifyDataSetChanged();
                    } else {
                        myadapter = new TypeAdapter(
                                AActivity_3_0.this, gd_datas);
                        gd_00.setAdapter(myadapter);
                    }
                }
                gd_datas.clear();
                for (int i = 0; i < seedlingTypeList.length(); i++) {
                    JSONObject jsonObject2 = seedlingTypeList
                            .getJSONObject(i);
                    HashMap<String, Object> hMap = new HashMap<String, Object>();
                    String id = JsonGetInfo.getJsonString(
                            jsonObject2, "id");
                    String icon = JsonGetInfo.getJsonString(
                            jsonObject2, "icon");
                    String name = JsonGetInfo.getJsonString(
                            jsonObject2, "name");
                    if ("乔木".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type01);
                    } else if ("灌木".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type02);
                    } else if ("桩景".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type03);
                    } else if ("地被".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type04);
                    } else if ("草皮".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type05);
                    } else if ("棕榈".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type06);
                    } else if ("苏铁".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type07);
                    } else if ("更多".equals(name)) {
                        type = new Type(id, name, icon,
                                R.drawable.home_icon_type08);
                    }
                    gd_datas.add(type);
                }

                if (gd_datas.size() > 0) {
                    if (myadapter != null) {
                        myadapter.notifyDataSetChanged();
                    } else {
                        myadapter = new TypeAdapter(
                                AActivity_3_0.this, gd_datas);
                        gd_00.setAdapter(myadapter);
                    }
                }
                lv_datas.clear();


                // 商铺
                // JSONArray storeList =
                // jsonObject.getJSONObject(
                // "data").getJSONArray("storeList");
                JSONArray storeList = JsonGetInfo.getJsonArray(
                        JsonGetInfo.getJSONObject(jsonObject,
                                "data"), "storeList");
                url0s.clear();
                for (int i = 0; i < storeList.length(); i++) {
                    JSONObject jsonObject2 = storeList
                            .getJSONObject(i);
                    HomeStore a_first_product = new HomeStore(
                            JsonGetInfo.getJsonString(
                                    jsonObject2, "id"),
                            JsonGetInfo.getJsonString(
                                    jsonObject2, "code"),
                            JsonGetInfo.getJsonString(
                                    jsonObject2, "logoUrl"),
                            JsonGetInfo.getJsonString(
                                    jsonObject2, "id"),
                            JsonGetInfo.getJsonString(
                                    jsonObject2, "name"));
                    url0s.add(a_first_product);
                }
                FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction();
                if (url0s.size() > 0) {
                    if (url0s.size() % MyFragment.Num != 0) {
                        int array = MyFragment.Num
                                - url0s.size() % MyFragment.Num;
                        for (int i = 0; i < array; i++) {
                            HomeStore a_first_product = new HomeStore("", "", "", "", "");
                            url0s.add(a_first_product);
                        }
                    }
                    myFragment0 = new MyFragment();
                    myFragment0.setUrls(url0s);
                    ft.add(R.id.con0, myFragment0);
//                    myFragment0.setAutoChange(true);
//                                    iv_home_merchants  .setVisibility(View.VISIBLE);
                }
                ft.commitAllowingStateLoss();

//                                Rect bounds = new Rect();
//                                findViewById(R.id.con0).getDrawingRect(bounds);

//                                Rect scrollBounds = new Rect(
//                                        scrollView.getScrollX(),
//                                        scrollView.getScrollY(),
//                                        scrollView.getScrollX() + scrollView.getWidth(),
//                                        scrollView.getScrollY() + scrollView.getWidth());


//                                if (myFragment0.getviewpager() != null) {
//                                    myFragment0.setAutoChange(false);


//                                    scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                                        @Override
//                                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                                            if (Rect.intersects(scrollBounds, bounds)) {
//                                                D.e("======is===visible====");
//                                                myFragment0.setAutoChange(true);
//                                            } else {
//                                                D.e("======no===visible====");
//                                                myFragment0.setAutoChange(false);
//                                            }
//                                        }
//                                    });
            }

//
//                            } else {

//                            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 新增的2个数据列表
     *
     * @param indexGsonBean
     */
    private void initNewList(IndexGsonBean indexGsonBean) {


        try {//采购项目
            if (indexGsonBean.data.purchaseList.size() != 0) {
                findViewById(R.id.ll_caigou_parent).setVisibility(View.VISIBLE);
            }
            PurchaseListAdapter adapter = new PurchaseListAdapter(AActivity_3_0.this, indexGsonBean.data.purchaseList, R.layout.list_item_purchase_list_new);
            lv_00.setAdapter(adapter);
        } catch (Exception e) {
            findViewById(R.id.ll_caigou_parent).setVisibility(View.GONE);
            D.e("=============没有采购列表，或者采购数据异常===============");
            e.printStackTrace();
        }
        try {
            if (indexGsonBean.data.seedlingList.size() != 0) {
                findViewById(R.id.ll_tuijian_parent).setVisibility(View.VISIBLE);
            }
            BProduceAdapt bProduceAdapt = new BProduceAdapt(AActivity_3_0.this, indexGsonBean.data.seedlingList, R.layout.list_view_seedling_new);
            ((ListView) findViewById(R.id.lv_00_store)).setAdapter(bProduceAdapt);
        } catch (Exception e) {
            findViewById(R.id.ll_tuijian_parent).setVisibility(View.GONE);
            D.e("=============没有推荐列表，或者数据异常===============");
            e.printStackTrace();
        }

        {  // 根据list 显示 title  并且设置点击事件
            TextView home_title_first = (TextView) findViewById(R.id.home_title_first);
            TextView home_title_second = (TextView) findViewById(R.id.home_title_second);
            TextView home_title_third = (TextView) findViewById(R.id.home_title_third);
            TextView tv_titles[] = new TextView[]{home_title_first, home_title_second, home_title_third};

            View v1 = findViewById(R.id.home_title_first);
            View v2 = findViewById(R.id.home_title_second);
            View v3 = findViewById(R.id.home_title_third);
            View views[] = new View[]{v1, v2, v3};

            try {
                autoSetTitles(tv_titles, indexGsonBean.data.titleList, views);
            } catch (Exception e) {
                D.e("=============没有title 列表===============");
                e.printStackTrace();
            }
        }


    }

//    private void initAbsViewPager() {
//
//        if (allListView != null) {
//            allListView.clear();
//            allListView = null;
//        }
//        allListView = new ArrayList<View>();
//
//        for (int i = 0; i < datas.size(); i++) {
//            // 导入ViewPager的布局
//            View view = LayoutInflater.from(this).inflate(R.layout.pic_item,
//                    null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
//            if (datas.get(i).get("url").toString().startsWith("http")) {
//                ImageLoader.getInstance().displayImage(
//                        datas.get(i).get("url").toString(), imageView);
//                // holder.imageView.setImageResource(imageIdList.get(getPosition(position)));
//            } else {
//                imageView.setImageResource(R.drawable.ic_launcher);
//            }
//            allListView.add(view);
//        }
//
//        absviewPager.addViews(allListView);
//        // 开始轮播
//        absviewPager.startPlay();
//
//    }

    /**
     * 初始化 顶部
     */
    private void initViewPager() {

        if (imagePagerAdapter != null) {
            imagePagerAdapter.notifyDataSetChanged();
            return;
        }
        imagePagerAdapter = new ImagePagerAdapter(AActivity_3_0.this, datas);
        viewPager.setAdapter(imagePagerAdapter);
        indicator.setViewPager(viewPager);
        // imagePagerAdapter.notifyDataSetChanged();
        // indicator.setRadius(5);
        indicator.setOrientation(LinearLayout.HORIZONTAL);
        // indicator.setStrokeWidth(5);
        // indicator.setSnap(true);
        viewPager.setInterval(3500);
        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
        viewPager
                .setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);
        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        viewPager.setCycle(true);
        viewPager.setBorderAnimation(true);
        viewPager.startAutoScroll();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.top_btn:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                toTopBtn.setVisibility(View.GONE);
                break;

            case R.id.iv_a_msg:
                if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {//没有登录跳转到登录界面
                    LoginActivity.start2Activity(this);
                    return;
                }
                Intent toMessageListActivity = new Intent(AActivity_3_0.this,
                        MessageListActivity.class);
                startActivity(toMessageListActivity);
                getParent().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
                break;
//            case R.id.RelativeLayout2://搜索按钮
//			Intent intent = new Intent(AActivity.this,
//					PurchaseSearchListActivity.class);
//			intent.putExtra("from", "AActivity");
//			startActivityForResult(intent, 1);
//                break;

            case R.id.tv_a_search://搜索按钮 --- new
                //// TODO: 2017/4/10  需要添加 共享动画
                Intent intent = new Intent(AActivity_3_0.this,
                        PurchaseSearchListActivity.class);
                intent.putExtra("from", "AActivity");
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 2) {
            String decodeResult = data.getStringExtra("decodeResult");
            // Toast.makeText(AActivity.this,
            // "此功能预留暂不开放，解析成功，结果为：" + decodeResult, Toast.LENGTH_SHORT)
            // .show();
            if (StringUtil.isHttpUrlPicPath(decodeResult)) {
                Intent toWebActivity3 = new Intent(AActivity_3_0.this,
                        WebActivity.class);
                toWebActivity3.putExtra("title", "标题");
                toWebActivity3.putExtra("url", decodeResult);
                startActivityForResult(toWebActivity3, 1);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void autoSetTitles(TextView[] tvs, List<IndexGsonBean.TitleBean> list, View[] views) {
        if (list.size() == 0) {
            //all gone
            for (int i = 0; i < tvs.length; i++) {
                ((ViewGroup) tvs[i].getParent()).setVisibility(View.GONE);
            }

        } else {
            for (int i = 0; i < tvs.length; i++) {
                tvs[i].setText(list.get(i).title);
                ((ViewGroup) tvs[i].getParent()).setVisibility(View.VISIBLE);

                if (list.get(i).isClick) {
                    ((SuperTextView) views[i]).setShowState(true);
                } else {
                    ((SuperTextView) views[i]).setShowState(false);
//                    views[i].setVisibility(View.GONE);
                }

            }


        }
    }

}
