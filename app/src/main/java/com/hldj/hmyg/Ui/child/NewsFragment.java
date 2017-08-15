package com.hldj.hmyg.Ui.child;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.NewsActivity;
import com.hldj.hmyg.util.D;
import com.lqr.optionitemview.OptionItemView;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NewsFragment extends Fragment {


//    private LoadingLayout mLoadingLayout;

    public NewsFragment() {

    }

    private ProgressBar pg1;

    public static NewsFragment instance(String url) {
        NewsFragment newsFragment = new NewsFragment();
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString("url", url);
        newsFragment.setArguments(b);
        return newsFragment;
    }

    View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == mFragmentView) {
            mFragmentView = inflater.inflate(R.layout.fragment_news_content, container, false);

            D.e("====inner==onCreateView=======");
        } else {
            D.e("====out===onCreateView=======");
        }


        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = view.findViewById(R.id.news_webview);

//        mLoadingLayout.setStatus(LoadingLayout.Loading);
        pg1 = view.findViewById(R.id.news_progressBar);
        /**
         *
         loadingLayout.setStatus(LoadingLayout.Loading);//表示展示加载界面
         loadingLayout.setStatus(LoadingLayout.Empty);//表示展示无数据界面
         loadingLayout.setStatus(LoadingLayout.Error);//表示展示加载错误界面
         loadingLayout.setStatus(LoadingLayout.No_Network);表示无网络连接界面
         */
        initWebView(webView);
//        new Handler().postDelayed(()->,2000);
    }



    @SuppressLint("JavascriptInterface")
    private void initWebView(WebView webView) {
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
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
        webView.loadUrl("http://192.168.1.252:8090/article?isHeader=true");
//        webView.loadUrl("http://blog.csdn.net/a394268045/article/details/51892015");
    }


    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(), "js调用了Native函数传递参数：" + str, Toast.LENGTH_SHORT).show();
            String text = "\njs调用了Native函数传递参数：" + str;
            D.e("======text==========" + text);
        });

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
//            view.loadUrl("javascript:window.java_obj.getSource('<head>'+" + "document.getElementById('article-cover').innerHTML+'</head>');");
//            String str = "document.getElementById('article-cover').innerHTML";
//            D.e("title=" + str);
            super.onPageFinished(view, url);

            view.getTitle();
            D.e("==title==" + view.getTitle());
            D.e("url=" + url);


            if (url.contains("article/detail/")) {
                D.e("=====yes=======");
                OptionItemView optionItemView = getActivity().findViewById(R.id.news_title);
                optionItemView.showRightImg(true);
//showRightImg
            } else {
                D.e("=====no=======");
                OptionItemView optionItemView = getActivity().findViewById(R.id.news_title);
                optionItemView.showRightImg(false);
            }

            view.loadUrl("javascript:window.java_obj.getSource(" + "document.getElementById('article-cover').innerHTML);");

//            new Handler().postDelayed(()->{
//            },1);

//            view.loadUrl("javascript:window.java_obj.showSource('<head>'+"
//                    + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            D.e("========onReceivedError=========");
            super.onReceivedError(view, request, error);
        }
    }

    /**
     * 逻辑处理
     *
     * @author linzewu
     */

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
            D.e("html=" + html);
        }
    }



}
