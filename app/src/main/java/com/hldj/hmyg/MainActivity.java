package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.friend.FriendCycleActivity;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.PermissionUtils;
import com.hldj.hmyg.bean.UserInfoGsonBean;
import com.hldj.hmyg.presenter.LoginPresenter;
import com.hldj.hmyg.update.UpdateDialog;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.LoginUtil;
import com.hldj.hmyg.util.MLocationManager;
import com.hldj.hmyg.util.MyUtil;
import com.hldj.hmyg.util.StartBarUtils;
import com.hldj.hmyg.widget.MainRadioButton;
import com.tencent.bugly.crashreport.CrashReport;
import com.white.update.UpdateInfo;
import com.white.utils.SettingUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.drakeet.materialdialog.MaterialDialog;


@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener {
    private static TabHost tabHost;
    private static RadioGroup radioderGroup;
    MaterialDialog mMaterialDialog;

    // 更新版本要用到的一些信息
    private ProgressDialog progressDialog;
    private UpdateInfo updateInfo;


    public static MainRadioButton radio_d;
    public static MainRadioButton radio_e;


    public Editor e;


    private View friend_view;
    public static View.OnClickListener clicks;


    long mLastTime;
    long mCurTime;

    public String check = "1";

    public void aaa() {
        friend_view = findViewById(R.id.iv_publish);
        friend_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                if (mCurTime - mLastTime < 500) {
//                    Toast.makeText(MainActivity.this, "这就是传说中的双击事件", Toast.LENGTH_LONG).show();
                    if (clicks != null) {
                        Log.i(TAG, "onClick: 触发回调");
                        clicks.onClick(v);
                    }
                }
                Log.i(TAG, "onClick: 点击了苗木圈");
            }
        });
    }

    ;


    public static MainActivity instance;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        try {
            if (savedInstanceState != null) {
                updateInfo = (UpdateInfo) savedInstanceState.getSerializable("updateInfo");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }


//        String str = null ;
//        D.e("======="+ str.toString() );
        //补丁修改
//        ToastUtil.showShortToast("13次修复混淆问题   热更新生效 \n   修改搜索 筛选 的内容");

        /**
         * 控制状态栏为黑色  miui flyme
         */
        StartBarUtils.FlymeSetStatusBarLightMode(getWindow(), true);
        StartBarUtils.MIUISetStatusBarLightMode(getWindow(), true);
        setContentView(R.layout.activity_main);

        aaa();


        getAddr();

        e = MyApplication.Userinfo.edit();//初始化 sp


        Data.screenWidth = MyUtil.getScreenWidth(false, MainActivity.this);

        if (Build.VERSION.SDK_INT >= 23 && MyApplication.getInstance().getApplicationInfo().targetSdkVersion >= 23) {
            new PermissionUtils(this).needPermission(200);
        }
//         StatusBarCompat.compat(this);// 状态栏
//         StateBarUtil.setStatusBarIconDark(this,true);
        mMaterialDialog = new MaterialDialog(this);


        tabHost = this.getTabHost();


        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1")
//                .setContent(new Intent(this, BActivity_new_test.class)));
                .setContent(new Intent(this, AActivity_3_0.class)));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2")
                .setContent(new Intent(this, BActivity_new_test.class)));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3")
                .setContent(new Intent(this, FriendCycleActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4")
                .setContent(new Intent(this, DActivity_new_mp.class)));//跳转到收藏夹  界面
        tabHost.addTab(tabHost.newTabSpec("5").setIndicator("5")
                .setContent(new Intent(this, Eactivity3_0.class)));
        radioderGroup = (RadioGroup) findViewById(R.id.rg_tab);
//        RadioButton tab_c = (RadioButton) findViewById(R.id.tab_c);


        radioderGroup.setOnCheckedChangeListener(this);

        radio_d = findViewById(R.id.tab_d);
        radio_e = findViewById(R.id.tab_e);

        radioderGroup.check(R.id.tab_a);// 默认第一个按钮
        check = "1";
        // getUpDateInfo();
        // getUpDateInfo4Pgyer();
//      getVersion();

        if (getIntent().getScheme() != null
                && getIntent().getDataString() != null) {
            String scheme = this.getIntent().getScheme();// 获得Scheme名称
            String sche_data = getIntent().getDataString();// 获得Uri全部路径
            Log.e("onCreate", scheme + sche_data);
            // hldjhmeg+hldjhmeg://store?code=longlongago
            // hldjhmeg+hldjhmeg://mallDetailed?id=331561d94cee4017a5067c94f83203af
            if ("hldjhmeg".equals(scheme)) {
                if (sche_data.contains("hldjhmeg://store?id=")) {
                    Intent toStoreActivity = new Intent(MainActivity.this,
                            StoreActivity.class);
                    toStoreActivity.putExtra("id",
                            sche_data.replace("hldjhmeg://store?id=", ""));
                    startActivity(toStoreActivity);
                } else if (sche_data.contains("hldjhmeg://mallDetailed?id=")) {
                    Intent toFlowerDetailActivity = new Intent(
                            MainActivity.this, FlowerDetailActivity.class);
                    toFlowerDetailActivity.putExtra("id", sche_data.replace(
                            "hldjhmeg://mallDetailed?id=", ""));
                    startActivity(toFlowerDetailActivity);
                    overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_right);
                } else if (sche_data.contains("hldjhmeg://mall")) {
                    radioderGroup.check(R.id.tab_b);
                }
            }
        }

        requestUserInfo();


        JPushInterface.setAlias(MainActivity.this, MyApplication.Userinfo
                .getString("id", "").replace("-", "").toLowerCase().trim()
                .toString(), new TagAliasCallback() {
            @Override
            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                // TODO Auto-generated method stub
                Log.w(TAG, "gotResult: " + arg0);
                Log.w(TAG, "gotResult: " + arg1);
                Log.w(TAG, "gotResult: " + arg2);
            }
        });
        if (MyApplication.Userinfo.getBoolean("notification", true)) {
            JPushInterface.resumePush(getApplicationContext());
            Log.i(TAG, "onCreate: 开启  极光通知");
        } else {
            JPushInterface.stopPush(getApplicationContext());
            Log.i(TAG, "onCreate: 关闭  极光通知");
        }

    }


    public static void toA() {

        radioderGroup.check(R.id.tab_a);
    }

    public static void toB() {
        radioderGroup.check(R.id.tab_b);
    }

    public static void toD() {

        if (!LoginUtil.toLogin((instance))) {

            return;

        }

        radioderGroup.check(R.id.tab_d);
    }

    public static void toC() {
        radioderGroup.check(R.id.iv_publish);
    }

    int old_item_id = R.id.tab_a;//默认是 主页

    public void resetGroupState(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            if (old_item_id == checkedId) {
                ((RadioButton) group.getChildAt(i)).setChecked(true);
            } else {
//                ( (RadioButton)group.getChildAt(i)).setChecked(false);
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.tab_a:
                tabHost.setCurrentTabByTag("1");
                check = "1";
                break;
            case R.id.tab_b:
                tabHost.setCurrentTabByTag("2");
                check = "2";
                break;
            case R.id.iv_publish:
                tabHost.setCurrentTabByTag("3");
                check = "3";
                break;
            case R.id.tab_d:
                if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {
                    start2ActivityWithResult(LoginActivity.class, 4);
                    if (!((RadioButton) group.getChildAt((Integer.parseInt(check)) - 1)).isChecked()) {
                        ((RadioButton) group.getChildAt((Integer.parseInt(check)) - 1)).setChecked(true);
                    }

                } else {
                    tabHost.setCurrentTabByTag("4");
                    check = "4";
                }
//
//                if (!LoginUtil.toLogin(MainActivity.this))
//                {
//
//                    return;
//                }


                try {
                    MainRadioButton tab_c = findViewById(R.id.tab_d);
                    tab_c.setShowSmallDot(false);
                } catch (Exception e) {
                    CrashReport.postCatchedException(new Exception("强转失败了,某些机型吗?"));
                }


                break;
            case R.id.tab_e:
                if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {
                    start2ActivityWithResult(LoginActivity.class, 4);
                    ((RadioButton) group.getChildAt((Integer.parseInt(check)) - 1)).setChecked(true);
                } else {
                    tabHost.setCurrentTabByTag("5");
                    check = "5";
                }


                try {
                    MainRadioButton tab_e = findViewById(R.id.tab_e);
                    tab_e.setShowSmallDot(false);
                } catch (Exception e) {
                    CrashReport.postCatchedException(new Exception("强转失败了,某些机型吗?"));
                }
//                tab_e.setCompoundDrawablePadding(20);


                break;
        }
    }


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;

        }


        return super.dispatchKeyEvent(event);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            // 如果有更新就提示

            if (msg.what == 0) {
                // showUpdateDialog();
                showUpdateDialog2();
            } else if (msg.what == 1) {
                // Toast toast = Toast.makeText(MainActivity.this,
                // R.string.the_version_is_new, Toast.LENGTH_SHORT);
                // toast.setGravity(Gravity.CENTER, 0, 0);
                // toast.show();
            }
        }

    };
//    private ChooseManagerAdapter myadapter;

    private static final String TAG = "MainActivity";

    private void showUpdateDialog2() {
        if (updateInfo == null || TextUtils.isEmpty(updateInfo.getUrl())) {
            Log.i(TAG, "showUpdateDialog2: updateInfo == null");
            return;
        }

        UpdateDialog.Builder builder = new UpdateDialog.Builder(this);
        builder.setTitle(getResources().getString(
                R.string.please_update_app_to_version)
                + updateInfo.getVersion());
        builder.setPrice("");
        builder.setCount("");
        builder.setAccountName(updateInfo.getDescription());
        builder.setAccountBank("");
        builder.setAccountNum("");


        builder.setPositiveButton("确定", (dialog12, which) -> {
            dialog12.dismiss();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // downFile(updateInfo.getUrl());
                try {
                    SettingUtils.launchBrowser(MainActivity.this, updateInfo.getUrl());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            } else {
                Toast.makeText(MainActivity.this,
                        R.string.sd_card_is_disable, Toast.LENGTH_SHORT)
                        .show();
            }
            // 设置你的操作事项
        });

        if (updateInfo.isForce()) {
            UpdateDialog dialog = builder.create();
            //强制更新
            if (ConstantState.ON_OFF) {
                dialog.setCancelable(false);
            }
            dialog.show();
        } else {
            builder.setNegativeButton("取消",
                    (dialog1, which) -> dialog1.dismiss());
            UpdateDialog dialog = builder.create();
            dialog.show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state：保存其他数据
        savedInstanceState.putSerializable("updateInfo", updateInfo);

    }


    /**
     * 携参数 跳转
     *
     * @param cls 开启的activity
     * @param i   result 参数
     */
    public void start2ActivityWithResult(Class cls, int i) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivityForResult(intent, 4);
        MyUtil.overridePendingTransition_open(MainActivity.this);
    }


    //获取当前地址
    private void getAddr() {

        MLocationManager.getInstance().startLoaction(location -> {
            if (location != null && location.getErrorCode() == 0) {
                alocation = location;
                D.e("=====result===" + location.toString());
                province_loc = location.getProvince();
                D.e("==============" + location.getProvince());
                city_loc = location.getCity();
                D.e("==============" + location.getCity());
                district_loc = location.getDistrict();
                D.e("==============" + location.getDistrict());

                latitude = location.getLatitude() + "";
                longitude = location.getLongitude() + "";


                if (location.getAdCode() != null && location.getAdCode().length() >= 4) {
                    cityCode = location.getAdCode().substring(0, 4);
                }

                aMapLocation = location;
                MLocationManager.getInstance().stopLoaction();//一次成功后关闭定位
            } else {
                //多次失败 会超时关闭
                D.e("======定位失败=========");
            }
        });
    }


    private void requestUserInfo() {
//        if (GetServerUrl.isTest) {
//            ToastUtil.showShortToast("测试时显示\n请求 个人数据");
//        }
        LoginPresenter.getUserInfo(MyApplication.Userinfo.getString("id", ""), new ResultCallBack<UserInfoGsonBean>() {
            @Override
            public void onSuccess(UserInfoGsonBean userInfoGsonBean) {
                D.e("========获取个人数据=====userInfoGsonBean=====" + userInfoGsonBean.toString());
//                if (GetServerUrl.isTest) {
//                    ToastUtil.showShortToast("测试时显示\n请求 个人数据" + userInfoGsonBean.toString());
//                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                D.e("=========strMsg=========" + strMsg);
            }
        });
    }

    public static AMapLocation alocation;
    public static String latitude = "0.0";
    public static String longitude = "0.0";
    public static String province_loc = "";
    public static String city_loc = "";
    public static String district_loc = "";
    public static AMapLocation aMapLocation = null;
    public static String cityCode = "";

    /**
     * ======amapLocation=========latitude=24.488207longitude=118.09651province=福建省#city=厦门市#district=思明区
     * #cityCode=0592#adCode=350203#address=福建省厦门市思明区七星西路靠近中国农业银行(厦门岳阳支行)
     * #country=中国#road=七星西路#poiName=中国农业银行(厦门岳阳支行)#street=七星西路#streetNum=31号#aoiName=七星大厦(七星西路)
     * #errorCode=0#errorInfo=success#locationDetail=-5 #csid:42a901e6e47042779c9890bb49f2f5fe#locationType=2
     */


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

}
