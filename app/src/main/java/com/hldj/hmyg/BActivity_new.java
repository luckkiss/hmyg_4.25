package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.P.BPresenter;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.QueryBean;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.buyer.weidet.BaseMultAdapter;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SortSpinner;

import net.tsz.afinal.FinalBitmap;

import java.util.List;
import java.util.Map;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagFactory;
import me.kaede.tagview.TagView;

import static com.hldj.hmyg.R.id.iv_img;
import static com.hldj.hmyg.buyer.weidet.BaseMultAdapter.GRID_VIEW;
import static com.hldj.hmyg.util.ConstantParams.container;
import static com.hldj.hmyg.util.ConstantParams.heelin;
import static com.hldj.hmyg.util.ConstantParams.planted;
import static com.hldj.hmyg.util.ConstantParams.transplant;
import static com.hldj.hmyg.util.ConstantState.FILTER_OK;
import static com.hldj.hmyg.util.ConstantState.SEARCH_OK;


/**
 * 商城界面
 */
@SuppressLint("NewApi")
public class BActivity_new extends NeedSwipeBackActivity {

    private CoreRecyclerView recyclerView1;

    int type = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_to_toolbar);
        bitmap = FinalBitmap.create(this);

        initViewClick();
        recyclerView1 = (CoreRecyclerView) findViewById(R.id.core_rv_b);
        recyclerView1.getRecyclerView().setHasFixedSize(true);
//        recyclerView1.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0, 0, 0, 1);
//            }
//        });
        D.e("======设置fix======");
        recyclerView1.init(new BaseMultAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean, BaseViewHolder>(R.layout.list_view_seedling_new, R.layout.grid_view_seedling) {
            @Override
            protected void convert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
                if (type == GRID_VIEW) {
                    D.e("=======GRID_VIEW========");
                    initGridType(helper, item);
                } else {
                    D.e("=======常规布局========");
                    initListType(helper, item, bitmap, "BActivity_new");
                }
                helper.getConvertView().setOnClickListener(v -> {
                    FlowerDetailActivity.start2Activity(BActivity_new.this, "seedling_list", item.id);
                });
            }
        }).openLoadMore(getQueryBean().pageSize, page -> {
            queryBean.pageIndex = page;
            initData();
        }).openRefresh()
                .openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                .selfRefresh(true);


        getExtras();//在初始化数据之前
        new Handler().postDelayed(() -> initData(), 800);


    }

    private void initViewClick() {
        //搜索

        getView(R.id.sptv_b_search).setOnClickListener(v -> {
            Intent intent = new Intent(BActivity_new.this, PurchaseSearchListActivity.class);
            intent.putExtra("from", "BActivity");
            startActivityForResult(intent, 1);
        });
        //筛选
        getView(R.id.tv_b_filter).setOnClickListener(v -> {
            SellectActivity2.start2Activity(this, queryBean);
        });

        getView(R.id.iv_view_type).setOnClickListener(v -> {
            if (v.isSelected())//选中.点击了grid 变换成grid
            {
                D.e("==isSelected==" + v.isSelected());
                changeStyle(v.isSelected());
                v.setSelected(false);
            } else//未选中  点击了list 变成list
            {
                D.e("==isSelected==" + v.isSelected());
                changeStyle(v.isSelected());
                v.setSelected(true);
            }
        });

        //排序
        getView(R.id.tv_b_sort).setOnClickListener(v -> {
            ChoiceSortList();
        });


    }


    int pos = 0;
    public SortSpinner sortSpinner;

    /**
     * 排序 显示位置不对. 小米上正确
     */
    private void ChoiceSortList() {
        View view = getView(R.id.tagview_b_act);
        if (sortSpinner == null) {
            sortSpinner = SortSpinner.getInstance(BActivity_new.this, view)
                    .addOnItemClickListener((parent, view1, position, id) -> {
                        D.e("addOnItemClickListener" + position);
                        switch (position) {
                            case 0:
                                getQueryBean().orderBy = "default_asc";//综合排序
                                break;
                            case 1:
                                getQueryBean().orderBy = "publishDate_desc";//最新发布
                                break;
                            case 2:
                                getQueryBean().orderBy = "distance_asc";//最近距离
                                break;
                            case 3:
                                getQueryBean().orderBy = "price_asc";//价格从低到高
                                break;
                            case 4:
                                getQueryBean().orderBy = "price_desc";//综合排序
                                break;
                        }
                        pos = position;
                        sortSpinner.dismiss();
                        refreshRc();
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


    public SparseArray<Map<String, String>> mapSparseArray = new SparseArray<>();

    private void addTagsByBean(QueryBean queryBean) {
        TagView tagView = getView(R.id.tagview_b_act);
        tagView.removeAllTags();


        //最小 最大 hight
        tagView.addTag(TagFactory.createDelTag(queryBean.minHeight, queryBean.maxHeight, "高度"), 95);

        //最小 最大  crow
        tagView.addTag(TagFactory.createDelTag(queryBean.minCrown, queryBean.maxCrown, "冠幅"), 96);

        //最小
        tagView.addTag(TagFactory.createDelTag(queryBean.minRod, queryBean.maxRod, "杆径"), 97);


        if (!TextUtils.isEmpty(queryBean.plantTypes)) {
            String[] strs = queryBean.plantTypes.split(",");
            for (int i = 0; i < strs.length; i++) {
                tagView.addTag(TagFactory.createDelTag(strs[i]), getTypeId(strs[i]));
            }
        }
        if (!TextUtils.isEmpty(queryBean.searchKey)) {
            tagView.addTag(TagFactory.createDelTag(queryBean.searchKey), 98);
        }

        if (!TextUtils.isEmpty(SellectActivity2.childBeans.name))
            tagView.addTag(TagFactory.createDelTag(SellectActivity2.childBeans.name), 99);

//        tagView.addTag(TagFactory.createDelTag(queryBean.searchSpec), 100);

        tagView.setOnTagDeleteListener((position, tag) -> {
            if (tag.id == 97) {
                getQueryBean().specMinValue = "";
                getQueryBean().specMaxValue = "";
                getQueryBean().searchSpec = "";
            } else if (tag.id == 98) {
                getQueryBean().searchKey = "";
            } else if (tag.id == 99) {
                //城市被删除
                SellectActivity2.childBeans = new CityGsonBean.ChildBeans();
                queryBean.cityCode = "";
            } else if (tag.id == 90) {
                // 范围删除
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(planted + ",", "");
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(planted, "");
            } else if (tag.id == 91) {
                // 范围删除
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(container + ",", "");
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(container, "");
            } else if (tag.id == 92) {
                // 范围删除
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(heelin + ",", "");
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(heelin, "");
            } else if (tag.id == 93) {
                // 范围删除
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(transplant + ",", "");
                queryBean.plantTypes = queryBean.plantTypes.replaceAll(transplant, "");
            }

            if (queryBean.plantTypes.endsWith(",")) {
                queryBean.plantTypes = queryBean.plantTypes.substring(0, queryBean.plantTypes.length() - 1);
            }
            D.e("===" + getQueryBean().toString());
            refreshRc();

        });
    }


    /**
     * case planted:
     * return "地栽苗";
     * case container:
     * return "容器苗";
     * case heelin:
     * return "假植苗";
     * case transplant:
     * return "移植苗";
     *
     * @param str
     * @return
     */
    private int getTypeId(String str) {
        if (str.equals(planted)) return 90;
        if (str.equals(container)) return 91;
        if (str.equals(heelin)) return 92;
        if (str.equals(transplant)) return 93;
        return -1;
    }

    private void changeStyle(boolean selected) {

        if (!selected)//grid
        {
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
        } else {//list
            RecyclerView.LayoutManager layoutManager = recyclerView1.getRecyclerView().getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法

            if (layoutManager instanceof LinearLayoutManager) {

            } else if (layoutManager instanceof GridLayoutManager) {
                D.e("=======GridLayoutManager========");
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

                now_position = gridLayoutManager.findFirstVisibleItemPosition();


                //获取第一个可见view的位置
                D.e("=======firstItemPosition========");

            }

            ((BaseMultAdapter) recyclerView1.getAdapter()).setDefaultType(0);
            type = 0;
            recyclerView1.getRecyclerView().setLayoutManager(new LinearLayoutManager(BActivity_new.this));
            recyclerView1.getAdapter().notifyDataSetChanged();

            recyclerView1.getRecyclerView().scrollToPosition(now_position);

            startAnimation(recyclerView1.getRecyclerView(), R.anim.zoom_in);


        }
        recyclerView1.getAdapter().onAttachedToRecyclerView(recyclerView1.getRecyclerView());
    }

    int now_position = 0;
    FinalBitmap bitmap;

    /**
     * 初始化listview 列表
     *
     * @param helper
     * @param item
     */
    public static void initListType(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, FinalBitmap bitmap, String tag) {
        bitmap.display(helper.getView(iv_img), item.smallImageUrl);

        TextView tv_01 = helper.getView(R.id.tv_01);
        MySwipeAdapter.setSrcByType(tv_01, item.plantType);

//        View iv_right_top = helper.getView(R.id.iv_right_top);
        TextView tv_04 = helper.getView(R.id.tv_04);


        if (tag.equals("BActivity_new")) {
//            iv_right_top.setVisibility(item.attrData.ziying ? View.VISIBLE : View.GONE);

            if (tv_04 !=null )
            tv_04.setText("苗源地: " + item.ciCity.fullName);
        } else {
//            iv_right_top.setVisibility(View.GONE);
            if (tv_04 !=null )
            tv_04.setText("苗源地: " + item.nurseryJson.getCityName());
        }


        TextView tv_02 = helper.getView(R.id.tv_02);
        tv_02.setText(item.standardName);

        TextView tv_03 = helper.getView(R.id.tv_03);//sptext
        tv_03.setText(item.specText);


        TextView tv_06 = helper.getView(R.id.tv_06);


        if (item.ownerJson != null) {
            BProduceAdapt.setPublishName(tv_06,
                    item.ownerJson.companyName,
                    item.ownerJson.publicName,
                    item.ownerJson.realName);
        } else {
            if (item.closeDate.length() > 10) {
                tv_06.setText("下架日期：" + item.closeDate.substring(0, 10));
            } else {
                tv_06.setText("下架日期：" + item.closeDate);
            }
        }

        TextView tv_07 = helper.getView(R.id.tv_07);
        TextView tv_08 = helper.getView(R.id.tv_08);
        ProductListAdapter.setPrice(tv_07, item.priceStr, item.minPrice, item.isNego, tv_08);

        tv_08.setText("元/" + item.unitTypeName);
        TextView tv_09 = helper.getView(R.id.tv_09);
        tv_09.setText("库存：" + item.count);

    }

    private void initGridType(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        TextView tv_01 = helper.getView(R.id.tv_01);
        MySwipeAdapter.setSrcByType(tv_01, item.plantType);
        TextView tv_02 = helper.getView(R.id.tv_02);
        tv_02.setText(item.standardName);
        TextView tv_03 = helper.getView(R.id.tv_03);
        tv_03.setText(item.ciCity.fullName);
        TextView tv_07 = helper.getView(R.id.tv_07);
        TextView tv_08 = helper.getView(R.id.tv_08);
        ProductListAdapter.setPrice(tv_07, item.maxPrice, item.minPrice, item.isNego, tv_08);

        tv_08.setText("元/" + item.unitTypeName);
        bitmap.display(iv_img, item.smallImageUrl);
    }

    private void initData() {
        BasePresenter bPresenter = new BPresenter()
                .putParams(getQueryBean())//传一个对象进去
                .addResultCallBack(new ResultCallBack<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>() {
                    @Override
                    public void onSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> pageBean) {
                        recyclerView1.selfRefresh(false);
                        D.e("==============");
                        recyclerView1.getAdapter().addData(pageBean);

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        D.e("==============");
                        recyclerView1.selfRefresh(false);
                    }
                });
        ((BPresenter) bPresenter).getDatas("seedling/list", false);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SEARCH_OK) {
            // 在商城搜索界面过来的

            getQueryBean().searchKey = data.getStringExtra("searchKey");

//            addADelTag();
            addTagsByBean(queryBean);

            refreshRc();

        } else if (resultCode == FILTER_OK) {//筛选结束
            if (data != null && data.getExtras().getSerializable("hellow") != null) {
                queryBean = (QueryBean) data.getExtras().getSerializable("hellow");
//                getQueryBean().searchKey = "";
            }
            D.e("==" + queryBean.toString());
            addTagsByBean(queryBean);
            refreshRc();
        }
    }

    private void addADelTag() {
        TagView tagView = getView(R.id.tagview_b_act);
        tagView.removeAllTags();
        if (!TextUtils.isEmpty(getQueryBean().searchKey)) {
            Tag tag = new Tag(getQueryBean().searchKey);
            tag.layoutColor = MyApplication.getInstance().getResources().getColor(R.color.main_color);
            tag.isDeletable = true;
            tag.id = 1; // 1 搜索 2分类
            tagView.addTag(tag);
            tagView.setOnTagDeleteListener((position, tag1) -> {
                getQueryBean().searchKey = "";
                refreshRc();
            });//删除事件。。。并且刷新
        }
    }


    public void getExtras() {

        if (!TextUtils.isEmpty(getIntent().getStringExtra("tag"))) {
            getQueryBean().searchKey = getIntent().getStringExtra("tag");
            addADelTag();

            setSwipeBackEnable(true);//默认不可滑动
            getView(R.id.ib_b_back).setVisibility(View.VISIBLE);
            getView(R.id.ib_b_back).setOnClickListener(view -> finish());

        } else {
            setSwipeBackEnable(false);//默认不可滑动
            getView(R.id.ib_b_back).setVisibility(View.GONE);
        }

    }

    public static void start2Activity(Context context, String tag) {
        Intent intent = new Intent(context, BActivity_new.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    public void refreshRc() {
//        getQueryBean().pageIndex = 0;
        recyclerView1.onRefresh();
//        recyclerView1.selfRefresh(true);
//        new Handler().postDelayed(() -> initData(), 600);
    }
}
