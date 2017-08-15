package com.hldj.hmyg.buyer.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.hldj.hmyg.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WebViewDialogFragment2 extends DialogFragment {


    private WebView webView;
    private TextView tv_ok_to_close;
    private View view;


    public static WebViewDialogFragment2 newInstance(StoreInfoBean infoBean) {
        WebViewDialogFragment2 f = new WebViewDialogFragment2();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("str", infoBean);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          storeInfoBean = ((StoreInfoBean) getArguments().getSerializable("str"));

    }
    StoreInfoBean storeInfoBean ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_webview_dialog_store_info, null);
        initView(view);

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        setInfoBean(storeInfoBean);

        return view;
    }

    private void initView(View view) {
        TextView tv_ok_to_close = view.findViewById(R.id.tv_ok_to_close);

        tv_sotreName = view.findViewById(R.id.tv_sotreName);
        tv_inTheCity = view.findViewById(R.id.tv_inTheCity);
        tv_phoneNum = view.findViewById(R.id.tv_phoneNum);
        tv_itemNum = view.findViewById(R.id.tv_itemNum);
        tv_priced = view.findViewById(R.id.tv_priced);


        tv_ok_to_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    TextView tv_sotreName;
    TextView tv_inTheCity;
    TextView tv_phoneNum;
    TextView tv_itemNum;
    TextView tv_priced;

    public void setInfoBean(StoreInfoBean infoBean) {
        tv_sotreName.setText(infoBean.sotreName);
        tv_inTheCity.setText(infoBean.inTheCity);
        tv_phoneNum.setText(infoBean.phoneNum);
        tv_itemNum.setText(infoBean.itemNum);
        tv_priced.setText(infoBean.priced);

    }

    public static class StoreInfoBean implements Serializable {
        String sotreName = "-";//采购商家
        String inTheCity = "-";//所在地区
        String phoneNum = "-";//联系电话
        String itemNum = "-";//采购单号
        String priced = "-";// 已有报价
    }


}
