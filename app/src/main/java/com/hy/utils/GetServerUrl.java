package com.hy.utils;

import android.util.Log;

import com.hldj.hmyg.application.MyApplication;

import net.tsz.afinal.FinalHttp;

/**
 * 获取服务器I址
 */

public class GetServerUrl {
    public static boolean isTest = true;//测试时使用true 表示正在测试
    /*发布时主要版本号   后面需要加 / */
//    public static String apiVersion = "3.0.5/";//发布的时候修改 api 版本号
//    public static String apiVersion = "4.0.4/";//   7.16  商城 图片变成正方型
//        public static String apiVersion = "4.0.3/";//   6.21 首页改版 api

    //    public static String apiVersion = "4.0.2/";//测试的时候修改 api 版本号
//    public static String apiVersion = "4.0.1/";//测试的时候修改 api 版本号
    public static String apiVersion = "3.0.4/";//测试的时候修改 api 版本号


    static String PGYER = "http://www.pgyer.com/apiv1/app/viewGroup";
    // 正式 可用 2.0
//    static String API_01 = "http://hmeg.cn:93/";
    // 正式 可用 3.0
    static String API_01 = "http://api.hmeg.cn/" + apiVersion;
    static String API_03 = "http://test.api.hmeg.cn/" + apiVersion;//3.0测式库
//    static String API_03 = "http://172.30.136.1:80/api/";//罗大傻的  电脑 服务器地址/
//  static String API_03 = "http://192.168.0.147:8091/api/";//罗伟电脑 服务器地址/
//    static String API_03 = "http://192.168.1.20:83/";// 则金 服务器地址/
//    static String API_03 = "http://110.86.33.238:90/";// 则金 外网 服务器地址/
//    http://192.168.1.20:83/api


//    private static final String api_html = "http://192.168.1.252:8090/";
    private static final String api_html = "http://m.hmeg.cn/";
    private static final String api_html_test0 = "http://test.m.hmeg.cn/";
//    private static final String api_html_test0 = "http://192.168.1.252:8090/";


    static String FIR_01 = "http://api.fir.im/apps/latest/57882cfc748aac17af00001e?api_token=7b3d87a7cb04b3a1624abb900c045c22&type=android&bundle_id=com.hldj.hmyg";
    static String PGYER_UPLOAD_01 = "https://www.pgyer.com/hmeg3";
    static String aId_01 = "87045b497d4eaffc7621c1f2ef75a79f";
    static String _api_key_01 = "eb7b16eabaf1d9af652fc65e921ba205";
    static String uKey_01 = "424e35aa2abf90730b5de65b2d7896e4";


    //测试看看是不是
    // 正式 不可用
    // static String API = "http://api.hmeg.cn:81/";
    // static String FIR =
    // "http://api.fir.im/apps/latest/57882cfc748aac17af00001e?api_token=7b3d87a7cb04b3a1624abb900c045c22&type=android&bundle_id=com.hldj.hmyg";


    //http://test.api.hmeg.cn/_____for luocaa

    //http://192.168.1.20:83/api

    // 测试,xingguo.huang@qq.com
//   static String API_03 = "http://test.hmeg.cn:93/";


    //http://test.api.hmeg.cn

    //    static String API_03 = "http://192.168.1.20:83/api/";
    static String FIR_03 = "http://api.fir.im/apps/latest/574270cc00fc744aef000000?api_token=d5ec18bebb4cd5acd798ffeeccbed6f4&type=android&bundle_id=com.hldj.hmyg";
    static String PGYER_UPLOAD_03 = "https://www.pgyer.com/hmegandroid";
    static String aId_03 = "ca3d2e3158115aa784e85a145acdcb0f";
    static String _api_key_03 = "686295388c4b09f08725f897937c8dda";
    static String uKey_03 = "2c65837ad0174ea90979e16cf3cb0ca3";

    public static final String purchaseConfirm1 = "http://m.hmeg.cn/purchaseConfirm/confirm/detail?t=";
    public static final String purchaseConfirm3 = "http://192.168.1.252:8090/purchaseConfirm/confirm/detail?t=";

    public static String getPurchaseconfirm() {
        if (isTest) {
            return purchaseConfirm3;
        } else {
            return purchaseConfirm1;
        }
    }

    public static String getPGYER() {
        return PGYER;
    }

    public static String getPGYER_UPLOAD() {
        if (isTest) {
            return PGYER_UPLOAD_03;
        } else {
            return PGYER_UPLOAD_01;
        }
    }

    public static String getFIR() {
        if (isTest) {
            return FIR_03;
        } else {
            return FIR_01;
        }

    }

    public static String getUrl() {
        if (isTest) {
            return API_03;
            // return getTestUrl();
        } else {
            return API_01;
        }
    }


    public static String getHtmlUrl() {
        if (isTest) {
//            return api_html_test1;
            return getTestUrl();
            // return getTestUrl();
//            GetServerUrl.getTestUrl()
        } else {
            return api_html;
        }
    }


    public static String getaId() {
        if (isTest) {
            return aId_03;
        } else {
            return aId_01;
        }
    }

    public static String get_api_key() {
        if (isTest) {
            return _api_key_03;
        } else {
            return _api_key_01;
        }
    }

    public static String getuKey() {
        if (isTest) {
            return uKey_03;
        } else {
            return uKey_01;
        }
    }

    static String TEST_API = "http://192.168.1.20:83/api/";
    // static String TEST_API = "http://192.168.1.146:8080/api/";
    public final static String WEB = "http://www.hmeg.cn";
    //    public final static String ICON_PAHT = "http://p3.so.qhimg.com/sdr/449_800_/t0147220f71d4562943.jpg";
    public final static String ICON_PAHT = "http://m.hmeg.cn/static/images/sharelogo.png";
    public final static String HEAD_DEFAULT = "------";//默认头像的地址
    public final static String TEST_URL = "https://m.baidu.com";
    public final static String Customer_Care_Phone = "4006579888";
    public final static String Customer_Care_QQ = "873528519";
    static String keyStr = "hmeg_api_~!@*(hmeg.cn";

    public static String getTestUrl() {
        return api_html_test0;
//      return TEST_API;

    }

    public static String Ramdom16Str(long current_time) {
        String rad16str = current_time + "";
        rad16str = MD5.Md(rad16str, false);
        return rad16str;
    }

    public static String getKeyStr(long current_time) {
        String Key = "";
        String Salt = current_time + "";
        Salt = MD5.Md(Salt, false);
        Key = Salt + MD5.Md((keyStr + Salt), true);
        return Key;
    }

    public static void addHeaders(FinalHttp finalHttp, boolean needId) {
        // TODO Auto-generated method stub
        finalHttp.addHeader("token",
                GetServerUrl.getKeyStr(System.currentTimeMillis()));
        if (true) {
            finalHttp.addHeader("authc",
                    MyApplication.Userinfo.getString("id", ""));
        }
        finalHttp.addHeader("version", version);
        finalHttp.addHeader("deviceId", deviceId);
        finalHttp.addHeader("type", "Android");

        finalHttp.addHeader("sdkVersion", SdkChangeByTagUtil.getVersionByLevel(sdk_version));
        finalHttp.addHeader("deviceType", device_type);
        finalHttp.addHeader("manufacturer", manufacturer);


//        GetServerUrl.sdk_version = Build.VERSION.SDK_INT + "";
//        GetServerUrl.device_type = Build.MODEL;
//        GetServerUrl.manufacturer = Build.MANUFACTURER;

        Log.e("authc", MyApplication.Userinfo.getString("id", ""));
    }

    public static String version = "";
    public static String deviceId = "";


    public static String sdk_version = "";

    //机型
    public static String device_type = "";

    //厂商
    public static String manufacturer = "";

}
