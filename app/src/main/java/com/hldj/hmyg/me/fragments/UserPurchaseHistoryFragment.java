package com.hldj.hmyg.me.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity;
import com.hldj.hmyg.util.D;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 用户求购足迹列表
 */

public class UserPurchaseHistoryFragment extends BaseRecycleViewFragment<UserPurchase> {


    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();

        new BasePresenter()

                .doRequest("admin/footmark/userPurchase/list", new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> e) {
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
                        textView.setText(((UserPurchase) mCoreRecyclerView.getAdapter().getItem(position)).createDate);
                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());


    }

    @Override
    protected void doConvert(BaseViewHolder helper, UserPurchase item, NeedSwipeBackActivity mActivity) {

        D.i("=============doConvert==============" + item.name);
        BuyForUserActivity.doConvert(helper, item, mActivity);


    }

    @Override
    public int bindRecycleItemId() {
        return R.layout.item_buy_for_user;
    }

}
