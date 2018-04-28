package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 邀请好友  界面   分享链接邀请好友
 */

public class InviteFriendListActivity extends BaseMVPActivity {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    List<IntegralBean> integralBeens;

    OptionItemView top;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_invite_friend_list;
    }


    @Override
    public void initView() {

        FinalActivity.initInjectedView(this);


        View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.invite_friend_empty, null);

        emptyView.findViewById(R.id.tv_go_to_invite).setOnClickListener(v -> {
            ToastUtil.showLongToast("go to inviteing  11111");
        });

        recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_invite_friend_list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        })
                .openRefresh()
//                .openLoadMore(10, page -> {
//
//                })

                .closeDefaultEmptyView()
                .setEmptyView(emptyView);


//        recycle.closeRefresh();

//        Observable.just("hello - world")
//                .delay(3, TimeUnit.SECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//
//                    }
//                });


        new Handler().postDelayed(() -> {
//
//            recycle.post(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtil.showLongToast("加载成功3333....");
//


//            recycle.getAdapter().addData("aaa");


            ArrayList arrayList = new ArrayList<>();
            arrayList.add("aada");
            arrayList.add("aada");
            arrayList.add("aada");
            arrayList.add("aada");
            arrayList.add("aada");
            recycle.getAdapter().addData(arrayList);
//                }
//            });
        }, 3000);


//        getView(R.id.tv_go_to_invite).setOnClickListener(v -> {
//            InviteFriendActivity.start(mActivity);
//        });

    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, InviteFriendListActivity.class));
    }


    public void requestDatas(AjaxCallBack callBack) {
//        new BasePresenter()
//                .doRequest("userPoint/list", true, callBack);
        new BasePresenter()
                .doRequest("admin/userPoint/index", true, callBack);
    }


    @Override
    public String setTitle() {
        return "邀请好友记录";
    }
}
