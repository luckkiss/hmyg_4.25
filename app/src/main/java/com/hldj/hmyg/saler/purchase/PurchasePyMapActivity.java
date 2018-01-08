package com.hldj.hmyg.saler.purchase;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.PinyinComparatorSubscribe;
import com.example.sortlistview.SideBar;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.listener.OnBtnClickL;
import com.hldj.hmyg.BFragment;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
import com.hldj.hmyg.saler.Adapter.MapSearchAdapter;
import com.hldj.hmyg.saler.Adapter.PurchaseListAdapter;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.PurchasePyMapPresenter;
import com.hldj.hmyg.saler.SubscribeManagerListActivity;
import com.hldj.hmyg.saler.bean.ParamsList;
import com.hldj.hmyg.saler.bean.Subscribe;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SegmentedGroup;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.drakeet.materialdialog.MaterialDialog;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

/**
 * 快速报价
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PurchasePyMapActivity extends NeedSwipeBackActivity implements OnCheckedChangeListener, IXListViewListener {

    private ImageView iv_seller_arrow2;
    private ImageView iv_seller_arrow3;
    private XListView listview;

    private ArrayList<Subscribe> datas = new ArrayList<Subscribe>();
    private ArrayList<PurchaseList> puchaseDatas = new ArrayList<PurchaseList>();
    private int pageSize = 10;
    private int pageIndex = 0;
    private MapSearchAdapter Adapter;
    boolean getdata; // 避免刷新多出数据
    private String noteType = "1";
    FinalHttp finalHttp = new FinalHttp();


    //    private TagView tagView;
    MaterialDialog mMaterialDialog;
    private String[] keySort = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparatorSubscribe pinyinComparator;
    private SideBar sideBar;
    //    private EditText et_search;
    private RadioButton button31;
    private RadioButton button32;
    private XListView lv;
    private FrameLayout fl_type;
    private PurchaseListAdapter listAdapter;
    private ArrayList<String> lanmus = new ArrayList<String>();
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;
    String type = "quoting";
    private LinearLayout ll_01;
    private TextView tv_xiaoxitishi;
    private ImageView iv_close;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    private Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_py_map);

        SegmentedGroup segmented3 = (SegmentedGroup) findViewById(R.id.segmented3);
        button31 = (RadioButton) findViewById(R.id.button31);
        button32 = (RadioButton) findViewById(R.id.button32);
        ll_01 = (LinearLayout) findViewById(R.id.ll_01);
        tv_xiaoxitishi = (TextView) findViewById(R.id.tv_xiaoxitishi);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        e = MyApplication.Userinfo.edit();
        setOverflowShowingAlways();
        dm = getResources().getDisplayMetrics();
        lanmus.add("按采购单");
        lanmus.add("按品种");
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(lanmus.size());
        tabs.setViewPager(pager);
        setTabsValue();
        showNotice(0);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparatorSubscribe();
        mMaterialDialog = new MaterialDialog(this);
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        TextView id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);

        fl_type = (FrameLayout) findViewById(R.id.fl_type);

        iv_seller_arrow2 = (ImageView) findViewById(R.id.iv_seller_arrow2);
        iv_seller_arrow3 = (ImageView) findViewById(R.id.iv_seller_arrow3);
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
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        TextView dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = Adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listview.setSelection(position);
                }

            }
        });

        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();

        btn_back.setOnClickListener(multipleClickProcess);
        id_tv_edit_all.setOnClickListener(multipleClickProcess);
//        RelativeLayout2.setOnClickListener(multipleClickProcess);
        iv_close.setOnClickListener(multipleClickProcess);

    }

    private void showNotice(int position) {
        // TODO Auto-generated method stub
        if (0 == position) {
            if (MyApplication.Userinfo.getBoolean("NeedShowquoting", true)) {
                tv_xiaoxitishi.setText("采购中：已经确认采购且即将调苗的采购项目。");
                ll_01.setVisibility(View.GONE);
            } else {
                ll_01.setVisibility(View.GONE);
            }
        } else if (1 == position) {
            if (MyApplication.Userinfo.getBoolean("NeedShowunquote", true)) {
                tv_xiaoxitishi.setText("待采购：已经确认采购但还未确定调苗时间的采购项目。");
                ll_01.setVisibility(View.GONE);
            } else {
                ll_01.setVisibility(View.GONE);
            }
        }
    }

    private void DialogNoti(String string) {
        // TODO Auto-generated method stub

        CommonDialogFragment1.newInstance(new CommonDialogFragment1.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                Dialog dialog = new Dialog(context);
                //添加这一行
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.purchase_tips);
                TextView textView = ((TextView) dialog.findViewById(R.id.tv_pur_content));

                if ("quoting".equals(type)) {
                    StringFormatUtil formatUtil = new StringFormatUtil(context, "[采购中]：已经确认采购且即将调苗的采购项目。", "[采购中]", R.color.red).fillColor();
                    textView.setText(formatUtil.getResult());
                } else if ("unquote".equals(type)) {
                    StringFormatUtil formatUtil = new StringFormatUtil(context, "[待采购]：已经确认采购且即将调苗的采购项目。", "[待采购]", R.color.red).fillColor();
                    textView.setText(formatUtil.getResult());
                }
                dialog.findViewById(R.id.btn_left).setOnClickListener(view -> {
                    if ("quoting".equals(type)) {
                        e.putBoolean("NeedShowquoting", false);
                    } else if ("unquote".equals(type)) {
                        e.putBoolean("NeedShowunquote", false);
                    }
                    e.commit();
                    dialog.cancel();
                    ll_01.setVisibility(View.GONE);
                });
                dialog.findViewById(R.id.btn_right).setOnClickListener(view -> {
                    dialog.cancel();
                    ll_01.setVisibility(View.GONE);
                });
                //[待采购]：已经确认采购且即将调苗的采购项目。


                return dialog;
            }
        }, true)
                .show(getSupportFragmentManager(), getClass().getName());

        final com.flyco.dialog.widget.MaterialDialog dialog = new com.flyco.dialog.widget.MaterialDialog(
                PurchasePyMapActivity.this);
        dialog.title("温馨提示").content(string)
                //
                .btnText("不再提示", "取消")//
//                .showAnim(mBasIn)//
//                .dismissAnim(mBasOut)//
        ;

        dialog.setOnBtnClickL(new OnBtnClickL() {// left btn click listener
            @Override
            public void onBtnClick() {
                if ("quoting".equals(type)) {
                    e.putBoolean("NeedShowquoting", false);
                } else if ("unquote".equals(type)) {
                    e.putBoolean("NeedShowbangwo", false);
                }
                e.commit();
                dialog.dismiss();
                ll_01.setVisibility(View.GONE);
            }
        }, new OnBtnClickL() {// right btn click listener
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                ll_01.setVisibility(View.GONE);

            }
        });

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lanmus.get(position);
        }

        @Override
        public int getCount() {
            return lanmus.size();
        }

        @Override
        public Fragment getItem(int position) {
            BFragment fragment = new BFragment();
            return fragment;

        }
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTabsValue() {

        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setDividerPadding(5);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        tabs.setTextColor(getResources().getColor(R.color.gray));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(getResources().getColor(R.color.main_color));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(getResources().getColor(R.color.main_color));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
        tabs.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                if (arg0 == 0) {
                    fl_type.setVisibility(View.GONE);
                    findViewById(R.id.fl_list).setVisibility(View.VISIBLE);
//                    listview.setVisibility(View.VISIBLE);
                } else {
                    fl_type.setVisibility(View.VISIBLE);
//                    listview.setVisibility(View.GONE);
                    findViewById(R.id.fl_list).setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

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
                    case R.id.iv_close:
                        // TODO Auto-generated method stub
                        DialogNoti("关闭提示");
                        break;


                    case R.id.id_tv_edit_all:
                        D.e("订阅 与 分享");


                        CommonPopupWindow.builder(mActivity)
                                .bindLayoutId(R.layout.popuplayout)
                                .setCovertViewListener(new CommonPopupWindow.OnCovertViewListener() {
                                    @Override
                                    public void covertView(View viewRoot) {
                                        //订阅
                                        viewRoot.findViewById(R.id.pup_subscriber).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                subscriber();

                                            }
                                        });
                                        //分享
                                        viewRoot.findViewById(R.id.pup_show_share).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                share();
                                            }
                                        });
                                    }
                                })
                                .setWidthDp(100)
                                .setHeightDp(115)
                                .build()
                                .showAsDropDown(view);


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

    /**
     * 执行订阅方法
     */
    private void subscriber() {
        D.e("订阅");
        if (MyApplication.Userinfo.getBoolean("isLogin", false) == false) {
            Intent toLoginActivity = new Intent(
                    PurchasePyMapActivity.this, LoginActivity.class);
            startActivityForResult(toLoginActivity, 4);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            Intent toSubscribeManagerListActivity = new Intent(
                    PurchasePyMapActivity.this,
                    SubscribeManagerListActivity.class);
            startActivityForResult(toSubscribeManagerListActivity, 1);
        }
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

    private void init() {
        // TODO Auto-generated method stub
        datas.clear();
        if (Adapter != null) {
            Adapter.notifyDataSetChanged();
        }
        getdata = false;
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
//        params.put("type", type);
        finalHttp.post(GetServerUrl.getUrl() + "purchase/pyMap", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject, "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject, "msg");
                            if (!"".equals(msg)) {
                            }
                            if ("1".equals(code)) {
                                JSONObject data = JsonGetInfo.getJSONObject(jsonObject, "data");
                                JSONObject pyMaps = JsonGetInfo.getJSONObject(data, "pyMaps");
                                for (int r = 0; r < keySort.length; r++) {
                                    JSONArray jsonArray = JsonGetInfo.getJsonArray(pyMaps, keySort[r]);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        Subscribe hMap = new Subscribe();
                                        hMap.setSortLetters(keySort[r]);
                                        hMap.setId(JsonGetInfo.getJsonString(jsonObject2, "id"));
                                        hMap.setCreateBy(JsonGetInfo.getJsonString(jsonObject2, "createBy"));
                                        hMap.setCreateDate(JsonGetInfo.getJsonString(jsonObject2, "createDate"));
                                        hMap.setName(JsonGetInfo.getJsonString(jsonObject2, "name"));
                                        hMap.setAliasName(JsonGetInfo.getJsonString(jsonObject2, "aliasName"));
                                        hMap.setParentId(JsonGetInfo.getJsonString(jsonObject2, "parentId"));
                                        hMap.setParentName(JsonGetInfo.getJsonString(jsonObject2, "parentName"));
                                        hMap.setFullPinyin(JsonGetInfo.getJsonString(jsonObject2, "firstPinyin"));
                                        hMap.setFirstPinyin(JsonGetInfo.getJsonString(jsonObject2, "firstPinyin"));
                                        hMap.setSeedlingParams(JsonGetInfo.getJsonString(jsonObject2, "seedlingParams"));
                                        hMap.setIsTop(JsonGetInfo.getJsonString(jsonObject2, "isTop"));
                                        hMap.setParentSeedlingParams(JsonGetInfo.getJsonString(jsonObject2, "parentSeedlingParams"));
                                        hMap.setSubscribeId(JsonGetInfo.getJsonString(jsonObject2, "subscribeId"));
                                        hMap.setLevel(JsonGetInfo.getJsonInt(jsonObject2, "level"));
                                        hMap.setSort(JsonGetInfo.getJsonInt(jsonObject2, "sort"));
                                        hMap.setCountPurchaseBysubscribeJson(JsonGetInfo.getJsonInt(jsonObject2, "countPurchaseBysubscribeJson"));
                                        ArrayList<String> str_plantTypeLists = new ArrayList<String>();
                                        ArrayList<String> str_plantTypeList_ids_s = new ArrayList<String>();
                                        ArrayList<String> str_qualityTypeLists = new ArrayList<String>();
                                        ArrayList<String> str_qualityTypeList_ids = new ArrayList<String>();
                                        ArrayList<String> str_qualityGradeLists = new ArrayList<String>();
                                        ArrayList<String> str_qualityGradeList_ids = new ArrayList<String>();
                                        ArrayList<ParamsList> paramsLists = new ArrayList<ParamsList>();
                                        JSONArray paramsList = JsonGetInfo.getJsonArray(jsonObject2, "paramsList");
                                        JSONArray qualityTypeList = JsonGetInfo.getJsonArray(jsonObject2, "qualityTypeList");
                                        JSONArray plantTypeList = JsonGetInfo.getJsonArray(jsonObject2, "plantTypeList");
                                        JSONArray qualityGradeList = JsonGetInfo.getJsonArray(jsonObject2, "qualityGradeList");
                                        for (int m = 0; m < paramsList.length(); m++) {

                                            JSONObject jsonObject3 = paramsList.getJSONObject(m);
                                            paramsLists.add(new ParamsList(JsonGetInfo.getJsonString(jsonObject3, "value"), JsonGetInfo.getJsonBoolean(jsonObject3, "required")));
                                        }
                                        for (int o = 0; o < qualityTypeList.length(); o++) {
                                            JSONObject qualityType = qualityTypeList.getJSONObject(o);
                                            str_qualityTypeLists.add(JsonGetInfo.getJsonString(qualityType, "text"));
                                            str_qualityTypeList_ids.add(JsonGetInfo.getJsonString(qualityType, "value"));
                                        }
                                        for (int p = 0; p < plantTypeList.length(); p++) {
                                            JSONObject plantType1 = plantTypeList.getJSONObject(p);
                                            str_plantTypeLists.add(JsonGetInfo.getJsonString(plantType1, "text"));
                                            str_plantTypeList_ids_s.add(JsonGetInfo.getJsonString(plantType1, "value"));
                                        }
                                        for (int q = 0; q < qualityGradeList.length(); q++) {
                                            JSONObject qualityGrade = qualityGradeList.getJSONObject(q);
                                            str_qualityGradeLists.add(JsonGetInfo.getJsonString(qualityGrade, "text"));
                                            str_qualityGradeList_ids.add(JsonGetInfo.getJsonString(qualityGrade, "value"));
                                        }
                                        hMap.setStr_plantTypeLists(str_plantTypeLists);
                                        hMap.setStr_plantTypeList_ids_s(str_plantTypeList_ids_s);
                                        hMap.setStr_qualityGradeLists(str_qualityGradeLists);
                                        hMap.setStr_qualityGradeList_ids(str_qualityGradeList_ids);
                                        hMap.setStr_qualityTypeLists(str_qualityTypeLists);
                                        hMap.setStr_qualityTypeList_ids(str_qualityTypeList_ids);
                                        hMap.setParamsLists(paramsLists);
                                        hMap.setEdit(false);
                                        datas.add(hMap);

                                    }
                                    // pageIndex++;
                                }
                                if (Adapter == null) {
                                    Adapter = new MapSearchAdapter(PurchasePyMapActivity.this, datas);
                                    lv.setAdapter(Adapter);
                                    lv.setOnItemClickListener(new OnItemClickListener() {

                                        @Override
                                        public void onItemClick(
                                                AdapterView<?> arg0, View arg1,
                                                int position, long arg3) {
                                        }
                                    });

                                } else {
                                    Adapter.notifyDataSetChanged();
                                }

                                if (datas == null || datas.size() == 0) {
                                    showContent(getView(R.id.lv_loading));
                                    hidenContent(getView(R.id.lv_loading));
                                } else {
                                    showContent(getView(R.id.lv_loading));
                                }

                                if (datas.size() > 0) {
                                    sideBar.setVisibility(View.VISIBLE);
                                    // 根据a-z进行排序源数据
                                    Collections.sort(datas, pinyinComparator);
                                } else {
                                    sideBar.setVisibility(View.GONE);
                                }

                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                        super.onFailure(t, errorNo, strMsg);
                    }

                });
        getdata = true;
    }

    private void initData() {
        showLoading();
        //初始化监听接口
        ResultCallBack<List<PurchaseBean>> callBack = new ResultCallBack<List<PurchaseBean>>() {
            @Override
            public void onSuccess(List<PurchaseBean> purchaseBeen) {
                listAdapter.addData(purchaseBeen);//返回空 就添加到数组中，并刷新  如果为null  listAdapter 会自动清空
                getdata = true;//成功获取到了
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
                .requestDatas("purchase/purchaseList");

    }

    @Override
    public void onLoadMore() {
        listview.setPullRefreshEnable(false);
        initData();
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                listview.stopRefresh();

                listview.stopLoadMore();
                if (listAdapter.getDatas().size() % pageSize == 0) {
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
                showLoading();
                StorePurchaseListActivity.shouldShow = true;
                type = "quoting";
                onRefresh();
                new Handler().postDelayed(() -> {
                    init();
                }, 30);

                showNotice(0);
                getView(R.id.ll_show_3).setVisibility(View.GONE);
                getView(R.id.ll_show_12).setVisibility(View.VISIBLE);
                break;
            case R.id.button32:
                showLoading();
                StorePurchaseListActivity.shouldShow = true;
                type = "unquote";
                onRefresh();
                new Handler().postDelayed(() -> {
                    init();
                }, 30);
                showNotice(1);
                getView(R.id.ll_show_3).setVisibility(View.GONE);
                getView(R.id.ll_show_12).setVisibility(View.VISIBLE);
                break;

            case R.id.button33:

                showLoading();
                StorePurchaseListActivity.shouldShow = false;
                getView(R.id.ll_show_3).setVisibility(View.VISIBLE);
                getView(R.id.ll_show_12).setVisibility(View.GONE);


                XListView listView = getView(R.id.listview_show_3);
                listview.setPullLoadEnable(true);
                listview.setPullRefreshEnable(true);
                listview.setDivider(null);
                lv.setPullLoadEnable(false);
                lv.setPullRefreshEnable(false);
                GlobBaseAdapter purchaseBeenad = new PurchaseListAdapter(PurchasePyMapActivity.this, null, R.layout.list_item_purchase_list_new);
                listView.setAdapter(purchaseBeenad);
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
                        onLoad3(listView);
                        hindLoading();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        getdata = true;//获取到了，但是失败了
                        onLoad3(listView);
                        hindLoading();
                    }
                };


                listView.setXListViewListener(new IXListViewListener() {
                    @Override
                    public void onRefresh() {
                        showLoading();
                        req3(callBack, 0);
                        pageIndex3 = 0;
                        purchaseBeenad.setState(GlobBaseAdapter.REFRESH);
                        onLoad3(listView);
                    }


                    @Override
                    public void onLoadMore() {
                        listview.setPullRefreshEnable(false);
                        req3(callBack, pageIndex3);
                        onLoad3(listView);
                    }
                });


                req3(callBack, pageIndex3);
                getdata = false;//数据获取到了吗？

                break;

            default:
                // Nothing to do
        }
    }

    private void onLoad3(XListView listview) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                listview.stopRefresh();

                listview.stopLoadMore();
                if (listAdapter.getDatas().size() % pageSize == 0) {
                    listview.setPullLoadEnable(true);
                } else {
                    listview.setPullLoadEnable(false);
                }
                listview.setRefreshTime(new Date().toLocaleString());
                listview.setPullRefreshEnable(true);
            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    public int pageIndex3 = 0;

    public void req3(ResultCallBack<List<PurchaseBean>> callBack, int index) {
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

}
