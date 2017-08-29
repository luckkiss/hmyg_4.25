package com.hldj.hmyg.base;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hldj.hmyg.R;

/**
 * 基础webview activity  ，集成本界面   来控制所有的 h5 界面
 */
//showConsumerName
public abstract class BaseWebViewActivity extends BaseMVPActivity {

    private static final String TAG = "BaseWebViewActivity";
    WebView mWebView;
    ProgressBar progressBar;


    @Override
    public void initView() {
        //初始化控件
        mWebView = getView(R.id.base_web_view);
        progressBar = getView(R.id.base_web_progressBar);

        //启用支持Javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "loadingUrl =   \n" + url);
                    onLoadUrl(mWebView, url);
//                view.loadUrl(url);
                return true;
            }
        });
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    //页面加载完成，关闭ProgressDialog
//                    mLoadingLayout.setStatus(LoadingLayout.Success);
                } else {
                    //网页正在加载，打开ProgressDialog
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });

        mWebView.loadUrl(BaseUrl());
//        mWebView.loadUrl("http://www.cnblogs.com/popfisher/p/5191242.html");
    }


    @Override
    public boolean setSwipeBackEnable() {

        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_webview_base;
    }


    public abstract String BaseUrl();

    public void onLoadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }

}
