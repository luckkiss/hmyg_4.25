package com.hldj.hmyg.Ui.storeChild;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.base.BaseFragment;

/**
 * 我的店铺   ------   店铺详情
 */

public class StoreDetailFragment extends BaseFragment {
    private static final String TAG = "StoreDetailFragment";

    @Override
    protected void initView(View rootView) {


        WebView webView = getView(R.id.web_view_detail);

        webView.loadUrl(Data.getStorePageById(getStoreId()));


    }


    public static StoreDetailFragment Instance(String storeId) {
        StoreDetailFragment fragment = new StoreDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TAG, storeId);
        fragment.setArguments(bundle);
        return fragment;

    }


    public String getStoreId() {
        return getArguments().getString(TAG);
    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_store_detail;
    }
}
