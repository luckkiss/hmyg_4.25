package com.hldj.hmyg.saler.purchase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.Adapter.MapSearchAdapter;
import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.P.PurchasePyMapPresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SegmentedGroup;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.lang.reflect.Type;
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
    private View iv_histtory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_py_map);

        recycle = getView(R.id.recycle);
        sptv_program_do_search = getView(R.id.sptv_program_do_search);

        iv_histtory = getView(R.id.iv_histtory);
        fbqg = getView(R.id.fbqg);
        et_program_serach_text = getView(R.id.et_program_serach_text);
        select_city = getView(R.id.select_city);

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
        iv_histtory.setOnClickListener(multipleClickProcess);
        sptv_program_do_search.setOnClickListener(multipleClickProcess);
        btn_back.setOnClickListener(multipleClickProcess);
        id_tv_edit_all.setOnClickListener(multipleClickProcess);

        initViewPurchaseByUser();


        /**
         *  报价权限判断
         */


//        if (!MyApplication.getUserBean().isQuote) {
        if (!MyApplication.getUserBean().isQuote) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.button33);
            radioButton.setChecked(true);
        }
        if (!isShowLeft) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.button33);
            radioButton.setChecked(true);
        }


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.button32:

                break;
            case R.id.button31:

                StorePurchaseListActivity.shouldShow = true;


                getView(R.id.ll_show_3).setVisibility(View.GONE);
                getView(R.id.ll_show_2).setVisibility(View.GONE);
                getView(R.id.ll_show_12).setVisibility(View.VISIBLE);


                break;

            case R.id.button33:

//                ToastUtil.showShortToast("zhongjian");
//                recycle.onRefresh();
                getView(R.id.ll_show_2).setVisibility(View.VISIBLE);
                getView(R.id.ll_show_3).setVisibility(View.GONE);
                getView(R.id.ll_show_12).setVisibility(View.GONE);

                break;

            default:
                // Nothing to do
        }
    }


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.sptv_program_do_search:
                        sptv_program_do_search(view);
                        break;
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.iv_histtory:


                        PurchaseHistoryActivity.start(mActivity);

                        StorePurchaseListActivity.shouldShow = false;


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
        start2Activity(context, true);
//        context.startActivity(new Intent(context, PurchasePyMapActivity.class));
    }


    public static boolean isShowLeft = true;

    public static void start2Activity(Context context, boolean isLeft) {
        isShowLeft = isLeft;
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


    /*    处理  用户报价内容   */
    CoreRecyclerView recycle;


    ImageView fbqg;

    TextView select_city;

    View sptv_program_do_search;

    EditText et_program_serach_text;

    private String mCityCode = "";


    public void sptv_program_do_search(View view) {
        recycle.onRefresh();
    }

    private String getSearchContent() {
        return et_program_serach_text.getText().toString();
    }


    public void initViewPurchaseByUser() {
//        ToastUtil.showShortToast("初始化底部");
        Log.i(TAG, "初始化底部初始化底部初始化底部初始化底部: ");

        getView(R.id.fbqg).setOnClickListener(v -> {
            ToastUtil.showShortToast("asfda");
        });
        fbqg.setOnClickListener(v -> BuyForUserActivity.发布求购(mActivity));
        et_program_serach_text.setOnEditorActionListener((arg0, arg1, arg2) -> {
            if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                sptv_program_do_search(null);
            }
            return false;
        });

        et_program_serach_text.setHint("请输入品种名称");

        select_city
                .setOnClickListener(v -> {

//                    ComonCityWheelDialogF.instance().addSelectListener((province, city, distrect, cityCode) -> {
////                        ToastUtil.showLongToast(province + " " + city);
//                        mCityCode = cityCode.substring(0, 4);
//                        select_city.setText(province + " " + city);
//                        recycle.onRefresh();
//                    }).show(getSupportFragmentManager(), TAG);


                    CityWheelDialogF.instance()
                            .isShowCity(true)
                            .isShow全国(true)
                            .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                                @Override
                                public void onCitySelect(CityGsonBean.ChildBeans childBeans) {

                                    mCityCode = childBeans.cityCode;
                                    select_city.setText(childBeans.fullName);
                                    recycle.onRefresh();

                                }

                                @Override
                                public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                                }
                            }).show(getSupportFragmentManager(), "SellectActivity2");


                });

        recycle
                .init(new BaseQuickAdapter<UserPurchase, BaseViewHolder>(R.layout.item_buy_for_user) {
                    @Override
                    protected void convert(BaseViewHolder helper, UserPurchase item) {
                        Log.i(TAG, "chenggongconvertconvertconvertconvert: ");

                        helper.convertView.setOnClickListener(v -> PublishForUserDetailActivity.start2Activity(mActivity, item.id, item.ownerId));
                        BuyForUserActivity.doConvert(helper, item, mActivity);

                        if (MyApplication.getUserBean().id.equals(item.ownerId)) {
                            if (GetServerUrl.isTest)
                                helper.setTextColorRes(R.id.title, R.color.main_color);
                            else helper.setTextColorRes(R.id.title, R.color.text_color666);
                        }


                    }
                })
                .openRefresh()
                .openLoadMore(10, page -> {
                    requestData(0);
                });

//        BuyForUserActivity.
//                requestData(0, mCityCode, getSearchContent(), mActivity, recycle);

//        recycle.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//
//
//            }
//        });


        recycle.onRefresh();
    }


    private static final String TAG = "PurchasePyMapActivity";

    /*  test page gsonbean  format */
    public void requestData(int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page + "")
                .putParams("cityCode", mCityCode)
                .putParams(ConstantParams.name, getSearchContent())
                .doRequest("userPurchase/list", true, new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type, UserPurchase.class) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> seedlingBeans) {
                        Log.i(TAG, "onRealSuccess: " + seedlingBeans);
                        Log.i(TAG, "onRealSuccess: " + seedlingBeans);
                        recycle.getAdapter().addData(seedlingBeans);
//                        recycle.setBackgroundColor(Color.RED);
                        Log.i(TAG, "chenggong: ");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                    }
                });
    }





    /*    处理  用户报价内容   */


}
