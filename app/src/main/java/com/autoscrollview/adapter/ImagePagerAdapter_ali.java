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
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.WebActivity;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.GsonUtil;
import com.white.utils.StringUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * 首页轮播  顶部
 */
public class ImagePagerAdapter_ali extends RecyclingPagerAdapter {

    private Context context;
    private List<BannarBean> bannanList;

    private int size;
    private boolean isInfiniteLoop;
    FinalBitmap finalBitmap;

    public ImagePagerAdapter_ali(Context context, List<BannarBean> bannanList) {
        this.context = context;
        this.bannanList = bannanList;
        this.size = ListUtils.getSize(bannanList);
        isInfiniteLoop = false;
        finalBitmap = FinalBitmap.create(context);

    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(bannanList);
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


        BannarBean mbBannarBean = (BannarBean) bannanList.get(position);
        mbBannarBean.url = mbBannarBean.imageJson.url;

        if (mbBannarBean.url.startsWith("http")) {
            finalBitmap.display(holder.imageView, mbBannarBean.url);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }

        holder.imageView.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mbBannarBean.href)) {
                return;
            }


            if (StringUtil.isHttpUrlPicPath(mbBannarBean.href)) {

                Intent toWebActivity3 = new Intent(context,
                        WebActivity.class);
                toWebActivity3.putExtra("title", mbBannarBean.name);
                toWebActivity3.putExtra("url", mbBannarBean.url);
                ((Activity) context).startActivityForResult(toWebActivity3, 1);
            } else if (!TextUtils.isEmpty(mbBannarBean.href)) {

                TypeBean typeBean = GsonUtil.formateJson2Bean(mbBannarBean.href, TypeBean.class);

                if (typeBean == null) {
                    return;
                }
                switch (typeBean.type) {
                    case "seedling":
                        //详情
                        FlowerDetailActivity.start2Activity(context, typeBean.sourceId);
                        break;
                    case "store":
                        //首页轮播  顶部
                        StoreActivity_new.start2Activity(context, typeBean.sourceId);
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


    public static class BannarBean {
        public String name = "";//标题
        public String url = "";//头像地址
        public String href = "";//webbiew 跳转连接
        public ImagesJsonBean imageJson = new ImagesJsonBean();
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
    public ImagePagerAdapter_ali setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
