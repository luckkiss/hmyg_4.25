package com.hldj.hmyg.Ui.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hldj.hmyg.R;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NewsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_content, null);
        WebView webView = (WebView) rootView.findViewById(R.id.news_webview);

        initWebView(webView);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initWebView(WebView webView) {





    }


}
