package com.hldj.hmyg.buyer.Ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hy.utils.GetServerUrl;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WebViewDialogFragment1 extends DialogFragment {


    private WebView webView;
    private TextView tv_ok_to_close;
    String url = GetServerUrl.getUrl() + "h5/page/serviceagreement.html";


    public static WebViewDialogFragment1 newInstance() {
        WebViewDialogFragment1 f = new WebViewDialogFragment1();
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview_dialog_tcp, null);
        initView(view);

        return view;
    }

    private void initView(View view) {

        view.findViewById(R.id.ic_back).setOnClickListener(v -> dismiss());
        TextView textView = (TextView) view.findViewById(R.id.ic_title);
        textView.setText("服务协议");

//        tv_show_html.setText(html);
        webView = (WebView) view.findViewById(R.id.webview_show_quite_detail);
        //WebView加载本地资源
        webView.loadUrl(url);
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

                } else {
                    //网页正在加载，打开ProgressDialog

                }
            }


        });


    }


}
