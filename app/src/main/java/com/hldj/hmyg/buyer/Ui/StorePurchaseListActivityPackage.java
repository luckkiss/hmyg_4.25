package com.hldj.hmyg.buyer.Ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_package;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

import java.util.List;

import static com.hldj.hmyg.util.ConstantState.PUBLIC_TMP_SUCCEED;

/**
 * 报价列表   整包报价界面
 */
public class StorePurchaseListActivityPackage extends StorePurchaseListActivityAlongSecond {


//    @Override
//    public boolean setSwipeBackEnable() {
//        host = "admin/purchase/purchaseItemList";
//        return true;
//    }

    @Override
    protected void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
        super.initHeadBean(headPurchase);

        //修改 报价时间为   第一次结束时间

//          ((TextView) getView(R.id.tv_06)).setText("截止时间：" + 666);
        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.closeDate);
        getView(R.id.tv_show_tip).setVisibility(View.VISIBLE);

        getView(R.id.bottom_tv).setVisibility(View.VISIBLE);
        getView(R.id.bottom_btn).setVisibility(View.VISIBLE);
    }

    public void setEditAble(List<PurchaseItemBean_new> editAble, boolean flag) {
        for (PurchaseItemBean_new itemBean_new : editAble) {
            itemBean_new.editAble = flag;
        }
    }


    //打包报价   执行
    public void initPageBeans(List<PurchaseItemBean_new> data) {

        for (int i = 0; i < data.size(); i++) {
            shareBean.text += data.get(i).name + ",";
        }

        if (listAdapter == null) {
            listAdapter = new StorePurchaseListAdapter_new_package(StorePurchaseListActivityPackage.this, data, R.layout.list_item_store_purchase) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PUBLIC_TMP_SUCCEED) {

            SellerQuoteJsonBean tmp_quote = (SellerQuoteJsonBean) data.getSerializableExtra("bean");
//            ToastUtil.showShortToast("toast" + itemBean_new.toString());
//            try {
//                for (int i = 0; i < listAdapter.getDatas().size(); i++) {
//                    if (listAdapter.getDatas().get(i).id.equals(itemBean_new.id)) {
//                        listAdapter.getDatas().set(i, itemBean_new);
//                        listAdapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                onRefresh();
//            }
            ((StorePurchaseListAdapter_new_package) listAdapter).processListView(null,tmp_quote);


            ToastUtil.showLongToast("临时保存成功+tmp_quote" + tmp_quote.toString());
            D.i("=======临时保存成功======" + tmp_quote.toString());


        }


    }


//
//    @Override
//    protected void initSecondList(List<PurchaseItemBean_new> preBidList, List<PurchaseItemBean_new> unEditList) {
//
//        listAdapter = null;
//
//        setEditAble(preBidList, true);
//        setEditAble(unEditList, false);
//
//
//        List<PurchaseItemBean_new> data = new ArrayList<>();
//        data.addAll(preBidList);
//        if (unEditList != null && unEditList.size() != 0) {
//            PurchaseItemBean_new itemBean_new = new PurchaseItemBean_new();
//            itemBean_new.id = "-1";
//            data.add(itemBean_new);
//            data.addAll(unEditList);
//        }
//
//
//        for (int i = 0; i < data.size(); i++) {
//            shareBean.text += data.get(i).name + ",";
//        }
//
//        if (listAdapter == null) {
//            listAdapter = new StorePurchaseListAdapter_new_package(mActivity, data, R.layout.list_item_store_purchase) {
//                @Override
//                public String setCityName() {
//                    return cityName;
//                }
//
//                @Override
//                public Boolean isExpired() {
//                    return !shouldShow;
//                }
//
//                @Override
//                public String getItemId() {
//                    return purchaseFormId;
//                }
//
//                @Override
//                public boolean isNeedPreQuote() {
//                    return needPreQuote;
//                }
//
//            };
//            xListView.setAdapter(listAdapter);
//        } else {
//            listAdapter.addData(data);
//        }
//
//        if (listAdapter.getDatas().size() % pageSize == 0) {
//            pageIndex++;
//        } else {
//
//        }
//
//        onLoad();
//    }


    //一轮报价时 执行
//    @Override
//    public void initPageBeans(List<PurchaseItemBean_new> data) {
//      super. initPageBeans(data);
//
//    }


}
