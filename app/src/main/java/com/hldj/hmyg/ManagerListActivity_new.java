package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.adapter.ProductListAdapterForManager;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.model.ManagerListModel;
import com.hldj.hmyg.presenter.ManagerListPresenter;
import com.hldj.hmyg.saler.SearchActivity;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import me.kaede.tagview.TagView;

/**
 * 苗木管理界面
 */
@SuppressLint("ClickableViewAccessibility")
public class ManagerListActivity_new extends BaseMVPActivity<ManagerListPresenter, ManagerListModel> implements ManagerListContract.View {

    private String status = "";
    private String searchKey = "";
    CoreRecyclerView xRecyclerView;

    List<TextView> list_counts = new ArrayList<>();//显示count 的几个textview

    @Override
    public int bindLayoutID() {
        return R.layout.activity_manager_list_new;
    }

    @Override
    public void initView() {
        initToolbar();
        xRecyclerView = getView(R.id.xrecycle);
//        list_view_seedling_new

        xRecyclerView.init(new BaseQuickAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean, BaseViewHolder>(R.layout.list_view_seedling_new) {
            @Override
            protected void convert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
                BActivity_new.initListType(helper, item, FinalBitmap.create(mContext));
                ProductListAdapterForManager.setStateColor(helper.getView(R.id.tv_right_top), item.status, item.statusName);


                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent toFlowerDetailActivity = new Intent(ManagerListActivity_new.this, FlowerDetailActivity.class);
                        toFlowerDetailActivity.putExtra("id", item.id);
                        toFlowerDetailActivity.putExtra("show_type", "manage_list");
                        startActivityForResult(toFlowerDetailActivity, 1);
                    }
                });

            }
        }).openLoadMore(10, page -> {
            xRecyclerView.selfRefresh(true);
            mPresenter.getData(page + "", status, searchKey);
        }).openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                .openRefresh();

        switch2Refresh("", 0);
        mPresenter.getCounts();


    }


    @Override
    public void initVH() {

        getView(R.id.rl_01).setOnClickListener(view -> {//所有
            switch2Refresh("", 0);
        });
        getView(R.id.rl_02).setOnClickListener(view -> {//所有
            switch2Refresh("unaudit", 1);
        });
        getView(R.id.rl_03).setOnClickListener(view -> {//所有

            switch2Refresh("published", 2);

        });
        getView(R.id.rl_04).setOnClickListener(view -> {//所有

            switch2Refresh("outline", 3);

        });
        getView(R.id.rl_05).setOnClickListener(view -> {//所有

            switch2Refresh("backed", 4);

        });
        getView(R.id.rl_06).setOnClickListener(view -> {//所有
            switch2Refresh("unsubmit", 5);

        });


    }


    TextView[] tvs = null;

    private void switch2Refresh(String stat, int index) {

        if (tvs == null) {
            tvs = new TextView[]{getView(R.id.tv_01), getView(R.id.tv_02), getView(R.id.tv_03), getView(R.id.tv_04), getView(R.id.tv_05), getView(R.id.tv_06)};
        }

        status = stat;
        for (int i1 = 0; i1 < tvs.length; i1++) {
            if (index == i1) {
                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            } else {
                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            }
        }
        xRecyclerView.onRefresh();


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    private void initToolbar() {
        ImageView iv_search = getView(R.id.toolbar_right_icon);
        iv_search.setVisibility(View.VISIBLE);
        setTitle("苗木管理");
        iv_search.setOnClickListener(view -> {
            Intent toSearchActivity = new Intent(ManagerListActivity_new.this, SearchActivity.class);
            toSearchActivity.putExtra("searchKey", searchKey);
            startActivityForResult(toSearchActivity, 1);
        });
    }

//    private void setCounts() {
//        BasePresenter listPresenter = new ManagerListPresenter()
//                .addResultCallBack(new ResultCallBack<CountTypeGsonBean.DataBean.CountMapBean>() {
//                    @Override
//                    public void onSuccess(CountTypeGsonBean.DataBean.CountMapBean countMapBean) {
//                        list_counts.clear();
//                        LinearLayout linearLayout = getView(R.id.ll_counts_content);
//                        findTagTextView(linearLayout);
//                        for (int i = 0; i < list_counts.size(); i++) {
//                            switch (i) {
//                                case 0:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.all + ")");
//                                    break;
//                                case 1:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.unaudit + ")");
//                                    break;
//                                case 2:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.published + ")");
//                                    break;
//                                case 3:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.outline + ")");
//                                    break;
//                                case 4:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.backed + ")");
//                                    break;
//                                case 5:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.unsubmit + ")");
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                    }
//                });
//        ((ManagerListPresenter) listPresenter).getStatusCount();
//
//
////        findTagTextView(linearLayout);
//
//
//    }


    private void findTagTextView(ViewGroup viewGroup) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findTagTextView(((ViewGroup) viewGroup.getChildAt(i)));
            } else {
                if (viewGroup.getChildAt(i).getTag() != null && viewGroup.getChildAt(i).getTag().toString().equals("tag")) {
                    list_counts.add((TextView) viewGroup.getChildAt(i));
                }
            }
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, ManagerListActivity_new.class));
    }


    @Override
    public void initXRecycle(BPageGsonBean gsonBean) {

        xRecyclerView.getAdapter().addData(gsonBean.data.page.data);
//        xRecyclerView.getAdapter().notifyDataSetChanged();

        xRecyclerView.selfRefresh(false);

    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg2 != null && arg1 == 6) {
            switch2Refresh("", 0);
            searchKey = arg2.getStringExtra("searchKey");
            TagView tagView = getView(R.id.tagview);
            tagView.removeAllTags();

            if (!"".equals(searchKey)) {
                me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(searchKey);
                tag.layoutColor = ContextCompat.getColor(mActivity, R.color.main_color);
                tag.isDeletable = true;
                tag.id = 1; // 关键字
                tagView.addTag(tag);
                tagView.setOnTagDeleteListener((position, tag1) -> {
                    searchKey = "";
                    xRecyclerView.onRefresh();
                });
            }

            xRecyclerView.onRefresh();
        }

        super.onActivityResult(arg0, arg1, arg2);
    }


    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {
        list_counts.clear();
        LinearLayout linearLayout = getView(R.id.ll_counts_content);
        findTagTextView(linearLayout);
        for (int i = 0; i < list_counts.size(); i++) {
            switch (i) {
                case 0:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.all + ")");
                    break;
                case 1:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.unaudit + ")");
                    break;
                case 2:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.published + ")");
                    break;
                case 3:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.outline + ")");
                    break;
                case 4:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.backed + ")");
                    break;
                case 5:
                    ((TextView) list_counts.get(i)).setText("(" + gsonBean.data.countMap.unsubmit + ")");
                    break;

            }
        }

    }

    @Override
    public void showError(String errorMsg) {
        //此处处理错误 样式

        if (xRecyclerView != null) {
            xRecyclerView.selfRefresh(false);

        }
    }

    FinalBitmap bitmap;

}
