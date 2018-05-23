package com.hldj.hmyg.Ui.myProgramChild.consumerFragments;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hhl.library.FlowTagLayout;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.search.ISearch;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramPurchaseActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.SpanUtils;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.TagAdapter;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.saler.Adapter.PurchaseListAdapter.jump;

/**
 * 我的项目  ---  采购选标  状态 界面   -- 采购中
 */

public class ConsumerFragment1 extends BaseRecycleViewFragment<PurchaseBean> {


    private String searchKey;

    private static final String parameterKey = "parameterKey";

    public static ConsumerFragment1 newInstance(String status) {
        ConsumerFragment1 consumerFragment1 = new ConsumerFragment1();
        addArgument(parameterKey, status, consumerFragment1);
        return consumerFragment1;
    }


    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<PurchaseBean>>>>() {
        }.getType();


        new BasePresenter()
                .putParams("searchKey", getSearchKey())
                .putParams("status", getStringArgument(parameterKey))
//                .putParams("status", PurchaseStatus.open.enumValue)
                .doRequest("admin/purchase/listByConsumer", new HandlerAjaxCallBackPage<PurchaseBean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<PurchaseBean> purchases) {
                        mCoreRecyclerView.getAdapter().addData(purchases);


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mCoreRecyclerView.selfRefresh(false);
                    }
                });


    }

    @Override
    protected void onRecycleViewInited(CoreRecyclerView coreRecyclerView) {

        // 初始化结束调用  做一些额外添加 比如 背景  固定头 divder 高度什么的


    }

    @Override
    protected void doConvert(BaseViewHolder myViewHolder, PurchaseBean item, NeedSwipeBackActivity mActivity) {
        D.i("----------doConvert------------");


//        ToastUtil.showLongToast("hellow world");


        /*是否显示简易报价 图标*/
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

        mMobileFlowTagLayout.ClearClickAble(true, view1 -> jump(mActivity, item));

//            Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩")

        //+ "采购单"
        String html_source = item.name;
        String html_source1 = "(" + item.num + ")";

        SpannableStringBuilder builder = new SpanUtils().
                append(item.name)
                .append("(" + item.num + ")").setForegroundColor(getResources().getColor(R.color.text_color999)).setFontSize(12, true)
                .create();

        tv_01.setText(builder);

        tv_03.setText(item.cityName);
        tv_04.setText("项目名称：" + item.projectName);


//        ToastUtil.showShortToast("item.showConsumerName" + item.showConsumerName);
        if (item.showConsumerName) {
            tv_05.setText("发布日期：" + item.attrData.publishDateStr);
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


        myViewHolder.convertView.setOnClickListener(view -> ProgramPurchaseActivity.start(mActivity, item.id));


    }

    @Override
    public int bindRecycleItemId() {
        return R.layout.item_fragment_consumer;
    }


    public String getSearchKey() {
        if (mActivity instanceof ISearch) {
            D.i("activity 是  Isearch 接口");
            return ((ISearch) mActivity).getSearchKey();
        } else {
            D.i("不 是  Isearch 接口  搜索key 默认返回空");
            return "";

        }
    }
}
