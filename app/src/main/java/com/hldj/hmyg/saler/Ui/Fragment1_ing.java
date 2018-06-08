package com.hldj.hmyg.saler.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hldj.hmyg.CallBack.search.IRefresh;
import com.hldj.hmyg.CallBack.search.ISearch;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.saler.Ui.Fragment1.doConvert;


/**
 * 选标中 界面
 */

public class Fragment1_ing extends Fragment implements IRefresh {

    private static final String TAG = "Fragment1_ing";

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

        recyclerView.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        return recyclerView;
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
        params.put("status", "choosing");
        params.put("pageSize", 6 + "");
        params.put(ConstantParams.searchKey, getSearchKey());
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


    private String getSearchKey() {
        if (mActivity != null && mActivity instanceof ISearch) {
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