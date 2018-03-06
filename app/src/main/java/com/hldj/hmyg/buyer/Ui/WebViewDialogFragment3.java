package com.hldj.hmyg.buyer.Ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.UserInfoGsonBean;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CustomDialog;
import com.hldj.hmyg.presenter.LoginPresenter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WebViewDialogFragment3 extends DialogFragment {

    private static final String TAG = "WebViewDialogFragment3";

    private WebView webView;
    private TextView tv_ok_to_close;
    //    String url = GetServerUrl.getUrl() + "h5/page/serviceagreement.html";
    String url = Data.Protocol_Url;
//http://192.168.1.252:8090/page/help/serviceagreement.html?isApp=true

    public interface OnAgreeListener {
        void OnAgree(boolean b);
    }

    OnAgreeListener onAgreeListener;
    /**
     * 监听弹出窗是否被取消
     */
    private OnAgreeListener mCancelListener;

    public static WebViewDialogFragment3 newInstance(OnAgreeListener onAgreeListener) {
        WebViewDialogFragment3 f = new WebViewDialogFragment3();
        f.setCancelable(true);
        f.onAgreeListener = onAgreeListener;
        return f;
    }


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


        //注册接口
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");

//        webView.addJavascriptInterface(this, "android");


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

            //拦截js 的alert 方法
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }


            //拦截js 的Confirm 方法
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
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
            ToastUtil.showShortToast_All(getActivity(), url);
            Log.e(TAG, "shouldOverrideUrlLoading: " + url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {


//            view.loadUrl("javascript:window.java_obj.getResult(" + "document.getElementById('article-cover').innerHTML);");

            //动态注入监听函数
            view.loadUrl("javascript:(function(){document.getElementById('agree').onclick=function(){window.java_obj.supplierAgree();}})()");

            view.loadUrl("javascript:(function(){document.getElementById('disagree').onclick=function(){window.java_obj.supplierDisagree();}})()");

//            view.loadUrl("javascript:(function(){document.getElementById('child-btn agree').onclick=function(){window.java_obj.onResultSelectOk();}})()");
            super.onPageFinished(view, url);

            /**
             *  mWebview.loadUrl("javascript:function getSub(){alert(\"Welcome\");" +
             "document.forms[0].submit();};getSub();");
             }        });
             */
        }

    }


    // 注入js函数监听
//    private void supplierAgree() {
//        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
//        webView.loadUrl("javascript:(function(){" + "java_obj.supplierAgree();"
//                + "})()");
//    }

    /**
     * 逻辑处理
     *
     * @author linzewu
     */

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void supplierDisagree() {
            Observable.just("disAgree")
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(next -> {
                        onAgreeListener.OnAgree(false);
                        dismiss();
                    });
        }

        @JavascriptInterface
        public void supplierAgree() {
            RequestUtil requestUtil = new RequestUtil();
            ///admin/user/supplierIsAgree
            requestUtil.requestAgree()
                    .flatMap(new Function<String, Observable<String>>() {
                        @Override
                        public Observable<String> apply(@NonNull String s) throws Exception {
                            Log.e(TAG, "---aggre请求成功调用-----继续请求个人信息----apply: s=" + s);
//                            ToastUtil.showShortToast("aggre请求成功调用-----继续请求个人信息");
                            return requestUtil.requestUserInfo();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(next -> {
                        Log.e(TAG, "supplierAgree: ");
                        if (next.equals(ConstantState.SUCCEED_CODE)) {
                            onAgreeListener.OnAgree(true);
                            dismiss();
//                            ToastUtil.showShortToast("rxjava_    next=" + next);
                        } else {
                            onAgreeListener.OnAgree(false);
                            dismiss();
//                            ToastUtil.showShortToast("rxjava_ but  faild   next=" + next);
                        }

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            ToastUtil.showShortToast("出错了！");
                            onAgreeListener.OnAgree(false);
                            dismiss();
                        }
                    });
        }
    }


    private class RequestUtil {

        /**
         * 执行修改个人数据接口
         */


        public Observable<String> requestUserInfo() {
            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    LoginPresenter.getUserInfo(MyApplication.Userinfo.getString("id", ""), new ResultCallBack<UserInfoGsonBean>() {
                        @Override
                        public void onSuccess(UserInfoGsonBean userInfoGsonBean) {
                            e.onNext(userInfoGsonBean.getCode());

                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            e.onError(t);
                        }
                    });
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }


        /**
         * 声明一个被观察者对象，作为结果返回
         */

        public Observable<String> requestAgree() {

            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {

                    new MyPrestener().doingReq(new AjaxCallBack<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.e(TAG, "requestAgree  onSuccess: ");
                            SimpleGsonBean gsonBean = GsonUtil.formateJson2Bean(s, SimpleGsonBean.class);
                            e.onNext(gsonBean.code);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            e.onError(t);
                        }
                    });
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        }


    }


    private class MyPrestener extends BasePresenter {

        public void doingReq(AjaxCallBack<String> callBack) {
            doRequest("admin/user/supplierIsAgree", true, callBack);
        }
    }


    @JavascriptInterface
    public void show(String s) {
//        ToastUtil.showShortToast("网页的按钮被点击了被点击了");
    }


    OnResultListener onResult;

    public interface OnResultListener {
        boolean onResultSelect();

        boolean onResultSelectOk();
    }


}
