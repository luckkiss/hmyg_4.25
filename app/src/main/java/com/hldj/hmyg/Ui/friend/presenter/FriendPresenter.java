package com.hldj.hmyg.Ui.friend.presenter;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import net.tsz.afinal.http.AjaxCallBack;

import static com.hldj.hmyg.CallBack.MyBetaPatchListener.TAG;

/**
 * 朋友圈公用类
 */

public class FriendPresenter {

    /**
     * 朋友圈 帖子加入收藏
     */
    public static void doCollect(String id, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("sourceId", id)
                .putParams("type", "moment")
                .doRequest("admin/collect/save", true, ajaxCallBack);
    }


    /**
     * 朋友圈 帖子收藏  列表
     */
    public static void momentsCollect(String id, AjaxCallBack<String> ajaxCallBack) {
        new BasePresenter()
                .putParams("sourceId", id)
                .putParams("type", "moment")
                .doRequest("admin/collect/listMoment", true, ajaxCallBack);
    }


    /**
     * 朋友圈 帖子收藏  列表   moment
     */
    public static void doDelete(String id, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("ids", id)
                .putParams("id", id)
                .doRequest("admin/moments/doDel", true, ajaxCallBack);
    }

    /**
     * 朋友圈 帖子收藏  列表   moment
     */
    public static void doDeleteReply(String id, String fromId, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("ids", id)
                .putParams("id", id)
                .putParams("fromId", fromId)
                .doRequest("admin/momentsReply/doDel", true, ajaxCallBack);
    }


    /**
     * 执行分享方法
     */
    public static void share(FragmentActivity activity, Moments item) {
        D.e("分享");
        ComonShareDialogFragment.newInstance()
                .setShareBean(new ComonShareDialogFragment.ShareBean(
                        "花木易购苗木圈",
                        item.content,
                        "desc",
                        (item.imagesJson != null && item.imagesJson.size() > 0) ? item.imagesJson.get(0).ossMediumImagePath : GetServerUrl.ICON_PAHT,
                        GetServerUrl.getHtmlUrl() + "moments/detail/" + item.id + ".html"))
                .show(activity.getSupportFragmentManager(), activity.getClass().getName());
    }


    private static CommonPopupWindow popupWindow1;

    /**
     * 执行 更多弹窗
     */
    public static CommonPopupWindow createMorePop(Moments item, BaseMVPActivity baseMVPActivity, View.OnClickListener onDelete) {


        return popupWindow1 = new CommonPopupWindow.Builder(baseMVPActivity)
                .setOutsideTouchable(true)
                .bindLayoutId(R.layout.friend_more)
                .setCovertViewListener(viewRoot -> {

                                /*分享按钮*/
                    TextView pup_share = (TextView) viewRoot.findViewById(R.id.pup_share);
                    pup_share.setTextColor(baseMVPActivity.getColorByRes(R.color.text_color111));
                    pup_share.setVisibility(View.VISIBLE);
                    pup_share.setOnClickListener(v_share -> {
//                                    ToastUtil.showLongToast("分享");
                        FriendPresenter.share(baseMVPActivity, item);
                        popupWindow1.dismiss();
                    });


                    TextView pup_del = (TextView) viewRoot.findViewById(R.id.pup_del);

                    pup_del.setVisibility(item.isOwner ? View.VISIBLE : View.GONE);

                    pup_del.setTextColor(baseMVPActivity.getColorByRes(R.color.text_color111));
                    pup_del.setOnClickListener(vd -> {
                        new AlertDialog(baseMVPActivity).builder()
                                .setTitle("确定删除本项?")
                                .setPositiveButton("确定删除", v -> {

                                    FriendPresenter.doDelete(item.id, new HandlerAjaxCallBack(baseMVPActivity) {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                                            ToastUtil.showLongToast(gsonBean.msg);
                                            baseMVPActivity.hindLoading();
                                            RxBus.getInstance().post(RxBus.TAG_DELETE, item);
                                            onDelete.onClick(v);
                                        }
                                    });

                                    popupWindow1.dismiss();
                                }).setNegativeButton("取消", v2 -> {
                        }).show();
//                      ToastUtil.showLongToast("删除\n" + item.id);
                        popupWindow1.dismiss();
                    });
                    // doDelete(item, helper.getAdapterPosition());

                                /*收藏按钮*/
                    SuperTextView tv1 = (SuperTextView) viewRoot.findViewById(R.id.pup_subscriber);
//                                tv1.setText("加入收藏");

                    Log.i("isFavour", "item.isFavour: " + item.isFavour);
                    if (item.isFavour) {  /*已经收藏*/
                        tv1.setDrawable(baseMVPActivity.getResources().getDrawable(R.mipmap.love_sel));
                        tv1.setTextColor(baseMVPActivity.getColorByRes(R.color.main_color));
                    } else {    /*未收藏状态*/
                        tv1.setDrawable(baseMVPActivity.getResources().getDrawable(R.mipmap.love));
                        tv1.setTextColor(baseMVPActivity.getColorByRes(R.color.text_color111));
                    }


//                                tv1.setTextColor(baseMVPActivity.getColorByRes(R.color.text_color111));
                    tv1.setOnClickListener(v1 -> {
//                                        ToastUtil.showLongToast("加入收藏" + item.id);
                        Log.i(TAG, "covertView: " + item.id);
//                                    tv1.setSelected(!tv1.isSelected());
                        if (item.isFavour) {
                            tv1.setDrawable(baseMVPActivity.getResources().getDrawable(R.mipmap.love_sel));
                        } else {
                            tv1.setDrawable(baseMVPActivity.getResources().getDrawable(R.mipmap.love));
                        }
                        FriendPresenter.doCollect(item.id, new HandlerAjaxCallBack(baseMVPActivity) {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                ToastUtil.showLongToast(gsonBean.msg);
                                item.isFavour = gsonBean.getData().isCollect();
                            }
                        });
                        popupWindow1.dismiss();
                    });


                    TextView tv2 = (TextView) viewRoot.findViewById(R.id.pup_show_share);
                    tv2.setTextColor(baseMVPActivity.getColorByRes(R.color.text_color111));
//                              tv2.setText("进入店铺");
                    tv2.setOnClickListener(v1 -> {
                        if (TextUtils.isEmpty(item.attrData.storeId)) {
                            ToastUtil.showLongToast("sorry , 此人可能没有开通店铺~_~");
                            return;
                        }
                        StoreActivity_new.start2Activity(baseMVPActivity, item.attrData.storeId);
                        popupWindow1.dismiss();
                    });
                })
                .build();//按钮4 电话
    }


}
