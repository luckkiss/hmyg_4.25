package com.hldj.hmyg.saler.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Fragment2 extends Fragment {



    private CoreRecyclerView recyclerView;
    View view;

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

//    private void getData() {
//        initData();
//
//        // TODO: 2017/5/2 0002
//    }

    public View getContentView() {

        recyclerView = new CoreRecyclerView(getActivity());
        recyclerView.initView(getActivity()).init(new BaseQuickAdapter<SaveSeedingGsonBean.DataBean.SeedlingBean, BaseViewHolder>(R.layout.item_fragment1) {
            @Override
            protected void convert(BaseViewHolder helper, SaveSeedingGsonBean.DataBean.SeedlingBean item) {

                D.e("==========item=============" + item.toString());
                helper.setText(R.id.tv_fr_item_plant_name, item.purchaseJson.name);
                helper.setText(R.id.tv_fr_item_company_name, item.purchaseJson.buyer.displayName);
                helper.setText(R.id.tv_fr_item_company_addr_name, item.purchaseJson.cityName);
                helper.setText(R.id.tv_fr_item_price, item.price);
                helper.setText(R.id.tv_fr_item_specText, item.getSpecText());

                setStatus(helper, item.getStatus());//通过状态设置背景颜色
                helper.addOnClickListener(R.id.cv_root, v -> {

                    ManagerQuoteListItemDetail.start2Activity(getActivity(), item);

                });


            }
        }).openLoadMore(6, page -> {
            initData(page);
        }).openRefresh();

        return recyclerView;
    }

    private void setStatus(BaseViewHolder helper, String status) {


        if (TextUtils.isEmpty(status)) {
            helper.setVisible(R.id.tv_fr_item_state, false);
        } else if (status.equals("unused")) {
            helper.setVisible(R.id.tv_fr_item_state, true);
            helper.setText(R.id.tv_fr_item_state, "未中标");
            helper.setBackgroundColor(R.id.tv_fr_item_state, getResources().getColor(R.color.orange));
        } else if (status.equals("used")) {
            helper.setVisible(R.id.tv_fr_item_state, true);
            helper.setText(R.id.tv_fr_item_state, "已中标");
            helper.setBackgroundColor(R.id.tv_fr_item_state, getResources().getColor(R.color.main_color));
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
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("status", "used");
        params.put("pageSize", 6 + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/list", params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String json) {
                D.e("======json=========" + json);
                CollectGsonBean pageBean = GsonUtil.formateJson2Bean(json, CollectGsonBean.class);
                if (pageBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    recyclerView.getAdapter().addData(pageBean.data.page.data);

                }
                super.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
}
