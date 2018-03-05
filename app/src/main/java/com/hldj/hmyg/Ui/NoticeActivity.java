package com.hldj.hmyg.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.lqr.optionitemview.OptionItemView;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 *  成交公告   界面
 */

public class NoticeActivity extends NeedSwipeBackActivity {


    private ViewHolder viewHolder;//通过geviewholder 来获取
    private WebView webView;
    private boolean canClick;

    public ViewHolder getViewHolder() {
        if (viewHolder == null) {
            viewHolder = new ViewHolder(this);
        }
        return viewHolder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
    }


    private ProgressBar pg1;

    private void initView() {

        getViewHolder().news_title.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                onBackPressed();
            }

            @Override
            public void centerOnClick() {
                finish();
            }

            @Override
            public void rightOnClick() {
                if (canClick) ComonShareDialogFragment.newInstance()
                        .setShareBean(new ComonShareDialogFragment.ShareBean(title, desc, desc, cover, pageUrl))
                        .show(getSupportFragmentManager(), getClass().getName());
            }
        });

        webView = (WebView) findViewById(R.id.news_webview);

//        mLoadingLayout.setStatus(LoadingLayout.Loading);
        pg1 = (ProgressBar) findViewById(R.id.news_progressBar);
        /**
         *
         loadingLayout.setStatus(LoadingLayout.Loading);//表示展示加载界面
         loadingLayout.setStatus(LoadingLayout.Empty);//表示展示无数据界面
         loadingLayout.setStatus(LoadingLayout.Error);//表示展示加载错误界面
         loadingLayout.setStatus(LoadingLayout.No_Network);表示无网络连接界面
         */
        initWebView(webView);

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView(WebView webView) {
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj1");
        webView.setWebViewClient(new CustomWebViewClient());
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                    //页面加载完成，关闭ProgressDialog
//                    mLoadingLayout.setStatus(LoadingLayout.Success);
                } else {
                    //网页正在加载，打开ProgressDialog
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });



//        webView.loadUrl("http://192.168.1.252:8090/article?isHeader=true");
//       http://test.m.hmeg.cn/noticeArticle?isHeader=true
          webView.loadUrl(Data.noticesUrls);
             //"http://api.hmeg.cn/"
//          webView.loadUrl("http://blog.csdn.net/a394268045/article/details/51892015");
    }


    /**
     * @author linzewu
     */
      final class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            title = view.getTitle();
            pageUrl = url;
            D.e("==pageUrl==" + pageUrl);
            if (url.contains("article/detail/")) {
                D.e("=====yes=======");
                OptionItemView optionItemView = (OptionItemView) findViewById(R.id.news_title);
                optionItemView.showRightImg(true);
                canClick = true;
                //showRightImg
            } else {
                D.e("=====no=======");
                OptionItemView optionItemView = (OptionItemView) findViewById(R.id.news_title);
                optionItemView.showRightImg(false);
                canClick = false;
            }
            cover = "";
            view.loadUrl("javascript:window.java_obj.getSource(" + "document.getElementById('article-cover').innerHTML);");
//            view.loadUrl("javascript:window.java_obj.getSource(" + "document.getElementById('article-title').innerHTML);");

            desc = "";
            view.loadUrl("javascript:window.java_obj1.getSource1(" + "document.getElementById('article-desc').innerHTML);");
//            view.loadUrl("javascript:window.java_obj1.getSource1(" + "document.getElementById('article-desc').innerHTML);");

        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            D.e("========onReceivedError=========");
            super.onReceivedError(view, request, error);
        }
    }

    String title = "";//title
    String cover = "";//头像地址
    String desc = "";//描述
    String pageUrl = "";//描述

    final class InJavaScriptLocalObj {
        //cover
        @JavascriptInterface
        public void getSource(String html) {
            D.e("html=" + html);
            cover = html;
        }

        // desc
        @JavascriptInterface
        public void getSource1(String html) {
            D.e("html=" + html);
            desc = html;
        }
    }

    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, NoticeActivity.class));
    }


    private class ViewHolder {
        public OptionItemView news_title; // title

        public ViewHolder(Activity rootView) {
            this.news_title = (OptionItemView) rootView.findViewById(R.id.news_title);

        }

    }
}
