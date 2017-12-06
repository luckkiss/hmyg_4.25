package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.widget.EditDialog;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hy.utils.ToastUtil;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.weixin_friendcircle.Util.dip2px;

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
    private Moments moments;

    private void initTop(Moments moments) {
        if (moments.thumbUpListJson == null) {
            moments.thumbUpListJson = new ArrayList<>();
        }

        tablayout.getTabAt(0).setText("点赞 (" + moments.thumbUpCount + ")");
        first.setText("点赞 " + moments.thumbUpCount);
        first.setSelected(moments.isFavour);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                ToastUtil.showLongToast(gsonBean.msg);

                                first.setText("点赞 " + moments.thumbUpCount + "");
                                first.setSelected(moments.isFavour);
                                tablayout.getTabAt(0).setText("点赞 (" + moments.thumbUpCount + ")");
                                if (moments.isFavour) {
                                    //add
                                    MomentsThumbUp up = new MomentsThumbUp();
                                    up.attrData.displayName = gsonBean.getData().displayName;
                                    // helper.setText(android.R.id.text1, ((MomentsThumbUp) item).attrData.displayName);
                                    moments.thumbUpListJson.add(up);
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.thumbUpListJson);
                                    tablayout.getTabAt(0).select();

//                                    mCoreRecyclerView.onRefresh();

                                } else {
                                    //remove
                                    for (int i = 0; i < moments.thumbUpListJson.size(); i++) {
                                        if (moments.thumbUpListJson.get(i).attrData.displayName.equals(gsonBean.getData().displayName)) {
                                            moments.thumbUpListJson.remove(i);
                                        }
                                    }
                                    tablayout.getTabAt(0).select();
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.thumbUpListJson);
//                                    mCoreRecyclerView.onRefresh();
                                }


                            }
                        });
            }
        });

        tablayout.getTabAt(1).setText("评论 (" + moments.replyCount + ")");
        reflex(tablayout);
        second.setOnClickListener(v -> {
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
                                    tablayout.getTabAt(1).select();
                                    mCoreRecyclerView.getAdapter().setDatasState(100);
                                    mCoreRecyclerView.getAdapter().addData(moments.itemListJson);
                                    tablayout.getTabAt(1).setText("评论 (" + moments.itemListJson.size() + ")");
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
            finalBitmap.display(head, moments.attrData.headImage);
            third.setOnClickListener(v -> {
                if (TextUtils.isEmpty(moments.attrData.displayPhone)) {
                    ToastUtil.showLongToast("未留电话号码~_~");
                } else {
                    FlowerDetailActivity.CallPhone(moments.attrData.displayPhone, mActivity);
                }
            });
        }
        imageView7.setImageResource(moments.momentsType.equals(MomentsType.purchase.getEnumValue()) ? R.mipmap.purchase : R.mipmap.publish);
        fourth.setOnClickListener(v ->
                {
//                    ToastUtil.showLongToast("点击第4个");
                    if (popupWindow1 == null)
                        popupWindow1 = new CommonPopupWindow.Builder(mActivity)
                                .setWidthDp(115)
                                .setHeightDp(108)
                                .setOutsideTouchable(true)
                                .bindLayoutId(R.layout.friend_more)
                                .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
                                    @Override
                                    public void covertView(View viewRoot) {
                                        TextView tv1 = (TextView) viewRoot.findViewById(R.id.pup_subscriber);
                                        tv1.setText("加入收藏");
                                        tv1.setOnClickListener(v -> {
                                            popupWindow1.dismiss();
                                            ToastUtil.showLongToast("加入收藏" + moments.id);
                                        });
                                        tv1.setTextColor(getColorByRes(R.color.text_color111));
                                        TextView tv2 = (TextView) viewRoot.findViewById(R.id.pup_show_share);
                                        tv2.setTextColor(getColorByRes(R.color.text_color111));
                                        tv2.setText("进入店铺");
                                        tv2.setOnClickListener(v -> {
//                                            ToastUtil.showLongToast("进入店铺" + moments.attrData.storeId);
                                            StoreActivity_new.start2Activity(mActivity, moments.attrData.storeId);
                                            popupWindow1.dismiss();
                                        });
                                    }
                                })
                                .build();
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

        mCoreRecyclerView.init(new BaseQuickAdapter<Object, BaseViewHolder>(android.R.layout.simple_list_item_1) {

            @Override
            protected void convert(BaseViewHolder helper, Object item) {
                if (item instanceof MomentsReply) {
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
                    helper.setText(android.R.id.text1, result);
                    helper.addOnClickListener(android.R.id.text1, v -> {
                        EditDialog.replyListener = reply -> {
//                            ToastUtil.showLongToast("发表评论：\n" + reply);
                            new BasePresenter()
                                    .putParams("momentsId", moments.id)
                                    .putParams("reply", reply)
                                    .putParams("toId", s.fromId)
                                    .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack(mActivity) {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                                            setResult(ConstantState.REFRESH);
                                            MomentsReply momentsReply = new MomentsReply();
                                            momentsReply = gsonBean.getData().momentsReply;
                                            if (moments.itemListJson != null)
                                                moments.itemListJson.add(momentsReply);
                                            tablayout.getTabAt(1).select();
                                            mCoreRecyclerView.getAdapter().setDatasState(100);
                                            mCoreRecyclerView.getAdapter().addData(moments.itemListJson);
                                            tablayout.getTabAt(1).setText("评论 (" + moments.itemListJson.size() + ")");
                                            ToastUtil.showLongToast(gsonBean.msg);

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
                    helper.setText(android.R.id.text1, ((MomentsThumbUp) item).attrData.displayName);
                    helper.addOnClickListener(android.R.id.text1, null);
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
                .doRequest("admin/moments/detail", true, new HandlerAjaxCallBack_test<SimpleGsonBean_test<Moments>>(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean_test<Moments> result) {
                        setResult(ConstantState.REFRESH);
                        Log.i(TAG, "onRealSuccess: " + result);
                        moments = result.data.moments;
                        //此处处理数据列表
                        initTop(result.data.moments);
                        mCoreRecyclerView.getAdapter().addData(result.data.moments.thumbUpListJson);
                        tablayout.addOnTabSelectedListener(new CenterActivity.MyOnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                super.onTabSelected(tab);
//                                ToastUtil.showLongToast(tab.getText().toString());
                                mCoreRecyclerView.getAdapter().setDatasState(100);
                                if (tab.getPosition() == 0) {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.thumbUpListJson);
                                } else {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.itemListJson);
                                }
                            }
                        });

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

    @Override
    public String setTitle() {
        return "供应详情";
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
}
