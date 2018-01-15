package com.hldj.hmyg.buyer.Ui;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter_new_along_second;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 报价列表   直接跳过一轮 。 二轮报价
 */
public class StorePurchaseListActivityHistory extends StorePurchaseListActivity {

    @Override
    public boolean setSwipeBackEnable() {
//        host = "admin/purchase/secondQuoteList";
        host = "purchase/historyQuoteList";
//        ToastUtil.showLongToast(host);
//      host = "admin/purchase/secondQuoteList";
        return true;
    }

    @Override
    protected void requestHeadData() {
        // 不进行头部处理，，，，直接再主数据中获取head
        Log.i("requestHeadData", "不进行头部处理，，，，直接再主数据中获取head: ");
    }


    @Override
    protected void initHeadBean(PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase) {
        super.initHeadBean(headPurchase);

        ((TextView) getView(R.id.tv_06)).setText("截止时间：" + headPurchase.preCloseDate);
    }

    @Override
    protected void initSecondList(List<PurchaseItemBean_new> preBidList, List<PurchaseItemBean_new> unEditList) {

    }


    @Override
    protected void processJson(String json) {

        //对   json  进行   处理再输出


        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<PurchaseItemBean_new>>>>() {
        }.getType();

//                SimpleGsonBean_new<SimplePageBean<List<PurchaseItemBean_new>>> bean = GsonUtil.formateJson2Bean(t, beanType);
        //SimplePageBean<List<Moments>>
        SimpleGsonBean_new<SimplePageBean<List<PurchaseItemBean_new>>> bean = GsonUtil.formateJson2Bean(json, beanType);


//                ToastUtil.showLongToast(gsonBean.msg);
        if (bean.code.equals(ConstantState.SUCCEED_CODE)) {
            if (bean.data.page.data != null) {
                initPageBeans(bean.data.page.data);
            }else {
                initPageBeans(new ArrayList<>());
            }

            if (bean.data.headPurchase != null) {
//                      initHistoryHead();
                initHeadBean(bean.data.headPurchase);
            }


//                    is = gsonBean.data.expired;
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            loadingLayout.setErrorText(bean.msg);
            loadingLayout.setStatus(LoadingLayout.Error);
        }
    }

    //一轮报价时 执行
    public void initPageBeans(List<PurchaseItemBean_new> data) {

        for (int i = 0; i < data.size(); i++) {
            shareBean.text += data.get(i).name + ",";
        }

        if (listAdapter == null) {
            listAdapter = new StorePurchaseListAdapter_new_along_second(StorePurchaseListActivityHistory.this, data, R.layout.list_item_store_purchase) {
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
