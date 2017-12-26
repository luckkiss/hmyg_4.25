package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Message;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.bean.UnRead;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BaseRxPresenter;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 未读消息列表
 */

public class PushListActivity extends BaseMVPActivity {

    private static final String TAG = "PushListActivity";

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;
    FinalDb finalDb;

    @Override
    public void initView() {
        finalDb = FinalDb.create(mActivity);
        FinalActivity.initInjectedView(this);
        recycle.init(new BaseQuickAdapter<UnRead, BaseViewHolder>(R.layout.item_friend_push_list) {

            @Override
            protected void convert(BaseViewHolder helper, UnRead item) {
                /*处理回复的内容*/
                processReply(helper, item);

            }
        }).openRefresh().openLoadMore(Integer.MAX_VALUE, page -> {
            requestUnReadList(finalDb.findAll(Message.class));
        });

        recycle.onRefresh();
//      recycle.getAdapter().addData(finalDb.findAll(Message.class));

//      finalDb.deleteAll(Message.class);
    }

    /**
     * public String momentsContent;
     * public MomentsReply reply;
     * public String momentsImage;
     * public String momentsId;
     * public String type;
     * public MomentsThumbUp thumbUp;
     *
     * @param helper
     * @param item
     */

    private void processReply(BaseViewHolder helper, UnRead item) {

        MomentsReply reply = item.reply;
        if (reply != null) {
            displayImg(reply.attrData.fromHeadImage, helper.getView(R.id.iv_left));
            displayImg(item.momentsImage, helper.getView(R.id.iv_right));
            helper
                    .setText(R.id.title, reply.attrData.fromDisplayName) // 大傻
                    .setText(R.id.content, getReplyContent(reply, item.momentsContent)) // 你很帅
                    .setText(R.id.time, item.reply.timeStampStr) // 一小时前
                    .setVisible(R.id.iv_right, true)
                    .addOnClickListener(R.id.root, v -> {
                        Log.i(TAG, "processReply:  item.momentsId" + item.momentsId);
                        DetailActivity.start(mActivity, item.momentsId);
                    });
        }
        MomentsThumbUp thumbUp = item.thumbUp;
        if (thumbUp != null) {
            displayImg(thumbUp.attrData.headImage, helper.getView(R.id.iv_left));

            helper
                    .setText(R.id.title, FUtil.$_zero(thumbUp.attrData.displayName)) // 大傻
//                  .setDrawableLeft(R.id.content, R.mipmap.love_sel) // 你很帅
                    .setText(R.id.content, "♡") // 你很帅
                    .setText(R.id.tv_right, item.momentsContent) // 你很帅
                    .setText(R.id.time, item.thumbUp.timeStampStr) // 一小时前
                    .setVisible(R.id.iv_right, false)
                    .addOnClickListener(R.id.root, v -> {
                        Log.i(TAG, "processReply:  item.momentsId" + item.momentsId);
                        DetailActivity.start(mActivity, item.momentsId);
                    });
        }


    }

    private void displayImg(String headImage, ImageView imageView) {
        ImageLoader.getInstance().displayImage(headImage, imageView);
    }

    private String getReplyContent(MomentsReply reply, String content) {
        if (!TextUtils.isEmpty(reply.toId)) {
            /* a  回复   b   *@#！@  */
            //  有回复
            return "回复了 " + reply.attrData.toDisplayName + " : " + reply.reply;
        } else {
            return reply.reply;
        }
    }


    private void requestUnReadList(List<Message> messageList) {
        showLoading();
        Observable.just(messageList)
                .doOnSubscribe(sub -> showLoading())

                .map(messageList1 -> {
                    return processList(messageList1);
                })
                .map(ids -> request(ids))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        ;

//        new BaseRxPresenter()
//                .doRequest("", true, new HandlerAjaxCallBack() {
//                    @Override
//                    public void onRealSuccess(SimpleGsonBean gsonBean) {
//
//                    }
//                });


    }

    private String processList(List<Message> messageList1) {
        if (messageList1 == null) {
            ToastUtil.showLongToast("暂无未读消息~_~");
            return "";
        }
        String ids = "";
        for (int i = 0; i < messageList1.size(); i++) {
            if (i == messageList1.size() - 1) {
                ids += messageList1.get(i).getSourceId();
            } else {
                ids += messageList1.get(i).getSourceId() + ",";
            }
        }
        Log.i("ids = \n", "processList: " + ids);
        return ids;
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_friend_push_list;
    }


    @Override
    public String setTitle() {
        return "消息";
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, PushListActivity.class));
    }


    public Observable<String> request(String ids) {


        Observable<String> e = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                new BaseRxPresenter()
                        .putParams("replyIds", ids)
//                      .putParams("thumbUpIds", "564d2c79403b4a32a3aab36500373c14")
                        .doRequest("admin/moments/unReadList", true, new HandlerAjaxCallBack() {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                                        Log.i(TAG, "onRealSuccess: " + gsonBean.getData().optionList);
                                        Log.i("onRealSuccess", "onRealSuccess: onnext");
                                        recycle.getAdapter().addData(gsonBean.getData().optionList);
                                        e.onNext(gsonBean.msg);
                                        e.onComplete();
                                        hindLoading();
//                                        FinalDb finalDb = FinalDb.create(mActivity);
//                                        finalDb.deleteAll(Message.class);
                                        recycle.selfRefresh(false);

                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        super.onFailure(t, errorNo, strMsg);
                                        recycle.selfRefresh(false);
                                    }
                                }

                        );
            }
        });
        e.subscribe();
        return e;
    }


    @Override
    protected void onDestroy() {
        finalDb.deleteAll(Message.class);
        super.onDestroy();
    }



}
