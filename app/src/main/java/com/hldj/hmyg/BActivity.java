package com.hldj.hmyg;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.PinyinComparator;
import com.example.sortlistview.SideBar;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.M.BProduceGridAdapt;
import com.hldj.hmyg.P.BPresenter;
import com.hldj.hmyg.adapter.ProductGridAdapter;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.bean.DaQuYu;
import com.hldj.hmyg.bean.XiaoQuYu;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SortSpinner;
import com.huewu.pla.lib.me.maxwin.view.PLAXListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mrwujay.cascade.activity.BaseSecondActivity;
import com.ns.developer.tagview.widget.TagCloudLinkView;
import com.yangfuhai.asimplecachedemo.lib.ACache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import static com.hldj.hmyg.util.ConstantParams.container;
import static com.hldj.hmyg.util.ConstantParams.dbh;
import static com.hldj.hmyg.util.ConstantParams.diameter;
import static com.hldj.hmyg.util.ConstantParams.heelin;
import static com.hldj.hmyg.util.ConstantParams.mijing;
import static com.hldj.hmyg.util.ConstantParams.planted;
import static com.hldj.hmyg.util.ConstantParams.transplant;


/**
 * 商城界面
 */
@SuppressLint("NewApi")
public class BActivity extends BaseSecondActivity implements
        IXListViewListener,
        com.huewu.pla.lib.me.maxwin.view.PLAXListView.IXListViewListener {
    final String hintText = "<img src=\"" + R.drawable.seller_search
            + "\" /> 搜索商店名,商品名";
    String view_type = "list";
    private XListView xListView;
    private PLAXListView glistView;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
    private ArrayList<String> data_ids = new ArrayList<String>();
    boolean getdata; // 避免刷新多出数据
    private ImageView iv_view_type;
    private ImageView iv_seller_arrow2;
    private ImageView iv_seller_arrow3;
    private ProductListAdapter listAdapter;
    private ProductGridAdapter gridAdapter;
    //    private DaquyuAdapter daquyuAdapter;
//    private MultipleClickProcess.XiaoquyuAdapter xiaoquyuAdapter;
    private String searchKey = "";

    private String firstSeedlingTypeId = "";
    private String firstSeedlingTypeName = "";
    private String supportTradeType = "";
    private String supportTradeTypeName = "";
    private String secondSeedlingTypeId = "";
    private String secondSeedlingTypeName = "";
    private String plantTypes = "";
    private String searchSpec = "";
    private String specMinValue = "";
    private String specMaxValue = "";
    private ArrayList<String> planttype_has_ids = new ArrayList<String>();

    private String orderBy = "";
    private String priceSort = "";
    private String publishDateSort = "";
    private String cityCode = "";
    private String cityName = "全国";
    private int pageSize = 20;
    private int pageIndex = 0;
    private int RefreshpageIndex = 0;
    private ArrayList<DaQuYu> daQuYus = new ArrayList<DaQuYu>();
    private List<XiaoQuYu> xiaoQuYus = new ArrayList<XiaoQuYu>();
    private PopupWindow popupWindow;
    private String[] keySort = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};


    private static final int CITY_ID = 100;
    private static final int SPACE_TEXT_ID = 101;
    private static final int SEARCH_SPEC_ID = 102;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private DisplayMetrics dm;
    private ArrayList<String> lanmus = new ArrayList<String>();
    private ArrayList<String> lanmu_ids = new ArrayList<String>();
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private Editor e;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    private ACache mCache;
    private BProduceAdapt bProduceAdapt;
    private BProduceGridAdapt gridAdapt;


    //    private Dialog dialog;
//    private WheelView mViewProvince;
//    private WheelView mViewCity;
//    private WheelView mViewDistrict;

    public void setBasIn(BaseAnimatorSet bas_in) {
        this.mBasIn = bas_in;
    }

    public void setBasOut(BaseAnimatorSet bas_out) {
        this.mBasOut = bas_out;
    }

    //    private ArrayList<String> planttype_names = new ArrayList<String>();
//    private ArrayList<String> planttype_ids = new ArrayList<String>();
    private KProgressHUD hud;
//    private ArrayList<SortList> sortLists = new ArrayList<SortList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentExtral();

        setContentView(R.layout.activity_b);
        hud = KProgressHUD.create(BActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("数据加载中...").setMaxProgress(100).setCancellable(true)
                .setDimAmount(0f);
        e = MyApplication.Userinfo.edit();
        mCache = ACache.get(this);
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        setOverflowShowingAlways();
        dm = getResources().getDisplayMetrics();
        lanmus.add("全部");
        lanmus.add("放心购");
        lanmus.add("帮我购");
        lanmus.add("担保购");
        lanmus.add("委托购");
        lanmu_ids.add("");
        lanmu_ids.add("fangxin");
        lanmu_ids.add("bangwo");
        lanmu_ids.add("danbao");
        lanmu_ids.add("weituo");


        tagView = (TagView) this.findViewById(R.id.tagview);
        tagView.setOnTagDeleteListener((position, tag) -> {
            // TODO Auto-generated method stub
            if (tag.id == 1) {
                searchKey = "";
                onRefresh();
            } else if (tag.id == 2) {
                firstSeedlingTypeId = "";
                firstSeedlingTypeName = "";
                onRefresh();
            } else if (tag.id == 3) {
                secondSeedlingTypeId = "";
                secondSeedlingTypeName = "";
                onRefresh();
            }
        });

        if (getIntent().getStringExtra("firstSeedlingTypeId") != null
                && getIntent().getStringExtra("firstSeedlingTypeName") != null) {
            firstSeedlingTypeId = getIntent().getStringExtra(
                    "firstSeedlingTypeId");
            firstSeedlingTypeName = getIntent().getStringExtra(
                    "firstSeedlingTypeName");
            tagView.removeAllTags();
            if (!"".equals(firstSeedlingTypeId)) {
                me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(
                        firstSeedlingTypeName);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 2; // 1 搜索 2分类
                tagView.addTag(tag);
            }
        }
        if (getIntent().getStringExtra("searchKey") != null) {
            searchKey = getIntent().getStringExtra("searchKey");
            tagView.removeAllTags();
            if (!"".equals(searchKey)) {
                me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(searchKey);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 1; // 1 搜索 2分类
                tagView.addTag(tag);
            }
        }
        if (getIntent().getStringExtra("from") != null) {
            // if (Build.VERSION.SDK_INT >= 23) {
            // setSwipeBackEnable(false);
            // }
        } else {
            // setSwipeBackEnable(false);
        }
        // 实例化汉字转拼音类
//        characterParser = CharacterParser.getInstance();
//        pinyinComparator = new PinyinComparator();

//        pager = (ViewPager) findViewById(R.id.pager);
//        ll_01 = (LinearLayout) findViewById(R.id.ll_01);
//        ll_all = (LinearLayout) findViewById(R.id.ll_all);
//        ll_choice = (LinearLayout) findViewById(R.id.ll_choice);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
//        tv_xiaoxitishi = (TextView) findViewById(R.id.tv_xiaoxitishi);
//        iv_close = (ImageView) findViewById(R.id.iv_close);
//        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        pager.setOffscreenPageLimit(lanmus.size());
//        tabs.setViewPager(pager);
//        setTabsValue();
//        if (getIntent().getStringExtra("supportTradeType") != null
//                && getIntent().getStringExtra("supportTradeTypeName") != null) {
//            supportTradeType = getIntent().getStringExtra("supportTradeType");
//            supportTradeTypeName = getIntent().getStringExtra(
//                    "supportTradeTypeName");
//            if ("".equals(supportTradeType)) {
//                pager.setCurrentItem(0);
//            } else if ("fangxin".equals(supportTradeType)) {
//                pager.setCurrentItem(1);
//            } else if ("bangwo".equals(supportTradeType)) {
//                pager.setCurrentItem(2);
//            } else if ("danbao".equals(supportTradeType)) {
//                pager.setCurrentItem(3);
//            } else if ("weituo".equals(supportTradeType)) {
//                pager.setCurrentItem(4);
//            }
//        }
//        rl_choose_type = (RelativeLayout) findViewById(R.id.rl_choose_type);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        RelativeLayout rl_choose_price = (RelativeLayout) findViewById(R.id.rl_choose_price);
        RelativeLayout rl_choose_time = (RelativeLayout) findViewById(R.id.rl_choose_time);
        RelativeLayout rl_choose_screen = (RelativeLayout) findViewById(R.id.rl_choose_screen);
        TextView tv_search = (TextView) findViewById(R.id.tv_search);
        iv_view_type = (ImageView) findViewById(R.id.iv_view_type);
        iv_seller_arrow2 = (ImageView) findViewById(R.id.iv_seller_arrow2);
        iv_seller_arrow3 = (ImageView) findViewById(R.id.iv_seller_arrow3);
        xListView = (XListView) findViewById(R.id.xlistView);
        view = (TagCloudLinkView) findViewById(R.id.tag_cloud);
        // view.add(new Tag(1,"TAG TEXT 1  ×"));
        view.setOnTagDeleteListener((tag, i) -> {
            // write something
        });
        xListView.setDivider(null);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        glistView = (PLAXListView) findViewById(R.id.glistView);
        glistView.setPullLoadEnable(true);
        glistView.setPullRefreshEnable(true);
        glistView.setXListViewListener(this);


        bProduceAdapt = new BProduceAdapt(BActivity.this, null, R.layout.list_view_seedling_new);
        xListView.setAdapter(bProduceAdapt);

        gridAdapt = new BProduceGridAdapt(BActivity.this, null, R.layout.grid_view_seedling);
        glistView.setAdapter(gridAdapt);
//        initDataGetFirstType();


        initData();


        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        iv_view_type.setOnClickListener(multipleClickProcess);
        rl_choose_price.setOnClickListener(multipleClickProcess);
        rl_choose_time.setOnClickListener(multipleClickProcess);
        rl_choose_screen.setOnClickListener(multipleClickProcess);
        relativeLayout2.setOnClickListener(multipleClickProcess);
//        iv_close.setOnClickListener(multipleClickProcess);


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };


    private int topMargin;
    LinearLayout.LayoutParams top;
    int mFirstY = 0;
    int mCurrentY = 0;
    boolean direction;
    int marginTop = 150;
    int height = 150;


    private me.kaede.tagview.Tag getTagViewByName(String item) {
        if (!"".equals(getTypeName(item))) {
            me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(getTypeName(item));
            tag.layoutColor = getResources().getColor(R.color.main_color);
            tag.isDeletable = true;
            return tag;
        } else {
            return null;
        }

    }

    private String getTypeName(String item) {
//        planted container transplant heelin
        switch (item) {
            case planted:
                return "地栽苗";
            case container:
                return "容器苗";
            case heelin:
                return "假植苗";
            case transplant:
                return "移植苗";
            case mijing:
                return "米径";
            case diameter:
                return "地径";
            case dbh:
                return "胸径";
            default:
                return item;
        }
    }


    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTabsValue() {

        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setDividerPadding(5);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        tabs.setTextColor(getResources().getColor(R.color.gray));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(getResources().getColor(R.color.main_color));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(getResources().getColor(R.color.main_color));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
        tabs.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                if ("".equals(lanmu_ids.get(arg0))) {
                    supportTradeType = lanmu_ids.get(arg0);
                    if (listAdapter != null && gridAdapter != null) {

                        firstSeedlingTypeId = "";
                        firstSeedlingTypeName = "";
                        supportTradeType = "";
                        supportTradeTypeName = "";
                        secondSeedlingTypeId = "";
                        secondSeedlingTypeName = "";
                        plantTypes = "";
                        orderBy = "";
                        priceSort = "";
                        publishDateSort = "";
                        cityCode = "";
                        cityName = "全国";
                        onRefresh();
                    }
                    showNotice(lanmu_ids.get(arg0));
                } else if ("fangxin".equals(lanmu_ids.get(arg0))) {
                    supportTradeType = lanmu_ids.get(arg0);
                    if (listAdapter != null && gridAdapter != null) {
                        onRefresh();
                    }
                    showNotice(lanmu_ids.get(arg0));
                } else if ("bangwo".equals(lanmu_ids.get(arg0))) {
                    supportTradeType = lanmu_ids.get(arg0);
                    if (listAdapter != null && gridAdapter != null) {
                        onRefresh();
                    }
                    showNotice(lanmu_ids.get(arg0));
                } else if ("danbao".equals(lanmu_ids.get(arg0))) {
                    supportTradeType = lanmu_ids.get(arg0);
                    if (listAdapter != null && gridAdapter != null) {
                        onRefresh();
                    }
                    showNotice(lanmu_ids.get(arg0));
                } else if ("weituo".equals(lanmu_ids.get(arg0))) {
                    // supportTradeType = lanmu_ids.get(arg0);
                    // 弹出
                    // showNotice(lanmu_ids.get(arg0));

                    if ("".equals(supportTradeType)) {
                        pager.setCurrentItem(0);
                        tabs.notifyDataSetChanged();
                    } else if ("fangxin".equals(supportTradeType)) {
                        pager.setCurrentItem(1);
                        tabs.notifyDataSetChanged();
                    } else if ("bangwo".equals(supportTradeType)) {
                        pager.setCurrentItem(2);
                        tabs.notifyDataSetChanged();
                    } else if ("danbao".equals(supportTradeType)) {
                        pager.setCurrentItem(3);
                        tabs.notifyDataSetChanged();
                    }

                    Intent toWeituoActivity = new Intent(BActivity.this,
                            WeituoActivity.class);
                    toWeituoActivity.putExtra("title", "委托购");
                    toWeituoActivity.putExtra("url", Data.weituogou);
                    startActivity(toWeituoActivity);
                    overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_right);
                }
            }

            private void showNotice(String id) {
                // TODO Auto-generated method stub
                if ("".equals(id)) {
                    ll_01.setVisibility(View.GONE);
                } else if ("fangxin".equals(id)) {
                    if (MyApplication.Userinfo.getBoolean("NeedShowfangxin",
                            true)) {

                        tv_xiaoxitishi
                                .setText("我们承诺：放心购所有资源，若有同批更低价格，一经确认则再优惠2%。");
//						ll_01.setVisibility(View.VISIBLE);
                    } else {
                        ll_01.setVisibility(View.GONE);
                    }
                } else if ("bangwo".equals(id)) {
                    if (MyApplication.Userinfo.getBoolean("NeedShowbangwo",
                            true)) {
                        tv_xiaoxitishi.setText("帮我购：买家可委托花木易购帮其验苗或者发货。");
//						ll_01.setVisibility(View.VISIBLE);
                    } else {
                        ll_01.setVisibility(View.GONE);
                    }
                } else if ("danbao".equals(id)) {
                    if (MyApplication.Userinfo.getBoolean("NeedShowdanbao",
                            true)) {
                        tv_xiaoxitishi
                                .setText("担保购：买家可将资金托管在花木易购，确认收货后再将款项支付给苗圃。");
//						ll_01.setVisibility(View.VISIBLE);
                    } else {
                        ll_01.setVisibility(View.GONE);
                    }
                } else if ("weituo".equals(id)) {
                    ll_01.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lanmus.get(position);
        }

        @Override
        public int getCount() {
            return lanmus.size();
        }

        @Override
        public Fragment getItem(int position) {
            BFragment fragment = new BFragment();
            return fragment;

        }
    }

    private void initData() {
        hud.show();
        getdata = false;
        BPresenter bPresenter = (BPresenter) new BPresenter()
                .putParams("searchSpec", searchSpec)
                .putParams("specMinValue", specMinValue)
                .putParams("specMaxValue", specMaxValue)
                .putParams("searchKey", searchKey)
                .putParams("TradeType", supportTradeType)
                .putParams("firstSeedlingTypeId", firstSeedlingTypeId)
                .putParams("secondSeedlingTypeId", secondSeedlingTypeId)
                .putParams("cityCode", cityCode)
                .putParams("plantTypes", plantTypes)
                .putParams("orderBy", orderBy)
                .putParams("pageSize", pageSize + "")
                .putParams("pageIndex", pageIndex + "")
                .putParams("latitude", MyApplication.Userinfo.getString("latitude", ""))
                .putParams("longitude", MyApplication.Userinfo.getString("longitude", ""))
                .addResultCallBack(new ResultCallBack<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>() {
                    @Override
                    public void onSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> pageBean) {
                        D.e("==============");
                        pageIndex++;
                        bProduceAdapt.addData(pageBean);
//                        bProduceAdapt = new BProduceAdapt(BActivity.this, pageBean, R.layout.list_view_seedling_new);
//                        xListView.setAdapter(bProduceAdapt);
                        gridAdapt.addData(pageBean);
//                        gridAdapt = new BProduceGridAdapt(BActivity.this, pageBean, R.layout.grid_view_seedling);
//                        glistView.setAdapter(gridAdapt);
                        onLoad();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        onLoad();
                    }
                });
        bPresenter.getDatas("seedling/list", false);

        getdata = true;
    }

    /**
     * 通过 list<string> 数组 来动态添加tag</>
     *
     * @param mList
     * @return
     */
    private String filPlantTypes(ArrayList<String> mList) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mList.size(); i++) {
            buffer.append(mList.get(i) + ",");
        }
        if (buffer.length() > 0) {
            return buffer.toString().substring(0, buffer.length() - 1);
        }
        return "";
//                     ;;
    }


    /**
     * me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(
     * firstSeedlingTypeName);
     * tag.layoutColor = getResources().getColor(R.color.main_color);
     * tag.isDeletable = true;
     * tag.id = 2; // 1 搜索 2分类
     * tagView.addTag(tag);
     *
     * @param list
     */
    private void addTags(ArrayList<String> list) {
        tagView.removeAllTags();
        for (String item : list) {
            //此处添加多个可删除 tag
            tagView.addTag(getTagViewByName(item));
        }
        tagView.setOnTagDeleteListener((position, tag) -> {
            D.e("======position====" + position);
            if (position < planttype_has_ids.size()) {
                planttype_has_ids.remove(position);
            } else {
                switch (tag.id) {
                    case CITY_ID:
                        cityCode = "";
                        break;
                    case SPACE_TEXT_ID:
                        specMinValue = "";
                        specMaxValue = "";
                        break;
                    case SEARCH_SPEC_ID:
                        searchSpec = "";
                        break;
                }
                /**
                 * //判断城市是否增加
                 Tag tag = getTagViewByName(cityName);
                 tag.id = 100;//城市id
                 tagView.addTag(tag);
                 tag = getTagViewByName(spaceText);
                 tag.id = 101;//城市id
                 tagView.addTag(tag);
                 tag = getTagViewByName(searchSpec);
                 tag.id = 102;//城市id
                 tagView.addTag(tag);
                 */
            }


            onRefresh();
//            if (tag.id == 1) {
//                searchKey = "";
//                onRefresh();
//            } else if (tag.id == 2) {
//                firstSeedlingTypeId = "";
//                firstSeedlingTypeName = "";
//                onRefresh();
//            } else if (tag.id == 3) {
//                secondSeedlingTypeId = "";
//                secondSeedlingTypeName = "";
//                onRefresh();
//            }

        });


    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (sortSpinner != null) {
            sortSpinner = null;
        }
        if (hud != null) {
            hud.dismiss();
        }


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (hud != null) {
            hud.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (hud != null) {
            hud.dismiss();
        }
        super.onBackPressed();
    }

    int pos = 0;
    public SortSpinner sortSpinner;

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;
        private SideBar sideBar;
//        private ListView listview_xiaoquyu;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.iv_view_type:
                        if ("grid".equals(view_type)) {
                            // gridview
                            view_type = "list";
                            iv_view_type
                                    .setImageResource(R.drawable.icon_grid_view);
                            glistView.setVisibility(View.GONE);
                            xListView.setVisibility(View.VISIBLE);
                        } else if ("list".equals(view_type)) {
                            // listview
                            view_type = "grid";
                            iv_view_type
                                    .setImageResource(R.drawable.icon_list_view);
                            xListView.setVisibility(View.GONE);
                            glistView.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.rl_choose_price:
                        if (!hud.isShowing() && hud != null) {
                            hud.show();
                        }
                        if ("".equals(priceSort)) {
                            priceSort = "price_asc";
                            iv_seller_arrow2
                                    .setImageResource(R.drawable.icon_seller_arrow2);
                        } else if ("price_asc".equals(priceSort)) {
                            priceSort = "price_desc";
                            iv_seller_arrow2
                                    .setImageResource(R.drawable.icon_seller_arrow3);
                        } else if ("price_desc".equals(priceSort)) {
                            priceSort = "";
                            iv_seller_arrow2
                                    .setImageResource(R.drawable.icon_seller_arrow1);
                        }
                        onRefresh();
                        break;
                    case R.id.rl_choose_time:
                        ChoiceSortList();//排序，显示pop
                        break;
                    case R.id.rl_choose_screen:
                        Intent toSellectActivity = new Intent(BActivity.this,
                                SellectActivity2.class);
                        toSellectActivity.putExtra("from", "BActivity");
                        toSellectActivity.putExtra("cityCode", cityCode);
                        toSellectActivity.putExtra("cityName", cityName);
                        toSellectActivity.putExtra("plantTypes", plantTypes);
                        toSellectActivity.putStringArrayListExtra("planttype_has_ids", planttype_has_ids);
                        toSellectActivity.putExtra("searchSpec", searchSpec);
                        toSellectActivity.putExtra("specMinValue", specMinValue);
                        toSellectActivity.putExtra("specMaxValue", specMaxValue);
                        toSellectActivity.putExtra("searchKey", searchKey);
                        startActivityForResult(toSellectActivity, 1);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                    case R.id.RelativeLayout2:
                        Intent intent = new Intent(BActivity.this,
                                PurchaseSearchListActivity.class);
                        intent.putExtra("from", "BActivity");
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.iv_close:
                        // TODO Auto-generated method stub
                        if ("".equals(supportTradeType)) {
                        } else if ("fangxin".equals(supportTradeType)) {
                            DialogNoti("我们承诺：放心购所有资源，若有同批更低价格，一经确认则再优惠2%。");
                        } else if ("bangwo".equals(supportTradeType)) {
                            DialogNoti("帮我购：买家可委托花木易购帮其验苗或者发货。");
                        } else if ("danbao".equals(supportTradeType)) {
                            DialogNoti("担保购：买家可将资金托管在花木易购，确认收货后再将款项支付给苗圃。");
                        } else if ("weituo".equals(supportTradeType)) {
                        }
                        break;

                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }


        /**
         * 排序 显示位置不对. 小米上正确
         */
        private void ChoiceSortList() {
            View view = findViewById(R.id.ll_spanner_line);
            if (sortSpinner == null) {
                sortSpinner = SortSpinner.getInstance(BActivity.this, view)
                        .addOnItemClickListener((parent, view1, position, id) -> {
                            D.e("addOnItemClickListener" + position);
                            switch (position) {
                                case 0:
                                    orderBy = "default_asc";//综合排序
                                    break;
                                case 1:
                                    orderBy = "publishDate_desc";//最新发布
                                    break;
                                case 2:
                                    orderBy = "distance_asc";//最近距离
                                    break;
                                case 3:
                                    orderBy = "price_asc";//价格从低到高
                                    break;
                                case 4:
                                    orderBy = "price_desc";//综合排序
                                    break;
                            }
                            pos = position;
                            onRefresh();
                            sortSpinner.dismiss();

                        });

                sortSpinner.ShowWithPos(pos);
            } else {
                try {
                    sortSpinner.ShowWithPos(pos);
                } catch (Exception e) {
                    D.e("==baocuo==" + e.getMessage());
                }

            }

        }


        private void DialogNoti(String string) {
            // TODO Auto-generated method stub

            final com.flyco.dialog.widget.MaterialDialog dialog = new com.flyco.dialog.widget.MaterialDialog(
                    BActivity.this);
            dialog.title("温馨提示").content(string)
                    //
                    .btnText("不再提示", "取消")//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();

            dialog.setOnBtnClickL(new OnBtnClickL() {// left btn click listener
                @Override
                public void onBtnClick() {
                    if ("fangxin".equals(supportTradeType)) {
                        e.putBoolean("NeedShowfangxin", false);
                    } else if ("bangwo".equals(supportTradeType)) {
                        e.putBoolean("NeedShowbangwo", false);
                    } else if ("danbao".equals(supportTradeType)) {
                        e.putBoolean("NeedShowdanbao", false);
                    }
                    e.commit();
                    dialog.dismiss();
                    ll_01.setVisibility(View.GONE);
                }
            }, new OnBtnClickL() {// right btn click listener
                @Override
                public void onBtnClick() {
                    dialog.dismiss();
                    ll_01.setVisibility(View.GONE);

                }
            });

        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(200);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 9) {
            if (hud != null) {
                hud.dismiss();
            }
            cityCode = data.getStringExtra("cityCode");
            cityName = data.getStringExtra("cityName");

            plantTypes = data.getStringExtra("plantTypes");
            planttype_has_ids = data.getStringArrayListExtra("planttype_has_ids");

            if (planttype_has_ids.size() == 0) {
                tagView.removeAllTags();
            } else {
                addTags(planttype_has_ids);//此处添加tag 并 监听删除事件
            }


            searchSpec = data.getStringExtra("searchSpec");


            specMinValue = data.getStringExtra("specMinValue");
            specMaxValue = data.getStringExtra("specMaxValue");

            String spaceText = "";

            if (!TextUtils.isEmpty(specMinValue) && !TextUtils.isEmpty(specMaxValue)) {
                spaceText = specMinValue + "-" + specMaxValue;
            } else if (!TextUtils.isEmpty(specMinValue)) {
                spaceText = specMinValue;
            } else {
                spaceText = specMaxValue;
            }
            if (!spaceText.isEmpty()) {
                spaceText = spaceText + "cm";
            }
            //判断城市是否增加

            Tag tag = getTagViewByName(cityName);
            if (tag != null)
                tag.id = CITY_ID;//城市id
            if (!cityName.equals("全国"))
                tagView.addTag(tag);

            tag = getTagViewByName(spaceText);
            if (tag != null)
                tag.id = SPACE_TEXT_ID;//城市id
            tagView.addTag(tag);

            tag = getTagViewByName(searchSpec);
            if (tag != null)
                tag.id = SEARCH_SPEC_ID;//城市id
            tagView.addTag(tag);

            searchKey = data.getStringExtra("searchKey");


            onRefresh();
        } else if (resultCode == 8) {
            if (hud != null) {
                hud.dismiss();
            }
            searchKey = data.getStringExtra("searchKey");
            tagView.removeAllTags();
            if (!"".equals(searchKey)) {
                me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(searchKey);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 1; // 1 搜索 2分类
                tagView.addTag(tag);
            }
            onRefresh();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        plantTypes = filPlantTypes(planttype_has_ids);
        if (orderBy.endsWith(",")) {
            orderBy = orderBy.substring(0, orderBy.length() - 1);
        }
        pageIndex = 0;
        bProduceAdapt.setState(GlobBaseAdapter.REFRESH);
        xListView.setAdapter(bProduceAdapt);
        gridAdapt.setState(GlobBaseAdapter.REFRESH);
        glistView.setAdapter(gridAdapt);
        initData();
    }

    @Override
    public void onLoadMore() {
        xListView.setPullRefreshEnable(false);
        glistView.setPullRefreshEnable(false);
        initData();
    }

    private void onLoad() {
        new Handler().postDelayed(() -> {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            // TODO Auto-generated method stub
            xListView.stopRefresh();
            xListView.stopLoadMore();
            xListView.setRefreshTime(new Date().toLocaleString());
            if (gridAdapt.getDatas().size() % pageIndex == 0) {
                xListView.setPullLoadEnable(true);
            } else {
                xListView.setPullLoadEnable(false);
            }
            xListView.setPullRefreshEnable(true);
            glistView.stopRefresh();
            glistView.stopLoadMore();
            glistView.setRefreshTime(new Date().toLocaleString());
            glistView.setPullLoadEnable(true);
            glistView.setPullRefreshEnable(true);

        }, com.hldj.hmyg.application.Data.refresh_time);

    }


    private TagCloudLinkView view;
    private RelativeLayout relativeLayout2;//搜索框
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TextView tv_xiaoxitishi;
    private ImageView iv_close;
    private LinearLayout ll_01;
    private LinearLayout ll_all;
    private RelativeLayout relativeLayout1;
    private TagView tagView;
    private PopupWindow popupWindow2;
    private LinearLayout ll_choice;

    /**
     * +
     *
     * @param context
     * @param filTypeId     传进来得 筛选 id
     * @param StringfilName 传进来得 筛选 种类名称
     */
    public static void start2Activity(Context context, String filTypeId, String StringfilName) {
        Intent intent = new Intent(context, BActivity.class);
        intent.putExtra("firstSeedlingTypeId", filTypeId);
        intent.putExtra("firstSeedlingTypeName", StringfilName);
        intent.putExtra("isOpenSwipe", true);
        context.startActivity(intent);
    }


    public static void start2ActivityOnly(Context context) {
        Intent intent = new Intent(context, BActivity.class);
        intent.putExtra("isOpenSwipe", true);
        context.startActivity(intent);
    }


    float startY;//开始y坐标
    float moveY;//移动中的y坐标
    boolean isOpen = true;


    int padingTop = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstY = (int) event.getRawY();
                top = (LinearLayout.LayoutParams) relativeLayout1.getLayoutParams();
                topMargin = top.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentY = (int) event.getRawY();
                if (Math.abs(mCurrentY - mFirstY) < 5) {
                    break;
                }
                if (mCurrentY - mFirstY > 0)//下滑
                {
                    direction = true;
                } else if (mCurrentY - mFirstY < 0)//上滑
                {
                    direction = false;
                }
                if (!direction)//上滑
                {
//                    top = (LinearLayout.LayoutParams) relativeLayout1.getLayoutParams();
//                        animToolBar(0, relativeLayout1);
                    if (top.topMargin > -marginTop) {
                        top.topMargin += mCurrentY - mFirstY;
                        if (top.topMargin < -marginTop) {
                            top.topMargin = -marginTop;
                        }
                        relativeLayout1.requestLayout();

                    } else {
                        top.topMargin = -marginTop;
                        relativeLayout1.requestLayout();
                    }
                    mFirstY = mCurrentY;
                } else {//下滑
//                    top = (LinearLayout.LayoutParams) relativeLayout1.getLayoutParams();
//                        animToolBar(2,relativeLayout1);
                    if (top.topMargin < 0) {
                        top.topMargin += mCurrentY - mFirstY;
                        if (top.topMargin > 0) {
                            top.topMargin = 0;
                        }
                        relativeLayout1.requestLayout();
                    } else {
                        top.topMargin = 0;
                        relativeLayout1.requestLayout();
                    }
                    mFirstY = mCurrentY;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * +
     *
     * @param context
     * @param from      传进来得 筛选 id
     * @param searchKey 传进来得 筛选 种类名称
     */
    public static void start2ActivitySearch(Context context, String from, String searchKey) {
        Intent intent = new Intent(context, BActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("searchKey", searchKey);
        intent.putExtra("isOpenSwipe", true);
        context.startActivity(intent);
    }

    private void getIntentExtral() {
//        if (getIntent().getBooleanExtra("isOpenSwipe", false)) {
        setSwipeBackEnable(getIntent().getBooleanExtra("isOpenSwipe", false));//是的跳转过来的  界面具有滑动功能
//        }
    }


    ObjectAnimator mAnimator;

    private void animToolBar(int flag, View view) {

        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        if (flag == 0) {//向上滑隐藏toolBar
            mAnimator = new ObjectAnimator().ofFloat(view, "translationY", view.getTranslationY(), -view.getHeight());
        } else {//向下滑
            mAnimator = new ObjectAnimator().ofFloat(view, "translationY", view.getTranslationY(), 0);
        }

        mAnimator.start();
    }


}
