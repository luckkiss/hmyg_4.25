package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.base.BaseWebViewActivity;
import com.hy.utils.GetServerUrl;

/**
 * 调度专员界面
 */

public class DispatcherActivity extends BaseWebViewActivity {


    public static void start(Activity mActivity) {

        mActivity.startActivity(new Intent(mActivity, DispatcherActivity.class));

    }

    @Override
    public String setTitle() {
        return "调度专员";
    }

    @Override
    public String BaseUrl() {
        String head = GetServerUrl.getHtmlUrl();
        return head + "page/dispatch.html?isApp=true";
    }

    @Override
    public void onLoadUrl(WebView webView, String url) {
        if (url.contains("tel:")) {
//            ToastUtil.showShortToast(url);
            String phone = url.split(":")[1];
            FlowerDetailActivity.CallPhone(phone, mActivity);
        } else {
            super.onLoadUrl(webView, url);
        }
    }

    //    private static final String api_html_test0 = "http://test.m.hmeg.cn/";
//    private static final String api_html_test1 = "http://192.168.1.252:8090/";
}
