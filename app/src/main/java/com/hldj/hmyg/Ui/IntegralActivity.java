package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.M.userPoint.enums.UserPointType;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.purchase.PurchasePyMapActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.StartBarUtils;
import com.hldj.hmyg.widget.IntegralItemRelative;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.R.id.score;

/**
 * 花币 详情界面
 */

public class IntegralActivity extends BaseMVPActivity {


    CoreRecyclerView recycle;

    List<IntegralBean> integralBeens;

    OptionItemView top;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_integral;
    }


    @Override
    public void initView() {
        StateBarUtil.setStatusTranslater(this, false);
        StartBarUtils.FlymeSetStatusBarLightMode(getWindow(), false);
        StartBarUtils.MIUISetStatusBarLightMode(getWindow(), false);

        recycle = getView(R.id.recycle);
        top = getView(R.id.top);
        top.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                finish();
            }

            @Override
            public void centerOnClick() {
                Log.i("", "centerOnClick: ");
            }

            @Override
            public void rightOnClick() {
//                IntegralDetailsActivity.start(mActivity);
                Log.i("", "rightOnClick: ");
            }
        });


        getView(R.id.btn_jfmx).setOnClickListener(v -> {
            IntegralDetailsActivity.start(mActivity);
            Log.i("onClick", "积分明细: ");
        });

        getView(R.id.btn_yyq).setOnClickListener(v -> {
            InviteFriendListActivity.start(mActivity);
            Log.i("onClick", "已邀请: ");
        });

//        StateBarUtil.setColorPrimaryDark(getColorByRes(R.color.main_color), this.getWindow());

        recycle.init(new BaseQuickAdapter<UserPointType, BaseViewHolder>(R.layout.activity_integral_item_layout) {

            @Override
            protected void convert(BaseViewHolder helper, UserPointType item) {
                IntegralItemRelative relative = helper.getView(R.id.content);
                relative.setBottomText(item.detail);
                relative.setTopText(item.title);
                relative.setLeftIcon(item.icon);
                relative.setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch2Start(v);
                    }
                });
                relative.setRightTextStates(item.finish, item.point + " 花币", item.value);
                //red_btn_bg_color


            }
        })
                .closeDefaultEmptyView()
//                .openRefresh()
//                .addRefreshListener(new CoreRecyclerView.RefreshListener() {
//                    @Override
//                    public void refresh() {
//                        recycle.selfRefresh(false);
//                    }
//                })
        ;

//        recycle.getAdapter().addData(getIntegralBeens());


        showLoading();
        requestDatas(new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                //                Log.i(TAG, "onSuccess: " + json);
                Type beanType = new TypeToken<SimpleGsonBean_new<List<UserPointType>>>() {
                }.getType();
                SimpleGsonBean_new<List<UserPointType>> bean_new = GsonUtil.formateJson2Bean(json, beanType);
//
//                if (bean_new.data != null && bean_new.data.page != null)
                Log.i("", "onSuccess: " + json);

                ((TextView) getView(score)).setText(bean_new.data.point + "");

                recycle.getAdapter().setDatasState(100);

                /**
                 *
                 public String title;

                 public boolean finish;

                 public String icon;

                 public int point;

                 public String detail;

                 public String value;
                 */

                recycle.getAdapter().addData(bean_new.data.userPointTypeList);

                UserPointType userPointType = new UserPointType();
                userPointType.title = "邀请好友注册";
                userPointType.detail = "每成功邀请好友注册获得50积分";
                userPointType.point = 50;
                userPointType.icon = "";
                userPointType.value = "yaoqing";


//                recycle.getAdapter().add(0, userPointType);
//                ToastUtil.showLongToast(json);
                hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                ToastUtil.showLongToast("" + strMsg);
            }
        });


    }

    private void switch2Start(View v) {

        D.i("========switch2Start======" + v.getTag());
        String tag = "";
        if (null == v.getTag()) {
            return;
        }
        tag = v.getTag().toString();


        switch (tag) {
            case "invite":// 好友邀请

//                ToastUtil.showLongToast("好友yaoqing");
                InviteFriendActivity.start(mActivity);

                break;
            case "dailyLogin"://每日登陆
                break;
            case "publishMoments"://发布苗木圈
            case "share"://内容分享
                finish();
                MainActivity.toC();
                break;
            case "publishSeedling"://发布苗木
                finish();
                MainActivity.toD();
//                SaveSeedlingActivity.start2Activity(mActivity);
                break;
            case "quote"://采购报价
                PurchasePyMapActivity.start2Activity(mActivity);
                break;
            case "browseSeedling"://浏览苗木超过3条
                finish();
                MainActivity.toB();
                break;
        }


    }

//                isFirst = false;
//                Log.i(TAG, "onSuccess: " + json);
//                Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
//                }.getType();
//                SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);
//
//                if (bean_new.data != null && bean_new.data.page != null)
//                    mRecyclerView.getAdapter().addData(bean_new.data.page.data);
////                        ToastUtil.showLongToast(bean_new.data.page.total + "条数据");
//                mRecyclerView.selfRefresh(false);
//                baseMVPActivity.hindLoading();


    private List<IntegralBean> getIntegralBeens() {


        if (integralBeens == null) {
            integralBeens = new ArrayList<>();
            integralBeens.add(new IntegralBean(R.mipmap.jf_fbmmq, "发布苗木圈", "每条获得5分，每天上限20分", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_fbmmq, "发布苗木圈", "每条获得5分，每天上限20分", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_fbsp, "发布商品", "发布或更新商品5分", false));
            integralBeens.add(new IntegralBean(R.mipmap.jf_cgbj, "采购报价", "每日报价获得5分，不设上限", false));
            integralBeens.add(new IntegralBean(R.mipmap.jf_mrdl, "每日登陆", "每日打开花木易购获得2分，需要登录", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_nrfx, "内容分享", "分享店铺，商品，苗木圈获得10分", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_llsp, "浏览商品", "浏览店铺,商品，苗木圈获得10分", true));
        }
        return integralBeens;
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, IntegralActivity.class));
    }


    public void requestDatas(AjaxCallBack callBack) {
//        new BasePresenter()
//                .doRequest("userPoint/list", true, callBack);
        new BasePresenter()
                .doRequest("admin/userPoint/index", true, callBack);
    }


}
