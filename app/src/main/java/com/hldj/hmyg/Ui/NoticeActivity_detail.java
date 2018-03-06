package com.hldj.hmyg.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Keep;
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
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.lqr.optionitemview.OptionItemView;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 *
 */
@Keep
public class NoticeActivity_detail extends NeedSwipeBackActivity {


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
        setContentView(R.layout.activity_notice_detail);

        getExtral();
        initView();
        initWebView(getViewHolder().webview_notice_detail);
    }

    private void requestData() {
//        initView();

    }


    @Override
    public void onBackPressed() {
        if (getViewHolder().webview_notice_detail.canGoBack()) {
            getViewHolder().webview_notice_detail.goBack();
        } else {
            finish();
        }
    }

    private void initView() {

//        loadingLayout.setStatus(loadingLayout.Loading);
//        loadingLayout.show
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
               ComonShareDialogFragment.newInstance()
                        .setShareBean(new ComonShareDialogFragment.ShareBean(title, desc, desc, cover, pageUrl))
                        .show(getSupportFragmentManager(), getClass().getName());
//                ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName());
            }
        });




    }


    @SuppressLint("JavascriptInterface")
    private void initWebView(WebView webView) {
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj1");
        //页面加载
        webView.setWebViewClient(new CustomWebViewClient());
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    getViewHolder().pg1.setVisibility(View.GONE);//加载完网页进度条消失
                    //页面加载完成，关闭ProgressDialog
//                    mLoadingLayout.setStatus(LoadingLayout.Success);
                } else {
                    //网页正在加载，打开ProgressDialog
                    getViewHolder().pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    getViewHolder().pg1.setProgress(newProgress);//设置进度值
                }
            }
        });

        webView.loadUrl(url);
//          webView.loadUrl("http://blog.csdn.net/a394268045/article/details/51892015");
    }




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

                D.e("=====yes=======");
                OptionItemView optionItemView = (OptionItemView) findViewById(R.id.news_title_detail);
                optionItemView.showRightImg(true);
                //showRightImg

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



    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, NoticeActivity_detail.class));
    }


    public String url = "";

    public void getExtral() {
        url = getIntent().getStringExtra("url");
    }

    public static void start2Activity(Context context, String detail_path) {
        Intent intent
                = new Intent(context, NoticeActivity_detail.class);
        intent.putExtra("url", detail_path);
        context.startActivity(intent);
    }

    private class ViewHolder {
        public OptionItemView news_title; // title
        public WebView webview_notice_detail; // tablayout
        public ProgressBar pg1; // tablayout

        public ViewHolder(Activity rootView) {
            this.news_title = (OptionItemView) rootView.findViewById(R.id.news_title_detail);
            this.webview_notice_detail = (WebView) rootView.findViewById(R.id.webview_notice_detail);
            this.pg1 = (ProgressBar) rootView.findViewById(R.id.news_progressBar_detail);
        }

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }
}
