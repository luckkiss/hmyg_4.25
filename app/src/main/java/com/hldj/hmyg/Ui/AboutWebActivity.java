package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.WebActivity;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.base.BaseWebViewActivity;
import com.mabeijianxi.smallvideorecord2.Log;

/**
 * 客服 与 关于 统一 集成到 web 中去
 */

public class AboutWebActivity extends BaseWebViewActivity {
    @Override
    public String BaseUrl() {
        Log.i("BaseUrl", Data.About);
        return Data.helpIndex;
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public void onLoadUrl(WebView webView, String url) {
//        super.onLoadUrl(webView, url);

        if (url.contains("tel:")) {
//            ToastUtil.showShortToast(url);
            String phone = url.split(":")[1];
            FlowerDetailActivity.CallPhone(phone, mActivity);
        } else {
//            super.onLoadUrl(webView, url);

            Intent intent = new Intent(mActivity, WebActivity.class);

            intent.putExtra("title", "");
            intent.putExtra("url", url);
            mActivity.startActivity(intent);



        }
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, AboutWebActivity.class));

    }

    @Override
    public String setTitle() {
        return "客服";
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (getmWebView() != null) {
            if (getmWebView().canGoBack()) {
                getmWebView().goBack();
            } else {
                finish();
            }
        } else {
            finish();
        }


    }
}
