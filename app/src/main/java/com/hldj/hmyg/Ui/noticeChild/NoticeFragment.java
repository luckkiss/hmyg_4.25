package com.hldj.hmyg.Ui.noticeChild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NoticeFragment extends Fragment {


    private LoadingLayout mLoadingLayout;

    public NoticeFragment() {

    }


    public static NoticeFragment instance(String url) {
        NoticeFragment newsFragment = new NoticeFragment();
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
            mFragmentView = inflater.inflate(R.layout.fragment_notice_content, container, false);

            D.e("====inner==onCreateView=======");
        } else {
            D.e("====out===onCreateView=======");
        }


        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) view.findViewById(R.id.notice_webview);
        mLoadingLayout= (LoadingLayout) view.findViewById(R.id.notice_load_layout);

        mLoadingLayout.setStatus(LoadingLayout.Loading);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        D.e("===onDestroyView====");
    }

    private void initWebView(WebView webView) {


//        tv_show_html.setText(html);

        //WebView加载本地资源
//        webView.loadUrl("file:///android_asset/example.html");
        //WebView加载web资源
//        webView.loadUrl(html);

        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
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
                    //页面加载完成，关闭ProgressDialog
                } else if (newProgress == 20) {
                    mLoadingLayout.setStatus(LoadingLayout.Success);
                } else {
                    //网页正在加载，打开ProgressDialog
//                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
//                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });
        webView.loadUrl("https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fr=&sf=1&fmq=1462357247335_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (null != mFragmentView) {
//            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
//        }
    }
}
