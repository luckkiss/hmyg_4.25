package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.MaterialDialog;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.presenter.FriendPresenter;
import com.hldj.hmyg.Ui.friend.widget.EditDialog;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.base.rxbus.event.EventThread;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.example.weixin_friendcircle.Util.dip2px;
import static com.hldj.hmyg.Ui.friend.child.FriendBaseFragment.sendPush;
import static com.hldj.hmyg.base.rxbus.RxBus.TAG_UPDATE;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 苗友圈详情界面
 */

public class DetailActivity extends BaseMVPActivity {

    private static final String TAG = "DetailActivity";

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView mCoreRecyclerView;

    @ViewInject(id = R.id.tv_activity_purchase_back)
    TextView tv_activity_purchase_back;
    @ViewInject(id = R.id.tab1)
    TabItem tab1;
    @ViewInject(id = R.id.tab2)
    TabItem tab2;
    @ViewInject(id = R.id.tablayout)
    TabLayout tablayout;

    @ViewInject(id = R.id.first)
    TextView first;
    @ViewInject(id = R.id.second)
    TextView second;
    @ViewInject(id = R.id.third)
    TextView third;
    @ViewInject(id = R.id.fourth)
    TextView fourth;

    @ViewInject(id = R.id.title)
    TextView title;
    @ViewInject(id = R.id.descript)
    TextView descript;

    @ViewInject(id = R.id.time_city)
    TextView time_city;

    @ViewInject(id = R.id.head)
    ImageView head;

    @ViewInject(id = R.id.imageView7)
    ImageView imageView7;


    @ViewInject(id = R.id.imageView8)
    MeasureGridView gridView;

    FinalBitmap finalBitmap;
    CommonPopupWindow popupWindow1;
    private Moments mMoments;

    private void initTop(Moments moments) {
        if (moments.thumbUpListJson == null) {
            moments.thumbUpListJson = new ArrayList<>();
        }

        tablayout.getTabAt(1).setText("点赞 (" + moments.thumbUpCount + ")");
        first.setText("点赞 " + moments.thumbUpCount);
        first.setSelected(moments.isFavour);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未登录。跳转登录界面
                if (!commitLogin()) return;
//                ToastUtil.showLongToast("点击第一个");
                // {"code":"1","msg":"操作成功","data":{"thumbUpCount":1,"isThumUp":false},"version":"tomcat7.0.53"}
                new BasePresenter()
                        .putParams("momentsId", moments.id)
                        .doRequest("admin/momentsThumbUp/thumbUpDown", true, new HandlerAjaxCallBack(mActivity) {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                setResult(ConstantState.REFRESH);
                                moments.thumbUpCount = gsonBean.getData().thumbUpCount;
                                moments.isFavour = gsonBean.getData().isThumUp;
//                                moments.i = MyApplication.Userinfo.getString("id", "");
                                ToastUtil.showLongToast(gsonBean.msg);

                                first.setText("点赞 " + moments.thumbUpCount + "");
                                first.setSelected(moments.isFavour);
                                tablayout.getTabAt(1).setText("点赞 (" + moments.thumbUpCount + ")");
                                if (moments.isFavour) {
                                    //add
                                    MomentsThumbUp up = new MomentsThumbUp();
                                    up.attrData.displayName = gsonBean.getData().displayName;
                                    up.attrData.headImage = gsonBean.getData().headImage;
                                    up.ownerId = MyApplication.Userinfo.getString("id", "");
                                    // helper.setText(android.R.id.text1, ((MomentsThumbUp) item).attrData.displayName);
                                    moments.thumbUpListJson.add(up);
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.thumbUpListJson);
                                    tablayout.getTabAt(1).select();

//                                    mCoreRecyclerView.onRefresh();

                                } else {
                                    //remove
                                    for (int i = 0; i < moments.thumbUpListJson.size(); i++) {
                                        if (moments.thumbUpListJson.get(i).attrData.displayName.equals(gsonBean.getData().displayName)) {
                                            moments.thumbUpListJson.remove(i);
                                        }
                                    }
                                    tablayout.getTabAt(1).select();
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.thumbUpListJson);
//                                    mCoreRecyclerView.onRefresh();
                                }

                                RxBus.getInstance().post(TAG_UPDATE, moments);
                                sendPush(moments);

                            }
                        });
            }
        });

        tablayout.getTabAt(0).setText("评论 (" + moments.replyCount + ")");

        reflex(tablayout);
        second.setOnClickListener(v -> {
            //未登录。跳转登录界面
            if (!commitLogin()) return;

            EditDialog.replyListener = new EditDialog.OnReplyListener() {
                @Override
                public void OnReply(String reply) {

//                    ToastUtil.showLongToast("发表评论：\n" + reply);
                    new BasePresenter()
                            .putParams("momentsId", moments.id)
                            .putParams("reply", reply)
                            .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack(mActivity) {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    setResult(ConstantState.REFRESH);
                                    ToastUtil.showLongToast(gsonBean.msg);
                                    MomentsReply momentsReply = new MomentsReply();
                                    momentsReply = gsonBean.getData().momentsReply;
                                    if (moments.itemListJson != null)
                                        moments.itemListJson.add(momentsReply);
                                    else {
                                        moments.itemListJson = new ArrayList<MomentsReply>();
                                        moments.itemListJson.add(momentsReply);
                                    }

                                    tablayout.getTabAt(0).select();
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.itemListJson);
                                    tablayout.getTabAt(0).setText("评论 (" + moments.itemListJson.size() + ")");
                                    moments.replyCount = moments.itemListJson.size();
                                    RxBus.getInstance().post(RxBus.TAG_UPDATE, moments);
                                    sendPush(moments);
//                                    mCoreRecyclerView.getRecyclerView().scrollToPosition(mCoreRecyclerView.getAdapter().getItemCount()-1);
//                                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                    globBaseAdapter.notifyDataSetChanged();
                                }
                            });
//                                                list.add(s);
//                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                                globBaseAdapter.notifyDataSetChanged();

                }
            };
            EditDialog.instance("评论: " + moments.attrData.displayName).show(mActivity.getSupportFragmentManager(), TAG);


        });


//        descript.setMaxLines(Integer.MAX_VALUE);
        descript.setText(moments.content);
        time_city.setText(moments.timeStampStr + "  " + moments.ciCity.fullName);
        if (moments.attrData != null) {
            title.setText(moments.attrData.displayName);
//          finalBitmap.display(head, moments.attrData.headImage);
//            if (!TextUtils.isEmpty(moments.attrData.headImage)) {
            ImageLoader.getInstance().displayImage(moments.attrData.headImage, head);
            head.setOnClickListener(v -> {
                //未登录。跳转登录界面
//                if (!commitLogin()) return;
                CenterActivity.start(mActivity, moments.ownerId);
            });
//            }
            third.setOnClickListener(v -> {
                //未登录。跳转登录界面
//                if (!commitLogin()) return;
                if (TextUtils.isEmpty(moments.attrData.displayPhone)) {
                    ToastUtil.showLongToast("未留电话号码~_~");
                } else {
                    FriendPresenter.postWhoPhone(moments.id, moments.attrData.displayPhone, ConstantState.TYPE_OWNER);
                    FlowerDetailActivity.CallPhone(moments.attrData.displayPhone, mActivity);
                }
            });
        }
        imageView7.setImageResource(moments.momentsType.equals(MomentsType.purchase.getEnumValue()) ? R.mipmap.purchase : R.mipmap.publish);
        fourth.setOnClickListener(v ->
                {
                    //未登录。跳转登录界面
                    if (!commitLogin()) return;
//                    ToastUtil.showLongToast("点击第4个");
                    popupWindow1 = FriendPresenter.createMorePop(moments, (BaseMVPActivity) mActivity, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
//                            .showAsDropDown(helper.getView(R.id.fourth));
                    popupWindow1.showUp2(fourth);

//                    ToastUtil.showLongToast("点击第4个");
//                    new CommonPopupWindow.Builder(mActivity)
//                            .setWidthDp(100)
//                            .setHeightDp(100)
//                            .setOutsideTouchable(true)
//                            .bindLayoutId(R.layout.friend_more)
//                            .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
//                                @Override
//                                public void covertView(View viewRoot) {
//
//                                }
//                            })
//                            .build()
////                          .showAsDropDown(helper.getView(R.id.fourth));
//                            .showUp2(fourth);
//                    ToastUtil.showLongToast("更多");
                }
        );

        gridView.setImageNumColumns(3);
        gridView.setHorizontalSpacing(3);
        gridView.setVerticalSpacing(0);

        gridView.init(mActivity, PurchaseDetailActivity.getPicList(moments.imagesJson), (ViewGroup) gridView.getParent(), null);
        gridView.setOnViewImagesListener((mContext, pos, pics) -> {
            GalleryImageActivity.startGalleryImageActivity(
                    mContext, pos, PurchaseDetailActivity.getPicListOriginal(moments.imagesJson));
        });

        gridView.getAdapter().closeAll(true);
        gridView.getAdapter().notifyDataSetChanged();


    }


    public int bindLayoutID() {
        return R.layout.activity_friend_detail;
    }


    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }


        finalBitmap = FinalBitmap.create(mActivity);
        finalBitmap.configLoadfailImage(R.drawable.no_image_show);
        finalBitmap.configLoadingImage(R.drawable.no_image_show);
        tv_activity_purchase_back.setOnClickListener(v -> finish());

        mCoreRecyclerView.init(new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.item_list_simple_with_head) {

            @Override
            protected void convert(BaseViewHolder helper, Object item) {
                if (item instanceof MomentsReply) {
                    ImageView head = helper.getView(R.id.head);
                    head.setVisibility(View.GONE);
                    MomentsReply s = ((MomentsReply) item);
//                    helper.setText(android.R.id.text1, ((MomentsReply) item).reply);
                    if (s.attrData == null || s.attrData.fromDisplayName == null) {
                        return;
                    }
//                        textView.setText(s.reply);
                    SpannableStringBuilder result;
                    String from = "";
                    from = s.attrData.fromDisplayName;
                    if (s.attrData.toDisplayName == null) {
                        //评论
                        result = filterColor(from + ": " + s.reply, from, R.color.main_color);
                    } else {
                        String to = s.attrData.toDisplayName;
                        SpannableStringBuilder str = filterColor(from, from, R.color.main_color);
                        //回复
                        result = str.append(filterColor("回复" + to + ": " + s.reply, to, R.color.main_color));
                    }
                    helper.setText(R.id.content, result);
                    helper.addOnClickListener(R.id.content, v -> {
                        //未登录。跳转登录界面
                        if (!commitLogin()) return;
                        if (isSelf(mCoreRecyclerView, helper, mMoments, tablayout, s, mActivity))
                            return;
                        EditDialog.replyListener = reply -> {
//                      ToastUtil.showLongToast("发表评论：\n" + reply);
                            new BasePresenter()
                                    .putParams("momentsId", mMoments.id)
                                    .putParams("reply", reply)
                                    .putParams("toId", s.fromId)
                                    .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack(mActivity) {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                                            setResult(ConstantState.REFRESH);
                                            MomentsReply momentsReply = new MomentsReply();
                                            momentsReply = gsonBean.getData().momentsReply;
                                            if (mMoments.itemListJson != null)
                                                mMoments.itemListJson.add(momentsReply);
                                            tablayout.getTabAt(1).select();
                                            mCoreRecyclerView.getAdapter().setDatasState(100);
                                            mCoreRecyclerView.getAdapter().addData(mMoments.itemListJson);
                                            tablayout.getTabAt(1).setText("评论 (" + mMoments.itemListJson.size() + ")");
                                            mMoments.replyCount = mMoments.itemListJson.size();
                                            ToastUtil.showLongToast(gsonBean.msg);

                                            RxBus.getInstance().post(RxBus.TAG_UPDATE, mMoments);
                                            sendPush(mMoments);
//                                    mCoreRecyclerView.getRecyclerView().scrollToPosition(mCoreRecyclerView.getAdapter().getItemCount()-1);
//                                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                    globBaseAdapter.notifyDataSetChanged();
                                        }
                                    });
//                                                list.add(s);
//                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                                globBaseAdapter.notifyDataSetChanged();

                        };
                        EditDialog.instance("回复: " + s.attrData.fromDisplayName).show(mActivity.getSupportFragmentManager(), TAG);
                    });

                } else if (item instanceof MomentsThumbUp) {
                    helper.setText(R.id.content, ((MomentsThumbUp) item).attrData.displayName);

                    ImageView head = helper.getView(R.id.head);
                    head.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(((MomentsThumbUp) item).attrData.headImage, head);
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //未登录。跳转登录界面
//                            if (!commitLogin()) return;
                            CenterActivity.start(mActivity, ((MomentsThumbUp) item).ownerId);
                        }
                    };
                    helper.addOnClickListener(R.id.content, onClickListener);
                    head.setOnClickListener(onClickListener);
                }


            }
        });


        requestData();


    }


    /**
     * 网络请求，本界面数据
     */
    private void requestData() {
        Log.i(TAG, "开始请求");
        new BasePresenter()
                .putParams("id", getMomentId())
                .doRequest("moments/detail", true, new HandlerAjaxCallBack_test<SimpleGsonBean_test<Moments>>(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean_test<Moments> result) {
//                      setResult(ConstantState.REFRESH);
                        Log.i(TAG, "onRealSuccess: " + result);
                        mMoments = result.data.moments;
                        //此处处理数据列表
                        initTop(result.data.moments);
                        mCoreRecyclerView.getAdapter().setDatasState(100);
                        mCoreRecyclerView.getAdapter().addData(result.data.moments.itemListJson);
                        tablayout.addOnTabSelectedListener(new CenterActivity.MyOnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                super.onTabSelected(tab);
//                                ToastUtil.showLongToast(tab.getText().toString());
                                mCoreRecyclerView.getAdapter().setDatasState(100);
                                if (tab.getPosition() == 1) {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.thumbUpListJson);
                                } else {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.itemListJson);
                                }
                            }
                        });
                        if (tablayout.getSelectedTabPosition() == 1) {
                            mCoreRecyclerView.getAdapter().setDatasState(100);
                            mCoreRecyclerView.getAdapter().addData(mMoments.thumbUpListJson);
                        }
                    }

                });
        Log.i(TAG, "结束请求");
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


    public String getMomentId() {
        Log.i(TAG, "帖子id: \n" + getIntent().getExtras().getString(TAG));
        return getIntent().getExtras().getString(TAG);
    }

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(TAG, id);
        activity.startActivityForResult(intent, 110);
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TAG, id);
        context.startActivity(intent);
    }

    public static void start(Fragment fragment, String id) {
        Intent intent = new Intent(fragment.getActivity(), DetailActivity.class);
        intent.putExtra(TAG, id);
        fragment.startActivityForResult(intent, 110);
    }

    @Override
    public String setTitle() {
        return "详情";
    }


    @Override
    protected void onResume() {
        super.onResume();
        D.w("DetailActivity  取消注册 RxBus");
        RxBus.getInstance().unRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        D.w("DetailActivity  订阅 RxBus");
        RxBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        D.w("DetailActivity  取消注册 RxBus");
        RxBus.getInstance().unRegister(this);
    }

    //订阅  更新
    @Keep
    @Subscribe(tag = TAG_UPDATE)
    public void postUpdata(Moments momentsNew) {
        if (momentsNew == null) {
            Log.i(TAG, "DetailActivity  postUpdata: momentsNews is null");
            return;
        }
        Log.i(TAG, "DetailActivity  postUpdata: momentsNews is no null \n 立刻刷新当前 start");
        try {

            if (momentsNew.thumbUpListJson != mMoments.thumbUpListJson) {
                //notify thumbUpListJson
                mMoments = momentsNew;
//                mMoments.thumbUpListJson = momentsNew.thumbUpListJson;
                mCoreRecyclerView.getAdapter().setDatasState(100);
                mCoreRecyclerView.getAdapter().addData(mMoments.thumbUpListJson);
                tablayout.getTabAt(0).select();
                tablayout.getTabAt(0).setText("评论 (" + momentsNew.replyCount + ")");

                D.i("========刷新了=======点赞列表===notify thumbUpListJson===");
            }
            if (momentsNew.itemListJson != mMoments.itemListJson) {
                //notify itemListJson
                mMoments = momentsNew;
                mMoments.itemListJson = momentsNew.itemListJson;
                mCoreRecyclerView.getAdapter().setDatasState(100);
                mCoreRecyclerView.getAdapter().addData(mMoments.itemListJson);
                tablayout.getTabAt(1).select();
                first.setText("点赞 " + momentsNew.thumbUpCount + "");

                D.i("========刷新了=======回复列表=====notify itemListJson=");
            }
        } catch (Exception e) {
            Log.w(TAG, "postUpdata: 刷新失败" + e.getMessage());
            e.printStackTrace();
        }
        Log.i(TAG, "DetailActivity  postUpdata: momentsNews is no null \n 立刻刷新当前 end");
    }

    //订阅  删除
    @Keep
    @Subscribe(tag = RxBus.TAG_DELETE)
    public void postDelete(Moments momentsNew) {
        D.w("======postDelete======" + momentsNew);
        if (momentsNew == null) return;
        D.w("======postDelete======关闭当前页面");
        if (mMoments.id.equals(momentsNew.id)) {
            finish();
        }
    }


    //订阅
    @Keep
    @Subscribe(tag = 5, thread = EventThread.MAIN_THREAD)
    private void dataBinding11(Eactivity3_0.OnlineEvent event) {
        D.e("===Detail=刷新==Rx====dataBinding11===" + event.toString());
        Observable.just(event)
                .filter(event1 -> event.isOnline)
                .map((Function<Eactivity3_0.OnlineEvent, Object>) event12 -> event12.isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(500, TimeUnit.MILLISECONDS)
                .subscribe((b) -> {
                    requestData();
                });


        D.i("========详情界面====DetailActivity========LOGIN_SUCCEED====登录成功，执行刷新");
        D.i("========详情界面====dataBinding11========LOGIN_SUCCEED====登录成功，执行刷新");
        D.i("=======详情界面=====dataBinding11========LOGIN_SUCCEED====登录成功，执行刷新");
        D.i("========详情界面====dataBinding11========LOGIN_SUCCEED====登录成功，执行刷新");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.LOGIN_SUCCEED) {
//            requestData();
            D.i("============onActivityResult========LOGIN_SUCCEED====登录成功，执行刷新");
            D.i("============onActivityResult========LOGIN_SUCCEED====登录成功，执行刷新");
            D.i("============onActivityResult========LOGIN_SUCCEED====登录成功，执行刷新");
            D.i("============onActivityResult========LOGIN_SUCCEED====登录成功，执行刷新");
        }
    }

    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width + dp10;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 判断是否自己 的评论
     *
     * @param
     * @param item
     * @param
     * @param
     * @return
     */
    public boolean isSelf(CoreRecyclerView mCoreRecyclerView, BaseViewHolder helper, final Moments moments, TabLayout tablayout, MomentsReply item, NeedSwipeBackActivity m) {
        boolean isSelf = item.isOwner;
        if (isSelf) {
            final MaterialDialog dialog = new MaterialDialog(
                    m);
            dialog.title("提示").content("确认删除评论?")//
                    .btnText("取消", "确认")//
                    .show();
            dialog.setOnBtnClickL(() -> {
                dialog.dismiss();
//               ToastUtil.showLongToast("确认删除");
            }, () -> {
                dialog.dismiss();
//                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) myViewHolder.getAdapter();
                FriendPresenter.doDeleteReply(item.id, item.fromId, new HandlerAjaxCallBack((NeedSwipeBackActivity) m) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
//                        MomentsReply momentsReply = new MomentsReply();
//                        momentsReply = gsonBean.getData().momentsReply;
//                        if (mMoments.itemListJson != null)
//                            mMoments.itemListJson.add(momentsReply);

                        mMoments.itemListJson.remove(helper.getAdapterPosition());
                        mCoreRecyclerView.getAdapter().setDatasState(100);
                        mCoreRecyclerView.getAdapter().addData(mMoments.itemListJson);
//                        Moments moments = gsonBean.getData().moments;
                        tablayout.getTabAt(0).select();
//
                        mMoments.replyCount = mMoments.itemListJson.size();
//                        mCoreRecyclerView.getAdapter().addData(mMoments.itemListJson);
                        tablayout.getTabAt(0).setText("评论 (" + mMoments.itemListJson.size() + ")");

//                        ToastUtil.showLongToast(gsonBean.msg);
                        RxBus.getInstance().post(RxBus.TAG_UPDATE, moments);
                        sendPush(moments);
//                        mCoreRecyclerView.getAdapter().remove(helper.getAdapterPosition());
//                        globBaseAdapter.getDatas().remove(position);
//                        globBaseAdapter.notifyDataSetChanged();
                    }
                });
            });

            Log.i(TAG, "isSelf: \n" + "是自己");
            return true;
        } else {
            Log.i(TAG, "isSelf: \n" + "不是自己");
            return false;
        }

//        myItemClickLister_content.OnItemDel(position, data.get(position).get("id").toString(),);
    }

}
