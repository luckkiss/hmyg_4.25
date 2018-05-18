package com.hldj.hmyg.me.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseLazyFragment;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.me.AskToByActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserActivity;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserListActivity;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hy.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.List;

import static com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity.doConvert;


/**
 * 用户求购  列表  懒加载模型  试试水
 * https://www.cnblogs.com/dasusu/p/6745032.html  懒加载
 */

public class AskToByFragment extends BaseLazyFragment {

    private static final String TAG = "AskToByFragment";
    private CoreRecyclerView recycle;

    public static AskToByFragment newInstance(boolean isClose) {
        AskToByFragment askToByFragment = new AskToByFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isClose", isClose);
        askToByFragment.setArguments(bundle);
        return askToByFragment;
    }

    private boolean isClose() {
        Bundle bundle = getArguments();
//        if (bundle != null) {
//            return bundle.getBoolean("isClose");
//        } else {
//            return false;
//        }
        return bundle.getBoolean("isClose");
    }

    @Override
    protected void onFragmentVisibleChange(boolean b) {

        Log.i(TAG, "onFragmentVisibleChange: " + this);


        Log.i(TAG, "展示用户界面----   " + b);

    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.i(TAG, "onFragmentFirstVisible: ");
        Log.i(TAG, "首次加载....请求网络中.... ");
        showLoading();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                Log.i(TAG, "首次加载.成功.... ");
//
//                List<String> datas = new ArrayList<>();
//                datas.add("aaa");
//                datas.add("aaa");
//                datas.add("aaa");
//                datas.add("aaa");
//                datas.add("aaa");
//                datas.add("aaa");
//                coreRecyclerView.getAdapter().addData(datas);
//
//                hidenLoading();
//
//
//            }
//        }, 5000);

        recycle.onRefresh();


    }


    /*  test page gsonbean  format */
    public void requestData() {
        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();
        new BasePresenter()
                .putParams("isClose", isClose() + "")
                .doRequest("admin/userPurchase/listForMe", true, new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type, UserPurchase.class) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> purchaseList) {
                        Log.i(TAG, "onRealSuccess: " + purchaseList);
                        recycle.getAdapter().addData(purchaseList);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                        if (mActivity != null && mActivity instanceof AskToByActivity) {
                            ((AskToByActivity) mActivity).refreshCount();
                        }

                    }
                });
    }


    @Override
    protected void initView(View rootView) {
        Log.i(TAG, "initView: ");
    }


    @Override
    protected int bindLayoutID() {
        return 0;
    }


    @Override
    public View getContentView() {
        recycle = new CoreRecyclerView(mActivity);

        recycle.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_bg_ed));


        recycle.init(new BaseQuickAdapter<UserPurchase, BaseViewHolder>(R.layout.item_ask_to_by) {

            @Override
            protected void convert(BaseViewHolder helper, UserPurchase item) {
                doConvert(helper, item, mActivity);

                helper.setText(R.id.price, item.price);


//                ToastUtil.showShortToast("item.unreadQuoteCountJson = "+ item.unreadQuoteCountJson);

                if (item.quoteCountJson.equals("0")) {
                    helper
                            .setText(R.id.unread, "暂无报价")
                            .setTextColorRes(R.id.unread, R.color.text_colorccc)
                            .setVisible(R.id.unread, false)
                    ;
//                    ToastUtil.showShortToast("helper.getview = 1");
                } else if (item.unreadQuoteCountJson == 0) {
                    helper
                            .setText(R.id.unread, "全部已读")
                            .setTextColorRes(R.id.unread, R.color.text_colorccc)
                            .setVisible(R.id.unread, true)
                    ;
//                    ToastUtil.showShortToast("helper.getview = 2");
                } else {
                    helper
                            .setText(R.id.unread, item.unreadQuoteCountJson + "条未读")
                            .setTextColorRes(R.id.unread, R.color.price_orige)
                            .setVisible(R.id.unread, true)
                    ;
                    Log.i(TAG, "convert: " + item.unreadQuoteCountJson + "条未读");
//                    ToastUtil.showShortToast("helper.getview = 3");
                }


                helper.convertView.setOnClickListener(v -> {
                    PublishForUserListActivity.start2Activity(mActivity, item.id);
                });

                if (isClose()) {
                    helper
                            .setText(R.id.bottom_left, "重新求购")
                            .setText(R.id.bottom_right, "删除求购")
                    ;
                }

                helper.addOnClickListener(R.id.bottom_left, v -> {
                    if (isClose()) {
                        重新求购(item.id);
                    } else {
                        分享求购(item);
                    }

                });
                helper.addOnClickListener(R.id.bottom_right, v -> {
                    if (isClose()) {
                        showDialogRight(item.id, helper.getAdapterPosition(), "确定删除本条求购?");
                    } else {
                        showDialogRight(item.id, helper.getAdapterPosition(), "确定结束本条求购?");
                    }

                });
            }
        }).setDefaultEmptyView(true, "", v -> {
            ToastUtil.showLongToast("retry  bottom btn");
            PublishForUserActivity.start2Activity(mActivity);
        }).openLoadMore(100, page -> {
            requestData();
        }).openRefresh();

        recycle.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 20);

            }
        });

        return recycle;
    }

    private void 分享求购(UserPurchase userPurchase) {
        ComonShareDialogFragment.newInstance()
                .setShareBean(new ComonShareDialogFragment.ShareBean(String.format("求购：%s %s%s 电话:%s",
                        userPurchase.name,
                        userPurchase.count,
                        userPurchase.unitTypeName, MyApplication.getUserBean().phone),
                        "用苗地：" + userPurchase.cityName + " 规格要求：" + userPurchase.specText + " " + userPurchase.remarks,
                        "",
                        MyApplication.getUserBean().headImage,
                        Data.getShareMyPurchase(userPurchase.id, "admin/userPurchase/detail")))
                .show(mActivity.getSupportFragmentManager(), "分享求购");

        //admin/userPurchase/detail/"
        //http://192.168.1.252:8090/admin/userPurchase/detail/5f2d96a19f7540728ba764826c1a1625.html
        /**
         * 标题：求购：红花鸡蛋花 100株电话15900006666
         内容：用苗地：福建 厦门 规格要求：胸径10cm 假植苗或中上品质优先
         */
    }


    private void showDialogRight(String id, int pos, String title) {

        final MaterialDialog dialog = new MaterialDialog(mActivity);
        dialog.title("").content(title)//
                .btnText("取消", "确认")//
                .show();
        dialog.setOnBtnClickL(() -> {
            dialog.dismiss();

        }, () -> {
            dialog.dismiss();
            if (isClose()) {
                删除求购(id, pos);
            } else {
                结束求购(id, pos);
            }

        });

    }

    private void 结束求购(String id, int pos) {
        new BasePresenter()
                .putParams("id", id)
                .doRequest("admin/userPurchase/close", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(" 结束求购 成功");
                        deleteItem(id, pos);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.onRefresh();
                    }
                });
    }

    private void 删除求购(String id, int pos) {
        new BasePresenter()
                .putParams("id", id)
                .doRequest("admin/userPurchase/del", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(" 删除 成功");
                        deleteItem(id, pos);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.onRefresh();
                    }
                });
//        ToastUtil.showLongToast("删除求购");
    }


    //edit
    private void 重新求购(String id) {
//        new BasePresenter()
//                .putParams("id", id)
//                .doRequest("admin/userPurchase/edit")
//        ToastUtil.showLongToast("重新求购.....");


        PublishForUserActivity.start2Activity(mActivity, id);

//        new BasePresenter()
//                .putParams("id", id)
//                .doRequest("admin/userPurchase/edit", new HandlerAjaxCallBack() {
//                    @Override
//                    public void onRealSuccess(SimpleGsonBean gsonBean) {
//                        ToastUtil.showLongToast(" 重新求购 成功");
//
//
//                    }
//                });


    }


    private void deleteItem(String s, int pos) {
        recycle.getAdapter().remove(pos);
        recycle.getAdapter().notifyItemRemoved(pos);
    }


    @Override
    public void onResume() {
        super.onResume();
        recycle.onRefresh();
    }
}
