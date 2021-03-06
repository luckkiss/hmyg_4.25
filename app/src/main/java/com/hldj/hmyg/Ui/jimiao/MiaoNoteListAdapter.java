package com.hldj.hmyg.Ui.jimiao;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.hldj.hmyg.saler.SaveSeedlingActivity_pubsh_quick;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fanrunqi.swipelayoutlibrary.SwipeLayout;

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
            holder.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);

//            holder.view_line = (TextView) convertView.findViewById(R.id.view_line);
            holder.tv_08 = (TextView) convertView.findViewById(R.id.tv_03);
//            holder.tv_04 = (TextView) convertView.findViewById(R.id.tv_04);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
//            holder.textView27 = (TextView) convertView.findViewById(R.id.textView27);
            holder.image = (ImageView) convertView.findViewById(R.id.iv_img);

            holder.tv_05 = (TextView) convertView.findViewById(R.id.tv_07);//价格
            holder.tv_07 = (TextView) convertView.findViewById(R.id.tv_09); // 数量
//            holder.tv_08 = (TextView) convertView.findViewById(tv_08);
            holder.tv_right_top = (TextView) convertView.findViewById(R.id.tv_04);//  省份地区
            holder.tv_mpmc_lxr_dh = (TextView) convertView.findViewById(R.id.tv_06);//苗圃名称

//            holder.lxr = (TextView) convertView.findViewById(R.id.lxr);//苗圃名称
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

//        MySwipeAdapter.setSrcByType( holder.tv_01, item.plantType);


        holder.tv_mpmc_lxr_dh.setText("苗圃名称：" + data.get(position).get("nurseryJson_name").toString());

//        holder.lxr.setText("联系人：" + data.get(position).get("contactName"));
//                + "  " + data.get(position).get("contactPhone"));
        //        TextView tv_lxdh = (TextView) inflate.findViewById(R.id.tv_lxdh);//联系人 + 电话
//        tv_lxdh.setText("联系人：" + data.get(position).get("contactName")
//                + "联系电话：" + data.get(position).get("contactPhone")
//        );
        holder.tv_right_top.setText(data.get(position).get("fullName") + "");


        boolean is = isSelf(position, data.get(position).get("ownerId").toString());


        String ownerName = data.get(position).get("ownerName").toString();
        String ownerPhone = data.get(position).get("ownerPhone").toString();

//        holder.tv_mpmc_lxr_dh.setText("发布人：" + ownerName);
//        holder.textView27.setVisibility(is ? View.GONE : View.VISIBLE);
//        holder.textView27.setText("发布人:" + ownerName);


//        tv_right_top.setText(data.get(position).get("nurseryJson_createDate") + "");

//        holder.tv_01.setText(data.get(position).get("name").toString());
//        MySwipeAdapter.setSrcByType(holder.tv_01, data.get(position).get("plantType").toString());
        holder.tv_01.setVisibility(View.GONE);
        holder.tv_02.setText(data.get(position).get("name").toString());

        String date = data.get(position).get("nurseryJson_createDate") + "";
//        holder.tv_03.setText("发布日期：" + date.split(" ")[0]);

//        holder.tv_04 .setText("");


        String imgJson = data.get(position).get("imagesJson") + "";
        String imageUrl = data.get(position).get("imageUrl") + "";
//        D.e("=======imgJson===========" + imgJson);
        List<ImagesJsonBean> jsonBeen = new ArrayList<>();
        if (!TextUtils.isEmpty(imgJson)) {
            jsonBeen = GsonUtil.formateJson2Bean(imgJson, new TypeToken<List<ImagesJsonBean>>() {
            }.getType());

            if (jsonBeen.size() > 0) {
                fb.display(holder.image, jsonBeen.get(0).ossSmallImagePath);

            }
        } else {
//            Log.e(TAG, "getView: 没有图片显示");
        }

        Log.e(TAG, "getView:  图片显示地址  " + imageUrl);
        fb.display(holder.image, imageUrl);

//      jsonBeen = GsonUtil.formateJson2Bean(imgJson, new TypeToken<List<ImagesJsonBean>>() {}.getType()) ;

//        PurchaseDetailActivity.setImgCounts(((Activity) context), ((SuperTextView) convertView.findViewById(R.id.tv_quote_item_photo_num)), jsonBeen);

//        holder.tv_05.setText(ValueGetInfo.doubleTrans1(Double.parseDouble(data.get(position).get("price").toString())));
        holder.tv_05.setText(FUtil.$_head("¥", data.get(position).get("price").toString()));
        holder.tv_07.setText("库存：" + FUtil.$_zero(data.get(position).get("count").toString()));//数量
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

        String id = data.get(position).get("id").toString();
        holder.swipeLayout.SimulateScroll(SwipeLayout.SHRINK);

        holder.tv_delete_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeLayout.SimulateScroll(SwipeLayout.SHRINK);
                if (myItemClickLister != null) {

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


        /*将需要隐藏显示的空间返回的主界面。由主界面来控制*/

        if (null != myItemClickLister_content) {
            String ownerId = data.get(position).get("ownerId").toString();
            myItemClickLister_content.OnTvVisibleChange(is, "发布人：" + ownerName, holder.tv_mpmc_lxr_dh, holder.lxr, holder.tv_mpmc_lxr_dh, holder.textView, holder.view_line);
        }

        //true  绿色     显示   发布到商城
        //false 红色     显示   已发布到商城
        String seedlingId = data.get(position).get("seedlingId").toString();

        boolean isSeeding = !TextUtils.isEmpty(seedlingId); // 是否已经发布到商城

//        D.e("=====seedlingId========" + seedlingId);
//        D.e("=====isSeeding========" + isSeeding);

        if (!isSeeding) {//true
            holder.textView.setSelected(!isSeeding);
            holder.textView.setText("发布到商城");

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.e("=========发布到商城==========\n" + id);
                    SaveSeedlingActivity_pubsh_quick.start2Activity((Activity) context, id);
                }
            });

        } else {//false
            holder.textView.setSelected(!isSeeding);
            holder.textView.setText("已发布到商城");
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast("此资源已经发布到商城^_^");
                }
            });
        }


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

    void notifyOne(String noteId) {
        int pos = -1;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).get("id").toString().equals(noteId)) {
                pos = i;
            }
        }

        D.e("=======notifyOne========pos = " + pos);
        if (pos == -1) {
            D.e("=========找不到刷新的数据。整体刷新======");
            this.notifyDataSetChanged();
            return;
        }


        HashMap<String, Object> hashMap = this.data.get(pos);

        //http://www.trinea.cn/android/hashmap-loop-performance/   map 4种便利方式


        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String key = entry.getKey();
//            Object value = entry.getValue();
            if ("seedlingId".equals(key)) {
                hashMap.put(key, noteId);
            }
        }
//        hashMap.forEach((s, o) -> {
//            if ("seedlingId".equals(s)) {
//                hashMap.put(s, noteId);
//            }
//        });
        this.data.set(pos, hashMap);
        this.notifyDataSetChanged();

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
        TextView textView;//已发布到商城
        TextView tv_01;
        TextView tv_02;
        TextView view_line;
        TextView tv_03;
        TextView tv_04;
//        TextView textView27;//发布人

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

        void OnTvVisibleChange(boolean isSelf, String owername, TextView... tvs);
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
//        D.e("ownerId" + ownerId);
//        D.e("my id " + MyApplication.Userinfo.getString("id", ""));
//        D.e("====" + ownerId.equals(MyApplication.Userinfo.getString("id", "")));
//        myItemClickLister_content.OnSelf(ownerId.equals(MyApplication.Userinfo.getString("id", "")));
        return ownerId.equals(MyApplication.Userinfo.getString("id", ""));


//        myItemClickLister_content.OnItemDel(position, data.get(position).get("id").toString(),);
    }

}
