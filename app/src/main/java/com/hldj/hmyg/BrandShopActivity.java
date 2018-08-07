package com.hldj.hmyg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoscrollview.adapter.ImagePagerAdapter;
import com.autoscrollview.widget.AutoScrollViewPager;
import com.autoscrollview.widget.indicator.CirclePageIndicator;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.AlertUtil;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 * 品牌店铺
 */
public class BrandShopActivity extends BaseMVPActivity implements View.OnClickListener {

    private View headView;
    //    private View footerView;
    private ComonShareDialogFragment.ShareBean shareBean;
    private View head_parent;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_brand_shop;
    }

    @ViewInject(id = R.id.btn_mpgl, click = "onClick")
        /* 动态添加的  view  无法注入 headview  */
            ImageView btn_mpgl;


    //    @ViewInject(id = R.id.logo, click = "onClick")
    /* 动态添加的  view  无法注入 headview  */
    ImageView logo;

    @ViewInject(id = R.id.toolbar, click = "onClick")
    Toolbar toolbar;

//    @ViewInject(id = R.id.djck, click = "onClick")
//    TextView djck;

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mpgl:
                D.i("========苗圃管理==========");
                DActivity_new_mp.start2Activity(mActivity);
                break;


            case R.id.toolbar:
// 更多 商家
                BrandShopMoreActivity.start2Activity(mActivity);


                break;

        }
    }


    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        /* 由于注入是在 添加头部之前 。可能会找不到。需要手动 添加一个头部。再injection  不然会导致空指针 →_→  猜测*/
        initCoreRecycleView();


    }


    @Override
    public void initData() {

    }


    int count = 0;

    private void initCoreRecycleView() {

        int headViewId = R.layout.item_head_d_new_mp;

        recycle.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_brand_shop) {
            @Override
            public void convert(BaseViewHolder helper, AddressBean item) {
//                helper.convertView.setOnClickListener(v -> {
//                    ToastUtil.showLongToast("aaa");
//                    D.i("========苗圃管理==========");
////                    ManagerListActivity_new.start2Activity(mActivity);
//                    ManagerSplitListActivity_new.start2Activity(mActivity);
//                });

                D.i("========品牌店铺 渲染==========" + item.fullAddress);

                doConvert(helper, item, mActivity);


            }
        })

                .openLoadMore(20, page -> {

                    requestData(20, page);


                })


                .openRefresh();


        headView = getLayoutInflater().inflate(R.layout.item_brand_shop_head, null);

        headView.findViewById(R.id.hysj_more)
                .setOnClickListener(v -> {
                    // 更多 商家
                    BrandShopMoreActivity.start2Activity(mActivity);
                });


        List<HashMap<String, Object>> data = new ArrayList<>();

        HashMap hashMap = new HashMap<>();
        hashMap.put("url", "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");
        data.add(hashMap);
        data.add(hashMap);
        data.add(hashMap);
        data.add(hashMap);

        initViewPager(data, headView.findViewById(R.id.view_pager), headView.findViewById(R.id.indicator));


        headView.findViewById(R.id.title_hysj)
                .setOnClickListener(v -> {
                    AlertUtil.showCommonDialog(R.layout.home_tip_show, getSupportFragmentManager(), new AlertUtil.DoConvertView() {
                        @Override
                        public void onConvert(Dialog viewRoot) {
                            TextView title = viewRoot.findViewById(R.id.title);
                            title.setText("活跃商家");
                            TextView content = viewRoot.findViewById(R.id.content);
                            content.setText("推荐展示上月最活跃20家供应商，苗圃需要有已上架商品");
                            viewRoot.findViewById(R.id.close)
                                    .setOnClickListener(v -> {
                                        viewRoot.dismiss();
                                    });

                        }
                    }, true);
                });


        recycle.addHeaderView(headView);
//        recycle.addFooterView(footerView);
        recycle.onRefresh();


    }


    /* recycle view  内容    */
    public static void doConvert(BaseViewHolder helper, AddressBean item, NeedSwipeBackActivity mActivity) {
        int layout_id = R.layout.item_brand_shop;

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_left), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_center), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_right), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        ImageLoader
                .getInstance()
                .displayImage("https://avatar.csdn.net/9/7/A/3_zhangphil.jpg", (ImageView) helper.getView(R.id.head));

        helper.addOnClickListener(R.id.tv_right_top, v -> {
            StoreActivity_new.start2Activity(mActivity, MyApplication.getUserBean().storeId);
        });


    }

    private void requestData(int pageSize, int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<AddressBean>>>>() {
        }.getType();
        new BasePresenter()
                .putParams(ConstantParams.searchKey, "")
                .putParams(ConstantParams.pageIndex, page + "")
                .putParams(ConstantParams.pageSize, "" + pageSize)
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
        Intent intent = new Intent(context, BrandShopActivity.class);
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


    /**
     * 监听登录成功  刷新
     */


    private Disposable subscription;


    private ImagePagerAdapter imagePagerAdapter;


    /**
     * 初始化 顶部
     */
    private void initViewPager(List<HashMap<String, Object>> datas, AutoScrollViewPager viewPager, CirclePageIndicator indicator) {

        if (imagePagerAdapter != null) {
            imagePagerAdapter.notifyDataSetChanged();
            return;
        }
        imagePagerAdapter = new ImagePagerAdapter(mActivity, datas);
        viewPager.setAdapter(imagePagerAdapter);
        indicator.setViewPager(viewPager);
        // imagePagerAdapter.notifyDataSetChanged();
        // indicator.setRadius(5);
        indicator.setOrientation(LinearLayout.HORIZONTAL);
        // indicator.setStrokeWidth(5);
        // indicator.setSnap(true);
        viewPager.setInterval(3500);
        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
        viewPager
                .setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);
        // viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        viewPager.setCycle(true);
        viewPager.setBorderAnimation(true);
        viewPager.startAutoScroll();
    }


}

