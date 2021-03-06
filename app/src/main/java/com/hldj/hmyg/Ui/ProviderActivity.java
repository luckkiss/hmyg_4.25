package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;

import com.hldj.hmyg.WebActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseWebViewActivity;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

/**
 *  供应商等级 权限申请 界面
 */

public class ProviderActivity extends BaseWebViewActivity {

    @Override
    public String BaseUrl() {
//        String head = "http://192.168.1.252:8090/";
        String head = GetServerUrl.getHtmlUrl();
        //http://192.168.1.252:8090
//        林则金<linzj183@qq.com>  下午 4:31:05
//                /app/userPoint/detail
        /**
         * http://192.168.1.252:8090/app/userPoint/seller?uId=2876f7e0f51c4153aadc603b661fedfa&token=cf37452d815debd48426c031ee3607b1a869451fe48ce86c
         */

        String url = "";
        //http://192.168.1.252:8090/app/protocol/supplier

        if (isQutot()) {
            url = head + "app/userPoint/seller?uid=" + MyApplication.Userinfo.getString("id", "") + "&token=" + GetServerUrl.getKeyStr(System.currentTimeMillis()) +"&isNew=true" ;

            D.e("=======BaseUrl===url===\n" + url);
        } else {
            url = head + "app/protocol/supplier?uid=" + MyApplication.Userinfo.getString("id", "") + "&token=" + GetServerUrl.getKeyStr(System.currentTimeMillis())  ;
        }

        return url;
    }
//uId=XXXX&token=XXXXX

    @Override
    public String setTitle() {
        if (isQutot()) {
            return "供应商";
        } else {
            return "供应商协议";
        }
    }


    @Override
    public void onLoadUrl(WebView webView, String url) {

        if (url.contains("app/userPoint/specialRateDetail")) {

            url = url.substring(0, url.lastIndexOf("&"));
            url += "&token=" + GetServerUrl.getKeyStr(System.currentTimeMillis());

            Intent intent = new Intent(mActivity, WebActivity.class);
            intent.putExtra("title", "");
            intent.putExtra("url", url);
            mActivity.startActivity(intent);


            return;
        }

        super.onLoadUrl(webView, url);
        // http://192.168.1.252:8090/app/userPoint/specialRateDetail?uid=32704040045549a8ba89e234f663f39e&token=667940947325173071cbc26165f9e0af74bce37d3f870dc7


        if (url.contains("/personal/index")) {
            finish();
        } else {
            webView.loadUrl(url);
        }

    }

    public boolean isQutot() {
        return getIntent().getBooleanExtra("isQuot", false);
    }

    public static void start(Activity mActivity, boolean isQuot) {
        Intent intent = new Intent(mActivity, ProviderActivity.class);
        intent.putExtra("isQuot", isQuot);
        mActivity.startActivity(intent);
    }


}
