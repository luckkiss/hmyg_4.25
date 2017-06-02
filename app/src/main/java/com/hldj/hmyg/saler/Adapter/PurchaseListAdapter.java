package com.hldj.hmyg.saler.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.hhl.library.FlowTagLayout;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.TagAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class PurchaseListAdapter extends GlobBaseAdapter<PurchaseBean> {

    public PurchaseListAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setConverView(ViewHolders myViewHolder, PurchaseBean item, int position) {

        int id = R.layout.list_item_purchase_list_new;

        TextView tv_01 = myViewHolder.getView(R.id.tv_01);
        TextView tv_03 = myViewHolder.getView(R.id.tv_03);
        TextView tv_04 = myViewHolder.getView(R.id.tv_04);
//            TextView tv_08 =   myViewHolder.getView(R.id.tv_08);
        TextView tv_10 = myViewHolder.getView(R.id.tv_10);
        TextView tv_11 = myViewHolder.getView(R.id.tv_11);
        TextView tv_caozuo01 = myViewHolder.getView(R.id.tv_caozuo01);

        FlowTagLayout mMobileFlowTagLayout = (FlowTagLayout) myViewHolder.getView(R.id.mobile_flow_layout);
        // 移动研发标签
        TagAdapter<String> mMobileTagAdapter = new TagAdapter<>(context);
        // mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);

        mMobileTagAdapter.onlyAddAll(item.itemNameList);
//            Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩")

        String html_source = item.blurProjectName + "采购单";
        String html_source1 = "(" + item.num + ")";

        tv_01.setText(Html.fromHtml(html_source + "<font color='#FFA19494'><small>" + html_source1 + "</small></font>"));

        tv_03.setText(item.cityName);
        tv_04.setText("采购商家：" + item.buyer.companyName);

        if (MyApplication.getUserBean().showQuoteCount) {
            if (item.quoteCountJson > 0) {
                StringFormatUtil fillColor = new StringFormatUtil(context, "已有"
                        + item.quoteCountJson + "条报价", item.quoteCountJson + "", R.color.price_orige)
                        .fillColor();
                tv_11.setText(fillColor.getResult());
            } else {
                tv_11.setText("暂无报价");
            }
        }


        if (!TextUtils.isEmpty(item.itemCountJson)) {
            StringFormatUtil fillColor = new StringFormatUtil(context, "已有" + item.itemCountJson + "条品种", item.itemCountJson + "", R.color.green)
                    .fillColor();
            if (null != fillColor.getResult()) {
                tv_10.setText(fillColor.getResult());
            } else {
                tv_10.setText("暂无报价");
            }
        } else {
            tv_10.setText("暂无报价");
        }

        tv_caozuo01.setText("截止时间：" + item.closeDate);


        myViewHolder.getConvertView().setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent intent = new Intent(context,
                    StorePurchaseListActivity.class);
            intent.putExtra("purchaseFormId", item.purchaseFormId);
            intent.putExtra("title", item.num);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });


    }


}
