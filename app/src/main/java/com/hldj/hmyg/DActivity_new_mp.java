package com.hldj.hmyg;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.Ui.friend.child.BeReportActivity;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.me.BeAttentionActivity;
import com.hldj.hmyg.me.HandleTipActivity;
import com.hldj.hmyg.saler.AddAdressActivity;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;


/**
 * 收藏夹  界面
 */
public class DActivity_new_mp extends BaseMVPActivity implements View.OnClickListener {

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

        /* 由于注入是在 添加头部之前 。可能会找不到。需要手动 添加一个头部。再injection  不然会导致空指针 →_→  猜测*/
        recycle = (CoreRecyclerView) findViewById(R.id.recycle);
        initCoreRecycleView(recycle);
        View headView = createHeadView();
        recycle.addHeaderView(headView);


//        FinalActivity.initInjectedView(this, recycle);
        FinalActivity.initInjectedView(this);

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
        recycle.getAdapter().addData("first");
        recycle.getAdapter().addData("first");
        recycle.getAdapter().addData("first");
        recycle.getAdapter().addData("first");
        recycle.getAdapter().addData("first");
    }

    private void initCoreRecycleView(CoreRecyclerView recycle) {

        recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_d_new_mp) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

                helper.convertView.setOnClickListener(v -> {
                    ToastUtil.showLongToast("aaa");
                    D.i("========苗圃管理==========");
//                    ManagerListActivity_new.start2Activity(mActivity);
                    ManagerSplitListActivity_new.start2Activity(mActivity);

                });


            }
        });


    }

    private View createHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_d_new_mp, null);


//           <cn.bingoogolapple.badgeview.BGABadgeLinearLayout
//        android:id="@+id/ll_show_num1"
        BGABadgeLinearLayout ll_show_num1 = headView.findViewById(R.id.ll_show_num1);
        BGABadgeLinearLayout ll_show_num2 = headView.findViewById(R.id.ll_show_num2);
        BGABadgeLinearLayout ll_show_num3 = headView.findViewById(R.id.ll_show_num3);
        BGABadgeLinearLayout ll_show_num4 = headView.findViewById(R.id.ll_show_num4);


        ll_show_num1.setOnClickListener(v -> BeAttentionActivity.start(mActivity));
        ll_show_num2.setOnClickListener(v -> BeReportActivity.start(mActivity));
        ll_show_num3.setOnClickListener(v -> BeReportActivity.start(mActivity));
        ll_show_num4.setOnClickListener(v -> HandleTipActivity.start(mActivity));//处理提示

        setStyleAndText(ll_show_num1, "22");
        setStyleAndText(ll_show_num2, "5");
        setStyleAndText(ll_show_num3, "60");
        setStyleAndText(ll_show_num4, "99+");


        setStyleAndText(ll_show_num1, "180");


        // 头部控件初始化   点击事件 由框架注入
        logo = headView.findViewById(R.id.logo);
        btn_xzmp = headView.findViewById(R.id.btn_xzmp);
        logo.setOnClickListener(this);
        btn_xzmp.setOnClickListener(this);
        return headView;
    }

    private void setStyleAndText(BGABadgeLinearLayout ll_show_num, String s) {
        ll_show_num.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        ll_show_num.showTextBadge(s);
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

