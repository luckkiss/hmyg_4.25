package com.hldj.hmyg.buyer.Ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
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

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.SortList;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SharePopupWindow;
import com.hy.utils.GetServerUrl;
import com.weavey.loading.lib.LoadingLayout;

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
    private static final String TAG = "StorePurchaseListActivi";
    private XListView xListView;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    boolean getdata; // 避免刷新多出数据
    private StorePurchaseListAdapter_new listAdapter;

    public static boolean shouldShow = true;

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
    private String companyInfo = "";

    public String getCompanyInfo() {
        return companyInfo;
    }

    public String getQuoteDesc() {
        return quoteDesc;
    }

    boolean isFirstLoading = true;

    private LoadingLayout loadingLayout;

    /**
     * 快速报价
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_purchase_list);


        loadingLayout = (LoadingLayout) findViewById(R.id.loading_spa);
        loadingLayout.setOnReloadListener(v -> {
            onRefresh();
        });
        showLoading();
        View headView = getLayoutInflater().inflate(R.layout.head_purchase, null);


        if (getIntent().getStringExtra("purchaseFormId") != null) {
            purchaseFormId = getIntent().getStringExtra("purchaseFormId");
            shareBean.pageUrl = "http://m.hmeg.cn/purchase/order/list/" + purchaseFormId + ".html ";
//            "http://m.hmeg.cn/purchase/order/list/f945f5ba092046b58b4f50682b2c9978.html"
        }
        if (getIntent().getStringExtra("secondSeedlingTypeId") != null) {
            secondSeedlingTypeId = getIntent().getStringExtra("secondSeedlingTypeId");

            StorePurchaseListAdapter_new.isShow = true;//表示   不可分享
        } else {
            StorePurchaseListAdapter_new.isShow = false;//表示可以分享
        }

        /**
         * 展示分享按钮  通过条件判断是否显示
         */
        showToolbarRightText(!StorePurchaseListAdapter_new.isShow);

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
        showLoading();
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
                loadingLayout.setStatus(LoadingLayout.No_Network);
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
                    loadingLayout.setStatus(LoadingLayout.Success);

                } else {
                    loadingLayout.setErrorText(gsonBean.msg);
                    loadingLayout.setStatus(LoadingLayout.Error);
                }


                hindLoading();
                onLoad();
                getdata = true;
            }


            private void initPageBeans(List<PurchaseItemBean_new> data) {

                for (int i = 0; i < data.size(); i++) {
                    shareBean.text += data.get(i).name + ",";
                }

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
                if (isFirstLoading && shouldShow) {
                    showWebViewDialog(getQuoteDesc(), "报价说明");
                    isFirstLoading = false;
                }

                int headViewId = R.layout.head_purchase;
                        /*项目名*/
                String res = headPurchase.blurProjectName + "采购单" + "<font color='#FFA19494'><small>" + "(" + headPurchase.num + ")" + "</small></font>";
                shareBean.title = headPurchase.blurProjectName + "【" + headPurchase.num + "】";

                ((TextView) getView(R.id.tv_01)).setText(Html.fromHtml(res));
                        /*显示名称*/
                ((TextView) getView(R.id.tv_02)).setText(headPurchase.buyer.displayName);
                        /*报价说明*/
                ((TextView) getView(R.id.tv_021)).setText(headPurchase.consumerFullName);
                        /*用苗单位*/

                getView(R.id.ll_content_company_info).setVisibility(headPurchase.showConsumerName ? View.VISIBLE : View.GONE);

                companyInfo = headPurchase.attrData.consumerRemarks;
                        /* 单位信息*/


                ((TextView) getView(tv_03)).setText("报价说明");
                getView(tv_03).setOnClickListener(StorePurchaseListActivity.this);
                getView(R.id.tv_ymdw).setOnClickListener(StorePurchaseListActivity.this);

                        /* 用  苗  地*/
                ((TextView) getView(R.id.tv_04)).setText("用  苗  地：" + headPurchase.cityName);

                        /*联系人*/

//                choseOne2Show(getView(R.id.tv_05), headPurchase.dispatchPhone, headPurchase.dispatchName, headPurchase.displayPhone);
                ((TextView) getView(R.id.tv_050)).setText("联系人:  " + headPurchase.dispatchName);

                  /*联系电话*/
                TextView tv_05 = getView(R.id.tv_05);
                String phoneNum = !TextUtils.isEmpty(headPurchase.dispatchPhone) ? headPurchase.dispatchPhone : headPurchase.dispatchPhone;

                tv_05.setText(phoneNum);
                tv_05.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tv_05.getPaint().setAntiAlias(true);//抗锯齿
                tv_05.setOnClickListener(v -> FlowerDetailActivity.CallPhone(phoneNum, mActivity));

//                choseOne2Show(getView(R.id.tv_05), headPurchase.dispatchPhone, headPurchase.dispatchName, headPurchase.displayPhone);


                        /*   tv_06.setText("截止时间：" + closeDate);*/
//                ((TextView) getView(R.id.tv_06)).setText("用  苗  地：" + headPurchase.closeDate);
                ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.closeDate);

                shareBean.text = "用  苗  地：" + headPurchase.cityName + "\n" + "截止时间：" + headPurchase.closeDate + "\n";

            }

            private void choseOne2Show(TextView tv_05, String dispatchPhone, String dispatchName, String displayPhone) {
                if (!"".equals(dispatchPhone) && "".equals(dispatchName)) {
                    tv_05.setText(dispatchPhone);
                    setText(getView(R.id.tv_050), "联系电话：");
                } else if ("".equals(dispatchPhone) && !"".equals(dispatchName)) {
                    tv_05.setText(dispatchName);
                    setText(getView(R.id.tv_050), "联系人：");
                } else if (!"".equals(dispatchPhone) && !"".equals(dispatchName)) {
                    tv_05.setText(dispatchName + "/" + dispatchPhone);
                    setText(getView(R.id.tv_050), "联系电话：");
                } else {
                    tv_05.setText(displayPhone);
                    setText(getView(R.id.tv_050), "联系电话：");
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
            try {
                for (int i = 0; i < listAdapter.getDatas().size(); i++) {
                    if (listAdapter.getDatas().get(i).id.equals(itemBean_new.id)) {
                        listAdapter.getDatas().set(i, itemBean_new);
                        listAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            } catch (Exception e) {
                onRefresh();
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

        if (listAdapter != null)
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
                showWebViewDialog(getQuoteDesc(), "报价说明");
                break;
            case R.id.tv_ymdw:
                showWebViewDialog(getCompanyInfo(), "单位信息");
            default:
                break;
        }
    }

    WebViewDialogFragment webViewDialogFragment;

    private void showWebViewDialog(String quoteDesc, String title) {
        if (TextUtils.isEmpty(quoteDesc)) {
            return;
        }
        try {
            if (StorePurchaseListActivity.this.isFinishing()) {
                return;
            }
//            if (webViewDialogFragment == null) {
            webViewDialogFragment = WebViewDialogFragment.newInstance(quoteDesc).setTitle(title);
            webViewDialogFragment.show(getSupportFragmentManager(), this.getClass().getName());
//            } else {
//                webViewDialogFragment.setContent(quoteDesc);
//                webViewDialogFragment.show(getSupportFragmentManager(), this.getClass().getName());
//            }
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


    private void showToolbarRightText(boolean isShow) {
        ImageView imageView = getView(R.id.toolbar_right_icon);
        if (isShow) {
            imageView.setImageResource(R.drawable.fenxiang);
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(v -> {
                D.e("采购单 分享");
                SharePopupWindow window = new SharePopupWindow(mActivity, constructionShareBean());
                window.showAsDropDown(imageView);
            });
        }
    }


    /**
     * 默认的分享
     */
    ComonShareDialogFragment.ShareBean shareBean = new ComonShareDialogFragment.ShareBean(
            "采购单分享",
            "",
            "",
            "" + GetServerUrl.ICON_PAHT,
            "http://m.hmeg.cn/purchase/order/list/f945f5ba092046b58b4f50682b2c9978.html"
    );

    /**
     * 构造一个分享的bean
     *
     * @return
     */
    private ComonShareDialogFragment.ShareBean constructionShareBean() {

        return shareBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        try {
            if (webViewDialogFragment != null) {
                webViewDialogFragment.dismiss();
                webViewDialogFragment = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
