package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.Ui.QuoteListActivity_bak;
import com.hldj.hmyg.util.D;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;


@SuppressLint("ResourceAsColor")
public class StorePurchaseListAdapter extends BaseAdapter {
    private static final String TAG = "StorePurchaseListAdapter";

    private ArrayList<HashMap<String, Object>> data = null;

    private Context context = null;
    private FinalBitmap fb;

    private View mainView;


    public static boolean isShow = true;


    public StorePurchaseListAdapter(Context context,
                                    ArrayList<HashMap<String, Object>> data, View mainView) {
        this.data = data;
        this.context = context;
        this.mainView = mainView;
        fb = FinalBitmap.create(context);
        fb.configLoadingImage(R.drawable.no_image_show);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.data.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(  R.layout.list_item_store_purchase, null);
        ImageView iv_img2 = (ImageView) inflate.findViewById(R.id.iv_img2);
        TextView tv_01 = (TextView) inflate.findViewById(R.id.tv_01);
        TextView tv_ac = (TextView) inflate.findViewById(R.id.tv_ac);
        TextView tv_02 = (TextView) inflate.findViewById(R.id.tv_02);
        TextView tv_03 = (TextView) inflate.findViewById(R.id.tv_03);
        TextView tv_04 = (TextView) inflate.findViewById(R.id.tv_04);
        TextView tv_05 = (TextView) inflate.findViewById(R.id.tv_05);
        TextView tv_06 = (TextView) inflate.findViewById(R.id.tv_06);
        TextView tv_07 = (TextView) inflate.findViewById(R.id.tv_07);
        TextView tv_08 = (TextView) inflate.findViewById(R.id.tv_08);
        TextView tv_09 = (TextView) inflate.findViewById(R.id.tv_09);
        TextView tv_10 = (TextView) inflate.findViewById(R.id.tv_10);
        TextView tv_11 = (TextView) inflate.findViewById(R.id.tv_11);
        TextView tv_caozuo01 = (TextView) inflate.findViewById(R.id.tv_caozuo01);
        TextView tv_caozuo02 = (TextView) inflate
                .findViewById(R.id.tv_caozuo02);
        TextView tv_caozuo03 = (TextView) inflate.findViewById(R.id.tv_caozuo03);//立马报价按钮

        boolean expired = (boolean) data.get(position).get("expired");


        if (MyApplication.getInstance().Userinfo.getBoolean("isLogin", false)) {
//            tv_caozuo01.setText("");
            if (!expired)//false 未过期
            {
                tv_caozuo01.setText("马上报价");
                tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.green_btn_selector));
            } else {//已过期
                tv_caozuo01.setText("采购已关闭");
                tv_caozuo01.setTextColor(ContextCompat.getColor(context, R.color.orange));
                tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.trans_bg));
            }
        } else {
        }

        tv_01.setText((position + 1) + "、" + data.get(position).get("name").toString());


        tv_ac.setText(data.get(position).get("count").toString()
                + data.get(position).get("unitTypeName").toString());


        tv_02.setText("采购单：" + data.get(position).get("num").toString());
        if (data.get(position).get("cityName") != null) {
            tv_03.setText(data.get(position).get("cityName").toString());

            if (!isShow) {
                tv_03.setVisibility(View.GONE);
            } else {
                tv_03.setVisibility(View.VISIBLE);
            }


        }

        D.e("=======isShow======" + isShow);
        tv_04.setText("采购单位：" + data.get(position).get("displayName").toString());
        tv_02.setVisibility(View.GONE);
        tv_04.setVisibility(View.GONE);
        // tv_05.setText("截至日期：" +
        // data.get(position).get("closeDate").toString());
        // tv_06.setText("分类："
        // + data.get(position).get("firstTypeName").toString());
        // if (Boolean.parseBoolean(data.get(position).get("needInvoice")
        // .toString())) {
        // tv_10.setText("发票要求：需要");
        // } else {
        // tv_10.setText("发票要求：不需要");
        // }

        String specText = data.get(position).get("specText") + "";
        String remarks = data.get(position).get("remarks") + "";


        setSpaceAndRemark(tv_05, specText, remarks);


        tv_07.setText("种植类型："
                + data.get(position).get("plantTypeName").toString());

        boolean canQuote = (boolean) data.get(position).get("canQuote");


        // tv_06.setText("其他要求：" +
        // data.get(position).get("remarks").toString());


        if (Boolean.parseBoolean(data.get(position).get("isQuoted").toString())) {
            iv_img2.setVisibility(View.VISIBLE);
        } else {
            iv_img2.setVisibility(View.INVISIBLE);
        }
        /**
         *     * showQuote : true
         * showQuoteCount : true
         */

        if (MyApplication.getInstance().getUserBean().showQuoteCount)//拥有权限
        {
            StringFormatUtil fillColor = new StringFormatUtil(context, "已有"
                    + data.get(position).get("quoteCountJson").toString() + "条报价", data.get(position).get("quoteCountJson").toString(),
                    R.color.red).fillColor();
            tv_10.setText(fillColor.getResult());
            tv_10.setVisibility(View.VISIBLE);

            if (data.get(position).get("quoteCountJson") != null && data.get(position).get("quoteCountJson").toString().equals("0")) {
                //跳转到报价列表详情
                tv_10.setOnClickListener(v -> ToastUtil.showShortToast("暂无报价"));
            } else {
                //跳转到报价列表详情
                tv_10.setOnClickListener(v -> QuoteListActivity_bak.start2Activity((Activity) context, data.get(position).get("id").toString()));
            }


        } else {
            tv_10.setVisibility(View.GONE);//没有权限 就不显示  和没有点击事件
        }


        inflate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.getInstance().Userinfo.getBoolean("isLogin", false)) {

                    if (expired) {
                        ToastUtil.showShortToast("采购已关闭");
                        return;
                    }
//                          PurchaseDetailActivity.start2Activity((Activity) context, data.get(position).get("id").toString());//采购详情 界面
                    PurchaseDetailActivity.start2Activity((Activity) context, data.get(position).get("id").toString());//采购详情 界面

                } else {

                    LoginActivity.start2Activity((Activity) context);
                }


            }
        });

        return inflate;
    }

    public static void setSpaceAndRemark(TextView tv_05, String specText, String remarks) {

        if (!TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText(specText + ":" + remarks);
        } else if (!TextUtils.isEmpty(specText) && TextUtils.isEmpty(specText)) {
            tv_05.setText(specText);
        } else if (TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText(remarks);
        }
    }

    public void notify(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
