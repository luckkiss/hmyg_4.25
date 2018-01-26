package com.hldj.hmyg;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.presenter.CollectPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.zf.iosdialog.widget.AlertDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;


/**
 * 收藏夹  界面
 */
public class DActivity_new extends NeedSwipeBackActivity implements IXListViewListener {
    private XListView xlistView_d_new;

    boolean getdata = true; // 避免刷新多出数据

    private int pageSize = 20;
    private int pageIndex = 0;
    private MySwipeAdapter collectAdapter;//收藏列表的  适配器

    //    public static DActivity_new instance;
    //    private KProgressHUD hud;
    private LocalBroadcastReceiver localReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hud = KProgressHUD.create(DActivity_new.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("数据加载中...").setMaxProgress(100).setCancellable(true)
//                .setDimAmount(0f);


        setContentView(R.layout.activity_d_new);
//        instance = this;

//        StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//        StateBarUtil.setStatusBarIconDark(MainActivity.instance, true);
//        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        StatusBarUtil.setColor(MainActivity.instance, Color.GREEN);
//        StateBarUtil.setStatusBarIconDark(MainActivity.instance, true);
//        StateBarUtil.setStatusTranslaterNoFullStatus(MainActivity.instance, true);

        setSwipeBackEnable(false);
        initView();

//        hud.show();
        initData();

        addLocalBrodcast();


    }


//    public static DActivity_new getInstance() {
//
//        if (instance != null) {
//            return instance;
//        } else {
//            return null;
//        }
//
//    }


    List<SaveSeedingGsonBean.DataBean.SeedlingBean> seedlingBeen = new ArrayList<>();

    private void initView() {


        if (getIntent().getExtras() != null) {
            boolean isShow = (boolean) getIntent().getExtras().get(IS_SHOW);
            if (isShow) {
                getView(R.id.iv_back).setVisibility(View.VISIBLE);
                getView(R.id.iv_back).setOnClickListener(v -> finish());
                setSwipeBackEnable(true);
            }
        } else {

        }


        getView(R.id.tv_clear_all).setOnClickListener(v -> {
                    D.e("==============清空收藏夹============");
                    new AlertDialog(this).builder()
                            .setTitle("确定清空所有收藏?")
                            .setPositiveButton("确定删除", v1 -> {
                                new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
                                    @Override
                                    public void onSuccess(SimpleGsonBean simpleGsonBean) {
//                                        pageIndex = 0;
//                                        seedlingBeen.clear();
//                                        collectAdapter.notifyDataSetChanged();
                                        onRefresh();
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                                    }
                                }).reqClearCollect("seedling");
                            }).setNegativeButton("取消", v2 -> {
                    }).show();

                }

        );


        xlistView_d_new = getView(R.id.xlistView_d_new);
//        xlistView_d_new.setDivider(new BitmapDrawable() );
        xlistView_d_new.setPullLoadEnable(true);
        xlistView_d_new.setPullRefreshEnable(true);
        xlistView_d_new.setXListViewListener(this);

        collectAdapter = new MySwipeAdapter(this, seedlingBeen);
        xlistView_d_new.setAdapter(collectAdapter);

    }


//    boolean isRefreshing = false;

    private void initData() {
        /**
         * 2.5.8.1 收藏夹苗木列表
         1，说明
         收藏夹苗木列表返回该用户对应类型的收藏内容
         2，URL
         Post:      /admin/collect/listSeedling
         3，参数
         admin/collect/listSeedling
         */
        // TODO Auto-generated method stub
        showLoading();

        getdata = false;
//        isRefreshing = true;
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/listSeedling", params,
//        finalHttp.post(GetServerUrl.getUrl() + "admin/cart/list", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        CollectGsonBean collectGsonBean = GsonUtil.formateJson2Bean(json, CollectGsonBean.class);
//                        {"code":"1","msg":"操作成功","data":{"page":{"pageNo":1,"pageSize":20,"total":2,"firstResult":20,"maxResults":20}}}
                        D.e("========json=========" + json);
                        if (collectGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                            if (collectGsonBean.data.page.total != 0 && collectGsonBean.data.page.data.size() != 0) {
                                if (isFresh) {
                                    seedlingBeen.clear();
                                }
                                seedlingBeen.addAll(collectGsonBean.data.page.data);
                                collectAdapter.notifyDataSetChanged();
                                D.e("=====pageIndex=======" + pageIndex);
                                getView(R.id.xlistView_d_new).setVisibility(View.VISIBLE);
                                getView(R.id.rl_refresh).setVisibility(View.GONE);

                            } else {
                                getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                                getView(R.id.rl_refresh).setVisibility(View.VISIBLE);
                            }


                        } else {
                            getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                            getView(R.id.rl_refresh).setVisibility(View.VISIBLE);

                            D.e("===数据库空空如也====");
                        }

//                        hud.dismiss();
//                        isRefreshing = false;

                        hindLoading();
                        pageIndex = seedlingBeen.size() / 20;
                        getdata = true;
                        getView(R.id.rl_refresh).setOnClickListener(refresh);

                        D.e("===============collectGsonBean================" + collectGsonBean);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(DActivity_new.this, R.string.error_net,
                                Toast.LENGTH_SHORT).show();
                        getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                        getView(R.id.rl_refresh).setVisibility(View.VISIBLE);
                        getView(R.id.rl_refresh).setOnClickListener(refresh);
//                        hud.dismiss();
//                        isRefreshing = false;
                        hindLoading();
                        getdata = true;
                        isFresh = false;
                        super.onFailure(t, errorNo, strMsg);
                        getView(R.id.rl_refresh).setOnClickListener(refresh);
                    }

                });

    }


    public boolean isFresh = false;

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        xlistView_d_new.setPullLoadEnable(false);
        pageIndex = 0;
        isFresh = true;
//        datas.clear();
//        listAdapter.notifyDataSetChanged();
        if (getdata == true) {
            initData();
        }
        onLoad();

    }


    public void addLocalBrodcast() {
        localReceiver = new LocalBroadcastReceiver();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ConstantState.COLLECT_REFRESH);

        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }


    @Override
    public void onLoadMore() {
        xlistView_d_new.setPullRefreshEnable(false);
        //判断是否超过一页。。么有的话刷新
        if (seedlingBeen.size() % 20 == 0) {
            initData();
        }
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                xlistView_d_new.stopRefresh();
                xlistView_d_new.stopLoadMore();
                xlistView_d_new.setRefreshTime(new Date().toLocaleString());
                xlistView_d_new.setPullLoadEnable(true);
                xlistView_d_new.setPullRefreshEnable(true);

            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }


    class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override

        public void onReceive(Context context, Intent intent) {


            onRefresh();
//            seedlingBeen.clear();
//            pageIndex = 0;
//            initData();
            D.e("==refresh===");

        }

    }


    View.OnClickListener refresh = v -> {
        onRefresh();
    };


    protected static final String IS_SHOW = "isShow";

    public static void start2Activity(Activity context, boolean isShowBack) {
        Intent intent = new Intent(context, DActivity_new.class);
        intent.putExtra(IS_SHOW, isShowBack);
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


    }



}

