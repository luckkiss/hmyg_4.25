package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
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
import com.hy.utils.ToastUtil;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

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

    private void initTop(Moments moments) {
        finalBitmap = FinalBitmap.create(mActivity);
        tablayout.getTabAt(0).setText("点赞 (" + moments.thumbUpCount + ")");
        first.setText("点赞 " + moments.thumbUpCount);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast("点击第一个");
                new BasePresenter()
                        .putParams("momentsId", moments.id)
                        .doRequest("admin/momentsThumbUp/thumbUpDown", true, new HandlerAjaxCallBack() {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                ToastUtil.showLongToast(gsonBean.msg);
                                int current = !first.isSelected() ? (moments.thumbUpCount + 1) : (moments.thumbUpCount);
                                first.setText("点赞 " + current + "");
                                first.setSelected(first.isSelected());
                            }
                        });
            }
        });

        second.setOnClickListener(v -> {
            EditDialog.replyListener = new EditDialog.OnReplyListener() {
                @Override
                public void OnReply(String reply) {

                    ToastUtil.showLongToast("发表评论：\n" + reply);
                    new BasePresenter()
                            .putParams("momentsId", moments.id)
                            .putParams("reply", reply)
                            .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack() {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    MomentsReply momentsReply = new MomentsReply();
                                    momentsReply.reply = reply;
                                    moments.itemListJson.add(0, momentsReply);
                                    mCoreRecyclerView.onRefresh();
//                                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                    globBaseAdapter.notifyDataSetChanged();
                                }
                            });
//                                                list.add(s);
//                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                                globBaseAdapter.notifyDataSetChanged();

                }
            };
            EditDialog.instance("回复二傻：").show(mActivity.getSupportFragmentManager(), TAG);


        });


        title.setText(moments.ownerUserJson.displayName);
//        descript.setMaxLines(Integer.MAX_VALUE);
        descript.setText(moments.content + "香樟价格实惠质量香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，又好，可供应5000株香樟价格实惠质量又好，可供应5000株香樟价又好，可供应5000株香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，可供应5000株香樟价格实惠质量又好，可供应5000株可供应5000株.");
        time_city.setText(moments.createDate + "  " + moments.ciCity.fullName);
        finalBitmap.display(head, moments.ownerUserJson.headImage);
        imageView7.setImageResource(moments.momentsType.equals(MomentsType.purchase.getEnumValue()) ? R.mipmap.purchase : R.mipmap.publish);
        third.setOnClickListener(v -> FlowerDetailActivity.CallPhone(moments.ownerUserJson.publicPhone, mActivity));
        fourth.setOnClickListener(v ->
                {
                    ToastUtil.showLongToast("点击第4个");
                    new CommonPopupWindow.Builder(mActivity)
                            .setWidthDp(100)
                            .setHeightDp(100)
                            .setOutsideTouchable(true)
                            .bindLayoutId(R.layout.friend_more)
                            .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
                                @Override
                                public void covertView(View viewRoot) {

                                }
                            })
                            .build()
//                            .showAsDropDown(helper.getView(R.id.fourth));
                            .showUp2(fourth);
                    ToastUtil.showLongToast("更多");
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

        tv_activity_purchase_back.setOnClickListener(v -> finish());

        mCoreRecyclerView.init(new BaseQuickAdapter<Object, BaseViewHolder>(android.R.layout.simple_list_item_1) {

            @Override
            protected void convert(BaseViewHolder helper, Object item) {
                if (item instanceof MomentsReply) {
                    helper.setText(android.R.id.text1, ((MomentsReply) item).reply);
                } else if (item instanceof MomentsThumbUp) {
                    helper.setText(android.R.id.text1, ((MomentsThumbUp) item).ownerUserJson.publicName);
                }


            }
        });


        Log.i(TAG, "开始请求");
        new BasePresenter()
                .putParams("id", getMomentId())
                .doRequest("admin/moments/detail", true, new HandlerAjaxCallBack_test<SimpleGsonBean_test<Moments>>() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean_test<Moments> result) {
                        Log.i(TAG, "onRealSuccess: " + result);
                        //此处处理数据列表


                        initTop(result.data.moments);


//                        List<String> stringList = new ArrayList<>();
//                        for (int i = 0; i < 10; i++) {
//                            stringList.add("数据" + i);
//                        }
                        mCoreRecyclerView.getAdapter().addData(result.data.moments.thumbUpListJson);


                        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                ToastUtil.showLongToast(tab.getText().toString());
                                mCoreRecyclerView.getAdapter().setDatasState(100);
                                if (tab.getPosition() == 0) {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.thumbUpListJson);
                                } else {
                                    mCoreRecyclerView.getAdapter().addData(result.data.moments.itemListJson);
                                }


                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                                ToastUtil.showLongToast("onTabUnselected");
                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {
                                ToastUtil.showLongToast("onTabReselected");
                            }
                        });

//                        tab1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastUtil.showLongToast("1");
//                            }
//                        });
//
//                        tab2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastUtil.showLongToast("2");
//                            }
//                        });

                    }


//                    @Override
//                    public void onRealSuccess(Moments result) {
//                        ToastUtil.showLongToast(result.toString());
//                        Log.i(TAG, "onRealSuccess: "+result.id);
//                    }

                });
        Log.i(TAG, "结束请求");


//          ToastUtil.showLongToast(result.msg);
//        Log.i(TAG, "onRealSuccess: 请求成功" + result.msg);
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
}
