package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.QuoteStatus;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.Ui.DialogActivity;
import com.hldj.hmyg.buyer.Ui.QuoteListActivity_bak;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressLint("ResourceAsColor")
public abstract class StorePurchaseListAdapter_new extends GlobBaseAdapter<PurchaseItemBean_new> {
    private static final String TAG = "StorePurchaseL";

    private ArrayList<HashMap<String, Object>> data = null;

    public abstract String setCityName();

    public abstract Boolean isExpired();

    public abstract String getItemId();


    public StorePurchaseListAdapter_new(Context context, List<PurchaseItemBean_new> data, int layoutId) {
        super(context, data, layoutId);
    }

    public static boolean isShow = true;

    @Override
    public void setConverView(ViewHolders myViewHolder, PurchaseItemBean_new purchaseItemBeanNew, int position) {
        int viewRootId = R.layout.list_item_store_purchase;

        Log.e(TAG, "setConverView: " + position);
        myViewHolder.getView(R.id.iv_img2).setVisibility(purchaseItemBeanNew.isQuoted ? View.VISIBLE : View.GONE);


        ((TextView) myViewHolder.getView(R.id.tv_01)).setText((position + 1) + "、" + purchaseItemBeanNew.name);

        /*种植类型*/
        ((TextView) myViewHolder.getView(R.id.tv_021)).setText("种植类型: " + purchaseItemBeanNew.plantTypeArrayNames);

        ((TextView) myViewHolder.getView(R.id.tv_ac)).setText(purchaseItemBeanNew.count + purchaseItemBeanNew.unitTypeName);

//        setCityName()
        ((TextView) myViewHolder.getView(R.id.tv_03)).setText(purchaseItemBeanNew.purchaseJson.cityName);
        myViewHolder.getView(R.id.tv_03).setVisibility(isShow ? View.VISIBLE : View.GONE);

        setSpaceAndRemark(myViewHolder.getView(R.id.tv_05), purchaseItemBeanNew.specText, purchaseItemBeanNew.remarks);

        TextView tv_10 = myViewHolder.getView(R.id.tv_10);
        setTv10(tv_10, purchaseItemBeanNew);

        setTv_isloagin(myViewHolder.getView(R.id.tv_caozuo01), purchaseItemBeanNew);

        ListView listView = myViewHolder.getView(R.id.list);

//        List list = new ArrayList();
//        list.add("1");
//        list.add("1");
//        list.add("1");

        listView.setAdapter(new GlobBaseAdapter<SellerQuoteJsonBean>(context, purchaseItemBeanNew.sellerQuoteListJson, R.layout.item_purchase_first_cons) {
            @Override
            public void setConverView(ViewHolders myViewHolder, SellerQuoteJsonBean jsonBean, int position) {
                D.e("=====setConverView======str=============" + jsonBean.toString());

                TextView tv = myViewHolder.getView(R.id.textView32);
                tv.setText("¥" + jsonBean.price);

//                textView35  苗源地


                TextView city = myViewHolder.getView(R.id.textView35);
                city.setText(FUtil.$_zero(jsonBean.cityName));


                TextView textView37 = myViewHolder.getView(R.id.textView37);
                textView37.setText("报价说明：" + FUtil.$_zero(jsonBean.remarks));

                TextView state = myViewHolder.getView(R.id.textView39);
                state.setText("当前报价状态：" + getStateName(jsonBean.status));



                /*删除*/
                myViewHolder.getView(R.id.textView40).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("delete");
                        purchaseItemBeanNew.sellerQuoteListJson.remove(position);
                        notifyDataSetChanged();
                    }
                });

                /*马上报价*/
                myViewHolder.getView(R.id.textView41).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("马上报价");
                        purchaseItemBeanNew.sellerQuoteListJson.set(position, null);
                        notifyDataSetChanged();
                    }
                });


            }
        });


        myViewHolder.getConvertView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.Userinfo.getBoolean("isLogin", false)) {
                    if (isExpired()) {
                        ToastUtil.showShortToast("采购已关闭");
                        return;
                    }
//                    PurchaseDetailActivity.start2Activity((Activity) context, purchaseItemBeanNew.id);//采购详情 界面

                    //一次报价
                    purchaseItemBeanNew.pid1 = getItemId();
                    purchaseItemBeanNew.pid2 = getItemId();
                    DialogActivity.start((Activity) context, purchaseItemBeanNew);
//                    DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id ,purchaseItemBeanNew);

                } else {
                    LoginActivity.start2Activity((Activity) context);
                }
            }
        });


    }

    private void setTv_isloagin(TextView tv_caozuo01, PurchaseItemBean_new purchaseItemBeanNew) {
        if (!isExpired())//false 未过期
        {
            tv_caozuo01.setText("马上报价");
            tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.green_btn_selector));
        } else {//已过期
            tv_caozuo01.setText("采购已关闭");
            tv_caozuo01.setTextColor(ContextCompat.getColor(context, R.color.orange));
            tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.trans_bg));
        }
    }

    private void setTv10(TextView tv_10, PurchaseItemBean_new purchaseItemBeanNew) {
        if (MyApplication.getUserBean().showQuoteCount)//拥有权限
        {
            StringFormatUtil fillColor = new StringFormatUtil(context, "已有"
                    + purchaseItemBeanNew.quoteCountJson + "条报价", purchaseItemBeanNew.quoteCountJson + "",
                    R.color.red).fillColor();
            tv_10.setText(fillColor.getResult());
            tv_10.setVisibility(View.VISIBLE);
            if (purchaseItemBeanNew.quoteCountJson == 0) {
                //跳转到报价列表详情
                tv_10.setOnClickListener(v -> ToastUtil.showShortToast("暂无报价"));
            } else {
                //跳转到报价列表详情
                tv_10.setOnClickListener(v -> QuoteListActivity_bak.start2Activity((Activity) context, purchaseItemBeanNew.id));
            }
        } else {
            tv_10.setVisibility(View.INVISIBLE);//没有权限 就不显示  和没有点击事件
        }
    }


    public static void setSpaceAndRemark(TextView tv_05, String specText, String remarks) {

        if (!TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + specText + "   " + remarks);
        } else if (!TextUtils.isEmpty(specText) && TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + specText);
        } else if (TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + remarks);
        }
    }


    /*外面传进来的 地区名称*/
//    String cityName;

    /*赋值，是否判断是否过期*/
//    private boolean expired = false;


    public static String getStateName(String status) {
        if (status == null) {
            return "-";
        }
        if (status.equals(QuoteStatus.preQuote.getEnumValue())) {
            /* 预报价*/
            return QuoteStatus.preQuote.getEnumText();
        } else if (status.equals(QuoteStatus.preBid.getEnumValue())) {
              /* 预中标*/
            return QuoteStatus.preBid.getEnumText();
        } else if (status.equals(QuoteStatus.choosing.getEnumValue())) {
              /* 选标中*/
            return QuoteStatus.choosing.getEnumText();
        } else if (status.equals(QuoteStatus.used.getEnumValue())) {
              /* 已中标*/
            return QuoteStatus.used.getEnumText();
        } else if (status.equals(QuoteStatus.unused.getEnumValue())) {
              /* 未中标*/
            return QuoteStatus.unused.getEnumText();
        } else {
            return "-";
        }


    }

}


