package com.hldj.hmyg.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hldj.hmyg.R;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.lqr.optionitemview.OptionItemView;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 *
 */

public class NoticeActivity extends NeedSwipeBackActivity {


    private ViewHolder viewHolder;//通过geviewholder 来获取

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
        initWebView(getViewHolder().notices_webview);
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView(WebView webView) {
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    getViewHolder().notices_progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    //页面加载完成，关闭ProgressDialog
//                    mLoadingLayout.setStatus(LoadingLayout.Success);
                } else {
                    //网页正在加载，打开ProgressDialog
                    getViewHolder().notices_progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    getViewHolder().notices_progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
//          webView.loadUrl("file:///asset/test.html");
//          webView.loadUrl("file:///android_asset/test.html");
        webView.loadUrl(getExtral());
//          webView.loadUrl("http://blog.csdn.net/a394268045/article/details/51892015");
    }

    private void initView() {
        getViewHolder().notices_title.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                finish();
            }

            @Override
            public void centerOnClick() {
            }

            @Override
            public void rightOnClick() {
                ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName());
            }
        });

    }

    public String getExtral() {
//        return getIntent().getStringExtra("url")
        return "http://192.168.1.252:8090/article?categoryId=e510b27cdefb49779e079d3a2a997753&isHeader=true\n";
    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, NoticeActivity.class));
    }


    private class ViewHolder {
        public OptionItemView notices_title; // title
        public ProgressBar notices_progressBar; // title
        public WebView notices_webview; // title

        public ViewHolder(Activity rootView) {
            this.notices_title = (OptionItemView) rootView.findViewById(R.id.notices_title);
            this.notices_progressBar = (ProgressBar) rootView.findViewById(R.id.notices_progressBar);
            this.notices_webview = (WebView) rootView.findViewById(R.id.notices_webview);
        }

    }


}
