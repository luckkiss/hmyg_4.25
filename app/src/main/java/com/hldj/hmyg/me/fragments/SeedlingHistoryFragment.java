package com.hldj.hmyg.me.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
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
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 商城资源 历史记录
 */

public class SeedlingHistoryFragment extends BaseRecycleViewFragment<BPageGsonBean.DatabeanX.Pagebean.Databean> implements IFootMarkEmpty {


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
//                            dateStr
                            BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
                            return databean.attrData.dateStr;
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
                        BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
                        textView.setText(databean.attrData.dateStr);
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

        helper
                .setVisible(R.id.tv_right_top, true)
                .addOnClickListener(R.id.tv_right_top, v -> {
                    doUserDelDelete(helper, item, mActivity);
                })
        ;


    }


    @Override
    public int bindRecycleItemId() {
        return R.layout.list_view_seedling_new_shoucan;
    }

    @Override
    public void doEmpty() {
//        ToastUtil.showLongToast("清空  苗木资源");
        doUserDelDelete(null, this, mActivity);
    }

    @Override
    public String getResourceId() {
        return null;
    }

    @Override
    public String getDomain() {
        return "admin/footmark/userDelBySource";
    }

    @Override
    public FootMarkSourceType sourceType() {
        return FootMarkSourceType.seedling;
    }


}
