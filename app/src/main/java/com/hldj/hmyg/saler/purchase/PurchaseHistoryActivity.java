package com.hldj.hmyg.saler.purchase;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hhl.library.FlowTagLayout;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.TagAdapter;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import static com.hldj.hmyg.saler.Adapter.PurchaseListAdapter.jump;

/**
 * 快速报价
 */
public class PurchaseHistoryActivity extends BaseMVPActivity {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(mActivity);
        /**
         *     public void req3(ResultCallBack<List<PurchaseBean>> callBack, int index) {
         showLoading();
         //根据参数请求数据
         new PurchasePyMapPresenter()
         .putParams("pageSize", 10 + "")
         .putParams("pageIndex", index + "")
         .addResultCallBack(callBack)
         .requestDatas("purchase/historyList");
         }

         */


        recycle.init(new BaseQuickAdapter<PurchaseBean, BaseViewHolder>(R.layout.list_item_purchase_list_new_three) {
            @Override
            protected void convert(BaseViewHolder myViewHolder, PurchaseBean item) {

                /**
                 *    /*是否显示简易报价 图标*/


                ImageView iv_jianyi = myViewHolder.getView(R.id.iv_jianyi);
                iv_jianyi.setVisibility(item.needPreQuote ? View.VISIBLE : View.GONE);

                if (item.status.equals("expired")) {
                    iv_jianyi.setVisibility(View.GONE);
                }
//        iv_jianyi.setVisibility(item.isPackage ? View.VISIBLE : View.GONE);
//        iv_jianyi.setVisibility(item.status.equals("expired") ? View.GONE : View.VISIBLE);/* 过期了 就不显示了 */
                if (item.status.equals("expired")) {
                    iv_jianyi.setVisibility(View.GONE);/* 过期了 就不显示了 */
                } else if (item.isPackage) {
                    iv_jianyi.setVisibility(View.VISIBLE);/* 没过期 并且 是 打包报价 */
                } else {
                    iv_jianyi.setVisibility(View.GONE);/* 什么都不是 */
                }


                int id = R.layout.list_item_purchase_list_new;

                TextView tv_01 = myViewHolder.getView(R.id.tv_01);
                TextView tv_03 = myViewHolder.getView(R.id.tv_03);
                TextView tv_04 = myViewHolder.getView(R.id.tv_04);
                TextView tv_05 = myViewHolder.getView(R.id.tv_05);
//            TextView tv_08 =   myViewHolder.getView(R.id.tv_08);
                TextView tv_10 = myViewHolder.getView(R.id.tv_10);
                TextView tv_11 = myViewHolder.getView(R.id.tv_11);
                TextView tv_caozuo01 = myViewHolder.getView(R.id.tv_caozuo01);

                FlowTagLayout mMobileFlowTagLayout = myViewHolder.getView(R.id.mobile_flow_layout);

                // 移动研发标签
                TagAdapter<String> mMobileTagAdapter = new TagAdapter<>(mActivity);
                // mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);


                mMobileTagAdapter.onlyAddAll(item.itemNameList);

                mMobileFlowTagLayout.ClearClickAble(true, view1 -> jump((Activity) mActivity, item));

//            Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩")

                //+ "采购单"
                String html_source = item.name;
                String html_source1 = "(" + item.num + ")";

                tv_01.setText(Html.fromHtml(html_source + "<font color='#FFA19494'><small>" + html_source1 + "</small></font>"));

                tv_03.setText(item.cityName);
                tv_04.setText("采购商家：" + item.buyer.companyName);


//        ToastUtil.showShortToast("item.showConsumerName" + item.showConsumerName);
                if (item.showConsumerName) {
                    tv_05.setText("用苗单位：" + item.consumerFullName);
                    tv_05.setVisibility(View.VISIBLE);
                } else {
                    tv_05.setVisibility(View.GONE);
                }


                if (MyApplication.getUserBean().showQuoteCount) {
                    if (item.quoteCountJson > 0) {
                        StringFormatUtil fillColor = new StringFormatUtil(mActivity, "已有"
                                + item.quoteCountJson + "条报价", item.quoteCountJson + "", R.color.price_orige)
                                .fillColor();
                        tv_11.setText(fillColor.getResult());

                    } else {
                        tv_11.setText("暂无报价");
                    }
                    tv_11.setVisibility(View.VISIBLE);
                } else {
                    tv_11.setVisibility(View.GONE);
                }


                if (!TextUtils.isEmpty(item.itemCountJson)) {
                    StringFormatUtil fillColor = new StringFormatUtil(mActivity, "共有" + item.itemCountJson + "个品种", item.itemCountJson + "", R.color.green)
                            .fillColor();
                    if (null != fillColor.getResult()) {
                        tv_10.setText(fillColor.getResult());
                    } else {
                        tv_10.setText("暂无报价");
                    }
                } else {
                    tv_10.setText("暂无报价");
                }

                tv_caozuo01.setText("截止时间：" + item.attrData.closeDateStr);


                myViewHolder.getConvertView().setOnClickListener(v -> {
                    jump((Activity) mActivity, item);
                });


            }
        }).openLoadMore(20, page -> {
            requestDataByPage(page);
        }).openRefresh();

        recycle.onRefresh();


    }

    private void requestDataByPage(int page) {
        //根据参数请求数据
//        new PurchasePyMapPresenter()
//                .putParams("pageIndex", page + "")
//                .addResultCallBack(callBack)
//                .requestDatas("purchase/historyList");
        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<PurchaseBean>>>>() {
        }.getType();
        new BasePresenter()
                .putParams("pageIndex", page + "")
                .doRequest("purchase/historyList", new HandlerAjaxCallBackPage<PurchaseBean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<PurchaseBean> purchaseBeans) {
                        recycle.getAdapter().addData(purchaseBeans);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                    }
                });

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.purchase_history;
    }


    @Override
    public String setTitle() {
        return "历史采购单";
    }

    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, PurchaseHistoryActivity.class));

    }

}
