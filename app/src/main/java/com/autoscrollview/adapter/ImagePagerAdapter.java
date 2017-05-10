/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with trinea.cn.
 */
package com.autoscrollview.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.autoscrollview.jakewharton.salvage.RecyclingPagerAdapter;
import com.autoscrollview.utils.ListUtils;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.WebActivity;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.white.utils.StringUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.HashMap;
import java.util.List;

/**
 * ImagePagerAdapter
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<HashMap<String, Object>> imageIdList;

    private int size;
    private boolean isInfiniteLoop;
    FinalBitmap finalBitmap;

    public ImagePagerAdapter(Context context,
                             List<HashMap<String, Object>> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = ListUtils.getSize(imageIdList);
        isInfiniteLoop = false;
        finalBitmap = FinalBitmap.create(context);

    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils
                .getSize(imageIdList);
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */

    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ScaleType.FIT_XY);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (imageIdList.get(position).get("url").toString().startsWith("http")) {
            // FinalBitmap.create(context).display(
            // holder.imageView,
            // imageIdList.get(position).get("url")
            // .toString());
//            ImageLoader.getInstance().displayImage(imageIdList.get(position).get("url").toString(), holder.imageView);
            finalBitmap.display(holder.imageView, imageIdList.get(position).get("url").toString());
            // holder.imageView.setImageResource(imageIdList.get(getPosition(position)));
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }

        holder.imageView.setOnClickListener(v -> {

            if (imageIdList.get(position).get("href") == null) {
                return;
            }
            String json = imageIdList.get(position).get("href").toString();
            D.e("setOnClickListener");
            if (StringUtil.isHttpUrlPicPath(json)) {

                Intent toWebActivity3 = new Intent(context,
                        WebActivity.class);
                toWebActivity3.putExtra("title", imageIdList.get(position)
                        .get("name").toString());
                toWebActivity3.putExtra("url", imageIdList.get(position)
                        .get("href").toString());
                ((Activity) context).startActivityForResult(toWebActivity3,
                        1);
            } else if (!TextUtils.isEmpty(json)) {

                TypeBean typeBean = GsonUtil.formateJson2Bean(json, TypeBean.class);

                if (typeBean == null) {
                    return;
                }


                switch (typeBean.type) {
                    case "seedling":
                        //详情
                        FlowerDetailActivity.start2Activity(context, typeBean.sourceId);
                        break;
                    case "store":
                        StoreActivity.start2Activity(context, typeBean.sourceId);
                        break;
                    case "purchase":
                        //报价 详情
                        PurchaseDetailActivity.start2Activity((Activity) context, typeBean.sourceId);
                        break;


                }


            }

        });
        return view;
    }

    private static class TypeBean {
        public String type = "";
        public String sourceId = "";
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
