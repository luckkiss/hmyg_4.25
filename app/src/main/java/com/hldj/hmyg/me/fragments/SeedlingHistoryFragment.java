package com.hldj.hmyg.me.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;
import com.hldj.hmyg.saler.P.BasePresenter;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 商城资源 历史记录
 */

public class SeedlingHistoryFragment extends BaseRecycleViewFragment<BPageGsonBean.DatabeanX.Pagebean.Databean> {


    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>>>() {
        }.getType();

        new BasePresenter()

                .doRequest("admin/footmark/seedling/list", new HandlerAjaxCallBackPage<BPageGsonBean.DatabeanX.Pagebean.Databean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> e) {
                        mCoreRecyclerView.getAdapter().addData(e);


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mCoreRecyclerView.selfRefresh(false);
                    }
                });

    }

    @Override
    protected void onRecycleViewInited(CoreRecyclerView corecyclerView) {

        corecyclerView.getRecyclerView().addItemDecoration(
                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {

                        if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
                            return null;
                        } else {
                            return mCoreRecyclerView.getAdapter().getItem(position) + "";
                        }

                    }

                    @Override
                    public View getGroupView(int position) {
//                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
//                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
//                        TextView tv = view.findViewById(android.R.id.text1);
//                        tv.setText(recycler.getAdapter().getItem(position) + "");
                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
                        TextView textView = view.findViewById(R.id.text1);
                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
                        textView.setText("2018-5-6");
                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());


    }

    @Override
//    protected void doConvert(BaseViewHolder helper, SaveSeedingGsonBean.DataBean.SeedlingBean item, NeedSwipeBackActivity mActivity) {
    protected void doConvert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        helper.getConvertView().setOnClickListener(v -> {
            FlowerDetailActivity.start2Activity(mActivity, "seedling_list", item.id);
        });
//        D.i("=============doConvert==============" + item.getName());
        BActivity_new_test.initListType(helper, item, FinalBitmap.create(mActivity), "BActivity_new");

    }

    @Override
    public int bindRecycleItemId() {
        return R.layout.list_view_seedling_new;
    }

}
