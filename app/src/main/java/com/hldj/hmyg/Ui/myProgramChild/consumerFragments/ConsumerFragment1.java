package com.hldj.hmyg.Ui.myProgramChild.consumerFragments;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hhl.library.FlowTagLayout;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.search.IConsumerSearch;
import com.hldj.hmyg.CallBack.search.ISearch;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramPurchaseActivity;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.bean.enums.PurchaseStatus;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hy.utils.SpanUtils;
import com.hy.utils.TagAdapter;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 我的项目  ---  采购选标  状态 界面   -- 采购中
 */

public class ConsumerFragment1 extends BaseRecycleViewFragment<PurchaseBean> {


    private String searchKey = "";

    public boolean isVisibleMustRefresh = false;

    private static final String parameterKey = "parameterKey";

    public static ConsumerFragment1 newInstance(String status) {
        ConsumerFragment1 consumerFragment1 = new ConsumerFragment1();
        addArgument(parameterKey, status, consumerFragment1);
        return consumerFragment1;
    }

    @Override
    public void onFragmentVisibleChange(boolean b) {
        if (b && mCoreRecyclerView != null && !searchKey.equals(getSearchKey()) && isVisibleMustRefresh && !isFirstVisible) {
            // 对  自己可见 判断 searchKey  与 父类 search key  不相等。进行刷新
            searchKey = getSearchKey();
            isVisibleMustRefresh = false;
            mCoreRecyclerView.onRefresh();

            D.i("------------刷新数据---------");
        } else {
            D.i("------------不进行刷新---------");
        }
    }

    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<PurchaseBean>>>>() {
        }.getType();

        if (!searchKey.equals(getSearchKey())) {
            searchKey = getSearchKey();
        }


        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page)
                .putParams("searchKey", searchKey)
                .putParams("status", getStringArgument(parameterKey))
//                .putParams("status", PurchaseStatus.open.enumValue)
                .doRequest("admin/purchase/listByConsumer", new HandlerAjaxCallBackPage<PurchaseBean>(mActivity, type,mCoreRecyclerView) {
                    @Override
                    public void onRealSuccess(List<PurchaseBean> purchases) {
                        mCoreRecyclerView.getAdapter().addData(purchases);
                    }
                });


    }

    @Override
    protected void onRecycleViewInited(CoreRecyclerView coreRecyclerView) {

        // 初始化结束调用  做一些额外添加 比如 背景  固定头 divder 高度什么的


    }

    int item_id = R.layout.item_fragment_consumer;
    ;

    @Override
    protected void doConvert(BaseViewHolder myViewHolder, PurchaseBean item, NeedSwipeBackActivity mActivity) {
        D.i("----------doConvert------------");

        StatesProxy.doConverByState1(myViewHolder, item, mActivity);


        myViewHolder.addOnClickListener(R.id.bottom, v -> {

//            ToastUtil.showLongToast("bottom");
            结束报价(myViewHolder, item, mActivity);
        });

        myViewHolder
                .setText(R.id.tv_pos, (myViewHolder.getAdapterPosition() + 1) + "")
                .setVisible(R.id.tv_pos, true)

        ;


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

        mMobileFlowTagLayout.ClearClickAble(true, view1 -> myViewHolder.convertView.setOnClickListener(view -> ProgramPurchaseActivity.start(mActivity, item.id, getStringArgument(parameterKey))));


//            Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩")

        //+ "采购单"
        String html_source = item.name;
        String html_source1 = "(" + item.num + ")";

        SpannableStringBuilder builder = new SpanUtils().
                append(item.name)
                .append("(" + item.num + ")").setForegroundColor(getResources().getColor(R.color.text_color999)).setFontSize(13, true)
                .create();

        tv_01.setText(builder);

        tv_03.setText(item.ciCity.fullName);

        SpannableStringBuilder project = new SpanUtils()
                .append("项目名称：").setForegroundColor(getResources().getColor(R.color.text_color999))
                .append(item.projectName)
                .create();
        tv_04.setText(project);


//        ToastUtil.showShortToast("item.showConsumerName" + item.showConsumerName);
        if (item.showConsumerName) {
            SpannableStringBuilder publishData = new SpanUtils()
                    .append("发布日期：").setForegroundColor(getResources().getColor(R.color.text_color999))
                    .append(item.attrData.publishDateStr)
                    .create();
            tv_05.setText(publishData);
            tv_05.setVisibility(View.VISIBLE);
        } else {
            tv_05.setVisibility(View.GONE);
        }


//        if (MyApplication.getUserBean().showQuoteCount) {
        if (item.quoteCountJson > 0) {
//                StringFormatUtil fillColor = new StringFormatUtil(mActivity, "报价条数："
//                        + item.quoteCountJson + "条报价", item.quoteCountJson + "", R.color.price_orige)
//                        .fillColor();
            SpannableStringBuilder quoteCount = new SpanUtils()
                    .append("报价条数：").setForegroundColor(getResources().getColor(R.color.text_color999))
                    .append(item.quoteCountJson + "").setForegroundColor(getResources().getColor(R.color.price_orige))
                    .append("条报价")
                    .create();
            tv_11.setText(quoteCount);
        } else {
            tv_11.setText("暂无报价");
        }
        tv_11.setVisibility(View.VISIBLE);
//        } else {
//            tv_11.setVisibility(View.GONE);
//        }


        if (!TextUtils.isEmpty(item.itemCountJson)) {
            if (getStringArgument(parameterKey).equals(PurchaseStatus.expired.enumValue) || getStringArgument(parameterKey).equals(PurchaseStatus.published.enumValue)) {
                D.i("--------已开标----或者 报价中----");

                SpannableStringBuilder builder1 = new SpanUtils()
                        .append("共有")
                        .append(item.itemCountJson).setForegroundColor(getResources().getColor(R.color.main_color))
                        .append("个品种")
                        .append(String.format("  (%s个已开标，报价中%s个)", item.attrData.openCount, item.attrData.unOpenCount))
                        .create();
                tv_10.setText(builder1);
                myViewHolder.setVisible(R.id.bottom, getStringArgument(parameterKey).equals(PurchaseStatus.expired.enumValue) );

            } else {
                D.i("--------非开标--------");

                SpannableStringBuilder builder1 = new SpanUtils()
                        .append("共有")
                        .append(item.itemCountJson).setForegroundColor(getResources().getColor(R.color.main_color))
                        .append("个品种")
                        .create();
                tv_10.setText(builder1);
                myViewHolder.setVisible(R.id.bottom, false);
            }


        } else {
            tv_10.setText("暂无报价");
        }

        SpanUtils spanUtils = new SpanUtils()
                .append("截止时间：").setForegroundColor(getResources().getColor(R.color.text_color999));
        if (!item.attrData.isOpen) {
            spanUtils.append(item.attrData.closeDateStr).setForegroundColor(getResources().getColor(R.color.price_orige));
        } else {
            spanUtils.append(item.attrData.closeDateStr).setForegroundColor(getResources().getColor(R.color.text_color333));
        }
        tv_caozuo01.setText(spanUtils.create());
//        tv_caozuo01.setText("截止时间：" + item.attrData.closeDateStr);


        myViewHolder.convertView.setOnClickListener(view -> ProgramPurchaseActivity.start(mActivity, item.id, getStringArgument(parameterKey)));


    }

    private void 结束报价(BaseViewHolder myViewHolder, PurchaseBean item, NeedSwipeBackActivity mActivity) {
        Observable.create(this::createAlert).filter(grand -> grand).subscribe(grand -> 取消报价(myViewHolder, item, mActivity), throwable -> ToastUtil.showLongToast(throwable.getMessage()));
    }

    private void createAlert(ObservableEmitter<Boolean> observableEmitter) {
        new AlertDialog(mActivity).builder()
                .setTitle("确定结束选标?")
                .setNegativeButton("取消", v -> {
                    observableEmitter.onNext(false);
                    observableEmitter.onComplete();
                }).setPositiveButton("确定", v -> {
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }).show();//取消
    }

    private void 取消报价(BaseViewHolder myViewHolder, PurchaseBean item, NeedSwipeBackActivity mActivity) {

//        ToastUtil.showLongToast("执行取消报价接口");
        new BasePresenter()
                .putParams("id", item.id)
                .doRequest("admin/purchase/finish", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        if (ConsumerFragment1.this.mActivity instanceof ISearch) {
                            ((IConsumerSearch) ConsumerFragment1.this.mActivity).doRefreshCount();
                            ((IConsumerSearch) ConsumerFragment1.this.mActivity).doRefreshOneFragment(2);
                            // 0 当前界面 1  报价中  2  已结束界面（ConsumerFragment1 类）
                        }
                        mCoreRecyclerView.onRefresh();
                    }
                });
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
