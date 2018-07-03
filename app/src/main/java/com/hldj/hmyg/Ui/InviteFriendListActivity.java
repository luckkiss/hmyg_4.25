package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.HeadDetailActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.bean.UserBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hy.utils.SpanUtils;
import com.lqr.optionitemview.OptionItemView;
import com.mabeijianxi.smallvideorecord2.Log;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 邀请好友  界面   分享链接邀请好友
 */

public class InviteFriendListActivity extends BaseMVPActivity {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @ViewInject(id = R.id.show_total)
    TextView show_total;

    List<IntegralBean> integralBeens;

    OptionItemView top;


    @Override
    public int bindLayoutID() {
        return R.layout.activity_invite_friend_list;
    }


    @Override
    public void initView() {

        FinalActivity.initInjectedView(this);


//        View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.invite_friend_empty, null);

//        emptyView.findViewById(R.id.tv_go_to_invite).setOnClickListener(v -> {
////            ToastUtil.showLongToast("go to inviteing  11111");
//
//            InviteFriendActivity.start(mActivity);
//        });

        recycle.init(new BaseQuickAdapter<UserBean, BaseViewHolder>(R.layout.item_invite_friend_list) {
            @Override
            protected void convert(BaseViewHolder helper, UserBean item) {
                doConvert(helper, item, mActivity);
            }
        })
                .openRefresh()
                .openLoadMore(20, this::requestDatas)
                .closeDefaultEmptyView()
                .setDefaultEmptyView(true, "邀请好友", retry -> {
                    InviteFriendActivity.start(mActivity);
                })
                .showEmptyAndSetTip("还没有好友接受邀请~")
                .lazyShowEmptyViewEnable(true)
//                .setEmptyView(emptyView)
        ;


        recycle.onRefresh();
//        recycle.closeRefresh();

//        Observable.just("hello - world")
//                .delay(3, TimeUnit.SECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//
//                    }
//                });


//        new Handler().postDelayed(() -> {
////
////            recycle.post(new Runnable() {
////                @Override
////                public void run() {
////                    ToastUtil.showLongToast("加载成功3333....");
////
//
//
////            recycle.getAdapter().addData("aaa");
//
//
//            ArrayList arrayList = new ArrayList<>();
//            arrayList.add("aada");
//            arrayList.add("aada");
//            arrayList.add("aada");
//            arrayList.add("aada");
//            arrayList.add("aada");
//            recycle.getAdapter().addData(arrayList);
////                }
////            });
//        }, 3000);


//        getView(R.id.tv_go_to_invite).setOnClickListener(v -> {
//            InviteFriendActivity.start(mActivity);
//        });

    }

    private void doConvert(BaseViewHolder helper, UserBean item, NeedSwipeBackActivity mActivity) {

        int item_id = R.layout.item_invite_friend_list;


        ImageLoader.getInstance().displayImage(item.headImage, (de.hdodenhof.circleimageview.CircleImageView) helper.getView(R.id.circleImageView));

        helper
                .setText(R.id.title, item.phone)
                .setText(R.id.content, item.timeStampStr + "  " + item.cityName)
                .setVisible(R.id.fensi, true)
                .setText(R.id.fensi, item.displayName)
                .setBackgroundColor(R.id.fensi, Color.TRANSPARENT)
                .setTextColorRes(R.id.fensi, R.color.text_color999)
        ;
        helper.getView(R.id.fensi).setPadding(0, 0, 0, 0);

//        layout_constraintVertical_bias

        TextView textView = helper.getView(R.id.fensi);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.verticalBias = 0.3f;
        textView.setLayoutParams(layoutParams);

        helper.getConvertView().setOnClickListener(v -> {
            HeadDetailActivity.start(mActivity, item.id);
        });


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, InviteFriendListActivity.class));
    }


    public void requestDatas(int page) {
//        new BasePresenter()
//                .doRequest("userPoint/list", true, callBack);

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserBean>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page + "")
                .doRequest("admin/user/invitelog", true,
                        new HandlerAjaxCallBackPage<UserBean>(mActivity, type,
                                this::setTotalText) {
                            @Override
                            public void onRealSuccess(List<UserBean> userBeans) {

                                Log.d("==================");
                                recycle.getAdapter().addData(userBeans);


                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                recycle.selfRefresh(false);
                            }
                        });
    }


    @Override
    public String setTitle() {
        return "邀请好友记录";
    }


    public void setTotalText(int totalText) {
        ;
        show_total.setText(new SpanUtils()
                .append("共邀请了")
                .append("" + totalText).setForegroundColor(getColorByRes(R.color.red))
                .append("个好友")
                .create());
        show_total.setVisibility(totalText == 0 ? View.GONE : View.VISIBLE);

    }
}
