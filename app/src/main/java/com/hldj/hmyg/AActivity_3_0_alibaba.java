package com.hldj.hmyg;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.autoscrollview.adapter.ImagePagerAdapter;
import com.autoscrollview.widget.AutoScrollViewPager;
import com.autoscrollview.widget.indicator.CirclePageIndicator;
import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.Ui.NewsActivity;
import com.hldj.hmyg.Ui.NoticeActivity;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.saler.purchase.PurchasePyMapActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AActivity_3_0_alibaba extends FragmentActivity {


    private static final boolean BANNER_LAYOUT = true;//首页bannar ，轮播效果
    //首页 4个 快速挑战按钮   （苗木商城   快速报价  成交公告  新闻资讯）
    private static final boolean GRID_LAYOUT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_3_0_alibaba);
//       Toolbar toolbar = (Toolbar) findViewById(R.id.a_toolbar);
//       setSupportActionBar(toolbar);

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

        viewPool.setMaxRecycledViews(0, 20);//0类型 缓存20个

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        recyclerView.setAdapter(delegateAdapter);
        //适配器的集合 用于管理各个item 不同布局的
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        //steep 1 添加主页banner
        if (BANNER_LAYOUT) {
            addBanner(adapters, mContext);
        }

        //step 2
        if (GRID_LAYOUT) {
            addFourGrid(adapters, mContext);
        }


        delegateAdapter.addAdapters(adapters);


    }

    private void addFourGrid(List<DelegateAdapter.Adapter> adapters, Context mContext) {

        GridLayoutHelper layoutHelper;
        layoutHelper = new GridLayoutHelper(4);
        layoutHelper.setMargin(0, 10, 0, 10);
        layoutHelper.setHGap(1);
        layoutHelper.setAspectRatio(4f);
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
                if (holder.itemView instanceof ViewPager) {
                    ((ViewPager) holder.itemView).setAdapter(null);
                }
            }

            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return new LinearLayoutHelper();
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                return new BaseViewHolder
                        (LayoutInflater.from(mContext).inflate(R.layout.a_activity_bannar_top, parent, false));

            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
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
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(AActivity_3_0_alibaba.this, datas);
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

}
