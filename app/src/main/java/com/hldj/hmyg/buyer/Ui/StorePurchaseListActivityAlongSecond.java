package com.hldj.hmyg.buyer.Ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_along_second;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hy.utils.ToastUtil;

import java.util.List;

/**
 * 报价列表   直接跳过一轮 。 二轮报价
 */
public class StorePurchaseListActivityAlongSecond extends StorePurchaseListActivity {


    @Override
    public boolean setSwipeBackEnable() {
//        host = "admin/purchase/secondQuoteList";
//        host = "purchase/historyQuoteList";
//        ToastUtil.showLongToast(host);
//      host = "admin/purchase/secondQuoteList";
        host = "admin/purchase/purchaseItemList";

        return true;
    }

    @Override
    protected void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
        super.initHeadBean(headPurchase);

        if (headPurchase == null) return;
        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.attrData.closeDateStr);


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


    @Override
    public void doShare(ImageView imageView) {

//        ToastUtil.showShortToast("bbb");
//        super.doShare(imageView);


//        SharePopupWindow window = new SharePopupWindow(mActivity, constructionShareBean());
//        window.showAsDropDown(imageView);


        new com.zf.iosdialog.widget.AlertDialog(mActivity).builder()
                .setTitle("请选择分享方式")
                .setPositiveButton("整单分享", v1 -> {
                    D.e("采购单 分享");
//                    SharePopupWindow window = new SharePopupWindow(mActivity, constructionShareBean());
//                    window.showAsDropDown(imageView);

                    ComonShareDialogFragment.newInstance().setShareBean(constructionShareBean())
                            .show(getSupportFragmentManager(), "AlertDialog");


                }).setNegativeButton("部分品种分享", v2 -> {


            if (shareList == null || shareList.size() == 0) {
                ToastUtil.showShortToast("无选择品种列表");
                return;
            }
            PartShareActivity.shareList = shareList;
            PartShareActivity.start(mActivity);
            ToastUtil.showShortToast("tiao ");


        }).show();


    }


}
