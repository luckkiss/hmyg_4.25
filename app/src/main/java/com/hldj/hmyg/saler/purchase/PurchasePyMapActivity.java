package com.hldj.hmyg.saler.purchase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
import com.hldj.hmyg.saler.Adapter.MapSearchAdapter;
import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.PurchasePyMapPresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SegmentedGroup;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

/**
 * 快速报价
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PurchasePyMapActivity extends NeedSwipeBackActivity implements OnCheckedChangeListener, IXListViewListener {


    private XListView listview;

    private int pageSize = 20;
    private int pageIndex = 0;
    private int mTotal = 0;
    private MapSearchAdapter Adapter;
    boolean getdata; // 避免刷新多出数据


    //    private TagView tagView;
//    MaterialDialog mMaterialDialog;


    //    private EditText et_search;
    private RadioButton button31;
    private RadioButton button32;
    private XListView lv;
    private PurchaseListAdapter listAdapter;
    private ArrayList<String> lanmus = new ArrayList<String>();
    String type = "";
    private LinearLayout ll_01;

    private GlobBaseAdapter purchaseBeenad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_py_map);

        SegmentedGroup segmented3 = (SegmentedGroup) findViewById(R.id.segmented3);
        button31 = (RadioButton) findViewById(R.id.button31);
        button32 = (RadioButton) findViewById(R.id.button32);
        ll_01 = (LinearLayout) findViewById(R.id.ll_01);

        lanmus.add("按采购单");
        lanmus.add("按品种");

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        View id_tv_edit_all = findViewById(R.id.id_tv_edit_all);
//        TextView id_tv_edit_all = (TextView) findViewById(R.id.iv_right);
//        id_tv_edit_all.setVisibility(View.GONE);

        listview = (XListView) findViewById(R.id.listview);
        lv = (XListView) findViewById(R.id.lv);
        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
//        listview.setDivider(null);
        lv.setPullLoadEnable(false);
        lv.setPullRefreshEnable(false);
        listview.setXListViewListener(this);
        segmented3.setOnCheckedChangeListener(this);
        button31.setChecked(true);

//         init();
        listAdapter = new PurchaseListAdapter(PurchasePyMapActivity.this, null, R.layout.list_item_purchase_list_new);

        listview.setAdapter(listAdapter);

//        showLoading();
        initData();


        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();

        btn_back.setOnClickListener(multipleClickProcess);
        id_tv_edit_all.setOnClickListener(multipleClickProcess);

    }


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.id_tv_edit_all:
                        D.e("订阅 与 分享");
                        share();
                        break;
                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(200);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行分享方法
     */
    private void share() {
        D.e("分享");
        ComonShareDialogFragment.newInstance()
                .setShareBean(new ComonShareDialogFragment.ShareBean(
                        "花木易购采购单",
                        "花木易购采购订单，百分百真实，等你来报！",
                        "花木易购采购订单，百分百真实，等你来报！",
                        GetServerUrl.ICON_PAHT,
                        "http://m.hmeg.cn/purchase"))
                .show(getSupportFragmentManager(), getClass().getName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 9) {
            onRefresh();
        } else if (resultCode == 8) {
            onRefresh();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
//        listview.setPullLoadEnable(false);
        pageIndex = 0;
        if (listAdapter != null) {
            listAdapter.setState(GlobBaseAdapter.REFRESH);
        }
        if (getdata == true) {
            initData();
        } else {
            hindLoading();
        }

    }

    private void initData() {
        showLoading();
        //初始化监听接口
        ResultCallBack<List<PurchaseBean>> callBack = new ResultCallBack<List<PurchaseBean>>() {
            @Override
            public void onSuccess(List<PurchaseBean> purchaseBeen) {
                listAdapter.addData(purchaseBeen);//返回空 就添加到数组中，并刷新  如果为null  listAdapter 会自动清空
                getdata = true;//成功获取到了
                pageIndex++;
                if (purchaseBeen == null || purchaseBeen.size() == 0) {
                    hidenContent(getView(R.id.listview_loading));
                } else {
                    showContent(getView(R.id.listview_loading));
                }


                Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(delay -> {
                    hindLoading();
                });
                onLoad();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                getdata = true;//获取到了，但是失败了
                onLoad();
                ToastUtil.showShortToast(strMsg);
                Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(delay -> {
                    hindLoading();
                });

            }
        };
        getdata = false;//数据获取到了吗？
        //根据参数请求数据
        new PurchasePyMapPresenter()
                .putParams("pageSize", pageSize + "")
                .putParams("pageIndex", pageIndex + "")
                .putParams("type", type)
                .addResultCallBack(callBack)
                .addOnTotalPageListener(total -> {
                    mTotal = total;
                })
                .requestDatas("purchase/purchaseList");

    }

    @Override
    public void onLoadMore() {
        listview.setPullRefreshEnable(false);
        initData();
        onLoad();
//        ToastUtil.showLongToast("onLoadMore");
    }

    private void onLoad() {
//        ToastUtil.showLongToast("onLoad");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                listview.stopRefresh();

                listview.stopLoadMore();// 10
                if (listAdapter.getDatas().size() % pageSize == 0 && listAdapter.getDatas().size() != mTotal) {
                    listview.setPullLoadEnable(true);
                } else {
                    listview.setPullLoadEnable(false);
                }
                listview.setRefreshTime(new Date().toLocaleString());
                listview.setPullRefreshEnable(true);
            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.button31:

                StorePurchaseListActivity.shouldShow = true;
//                if (listAdapter == null) {
//                    showLoading();
//                    StorePurchaseListActivity.shouldShow = true;
//
////              type = "quoting";
//                    type = "";
//                    onRefresh();
//
//                    new Handler().postDelayed(() -> {
//                        init();
//                    }, 30);
//
//                    showNotice(0);
//                }


                getView(R.id.ll_show_3).setVisibility(View.GONE);
                getView(R.id.ll_show_12).setVisibility(View.VISIBLE);
                break;

            case R.id.button33:

                StorePurchaseListActivity.shouldShow = false;
                if (purchaseBeenad != null) {
                    getView(R.id.ll_show_3).setVisibility(View.VISIBLE);
                    getView(R.id.ll_show_12).setVisibility(View.GONE);
                    return;
                }
                type = "";
                showLoading();

                getView(R.id.ll_show_3).setVisibility(View.VISIBLE);
                getView(R.id.ll_show_12).setVisibility(View.GONE);


                XListView listView3 = getView(R.id.listview_show_3);
//                listview.setPullLoadEnable(true);
//                listview.setPullRefreshEnable(true);
//                listview.setDivider(null);
                lv.setPullLoadEnable(false);
                lv.setPullRefreshEnable(false);
                purchaseBeenad = new PurchaseListAdapter(PurchasePyMapActivity.this, null, R.layout.list_item_purchase_list_new_three);
                listView3.setAdapter(purchaseBeenad);
                //初始化监听接口
                ResultCallBack<List<PurchaseBean>> callBack = new ResultCallBack<List<PurchaseBean>>() {
                    @Override
                    public void onSuccess(List<PurchaseBean> purchaseBeen) {
                        purchaseBeenad.addData(purchaseBeen);

                        if (purchaseBeen != null && purchaseBeen.size() % 10 == 0) {
                            pageIndex3++;
                        }

                        if (purchaseBeen == null || purchaseBeen.size() == 0) {
                            hidenContent(getView(R.id.listview_show_3_loading));
                        } else {
                            showContent(getView(R.id.listview_show_3_loading));
                        }

                        getdata = true;//成功获取到了
                        onLoad3(listView3);
                        hindLoading();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        getdata = true;//获取到了，但是失败了
                        onLoad3(listView3);
                        hindLoading();
                    }
                };


                listView3.setXListViewListener(new IXListViewListener() {
                    @Override
                    public void onRefresh() {
                        showLoading();
                        req3(callBack, 0);
                        pageIndex3 = 0;
                        purchaseBeenad.setState(GlobBaseAdapter.REFRESH);
                        onLoad3(listView3);
                    }


                    @Override
                    public void onLoadMore() {
                        listview.setPullRefreshEnable(false);
                        req3(callBack, pageIndex3);
                        onLoad3(listView3);
                    }
                });


                req3(callBack, pageIndex3);
                getdata = false;//数据获取到了吗？

                break;

            default:
                // Nothing to do
        }
    }

    private void onLoad3(XListView listView3) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (purchaseBeenad == null)
                    return;

                // TODO Auto-generated method stub
                listView3.stopRefresh();

                listView3.stopLoadMore();


                if (purchaseBeenad.getDatas().size() % pageSize == 0) {
                    listView3.setPullLoadEnable(true);
                } else {
                    listView3.setPullLoadEnable(false);
                }


                listView3.setRefreshTime(new Date().toLocaleString());
                listView3.setPullRefreshEnable(true);
            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    public int pageIndex3 = 0;

    public void req3(ResultCallBack<List<PurchaseBean>> callBack, int index) {
        showLoading();
        //根据参数请求数据
        new PurchasePyMapPresenter()
                .putParams("pageSize", 10 + "")
                .putParams("pageIndex", index + "")
                .addResultCallBack(callBack)
                .requestDatas("purchase/historyList");
    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, PurchasePyMapActivity.class));
    }

    public void showContent(LoadingLayout loadingLayout) {
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    public void hidenContent(LoadingLayout loadingLayout) {
        loadingLayout.setStatus(LoadingLayout.Empty);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRefresh();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//      StorePurchaseListActivity.shouldShow = true;
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }
}
