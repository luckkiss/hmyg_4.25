package com.hldj.hmyg;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.M.userIdentity.enums.CompanyIdentityStatus;
import com.hldj.hmyg.Ui.friend.bean.tipNum.TipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.TipNumType;
import com.hldj.hmyg.Ui.friend.child.MarchingPurchaseActivity;
import com.hldj.hmyg.Ui.friend.child.PhoneLogActivity;
import com.hldj.hmyg.Ui.miaopu.PendingActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.me.FootMarkActivity;
import com.hldj.hmyg.saler.AddAdressActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.SpanUtils;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 * 收藏夹  界面
 */
public class DActivity_new_mp extends BaseMVPActivity implements View.OnClickListener {

    private View headView;
    private View footerView;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_d_new_mp;
    }

    //    @ViewInject(id = R.id.btn_xzmp, click = "onClick")
        /* 动态添加的  view  无法注入 headview  */
    Button btn_xzmp;

    //    @ViewInject(id = R.id.logo, click = "onClick")
    /* 动态添加的  view  无法注入 headview  */
    ImageView logo;


//    @ViewInject(id = R.id.djck, click = "onClick")
//    TextView djck;

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @ViewInject(id = R.id.toolbar_left_icon)
    ImageView toolbar_left_icon;

    @ViewInject(id = R.id.toolbar_right_icon)
    ImageView toolbar_right_icon;

    @ViewInject(id = R.id.toolbar_right_text)
    TextView toolbar_right_text;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xzmp:
                D.i("========新增圃==========");
                AddAdressActivity.start2Activity(mActivity, null);
                break;
            case R.id.logo:
                D.i("========店铺设置==========");
                StoreSettingActivity.start2Activity(mActivity);
                break;
        }
    }


    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        /* 由于注入是在 添加头部之前 。可能会找不到。需要手动 添加一个头部。再injection  不然会导致空指针 →_→  猜测*/
        initCoreRecycleView();


//        FinalActivity.initInjectedView(this, recycle);

        toolbar_left_icon.setVisibility(View.GONE);
        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_text.setText("分享");


//        djck.setOnClickListener(v -> {
//                 /* 苗圃管理 */
//            D.i("========苗圃管理==========");
//            ManagerListActivity_new.start2Activity(mActivity);
//        });

    }

    @Override
    public void initData() {

    }

    private void initCoreRecycleView() {

        int headViewId = R.layout.item_head_d_new_mp;

        recycle.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_d_new_mp) {
            @Override
            public void convert(BaseViewHolder helper, AddressBean item) {

//                helper.convertView.setOnClickListener(v -> {
//                    ToastUtil.showLongToast("aaa");
//                    D.i("========苗圃管理==========");
////                    ManagerListActivity_new.start2Activity(mActivity);
//                    ManagerSplitListActivity_new.start2Activity(mActivity);
//                });

                D.i("========苗圃管理==========" + item.fullAddress);

                doConvert(helper, item, mActivity);


            }
        })

                .openLoadMore(999, page -> {
                    requestData();
                    requestHead();

                })
                .openRefresh();


//        headView = createHeadView();
//        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_d_new_mp, null);
        headView = getLayoutInflater().inflate(R.layout.item_head_d_new_mp, null);

        footerView = getLayoutInflater().inflate(R.layout.item_footer_d_new_mp, null);
//        footerView = LayoutInflater.from(mActivity).inflate(R.layout.item_footer_d_new_mp,null);
        recycle.addHeaderView(headView);
//        recycle.addFooterView(footerView);
        recycle.onRefresh();


    }

    /* 请求店铺信息 */
    private void requestHead() {

        new BasePresenter()
                .doRequest("admin/store/getStore", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        Log.i("----------", "onRealSuccess11: ");

                        recycle.selfRefresh(false);
//                        public StoreGsonBean.DataBean.StoreBean store ;
//                        public List<TipNum> tipList  ;
                        StoreGsonBean.DataBean.StoreBean storeBean = gsonBean.getData().store;
                        List<TipNum> tipNums = gsonBean.getData().tipList;


                        initHeadView(headView, storeBean, tipNums);


                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        recycle.addFooterView(footerView);
                    }
                });


    }

    private void initHeadView(View headView, StoreGsonBean.DataBean.StoreBean storeBean, List<TipNum> tipNums) {
        //           <cn.bingoogolapple.badgeview.BGABadgeLinearLayout
//        android:id="@+id/ll_show_num1"

        int head_id = R.layout.item_head_d_new_mp;
        BGABadgeLinearLayout ll_show_num1 = headView.findViewById(R.id.ll_show_num1);
        BGABadgeLinearLayout ll_show_num2 = headView.findViewById(R.id.ll_show_num2);
        BGABadgeLinearLayout ll_show_num3 = headView.findViewById(R.id.ll_show_num3);
        BGABadgeLinearLayout ll_show_num4 = headView.findViewById(R.id.ll_show_num4);

        ll_show_num1.hiddenBadge();
        ll_show_num2.hiddenBadge();
        ll_show_num3.hiddenBadge();
        ll_show_num4.hiddenBadge();


        ll_show_num1.setOnClickListener(v -> FootMarkActivity.start(mActivity));
        ll_show_num2.setOnClickListener(v -> PhoneLogActivity.start(mActivity));
        ll_show_num3.setOnClickListener(v -> MarchingPurchaseActivity.start(mActivity));
        ll_show_num4.setOnClickListener(v -> PendingActivity.start(mActivity, ""));//处理提示


        TextView textView = (TextView) headView.findViewById(R.id.name);
        textView.setText(storeBean.name);

        ImageView logo = (ImageView) headView.findViewById(R.id.logo);
        ImageLoader.getInstance().displayImage(storeBean.logoUrl, logo);

        boolean isPass = storeBean.attrData.store_identity.equals(CompanyIdentityStatus.pass.getEnumValue());

        headView.findViewById(R.id.store_identity).setVisibility(isPass ? View.VISIBLE : View.GONE);
        //通过认证
        TipNumType.setCount(tipNums, headView);
//        setStyleAndText(ll_show_num1, "22");
//        setStyleAndText(ll_show_num3, "");
        // 头部控件初始化   点击事件 由框架注入
        logo = headView.findViewById(R.id.logo);
        btn_xzmp = footerView.findViewById(R.id.btn_xzmp);
        logo.setOnClickListener(this);
        btn_xzmp.setOnClickListener(this);


    }


    /* recycle view  内容    */
    private void doConvert(BaseViewHolder helper, AddressBean item, NeedSwipeBackActivity mActivity) {
        int layout_id = R.layout.item_d_new_mp;


        helper
                .setText(R.id.title, String.format("%s.%s", (helper.getAdapterPosition()), item.name))
                .setText(R.id.textView64, new SpanUtils()
                        .append("在售")
                        .append("(" + item.onShelfJson + ")").setForegroundColor(getColorByRes(R.color.main_color))
                        .append("下架")
                        .append("(" + item.downShelfJson + ")").setForegroundColor(getColorByRes(R.color.main_color))
                        .create())
        ;

        helper.addOnClickListener(R.id.bj, v -> {
            AddAdressActivity.start2Activity(mActivity, item);
        });

        helper.addOnClickListener(R.id.mmgl, v -> {
            ToastUtil.showLongToast("aaa");
            D.i("========苗圃管理==========");
//                    ManagerListActivity_new.start2Activity(mActivity);

            SaveSeedingBottomLinearLayout.addressBean = item;

            ManagerSplitListActivity_new.start2Activity(mActivity, item.id);


        });


    }

    private void requestData() {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<AddressBean>>>>() {
        }.getType();
        new BasePresenter()
                .putParams("storeId", MyApplication.getUserBean().storeId)
                .doRequest("admin/nursery/listByStore", new HandlerAjaxCallBackPage<AddressBean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<AddressBean> addressBeans) {
                        recycle.getAdapter().addData(addressBeans);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                    }
                });


    }

    private View createHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_d_new_mp, null);

        return headView;
    }




    public static void start2Activity(Activity context) {
        Intent intent = new Intent(context, DActivity_new_mp.class);
        context.startActivity(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();

//        StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        View deco = mActivity.getWindow().getDecorView();
//        deco.setBackgroundColor(Color.WHITE);
//        deco.setPadding(0, 50, 0, 0);

//      StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//      StatusBarUtil.setColor(MainActivity.instance, ContextCompat.getColor(mActivity, R.color.main_color));

//        StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
//        StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);
        StateBarUtil.setStatusTranslater(MainActivity.instance, true);
//        StateBarUtil.setStatusTranslater(mActivity, true);
        StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
        StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);

    }

    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }


    @Override
    public String setTitle() {
        return "我的苗圃";
    }


}

