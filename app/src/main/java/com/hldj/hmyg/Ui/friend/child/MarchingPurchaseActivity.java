package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * 匹配求购界面
 */

public class MarchingPurchaseActivity extends BaseMVPActivity {

    int item_layout_id = R.layout.item_buy_for_user;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_marching_purchase;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        getView(R.id.ckwdbj).setVisibility(View.VISIBLE);
        getView(R.id.ckwdbj).setOnClickListener(v -> {
            ManagerQuoteListActivity_new.initLeft = false;
            ManagerQuoteListActivity_new.start2Activity(mActivity);
        });

        recycler.init(new BaseQuickAdapter<UserPurchase, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, UserPurchase item) {
                BuyForUserActivity.doConvert(helper, item, mActivity);
                helper.convertView.setOnClickListener(v ->
                        PublishForUserDetailActivity.start2Activity(mActivity, item.id, item.ownerId, true)
                );


            }
        })
                .setDefaultEmptyView(true, "发布苗木", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtil.showShortToast("发布苗木");
//                        PublishForUserActivity.start2Activity(mActivity);
                        finish();

                        MainActivity.toD();

                    }
                })
                .showEmptyAndSetTip("苗圃里的苗木品种越多，匹配到的求购越多")
                .setEmptyText("未匹配到求购信息")
                .openRefresh()
                .openLoadMore(20, this::requestData)
        ;
        recycler.onRefresh();

    }

    private void requestData(int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, "" + page)
                .doRequest("admin/userPurchase/listMatch", new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> purchaseList) {
                        recycler.getAdapter().addData(purchaseList);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycler.selfRefresh(false);
                    }
                });

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, MarchingPurchaseActivity.class));
    }

    @Override
    public String setTitle() {
        return "匹配求购";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == ConstantState.CANCLE_SUCCEED) {
            recycler.onRefresh();
        }
    }
}
