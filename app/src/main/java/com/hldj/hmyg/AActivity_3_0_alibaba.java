package com.hldj.hmyg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.autoscrollview.adapter.ImagePagerAdapter_ali;
import com.autoscrollview.widget.AutoScrollViewPager;
import com.autoscrollview.widget.indicator.CirclePageIndicator;
import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.M.IndexGsonBean;
import com.hldj.hmyg.Ui.NewsActivity;
import com.hldj.hmyg.Ui.NoticeActivity;
import com.hldj.hmyg.Ui.NoticeActivity_detail;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.ArticleBean;
import com.hldj.hmyg.bean.HomeStore;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.contract.AAliContract;
import com.hldj.hmyg.model.AAliModel;
import com.hldj.hmyg.presenter.AAliPresenter;
import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
import com.hldj.hmyg.saler.purchase.PurchasePyMapActivity;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.UPMarqueeView;
import com.hy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import aom.xingguo.huang.banner.MyFragment;


public class AActivity_3_0_alibaba extends BaseMVPActivity<AAliPresenter, AAliModel> implements AAliContract.View {


    private static final boolean BANNER_LAYOUT = true;//首页bannar ，轮播效果
    //首页 4个 快速挑战按钮   （苗木商城   快速报价  成交公告  新闻资讯）
    private static final boolean GRID_LAYOUT = true;

    private static final boolean LINEAR_LAYOUT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       Toolbar toolbar = (Toolbar) findViewById(R.id.a_toolbar);
//       setSupportActionBar(toolbar);

        showLoading();
        mPresenter.getData("params+url");//view 层请求数据


    }

    @Override
    public void initView() {

        getViewHolder().tv_a_search.setOnClickListener(view -> {
            Intent intent = new Intent(AActivity_3_0_alibaba.this, PurchaseSearchListActivity.class);
            intent.putExtra("from", "AActivity");
            startActivityForResult(intent, 1);
        });
        getViewHolder().iv_a_msg.setOnClickListener(view -> {
            if (!MyApplication.getInstance().Userinfo.getBoolean("isLogin", false)) {//没有登录跳转到登录界面
                LoginActivity.start2Activity(this);
                return;
            }
            Intent toMessageListActivity = new Intent(AActivity_3_0_alibaba.this, MessageListActivity.class);
        });


        getViewHolder().al_refresh_swip.setOnRefreshListener(() -> {
//            ToastUtil.showShortToast("refresh");
            new Handler()
                    .postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            mPresenter.getData("params+url");//view 层请求数据
                            getViewHolder().al_refresh_swip.setRefreshing(false);
                        }
                    }, 2000);
        });

    }


    private ViewHolder viewHolder;

    public ViewHolder getViewHolder() {
        if (viewHolder == null) {
            initVH();
        }
        return viewHolder;
    }

    @Override
    public void initVH() {
        viewHolder = new ViewHolder(this);
    }


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_a_3_0_alibaba;
    }

    //step 6.1
    private void addTitleThree(List<DelegateAdapter.Adapter> adapters, Context mContext) {
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                D.e("==addTitleThree===");
                return new LinearLayoutHelper();
            }

            @Override
            public void onViewRecycled(BaseViewHolder holder) {
                D.e("==onViewRecycled===");
                count = 0;
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                D.e("==addTitleThree===");
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.a_activity_bottom, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                D.e("==start==position=" + position);
                ArrayList<HomeStore> url0s = new ArrayList<HomeStore>();
                holder.addOnClickListener(R.id.home_title_third_bo, view -> MainActivity.toB());
                holder.setText(R.id.home_title_third_bo, "热门商家");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                HomeStore a_first_product = new HomeStore("id", "code", "http://mpic.tiankong.com/893/b7c/893b7c691cab3065c801bcf6860d3299/C1C7F619288CCE91E63AA87AE264704D.jpg", "id", "name");
//                for (int i = 0; i < 20; i++) {
//                    url0s.add(a_first_product);
//                }
                MyFragment myFragment0 = new MyFragment();
                ft.add(R.id.albaba_fragment_bottom, myFragment0);
                myFragment0.setUrls(getmIndexGsonBean().data.storeList);
                ft.commitAllowingStateLoss();
                D.e("==end==position=" + position);
                myFragment0.setAutoChange(true);
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    int count = 0;


    //step 5.1
    private void addTitleTwo(List<DelegateAdapter.Adapter> adapters, Context mContext) {
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
//                LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
//                layoutHelper.setPadding(0, 0, 0, 18);
//                layoutHelper.setBgColor(ContextCompat.getColor(mContext, R.color.gray_bg_ed));

                return new LinearLayoutHelper();
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.a_activity_titles, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                holder.addOnClickListener(R.id.home_title_first, view -> MainActivity.toB());
                holder.setText(R.id.home_title_first, "苗木推荐");
                ListView listView = holder.getView(R.id.list_at_most);
                listView.setAdapter(new PurchaseListAdapter(mContext, getmIndexGsonBean().data.titleList, R.layout.list_view_seedling_new));
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    //step 4.1
    private void addTitleOne(List<DelegateAdapter.Adapter> adapters, Context mContext) {
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {

                LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
                layoutHelper.setPadding(0, 0, 0, 18);
                layoutHelper.setBgColor(ContextCompat.getColor(mContext, R.color.gray_bg_ed));
                layoutHelper.setDividerHeight(18);
                return layoutHelper;
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.a_activity_titles, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {

                if (position == 0) {
                    holder.addOnClickListener(R.id.home_title_first, view -> PurchasePyMapActivity.start2Activity(mContext));
                    holder.setText(R.id.home_title_first, "最新采购");
                    ListView listView = holder.getView(R.id.list_at_most);
                    listView.setAdapter(new PurchaseListAdapter(mContext, getmIndexGsonBean().data.purchaseList, R.layout.list_item_purchase_list_new));
                } else if (position == 1) {
                    /**
                     *    BProduceAdapt bProduceAdapt = new BProduceAdapt(AActivity_3_0_alibaba.this, indexGsonBean.data.seedlingList, R.layout.list_view_seedling_new);
                     ((ListView) findViewById(R.id.lv_00_store)).setAdapter(bProduceAdapt);
                     */
                    holder.addOnClickListener(R.id.home_title_first, view -> MainActivity.toB());
                    holder.setText(R.id.home_title_first, "苗木推荐");
                    ListView listView = holder.getView(R.id.list_at_most);
//                    BProduceAdapt bProduceAdapt = new BProduceAdapt(AActivity_3_0_alibaba.this, indexGsonBean.data.seedlingList, R.layout.list_view_seedling_new);
//                    ((ListView) findViewById(R.id.lv_00_store)).setAdapter(bProduceAdapt);
                    listView.setAdapter(new BProduceAdapt(mContext, getmIndexGsonBean().data.seedlingList, R.layout.list_view_seedling_new));
                }


            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
    }


    //step 3.1
    private void addTopNews(List<DelegateAdapter.Adapter> adapters, Context mContext) {

        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
//        layoutHelper.setAspectRatio(1.0f);
//        layoutHelper.setDividerHeight(10);
//        layoutHelper.setMargin(0, 10, 0, 10);
        layoutHelper.setPadding(0, 18, 0, 18);
        layoutHelper.setBgColor(ContextCompat.getColor(mContext, R.color.gray_bg_ed));
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return layoutHelper;
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.a_activity_top_news, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                holder.convertView.setBackgroundColor(Color.WHITE);
                UPMarqueeView mUPMarqueeView = holder.getView(R.id.upview1);
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_cjgg, null);

                mUPMarqueeView.setViews(getViewsByDatas(mActivity,getmIndexGsonBean().data.articleList));
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });


    }

    //step 2.1
    private void addFourGrid(List<DelegateAdapter.Adapter> adapters, Context mContext) {

        GridLayoutHelper layoutHelper;
        layoutHelper = new GridLayoutHelper(4);
        layoutHelper.setMargin(0, 0, 0, 10);
        layoutHelper.setHGap(1);
        layoutHelper.setAspectRatio(5f);
//        adapters.add(new SubAdapter(this, layoutHelper, 8));
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return layoutHelper;
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.a_activity_four_item, parent, false));
            }


            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {

                SuperTextView mSuperTextView = holder.getView(R.id.a_stv_home_1);
                if (position == 0) {
                    mSuperTextView.setText("苗木商城");
                    mSuperTextView.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.home_miaobusc));
                    holder.addOnClickListener(R.id.a_stv_home_1, view -> MainActivity.toB());
                } else if (position == 1) {
                    mSuperTextView.setText("快速报价");
                    mSuperTextView.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.home_kuaisubj));
                    holder.addOnClickListener(R.id.a_stv_home_1, view -> PurchasePyMapActivity.start2Activity(mContext));
                } else if (position == 2) {
                    mSuperTextView.setText("成交公告");
                    mSuperTextView.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.home_chengjiaogg));
                    holder.addOnClickListener(R.id.a_stv_home_1, view -> NoticeActivity.start2Activity(mContext));
                } else if (position == 3) {
                    mSuperTextView.setText("新闻资讯");
                    mSuperTextView.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.home_xinwenzx));
                    holder.addOnClickListener(R.id.a_stv_home_1, view -> NewsActivity.start2Activity(mContext));
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });


    }


    //step 1.1
    private void addBanner(List<DelegateAdapter.Adapter> adapters, Context mContext) {
        adapters.add(new DelegateAdapter.Adapter<BaseViewHolder>() {

            @Override
            public void onViewRecycled(BaseViewHolder holder) {
                D.e("==onBindViewHolder===");
                if (holder.itemView instanceof ViewPager) {
                    ((ViewPager) holder.itemView).setAdapter(null);
                }
            }

            @Override
            public LayoutHelper onCreateLayoutHelper() {
                D.e("==onBindViewHolder===");
                LinearLayoutHelper mLayoutHelper = new LinearLayoutHelper();
                mLayoutHelper.setAspectRatio(2.0f);
                return mLayoutHelper;
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                D.e("==onBindViewHolder===");
                return new BaseViewHolder
                        (LayoutInflater.from(mContext).inflate(R.layout.a_activity_bannar_top, parent, false));

            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                D.e("==onBindViewHolder===");
                AutoScrollViewPager mViewPager = holder.getView(R.id.a_view_pager);
                CirclePageIndicator mCirclePageIndicator = holder.getView(R.id.a_indicator);
                mCirclePageIndicator.setAlpha((float) 0.6);
                initViewPager(mViewPager, mCirclePageIndicator, null);
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    /**
     * 初始化 顶部
     *
     * @param viewPager
     * @param indicator
     * @param datas
     */
    //step 1.2
    private void initViewPager(AutoScrollViewPager viewPager, CirclePageIndicator indicator, List datas) {

        datas = new ArrayList();
        Map<String, String> map = new HashMap<>();
        map.put("url", "http://avatar.csdn.net/0/C/0/1_pkandroid.jpg");
        map.put("href", "http://blog.csdn.net/pkandroid/article/details/52396864");
        map.put("name", "android开发 继承AppCompatActivity类去掉ActionBar");
        datas.add(map);
        /**
         *   toWebActivity3.putExtra("title", imageIdList.get(position)
         .get("name").toString());
         toWebActivity3.putExtra("url", imageIdList.get(position)
         .get("href").toString());
         */
        ImagePagerAdapter_ali imagePagerAdapter = new ImagePagerAdapter_ali(AActivity_3_0_alibaba.this, getmIndexGsonBean().data.bannerList);
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
    public void initRecycleView(String json) {
        hindLoading();

        // TODO Auto-generated method stub
        IndexGsonBean indexGsonBean = GsonUtil.formateJson2Bean(json, IndexGsonBean.class);

        if (!indexGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
            ToastUtil.showShortToast("数据请求失败！");
            return;
        }
        setmIndexGsonBean(indexGsonBean);

        //成功的时候的处理
        D.e("======json========" +
                "" + json);

        Context mContext = AActivity_3_0_alibaba.this;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.a_recycleview);

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);


        recyclerView.setLayoutManager(layoutManager);


        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((VirtualLayoutManager.LayoutParams) view.getLayoutParams()).getViewPosition();
                outRect.set(4, 4, 4, 4);
            }
        };

        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

        recyclerView.setRecycledViewPool(viewPool);

        viewPool.setMaxRecycledViews(0, 50);//0类型 缓存20个

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        recyclerView.setAdapter(delegateAdapter);
        //适配器的集合 用于管理各个item 不同布局的
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        //steep 1 添加主页banner
        if (BANNER_LAYOUT) {
            addBanner(adapters, mContext);
        }

        //step 2     苗木商城  快速报价  成交公告  新闻资讯
        if (GRID_LAYOUT) {
            addFourGrid(adapters, mContext);
        }

        //step 3  添加成交公告
        if (LINEAR_LAYOUT) {
            addTopNews(adapters, mContext);
        }
        /**
         *      苗木推荐                    更多》》》
         *      商品列表
         */
        //step 4   最新采购横幅 1
        if (LINEAR_LAYOUT) {
            addTitleOne(adapters, mContext);
        }

        /**
         *      最新采购                    更多》》》
         *      快速报价列表
         */
        //step 5   最新采购横幅 1
        if (LINEAR_LAYOUT) {
//            addTitleTwo(adapters, mContext);
        }

        //step 6   end
        if (LINEAR_LAYOUT) {
            addTitleThree(adapters, mContext);
        }
        delegateAdapter.addAdapters(adapters);


    }

    private IndexGsonBean mIndexGsonBean = new IndexGsonBean();

    public IndexGsonBean getmIndexGsonBean() {
        return mIndexGsonBean;
    }

    public void setmIndexGsonBean(IndexGsonBean mIndexGsonBean) {
        this.mIndexGsonBean = mIndexGsonBean;
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
            PurchaseListAdapter adapter = new PurchaseListAdapter(AActivity_3_0_alibaba.this, indexGsonBean.data.purchaseList, R.layout.list_item_purchase_list_new);

        } catch (Exception e) {
            findViewById(R.id.ll_caigou_parent).setVisibility(View.GONE);
            D.e("=============没有采购列表，或者采购数据异常===============");
            e.printStackTrace();
        }
        try {
            if (indexGsonBean.data.seedlingList.size() != 0) {
                findViewById(R.id.ll_tuijian_parent).setVisibility(View.VISIBLE);
            }
            BProduceAdapt bProduceAdapt = new BProduceAdapt(AActivity_3_0_alibaba.this, indexGsonBean.data.seedlingList, R.layout.list_view_seedling_new);
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


    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    public static List<View> getViewsByDatas(Activity aActivity,List<ArticleBean> data) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(aActivity).inflate(R.layout.item_home_cjgg, null);
            //初始化布局的控件
            SuperTextView tv1 = (SuperTextView) moreView.findViewById(R.id.tv_taggle1);
            SuperTextView tv2 = (SuperTextView) moreView.findViewById(R.id.tv_taggle2);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle1).setOnClickListener(view -> {
                String url = "http://192.168.1.252:8090/article/detail/" + data.get(position).id + ".html?isHeader=true";
                D.e("url=" + url);
                NoticeActivity_detail.start2Activity(aActivity, url);
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle2).setOnClickListener(view -> {
                String url = "http://192.168.1.252:8090/article/detail/" + data.get(position + 1).id + ".html?isHeader=true";
                D.e("url=" + url);
                NoticeActivity_detail.start2Activity(aActivity, url);
            });
            //进行对控件赋值
            tv1.setText(data.get(i).title);

            if (data.get(i).isNew) {
                tv1.setShowState(data.get(i).isNew);
                tv1.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
            } else {
                tv1.setPadding(10, 0, 0, 0);
                tv1.setShowState(data.get(i).isNew);
            }

            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).title);

                if (data.get(i + 1).isNew) {
                    tv2.setShowState(true);
                    tv2.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
                } else {
                    tv2.setPadding(10, 0, 0, 0);
                    tv2.setShowState(false);
                }
            } else {
                moreView.findViewById(R.id.tv_taggle2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }

        return views;
    }



    @Override
    public void showErrir(String erMst) {
        //失败的处理
        D.e("======errorMsg========" + "" + erMst);
        hindLoading();
    }

    private class ViewHolder {
        public SuperTextView tv_a_search;
        public ImageView iv_a_msg;
        public RecyclerView a_recycleview;
        public SwipeRefreshLayout al_refresh_swip;

        public ViewHolder(Activity rootView) {
            this.tv_a_search = (SuperTextView) rootView.findViewById(R.id.tv_a_search);
            this.iv_a_msg = (ImageView) rootView.findViewById(R.id.iv_a_msg);
            this.a_recycleview = (RecyclerView) rootView.findViewById(R.id.a_recycleview);
            this.al_refresh_swip = (SwipeRefreshLayout) rootView.findViewById(R.id.al_refresh_swip);
        }

    }
}
