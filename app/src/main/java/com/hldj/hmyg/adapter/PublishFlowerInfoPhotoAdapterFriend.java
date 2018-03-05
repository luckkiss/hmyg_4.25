package com.hldj.hmyg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.Pic;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;

public class PublishFlowerInfoPhotoAdapterFriend extends PublishFlowerInfoPhotoAdapter {

    FinalBitmap bitmap;

    public PublishFlowerInfoPhotoAdapterFriend(Context context, ArrayList<Pic> urlPaths) {
        super(context, urlPaths);
        bitmap = FinalBitmap.create(context);
    }

    @Override
    public int getCount() {
        return urlPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return urlPaths.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.publish_flower_info_photo_list_item_view_friend, null);
            holder = new ViewHolder();
            holder.photoIv = (ImageView) convertView.findViewById(R.id.publish_flower_info_photo_item_iv);
//            ViewGroup.LayoutParams para = holder.photoIv.getLayoutParams();
//            ViewGroup pr = (ViewGroup) holder.photoIv.getParent();
//            pr.setLayoutParams(new LinearLayout.LayoutParams(MyApplication.dp2px(context, 100), MyApplication.dp2px(context, 100)));

//            para.width = MyApplication.dp2px(context, 100);
//            para.height = MyApplication.dp2px(context, 100);

//            holder.photoIv.setLayoutParams(para);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bitmap.display(holder.photoIv, urlPaths.get(position).getUrl());
        return convertView;
    }

}
