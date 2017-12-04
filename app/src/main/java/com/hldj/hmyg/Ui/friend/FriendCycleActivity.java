package com.hldj.hmyg.Ui.friend;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.child.CenterActivity;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.Ui.friend.child.SearchActivity;
import com.hldj.hmyg.Ui.friend.widget.EditDialog;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;
import com.zzy.common.widget.MeasureGridView;
import com.zzy.common.widget.MeasureListView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.List;

import static com.hldj.hmyg.util.ConstantState.SEARCH_OK;

/**
 * FinalActivity 来进行    数据绑定
 */

public class FriendCycleActivity extends BaseMVPActivity implements View.OnClickListener {

    private static final String TAG = "FriendCycleActivity";

    public static Boolean notifier = Boolean.valueOf(false);

    /*列表 recycle*/
    @ViewInject(id = R.id.core_rv_c)
    CoreRecyclerView mRecyclerView;

    /*右上角  点击按钮。发布采购 发布供应*/
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


    FinalBitmap finalBitmap;

    @ViewInject(id = R.id.call_back)
    EditText editText;


    @Override
    public void onClick(View v) {

        ToastUtil.showLongToast(v.getId() + "");
        switch (v.getId()) {
            /*搜索*/
            case R.id.toolbar_left_icon:
                SearchActivity.start(mActivity, searchContent);
                break;

            case R.id.rb_title_left:
                ToastUtil.showLongToast("刷新供应");
                /*当前的展示类型*/
                currentType = MomentsType.supply.getEnumValue();
                mRecyclerView.onRefresh();
                break;
            case R.id.rb_title_right:
                /*当前的展示类型*/
                ToastUtil.showLongToast("刷新采购");
                currentType = MomentsType.purchase.getEnumValue();
                mRecyclerView.onRefresh();
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
        finalBitmap = FinalBitmap.create(mActivity);

        rb_title_left.setText("供应");
        rb_title_center.setVisibility(View.GONE);
        rb_title_right.setText("采购");

        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_icon.setImageResource(R.mipmap.friend_publish_edit);
        toolbar_left_icon.setImageResource(R.mipmap.friend_search);
//        toolbar_right_text.setText("哈哈");
//        toolbar_right_text.setVisibility(View.GONE);

        Toast.makeText(mActivity, "" + mRecyclerView, Toast.LENGTH_SHORT).show();


        mRecyclerView.init(new BaseQuickAdapter<Moments, BaseViewHolder>(R.layout.item_friend_cicle) {
            @Override
            protected void convert(BaseViewHolder helper, Moments item) {
                Log.i(TAG, "convert: " + item);
                //头像点击
                helper.addOnClickListener(R.id.head, v -> {
                    ToastUtil.showLongToast("点击头像--->跳转个人商店");
                    CenterActivity.start(mActivity, MyApplication.getUserBean().id);
                });

                if (item.ownerUserJson != null && !TextUtils.isEmpty(item.ownerUserJson.headImage)) {
                    //显示图片
                    finalBitmap.display(helper.getView(R.id.head), item.ownerUserJson.headImage);
                }


                if (currentType.equals(MomentsType.supply.getEnumValue())) {
                    helper.setImageResource(R.id.imageView7, R.mipmap.publish);
                } else if (currentType.equals(MomentsType.purchase.getEnumValue())) {
                    helper.setImageResource(R.id.imageView7, R.mipmap.purchase);
                } else {
                    helper.setVisible(R.id.imageView7, false);
                }

                View.OnClickListener clickListener = v ->
                {
                    ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
                    DetailActivity.start(mActivity, item.id);
                };
                helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题


                if (item.ownerUserJson != null)
                    BProduceAdapt.setPublishNameNoStart(helper.getView(R.id.title),
                            item.ownerUserJson.companyName,
                            item.ownerUserJson.publicName,
                            item.ownerUserJson.realName,
                            item.ownerUserJson.userName);

                helper.addOnClickListener(R.id.time_city, clickListener).setText(R.id.time_city, item.createDate + "  " + item.ciCity.fullName);//时间和  发布地址
                helper.addOnClickListener(R.id.descript, clickListener).setText(R.id.descript, item.content);//描述
//                helper.addOnClickListener(R.id.imageView8, v -> ToastUtil.showLongToast("点击图片--->跳转图片浏览器"));//描述
//                helper.addOnClickListener(R.id.receive, v -> ToastUtil.showLongToast("点击评论--->显示回复窗口"));//描述

                MeasureGridView gridView = helper.getView(R.id.imageView8);


                MeasureListView measureListView = helper.getView(R.id.receive);

//                List<MomentsReply> list = new ArrayList<MomentsReply>();
//                list.add("占楼");
//                list.add("占楼");
//                list.add("占楼");
//                list.add("占楼");
//                list.add("占楼");
                measureListView.setAdapter(new GlobBaseAdapter<MomentsReply>(mActivity, item.itemListJson, android.R.layout.simple_list_item_1) {
                    @Override
                    public void setConverView(ViewHolders myViewHolder, MomentsReply s, int position) {

                        if (s.attrData == null || s.attrData.fromDisplayName == null) {
                            return;
                        }
                        TextView textView = myViewHolder.getView(android.R.id.text1);
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

                        textView.setText(result);
                    }
                });


//                ArrayList<Pic> arrayList = new ArrayList<Pic>();

//                arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/40007/4901.jpg_wh300.jpg", 0));
//                arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/50045/5922.jpg_wh300.jpg", 1));
//                arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/00009/3523.jpg_wh300.jpg!/format/webp", 2));
//                arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/4625.jpg_wh300.jpg!/format/webp", 3));
//                arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/2066.jpg_wh300.jpg", 4));
                gridView.setImageNumColumns(3);
                gridView.setHorizontalSpacing(3);
                gridView.setVerticalSpacing(0);

                gridView.init(mActivity, PurchaseDetailActivity.getPicList(item.imagesJson), (ViewGroup) gridView.getParent(), null);
                gridView.getAdapter().closeAll(true);
                gridView.getAdapter().notifyDataSetChanged();

                helper.setSelected(R.id.first, item.isFavour);
                helper.addOnClickListener(R.id.first, v -> {
                    ToastUtil.showLongToast("点击第一个");
                    new BasePresenter()
                            .putParams("momentsId", item.id)
                            .doRequest("admin/momentsThumbUp/thumbUpDown", true, new HandlerAjaxCallBack() {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    item.isFavour = gsonBean.getData().isThumUp;
                                    item.thumbUpCount = gsonBean.getData().thumbUpCount;
                                    ToastUtil.showLongToast(gsonBean.msg);
//                                    int current = !helper.getView(R.id.first).isSelected() ? (item.thumbUpCount + 1) : (item.thumbUpCount);
                                    helper.setText(R.id.first, "点赞 " + gsonBean.getData().thumbUpCount + "");
                                    helper.setSelected(R.id.first, gsonBean.getData().isThumUp);
                                }
                            });
                }).setText(R.id.first, "点赞 " + item.thumbUpCount);//按钮一 点赞
                helper.addOnClickListener(R.id.second, v -> {
                    EditDialog.replyListener = new EditDialog.OnReplyListener() {
                        @Override
                        public void OnReply(String reply) {

                            ToastUtil.showLongToast("发表评论：\n" + reply);
                            new BasePresenter()
                                    .putParams("momentsId", item.id)
                                    .putParams("reply", reply)
                                    .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack() {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                            MomentsReply momentsReply = new MomentsReply();
//                                            momentsReply.reply = reply;
                                            if (gsonBean.getData() != null && gsonBean.getData().momentsReply != null) {
                                                item.itemListJson.add(gsonBean.getData().momentsReply);
                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
                                                globBaseAdapter.notifyDataSetChanged();
                                            }

                                        }
                                    });
//                                                list.add(s);
//                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                                globBaseAdapter.notifyDataSetChanged();

                        }
                    };
                    EditDialog.instance("回复二傻：").show(mActivity.getSupportFragmentManager(), TAG);


                });//按钮2 评论
                helper.addOnClickListener(R.id.third, v -> {
//                    ToastUtil.showLongToast("点击第3个");
//
//                    editText.setFocusable(true);
//                    editText.setFocusableInTouchMode(true);
//                    editText.requestFocus();
//                    InputMethodManager inputManager =
//                            (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputManager.showSoftInput(editText, 0);
//                    ToastUtil.showLongToast("点击第2个");
                    FlowerDetailActivity.CallPhone(item.attrData.displayPhone, mActivity);
                }); //按钮3 收藏
                helper.addOnClickListener(R.id.fourth, v -> {

                    ToastUtil.showLongToast("点击第4个");
                    new CommonPopupWindow.Builder(mActivity)
                            .setWidthDp(115)
                            .setHeightDp(108)
                            .setOutsideTouchable(true)
                            .bindLayoutId(R.layout.friend_more)
                            .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
                                @Override
                                public void covertView(View viewRoot) {
                                    TextView tv1 = (TextView) viewRoot.findViewById(R.id.pup_subscriber);
                                    tv1.setText("加入收藏");
                                    tv1.setTextColor(getColorByRes(R.color.text_color111));
                                    TextView tv2 = (TextView) viewRoot.findViewById(R.id.pup_show_share);
                                    tv2.setTextColor(getColorByRes(R.color.text_color111));
                                    tv2.setText("进入店铺");
                                }
                            })
                            .build()
//                            .showAsDropDown(helper.getView(R.id.fourth));
                            .showUp2(helper.getView(R.id.fourth));


                });//按钮4 电话


            }
        }).openRefresh()
                .openLoadMore(10, page -> {
                    showLoading();
                    requestDatas(page + "");
//                Observable.timer(2000, TimeUnit.MILLISECONDS)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Long>() {
//                            @Override
//                            public void accept(@NonNull Long aLong) throws Exception {
//                                mRecyclerView.selfRefresh(false);
//                                List<String> lists = new ArrayList<>();
//                                for (int i = 0; i < 10; i++) {
//                                    lists.add(i + "数据");
//                                }
//                                mRecyclerView.getAdapter().addData(lists);
//                            }
//                        });
                });
        mRecyclerView.onRefresh();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 110 && resultCode == SEARCH_OK) {
            searchContent = data.getStringExtra(SearchActivity.SEARCH_CONTENT);
            ToastUtil.showLongToast("搜索内容：\n" + searchContent);
            mRecyclerView.onRefresh();
        }


    }

    public String currentType = MomentsType.supply.getEnumValue();
    public String searchContent = "";

    public void requestDatas(String page) {
        new BasePresenter()
                .putParams("pageSize", "10")
                .putParams("pageIndex", page)
                .putParams("momentsType", currentType)
                .putParams("content", searchContent)
                .putParams("userId", MyApplication.getUserBean().id)
                .doRequest("moments/list", true, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {

                        Log.i(TAG, "onSuccess: " + json);
                        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
                        }.getType();
                        SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);


                        mRecyclerView.getAdapter().addData(bean_new.data.page.data);

                        ToastUtil.showLongToast(bean_new.data.page.total + "条数据");
                        mRecyclerView.selfRefresh(false);
                        hindLoading();

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        mRecyclerView.selfRefresh(false);
                        hindLoading();
                    }
                });
    }


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }


}
