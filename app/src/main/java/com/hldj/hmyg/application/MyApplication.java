package com.hldj.hmyg.application;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;
import android.widget.TextView;

import com.hldj.hmyg.DaoBean.SaveJson.DaoMaster;
import com.hldj.hmyg.DaoBean.SaveJson.DaoSession;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.UserBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.SPUtil;
import com.hldj.hmyg.util.SPUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.weavey.loading.lib.LoadingLayout;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import im.fir.sdk.FIR;


public class MyApplication extends MultiDexApplication {

    public static SharedPreferences Userinfo;
    public static SharedPreferences Deviceinfo;
    private static MyApplication myApplication = null;

    public TextView mLocationResult, logMsg;
    public TextView trigger, exit;
    public Vibrator mVibrator;




    //存储userbean
    private static UserBean userBean;

    public static void setUserBean(UserBean userBean) {
        MyApplication.userBean = userBean;
    }

    public static UserBean getUserBean() {
        if (userBean == null) {
            String json = SPUtil.get(getInstance(), SPUtils.UserBean, "").toString();
            if (json.equals("")) {
//                ToastUtil.showShortToast("未登录");
            } else {
                userBean = GsonUtil.formateJson2Bean(json, UserBean.class);
            }
            return new UserBean();
        }
        return userBean;

    }

    /*
    数据库
     */
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @SuppressLint("SdCardPath")
    public void onCreate() {
        super.onCreate();


        FIR.init(this);
//      4e9fef47d1c33625cb0d5495e6856e0a
//        TestinAgent.init(this, "S9Ip9zGgJzj779e9S849s9z94X9DTUGJ",
//                "your channel ID");
//        CrashReport
//                .initCrashReport(getApplicationContext(), "900021393", false);
        ShareSDK.initSDK(this);
//        CrashHandler1 crashHandler = CrashHandler1.getInstance();
//        crashHandler.init(getApplicationContext());
        // 本地奔溃保存
        Userinfo = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        Deviceinfo = getSharedPreferences("Deviceinfo", Context.MODE_PRIVATE);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initImageLoader(getApplicationContext());
        createSDCardDir(getApplicationContext());
        // 由于Application类本身已经单例，所以直接按以下处理即可。
        myApplication = this;
        mVibrator = (Vibrator) getApplicationContext().getSystemService(
                Service.VIBRATOR_SERVICE);


//        try {
//            initDao();
//        } catch (Exception e) {
//            D.e("===dao初始化失败==="+e.getMessage());
//        }

        initDao();

        initLoadingLayout();


    }

    private void initLoadingLayout() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.error)
                .setEmptyImage(R.mipmap.empty)
                .setNoNetworkImage(R.mipmap.no_network)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40);
    }

    private void initDao() {
        setDatabase();
    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "hmyg.db", null);

        try {
            db = mHelper.getWritableDatabase();
        } catch (Exception e) {
            D.e("====" + e.getMessage());
            db = mHelper.getReadableDatabase();
        }


        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    public static MyApplication getInstance() {
        return myApplication;
    }

    @SuppressLint("SdCardPath")
    public static void createSDCardDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String path = "/mnt/sdcard/hmyg";
            File file = new File(path);
            if (!file.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
                file.mkdirs();
            }
        } else {
            // Toast.makeText(context, "未检查到sd卡，部分功能无法使用",
            // Toast.LENGTH_SHORT).show();
        }

    }

    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
