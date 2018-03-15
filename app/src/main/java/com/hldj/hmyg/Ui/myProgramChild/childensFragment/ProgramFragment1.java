package com.hldj.hmyg.Ui.myProgramChild.childensFragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.coorchice.library.SuperTextView;
import com.hhl.library.FlowTagLayout;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramDirctActivity;
import com.hldj.hmyg.Ui.myProgramChild.ProgramPurchaseActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.M.PurchaseListGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.TagAdapter;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.Random;

import static com.hldj.hmyg.R.id.tv_caozuo01;

/**
 * 采购单
 */

public class ProgramFragment1 extends BaseFragment {

    private static final String TAG = "ProgramFragment1";
    private CoreRecyclerView coreRecyclerView;
    private boolean mIsPrepared = false;
    private boolean isFirst = true;

    private MyPresenter myPresenter;

    @Override
    public int bindLoadingLayout() {
        return R.id.loading_program1;
    }

    @Override
    protected void initView(View rootView) {
        showLoading();
        Log.e(TAG, "initView: ");
        coreRecyclerView = (CoreRecyclerView) rootView.findViewById(R.id.recycle_program1);

        myPresenter = new MyPresenter();

        coreRecyclerView.init(new BaseQuickAdapter<PurchaseBean, BaseViewHolder>(R.layout.item_program_one) {
            @Override
            protected void convert(BaseViewHolder helper, PurchaseBean item) {
                isFirst = false;
                Log.e(TAG, "convert: " + item.toString());


//                helper.setText(R.id.tv_01, item.num);
                String html_source = item.name + "";
                String html_source1 = "(" + item.num + ")";
                helper.setText(R.id.tv_01, Html.fromHtml(html_source + "<font color='#FFA19494'><small>" + html_source1 + "</small></font>"));
//                helper.setText(R.id.tv_01, Html.fromHtml(item.blurProjectName + "采购单" + "<font color='#FFA19494'><small>" + "(" + item.num + ")" + "</small></font>"));
                helper.setText(R.id.tv_03, item.createDate.split(" ")[0]);
                helper.setTextColor(R.id.tv_03, ContextCompat.getColor(mActivity, R.color.text_color));
                helper.setText(R.id.tv_04, item.ciCity == null ? item.cityName : item.ciCity.fullName);//福建莆田
                helper.setDrawableLeft(R.id.tv_04, R.mipmap.ic_location);

                helper.setVisible(R.id.tv_pos, true);
                helper.setText(R.id.tv_pos, (helper.getAdapterPosition() + 1) + "");
                // getResources().getDrawable(R.mipmap.ic_location);
//                Drawable drawable = ContextCompat.getDrawable(mActivity, R.mipmap.ic_location);
//                drawable.setBounds(5, 5, 5, 5);
//                ((TextView) getView(R.id.tv_04)).setCompoundDrawables(drawable, null, null, null);
                StringFormatUtil fillColor = new StringFormatUtil(mActivity, "共有" + item.itemCountJson + "个品种", item.itemCountJson + "", R.color.green).fillColor();
                helper.setText(R.id.tv_10, fillColor.getResult());

                StringFormatUtil fillColor1 = new StringFormatUtil(mActivity, "已有" + item.quoteCountJson + "条报价", item.quoteCountJson + "", R.color.price_orige).fillColor();
                helper.setText(R.id.tv_11, fillColor1.getResult());
                if (GetServerUrl.isTest) {
                    ToastUtil.showShortToast("测试时 显示");
                    helper.setVisible(R.id.tv_11, true);
                } else {
                    helper.setVisible(R.id.tv_11, MyApplication.getUserBean().showQuoteCount);
                }


                helper.setText(tv_caozuo01, "截止时间：" + item.attrData.closeDateStr);

                // 移动研发标签
                TagAdapter<String> mMobileTagAdapter = new TagAdapter<>(mActivity);
                // mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                ((FlowTagLayout) helper.getView(R.id.mobile_flow_layout)).setAdapter(mMobileTagAdapter);
                mMobileTagAdapter.onlyAddAll(item.itemNameList);

                ((FlowTagLayout) helper.getView(R.id.mobile_flow_layout)).ClearClickAble(true, view1 -> ProgramPurchaseActivity.start(mActivity, item.id));

                helper.convertView.setOnClickListener(view -> ProgramPurchaseActivity.start(mActivity, item.id));

                   /*是否显示简易报价 图标*/
                ImageView iv_jianyi = helper.getView(R.id.iv_jianyi);
//                iv_jianyi.setVisibility(item.needPreQuote ? View.VISIBLE : View.GONE);
//        iv_jianyi.setVisibility(item.isPackage ? View.VISIBLE : View.GONE);
//        iv_jianyi.setVisibility(item.status.equals("expired") ? View.GONE : View.VISIBLE);/* 过期了 就不显示了 */
                if (item.isPackage) {
                    iv_jianyi.setVisibility(View.VISIBLE);/* 没过期 并且 是 打包报价 */
                } else {
                    iv_jianyi.setVisibility(View.GONE);/* 什么都不是 */
                }

                SuperTextView textView = helper.getView(R.id.tv_caozuo01);
                if (item.attrData.isOpen) {
                    helper.setTextColorRes(R.id.tv_caozuo01, R.color.price_orige);
//                    Drawable[] drawable = textView.getdr
                    Drawable drawable = tintDrawable(textView.getCompoundDrawables()[0], ColorStateList.valueOf(Color.parseColor("#e75b45")));
                    textView.setDrawable(drawable);
                } else {
                    helper.setTextColorRes(R.id.tv_caozuo01, R.color.text_color666);
                    Drawable drawable = tintDrawable(textView.getCompoundDrawables()[0], ColorStateList.valueOf(Color.parseColor("#666666")));
                    textView.setDrawable(drawable);
                }


                /* 过期了 就不显示了 */

//                    */


            }
        }, false)
                .openLoadMore(10, page -> {
                    showLoading();
                    myPresenter.getDatas();
//                    myPresenter.getDatas(getProjectId(),page+"");

                }).openRefresh();


        loadData();

    }


    @Override
    protected void loadData() {
        if (!mIsVisible || !mIsPrepared || !isFirst) {
            Log.e(TAG, "不加载数据 mIsVisible=" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
            return;
        } else {
            Log.e(TAG, "加载数据" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
        }

        myPresenter.getDatas();

    }


    @Override
    protected void onVisible() {
        super.onVisible();
        Log.e(TAG, "onVisible: ");
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        Log.e(TAG, "onInvisible: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_program1;
    }


    public String getProjectId() {
        return ((ProgramDirctActivity) mActivity).getExtralID();
    }


    private class MyPresenter extends BasePresenter {

        public void getDatas() {
            putParams("projectId", getProjectId());
            AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {
                    // 显示成功后就不是第一次了，不再刷新
                    Log.i(TAG, "onSuccess: " + json.toString());

                    PurchaseListGsonBean listGsonBean = GsonUtil.formateJson2Bean(json, PurchaseListGsonBean.class);
                    if (listGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        coreRecyclerView.getAdapter().addData(listGsonBean.data.purchaseList);

                        new Handler().postDelayed(() -> {
                            hideLoading(coreRecyclerView);
                        }, 400);

                    } else {
                        new Handler().postDelayed(() -> {
                            hideLoading(LoadingLayout.Error, listGsonBean.msg);
                        }, 200);

                    }
                    coreRecyclerView.selfRefresh(false);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    coreRecyclerView.selfRefresh(false);

                    new Handler().postDelayed(() -> {
                        hideLoading(LoadingLayout.No_Network);
                    }, 200);
                }
            };
            doRequest("admin/project/purchaseList", true, ajaxCallBack);
//            doRequest("admin/project/purchaseItemlist", true, ajaxCallBack);

        }

//2.5.9.4 项目采购单列表
//1.说明
//                获取该project的采购单列表
//2.URL
//        Post   /admin/project/purchaseList
///admin/project/purchaseItemlist

    }


    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    public int getRandomColor() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;


        return Color.parseColor("#" + r + g + b);
    }


}
