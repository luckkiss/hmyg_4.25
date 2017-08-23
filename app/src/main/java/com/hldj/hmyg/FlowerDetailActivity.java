package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.application.AlphaTitleScrollView;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.PermissionUtils;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PlatformForShare;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.presenter.CollectPresenter;
import com.hldj.hmyg.saler.SavePriceAndCountAndOutlineActivity;
import com.hldj.hmyg.saler.SaveSeedlingActivity_change_data;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.AutoAdd2DetailLinearLayout;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;
import com.hy.utils.ValueGetInfo;
import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yangfuhai.asimplecachedemo.lib.ACache;
import com.zf.iosdialog.widget.AlertDialog;
import com.zzy.flowers.widget.popwin.EditP2;
import com.zzy.flowers.widget.popwin.EditP3;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import me.drakeet.materialdialog.MaterialDialog;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.util.ConstantState.LOGIN_SUCCEED;


/**
 * 商城详情  苗木详情展示
 */
@SuppressLint({"ResourceAsColor", "Override"})
public class FlowerDetailActivity extends NeedSwipeBackActivity implements PlatformActionListener {
    private AbSlidingPlayView viewPager;
    private FinalBitmap fb;
    private ArrayList<String> banners = new ArrayList<String>();
    private ArrayList<Pic> ossImagePaths = new ArrayList<Pic>();
    private ArrayList<View> allListView;
    private String show_type = "";
    private String id = "";
    private TextView tv_name;
    private TextView tv_no_server_area;
    private TextView tv_price;
    private TextView tv_count;
    private TextView tv_saleCount;
    private TextView tv_unitTypeName;
    private TextView tv_statusName;
    private TextView tv_seedlingNum;
    private TextView tv_closeDate;
    private TextView tv_remarks;
    private LinearLayout ll_to_d3;
    private LinearLayout ll_to_d4;
    private LinearLayout ll_manager_backed;
    private LinearLayout ll_manager_unaudit;
    private LinearLayout ll_manager_unsubmit;
    private LinearLayout ll_manager_published;
    private LinearLayout ll_manager_outline;
    private LinearLayout ll_buy_car;
    private MultipleClickProcess multipleClickProcess;
    private TextView tv_01_01;
    private TextView tv_01_02;
    private TextView tv_02_01;
    private TextView tv_03_01;
    private TextView tv_04_01;
    private TextView tv_04_02;
    private TextView tv_04_03;
    private TextView tv_05_02;
    private TextView tv_05_03;
    private TextView tv_add_car;
    private boolean isOwner;
    private boolean cartExist;
    private String url = "";
    public String et_num;
    public String days;
    private View mainView;
    private int saleCount = 0;
    private int stock = 0;
    //    private KProgressHUD hud;
    private String store_id = "";
    public String displayPhone = "";
    private LinearLayout ll_01;
    private LinearLayout ll_02;
    private TextView tv_01;
    //    private TextView tv_02;
    private ListView lv_00;
//    ArrayList<SeedlingParm> msSeedlingParms = new ArrayList<SeedlingParm>();
//    public ArrayList<paramsData> paramsDatas = new ArrayList<paramsData>();
    private TextView tv_status_01;
    private TextView tv_status_02;
    private TextView tv_status_03;
    private TextView tv_status_04;
    private TextView tv_status_05;
    private TextView tv_store_area;
    private TextView tv_store_name;
    private TextView tv_store_phone;
    private TextView tv_contanct_name;
    private LinearLayout ll_store;
    private LinearLayout ll_bohao;
    private String price = "";
    private Double floorPrice = 0.0;
    private int count = 0;
    private int lastDay = 0;
    private String remarks = "";
    private String name = "";
    private String firstSeedlingTypeId = "";
    private String secondSeedlingTypeId = "";
    private String diameterType = "";
    private String dbhType = "";
    private String plantType = "";
    private String unitType = "";
    private String firstTypeName = "";
    private String secondTypeName = "";
    private String nurseryId = "";
    private String seedlingParams = "";
    private String status;
    private int dbh = 0;
    private int diameter = 0;
    private int crown = 0;
    private int height = 0;
    private int length = 0;
    private int offbarHeight = 0;
    private int turang = 0;
    private int zhixiagao = 0;
    private int validity = 0;
    private ImageView iv_lianxi;
    private LinearLayout ll_data;
    private ImageView sc_ziying;
    private ImageView sc_fuwufugai;
    private ImageView sc_hezuoshangjia;
    private ImageView sc_huodaofukuan;
    private ACache mCache;
    private TextView tv_last_time;
    private String closeDate = "";
    private TextView tv_plant;
    private DisplayImageOptions options;

    private ArrayList<String> urlPaths = new ArrayList<String>();
    MaterialDialog mMaterialDialog;
    private TextView tv_dijia;
    private TextView tv_title;
    private String contactName = "";
    private String contactPhone = "";
    private String address_name = "";
    private Gson gson = new Gson();
    private LinearLayout ll_open;
    private TextView tv_open;
    private ImageView iv_open;
    private MyCount myCount;//倒计时线程
    private SaveSeedingGsonBean saveSeedingGsonBean;
    private String min_price = "";//最小价格
    private String max_price = "";//最小价格
    private boolean isNego;
    private boolean isFirst; //第一次加载
    private SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        /**
         * 设置状态栏 黑色 并且图片置顶
         */
        setar();
        showLoading();

//        hud = KProgressHUD
//                .create(FlowerDetailActivity.this)
//                .setStyle(
//                        KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("努力加载中...").setMaxProgress(100)
//                .setCancellable(true).show();

//		StateBarUtil.setStatusBarIconDark(this,true);
        setContentView(R.layout.activity_flower_detail_test_toobar_3_0);
//		setToolBarAlfa();
        setToolBarAlfaScr();
//
        mMaterialDialog = new MaterialDialog(this);
        fb = FinalBitmap.create(this);
        initShareParams();//初始化分享参数
        mCache = ACache.get(this);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.no_image_big_show)
                .showImageOnFail(R.drawable.no_image_big_show)
                .bitmapConfig(Bitmap.Config.ARGB_8888).cacheOnDisc(true)
                .cacheInMemory(true).build();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        show_type = getIntent().getStringExtra("show_type");
        id = getIntent().getStringExtra("id");
        multipleClickProcess = new MultipleClickProcess();
        mainView = findViewById(R.id.mainView);
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(v -> {
            onBackPressed();
        });

        iv_lianxi = (ImageView) findViewById(R.id.iv_lianxi);
        lv_00 = (ListView) findViewById(R.id.lv_00);
//        lv_00.setDivider(null);
        tv_no_server_area = (TextView) findViewById(R.id.tv_no_server_area);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_plant = (TextView) findViewById(R.id.tv_plant);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_dijia = (TextView) findViewById(R.id.tv_dijia);
        tv_count = (TextView) findViewById(R.id.tv_count);
//        tv_saleCount = (TextView) findViewById(R.id.tv_saleCount);
        tv_unitTypeName = (TextView) findViewById(R.id.tv_unitTypeName);
        tv_statusName = (TextView) findViewById(R.id.tv_statusName);
        tv_seedlingNum = (TextView) findViewById(R.id.tv_seedlingNum);
//        tv_closeDate = (TextView) findViewById(R.id.tv_closeDate);
//        tv_remarks = (TextView) findViewById(R.id.tv_remarks);
//        tv_status_01 = (TextView) findViewById(R.id.tv_status_01);
//        tv_status_02 = (TextView) findViewById(R.id.tv_status_02);
//        tv_status_03 = (TextView) findViewById(R.id.tv_status_03);
//        tv_status_04 = (TextView) findViewById(R.id.tv_status_04);
//        tv_status_05 = (TextView) findViewById(R.id.tv_status_05);
//        sc_ziying = (ImageView) findViewById(R.id.sc_ziying);
//        sc_fuwufugai = (ImageView) findViewById(R.id.sc_fuwufugai);
//        sc_hezuoshangjia = (ImageView) findViewById(R.id.sc_hezuoshangjia);
//        sc_huodaofukuan = (ImageView) findViewById(R.id.sc_huodaofukuan);

        tv_store_area = (TextView) findViewById(R.id.tv_store_area);
        tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        tv_store_phone = (TextView) findViewById(R.id.tv_store_phone);
        tv_contanct_name = (TextView) findViewById(R.id.tv_contanct_name);

//        ll_open = (LinearLayout) findViewById(R.id.ll_open);
//        tv_open = (TextView) findViewById(R.id.tv_open);
//        iv_open = (ImageView) findViewById(R.id.iv_open);

        viewPager = (AbSlidingPlayView) findViewById(R.id.viewPager_menu);
        // 设置播放方式为顺序播放
        viewPager.setPlayType(1);
        // 设置播放间隔时间
        viewPager.setSleepTime(3000);
        RelativeLayout.LayoutParams l_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        WindowManager wm = this.getWindowManager();
        l_params.height = wm.getDefaultDisplay().getWidth();
        viewPager.setLayoutParams(l_params);


//3
        tv_last_time = (TextView) findViewById(R.id.tv_last_time);
//        ll_data = (LinearLayout) findViewById(R.id.ll_data);
        ll_store = (LinearLayout) findViewById(R.id.ll_store);
        ll_bohao = (LinearLayout) findViewById(R.id.ll_bohao);
        ll_to_d3 = (LinearLayout) findViewById(R.id.ll_to_d3);//店铺  跳转
        ll_to_d3.setOnClickListener(multipleClickProcess);
        ll_to_d4 = (LinearLayout) findViewById(R.id.ll_to_d4);
        ll_to_d4.setOnClickListener(multipleClickProcess);

        ll_manager_backed = (LinearLayout) findViewById(R.id.ll_manager_backed);
        tv_01_01 = (TextView) findViewById(R.id.tv_01_01);
        tv_01_02 = (TextView) findViewById(R.id.tv_01_02);
        ll_manager_unaudit = (LinearLayout) findViewById(R.id.ll_manager_unaudit);
        tv_02_01 = (TextView) findViewById(R.id.tv_02_01);
        ll_manager_unsubmit = (LinearLayout) findViewById(R.id.ll_manager_unsubmit);
        tv_03_01 = (TextView) findViewById(R.id.tv_03_01);
        ll_manager_published = (LinearLayout) findViewById(R.id.ll_manager_published);
        tv_04_01 = (TextView) findViewById(R.id.tv_04_01);
        tv_04_02 = (TextView) findViewById(R.id.tv_04_02);
        tv_04_03 = (TextView) findViewById(R.id.tv_04_03);
        ll_manager_outline = (LinearLayout) findViewById(R.id.ll_manager_outline);
        tv_05_02 = (TextView) findViewById(R.id.tv_05_02);
        tv_05_03 = (TextView) findViewById(R.id.tv_05_03);
        tv_add_car = (TextView) findViewById(R.id.tv_add_car);
        ll_buy_car = (LinearLayout) findViewById(R.id.ll_buy_car);
        ll_01 = (LinearLayout) findViewById(R.id.ll_01);
        ll_02 = (LinearLayout) findViewById(R.id.ll_02);
        tv_01 = (TextView) findViewById(R.id.tv_01);
        if ("manage_list".equals(show_type)) {
            url = "admin/";
        } else if ("seedling_list".equals(show_type)) {
        }
        initData();
        visitsCount();

    }

    @Override
    protected void onDestroy() {
        Log.e("onDestroy", "onDestroy: ");
        findViewById(R.id.ll_detail_toolbar).setAlpha(0);

        super.onDestroy();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置状态栏 黑色 并且图片置顶
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarAlpha(0.0f);
            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色


        }


//		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//		{
//			// 透明状态栏
//			getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//		}
    }


    /**
     * 设置状态栏 黑色 并且图片置顶
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setar1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                );
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

//				window.setNavigationBarColor(Color.TRANSPARENT);
            }
//		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//		{
//			// 透明状态栏
//			getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//		}
        }
    }

    public void setToolBarAlfaScr() {
        final AlphaTitleScrollView scroll = (AlphaTitleScrollView) findViewById(R.id.alfa_scroll);
        LinearLayout title = (LinearLayout) findViewById(R.id.ll_detail_toolbar);
        TextView tv_title = getView(R.id.tv_title);
        View head = findViewById(R.id.view_detail_top);
        ImageView btn_back = getView(R.id.btn_back);
        btn_back.setSelected(false);
        scroll.setTitleAndHead(title, head, new AlphaTitleScrollView.OnStateChange() {
            @Override
            public void onShow() {
                tv_title.setVisibility(View.VISIBLE);
                btn_back.setSelected(false);
            }

            @Override
            public void onHiden() {
                tv_title.setVisibility(View.GONE);
                btn_back.setSelected(true);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll.smoothScrollTo(0, 10);
            }
        }, 100);

    }

    @SuppressLint("ResourceAsColor")
    private void initData() {
        // TODO Auto-generated method stub
        ll_manager_backed.setVisibility(View.GONE);
        ll_manager_outline.setVisibility(View.GONE);
        ll_manager_published.setVisibility(View.GONE);
        ll_manager_unaudit.setVisibility(View.GONE);
        ll_manager_unsubmit.setVisibility(View.GONE);
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        if (!url.contains("admin")) {
            params.put("userId", MyApplication.Userinfo.getString("id", ""));
        }
        finalHttp.post(GetServerUrl.getUrl() + url + "seedling/detail", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        if (!FlowerDetailActivity.this.isFinishing()) {

                        }

                    }

                    @Override
                    public void onLoading(long count, long current) {
                        // TODO Auto-generated method stub
                        super.onLoading(count, current);
                    }

                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub


                        D.e("=================");
                        mCache.remove("seedlingdetail" + id);
                        mCache.put("seedlingdetail" + id, t.toString());
                        AcheData(t.toString());
                        super.onSuccess(t);
                        hindLoading();
//                        if (hud != null) {
//                            hud.dismiss();
//                        }
                    }

                    private void AcheData(String t) {

                        // TODO Auto-generated method stub
                        saveSeedingGsonBean = GsonUtil.formateJson2Bean(t, SaveSeedingGsonBean.class);

                        if (!saveSeedingGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                            ToastUtil.showShortToast(saveSeedingGsonBean.getMsg());
                            new Handler().postDelayed(() -> finish(), 2000);
                            return;
                        }
                        seedlingBean = saveSeedingGsonBean.getData().getSeedling();
                        if (seedlingBean.attrData.ziying) {//自营店铺显示发票
                            findViewById(R.id.stv_is_show_piao).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.stv_is_show_piao)).setText(seedlingBean.attrData.ziyingRemarks);
                        } else {
                            findViewById(R.id.stv_is_show_piao).setVisibility(View.GONE);
                        }
                        D.e("================json==============" + t);
                        try {
                            JSONObject jsonObject = new JSONObject(t);
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                            }
                            if ("1".equals(code)) {
//                                msSeedlingParms.clear();
                                banners.clear();
                                JSONObject jsonObject2 = JsonGetInfo
                                        .getJSONObject(JsonGetInfo
                                                .getJSONObject(jsonObject,
                                                        "data"), "seedling");
                                JSONObject store = JsonGetInfo.getJSONObject(
                                        JsonGetInfo.getJSONObject(jsonObject,
                                                "data"), "store");
                                store_id = JsonGetInfo.getJsonString(store,
                                        "id");
                                isOwner = JsonGetInfo.getJsonBoolean(
                                        jsonObject.getJSONObject("data"),
                                        "isOwner");
                                cartExist = JsonGetInfo.getJsonBoolean(
                                        jsonObject.getJSONObject("data"),
                                        "cartExist");
                                if (cartExist == true) {
                                    unAddCart();
                                } else {

                                }
                                JSONArray jsonArray = JsonGetInfo.getJsonArray(
                                        jsonObject2, "imagesJson");
                                banners.clear();
                                ossImagePaths.clear();
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject3 = jsonArray
                                                .getJSONObject(i);
                                        banners.add(JsonGetInfo.getJsonString(
                                                jsonObject3,
                                                "ossAppLargeImagePath"));
                                        ossImagePaths.add(new Pic(JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "id"), false,
                                                JsonGetInfo.getJsonString(
                                                        jsonObject3, "ossUrl"),
                                                0));

                                    }
                                } else {
                                    banners.add(JsonGetInfo.getJsonString(
                                            jsonObject2, "imageUrl"));
                                    ossImagePaths.add(new Pic(JsonGetInfo.getJsonString(jsonObject2, "id"), false, JsonGetInfo.getJsonString(jsonObject2, "imageUrl"), 0));
                                }
                                if (banners.size() > 0) {
                                    initViewPager();
                                }
                                name = JsonGetInfo.getJsonString(jsonObject2,
                                        "name");
                                tv_title.setText(name);
                                firstSeedlingTypeId = JsonGetInfo
                                        .getJsonString(jsonObject2,
                                                "firstSeedlingTypeId");
                                secondSeedlingTypeId = JsonGetInfo
                                        .getJsonString(jsonObject2,
                                                "secondSeedlingTypeId");
                                diameterType = JsonGetInfo.getJsonString(
                                        jsonObject2, "diameterType");
                                dbhType = JsonGetInfo.getJsonString(
                                        jsonObject2, "dbhType");
                                plantType = JsonGetInfo.getJsonString(
                                        jsonObject2, "plantType");
                                unitType = JsonGetInfo.getJsonString(
                                        jsonObject2, "unitType");
                                firstTypeName = JsonGetInfo.getJsonString(
                                        jsonObject2, "firstTypeName");
                                secondTypeName = JsonGetInfo.getJsonString(
                                        jsonObject2, "secondTypeName");
                                nurseryId = JsonGetInfo.getJsonString(
                                        jsonObject2, "nurseryId");
                                seedlingParams = JsonGetInfo.getJsonString(
                                        jsonObject2, "seedlingParams");
                                validity = JsonGetInfo.getJsonInt(jsonObject2,
                                        "validity");


                                floorPrice = JsonGetInfo.getJsonDouble(
                                        jsonObject2, "floorPrice");
                                count = JsonGetInfo.getJsonInt(jsonObject2,
                                        "count");
                                lastDay = JsonGetInfo.getJsonInt(jsonObject2,
                                        "lastDay");
                                saleCount = JsonGetInfo.getJsonInt(jsonObject2,
                                        "saleCount");

                                String unitTypeName = JsonGetInfo
                                        .getJsonString(jsonObject2,
                                                "unitTypeName");
                                String seedlingNum = JsonGetInfo.getJsonString(
                                        jsonObject2, "seedlingNum");
                                uploadDatas.seedlingNum = seedlingNum;
                                status = JsonGetInfo.getJsonString(jsonObject2,
                                        "status");

                                closeDate = JsonGetInfo.getJsonString(
                                        jsonObject2, "closeDate");

                                boolean isCollect = JsonGetInfo.getJsonBoolean(
                                        jsonObject2, "isCollect");
                                findViewById(R.id.iv_shou_can).setSelected(isCollect);

                                if (isCollect) {
                                    ((TextView) getView(R.id.tv_shou_can)).setText("已收藏");
                                } else {
                                    ((TextView) getView(R.id.tv_shou_can)).setText("收藏");
                                }

                                if (floorPrice > 0
                                        && "manage_list".equals(show_type)) {
                                    tv_dijia.setText("底价："
                                            + ValueGetInfo.doubleTrans1(floorPrice));
                                }

//                                tv_saleCount.setText("已售：" + saleCount
//                                        + unitTypeName);

                                {

                                    //单位      元/ 株  盆     颗
                                    tv_unitTypeName.setText("/" + unitTypeName);


                                    if (isLogin()) {//登录显示
                                        //打电话监听
                                        iv_lianxi.setVisibility(View.VISIBLE);
                                        iv_lianxi.setOnClickListener(callPhotoClick);
                                    } else {
                                        //打电话监听
                                        iv_lianxi.setVisibility(View.GONE);
                                    }

                                    //价格
                                    price = JsonGetInfo.getJsonString(jsonObject2, "price");


                                    min_price = JsonGetInfo.getJsonString(jsonObject2, "minPrice");
                                    max_price = JsonGetInfo.getJsonString(jsonObject2, "maxPrice");
                                    isNego = JsonGetInfo.getJsonBoolean(jsonObject2, "isNego");

                                    ProductListAdapter.setPrice(tv_price, max_price, min_price, isNego, tv_unitTypeName);

                                    //库存数量
                                    stock = JsonGetInfo.getJsonInt(jsonObject2, "stock");
                                    tv_count.setText("库存：" + stock);

                                    //商品规格大小
                                    String specText = JsonGetInfo.getJsonString(jsonObject2, "specText");
                                    String type = JsonGetInfo.getJsonString(jsonObject2, "plantTypeName");
                                    tv_seedlingNum.setText("种植类型：" + type);

//                                    tv_closeDate.setText("下架时间：" + closeDate);


                                    if ("manage_list".equals(show_type)) {
                                        setNotingTimeManager(jsonObject2);
                                    } else {
                                        setNotingTime(jsonObject2);
                                    }


                                    remarks = JsonGetInfo.getJsonString(jsonObject2, "remarks");
                                    //备注
                                    if (remarks.length() == 0) {
                                        remarks = "-";
                                        uploadDatas.remarks = remarks;
                                    } else {
                                        remarks = JsonGetInfo.getJsonString(jsonObject2, "remarks");
                                    }
                                    uploadDatas.remarks = remarks;

                                }

                                {

                                    //植物名字
                                    tv_name.setText(JsonGetInfo.getJsonString(jsonObject2, "standardName"));
                                    //植物的品种 小图标
                                    if (plantType.contains("planted")) {
                                        tv_plant.setBackgroundResource(R.drawable.icon_seller_di);
                                    } else if (plantType.contains("transplant")) {
                                        tv_plant.setBackgroundResource(R.drawable.icon_seller_yi);
                                    } else if (plantType.contains("heelin")) {
                                        tv_plant.setBackgroundResource(R.drawable.icon_seller_jia);
                                    } else if (plantType.contains("container")) {
                                        tv_plant.setBackgroundResource(R.drawable.icon_seller_rong);
                                    } else {
                                        tv_plant.setVisibility(View.GONE);
                                    }
                                }


                                if ("manage_list".equals(show_type)) {
                                    iv_lianxi.setVisibility(View.GONE);
                                    tv_statusName.setVisibility(View.VISIBLE);
                                    ll_buy_car.setVisibility(View.GONE);
                                    if ("backed".equals(status)) {
                                        ll_manager_backed.setVisibility(View.VISIBLE);
                                        JSONObject auditLogJson = JsonGetInfo
                                                .getJSONObject(jsonObject2,
                                                        "auditLogJson");
                                        String remarks = JsonGetInfo
                                                .getJsonString(auditLogJson,
                                                        "remarks");

                                        tv_01_01.setText(remarks);
                                        ((ViewGroup) tv_01_01.getParent()).setVisibility(View.VISIBLE);
//                                      tv_01_01.setVisibility(View.VISIBLE);
                                        tv_statusName.setText("");
                                        setMainColorText(tv_statusName, "被退回");


                                    } else if ("unaudit".equals(status)) {
                                        ll_manager_unaudit.setVisibility(View.VISIBLE);
                                        setMainColorText(tv_statusName, "审核中");
                                    } else if ("unsubmit".equals(status)) {
                                        ll_manager_unsubmit.setVisibility(View.VISIBLE);
                                        setMainColorText(tv_statusName, "未提交");
                                    } else if ("published".equals(status)) {
                                        ll_manager_published.setVisibility(View.VISIBLE);
                                        setMainColorText(tv_statusName, "已发布");
                                    } else if ("outline".equals(status)) {
                                        ll_manager_outline.setVisibility(View.VISIBLE);
                                        setMainColorText(tv_statusName, "已下架");
                                    }
                                    tv_01_01.setOnClickListener(multipleClickProcess); // 退回原因
                                    tv_02_01.setOnClickListener(multipleClickProcess); // 撤回
                                    tv_04_01.setOnClickListener(multipleClickProcess); // 下架
                                    tv_04_02.setOnClickListener(multipleClickProcess);// 延期
                                    tv_05_02.setOnClickListener(multipleClickProcess);// 上架

                                    tv_01_02.setOnClickListener(multipleClickProcess);
                                    tv_03_01.setOnClickListener(multipleClickProcess);
                                    tv_04_03.setOnClickListener(multipleClickProcess);
                                    tv_05_03.setOnClickListener(multipleClickProcess);

                                } else if ("seedling_list".equals(show_type)) {//商城界面跳转过来的
                                    tv_statusName.setVisibility(View.VISIBLE);
                                    ll_buy_car.setVisibility(View.VISIBLE);
                                    ll_manager_backed.setVisibility(View.GONE);
                                    ll_manager_unaudit.setVisibility(View.GONE);
                                    ll_manager_unsubmit.setVisibility(View.GONE);
                                    ll_manager_published.setVisibility(View.GONE);
                                    ll_manager_outline.setVisibility(View.GONE);
                                    tv_add_car.setOnClickListener(multipleClickProcess);
                                    ll_buy_car.setOnClickListener(multipleClickProcess);
                                } else {
                                    //什么都不是的时候，隐藏底部
//                                    findViewById(R.id.activity_flower_detail_bottom).setVisibility(View.GONE);
                                    tv_statusName.setVisibility(View.VISIBLE);
                                    ll_buy_car.setVisibility(View.VISIBLE);
                                    ll_manager_backed.setVisibility(View.GONE);
                                    ll_manager_unaudit.setVisibility(View.GONE);
                                    ll_manager_unsubmit.setVisibility(View.GONE);
                                    ll_manager_published.setVisibility(View.GONE);
                                    ll_manager_outline.setVisibility(View.GONE);
                                    tv_add_car.setOnClickListener(multipleClickProcess);
                                    ll_buy_car.setOnClickListener(multipleClickProcess);
                                }

//                                if (!"".equals(seedlingNum)) {
//                                    msSeedlingParms.add(new SeedlingParm(  "商品编号：", seedlingNum));
//                                    uploadDatas.firstTypeName = seedlingNum;
//                                }
//                                if (!"".equals(firstTypeName)) {
//                                    msSeedlingParms.add(new SeedlingParm("分类：",
//                                            firstTypeName));
//                                    uploadDatas.firstTypeName = firstTypeName;
//                                }
                                String plantTypeName = JsonGetInfo  .getJsonString(jsonObject2,  "plantTypeName");
                                // tv_remarks.setText("备注：" + remarks);
//                                tv_remarks.setText("种植类型：" + plantTypeName);
                                // if (!"".equals(firstTypeName)) {
                                // msSeedlingParms.add(new SeedlingParm(
                                // "种植类型：", plantTypeName));
                                // }
                                String fullName = JsonGetInfo.getJsonString(
                                        JsonGetInfo.getJSONObject(jsonObject2,
                                                "ciCity"), "fullName");
                                if ("seedling_list".equals(show_type)) {
                                    tv_statusName.setText(fullName);
//                                    tv_statusName.setTextColor(Color.parseColor("#999999"));
                                }
                                dbh = JsonGetInfo.getJsonInt(jsonObject2, "dbh");

                                diameter = JsonGetInfo.getJsonInt(jsonObject2, "diameter");

                                height = JsonGetInfo.getJsonInt(jsonObject2, "height");
                                // if (height > 0) {
                                // msSeedlingParms.add(new SeedlingParm("高度",
                                // height + "cm"));
                                // }
                                crown = JsonGetInfo.getJsonInt(jsonObject2,
                                        "crown");
                                // if (crown > 0) {
                                // msSeedlingParms.add(new SeedlingParm("冠幅",
                                // crown + "cm"));
                                // }
                                length = JsonGetInfo.getJsonInt(jsonObject2,
                                        "length");
                                // if (length > 0) {
                                // msSeedlingParms.add(new SeedlingParm("长度",
                                // height + "cm"));
                                // }

                                offbarHeight = JsonGetInfo.getJsonInt(
                                        jsonObject2, "offbarHeight");
                                // if (offbarHeight > 0) {
                                // msSeedlingParms.add(new SeedlingParm("脱杆高",
                                // offbarHeight + "cm"));
                                // }

                                turang = JsonGetInfo.getJsonInt(jsonObject2,
                                        "turang");
                                // if (turang > 0) {
                                // msSeedlingParms.add(new SeedlingParm("土球",
                                // turang + "cm"));
                                // }
                                zhixiagao = JsonGetInfo.getJsonInt(jsonObject2,
                                        "zhixiagao");
                                // if (zhixiagao > 0) {
                                // msSeedlingParms.add(new SeedlingParm("枝下高",
                                // zhixiagao + "cm"));
                                // }

                                JSONArray extParamsList = JsonGetInfo
                                        .getJsonArray(jsonObject2,
                                                "extParamsList");
                                JSONArray specList = JsonGetInfo.getJsonArray(
                                        jsonObject2, "specList");
                                uploadDatas.jsonArray = specList;


//                                if (isFirst)
                                ((AutoAdd2DetailLinearLayout) findViewById(R.id.ll_auto_detail)).setDatas(uploadDatas);
//                                isFirst = false;

                                // if (specList.length() > 0) {
                                // for (int i = 0; i < specList.length(); i++) {
                                //
                                // if (!"".equals(JsonGetInfo
                                // .getJsonString(specList
                                // .getJSONObject(i),
                                // "value"))) {
                                // msSeedlingParms.add(new SeedlingParm(
                                // JsonGetInfo.getJsonString(
                                // specList.getJSONObject(i),
                                // "name"),
                                // JsonGetInfo.getJsonString(
                                // specList.getJSONObject(i),
                                // "value")));
                                // }
                                // }
                                // }
                                if (extParamsList.length() > 0) {
//                                    paramsDatas.clear();
                                    for (int i = 0; i < extParamsList.length(); i++) {
                                        if (!"".equals(JsonGetInfo  .getJsonString(extParamsList   .getJSONObject(i),    "value"))) {
//                                            msSeedlingParms.add(new SeedlingParm(    JsonGetInfo.getJsonString(     extParamsList     .getJSONObject(i), "name") + " :",
//                                                    JsonGetInfo.getJsonString(  extParamsList  .getJSONObject(i),   "value")));
                                        }

//                                        paramsData n = new paramsData();
//                                        n.setValue(JsonGetInfo.getJsonString(
//                                                extParamsList.getJSONObject(i),
//                                                "value"));
//                                        n.setCode(JsonGetInfo.getJsonString(
//                                                extParamsList.getJSONObject(i),
//                                                "code"));
//                                        n.setName(JsonGetInfo.getJsonString(
//                                                extParamsList.getJSONObject(i),
//                                                "name"));
//                                        paramsDatas.add(n);
                                    }

                                }
//								if (!"".equals(fullName)) {
//									msSeedlingParms.add(new SeedlingParm("地区：",
//											fullName));
//								}
//                                msSeedlingParms.add(new SeedlingParm("备注：",  remarks));

//                                if (msSeedlingParms.size() > 0) {
//                                    SeedlingParmAdapter myadapter = new SeedlingParmAdapter(
//                                            FlowerDetailActivity.this,
//                                            msSeedlingParms);
//                                    lv_00.setAdapter(myadapter);
//                                }
                                JSONObject ownerJson = JsonGetInfo
                                        .getJSONObject(jsonObject2, "ownerJson");
                                JSONObject coCity = JsonGetInfo.getJSONObject(
                                        ownerJson, "coCity");
                                displayPhone = JsonGetInfo.getJsonString(
                                        ownerJson, "displayPhone");
                                String displayName = JsonGetInfo.getJsonString(
                                        ownerJson, "displayName");
                                String publicName = JsonGetInfo.getJsonString(
                                        ownerJson, "publicName");


                                {//商家信息


                                    JSONObject nurseryJson = JsonGetInfo.getJSONObject(jsonObject2, "nurseryJson");
                                    address_name = JsonGetInfo.getJsonString(nurseryJson, "cityName") + JsonGetInfo.getJsonString(nurseryJson, "detailAddress");


                                    //登录后显示商家信息
                                    if (isLogin()) {
                                        ll_store.setVisibility(View.VISIBLE);
                                        findViewById(R.id.login_to_show).setVisibility(View.GONE);
                                        tv_store_name.setText(displayName);//商家信息    公司
                                        tv_store_area.setText(fullName);// 所在地区
                                        tv_store_phone.setText(displayPhone);//电话
                                        tv_contanct_name.setText(publicName);//联系人
                                    } else {
                                        ll_store.setVisibility(View.GONE);
                                        findViewById(R.id.login_to_show).setVisibility(View.VISIBLE);
                                        findViewById(R.id.login_to_show).setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                toLogin();
                                            }
                                        });
                                    }


//                                    tv_statusName.setText(fullName);
                                }
//								if ("".equals(JsonGetInfo.getJsonString(coCity,
//										"fullName"))) {
//									tv_store_area.setText("所在地区：" + fullName);
//								} else {
//									tv_store_area.setText("所在地区："
//											+ JsonGetInfo.getJsonString(coCity,
//													"fullName"));
//								}
//
//								tv_store_name.setText("公司名称：" + displayName);
//								tv_store_phone.setText("电话：" + displayPhone);
//								tv_contanct_name.setText("联系人：" + publicName);
                                if ("manage_list".equals(show_type)) {
                                    JSONObject nurseryJson = JsonGetInfo
                                            .getJSONObject(jsonObject2,
                                                    "nurseryJson");
                                    address_name = JsonGetInfo.getJsonString(
                                            nurseryJson, "cityName")
                                            + JsonGetInfo.getJsonString(
                                            nurseryJson,
                                            "detailAddress");
                                    contactName = JsonGetInfo.getJsonString(
                                            nurseryJson, "contactName");
                                    contactPhone = JsonGetInfo.getJsonString(
                                            nurseryJson, "contactPhone");
                                    String companyName = JsonGetInfo
                                            .getJsonString(nurseryJson,
                                                    "companyName");

                                    {//商家信息
                                        tv_store_area.setText(address_name);//商家信息    公司
                                        tv_store_name.setText(companyName);// 所在地区
                                        tv_store_phone.setText(contactPhone);//电话
                                        tv_contanct_name.setText(contactName);//联系人
                                    }
//                                    tv_02.setText("苗圃信息");
                                }


                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    private void setNotingTime(JSONObject jsonObject2) {
                        //下架时间
                        String createTime = JsonGetInfo.getJsonString(jsonObject2, "publishDate");
//                        "publishDate" -> "2017-06-20 14:05:21"

                        if (TextUtils.isEmpty(createTime)) {
                            tv_last_time.setVisibility(View.GONE);
                        } else {
                            tv_last_time.setVisibility(View.VISIBLE);
                            tv_last_time.setGravity(Gravity.CENTER_HORIZONTAL);
                            String showTime = createTime.split(" ") != null ? createTime.split(" ")[0] : "";
                            tv_last_time.setText("上架日期:" + showTime);
                        }


                    }

                    private void setNotingTimeManager(JSONObject jsonObject2) {
                        //下架时间
                        long lastTime = JsonGetInfo.getJsonLong(jsonObject2, "lastTime");
                        if (lastTime > 0) {
                            tv_last_time.setVisibility(View.VISIBLE);

                            if (myCount != null) {
                                myCount.cancel();
                                myCount = null;
                            }
                            myCount = new MyCount(lastTime, 100l);
                            myCount.start();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        if (mCache.getAsString("seedlingdetail" + id) != null
                                && !"".equals(mCache
                                .getAsString("seedlingdetail" + id))) {
                            AcheData(mCache.getAsString("seedlingdetail" + id));
                        }
                        Toast.makeText(FlowerDetailActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                        hindLoading();
//                        if (hud != null) {
//                            hud.dismiss();
//                        }
                    }

                });

    }

    private void setMainColorText(TextView tv_statusName, String s) {
        tv_statusName.setText(s);
        tv_statusName.setTextColor(getResources().getColor(R.color.main_color));
    }

    public void unAddCart() {
        tv_add_car.setBackgroundColor(Color.parseColor("#CCCCCC"));
        tv_add_car.setTextColor(Color.parseColor("#FFFFFF"));
        tv_add_car.setText("已加入购物车");
        tv_add_car.setClickable(false);
    }

    private void visitsCount() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("seedlingId", id);
        finalHttp.post(GetServerUrl.getUrl() + "seedling/visitsCount", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

    private void initViewPager() {

        if (allListView != null) {
            for (int i = 0; i < allListView.size(); i++) {
                allListView.remove(i);
            }
            allListView.clear();
            allListView = null;
            viewPager.removeAllViews();
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < banners.size(); i++) {
            // 导入ViewPager的布局
            View view = LayoutInflater.from(this).inflate(R.layout.pic_item,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            // fb.display(imageView, banners.get(i));
            imageView.setScaleType(ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(banners.get(i), imageView);
            allListView.add(view);
        }

        viewPager.addViews(allListView);
        // 开始轮播
        if (!viewPager.isPlaying()) {
            viewPager.startPlay();
        }


        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
            @Override
            public void onClick(int position) {

                GalleryImageActivity.startGalleryImageActivity(
                        FlowerDetailActivity.this, position, ossImagePaths);

                // String[] urls = new String[ossImagePaths.size()];
                // // TODO Auto-generated method stub
                // for (int i = 0; i < ossImagePaths.size(); i++) {
                // urls[i] = ossImagePaths.get(i);
                // }
                // Intent intent = new Intent(FlowerDetailActivity.this,
                // ImagePagerActivity.class);
                // intent.putExtra("image_urls", urls);
                // intent.putExtra("image_index", position); // 从第一张默认
                // intent.putExtra("name", "");
                // startActivity(intent);
                // overridePendingTransition(R.anim.push_left_in,
                // R.anim.push_left_out);

            }
        });
    }

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;
        private EditP2 popwin;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.ll_01:
                        tv_01.setTextColor(getResources().getColor(
                                R.color.main_color));
//                        tv_02.setTextColor(getResources().getColor(
//                                R.color.light_gray));
                        ll_store.setVisibility(View.GONE);
                        lv_00.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ll_02:
                        tv_01.setTextColor(getResources().getColor(
                                R.color.light_gray));
//                        tv_02.setTextColor(getResources().getColor(
//                                R.color.main_color));
                        ll_store.setVisibility(View.VISIBLE);
                        lv_00.setVisibility(View.GONE);
                        break;
                    case R.id.tv_01_01: // 退回原因
                        break;
                    case R.id.tv_01_02: // 修改信息
                        saveSeedling();
                        break;
                    case R.id.tv_03_01: // 修改信息 未提交
                        saveSeedling();
                        break;
                    case R.id.tv_04_03: // 修改信息  --- 已发布
                        savePriceAndCountAndOutline();
                        break;
                    case R.id.tv_05_03: // 修改信息
                        saveSeedling();
                        break;
                    case R.id.tv_02_01: // 撤回
                        doBack();
                        break;
                    case R.id.tv_04_01: // 下架

                        if (mMaterialDialog != null) {
                            mMaterialDialog
                                    .setMessage("确定是否下架该商品？")
                                    // mMaterialDialog.setBackgroundResource(R.drawable.background);
                                    .setPositiveButton(getString(R.string.ok),
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mMaterialDialog.dismiss();
                                                    doOutline();
                                                }
                                            })
                                    .setNegativeButton(getString(R.string.cancle),
                                            new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    mMaterialDialog.dismiss();
                                                }
                                            }).setCanceledOnTouchOutside(true)
                                    // You can change the message anytime.
                                    // mMaterialDialog.setTitle("提示");
                                    .setOnDismissListener(
                                            new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(
                                                        DialogInterface dialog) {
                                                }
                                            }).show();
                        } else {


                        }

                        break;
                    case R.id.tv_04_02: // 延期
                        getExtensionData();
                        break;
                    case R.id.tv_05_02: // 上架
                        savePriceAndCountAndOutline();
                        break;


                    case R.id.ll_to_d3://跳转到店铺
                        if (!"".equals(store_id)) {
//                            Intent toStoreActivity = new Intent(  FlowerDetailActivity.this, StoreActivity.class);
                            StoreActivity_new.start2Activity(mActivity, store_id);
//                            toStoreActivity.putExtra("code", store_id);
//                            startActivity(toStoreActivity);
                        } else {
                            ToastUtil.showShortToast("找不到该店铺");
                        }

                        break;
                    case R.id.ll_to_d4: //收藏

                        D.e("===");

                        if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {
                            Intent toLoginActivity = new Intent(FlowerDetailActivity.this, LoginActivity.class);
                            startActivityForResult(toLoginActivity, LOGIN_SUCCEED);
                            overridePendingTransition(R.anim.slide_in_left,
                                    R.anim.slide_out_right);
                            return;
                        }

                        add2Collect();


                        break;
                    case R.id.tv_add_car://分享
                        if (!isLogin()) {//没登录就直接登录
                            toLogin();
                            return;
                        }
                        share();
//                        if (isOwner == true) {
//                            Toast.makeText(FlowerDetailActivity.this, "自家商品不可购买",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (stock <= 0) {
//                            Toast.makeText(FlowerDetailActivity.this, "库存不足",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (!cartExist) {
//                            popwin = new EditP2(FlowerDetailActivity.this, ""
//                                    + stock, FlowerDetailActivity.this);
//                            popwin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                            popwin.showAtLocation(mainView, Gravity.BOTTOM
//                                    | Gravity.CENTER_HORIZONTAL, 0, 0);
//                        }

                        break;
                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        public void saveSeedling() {
            if (!"".equals(id)) {
//                Intent toSaveSeedlingActivity = new Intent(FlowerDetailActivity.this, SaveSeedlingActivity_change_data.class);
//                Bundle bundleObject = new Bundle();
//                bundleObject.putSerializable(SEEDLING_KEY, saveSeedingGsonBean);
//                toSaveSeedlingActivity.putExtras(bundleObject);
//                startActivityForResult(toSaveSeedlingActivity, 1);

                SaveSeedlingActivity_change_data.start2Activity(FlowerDetailActivity.this, saveSeedingGsonBean, 1, id);

            }
        }


        public void savePriceAndCountAndOutline() {
            if (!"".equals(id)) {
                Intent toSavePriceAndCountAndOutlineActivity = new Intent(
                        FlowerDetailActivity.this,
                        SavePriceAndCountAndOutlineActivity.class);
                toSavePriceAndCountAndOutlineActivity.putExtra("id", id);
                toSavePriceAndCountAndOutlineActivity.putExtra("min_price", min_price);
                toSavePriceAndCountAndOutlineActivity.putExtra("max_price", max_price);
                toSavePriceAndCountAndOutlineActivity.putExtra("isNego", isNego);
//                toSavePriceAndCountAndOutlineActivity.putExtra("floorPrice",
//                        floorPrice);
                toSavePriceAndCountAndOutlineActivity.putExtra("count", count);
                toSavePriceAndCountAndOutlineActivity.putExtra("lastDay",
                        lastDay);
                startActivityForResult(toSavePriceAndCountAndOutlineActivity, 200);
            }
        }

        private void doBack() {
            // TODO Auto-generated method stub
            FinalHttp finalHttp = new FinalHttp();
            GetServerUrl.addHeaders(finalHttp, true);
            AjaxParams params = new AjaxParams();
            params.put("id", id);
            finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/doBack",
                    params, new AjaxCallBack<Object>() {

                        @Override
                        public void onSuccess(Object t) {
                            // TODO Auto-generated method stub
                            try {
                                JSONObject jsonObject = new JSONObject(t
                                        .toString());
                                String code = JsonGetInfo.getJsonString(
                                        jsonObject, "code");
                                String msg = JsonGetInfo.getJsonString(
                                        jsonObject, "msg");
                                if (!"".equals(msg)) {
                                    Toast.makeText(FlowerDetailActivity.this,
                                            msg, Toast.LENGTH_SHORT).show();
                                }
                                if ("1".equals(code)) {

                                    initData();
                                } else {

                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            super.onSuccess(t);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo,
                                              String strMsg) {
                            // TODO Auto-generated method stub
                            Toast.makeText(FlowerDetailActivity.this,
                                    R.string.error_net, Toast.LENGTH_SHORT)
                                    .show();
                            super.onFailure(t, errorNo, strMsg);
                        }

                    });

        }

        private void doOutline() {
            // TODO Auto-generated method stub
            FinalHttp finalHttp = new FinalHttp();
            GetServerUrl.addHeaders(finalHttp, true);
            AjaxParams params = new AjaxParams();
            params.put("id", id);
            finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/doOutline",
                    params, new AjaxCallBack<Object>() {

                        @Override
                        public void onSuccess(Object t) {
                            // TODO Auto-generated method stub
                            try {
                                JSONObject jsonObject = new JSONObject(t
                                        .toString());
                                String code = JsonGetInfo.getJsonString(
                                        jsonObject, "code");
                                String msg = JsonGetInfo.getJsonString(
                                        jsonObject, "msg");
                                if (!"".equals(msg)) {

                                }
                                if ("1".equals(code)) {
                                    Toast.makeText(FlowerDetailActivity.this,
                                            "下架成功", Toast.LENGTH_SHORT).show();
                                    initData();
                                } else {

                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            super.onSuccess(t);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo,
                                              String strMsg) {
                            // TODO Auto-generated method stub
                            Toast.makeText(FlowerDetailActivity.this,
                                    R.string.error_net, Toast.LENGTH_SHORT)
                                    .show();
                            super.onFailure(t, errorNo, strMsg);
                        }

                    });

        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(Data.loading_time);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void share() {
        D.e("============此处执行分享代码===========");
        showDialog();

    }

    private void add2Collect() {

        new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
            @Override
            public void onSuccess(SimpleGsonBean simpleGsonBean) {
                findViewById(R.id.iv_shou_can).setSelected(simpleGsonBean.getData().isCollect());

                if (simpleGsonBean.getData().isCollect()) {
                    ((TextView) getView(R.id.tv_shou_can)).setText("已收藏");
                } else {
                    ((TextView) getView(R.id.tv_shou_can)).setText("收藏");
                }
                LocalBroadcastManager.getInstance(FlowerDetailActivity.this).sendBroadcast(new Intent(ConstantState.COLLECT_REFRESH));

                /**
                 * LocalBroadcastManager.getInstance(Context).sendBroadcast(new Intent("这里放一个action"));
                 */
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }).reqCollect(id);//请求接口


//        D.e("============store_id==========" + store_id);
//
//        FinalHttp finalHttp = new FinalHttp();
//        GetServerUrl.addHeaders(finalHttp, true);
//        AjaxParams params = new AjaxParams();
//        params.put("sourceId", id);
//        params.put("type", "seedling");
//
//
//        hud.show();
//
//
//        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/save", params, new AjaxCallBack<String>() {
//            @Override
//            public void onSuccess(String s) {
////                {"code":"1","msg":"操作成功","data":{"isCollect":false}}
//                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(s, SimpleGsonBean.class);
//                D.e("================");
//                super.onSuccess(s);
//
//                if (simpleGsonBean.code.equals("1")) {
//                    ToastUtil.showShortToast("修改成功");
//
//                    if (simpleGsonBean.getData().isCollect()) {
//                        findViewById(R.id.iv_shou_can).setSelected(true);
//                    } else {
//                        findViewById(R.id.iv_shou_can).setSelected(false);
//                    }
//                } else {
//                    ToastUtil.showShortToast(simpleGsonBean.msg);
//                }
//
//                hud.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                D.e("================");
//                hud.dismiss();
//                ToastUtil.showShortToast("网络超时");
//                super.onFailure(t, errorNo, strMsg);
//            }
//        });


    }

    public void NoInputMethodManager() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(FlowerDetailActivity.this
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        tv_add_car.setTextColor(Color.parseColor("#FFFFFF"));
        tv_add_car.setText("加入购物车");
        tv_add_car.setClickable(true);
    }

    public void addCart() {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null
                && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(FlowerDetailActivity.this
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("seedlingId", id);
        params.put("count", et_num);
        finalHttp.post(GetServerUrl.getUrl() + "admin/cart/add", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {

                            }
                            if ("1".equals(code)) {
                                Toast.makeText(FlowerDetailActivity.this,
                                        "已加入购物车", Toast.LENGTH_SHORT).show();
                                unAddCart();
                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(FlowerDetailActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

    public void getExtensionData() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        finalHttp.post(GetServerUrl.getUrl()
                        + "admin/seedling/getExtensionData", params,
                new AjaxCallBack<Object>() {

                    private int maxDays;
                    private String maxCloseDate;
                    private String closeDate;

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                Toast.makeText(FlowerDetailActivity.this, msg,
                                        Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(code)) {
                                JSONObject jsonObject2 = JsonGetInfo
                                        .getJSONObject(JsonGetInfo
                                                        .getJSONObject(jsonObject,
                                                                "data"),
                                                "extensionData");
                                closeDate = JsonGetInfo.getJsonString(
                                        jsonObject2, "closeDate");
                                maxCloseDate = JsonGetInfo.getJsonString(
                                        jsonObject2, "maxCloseDate");
                                maxDays = JsonGetInfo.getJsonInt(jsonObject2,
                                        "maxDays");


                                if (!"".equals(closeDate)
                                        && !"".equals(maxCloseDate)) {
                                    EditP3 popwin = new EditP3(
                                            FlowerDetailActivity.this, "下架日期："
                                            + closeDate + "可延期至："
                                            + maxCloseDate,
                                            FlowerDetailActivity.this, maxDays);
                                    popwin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                    popwin.showAtLocation(
                                            mainView,
                                            Gravity.BOTTOM
                                                    | Gravity.CENTER_HORIZONTAL,
                                            0, 0);
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
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(FlowerDetailActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

    public void saveExtension() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        params.put("days", days);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/saveExtension",
                params, new AjaxCallBack<Object>() {

                    private int maxDays;
                    private String maxCloseDate;
                    private String closeDate;

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                Toast.makeText(FlowerDetailActivity.this, msg,
                                        Toast.LENGTH_SHORT).show();


                                initData();//刷新界面

                            }
                            if ("1".equals(code)) {
                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(FlowerDetailActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // 同意给与权限 可以再此处调用拍照
                    Log.i("用户同意权限", "user granted the permission!");
                    CallPhone(displayPhone, mActivity);
                } else {

                    // permission denied, boo! Disable the
                    // f用户不同意 可以给一些友好的提示
                    Log.i("用户不同意权限", "user denied the permission!");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static void CallPhone(final String displayPhone, Activity mAct) {
        // TODO Auto-generated method stub
        if (!"".equals(displayPhone)) {
            new AlertDialog(mAct).builder()
                    .setTitle(displayPhone)
                    .setPositiveButton("呼叫", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                    .parse("tel:" + displayPhone));
                            // Intent intent = new Intent(Intent.ACTION_DIAL,
                            // Uri
                            // .parse("tel:" + displayPhone));
                            if (ActivityCompat.checkSelfPermission(mAct, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                return;
                            }
                            mAct.startActivity(intent);
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }

    class MyCount extends AdvancedCountdownTimer {

        public MyCount(long millisInFuture, long countDownInterval) { // 这两个参数在AdvancedCountdownTimer.java中均有(在“构造函数”中).
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_last_time.setVisibility(View.GONE);
        }

        // 更新剩余时间
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            int day = (int) (((millisUntilFinished / 1000) / 3600) / 24);
            int myhour = (int) (((millisUntilFinished / 1000) / 3600) % 24);
            long myminute = ((millisUntilFinished / 1000) - day * 24 * 3600 - myhour * 3600) / 60;
            long mysecond = millisUntilFinished / 1000 - myhour * 3600 - day
                    * 24 * 3600 - myminute * 60;
            if (!"".equals(closeDate)) {
                tv_last_time.setText("距下架还剩下" + day + "天" + myhour + "小时"
                        + myminute + "分钟" + mysecond + "秒");
            }

        }
    }

    public abstract class AdvancedCountdownTimer {
        private static final int MSG_RUN = 1;
        private final long mCountdownInterval;// 定时间隔，以毫秒计
        private long mTotalTime;// 定时时间
        private long mRemainTime;// 剩余时间

        // 构造函数
        public AdvancedCountdownTimer(long millisInFuture,
                                      long countDownInterval) {
            mTotalTime = millisInFuture;
            mCountdownInterval = countDownInterval;
            mRemainTime = millisInFuture;
        }

        // 取消计时
        public final void cancel() {
            mHandler.removeMessages(MSG_RUN);
        }

        // 重新开始计时
        public final void resume() {
            mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
        }

        // 暂停计时
        public final void pause() {
            mHandler.removeMessages(MSG_RUN);
        }

        // 开始计时
        public synchronized final AdvancedCountdownTimer start() {
            if (mRemainTime <= 0) {// 计时结束后返回
                onFinish();
                return this;
            }
            // 设置计时间隔
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN),
                    mCountdownInterval);
            return this;
        }

        public abstract void onTick(long millisUntilFinished, int percent); // 计时中

        public abstract void onFinish();// 计时结束

        // 通过handler更新android UI，显示定时时间
        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                synchronized (AdvancedCountdownTimer.this) {
                    if (msg.what == MSG_RUN) {
                        mRemainTime = mRemainTime - mCountdownInterval;

                        if (mRemainTime <= 0) {
                            onFinish();
                        } else if (mRemainTime < mCountdownInterval) {
                            sendMessageDelayed(obtainMessage(MSG_RUN),
                                    mRemainTime);
                        } else {

                            onTick(mRemainTime,
                                    new Long(100 * (mTotalTime - mRemainTime)
                                            / mTotalTime).intValue());

                            sendMessageDelayed(obtainMessage(MSG_RUN),
                                    mCountdownInterval);
                        }
                    }
                }
            }
        };
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        if (arg1 == LOGIN_SUCCEED) {
            initData();
        } else if (arg1 == ConstantState.PUBLIC_SUCCEED) {//发布成功  刷新界面
            initData();
        } else if (arg1 == ConstantState.CHANGE_DATES) {//修改 发布信息 成功
            initData();
        }
        super.onActivityResult(arg0, arg1, arg2);
    }


    /**
     * 打电话监听
     */
    OnClickListener callPhotoClick = v -> {

        if (isLogin()) {
            boolean requesCallPhonePermissions = new PermissionUtils(FlowerDetailActivity.this).requesCallPhonePermissions(200);
            if (requesCallPhonePermissions) {
                CallPhone(displayPhone, mActivity);
            }
        } else {
            Intent toLoginActivity = new Intent(FlowerDetailActivity.this, LoginActivity.class);
            startActivityForResult(toLoginActivity, 4);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }


    };


    AutoAdd2DetailLinearLayout.UploadDatas uploadDatas = new AutoAdd2DetailLinearLayout.UploadDatas();


    public void toLogin() {

        Intent toLoginActivity = new Intent(FlowerDetailActivity.this, LoginActivity.class);
        startActivityForResult(toLoginActivity, ConstantState.LOGIN_SUCCEED);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public boolean isLogin() {
        return MyApplication.Userinfo.getBoolean("isLogin", false);
    }


    public static void start2Activity(Context activity, String show_type, String id) {
        Intent intent = new Intent(activity, FlowerDetailActivity.class);
        intent.putExtra("show_type", show_type);
        intent.putExtra("id", id);
        activity.startActivity(intent);

    }

    class SharePlatformAdapter extends BaseAdapter {

        @Override
        public boolean areAllItemsEnabled() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return shares.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            View inflate = getLayoutInflater().inflate(
                    R.layout.list_item_share_platform, null);
            inflate.setBackgroundColor(Color.WHITE);
            ImageView iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
            TextView tv_name = (TextView) inflate.findViewById(R.id.tv_name);
            iv_icon.setImageResource(shares.get(position).getPic());
            tv_name.setText(shares.get(position).getName());
            inflate.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (!FlowerDetailActivity.this.isFinishing() && dialog != null
                            && dialog.isShowing()) {
                        dialog.cancel();
                    }

                    if ("WechatMoments".equals(shares.get(position).getEname())) {
                        if (seedlingBean == null) {
                            ToastUtil.showShortToast("分享失败");
                            return;
                        }
                        ShareToWechatMoments();
                    } else if ("Wechat".equals(shares.get(position).getEname())) {
                        if (seedlingBean == null) {
                            ToastUtil.showShortToast("分享失败");
                            return;
                        }
                        ShareToWechat();
                    } else if ("SinaWeibo".equals(shares.get(position)
                            .getEname())) {
                        if (seedlingBean == null) {
                            ToastUtil.showShortToast("分享失败");
                            return;
                        }
                        ShareToSinaWeibo();
                    } else if ("QZone".equals(shares.get(position).getEname())) {
                        if (seedlingBean == null) {
                            ToastUtil.showShortToast("分享失败");
                            return;
                        }
                        ShareToQzone();
                    }

                }
            });
            return inflate;
        }
    }

    public String getShareUrl() {
        String shareUrl = "";
        if (banners.size() != 0) {
            shareUrl = banners.get(0);
        }
        return shareUrl;

    }


    private void ShareToQzone() {
        Platform.ShareParams sp5 = new Platform.ShareParams();

        sp5.setTitle(seedlingBean.getName());
        sp5.setTitleUrl(Data.getSharePlantUrl(seedlingBean.getId())); // 标题的超链接
//        sp5.setTitleUrl(Data.getSharePlantUrl(seedlingBean.getId())); // 标题的超链接
        sp5.setText(seedlingBean.getSpecText());
        sp5.setImageUrl(seedlingBean.getMediumImageUrl());
        sp5.setUrl(Data.getSharePlantUrl(seedlingBean.getId()));
        sp5.setSite(seedlingBean.getSpecText());
        sp5.setSiteUrl(Data.getSharePlantUrl(seedlingBean.getId()));
//        sp5.setSite(getString(R.string.app_name));
//        sp5.setSiteUrl(Data.share);
        Platform qzone = ShareSDK.getPlatform(QQ.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp5);
    }

    private void ShareToSinaWeibo() {
        Platform.ShareParams sp3 = new Platform.ShareParams();
        sp3.setShareType(Platform.SHARE_WEBPAGE);
        sp3.setText(seedlingBean.getSpecText());
        sp3.setImageUrl(seedlingBean.getMediumImageUrl());
        sp3.setTitle(seedlingBean.getName());
        sp3.setUrl(Data.getSharePlantUrl(seedlingBean.getId()));
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp3);


        /**
         *  Platform.ShareParams sp1 = new Platform.ShareParams();
         sp1.setShareType(Platform.SHARE_WEBPAGE);
         sp1.setTitle(seedlingBean.getName());
         sp1.setText(seedlingBean.getSpecText());
         sp1.setImageUrl(seedlingBean.getSmallImageUrl());//小图
         sp1.setUrl(Data.getSharePlantUrl(seedlingBean.getId()));
         Platform Wechat = ShareSDK.getPlatform("Wechat");
         Wechat.setPlatformActionListener(this);
         Wechat.share(sp1);
         */
    }

    /**
     *  if (SHARE_TYPE == 1) {
     ShareParams sp1 = new ShareParams();
     sp1.setShareType(Platform.SHARE_IMAGE);
     sp1.setImagePath(img_path);
     Platform Wechat = ShareSDK.getPlatform("Wechat");
     Wechat.setPlatformActionListener(this);
     Wechat.share(sp1);
     } else if (SHARE_TYPE == 2) {
     ShareParams sp1 = new ShareParams();
     sp1.setShareType(Platform.SHARE_WEBPAGE);
     sp1.setTitle(title);
     sp1.setText(text);
     sp1.setImageUrl(img);
     sp1.setUrl(url);
     sp1.setSiteUrl(url);
     sp1.setImagePath(img_path);
     Platform Wechat = ShareSDK.getPlatform("Wechat");
     Wechat.setPlatformActionListener(this);
     Wechat.share(sp1);
     }
     */

    /**
     * NSString *shareUrl=@"http://m.hmeg.cn/seedling/detail/#{self.plantManageModel.plantManageId}.html";
     * NSString *title=self.plantManageModel.name;
     * NSString *descr=self.plantManageModel.specText;
     * UIImage *image= self.webImageScrollView.firstImageV.image;
     */
    private void ShareToWechat() {
        Platform.ShareParams sp1 = new Platform.ShareParams();
        sp1.setShareType(Platform.SHARE_WEBPAGE);
        sp1.setTitle(seedlingBean.getName());
        sp1.setText(seedlingBean.getSpecText());
        sp1.setImageUrl(seedlingBean.getMediumImageUrl());//小图
        String url = Data.getSharePlantUrl(seedlingBean.getId());
        D.e("===url==" + url);
        sp1.setUrl(url);
        Platform Wechat = ShareSDK.getPlatform("Wechat");
        Wechat.setPlatformActionListener(this);
        Wechat.share(sp1);
    }

    private void ShareToWechatMoments() {
        Platform.ShareParams sp2 = new Platform.ShareParams();
        sp2.setShareType(Platform.SHARE_WEBPAGE);
        sp2.setTitle(seedlingBean.getName());
        sp2.setText(seedlingBean.getSpecText());
        sp2.setImageUrl(seedlingBean.getMediumImageUrl());//小图
        sp2.setUrl(Data.getSharePlantUrl(seedlingBean.getId()));
        Platform Wechat_men = ShareSDK.getPlatform("WechatMoments");
        Wechat_men.setPlatformActionListener(this);
        Wechat_men.share(sp2);
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        // TODO Auto-generated method stub
        Toast.makeText(FlowerDetailActivity.this, "分享出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {
        // TODO Auto-generated method stub
        Toast.makeText(FlowerDetailActivity.this, "分享已取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
        String expName = arg0.getName();
        if ("SinaWeibo".equals(expName)) {
            platform = "3";
        } else if ("QZone".equals(expName)) {
            platform = "4";
        } else if ("Wechat".equals(expName)) {
            platform = "2";
        } else if ("WechatMoments".equals(expName)) {
            platform = "1";
        }

    }

    private Dialog dialog;

    private void showDialog() {
        View dia_choose_share = getLayoutInflater().inflate(
                R.layout.dia_choose_share, null);
        GridView gridView = (GridView) dia_choose_share
                .findViewById(R.id.gridView);
        Button btn_cancle = (Button) dia_choose_share
                .findViewById(R.id.btn_cancle);
        gridView.setAdapter(new SharePlatformAdapter());
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(dia_choose_share, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dia_choose_share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!FlowerDetailActivity.this.isFinishing() && dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    } else {
                        dialog.show();
                    }
                }

            }
        });
        btn_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!FlowerDetailActivity.this.isFinishing() && dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    } else {
                        dialog.show();
                    }
                }

            }
        });

        if (!FlowerDetailActivity.this.isFinishing() && dialog.isShowing()) {
            dialog.cancel();
        } else if (!FlowerDetailActivity.this.isFinishing() && dialog != null
                && !dialog.isShowing()) {
            dialog.show();
        }

    }

    private String platform = "1,2,3,4,5,6,7,8";
    private ArrayList<PlatformForShare> shares = new ArrayList<PlatformForShare>();

    public void initShareParams() {
        if (platform.contains("1")) {
            PlatformForShare platformForShare = new PlatformForShare("朋友圈",
                    "WechatMoments", "1", R.drawable.sns_icon_23);
            shares.add(platformForShare);
        }
        if (platform.contains("2")) {
            PlatformForShare platformForShare = new PlatformForShare("微信好友",
                    "Wechat", "2", R.drawable.sns_icon_22);
            shares.add(platformForShare);
        }
        if (platform.contains("3")) {
            PlatformForShare platformForShare = new PlatformForShare("新浪微博",
                    "SinaWeibo", "3", R.drawable.sns_icon_1);
            shares.add(platformForShare);
        }
        if (platform.contains("4")) {
            PlatformForShare platformForShare = new PlatformForShare("QQ好友",
                    "QZone", "4", R.drawable.sns_icon_24);
            shares.add(platformForShare);
        }
    }


    @Override
    public void onBackPressed() {
        setResult(ConstantState.FLOW_BACK);
        finish();
        super.onBackPressed();
    }

    /**
     * @param context
     * @param id
     */
    public static void start2Activity(Context context, String id) {
        Intent intent = new Intent(context, StoreActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param id
     */
//    public static void start2ActivityForRes(Activity context, String id) {
//        Intent intent = new Intent(context, StoreActivity.class);
//        intent.putExtra("id", id);
//        context.startActivityForResult(intent, 456);
//    }


}
