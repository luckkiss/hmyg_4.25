package com.hldj.hmyg.buyer.Ui;

import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_second;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 报价列表   二轮报价
 */
public class StorePurchaseListActivitySecond extends StorePurchaseListActivity {


    @Override
    public boolean setSwipeBackEnable() {
        host = "admin/purchase/secondQuoteList";
        return true;
    }

    @Override
    protected void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
        super.initHeadBean(headPurchase);

        //修改 报价时间为   第一次结束时间

//          ((TextView) getView(R.id.tv_06)).setText("截止时间：" + 666);
        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.closeDate);






    }

    public void setEditAble(List<PurchaseItemBean_new> editAble, boolean flag) {
        for (PurchaseItemBean_new itemBean_new : editAble) {
            itemBean_new.editAble = flag;
        }
    }

    @Override
    protected void initSecondList(List<PurchaseItemBean_new> preBidList, List<PurchaseItemBean_new> unEditList) {

        setEditAble(preBidList, true);
        setEditAble(unEditList, false);


        List<PurchaseItemBean_new> data = new ArrayList<>();
        data.addAll(preBidList);
        data.addAll(unEditList);

        for (int i = 0; i < data.size(); i++) {
            shareBean.text += data.get(i).name + ",";
        }

        if (listAdapter == null) {
            listAdapter = new StorePurchaseListAdapter_new_second(mActivity, data, R.layout.list_item_store_purchase) {
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


    //一轮报价时 执行
//    @Override
//    public void initPageBeans(List<PurchaseItemBean_new> data) {
//      super. initPageBeans(data);
//
//    }


}
