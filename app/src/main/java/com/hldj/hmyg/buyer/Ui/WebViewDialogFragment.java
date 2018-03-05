package com.hldj.hmyg.buyer.Ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
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

/**
 * Created by Administrator on 2017/4/28.
 */

public class WebViewDialogFragment extends DialogFragment {


    private WebView webView;
    private TextView tv_ok_to_close;
    String url = "http://blog.csdn.net/lmj623565791/article/details/37815413/";
    String html = "http://blog.csdn.net/lmj623565791/article/details/37815413/";
    private TextView tv_show_html;


    public String mTitle = "报价说明";
    public String mCloseContent = "确定";
    private TextView tv_show_title;
    private Window window;

    public static WebViewDialogFragment newInstance(String strs) {
        WebViewDialogFragment f = new WebViewDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("strs", strs);
        f.setArguments(args);
        return f;
    }

    public WebViewDialogFragment setTitle(String title) {
        mTitle = title;
        return this;
    }

    public WebViewDialogFragment setCloseContent(String closeContent) {
        mCloseContent = closeContent;
        return this;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        html = getArguments().getString("strs");
    }


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_webview_dialog, null);
////        initView(view);
//
//        //添加这一行
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        return view;
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_webview_dialog, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();

        window = dialog.getWindow();
         window.setGravity(Gravity.CENTER); //可设置dialog的位置
          window.getDecorView().setPadding(50, 0, 50, 0); //消除边距
        window.getDecorView().setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams lp =  window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(dialog);

        return dialog;

    }


    public void setContent(String h) {
//        if (tv_show_html != null)
        html = h;
//            tv_show_html.setText(Html.fromHtml(h, null, null));
    }

    private void initView(Dialog view) {
        tv_show_html = (TextView) view.findViewById(R.id.tv_show_html);
        tv_show_title = (TextView) view.findViewById(R.id.tv_show_title);

        tv_show_html.setText(Html.fromHtml(html, null, null));
        tv_show_title.setText(mTitle);

        tv_show_html.setVisibility(View.GONE);
        ((ViewGroup) tv_show_html.getParent()).setVisibility(View.GONE);


//        tv_show_html.setText(html);
        webView = (WebView) view.findViewById(R.id.webview_show_quite_detail);
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
        settings.setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
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

        webView.loadData(html , "text/html; charset=UTF-8", null);//这种写法可以正确解码

        tv_ok_to_close = (TextView) view.findViewById(R.id.tv_ok_to_close);
        view.findViewById(R.id.tv_show_title).setOnClickListener(v -> {
            dismiss();
        });

        tv_ok_to_close.setOnClickListener(v -> {
            dismiss();
        });
        tv_ok_to_close.setText(mCloseContent);


    }


}
