package com.hldj.hmyg.Ui.friend.child;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.IScrollHiden;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.FriendCycleSearchActivity;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.enums.AgentGrade;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.presenter.FriendPresenter;
import com.hldj.hmyg.Ui.friend.widget.EditDialog;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.VideoHempler;
import com.hldj.hmyg.widget.MyCircleImageView;
import com.hy.utils.SpanUtils;
import com.hy.utils.ToastUtil;
import com.mabeijianxi.smallvideo2.VideoPlayerActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.bugly.crashreport.CrashReport;
import com.zzy.common.widget.MeasureGridView;
import com.zzy.common.widget.MeasureListView;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.Ui.friend.FriendCycleActivity.isSelf;
import static com.hldj.hmyg.application.MyApplication.dp2px;
import static com.hldj.hmyg.base.rxbus.RxBus.TAG_UPDATE;
import static com.hldj.hmyg.me.AttentionActivity.getOption;

/**
 * 基础的  朋友圈首页  用于显示 发布的内容。。懒加载。不刷新模式
 */

public class FriendBaseFragment extends BaseFragment {

    private static final String TAG = "FriendBaseFragment";


    private boolean mIsPrepared = false;
    private boolean isFirst = true;

    CoreRecyclerView mRecyclerView;
    BaseMVPActivity baseMVPActivity;
    private CommonPopupWindow popupWindow1;
    private String currentType = MomentsType.all.getEnumValue();
    private String searchContent = "";
    private String[] cityCodes;
    private String cityCodesString = "";

    public static String getApendStr(String[] citys) {


        if (citys == null || citys.length == 0) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();
            for (String city : citys) {
                buffer.append(city + ",");
            }
            return buffer.toString();
        }


    }

//    private String searchType = MomentsType.all.getEnumValue();


    public static FriendBaseFragment newInstance(String type) {
        FriendBaseFragment fragment = new FriendBaseFragment();
        Bundle args = new Bundle();
        args.putString(TAG, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mActivity instanceof NeedSwipeBackActivity) {
            baseMVPActivity = ((BaseMVPActivity) mActivity);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        if (getArguments() != null) {
            currentType = getArguments().getString(TAG);
        }
    }

    @Override
    protected void initView(View rootView) {
        RxRegi();
        mRecyclerView = getView(R.id.recycle);
        mRecyclerView.init(new BaseQuickAdapter<Moments, BaseViewHolder>(R.layout.item_friend_cicle) {
            @Override
            protected void convert(BaseViewHolder helper, Moments item) {
                if (((IScrollHiden) mActivity).getHiddenView() != null) {
                    if (helper.getAdapterPosition() > 3) {

                        ((IScrollHiden) mActivity).getHiddenView().setVisibility(View.VISIBLE);
                    } else {
                        ((IScrollHiden) mActivity).getHiddenView().setVisibility(View.GONE);
                    }
                }

                isFirst = false;
                Log.i(TAG, "convert: " + item + "   id=" + item.id);
                D.i("-----------------");
                String json = GsonUtil.Bean2Json(item);
                D.i("Moments json \n" + json);
                D.i("-----------------");
                //头像点击
                helper.addOnClickListener(R.id.head, v -> {
//                    ToastUtil.showLongToast("点击头像--->跳转个人商店");
//                    if (!commitLogin()) return;
                    HeadDetailActivity.start(mActivity, item.ownerId);
                    /* 新家详细资料 界面 */


                });

                if (item.attrData != null) {
                    //显示图片
//                  finalBitmap.display(helper.getView(R.id.head), item.attrData.headImage);
                    MyCircleImageView circleImageView = helper.getView(R.id.head);
//                    circleImageView.setImageURL(item.attrData.headImage);
                    ImageLoader.getInstance().displayImage(item.attrData.headImage, circleImageView, getOption());

                    AgentGrade anEnum = Enum.valueOf(AgentGrade.class, item.attrData.level);

                    ImageView imageView14 = helper.getView(R.id.imageView14);


                    Log.i(TAG, "convert: " + anEnum.getUpScore());
                    if (anEnum.compareTo(AgentGrade.level0) == 0) {
                        imageView14.setVisibility(View.GONE);
                    } else {
                        imageView14.setImageDrawable(mActivity.getResources().getDrawable(anEnum.getUpScore()));
                        imageView14.setVisibility(View.VISIBLE);
                    }


                    ImageView imageView13 = helper.getView(R.id.imageView13);
                    helper.setVisible(R.id.imageView13, item.attrData.identity);
//                    imageView13.setVisibility(item.attrData.identity);


                }
                if (MomentsType.supply.getEnumValue().equals(item.momentsType)) {
                    helper.setImageResource(R.id.imageView7, R.mipmap.publish);
                } else {
                    helper.setImageResource(R.id.imageView7, R.mipmap.purchase);
                }
//                }

                View.OnClickListener clickListener = v ->
                {
//                    if (!commitLogin()) return;
//                    ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
                    DetailActivity.start(FriendBaseFragment.this, item.id);
//                    item.visitsCount ++ ;
//
//                    String city = "-";
//                    if (item.ciCity != null) {
//                        city = item.ciCity.fullName;
//                    }
//                    helper.setText(R.id.time_city, item.timeStampStr + "  " + city + FUtil.$_head_no_("  浏览量 ", item.visitsCount + ""));//时间和  发布地址
                };
                helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题


                if (item.attrData != null) {
                    helper.setText(R.id.title, item.attrData.displayName);
                }

                String city = "-";
                if (item.ciCity != null) {
                    city = item.ciCity.fullName;
                }
                helper.addOnClickListener(R.id.time_city, clickListener).setText(R.id.time_city, item.timeStampStr + "  " + city + FUtil.$_head_no_("  浏览量 ", item.visitsCount + ""));//时间和  发布地址
                helper.addOnClickListener(R.id.descript, clickListener).setText(R.id.descript, item.content);//描述
//                helper.addOnClickListener(R.id.imageView8, v -> ToastUtil.showLongToast("点击图片--->跳转图片浏览器"));//描述
//                helper.addOnClickListener(R.id.receive, v -> ToastUtil.showLongToast("点击评论--->显示回复窗口"));//描述

                /* 视频  预览图片   */
                if (item.isVideo) {
                    ImageView video = helper.getView(R.id.video);
                    video.setVisibility(View.VISIBLE);
//                  video.setImageBitmap(VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mContext, 80), MyApplication.dp2px(mContext, 80)));

                    D.e("============加载地址===========" + item.attrData.videoImageUrl);
                    if (item.attrData != null && !TextUtils.isEmpty(item.attrData.videoImageUrl))
                        ImageLoader.getInstance().displayImage(item.attrData.videoImageUrl, video);
                    else {
                        Observable.just(item.videoUrl)
                                .filter(test -> !TextUtils.isEmpty(item.videoUrl))
                                .subscribeOn(Schedulers.io())
                                .map(s -> VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mContext, 80), MyApplication.dp2px(mContext, 80)))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Bitmap>() {
                                    @Override
                                    public void accept(@NonNull Bitmap result) throws Exception {
                                        video.setImageBitmap(result);
                                    }
                                });
                    }


//                ImageLoader.getInstance().displayImage(item.videoUrl, video);

                    video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity, VideoPlayerActivity2.class).putExtra(
                                    "path", item.videoUrl));
                        }
                    });

                } else {
                    ImageView video = helper.getView(R.id.video);
                    video.setVisibility(View.GONE);
                }

                /* 视频  预览图片   */


                MeasureGridView gridView = helper.getView(R.id.imageView8);

                MeasureListView measureListView = helper.getView(R.id.receive);

                if (item.itemListJson == null) {
                    item.itemListJson = new ArrayList<MomentsReply>();
                }

                TextView tv_bottom_line = helper.getView(R.id.textView30);


//                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,1);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv_bottom_line.getLayoutParams();
                if (item.itemListJson != null && !item.itemListJson.isEmpty()) {
                    //有评论    -----^-------
                    tv_bottom_line.setBackgroundResource(R.mipmap.bottom_triangle);
                    params.height = dp2px(mActivity, 9);
                    params.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
                    tv_bottom_line.setVisibility(View.VISIBLE);
                } else {
                    //没有评论    ------------
                    params.height = 1;
//                    params.setMargins(0,  dp2px(14), 0, 0);//4个参数按顺序分别是左上右下
                    params.setMargins(dp2px(mActivity, 0), dp2px(mActivity, 4), dp2px(mActivity, 0), dp2px(mActivity, 4));//4个参数按顺序分别是左上右下
                    tv_bottom_line.setBackgroundColor(baseMVPActivity.getColorByRes(R.color.divider_color));
                    tv_bottom_line.setVisibility(View.INVISIBLE);
                }
                tv_bottom_line.setText("");
                tv_bottom_line.setLayoutParams(params);


//                ConstraintSet mConstraintSet1 = new ConstraintSet(); // create a Constraint Set
//                ConstraintLayout c = (ConstraintLayout) mRootView.findViewById(R.id.constraint);
//                mConstraintSet1.clone(c);
//                mConstraintSet1.
//                TransitionManager.beginDelayedTransition(c);
//                mConstraintSet1.applyTo(c); // set new constraints

//                ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) measureListView.getLayoutParams();
//                params1.topToBottom = 1;
//                measureListView.setLayoutParams(params1);

                if (item.itemListJson.size() == 0) {
                    measureListView.setVisibility(View.GONE);
                } else {
                    measureListView.setVisibility(View.VISIBLE);


                }
                measureListView.setAdapter(new GlobBaseAdapter<MomentsReply>(mActivity, item.itemListJson, R.layout.item_list_simple) {
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


                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        textView.setHighlightColor(ContextCompat.getColor(mActivity, android.R.color.transparent));
                        SpanUtils spanUtils = new SpanUtils();

                        ClickableSpan left = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                HeadDetailActivity.start(mActivity, s.attrData.fromUserId);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
//                                ds.setColor(Color.BLUE);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan right = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
//                                ToastUtil.showShortToast("被回复的人  ");
                                HeadDetailActivity.start(mActivity, s.attrData.toUserId);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
//                                ds.setColor(Color.BLUE);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan clickContent = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (!commitLogin()) return;
                                if (isSelf(measureListView, s, position, mActivity)) return;
                                EditDialog.replyListener = reply -> {
                                    new BasePresenter()
                                            .putParams("momentsId", item.id)
                                            .putParams("reply", reply)
                                            .putParams("toId", s.fromId)
                                            .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack() {
                                                @Override
                                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                                    ToastUtil.showLongToast(gsonBean.msg);
                                                    if (gsonBean.getData() != null && gsonBean.getData().momentsReply != null) {
                                                        item.itemListJson.add(gsonBean.getData().momentsReply);
                                                        GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
                                                        globBaseAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                };
                                EditDialog.instance("回复: " + s.attrData.fromDisplayName).show(baseMVPActivity.getSupportFragmentManager(), "aa");


                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
//                                ds.setColor(Color.BLUE);
                                ds.setUnderlineText(false);
                            }
                        };

                        if (s.attrData.toDisplayName == null) {
                            //评论
//                            result = baseMVPActivity.filterColor(from + ": " + s.reply, from, R.color.main_color);
                            spanUtils
                                    .append(from).setForegroundColor(mActivity.getResources().getColor(R.color.main_color)).setClickSpan(left)
                                    .append(": " + s.reply).setForegroundColor(mActivity.getResources().getColor(R.color.text_color666)).setClickSpan(clickContent)
                                    .append(".").setForegroundColor(mActivity.getResources().getColor(R.color.trans))
                            ;
                        } else {
                            String to = s.attrData.toDisplayName;
                            SpannableStringBuilder str = baseMVPActivity.filterColor(from, from, R.color.main_color);
                            //回复
//                            result = str.append(baseMVPActivity.filterColor("回复" + to + ": " + s.reply, to, R.color.main_color));

                            spanUtils
                                    .append(from).setClickSpan(left).setForegroundColor(mActivity.getResources().getColor(R.color.main_color))
                                    .append("回复").setForegroundColor(mActivity.getResources().getColor(R.color.text_color666))
                                    .append(to).setForegroundColor(mActivity.getResources().getColor(R.color.main_color)).setClickSpan(right)
                                    .append(": " + s.reply).setClickSpan(clickContent)
                                    .append(".").setForegroundColor(mActivity.getResources().getColor(R.color.trans))
                            ;
//                            Linkify.addLinks(textView, PHONE, null);


//                            spanUtils.append("回复")
//                                    .append(to).setForegroundColor(mActivity.getResources().getColor(R.color.main_color))
//                                    .setClickSpan(right)
//                                    .append(s.reply)
//                            ;
                        }
                        textView.setText(spanUtils.create());
//                        LinkifySpannableUtils.getInstance().setSpan(mActivity,textView);

//                        textView.setAutoLinkMask(Linkify.PHONE_NUMBERS );


//                        textView.setText(result);

//                        textView.setOnClickListener(v -> {
//                            if (!commitLogin()) return;
//                            if (isSelf(measureListView, s, position, mActivity)) return;
//                            EditDialog.replyListener = reply -> {
//
//                                new BasePresenter()
//                                        .putParams("momentsId", item.id)
//                                        .putParams("reply", reply)
//                                        .putParams("toId", s.fromId)
//                                        .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack() {
//                                            @Override
//                                            public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                                ToastUtil.showLongToast(gsonBean.msg);
//                                                if (gsonBean.getData() != null && gsonBean.getData().momentsReply != null) {
//                                                    item.itemListJson.add(gsonBean.getData().momentsReply);
//                                                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
//                                                    globBaseAdapter.notifyDataSetChanged();
//                                                }
//                                            }
//                                        });
//                            };
//                            EditDialog.instance("回复: " + s.attrData.fromDisplayName).show(baseMVPActivity.getSupportFragmentManager(), "aa");
//                        });


                    }
                });


                gridView.setImageNumColumns(3);
//                gridView.setHorizontalSpacing(3);
//                gridView.setVerticalSpacing(0);
                gridView.setHorizontalSpacing(6);
                gridView.setVerticalSpacing(6);

                /**
                 *
                 *    gridView.setHorizontalSpacing(6);
                 gridView.setVerticalSpacing(6);
                 */

                //中图
                gridView.initFriend(mActivity, PurchaseDetailActivity.getPicList(item.imagesJson), (ViewGroup) gridView.getParent(), null);
//                gridView.getAdapter().closeAll(true);
//                gridView.getAdapter().notifyDataSetChanged();
                gridView.setOnViewImagesListener((mContext1, pos, pics) -> {
                    //获取原始图片
//                    ToastUtil.showLongToast("点击了" + pos);
                    GalleryImageActivity.startGalleryImageActivity(
                            mContext1, pos, PurchaseDetailActivity.getPicListOriginal(item.imagesJson));


                    new BasePresenter()
                            .putParams("id", item.id)
                            .doRequest("moments/visits", new HandlerAjaxCallBack() {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    if (gsonBean.isSucceed()) {
//                                        ToastUtil.showLongToast(gsonBean.msg);
                                        item.visitsCount ++ ;

                                        String city = "-";
                                        if (item.ciCity != null) {
                                            city = item.ciCity.fullName;
                                        }
                                        helper.setText(R.id.time_city, item.timeStampStr + "  " + city + FUtil.$_head_no_("  浏览量 ", item.visitsCount + ""));//时间和  发布地址


                                    }
                                }
                            });


                });

                if (PurchaseDetailActivity.getPicList(item.imagesJson).size() == 0) {
                    gridView.setVisibility(View.GONE);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                }


                helper.setSelected(R.id.first, item.isFavour);
                helper.addOnClickListener(R.id.first, v -> {
//                    ToastUtil.showLongToast("点击第一个");
                    if (!commitLogin()) return;
                    new BasePresenter()
                            .putParams("momentsId", item.id)
                            .doRequest("admin/momentsThumbUp/thumbUpDown", true, new HandlerAjaxCallBack(baseMVPActivity) {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    item.isFavour = gsonBean.getData().isThumUp;
                                    item.thumbUpCount = gsonBean.getData().thumbUpCount;
                                    ToastUtil.showLongToast(gsonBean.msg);
//                                    int current = !helper.getView(R.id.first).isSelected() ? (item.thumbUpCount + 1) : (item.thumbUpCount);
                                    helper.setText(R.id.first, "点赞 " + FUtil.$_zero_2_null(gsonBean.getData().thumbUpCount + ""));
                                    helper.setSelected(R.id.first, gsonBean.getData().isThumUp);
                                    RxBus.getInstance().post(RxBus.TAG_UPDATE, item);
                                    sendPush(item);
                                }
                            });
                }).setText(R.id.first, "点赞 " + FUtil.$_zero_2_null(item.thumbUpCount + ""));//按钮一 点赞

                helper.addOnClickListener(R.id.second, v -> {
                    if (!commitLogin()) return;

                    EditDialog.replyListener = new EditDialog.OnReplyListener() {
                        @Override
                        public void OnReply(String reply) {
//                            ToastUtil.showLongToast("发表评论：\n" + reply);
                            new BasePresenter()
                                    .putParams("momentsId", item.id)
                                    .putParams("reply", reply)
                                    .doRequest("admin/momentsReply/save", true, new HandlerAjaxCallBack(baseMVPActivity) {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                                            ToastUtil.showLongToast(gsonBean.msg);
                                            if (gsonBean.getData() != null && gsonBean.getData().momentsReply != null) {
                                                item.itemListJson.add(gsonBean.getData().momentsReply);
                                                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) measureListView.getAdapter();
                                                globBaseAdapter.notifyDataSetChanged();
                                                RxBus.getInstance().post(RxBus.TAG_UPDATE, item);
                                                sendPush(item);
                                            }

                                        }
                                    });
                        }
                    };
                    EditDialog.instance("评论: " + item.attrData.displayName).show(baseMVPActivity.getSupportFragmentManager(), TAG);


                });//按钮2 评论
                helper.addOnClickListener(R.id.third, v -> {
//                  if (!commitLogin()) return;
                    if (TextUtils.isEmpty(item.attrData.displayPhone)) {
                        ToastUtil.showLongToast("未留电话号码~_~");
                    } else {

                        FlowerDetailActivity.CallPhone(item.attrData.displayPhone, mActivity, succeed -> {
                            FriendPresenter.postWhoPhone(item.ownerId, item.id, item.attrData.displayPhone, ConstantState.TYPE_OWNER);
                        });
                    }
//                    ToastUtil.showLongToast("点击第2个");
//                    FlowerDetailActivity.CallPhone(item.attrData.displayPhone, mActivity);
                }); //按钮3 收藏
                helper.addOnClickListener(R.id.fourth, v -> {
//                    ToastUtil.showLongToast("点击第4个");
//                    if (popupWindow1 == null)
                    //未登录。跳转登录界面
//                    if (!commitLogin()) return;
//                    ToastUtil.showLongToast("点击第4个");
                    popupWindow1 = FriendPresenter.createMorePop(
                            item,
                            (BaseMVPActivity) mActivity,
                            v1 -> {
                            });
//                            .showAsDropDown(helper.getView(R.id.fourth));
                    popupWindow1.showUp2(helper.getView(R.id.fourth));
                });//按钮4 电话


            }
        }).openRefresh()
                .openLoadMore(10, page -> {
                    baseMVPActivity.showLoading();
                    requestDatas(page + "");
                }).closeDefaultEmptyView();


        hiddenLisenter();

    }

    public static void sendPush(Moments item) {
        if (!item.isOwner) {
            D.w("=================发送自定义推送消息 start===================");
//            JpushUtil.sendCustommPush(item.id, item.ownerId);
            D.w("=================发送自定义推送消息 end===================");
        } else {
//                                      JpushUtil.sendCustommPush(item.id, item.ownerId);
            D.w("=================是自己，，不发送更新推送 ===================");
        }
    }


    public void requestDatas(String page) {
        new BasePresenter()
                .putParams("pageSize", "10")
                .putParams("pageIndex", page)
                .putParams("momentsType", currentType)
                .putParams("content", searchContent)
                .putParams("cityCodeList", cityCodesString)// 填写地区    str 【】
//                .putParams("cityCodeList", getApendStr(cityCodes))// 填写地区    str 【】
                .putParams("userId", MyApplication.getUserBean().id)
                .doRequest("moments/list", true, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        isFirst = false;

                        Log.i(TAG, "onSuccess: " + json);
                        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
                        }.getType();

                        try {

                            SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);

                            if (bean_new.data != null && bean_new.data.page != null)
                                mRecyclerView.getAdapter().addData(bean_new.data.page.data);
//                        ToastUtil.showLongToast(bean_new.data.page.total + "条数据");
                        } catch (Exception e) {
                            CrashReport.postCatchedException(e);  // bugly会将这个throwable上报
                            ToastUtil.showShortToast("数据解析失败");
                        }

                        mRecyclerView.selfRefresh(false);
                        baseMVPActivity.hindLoading();


                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        mRecyclerView.selfRefresh(false);
                        baseMVPActivity.hindLoading();
                    }
                });
    }

    @Override
    protected void loadData() {
        super.loadData();
        onRefresh("", "", "");//根据搜索条件。判断是否需要刷新数据
//        if (!mIsVisible || !mIsPrepared || !isFirst) {
//            Log.e(TAG, "不加载数据 mIsVisible=" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
//            return;
//        }
        Log.i(TAG, "loadData: mIsVisible" + mIsVisible);
//      if (!mIsVisible) return;

    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_friend_recycle;
    }


    /**
     * 判断是否需要登录
     *
     * @return
     */
    public boolean commitLogin() {
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start2Activity(mActivity);
            ToastUtil.showLongToast("请先登录 ^_^ ");
            return false;
        }
    }

    /*是否登录*/
    public boolean isLogin() {
        boolean isLogin = MyApplication.Userinfo.getBoolean("isLogin", false);
        Log.i("LOGIN", "判断是否登录: \n" + isLogin);
        return isLogin;
    }


    /**
     * 删除单条帖子
     *
     * @param moments
     * @param pos
     */
    private void doDelete(Moments moments, int pos) {
        Log.i(TAG, "doDelete: host\n" + "admin/moments/doDel");
        FriendPresenter.doDelete(moments.id, new HandlerAjaxCallBack((NeedSwipeBackActivity) mActivity) {
            @Override
            public void onRealSuccess(SimpleGsonBean gsonBean) {
                ToastUtil.showLongToast(gsonBean.msg);
                mRecyclerView.remove(pos);
                RxBus.getInstance().post(RxBus.TAG_DELETE, moments);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//      ToastUtil.showLongToast("--gragment --  执行 回调");


        if (mRecyclerView == null) {
            D.w("mRecyclerView==null,不能刷新");
            return;
        }


        if (resultCode == ConstantState.REFRESH) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRecyclerView != null)
                        mRecyclerView.onRefresh();

                }
            }, 500);
//            mRecyclerView.onRefresh();
        } else if (resultCode == ConstantState.PUBLISH_SUCCEED) {
//        } else if (resultCode == ConstantState.PUBLIC_SUCCEED) {
            //发布成功，当发布的时候刷新

            mRecyclerView.onRefresh();

//            if (currentType.equals(MomentsType.supply.getEnumValue()))
//                mRecyclerView.onRefresh();
//            else //切换到个别列表
//            {
//                currentType = MomentsType.supply.getEnumValue();
//                mRecyclerView.onRefresh();
//            }
            D.e("currentType" + currentType);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRecyclerView != null)
                        mRecyclerView.onRefresh();

                }
            }, 500);

        } else if (resultCode == ConstantState.PURCHASE_SUCCEED) {
            //求购成功，当求购的时候刷新
//            if (currentType.equals(MomentsType.purchase.getEnumValue()))
//                mRecyclerView.onRefresh();
//            else {
//                {
//                    currentType = MomentsType.purchase.getEnumValue();
//                    mRecyclerView.onRefresh();
//                }
//            }
            D.e("currentType" + currentType);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRecyclerView != null)
                        mRecyclerView.onRefresh();

                }
            }, 500);
        }


//        ToastUtil.showLongToast("requestCode=" + requestCode + "  resultCode=" + resultCode);
        D.i("requestCode=" + requestCode + "  resultCode=" + resultCode);
    }


    public void onRefresh() {
        D.i("" + mRecyclerView);
    }


    public void RxRegi() {
        RxBus.getInstance().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUnRegi();
    }

    public void RxUnRegi() {
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


    public void onRefresh(String content, String type, String cityCodes) {

//        if (cityCodes != null) {
//            ToastUtil.showLongToast("cityCodes=" + Arrays.toString(cityCodes) + " \n content =" + content);
//
//        } else {
//            ToastUtil.showLongToast("cityCodes is null ");
//        }

        D.i("" + mRecyclerView);
        if (baseMVPActivity instanceof FriendCycleSearchActivity) {

//            if (!mIsVisible || !mIsPrepared) {
//                Log.e(TAG, "不加刷新数据  onRefresh");
//                return;
//            }
            cityCodesString = cityCodes;
            currentType = type;
            D.i("当前界面是    search activity");
            D.i("fragment -> searchContent \n" + searchContent);
            D.i("activity -> searchContent\n" + ((FriendCycleSearchActivity) mActivity).searchContent);
            D.i("activity -> getSearchContent\n" + ((FriendCycleSearchActivity) mActivity).getSearchContent());

//            if (searchContent.equals(((FriendCycleSearchActivity) mActivity).searchContent)) {
//                D.e("跟上次搜索条件一样。不需要刷新");
//                if (content.equals("1")) {
//                    ToastUtil.showLongToast("请输入不一样的内容^_^");
//                }
//                return;
//            } else {
            searchContent = ((FriendCycleSearchActivity) mActivity).searchContent;
            D.e("跟上次搜索条件搜索  不一样。要刷新");
            if (mRecyclerView != null)
                mRecyclerView.onRefresh();
//            }
        } else {
            if (!mIsVisible || !mIsPrepared || !isFirst) {
                Log.e(TAG, "不加刷新数据  onRefresh");
                return;
            }
            mRecyclerView.onRefresh();
            D.i("当前界面不是 search activity");
        }
    }


    @Override
    protected void onInvisible() {
        super.onInvisible();
        D.w("onInvisible 不显示。。加入Rxbus " + this);
        RxRegi();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        MainActivity.clicks = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ToastUtil.showLongToast("是否显示----------" + getUserVisibleHint());

                Log.i("tag", FriendBaseFragment.this + "");
                if (mRecyclerView != null) {
                    mRecyclerView.getRecyclerView().smoothScrollBy(0, 0);
                    mRecyclerView.getRecyclerView().smoothScrollToPosition(0);
                }
            }
        };
        D.w("onVisible 显示  解除订阅" + this);

        hiddenLisenter();


    }

    public void hiddenLisenter() {
        if (mActivity instanceof IScrollHiden) {
            if (((IScrollHiden) mActivity).getHiddenView() != null) {
                ((IScrollHiden) mActivity).getHiddenView().setOnClickListener(v -> {

                    if (mRecyclerView != null) {
                        ((IScrollHiden) mActivity).getHiddenView().setVisibility(View.GONE);
                        mRecyclerView.getRecyclerView().smoothScrollBy(0, 0);
                        mRecyclerView.getRecyclerView().smoothScrollToPosition(0);
                        onrefresh();
                    }
                });
//                mRecyclerView.getRecyclerView().setOnScrollListener(new RecyclerView.OnScrollListener() {
//                    @Override
//                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                        super.onScrollStateChanged(recyclerView, newState);
//                    }
//
//                    /**
//                     *    //返回当前recyclerview的可见的item数目，也就是datas.length
//                     //dx是水平滚动的距离，dy是垂直滚动距离，向上滚动的时候为正，向下滚动的时候为负
//                     * @param recyclerView
//                     * @param dx
//                     * @param dy
//                     */
//                    @Override
//                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                        super.onScrolled(recyclerView, dx, dy);
//
//
//                        Log.i(" scroll -- > ", dy + "");
//                        Log.i("getY() -- > ", recyclerView.getY() + "");
//
////                        if (recyclerView.getTop() > 200) {
////                            ((IScrollHiden) mActivity).getHiddenView().setVisibility(View.VISIBLE);
////                        } else {
////                            ((IScrollHiden) mActivity).getHiddenView().setVisibility(View.GONE);
////                        }
//
//                        //System.out.println(linearLayoutManager.getItemCount());
//                        System.err.println(dy);
//                    }
//                });
//            }
            }
        }
        if (mActivity == null) {
            new Handler().postDelayed(this::hiddenLisenter, 1000);
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        D.w("onPause 界面暂停。。加入Rxbus " + this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxRegi();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "mIsVisible: " + mIsVisible);
        Log.i(TAG, "isVisible(): " + isVisible());
        if (mIsVisible) {
//          RxUnRegi();
            D.w("onResume FriendBaseFragment  当前界面。解除订阅  rxbus" + this);
        }
        loadData();
    }


    //   http://test.m.hmeg.cn/moments/detail/56dab9d64fd2424aa1450b9c4cb9192b.html

    //56dab9d64fd2424aa1450b9c4cb9192b
//    http://192.168.1.20:83/api/

    public boolean mIsSelf(String onwerId) {
        return onwerId.equals(MyApplication.Userinfo.getString("id", ""));
    }


    public void onrefresh() {
        mRecyclerView.onRefresh();
    }
}
