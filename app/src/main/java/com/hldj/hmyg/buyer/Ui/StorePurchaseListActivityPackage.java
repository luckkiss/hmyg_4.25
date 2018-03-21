package com.hldj.hmyg.buyer.Ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_package;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hldj.hmyg.R.id.bottom_tv;
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

//      ((TextView) getView(R.id.tv_06)).setText("截止时间：" + 666);
        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.closeDate);
        getView(R.id.tv_show_tip).setVisibility(View.VISIBLE);

        getView(bottom_tv).setVisibility(View.VISIBLE);
        getView(R.id.bottom_btn).setVisibility(View.VISIBLE);
        getView(R.id.bottom_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tempBeans.isEmpty()) {
//                    ToastUtil.showLongToast("未全部报价!");
                    Log.w("onClick", "未全部报价: ");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                for (SellerQuoteJsonBean sellerQuoteJsonBean : tempBeans) {
                    buffer.append(sellerQuoteJsonBean.id + "|");
                }
//                ToastUtil.showLongToast("批量报价=======" + buffer);

                new BasePresenter()
                        .putParams("quoteTempIds", buffer.toString())
                        .doRequest("admin/quote/package/save", true, new HandlerAjaxCallBack() {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                ToastUtil.showLongToast(gsonBean.msg);

                                if (gsonBean.isSucceed()) {
//                                    StorePurchaseListActivityPackage.super.onRefresh();
//                                    requestHeadData();
                                    tempBeans.clear();
                                    initData();
                                    getView(R.id.bottom_btn).setSelected(false);
                                    tempMap.clear();
                                }

                            }
                        });
            }
        });
//        TextView bottom_tv = getView(R.id.bottom_tv);
//        bottom_tv.setText("共3个品种,已报价0个品种");

        getView(R.id.iv_zbbj).setVisibility(View.VISIBLE);


    }

    public void setEditAble(List<PurchaseItemBean_new> editAble, boolean flag) {
        for (PurchaseItemBean_new itemBean_new : editAble) {
            itemBean_new.editAble = flag;
        }
    }


    private int totalCount = 0;

    //打包报价   执行
    public void initPageBeans(List<PurchaseItemBean_new> data) {
        totalCount = data.size();
        TextView bottom_tv = getView(R.id.bottom_tv);
//        bottom_tv.setText("共" + totalCount + "个品种,已报价0个品种");
        String str = "共" + totalCount + "个品种,已填写 " + tempBeans.size() + "个品种的报价";
        StringFormatUtil stringFormatUtil = new StringFormatUtil(mActivity, str, totalCount + "", " " + tempBeans.size() + "", R.color.red).fillColor();
        bottom_tv
                .setText(stringFormatUtil.getResult());


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
            listAdapter.refreshState();
            listAdapter.addData(data);
        }

        if (listAdapter.getDatas().size() % pageSize == 0) {
            pageIndex++;
        } else {

        }

        onLoad();

        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);


//        if (tempBeans.size() == 0) {
//            return;
//        }

        for (PurchaseItemBean_new itemBean_new : data) {
            D.i(" ===footSellerQuoteListJson===" + itemBean_new.footSellerQuoteListJson);

            itemBean_new.footSellerQuoteListJson = getTempBeanById(itemBean_new.id);

            if (itemBean_new.footSellerQuoteListJson != null) {
                tempBeans.add(itemBean_new.footSellerQuoteListJson);
            }

        }


        String str1 = "共" + totalCount + "个品种,已填写" + " " + tempBeans.size() + " 个品种的报价";

        StringFormatUtil stringFormatUtil1 = new StringFormatUtil(mActivity, str1, totalCount + "", " " + tempBeans.size() + " ", R.color.red).fillColor();

        ((TextView) getView(R.id.bottom_tv))
                .setText(stringFormatUtil1.getResult());

        if (totalCount == tempBeans.size()) {
            getView(R.id.bottom_btn).setSelected(true);
        }


    }


    @Override
    protected void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    /* 打包报价  临时保存集合   成功保存之后  删除   */
    private List<SellerQuoteJsonBean> tempBeans = new ArrayList<>();

    /* 临时保存 集合  */
    public static Map<String, SellerQuoteJsonBean> tempMap = new HashMap<>();

    /* id  每个采购单的    id  */
    public static void saveTempBeanById(String id, SellerQuoteJsonBean tempBean) {
        tempMap.put(id, tempBean);
        Log.i("getTempBeanById", "保存到集合 临时集合里面    ---采购单 - " + id);
    }

    public static SellerQuoteJsonBean getTempBeanById(String id) {
        Log.i("getTempBeanById", " 临时集合里面  ---   存储的数据    id " + id);
        return tempMap.get(id);
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


//            for (SellerQuoteJsonBean tempBean : tempBeans) {
//                if (tempBean.id.equals(tmp_quote.id)) {
//                    tempBeans.remove(tempBean);
//                    Log.i("remove", "onActivityResult: 删除  旧的数据  id = " + tempBean.id);
//                }
//            }
//
//            Iterator<SellerQuoteJsonBean> iterator = tempBeans.iterator();
//
//            while (iterator.hasNext()) {
//                SellerQuoteJsonBean jsonBean = iterator.next();
//                if (jsonBean.id.equals(tmp_quote.id)) {
//                    iterator.remove();
//                }
//            }
//http://blog.csdn.net/androidboy365/article/details/50540202/

            if (tempBeans.size() > 0) {
                for (int i = 0; i < tempBeans.size(); i++) {
                    if (tmp_quote.id.equals(tempBeans.get(i).id)) {
                        tempBeans.remove(tmp_quote);
                        Log.i("remove", "onActivityResult: 删除  旧的数据  id = " + tempBeans.get(i).id);
                    }
                }
            }


            tempBeans.add(tmp_quote);


            ((StorePurchaseListAdapter_new_package) listAdapter).processListView(null, tmp_quote);

            listAdapter.notifyDataSetChanged();


//            ToastUtil.showLongToast("临时保存成功+tmp_quote" + tmp_quote.toString());
            D.i("=======临时保存成功======" + tmp_quote.toString());


            String str = "共" + totalCount + "个品种,已填写" + " " + tempBeans.size() + " 个品种的报价";

            StringFormatUtil stringFormatUtil = new StringFormatUtil(mActivity, str, totalCount + "", " " + tempBeans.size() + " ", R.color.red).fillColor();

            ((TextView) getView(R.id.bottom_tv))
                    .setText(stringFormatUtil.getResult());


            if (totalCount == tempBeans.size()) {
                getView(R.id.bottom_btn).setSelected(true);
            }
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


//    一轮报价时 执行
//    @Override
//    public void initPageBeans(List<PurchaseItemBean_new> data) {
//      super. initPageBeans(data);
//
//    }


}
