package com.hldj.hmyg.Ui.friend;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.CenterActivity;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.Ui.friend.child.SearchActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * FinalActivity 来进行    数据绑定
 */

public class FriendCycleActivity extends BaseMVPActivity implements View.OnClickListener {

    private static final String TAG = "FriendCycleActivity";

    @ViewInject(id = R.id.core_rv_c)
    CoreRecyclerView mRecyclerView;

    @ViewInject(id = R.id.toolbar_right_icon, click = "onClick")
    ImageView toolbar_right_icon;

    /*左边返回键*/
    @ViewInject(id = R.id.toolbar_left_icon, click = "onClick")
    ImageView toolbar_left_icon;

    /*中间2个按钮*/
    @ViewInject(id = R.id.rb_title_left, click = "onClick")
    RadioButton rb_title_left;
    @ViewInject(id = R.id.rb_title_center)
    RadioButton rb_title_center;
    @ViewInject(id = R.id.rb_title_right, click = "onClick")
    RadioButton rb_title_right;


    @Override
    public void onClick(View v) {

        ToastUtil.showLongToast(v.getId() + "");
        switch (v.getId()) {
            /*搜索*/
            case R.id.toolbar_left_icon:
                SearchActivity.start(mActivity);
                break;
            /*选择发布类型*/
            case R.id.toolbar_right_icon:

                CommonDialogFragment1.newInstance(new CommonDialogFragment1.OnCallDialog() {
                    @Override
                    public Dialog getDialog(Context context) {
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.item_friend_type);
                        dialog.findViewById(R.id.iv_left).setOnClickListener(v -> {
                            ToastUtil.showLongToast("left");
                            dialog.dismiss();
                            PublishActivity.start(mActivity, PublishActivity.PUBLISH);

                        });
                        dialog.findViewById(R.id.iv_right).setOnClickListener(v -> {
                            ToastUtil.showLongToast("right");
                            dialog.dismiss();
                            PublishActivity.start(mActivity, PublishActivity.PURCHASE);
                        });

                        return dialog;
                    }
                }, true).show(getSupportFragmentManager(), TAG);
                break;
        }

    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_friend_cycle;
    }

    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }

        rb_title_left.setText("供应");
        rb_title_center.setVisibility(View.GONE);
        rb_title_right.setText("采购");

        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_icon.setImageResource(R.mipmap.friend_publish_edit);
        toolbar_left_icon.setImageResource(R.mipmap.friend_search);
//        toolbar_right_text.setText("哈哈");
//        toolbar_right_text.setVisibility(View.GONE);

        Toast.makeText(mActivity, "" + mRecyclerView, Toast.LENGTH_SHORT).show();


        mRecyclerView.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_friend_cicle) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                Log.i(TAG, "convert: " + item);

                //头像点击
                helper.addOnClickListener(R.id.head, v -> {
                    ToastUtil.showLongToast("点击头像--->跳转个人商店");
                    CenterActivity.start(mActivity, TAG);
                });
                View.OnClickListener clickListener = v ->
                {
                    ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
                    DetailActivity.start(mActivity);
                };
                helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题
                helper.addOnClickListener(R.id.time_city, clickListener);//时间和  发布地址
                helper.addOnClickListener(R.id.descript, clickListener);//描述
                helper.addOnClickListener(R.id.imageView8, v -> ToastUtil.showLongToast("点击图片--->跳转图片浏览器"));//描述
                helper.addOnClickListener(R.id.receive, v -> ToastUtil.showLongToast("点击评论--->显示回复窗口"));//描述


                helper.addOnClickListener(R.id.first, v -> ToastUtil.showLongToast("点击第一个"));//按钮一 点赞
                helper.addOnClickListener(R.id.second, v -> ToastUtil.showLongToast("点击第2个"));//按钮2 评论
                helper.addOnClickListener(R.id.third, v -> ToastUtil.showLongToast("点击第3个")); //按钮3 收藏
                helper.addOnClickListener(R.id.fourth, v -> {

                    ToastUtil.showLongToast("点击第4个");
                    new CommonPopupWindow.Builder(mActivity)
                            .setWidthDp(100)
                            .setHeightDp(100)
                            .setOutsideTouchable(true)
                            .bindLayoutId(R.layout.popuplayout)
                            .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
                                @Override
                                public void covertView(View viewRoot) {
                                }
                            })
                            .build()
                            .showAsDropDown(helper.getView(R.id.fourth));
//                            .showUp2(helper.getView(R.id.fourth));
                    ;

                });//按钮4 电话


            }
        }).openRefresh().openLoadMore(0, new CoreRecyclerView.addDataListener() {
            @Override
            public void addData(int page) {
                ToastUtil.showLongToast("current page " + page);
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                mRecyclerView.selfRefresh(false);
                                List<String> lists = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    lists.add(i + "数据");
                                }
                                mRecyclerView.getAdapter().addData(lists);
                            }
                        });


            }
        });

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add(i + "数据");
        }

        mRecyclerView.getAdapter().addData(lists);

    }


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }


}
