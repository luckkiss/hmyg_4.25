package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ValueGetInfo;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 商城主界面 适配器
 */
@SuppressLint("ResourceAsColor")
public class ProductListAdapter extends BaseAdapter {
    private static final String TAG = "ProductListAdapter";

    private ArrayList<HashMap<String, Object>> data = null;

    private Context context = null;
    private FinalBitmap fb;

    private ImageView iv_like;

    public ProductListAdapter(Context context,
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
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_view_seedling_new, null);
            childHolder = new ChildHolder();
            childHolder.iv_img = (ImageView) convertView
                    .findViewById(R.id.iv_img);
            childHolder.iv_like = (ImageView) convertView
                    .findViewById(R.id.iv_like);
            childHolder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            childHolder.iv_right_top = convertView.findViewById(R.id.iv_right_top);
            childHolder.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
            childHolder.tv_03 = (TextView) convertView.findViewById(R.id.tv_03);
            childHolder.tv_04 = (TextView) convertView.findViewById(R.id.tv_04);
            childHolder.tv_05 = (TextView) convertView.findViewById(R.id.tv_05);
            childHolder.tv_06 = (TextView) convertView.findViewById(R.id.tv_06);
            childHolder.tv_07 = (TextView) convertView.findViewById(R.id.tv_07);
            childHolder.tv_08 = (TextView) convertView.findViewById(R.id.tv_08);
            childHolder.tv_09 = (TextView) convertView.findViewById(R.id.tv_09);
            childHolder.tv_floorPrice = (TextView) convertView
                    .findViewById(R.id.tv_floorPrice);
            childHolder.rl_floorPrice = (RelativeLayout) convertView
                    .findViewById(R.id.rl_floorPrice);
            childHolder.tv_status_01 = (TextView) convertView
                    .findViewById(R.id.tv_status_01);
            childHolder.tv_status_02 = (TextView) convertView
                    .findViewById(R.id.tv_status_02);
            childHolder.tv_status_03 = (TextView) convertView
                    .findViewById(R.id.tv_status_03);
            childHolder.tv_status_04 = (TextView) convertView
                    .findViewById(R.id.tv_status_04);
            childHolder.tv_status_05 = (TextView) convertView
                    .findViewById(R.id.tv_status_05);

            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        try {

            if ((Boolean) data.get(position).get("ziying")) {
                childHolder.iv_right_top.setVisibility(View.VISIBLE);
            } else {
                childHolder.iv_right_top.setVisibility(View.GONE);
            }


//        if (data.get(position).get("isRecommend").toString().contains("true")) {
//            childHolder.iv_like.setImageResource(R.drawable.tuijian_lv);
//        } else {
//            childHolder.iv_like.setImageResource(R.drawable.tuijian_hui);
//        }

            if (data.get(position).get("plantType").toString().contains("planted")) {
                childHolder.tv_01.setBackgroundResource(R.drawable.icon_seller_di);
            } else if (data.get(position).get("plantType").toString()
                    .contains("transplant")) {
                childHolder.tv_01.setBackgroundResource(R.drawable.icon_seller_yi);
            } else if (data.get(position).get("plantType").toString()
                    .contains("heelin")) {
                childHolder.tv_01.setBackgroundResource(R.drawable.icon_seller_jia);
            } else if (data.get(position).get("plantType").toString()
                    .contains("container")) {
                childHolder.tv_01
                        .setBackgroundResource(R.drawable.icon_seller_rong);
            } else {
                childHolder.tv_01.setVisibility(View.GONE);
            }


            if ("manage_list".equals(data.get(position).get("show_type").toString())) {

                childHolder.rl_floorPrice.setVisibility(View.VISIBLE);
                if (data.get(position).get("floorPrice") != null) {
                    childHolder.tv_floorPrice.setText("底价："
                            + ValueGetInfo.doubleTrans1(Double.parseDouble(data.get(position).get("floorPrice").toString())));
                }
                if ("unaudit".equals(data.get(position).get("status").toString())) {
                    childHolder.tv_03.setTextColor(Color.parseColor("#6cd8b0"));
                } else if ("published".equals(data.get(position).get("status")
                        .toString())) {
                    childHolder.tv_03.setTextColor(Color.parseColor("#fa7600"));
                    iv_like.setVisibility(View.VISIBLE);
                } else if ("outline".equals(data.get(position).get("status")
                        .toString())) {
                    childHolder.tv_03.setTextColor(Color.parseColor("#93c5fc"));
                } else if ("backed".equals(data.get(position).get("status")
                        .toString())) {
                    childHolder.tv_03.setTextColor(Color.parseColor("#b8d661"));
                } else if ("unsubmit".equals(data.get(position).get("status")
                        .toString())) {
                    childHolder.tv_03.setTextColor(Color.parseColor("#eb8ead"));
                }
                childHolder.tv_03.setText(data.get(position).get("statusName")
                        .toString());
                childHolder.tv_05.setText("苗源地址："
                        + data.get(position).get("detailAddress").toString());
                if (data.get(position).get("closeDate").toString().length() > 10) {
                    childHolder.tv_06.setText("下架日期："
                            + data.get(position).get("closeDate").toString()
                            .substring(0, 10));
                } else {
                    childHolder.tv_06.setText("下架日期："
                            + data.get(position).get("closeDate").toString());
                }
            } else if ("seedling_list".equals(data.get(position).get("show_type")
                    .toString())) {//商城主页列表

                //高度冠幅  ：
                childHolder.tv_03.setText("" + data.get(position).get("specText"));
                //地区：
                childHolder.tv_04.setText("苗源地: " + data.get(position).get("fullName"));

                if (!"".equals(data.get(position).get("companyName").toString())) {
                    childHolder.tv_06.setText("发布人："
                            + data.get(position).get("companyName").toString());
                } else if ("".equals(data.get(position).get("companyName")
                        .toString())
                        && !"".equals(data.get(position).get("publicName")
                        .toString())) {
                    childHolder.tv_06.setText("发布人："
                            + data.get(position).get("publicName").toString());
                } else if ("".equals(data.get(position).get("companyName")
                        .toString())
                        && "".equals(data.get(position).get("publicName")
                        .toString())) {
                    childHolder.tv_06.setText("发布人："
                            + data.get(position).get("realName").toString());
                }
            }
            // tv_04.setText(ValueGetInfo.getValueString(data.get(position).get("dbh")
            // .toString(), data.get(position).get("height").toString(), data
            // .get(position).get("crown").toString(),
            // data.get(position).get("diameter").toString(), ""));
            // tv_04.setText(ValueGetInfo.getValueStringByTag(data.get(position).get("paramsList").toString(),data.get(position).get("dbh")
            // .toString(), data.get(position).get("height").toString(), data
            // .get(position).get("crown").toString(),
            // data.get(position).get("diameter").toString(),
            // data.get(position).get("offbarHeight").toString()));


            childHolder.tv_02.setText(data.get(position).get("name").toString());

            childHolder.tv_04
                    .setText(data.get(position).get("specText").toString());

            boolean isNego = (boolean) data.get(position).get("isNego");
            String minPrice = data.get(position).get("minPrice") + "";
            String maxPrice = data.get(position).get("maxPrice") + "";
            String priceStr = data.get(position).get("priceStr") + "";
            childHolder.tv_08.setText("  /" + data.get(position).get("unitTypeName"));
            setPrice(childHolder.tv_07, priceStr, minPrice, isNego, childHolder.tv_08);
//        if (TextUtils.isEmpty(maxPrice) && TextUtils.isEmpty(minPrice)) {
//            childHolder.tv_07.setText(minPrice + "-" + maxPrice);
//        } else {
//            childHolder.tv_07.setText("面议");
//        }


//        childHolder.tv_07.setText(ValueGetInfo.doubleTrans1(Double.parseDouble(data.get(position).get("price").toString())));


            childHolder.tv_09.setText("库存：" + data.get(position).get("count").toString());
            fb.display(childHolder.iv_img, data.get(position).get("imageUrl")
                    .toString());
        } catch (Exception e) {

        }

        convertView.setOnClickListener(v -> {
            if ("manage_list".equals(data.get(position).get("show_type")
                    .toString())) {
                // "published".equals(data.get(position).get("status")
                // .toString())
                Intent toFlowerDetailActivity = new Intent(context,
                        FlowerDetailActivity.class);
                toFlowerDetailActivity.putExtra("id", data.get(position)
                        .get("id").toString());
                toFlowerDetailActivity.putExtra("show_type",
                        data.get(position).get("show_type").toString());
                context.startActivity(toFlowerDetailActivity);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                Intent toFlowerDetailActivity = new Intent(context,
                        FlowerDetailActivity.class);
                toFlowerDetailActivity.putExtra("id", data.get(position)
                        .get("id").toString());
                toFlowerDetailActivity.putExtra("show_type",
                        data.get(position).get("show_type").toString());
                context.startActivity(toFlowerDetailActivity);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_left, R.anim.slide_out_right);
            }

        });

        return convertView;
    }

    public static void setPrice(TextView tv_07, String maxPrice, String minPrice, boolean isNego, TextView tv_unit) {


        tv_07.setText("" + maxPrice);
        if (tv_unit!=null)
        tv_unit.setVisibility(View.GONE);
//        String price = "";
//        if (isNego) {
//
//            tv_07.setText("面议");
//            if (tv_unit != null)
//            tv_unit.setVisibility(View.GONE);
//        } else {
//            if (!isPriceNull(minPrice) && !isPriceNull(maxPrice)) {
//                price = "¥" + minPrice + "-" + maxPrice;
//            } else if (isPriceNull(minPrice) && !isPriceNull(maxPrice)) {
//                price = "¥" + maxPrice;
//            } else if (!isPriceNull(minPrice) && isPriceNull(maxPrice)) {
//                price = "¥" + minPrice;
//            } else {
//                price = "面议";
//            }
//
//            if (tv_unit != null && price.equals("面议")) {
//                tv_unit.setVisibility(View.GONE);
//            }else
//            {
//                if (tv_unit != null)
//                tv_unit.setVisibility(View.VISIBLE);
//            }
//
//            tv_07.setText(price);
//        }
    }

    public static boolean isPriceNull(String price) {
        return TextUtils.isEmpty(price) || price.equals("0") || price.equals("null");
    }


    private class ChildHolder {
        ImageView iv_img;
        ImageView iv_like;
        TextView tv_01;
        TextView tv_02;
        TextView tv_03;
        TextView tv_04;
        TextView tv_05;
        TextView tv_06;
        View iv_right_top;
        TextView tv_07;
        TextView tv_08;
        TextView tv_09;
        TextView tv_floorPrice;
        RelativeLayout rl_floorPrice;
        TextView tv_status_01;
        TextView tv_status_02;
        TextView tv_status_03;
        TextView tv_status_04;
        TextView tv_status_05;
//		ImageView sc_ziying;
//		ImageView sc_fuwufugai;
//		ImageView sc_hezuoshangjia;
//		ImageView sc_huodaofukuan;
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

    private void setRecommend(final String id) {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/setRecommend",
                params, new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                                        .show();
                            }
                            if ("1".equals(code)) {

                                onGoodsCheckedChangeListener
                                        .onGoodsCheckedChange(id, true);
                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

}
