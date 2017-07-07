package com.hldj.hmyg.buyer.Ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CustomDialog;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WebViewDialogFragment3 extends DialogFragment {

    private WebView webView;
    private TextView tv_ok_to_close;
    //    String url = GetServerUrl.getUrl() + "h5/page/serviceagreement.html";
    String url = Data.Protocol_Url;
//http://192.168.1.252:8090/page/help/serviceagreement.html?isApp=true

    public static WebViewDialogFragment3 newInstance() {
        WebViewDialogFragment3 f = new WebViewDialogFragment3();
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


    @SuppressLint("JavascriptInterface")
    private void initView(View view) {
        CustomDialog customDialog = new CustomDialog(getActivity());
        view.findViewById(R.id.ic_back).setOnClickListener(v -> dismiss());
        ((ViewGroup) view.findViewById(R.id.ic_back).getParent()).setBackgroundColor(Color.WHITE);
        TextView textView = (TextView) view.findViewById(R.id.ic_title);
        textView.setText("苗木供应商合作框架协议");

//        tv_show_html.setText(html);
        webView = (WebView) view.findViewById(R.id.webview_show_quite_detail);
        //WebView加载本地资源
        D.e("====url=====" + url);
        webView.loadUrl(url);
        //WebView加载web资源
//        webView.loadUrl(html);


        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new CustomWebViewClient());

        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        customDialog.show();
        //页面加载
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    customDialog.dismiss();

                } else {
                    //网页正在加载，打开ProgressDialog

                }
            }


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

            super.onPageFinished(view, url);

//            view.loadUrl("javascript:window.java_obj.getResult(" + "document.getElementById('article-cover').innerHTML);");

            view.loadUrl("javascript:(function(){document.getElementById('child-btn cancel').onclick=function(){window.java_obj.getResult();}})()");

            view.loadUrl("javascript:(function(){document.getElementById('child-btn agree').onclick=function(){window.java_obj.onResultSelectOk();}})()");

        }

    }

    /**
     * 逻辑处理
     *
     * @author linzewu
     */

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getResult() {

            new Handler().post(() -> {
                ToastUtil.showShortToast("getResult");
            });

        }

        @JavascriptInterface
        public void onResultSelectOk() {
            new Handler().post(() -> {
                ToastUtil.showShortToast("getResult ok");
            });
        }
    }

    OnResultListener onResult;

    public interface OnResultListener {
        boolean onResultSelect();

        boolean onResultSelectOk();
    }
}
