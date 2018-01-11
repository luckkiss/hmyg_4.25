package com.hldj.hmyg.buyer.Ui;

import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_along_second;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;

import java.util.List;

/**
 * 报价列表   直接跳过一轮 。 二轮报价
 */
public class StorePurchaseListActivityAlongSecond extends StorePurchaseListActivity {


    @Override
    public boolean setSwipeBackEnable() {
//        host = "admin/purchase/secondQuoteList";
        return true;
    }

    @Override
    protected void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
        super.initHeadBean(headPurchase);

        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.preCloseDate);
    }

    @Override
    protected void initSecondList(List<PurchaseItemBean_new> preBidList, List<PurchaseItemBean_new> unEditList) {

    }


    //一轮报价时 执行
    public void initPageBeans(List<PurchaseItemBean_new> data) {

        for (int i = 0; i < data.size(); i++) {
            shareBean.text += data.get(i).name + ",";
        }

        if (listAdapter == null) {
            listAdapter = new StorePurchaseListAdapter_new_along_second(StorePurchaseListActivityAlongSecond.this, data, R.layout.list_item_store_purchase) {
                @Override
                public String setCityName() {
                    return cityName;
                }

                @Override
                public Boolean isExpired() {
                    return !shouldShow;
                }

                @Override
                public String getItemId() {
                    return purchaseFormId;
                }

                @Override
                public boolean isNeedPreQuote() {
                    return needPreQuote;
                }
            };
            xListView.setAdapter(listAdapter);
        } else {
            listAdapter.addData(data);
        }

        if (listAdapter.getDatas().size() % pageSize == 0) {
            pageIndex++;
        } else {

        }

        onLoad();

    }


}
