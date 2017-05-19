package com.hldj.hmyg.M;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.base.ViewHolders;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class BProduceAdapt extends GlobBaseAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean> {
    public BProduceAdapt(Context context, List<BPageGsonBean.DatabeanX.Pagebean.Databean> data, int layoutId) {
        super(context, data, layoutId);
    }


    @Override
    public void setConverView(ViewHolders myViewHolder, BPageGsonBean.DatabeanX.Pagebean.Databean item, int position) {
        int layout_id = R.layout.list_view_seedling_new;

        ImageView iv_img = myViewHolder.getView(R.id.iv_img);
        TextView tv_01 = myViewHolder.getView(R.id.tv_01);
        MySwipeAdapter.setSrcByType(tv_01, item.plantType);


        TextView tv_right_top = myViewHolder.getView(R.id.tv_right_top);

        if (item.attrData.ziying) {//自营显示票
            tv_right_top.setVisibility(View.VISIBLE);
        } else {
            tv_right_top.setVisibility(View.GONE);
        }


        TextView tv_02 = myViewHolder.getView(R.id.tv_02);
        tv_02.setText(item.name);

        TextView tv_03 = myViewHolder.getView(R.id.tv_03);
        tv_03.setText(item.ciCity.fullName);


        TextView tv_04 = myViewHolder.getView(R.id.tv_04);
        tv_04.setText(item.specText);

        TextView tv_06 = myViewHolder.getView(R.id.tv_06);
        setPublishName(tv_06,
                item.ownerJson.companyName,
                item.ownerJson.publicName,
                item.ownerJson.realName);


        TextView tv_07 = myViewHolder.getView(R.id.tv_07);
        ProductListAdapter.setPrice(tv_07, item.maxPrice, item.minPrice, item.isNego);


        TextView tv_08 = myViewHolder.getView(R.id.tv_08);
        tv_08.setText("元/" + item.unitTypeName);
        TextView tv_09 = myViewHolder.getView(R.id.tv_09);
        tv_09.setText("库存：" + item.count);

        finalBitmap.display(iv_img, item.smallImageUrl);


        //item 点击事件
        myViewHolder.getConvertView().setOnClickListener(v -> {
            FlowerDetailActivity.start2Activity(context, "seedling_list", item.id);
        });


    }

    private void setPublishName(TextView tv_06, String... str) {

        for (int i = 0; i < str.length; i++) {
            if (!TextUtils.isEmpty(str[i])) {
                tv_06.setText("发布人" + str[i]);
                return;
            } else {
                tv_06.setText("");
            }
        }
    }

}
