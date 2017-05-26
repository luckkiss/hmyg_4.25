package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.P.BPresenter;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.bean.QueryBean;
import com.hldj.hmyg.buyer.weidet.BaseMultAdapter;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.mrwujay.cascade.activity.BaseSecondActivity;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.R.id.iv_img;
import static com.hldj.hmyg.buyer.weidet.BaseMultAdapter.GRID_VIEW;


/**
 * 商城界面
 */
@SuppressLint("NewApi")
public class BActivity_new extends BaseSecondActivity {

    private CoreRecyclerView recyclerView1;

    int type = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_to_toolbar);


        initViewClick();

//        final ListView recyclerView = (ListView) findViewById(R.id.xlistView);
//        ArrayAdapter<String> myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getDatas());//适配器
//        recyclerView.setAdapter(myAdapter);

//(((BaseMultAdapter) recyclerView1.getAdapter())).getDefaultType() == GRID_VIEW
        recyclerView1 = (CoreRecyclerView) findViewById(R.id.core_rv_b);
        // List<BPageGsonBean.DatabeanX.Pagebean.Databean> data
        recyclerView1.init(new BaseMultAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean, BaseViewHolder>(R.layout.list_view_seedling_new, R.layout.grid_view_seedling) {
            @Override
            protected void convert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
                if (type == GRID_VIEW) {
                    D.e("=======GRID_VIEW========");
                    initGridType(helper, item);
                } else {
                    D.e("=======常规布局========");
                    initListType(helper, item);
                }
                helper.getConvertView().setOnClickListener(v -> {
                    FlowerDetailActivity.start2Activity(BActivity_new.this, "seedling_list", item.id);
                });
            }


        }).openLoadMore(getQueryBean().pageSize, page -> {
//            recyclerView1.getAdapter().addData(getDatas());
            queryBean.pageIndex = page;
            initData();
        }).openRefresh()
                .openLoadAnimation(BaseQuickAdapter.SCALEIN)
                .selfRefresh(true);

        new Handler().postDelayed(() -> {

//            getBeanFileName();
            initData();

//            recyclerView1.getAdapter().addData(getDatas());
//            recyclerView1.selfRefresh(false);
        }, 1500);


        findViewById(R.id.iv_view_type).setOnClickListener(v -> {


            RecyclerView.LayoutManager layoutManager = recyclerView1.getRecyclerView().getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法

            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                //获取第一个可见view的位置
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                D.e("=======firstItemPosition========" + firstItemPosition);
                now_position = firstItemPosition;
            } else if (layoutManager instanceof GridLayoutManager) {
                D.e("=======GridLayoutManager========");
            }
            ((BaseMultAdapter) recyclerView1.getAdapter()).setDefaultType(GRID_VIEW);

            type = GRID_VIEW;
            recyclerView1.getRecyclerView().setLayoutManager(new GridLayoutManager(BActivity_new.this, 2));
            recyclerView1.getAdapter().notifyDataSetChanged();
            startAnimation(recyclerView1.getRecyclerView(), R.anim.zoom_in);
            recyclerView1.getRecyclerView().scrollToPosition(now_position);

        });
        findViewById(R.id.RelativeLayout2).setOnClickListener(v -> {

            RecyclerView.LayoutManager layoutManager = recyclerView1.getRecyclerView().getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法

            if (layoutManager instanceof LinearLayoutManager) {

            } else if (layoutManager instanceof GridLayoutManager) {
                D.e("=======GridLayoutManager========");
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

                //获取第一个可见view的位置
                D.e("=======firstItemPosition========");

            }

            ((BaseMultAdapter) recyclerView1.getAdapter()).setDefaultType(0);
            type = 0;
            recyclerView1.getRecyclerView().setLayoutManager(new LinearLayoutManager(BActivity_new.this));
            recyclerView1.getAdapter().notifyDataSetChanged();

            recyclerView1.getRecyclerView().scrollToPosition(now_position);

            startAnimation(recyclerView1.getRecyclerView(), R.anim.zoom_in);

        });


    }

    private void initViewClick() {

        //筛选
        getView(R.id.tv_b_filter).setOnClickListener(v -> {
            SellectActivity2.start2Activity(this, queryBean);
//            toSellectActivity.putExtra("from", "BActivity");
//            toSellectActivity.putExtra("cityCode", cityCode);
//            toSellectActivity.putExtra("cityName", cityName);
//            toSellectActivity.putExtra("plantTypes", plantTypes);
//            toSellectActivity.putStringArrayListExtra("planttype_has_ids", planttype_has_ids);
//            toSellectActivity.putExtra("searchSpec", searchSpec);
//            toSellectActivity.putExtra("specMinValue", specMinValue);
//            toSellectActivity.putExtra("specMaxValue", specMaxValue);
//            toSellectActivity.putExtra("searchKey", searchKey);
//            startActivityForResult(toSellectActivity, 1);
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });
        //排序
        getView(R.id.tv_b_sort).setOnClickListener(v -> {

        });


    }

    int now_position = 0;
    FinalBitmap bitmap = FinalBitmap.create(this);

    /**
     * 初始化listview 列表
     *
     * @param helper
     * @param item
     */
    private void initListType(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
        bitmap.display(helper.getView(iv_img), item.smallImageUrl);

        TextView tv_01 = helper.getView(R.id.tv_01);
        MySwipeAdapter.setSrcByType(tv_01, item.plantType);

        TextView tv_02 = helper.getView(R.id.tv_02);
        tv_02.setText(item.name);

        TextView tv_03 = helper.getView(R.id.tv_03);
        tv_03.setText(item.ciCity.fullName);

        TextView tv_04 = helper.getView(R.id.tv_04);
        tv_04.setText(item.specText);

        TextView tv_06 = helper.getView(R.id.tv_06);
        BProduceAdapt.setPublishName(tv_06,
                item.ownerJson.companyName,
                item.ownerJson.publicName,
                item.ownerJson.realName);

        TextView tv_07 = helper.getView(R.id.tv_07);
        ProductListAdapter.setPrice(tv_07, item.maxPrice, item.minPrice, item.isNego);
        TextView tv_08 = helper.getView(R.id.tv_08);
        tv_08.setText("元/" + item.unitTypeName);
        TextView tv_09 = helper.getView(R.id.tv_09);
        tv_09.setText("库存：" + item.count);

    }

    private void initGridType(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        TextView tv_01 = helper.getView(R.id.tv_01);
        MySwipeAdapter.setSrcByType(tv_01, item.plantType);
        TextView tv_02 = helper.getView(R.id.tv_02);
        tv_02.setText(item.name);
        TextView tv_03 = helper.getView(R.id.tv_03);
        tv_03.setText(item.ciCity.fullName);
        TextView tv_07 = helper.getView(R.id.tv_07);
        ProductListAdapter.setPrice(tv_07, item.maxPrice, item.minPrice, item.isNego);
        TextView tv_08 = helper.getView(R.id.tv_08);
        tv_08.setText("元/" + item.unitTypeName);
        bitmap.display(iv_img, item.smallImageUrl);
    }

    public List<String> getDatas() {
        List list_datas = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list_datas.add("data" + i);
        }
        return list_datas;
    }


    boolean getdata;

    private void initData() {

        getdata = false;

        BasePresenter bPresenter = new BPresenter()
                .putParams(getQueryBean())//传一个对象进去
                .addResultCallBack(new ResultCallBack<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>() {
                    @Override
                    public void onSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> pageBean) {
                        D.e("==============");
                        recyclerView1.getAdapter().addData(pageBean);
                        recyclerView1.selfRefresh(false);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        D.e("==============");
                        recyclerView1.selfRefresh(false);
                    }
                });
        ((BPresenter) bPresenter).getDatas("seedling/list", false);

        getdata = true;
    }

    private QueryBean queryBean;

    public QueryBean getQueryBean() {

        if (queryBean == null) {
            queryBean = new QueryBean();
        }
        return queryBean;
    }


    /**
     * 开启动画
     */
    private void startAnimation(RecyclerView rv, int anim) {

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, anim));
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        rv.setLayoutAnimation(lac);
        rv.startLayoutAnimation();
    }

}
