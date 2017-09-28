package com.hldj.hmyg.Ui.jimiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.fanrunqi.swipelayoutlibrary.SwipeLayout;

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

    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();

    private Context context = null;
    private FinalBitmap fb;

    public MiaoNoteListAdapter(Context context,
                               ArrayList<HashMap<String, Object>> data) {

        this.data = data;
//        if (data == null) {
//            this.data = new ArrayList<>();
//        } else {
//            this.data.clear();
//            this.data.addAll(data);
//        }

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


        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note_miao_del, null);
            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe_manager);
            holder.tv_delete_manager = (TextView) convertView.findViewById(R.id.btn_delete_manager);
            holder.swipe_manager1 = convertView.findViewById(R.id.swipe_manager1);
            holder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            holder.tv_03 = (TextView) convertView.findViewById(tv_03);
            holder.textView27 = (TextView) convertView.findViewById(R.id.textView27);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);

            holder.tv_05 = (TextView) convertView.findViewById(tv_05);//价格
            holder.tv_07 = (TextView) convertView.findViewById(tv_07);
            holder.tv_08 = (TextView) convertView.findViewById(tv_08);
            holder.tv_right_top = (TextView) convertView.findViewById(R.id.tv_right_top);//  省份地区
            holder.tv_mpmc_lxr_dh = (TextView) convertView.findViewById(R.id.tv_mpmc_lxr_dh);//苗圃名称
            holder.lxr = (TextView) convertView.findViewById(R.id.lxr);//苗圃名称
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }


        holder.tv_mpmc_lxr_dh.setText("苗圃名称：" + data.get(position).get("nurseryJson_name").toString());

        holder.lxr.setText("联系人：" + data.get(position).get("contactName")
                + "  " + data.get(position).get("contactPhone"));
        //        TextView tv_lxdh = (TextView) inflate.findViewById(R.id.tv_lxdh);//联系人 + 电话
//        tv_lxdh.setText("联系人：" + data.get(position).get("contactName")
//                + "联系电话：" + data.get(position).get("contactPhone")
//        );
        holder.tv_right_top.setText(data.get(position).get("fullName") + "");


        boolean is = isSelf(position, data.get(position).get("ownerId").toString());

        holder.textView27.setVisibility(is ? View.GONE : View.VISIBLE);
        holder.textView27.setText("发布人：写死-----");


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

            if (jsonBeen.size() > 0) {
                fb.display(holder.image, jsonBeen.get(0).ossSmallImagePath);
            }
        } else {
            Log.e(TAG, "getView: 没有图片显示");
        }


//      jsonBeen = GsonUtil.formateJson2Bean(imgJson, new TypeToken<List<ImagesJsonBean>>() {}.getType()) ;

//        PurchaseDetailActivity.setImgCounts(((Activity) context), ((SuperTextView) convertView.findViewById(R.id.tv_quote_item_photo_num)), jsonBeen);

//        holder.tv_05.setText(ValueGetInfo.doubleTrans1(Double.parseDouble(data.get(position).get("price").toString())));
        holder.tv_05.setText(data.get(position).get("price").toString());
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

        holder.tv_08.setText("规格:" + s1 + " " + "高度:" + s2 + " " + "冠幅:" + s3);


        holder.swipeLayout.SimulateScroll(SwipeLayout.SHRINK);

        holder.tv_delete_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeLayout.SimulateScroll(SwipeLayout.SHRINK);
                if (myItemClickLister != null) {
                    String id = data.get(position).get("id").toString();
                    boolean is = isSelf(position, data.get(position).get("ownerId").toString());

                    D.e("pos=" + position);
                    D.e("id=" + id);
                    D.e("is=" + is);
                    myItemClickLister.OnItemDel(position, id, is);
                }
            }
        });

        holder.swipe_manager1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myItemClickLister_content != null) {
//                    isSelf = !isSelf ;
                    String ownerId = data.get(position).get("ownerId").toString();
                    D.e("ownerId" + ownerId);
                    D.e("my id " + MyApplication.Userinfo.getString("id", ""));
                    D.e("====" + ownerId.equals(MyApplication.Userinfo.getString("id", "")));
//                    myItemClickLister_content.OnSelf(ownerId.equals(MyApplication.Userinfo.getString("id", "")));

                    myItemClickLister_content.OnItemDel(position, data.get(position).get("id").toString(), isSelf(position, ownerId));

                }
            }
        });


        return convertView;
    }

    boolean isSelf;

    public void notify(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
//        if (data == null) {
//            this.data = new ArrayList<>();
//        } else {
//            this.data.clear();
//            this.data.addAll(data);
//        }
        notifyDataSetChanged();
    }

    public void notifyDel(int pos) {
        this.data.remove(pos);
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


    class ViewHolder {
        TextView tv_01;
        TextView tv_03;
        TextView textView27;//发布人
        TextView tv_05;
        TextView tv_07;
        TextView tv_08;
        TextView lxr;
        ImageView image;
        View swipe_manager1;
        TextView tv_right_top;//  省份地区
        TextView tv_mpmc_lxr_dh;//苗圃名称
        SwipeLayout swipeLayout;
        TextView tv_delete_manager;
    }

    public interface MyItemClickLister {
        void OnItemDel(int pos, String id, boolean isSelf);

//        void OnSelf(boolean isSelf);//判断是否自己的单子
    }

    public MyItemClickLister myItemClickLister;
    public MyItemClickLister myItemClickLister_content;

    public void setMyItemLis(MyItemClickLister itemLis) {
        myItemClickLister = itemLis;
    }

    public void setMyItemLisContent(MyItemClickLister itemLis) {
        myItemClickLister_content = itemLis;
    }


    public boolean isSelf(int position, String ownerId) {
        D.e("ownerId" + ownerId);
        D.e("my id " + MyApplication.Userinfo.getString("id", ""));
        D.e("====" + ownerId.equals(MyApplication.Userinfo.getString("id", "")));
//        myItemClickLister_content.OnSelf(ownerId.equals(MyApplication.Userinfo.getString("id", "")));
        return ownerId.equals(MyApplication.Userinfo.getString("id", ""));


//        myItemClickLister_content.OnItemDel(position, data.get(position).get("id").toString(),);
    }

}
