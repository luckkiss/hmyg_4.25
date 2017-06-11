package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;


@SuppressLint("ResourceAsColor")
public class ProductListAdapterForManager extends BaseAdapter {
    private static final String TAG = "ProductListAdapterForManager";

    private ArrayList<HashMap<String, Object>> data = null;

    private Context context = null;
    private FinalBitmap fb;


    public ProductListAdapterForManager(Context context,
                                        ArrayList<HashMap<String, Object>> data) {
        this.data = data;
        this.context = context;
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
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_view_seedling_new, null);
        ImageView iv_img = (ImageView) inflate.findViewById(R.id.iv_img);

        TextView tv_01 = (TextView) inflate.findViewById(R.id.tv_01);//小图标
        TextView tv_02 = (TextView) inflate.findViewById(R.id.tv_02);//名字

        TextView tv_03 = (TextView) inflate.findViewById(R.id.tv_03);//米径：15 高度：500 冠幅：400
        TextView tv_right_top = (TextView) inflate.findViewById(R.id.tv_right_top);//
        TextView tv_04 = (TextView) inflate.findViewById(R.id.tv_04);//苗源地：134
//		TextView tv_04_1 = (TextView) inflate.findViewById(R.id.tv_04_1);//多出一个 -1
        TextView tv_05 = (TextView) inflate.findViewById(R.id.tv_05);
        TextView tv_06 = (TextView) inflate.findViewById(R.id.tv_06);//发布人：河南基地
        TextView tv_07 = (TextView) inflate.findViewById(R.id.tv_07);
        TextView tv_08 = (TextView) inflate.findViewById(R.id.tv_08);
        TextView tv_09 = (TextView) inflate.findViewById(R.id.tv_09);


        if (data.get(position).get("plantType").toString().contains("planted")) {
            tv_01.setBackgroundResource(R.drawable.icon_seller_di);
        } else if (data.get(position).get("plantType").toString()
                .contains("transplant")) {
            tv_01.setBackgroundResource(R.drawable.icon_seller_yi);
        } else if (data.get(position).get("plantType").toString()
                .contains("heelin")) {
            tv_01.setBackgroundResource(R.drawable.icon_seller_jia);
        } else if (data.get(position).get("plantType").toString()
                .contains("container")) {
            tv_01.setBackgroundResource(R.drawable.icon_seller_rong);
        } else {
            tv_01.setVisibility(View.GONE);
        }

        if ("manage_list".equals(data.get(position).get("show_type").toString())) {

            if ("unaudit".equals(data.get(position).get("status").toString())) {//审核中
                tv_right_top.setTextColor(Data.STATUS_ORANGE);//审核中
            } else if ("published".equals(data.get(position).get("status")//已发布
                    .toString())) {
                tv_right_top.setTextColor(Data.STATUS_STROGE_GREEN);
                // iv_like.setVisibility(View.VISIBLE);
                // 暂时将设置推荐功能苗木隐藏
            } else if ("outline".equals(data.get(position).get("status")//已下架
                    .toString())) {
                tv_right_top.setTextColor(Data.STATUS_GREEN);
            } else if ("backed".equals(data.get(position).get("status")//被退回
                    .toString())) {
                tv_right_top.setTextColor(Data.STATUS_RED);
            } else if ("unsubmit".equals(data.get(position).get("status")//未提交
                    .toString())) {
                tv_right_top.setTextColor(Data.STATUS_BLUE);
            }
            tv_right_top.setVisibility(View.VISIBLE);


            tv_right_top.setText(data.get(position).get("statusName").toString());

            tv_04.setText("苗源地址：" + data.get(position).get("detailAddress").toString());

            if (data.get(position).get("closeDate").toString().length() > 10) {
                tv_06.setText("下架日期：" + data.get(position).get("closeDate").toString().substring(0, 10));
            } else {
                tv_06.setText("下架日期：" + data.get(position).get("closeDate").toString());
            }
        }


        tv_02.setText(data.get(position).get("name").toString());

        tv_03.setText(data.get(position).get("specText").toString());
//		tv_04_1.setText(data.get(position).get("specText").toString());
        String minPrice = data.get(position).get("minPrice") + "";
        String maxPrice = data.get(position).get("maxPrice") + "";


        boolean isNego = (boolean) data.get(position).get("isNego");


        tv_08.setText("/" + data.get(position).get("unitTypeName"));
        ProductListAdapter.setPrice(tv_07, maxPrice, minPrice, isNego, tv_08);
        tv_09.setText("库存：" + data.get(position).get("count"));
        fb.display(iv_img, data.get(position).get("imageUrl").toString());


        return inflate;
    }

    public void notify(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public interface OnGoodsCheckedChangeListener {
        void onGoodsCheckedChange(String id, boolean isRefresh);
    }

    OnGoodsCheckedChangeListener onGoodsCheckedChangeListener;

    public void setOnGoodsCheckedChangeListener(
            OnGoodsCheckedChangeListener onGoodsCheckedChangeListener) {
        this.onGoodsCheckedChangeListener = onGoodsCheckedChangeListener;
    }


    public static void setStateColor(TextView tv_right_top, String state, String stateName) {
        tv_right_top.setVisibility(View.VISIBLE);
        tv_right_top.setText(stateName);
        switch (state) {
            case "unaudit":
                tv_right_top.setTextColor(Data.STATUS_ORANGE);//审核中
                tv_right_top.setText("审核中");
                break;
            case "published":
                tv_right_top.setTextColor(Data.STATUS_STROGE_GREEN);
                break;
            case "outline":
                tv_right_top.setTextColor(Data.STATUS_GREEN);
                break;
            case "backed":
                tv_right_top.setTextColor(Data.STATUS_RED);
                break;
            case "unsubmit":
                tv_right_top.setTextColor(Data.STATUS_BLUE);
                break;
        }
    }

}
