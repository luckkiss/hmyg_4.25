package com.hldj.hmyg.Ui.jimiao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ValueGetInfo;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hldj.hmyg.R.id.tv_03;
import static com.hldj.hmyg.R.id.tv_05;
import static com.hldj.hmyg.R.id.tv_07;
import static com.hldj.hmyg.R.id.tv_08;

/**
 *
 *
 *
 */
@SuppressLint("ResourceAsColor")
public class MiaoNoteListAdapter extends BaseAdapter {
    private static final String TAG = "MiaoNoteListAdapter";

    private ArrayList<HashMap<String, Object>> data = null;

    private Context context = null;
    private FinalBitmap fb;

    public MiaoNoteListAdapter(Context context,
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


        ViewHolder holder ;
        if (convertView== null)
        {
            holder =  new ViewHolder();
            convertView =  LayoutInflater.from(context).inflate(  R.layout.list_item_note_miao, null) ;
            holder.tv_01= (TextView) convertView.findViewById(R.id.tv_01);
            holder.tv_03 = (TextView) convertView.findViewById(tv_03);
            holder.tv_05 = (TextView) convertView.findViewById(tv_05);
            holder.tv_07 = (TextView) convertView.findViewById(tv_07);
            holder.tv_08 = (TextView) convertView.findViewById(tv_08);
            holder.tv_right_top = (TextView) convertView.findViewById(R.id.tv_right_top);//  省份地区
            holder.tv_mpmc_lxr_dh = (TextView) convertView.findViewById(R.id.tv_mpmc_lxr_dh);//苗圃名称
            convertView.setTag(holder);
        }else{
            holder = ((ViewHolder) convertView.getTag());
        }



        holder.tv_mpmc_lxr_dh.setText("苗圃名称：" + data.get(position).get("nurseryJson_name").toString()
                + "  联系人：" + data.get(position).get("contactName")
                + "  " + data.get(position).get("contactPhone"));
//        TextView tv_lxdh = (TextView) inflate.findViewById(R.id.tv_lxdh);//联系人 + 电话
//        tv_lxdh.setText("联系人：" + data.get(position).get("contactName")
//                + "联系电话：" + data.get(position).get("contactPhone")
//        );
        holder.tv_right_top.setText(data.get(position).get("fullName") + "");


//        tv_right_top.setText(data.get(position).get("nurseryJson_createDate") + "");

        holder.tv_01.setText(data.get(position).get("name").toString());
        String date = data.get(position).get("nurseryJson_createDate") + "";
        holder.tv_03.setText("上传日期：" + date.split(" ")[0]);

        String imgJson = data.get(position).get("imagesJson") + "";
        D.e("=======imgJson===========" + imgJson);
        List<ImagesJsonBean> jsonBeen = new ArrayList<>();
        if (!TextUtils.isEmpty(imgJson)) {
            jsonBeen = GsonUtil.formateJson2Bean(imgJson, new TypeToken<List<ImagesJsonBean>>() {
            }.getType());
        }

//      jsonBeen = GsonUtil.formateJson2Bean(imgJson, new TypeToken<List<ImagesJsonBean>>() {}.getType()) ;

        PurchaseDetailActivity.setImgCounts(((Activity) context), ((SuperTextView) convertView.findViewById(R.id.tv_quote_item_photo_num)), jsonBeen);

        holder.tv_05.setText(ValueGetInfo.doubleTrans1(Double.parseDouble(data.get(position).get("price").toString())));
        holder.tv_07.setText(data.get(position).get("count").toString());
        String minSpec = data.get(position).get("minSpec").toString();
        String maxSpec = data.get(position).get("maxSpec").toString();
        if ("0".equals(minSpec)) {
            minSpec = "";
        }
        if ("0".equals(maxSpec)) {
            maxSpec = "";
        }

        String height = data.get(position).get("height").toString();
        String maxHeight = data.get(position).get("maxHeight").toString();
        if ("0".equals(height)) {
            height = "";
        }
        if ("0".equals(maxHeight)) {
            maxHeight = "";
        }

        String crown = data.get(position).get("crown").toString();
        String maxCrown = data.get(position).get("maxCrown").toString();
        if ("0".equals(crown)) {
            crown = "";
        }
        if ("0".equals(maxCrown)) {
            maxCrown = "";
        }

        String s1 = FUtil.$("-", minSpec, maxSpec);
        String s2 = FUtil.$("-", height, maxHeight);
        String s3 = FUtil.$("-", crown, maxCrown);

        holder.tv_08.setText("规格：" + s1 + "\u0020\u0020" + "高度：" + s2 + "\u0020\u0020" + "冠幅：" + s3);

        return convertView;
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


    class ViewHolder
    {
        TextView tv_01  ;
        TextView tv_03 ;
        TextView tv_05 ;
        TextView tv_07 ;
        TextView tv_08 ;
        TextView tv_right_top ;//  省份地区
        TextView tv_mpmc_lxr_dh ;//苗圃名称
    }


}
