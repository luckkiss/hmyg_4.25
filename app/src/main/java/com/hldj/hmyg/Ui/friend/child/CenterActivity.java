package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Keep;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.MyCircleImageView;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.hldj.hmyg.R.id.recycle;
import static com.hldj.hmyg.base.rxbus.RxBus.TAG_UPDATE;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 我的苗友圈  我的苗木圈
 */

public class CenterActivity extends BaseMVPActivity {

    private static final String TAG = "CenterActivity";


    /*底部 未知选择[*/
    @ViewInject(id = R.id.location)
    OptionItemView location;


    @ViewInject(id = recycle)
    CoreRecyclerView mRecyclerView;

    @ViewInject(id = R.id.tablayout)
    TabLayout tablayout;//可切换的选项卡

    @ViewInject(id = R.id.toolbar_right_icon, click = "onClick")
    public ImageView toolbar_right_icon;//发布按钮

    public int bindLayoutID() {
        return R.layout.activity_friend_center;
    }

    public String currentType = MomentsType.supply.getEnumValue();//供应   -   求购   -- 收藏
    FinalBitmap finalBitmap;


    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }

        if (isSelf()) {
            toolbar_right_icon.setVisibility(View.VISIBLE);
            toolbar_right_icon.setImageResource(R.mipmap.friend_publish_edit);
            toolbar_right_icon.setOnClickListener(v -> {
                CommonDialogFragment1.newInstance(context -> {
                    Dialog dialog1 = new Dialog(context);
                    dialog1.setCanceledOnTouchOutside(true);
                    dialog1.setCancelable(true);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.item_friend_type);
                    dialog1.findViewById(R.id.iv_left).setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("left");
                        dialog1.dismiss();
                        PublishActivity.start(mActivity, PublishActivity.PUBLISH);
                    });
                    dialog1.findViewById(R.id.iv_right).setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("right");
                        dialog1.dismiss();
                        PublishActivity.start(mActivity, PublishActivity.PURCHASE);
                    });

                    return dialog1;
                }, true).show(getSupportFragmentManager(), TAG);
            });
        }


        finalBitmap = FinalBitmap.create(mActivity);
        finalBitmap.configLoadfailImage(R.drawable.no_image_show);
        finalBitmap.configLoadfailImage(R.drawable.no_image_show);


        tablayout.addOnTabSelectedListener(new MyOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(mActivity, "选项卡：" + tab.getPosition(), Toast.LENGTH_SHORT).show();
                if (tab.getPosition() == 0) {
                    currentType = MomentsType.supply.getEnumValue();//供应
                } else if (tab.getPosition() == 1) {
                    currentType = MomentsType.purchase.getEnumValue();// 求购
                } else if (tab.getPosition() == 2) {
                    currentType = MomentsType.collect.getEnumValue();//  -- 收藏
                } else {
                    ToastUtil.showLongToast("别闹");
                }
                mRecyclerView.onRefresh();
                mRecyclerView.getRecyclerView().scrollToPosition(0);

//                ToastUtil.showLongToast(currentType);
            }
        });
        tablayout.getTabAt(0).setText("供应");
        tablayout.getTabAt(1).setText("求购");
        tablayout.getTabAt(2).setText("收藏");
        requestCount(tablayout.getTabAt(0), tablayout.getTabAt(1), tablayout.getTabAt(2));


        check();


        mRecyclerView.init(new BaseQuickAdapter<Moments, BaseViewHolder>(R.layout.item_friend_cicle_simple_center) {
            @Override
            protected void convert(BaseViewHolder helper, Moments item) {


                View.OnClickListener clickListener = v ->
                {
//                  ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
                    DetailActivity.start(mActivity, item.id);
                };
                helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题
                helper.addOnClickListener(R.id.time_city, clickListener);//时间和  发布地址
                helper.addOnClickListener(R.id.descript, clickListener);//描述

                helper.setVisible(R.id.imageView7, false)
                        .setVisible(R.id.tv_right_top, isSelf())
                        .addOnClickListener(R.id.tv_right_top, v ->
                                {
//                                    ToastUtil.showLongToast("删除");
                                    doDelete(item, helper.getAdapterPosition());
                                }
                        );
                helper.setText(R.id.descript, item.content);//描述
                String time_city = FUtil.$_zero(item.timeStampStr);
                if (item.ciCity != null && item.ciCity.fullName != null) {
                    time_city += "   " + item.ciCity.fullName;
                }
                helper.setText(R.id.time_city, time_city);//时间戳_发布点

                Log.i(TAG, "convert: " + item);


//                if (item.attrData != null && !TextUtils.isEmpty(item.ownerUserJson.headImage)) {
//                    //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.ownerUserJson.headImage);
//                }

                helper.setVisible(R.id.imageView7, false);


                if (item.attrData != null) {
                    helper.setText(R.id.title, item.attrData.displayName);
                    //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.attrData.headImage);
                    MyCircleImageView circleImageView = helper.getView(R.id.head);
                    if (!TextUtils.isEmpty(item.attrData.headImage))
                        ImageLoader.getInstance().displayImage(item.attrData.headImage, circleImageView);
//                    circleImageView.setImageURL(item.attrData.headImage);
                }


                MeasureGridView gridView = helper.getView(R.id.imageView8);
                gridView.setImageNumColumns(3);
                gridView.setHorizontalSpacing(6);
                gridView.setVerticalSpacing(6);
                gridView.initFriend(mActivity, PurchaseDetailActivity.getPicList(item.imagesJson), (ViewGroup) gridView.getParent(), null);
                gridView.setOnViewImagesListener( (mContext, pos, pics) ->{
                    GalleryImageActivity.startGalleryImageActivity(
                            mContext, pos, PurchaseDetailActivity.getPicListOriginal(item.imagesJson));
                });
//                gridView.getAdapter().closeAll(true);
//                gridView.getAdapter().notifyDataSetChanged();

                helper.addOnClickListener(R.id.first, clickListener).setText(R.id.first, " " + item.thumbUpCount);//按钮一 点赞

                if (item.thumbUpListJson == null)
                    item.thumbUpListJson = new ArrayList<MomentsThumbUp>();
                helper.addOnClickListener(R.id.second, clickListener).setText(R.id.second, " " + item.replyCount);//按钮2 评论
            }
        }).openRefresh()
                .openLoadMore(10, page -> {
                    showLoadingCus("刷新数据");
                    requestDatas(page + "", currentType);
                })
                .setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mRecyclerView.onLoadMoreRequested();
                    }
                })
        ;
        mRecyclerView.onRefresh();
    }

    private void requestCount(TabLayout.Tab tabAt, TabLayout.Tab tabAt1, TabLayout.Tab tabAt2) {
        new BasePresenter()
                .putParams("userId", getUserId())
                .doRequest("moments/momentsCount", true, new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        tabAt.setText("供应 ( " + gsonBean.getData().supplyCount + " )");
                        tabAt1.setText("求购 ( " + gsonBean.getData().purchaseCount + " )");
                        tabAt2.setText("收藏 ( " + gsonBean.getData().collectCount + " )");
                    }
                });
    }

    private void doDelete(Moments moments, int pos) {
        String host;
        if (currentType.equals(MomentsType.collect.getEnumValue()))//enumValue  collect
        {
            host = "admin/collect/save";
        } else {
            host = "admin/moments/doDel";
        }
        Log.i(TAG, "doDelete: host\n" + host);
        new BasePresenter()
                .putParams("ids", moments.id)
                .putParams("id", moments.id)
                .putParams("sourceId", moments.id)
                .putParams("type", "moment")
                .doRequest(host, true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
                        if (gsonBean.isSucceed()) {
                            requestCount(tablayout.getTabAt(0), tablayout.getTabAt(1), tablayout.getTabAt(2));
//                            mRecyclerView.onRefresh();
//                          mRecyclerView.getAdapter().getData().remove(moments);
                            mRecyclerView.remove(pos);

                            /*非 搜藏的情况。发送 删除关播  不需要删除列表的 */
                            if (!currentType.equals(MomentsType.collect.getEnumValue()))//enumValue  collect
                                RxBus.getInstance().post(RxBus.TAG_DELETE, moments);


                        }
                    }
                });


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


//      .putParams("sourceId", id)
//                .putParams("type", "moment")
                /*
                 .putParams("sourceId", id)
                .putParams("type", "moment")
                .doRequest("admin/collect/listMoment", true, ajaxCallBack);
                 */

    public void requestDatas(String page, String type) {
        showLoading();
        String host = "admin/moments/list";
        if (type.equals(MomentsType.collect.getEnumValue())) {
            host = "admin/collect/listMoment";
        } else {
            host = "moments/list";
//            host = "admin/moments/list";
        }
        Log.i(TAG, "host url: \n" + host);
//        ToastUtil.showLongToast(host);
//      String host_collect = "admin/collect/listMoment";
        new BasePresenter()
//                .putParams("sourceId", "2876f7e0f51c4153aadc603b661fedfa")
                .putParams("pageSize", "10")
                .putParams("pageIndex", page)
                .putParams("momentsType", type)
                .putParams("type", "moment")
                .putParams("userId", getUserId())
                .putParams("ownerId", getUserId())
                .doRequest(host, true, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        hindLoading();

                        Log.i(TAG, "onSuccess: " + json);
                        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
                        }.getType();
                        SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);

                        if (bean_new.data == null || bean_new.data.page == null) {
                            ToastUtil.showLongToast("暂无数据~_~");
                            mRecyclerView.getAdapter().addData(null);
                            return;
                        }

                        mRecyclerView.getAdapter().addData(bean_new.data.page.data);

//                      ToastUtil.showLongToast(bean_new.data.page.total + "条数据");
                        mRecyclerView.selfRefresh(false);
                        hindLoading();

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        mRecyclerView.selfRefresh(false);
                        mRecyclerView.showLoadMoreFailedView();
                        hindLoading();
                    }
                });
    }


    // 确认是谁的， 如果是别人的。就把收藏隐藏起来
    private void check() {
        if (!isSelf()) {
//            ((ViewGroup) tablayout.getTabAt(2).getCustomView().getParent()).removeView(tablayout.getTabAt(2).getCustomView());
            tablayout.removeTabAt(2);
        }

    }

    public boolean isSelf() {
        return MyApplication.Userinfo.getString("id", "").equals(getUserId());
    }

    public String getUserId() {
        return getIntent().getStringExtra(TAG);
    }

    public static void start(Activity activity, String userId) {
        Intent intent = new Intent(activity, CenterActivity.class);
        intent.putExtra(TAG, userId);
        Log.i(TAG, "start1: " + userId);
        activity.startActivityForResult(intent, 110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.REFRESH) ;
//        mRecyclerView.onRefresh()
        else if (resultCode == ConstantState.PUBLISH_SUCCEED) {

//        } else if (resultCode == ConstantState.PUBLIC_SUCCEED) {
            //发布成功，当发布的时候刷新
            if (currentType.equals(MomentsType.supply.getEnumValue()))
                mRecyclerView.onRefresh();
            else //切换到个别列表
            {
                tablayout.getTabAt(0).select();
                currentType = MomentsType.supply.getEnumValue();
//                mRecyclerView.onRefresh();
            }

            D.e("currentType" + currentType);

        } else if (resultCode == ConstantState.PURCHASE_SUCCEED) {
            //求购成功，当求购的时候刷新
            if (currentType.equals(MomentsType.purchase.getEnumValue()))
                mRecyclerView.onRefresh();
            else {
                {
                    tablayout.getTabAt(1).select();
                    currentType = MomentsType.purchase.getEnumValue();
//                    mRecyclerView.onRefresh();
                }
            }
            D.e("currentType" + currentType);
        }

    }

    @Override
    public String setTitle() {
        return "苗木圈";
    }


    public static class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {


        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        D.w("CenterActivity  取消注册 RxBus");
        RxBus.getInstance().unRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        D.w("CenterActivity  订阅 RxBus");
        RxBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        D.w("CenterActivity  取消注册 RxBus");
        RxBus.getInstance().unRegister(this);
    }


    //订阅  更新
    @Keep
    @Subscribe(tag = TAG_UPDATE)
    public void postUpdata(Moments momentsNew) {
        if (momentsNew == null) {
            Log.i(TAG, "postUpdata: momentsNews is null");
            return;
        }

        D.e("thumbUpCount" + momentsNew.thumbUpCount);
        D.e("replyCount" + momentsNew.replyCount);

        Log.i(TAG, "postUpdata: momentsNews is no null \n 立刻刷新当前 start");
        Moments moments = null;
        for (int i = 0; i < mRecyclerView.getAdapter().getData().size(); i++) {
            moments = (Moments) mRecyclerView.getAdapter().getData().get(i);
            if (momentsNew.id.equals(moments.id)) {
                Observable.just(i)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer pos) throws Exception {
                                Log.i(TAG, "accept: 刷新位置 = " + pos);
                                mRecyclerView.getAdapter().getData().set(pos, momentsNew);
                                mRecyclerView.getAdapter().notifyItemChanged(pos, momentsNew);
//                                mRecyclerView.getAdapter().notifyItemChanged(pos);
                            }
                        });
                Log.i(TAG, "刷新成功");
                return;
            }
        }
        Log.i(TAG, "postUpdata: momentsNews is no null \n 立刻刷新当前 end");
    }

    //订阅  删除
    @Keep
    @Subscribe(tag = RxBus.TAG_DELETE)
    public void postDelete(Moments momentsNew) {
        if (momentsNew == null) {
            Log.i(TAG, "postUpdata: momentsNews is null");
            return;
        }
        Log.i(TAG, "postDelete: momentsNews is no null \n 立刻删除当前 start");
        Moments moments = null;
        for (int i = 0; i < mRecyclerView.getAdapter().getData().size(); i++) {
            moments = (Moments) mRecyclerView.getAdapter().getData().get(i);
            if (momentsNew.id.equals(moments.id)) {
                Observable.just(i)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pos -> {
                            Log.i(TAG, "accept: 刷新位置 = " + pos);
                            mRecyclerView.remove(pos);
//                                mRecyclerView.getAdapter().notifyItemChanged(pos);
                        });
                Log.i(TAG, "刷新成功");
                return;
            }
        }
        Log.i(TAG, "postDelete: momentsNews is no null \n 立刻删除当前 end");
    }

}
