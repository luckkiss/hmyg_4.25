package com.hldj.hmyg.buyer.Ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.SortList;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import me.mhao.widget.SlipButton;

import static com.hldj.hmyg.R.id.tv_03;


/**
 * 报价列表
 */
public class StorePurchaseListActivity extends NeedSwipeBackActivity implements IXListViewListener, OnClickListener {
    private XListView xListView;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    boolean getdata; // 避免刷新多出数据
    private StorePurchaseListAdapter_new listAdapter;

    private int pageSize = 20;
    private int pageIndex = 0;
    private View mainView;
//    private DaquyuAdapter daquyuAdapter;
//    private XiaoquyuAdapter xiaoquyuAdapter;

    private String searchKey = "";
    private String name = "";
    private String isSubscribe = "";

    private String firstSeedlingTypeName = "";
    private String secondSeedlingTypeId = "";
    private String secondSeedlingTypeName = "";
    private String publishDateSort = "";
    //    private ArrayList<DaQuYu> daQuYus = new ArrayList<DaQuYu>();
//    private List<XiaoQuYu> xiaoQuYus = new ArrayList<XiaoQuYu>();
    private PopupWindow popupWindow;
    private TextView tv_title;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;

    private ArrayList<SortList> sortLists = new ArrayList<SortList>();
    private SlipButton newMsgAlertStatusOnOff;
    private PopupWindow popupWindow2;

    private Dialog dialog;


    private int subscribeUserCount = 0;

    private String purchaseFormId = "";
    private String title = "";
    //    private TextView tv_01;
//    private TextView tv_02;
//    private TextView tv_03;
//    private TextView tv_04;
//    private TextView tv_05;
//    private TextView tv_06;
    private String quoteDesc = "";

    public String getQuoteDesc() {
        return quoteDesc;
    }

    boolean isFirstLoading = true;

    /**
     * 快速报价
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_purchase_list);
        showLoading();
        View headView = getLayoutInflater().inflate(R.layout.head_purchase, null);


        if (getIntent().getStringExtra("purchaseFormId") != null) {
            purchaseFormId = getIntent().getStringExtra("purchaseFormId");
        }
        if (getIntent().getStringExtra("secondSeedlingTypeId") != null) {
            secondSeedlingTypeId = getIntent().getStringExtra("secondSeedlingTypeId");
            StorePurchaseListAdapter_new.isShow = true;
        } else {
            StorePurchaseListAdapter_new.isShow = false;
        }
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
            tv_title = (TextView) findViewById(R.id.toolbar_title);
            tv_title.setText(title);
        }
        if (getIntent().getStringExtra("searchKey") != null) {

            searchKey = getIntent().getStringExtra("searchKey");


        }

        ImageView btn_back = (ImageView) findViewById(R.id.toolbar_left_icon);


        mainView = findViewById(R.id.mainView);


        initXlistView(headView);

        initData();

        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        btn_back.setOnClickListener(multipleClickProcess);


    }


    private void initXlistView(View inflate) {
        xListView = (XListView) findViewById(R.id.xlistView);
        xListView.setDivider(null);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        if (!"".equals(purchaseFormId)) {
            xListView.addHeaderView(inflate);
        }
    }


    /*三*/
    private void initData() {
        // TODO Auto-generated method stub
        getdata = false;
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("subscribeUserId", MyApplication.Userinfo.getString("id", ""));

        if (MyApplication.Userinfo.getBoolean("isLogin", false)) {
            params.put("userId", MyApplication.Userinfo.getString("id", ""));
        }
        params.put("purchaseId", purchaseFormId);
        params.put("searchKey", searchKey);
        params.put("secondSeedlingTypeId", secondSeedlingTypeId);
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        Log.e("purchase/list", params.toString());
        finalHttp.post(GetServerUrl.getUrl() + "purchase/list", params, new AjaxCallBack<String>() {

            private String cityName = "";
            private boolean is;

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(StorePurchaseListActivity.this, R.string.error_net, Toast.LENGTH_SHORT).show();
                hindLoading();
                getdata = true;
            }

            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                Log.e("purchase/list", t.toString());

                PurchaseListPageGsonBean gsonBean = GsonUtil.formateJson2Bean(t, PurchaseListPageGsonBean.class);

                if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    if (gsonBean.data.page.pageNo == 0) {
                        initHeadBean(gsonBean.data.headPurchase);
                        is = gsonBean.data.expired;
                    }
                    initPageBeans(gsonBean.data.page.data);


                }
                hindLoading();
                onLoad();
                getdata = true;
            }


            private void initPageBeans(List<PurchaseItemBean_new> data) {

                if (listAdapter == null) {
                    listAdapter = new StorePurchaseListAdapter_new(StorePurchaseListActivity.this, data, R.layout.list_item_store_purchase) {
                        @Override
                        public String setCityName() {
                            return cityName;
                        }

                        @Override
                        public Boolean isExpired() {
                            return is;
                        }
                    };
                    xListView.setAdapter(listAdapter);
                } else {
                    listAdapter.addData(data);
                }

                if (listAdapter.getDatas().size() % pageSize == 0) {
                    pageIndex++;
                } else {

                }

                onLoad();

            }

            private void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
                if (headPurchase == null) {
                    return;
                }
                cityName = headPurchase.cityName;

                quoteDesc = headPurchase.quoteDesc;
                /*第一次显示*/
                if (isFirstLoading) {
                    showWebViewDialog(getQuoteDesc());
                    isFirstLoading = false;
                }

                int headViewId = R.layout.head_purchase;
                        /*项目名*/
                String res = headPurchase.blurProjectName + "采购单" + "<font color='#FFA19494'><small>" + "(" + headPurchase.num + ")" + "</small></font>";
                ((TextView) getView(R.id.tv_01)).setText(Html.fromHtml(res));
                        /*显示名称*/
                ((TextView) getView(R.id.tv_02)).setText(headPurchase.buyer.displayName);
                        /*报价说明*/
                ((TextView) getView(tv_03)).setText("报价说明");
                getView(tv_03).setOnClickListener(StorePurchaseListActivity.this);

                        /* 用  苗  地*/
                ((TextView) getView(R.id.tv_04)).setText("用  苗  地：" + headPurchase.cityName);

                        /*联系电话，用于显示*/
                choseOne2Show(getView(R.id.tv_05), headPurchase.dispatchPhone, headPurchase.dispatchName, headPurchase.displayPhone);

                        /*   tv_06.setText("截止时间：" + closeDate);*/
                ((TextView) getView(R.id.tv_06)).setText("用  苗  地：" + headPurchase.closeDate);
            }

            private void choseOne2Show(TextView tv_05, String dispatchPhone, String dispatchName, String displayPhone) {
                if (!"".equals(dispatchPhone) && "".equals(dispatchName)) {
                    tv_05.setText("联系电话：" + dispatchPhone);
                } else if ("".equals(dispatchPhone) && !"".equals(dispatchName)) {
                    tv_05.setText("联系人：" + dispatchName);
                } else if (!"".equals(dispatchPhone) && !"".equals(dispatchName)) {
                    tv_05.setText("联系电话：" + dispatchName + "/" + dispatchPhone);
                } else {
                    tv_05.setText("联系电话：" + displayPhone);
                }
            }


        });
        getdata = true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        D.e("===onActivityResult===" + resultCode);
//        ToastUtil.showShortToast("onActivityResult" + resultCode);
        /*发布成功    删除成功返回 itme  用来更新 列表*/
        if (resultCode == ConstantState.DELETE_SUCCEED || resultCode == ConstantState.PUBLIC_SUCCEED) {
            PurchaseItemBean_new itemBean_new = (PurchaseItemBean_new) data.getSerializableExtra("bean");
//            ToastUtil.showShortToast("toast" + itemBean_new.toString());
            for (int i = 0; i < listAdapter.getDatas().size(); i++) {
                if (listAdapter.getDatas().get(i).id.equals(itemBean_new.id)) {
                    listAdapter.getDatas().set(i, itemBean_new);
                    listAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        if (resultCode == ConstantState.LOGIN_SUCCEED) {
            onRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.toolbar_left_icon:
                        finish();
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


    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        xListView.setPullLoadEnable(false);
        pageIndex = 0;
//        datas.clear();
        if (listAdapter != null) {
            listAdapter.refreshState();
        }
        if (getdata == true) {
            initData();
        }
        onLoad();
    }

    @Override
    public void onLoadMore() {
        xListView.setPullRefreshEnable(false);
        if (getdata == true) {
            initData();
        }
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                xListView.stopRefresh();
                xListView.stopLoadMore();

                if (listAdapter.getDatas().size() % pageSize == 0) {
                    xListView.setPullLoadEnable(true);
                } else {
                    xListView.setPullLoadEnable(false);
                }
                xListView.setRefreshTime(new Date().toLocaleString());
                xListView.setPullRefreshEnable(true);
            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_03:
                showWebViewDialog(getQuoteDesc());
                break;
            default:
                break;
        }
    }

    WebViewDialogFragment webViewDialogFragment;

    private void showWebViewDialog(String quoteDesc) {
        if (TextUtils.isEmpty(quoteDesc)) {
            return;
        }
        try {
            if (StorePurchaseListActivity.this.isFinishing()) {
                return;
            }
            if (webViewDialogFragment == null) {
                webViewDialogFragment = WebViewDialogFragment.newInstance(quoteDesc);
                webViewDialogFragment.show(getSupportFragmentManager(), this.getClass().getName());
            } else {
                webViewDialogFragment.show(getSupportFragmentManager(), this.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            D.e("======e==========" + e.getMessage());
        }


    }

    public void hindlo() {
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> hindLoading());
    }


}
