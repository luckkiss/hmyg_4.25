package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.DActivity_new_mp;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.friend.child.HeadDetailActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.FootMark;
import com.hldj.hmyg.util.D;
import com.hy.utils.SpanUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 匿名访客   界面
 */

public class FootMarkActivity extends BaseMVPActivity {

    int item_layout_id = R.layout.item_invite_friend_list;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_be_attention;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;


    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);


        getView(R.id.nmfk).setVisibility(是否匿名() ? View.GONE : View.VISIBLE);

        getView(R.id.nmfk).setOnClickListener(v -> {
            FootMarkActivity.start(mActivity, true);
        });


        recycler.init(new BaseQuickAdapter<FootMark, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, FootMark item) {
                doConvert(helper, item, mActivity);
            }
        })
                .openRefresh()
                .openLoadMore(20, page -> {
                    requestDataByPage(page);
                })
        ;

        recycler.onRefresh();


    }

    private void doConvert(BaseViewHolder helper, FootMark item, NeedSwipeBackActivity mActivity) {

        int item_id = R.layout.item_invite_friend_list;

        helper
                .setVisible(R.id.fensi, false)
                .setText(R.id.title, item.attrData.userName)
                .setText(R.id.content, item.attrData.timeStamp + "   " + item.attrData.cityName)
        ;


        if (是否匿名()) {
            helper.setText(R.id.content, item.attrData.timeStamp + "   " + item.ipCity);
        }

        ImageLoader.getInstance().displayImage(item.attrData.userHeadImage, (ImageView) helper.getView(R.id.circleImageView));


        if (是否匿名()) {
            D.i("---------匿名---------");
        } else {
            helper
                    .convertView.setOnClickListener(v -> HeadDetailActivity.start(mActivity, item.userId));
        }


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, FootMarkActivity.class));
    }


    public final static String isTouTou = "是不是匿名访客";


    /* 匿名进入 */
    public static void start(Activity mActivity, boolean toutou) {
        Intent intent = new Intent(mActivity, FootMarkActivity.class);
        intent.putExtra(isTouTou, toutou);
        mActivity.startActivity(intent);
    }

    public boolean 是否匿名() {

        D.i("是否匿名: " + getIntent().getBooleanExtra(isTouTou, false));

        return getIntent().getBooleanExtra(isTouTou, false);
    }

    @Override
    public String setTitle() {
        return "访客";
    }


    public void requestDataByPage(int page) {

        java.lang.reflect.Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<FootMark>>>>() {
        }.getType();

        String host = "";
        if (是否匿名()) {
            host = "admin/footmark/guestList";
        } else {
            host = "admin/footmark/beUserList";
        }


        new BasePresenter()
                .putParams("pageIndex", "" + page)
                .doRequest(host, new HandlerAjaxCallBackPage<FootMark>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<FootMark> userQuotes) {
                        recycler.getAdapter().addData(userQuotes);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycler.selfRefresh(false);
                    }
                });


    }


    @Override
    protected void onStart() {
        super.onStart();
        doCallLogIsRead();
    }

    public void doCallLogIsRead() {

        if (是否匿名()) {
            new BasePresenter()
                    .doRequest("admin/footmark/guestIsRead", new HandlerAjaxCallBack() {
                        @Override
                        public void onRealSuccess(SimpleGsonBean gsonBean) {

                            RxBus.getInstance().post(DActivity_new_mp.refresh, new Eactivity3_0.OnlineEvent(true));
//                        ToastUtil.showShortToast("访客 未读 清除成功");
//                        ToastUtil.showShortToast(gsonBean.msg);
                        }
                    });
        }


        new BasePresenter()
                .doRequest("admin/footmark/isShowNew", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

//                        ToastUtil.showShortToast("访客 未读 清除成功");
//                        ToastUtil.showShortToast(gsonBean.msg);

                        TextView nmfk = getView(R.id.nmfk);


                        if (gsonBean.getData().hasNewGuest) {
                            nmfk.setText(
                                    new SpanUtils()
                                            .append("匿名访客 ")
                                            .appendImage(R.drawable.dot_red, SpanUtils.ALIGN_TOP)
                                            .create()
                            );
                        } else {
                            nmfk.setText("匿名访客");
                        }


                    }
                });


    }


}

