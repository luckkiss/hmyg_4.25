package com.hldj.hmyg.saler.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.search.IRefresh;
import com.hldj.hmyg.CallBack.search.ISearch;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 * Created by Administrator on 2017/5/2.
 */

public class Fragment1 extends Fragment implements IRefresh {

    private static final String TAG = "Fragment1";

    private Activity mActivity;

    private CoreRecyclerView recyclerView;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    public void showLoading(Activity mActivity) {
        if (mActivity != null && mActivity instanceof NeedSwipeBackActivity) {
            ((NeedSwipeBackActivity) mActivity).showLoading();
        }
    }

    public void hideLoading(Activity mActivity) {

        if (mActivity != null && mActivity instanceof NeedSwipeBackActivity) {
            ((NeedSwipeBackActivity) mActivity).hindLoading();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.activity_a_top_toolbar_new,null);

        if (view == null) view = getContentView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData(0);

    }

    public View getContentView() {

        recyclerView = new CoreRecyclerView(getActivity());
        recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray_bg_ed));
        recyclerView.initView(getActivity()).init(new BaseQuickAdapter<SaveSeedingGsonBean.DataBean.SeedlingBean, BaseViewHolder>(R.layout.item_fragment1) {
            @Override
            protected void convert(BaseViewHolder helper, SaveSeedingGsonBean.DataBean.SeedlingBean item) {


                D.e("==========item=============" + item.toString());

                doConvert(helper, item, mActivity);


            }
        }, true).openLoadMore(6, page -> {
            initData(page);
        }).openRefresh()
                .selfRefresh(true)
                .closeDefaultEmptyView();

//        SectionDecoration.PowerGroupListener groupListener = new SectionDecoration.PowerGroupListener() {
//            @Override
//            public String getGroupName(int position) {
//
//                if (position < 3) {
//                    return "group 1";
//                } else if (position < 6) {
//                    return "group 2";
//                } else {
//                    return "group 3";
//                }
//            }
//
//            @Override
//            public View getGroupView(int position) {
//                if (position < 3) {
//                    View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag_style, null);
//                    TextView textView = (TextView) view.findViewById(R.id.text1);
//                    textView.setText("hello world");
//                    return view;
//                } else if (position < 6) {
//                    View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_simple, null);
//                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
//                    textView.setText("2222");
//                    return view;
//                } else {
//                    View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
//                    TextView textView = (TextView) view.findViewById(R.id.text1);
//                    textView.setText("3333");
//                    return view;
//                }
//
//
//            }
//        };
//        SectionDecoration itemDecoration = SectionDecoration.Builder.init(groupListener)
//                .setGroupHeight(100)
//                .build();
//
//        recyclerView.getRecyclerView().addItemDecoration(itemDecoration);

        recyclerView.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        return recyclerView;
    }

    public static void doConvert(BaseViewHolder helper, SaveSeedingGsonBean.DataBean.SeedlingBean item, Activity mActivity) {

        String plant = "";
        if (TextUtils.isEmpty(item.getPlantTypeName())) {
            plant = "";
        } else {
            plant = "[" + item.getPlantTypeName() + "]";
        }

        StringFormatUtil fillColor = new StringFormatUtil(mActivity, plant + item.purchaseItemJson.name, item.purchaseItemJson.name, R.color.main_color).fillColor();


        helper.setText(R.id.tv_fr_item_plant_name, fillColor.getResult());

        helper.setText(R.id.tv_fr_item_company_name, item.purchaseJson.name + " (" + item.purchaseJson.num + ")");
//                helper.setText(R.id.tv_fr_item_company_name, item.purchaseJson.buyer.displayName);
        helper.setText(R.id.tv_fr_item_company_addr_name, FUtil.$_zero(item.purchaseJson.cityName));

                /* 苗源地址 */
        helper.setText(R.id.tv_fr_item_company_addr_source, FUtil.$_zero(item.getCityName()));
        helper.setText(R.id.tv_fr_item_price, "报价：" + "¥" + item.price + FUtil.$_head_no_("/", item.getUnitTypeName()));

        setImageCountAndClick(helper.getView(R.id.stv_fragment_show_images), item.getImagesJson(), mActivity);

        if (!TextUtils.isEmpty(FUtil.$_zero_2_null(item.prePrice))) {
            helper.setText(R.id.tv_fr_item_pre_price, "预估到货价：¥" + "" + item.prePrice);
        } else {
            helper.setText(R.id.tv_fr_item_pre_price, "");
        }


        helper.setText(R.id.tv_fr_item_specText, FUtil.$(item.getSpecText()) + FUtil.$_head("可供数量：", FUtil.$_zero_2_null(item.getCount() + "")));
//        helper.setText(R.id.tv_fr_item_specText, plant + FUtil.$(item.getSpecText()) + FUtil.$_head("可供数量：", FUtil.$_zero_2_null(item.getCount() + "")));


        helper.setText(R.id.stv_fragment_time, item.attrData.createDate);


        setStatus(helper, item.getStatus());//通过状态设置背景颜色
        helper.addOnClickListener(R.id.cv_root, v -> {
//                    ManagerQuoteListItemDetail.start2Activity(getActivity(), item);
            ManagerQuoteListItemDetail_new.start2Activity(mActivity, item.getId());
        });

    }

    public static void setImageCountAndClick(SuperTextView superTextView, List<ImagesJsonBean> imagesJson, Activity mActivity) {
        superTextView.setText("有" + imagesJson.size() + "张图片");
        PurchaseDetailActivity.setImgCountsSeed(mActivity, superTextView, imagesJson);
//                textView35  苗源地
        //有多少张图片
        if (imagesJson.size() > 0) {
            StringFormatUtil fillColor = new StringFormatUtil(mActivity, "有" + imagesJson.size() + "张图片", imagesJson.size() + "", R.color.red)
                    .fillColor();
            superTextView.setText(fillColor.getResult());
            superTextView.setShowState(true);
        } else {
            superTextView.setShowState(false);
        }


    }

    public static void setStatus(BaseViewHolder helper, String status) {
        if (TextUtils.isEmpty(status)) {
            helper.setVisible(R.id.tv_fr_item_state, false);
        } else if (status.equals("unused")) {
            helper.setVisible(R.id.tv_fr_item_state, true);
            helper.setText(R.id.tv_fr_item_state, "未中标");
            helper.setTextColor(R.id.tv_fr_item_state, MyApplication.getInstance().getResources().getColor(R.color.state_for_manager_no));
        } else if (status.equals("used")) {
            helper.setVisible(R.id.tv_fr_item_state, true);
            helper.setText(R.id.tv_fr_item_state, "已中标");
            helper.setTextColor(R.id.tv_fr_item_state, MyApplication.getInstance().getResources().getColor(R.color.main_color));
        } else if (status.equals("choosing")) {
            helper.setVisible(R.id.tv_fr_item_state, true);
            helper.setText(R.id.tv_fr_item_state, "选标中");
            helper.setTextColor(R.id.tv_fr_item_state, MyApplication.getInstance().getResources().getColor(R.color.orange));
        } else {
            helper.setVisible(R.id.tv_fr_item_state, false);
        }

    }


    @Override
    public void onDestroyView() {
        Log.d("-----", "destroyAccView");
        if (view != null)
            ((ViewGroup) view.getParent()).removeView(view); //从父容器中移除，避免重复添加
        super.onDestroyView();
    }


    private void initData(int pageIndex) {

        showLoading(mActivity);

        showLoading((NeedSwipeBackActivity) mActivity);
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("status", "");
        params.put(ConstantParams.searchKey, getSearchKey());
        params.put("pageSize", 6 + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/list", params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String json) {
                D.e("======json=========" + json);
                CollectGsonBean pageBean = GsonUtil.formateJson2Bean(json, CollectGsonBean.class);
                if (pageBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    recyclerView.getAdapter().addData(pageBean.data.page.data);

                    if (recyclerView.isDataNull()) {
                        recyclerView.setNoData("");
                    }
                }
                recyclerView.selfRefresh(false);
                hideLoading(mActivity);
                super.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                recyclerView.selfRefresh(false);
                recyclerView.setNoData("网络错误,请稍后重试！");
                hideLoading(mActivity);
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.DELETE_SUCCEED) {
            ToastUtil.showShortToast("删除成功");
            onRefresh("");
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            Log.i("setUserVisibleHint", "======显示  刷新====" + this);
        } else {
            Log.i("setUserVisibleHint", "====显示  不刷新======" + this);
        }

    }


    private String getSearchKey() {
        if (mActivity != null && mActivity instanceof ISearch)
        {
            return ((ISearch) mActivity).getSearchKey();
        } else {
            return "";
        }
    }

    @Override
    public void onRefresh(String searchKey) {

        recyclerView.selfRefresh(true);
        recyclerView.onRefresh();


    }
}