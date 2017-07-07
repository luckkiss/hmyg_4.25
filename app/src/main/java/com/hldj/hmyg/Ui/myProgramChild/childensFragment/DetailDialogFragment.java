package com.hldj.hmyg.Ui.myProgramChild.childensFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hldj.hmyg.M.InvoiceCarDetailGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseDialogFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗擦擦
 */

public class DetailDialogFragment extends BaseDialogFragment {

    private static final String TAG = "DetailDialogFragment";
    MyPresenter myPresenter;
    private CoreRecyclerView coreRecyclerView;
    LoadingLayout loadingLayout;

    public static DetailDialogFragment instance(String string) {
        DetailDialogFragment detailDialogFragment = new DetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("str", string);
        detailDialogFragment.setArguments(bundle);
        return detailDialogFragment;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.dialog_fragment_detail;
    }

    public void setTitle(String title) {
//        TextView textView = getView(R.id.sptv_detail_close);
//        textView.setText(title);
    }

    @Override
    public void initData() {
        loadingLayout.setStatus(LoadingLayout.Loading);
        myPresenter = new MyPresenter();
        myPresenter.getDatas();
    }


    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        coreRecyclerView = getView(R.id.recycle_dialog_fragment_detail);
        loadingLayout = getView(R.id.loading_dialog_fragment_detail);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        coreRecyclerView.init(new BaseQuickAdapter<InvoiceCarDetailGsonBean.DataBeanX.DataBean.ProjectListBean.ItemListBean, BaseViewHolder>(R.layout.item_dialog_detail) {
            @Override
            protected void convert(BaseViewHolder helper, InvoiceCarDetailGsonBean.DataBeanX.DataBean.ProjectListBean.ItemListBean item) {

                // count  == 0    1个数据     currenSize = 1   pos = 1
                // count == 1     10 个数据   currenSize == 10  pos = 1 - 10
                // count == 2     30 个数据   currenSize == 30  pos = 1 - 30
                int nowPos = helper.getAdapterPosition() + 1;
                Log.e(TAG, "convert: nowPos=" + nowPos + " count=" + count);

                //{1,10,20}
                if (count > 0) {
                    for (int i = 0; i < listSizes.size() - 1; i++) {
                        nowPos -= listSizes.get(i);
                        if (nowPos <= 0) {
                            nowPos = 1;
                        }
                    }
                }
//                if (nowPos > listSizes.get(count)) {
//
//                    for (Integer count : listSizes) {//1  10  20
//                        nowPos -= count;
//                    }
//                }
                helper.setVisible(R.id.tv_item_detail_project_name, nowPos == 1);
                helper.setVisible(R.id.textView7, nowPos == 1);
                helper.setText(R.id.tv_item_detail_project_name, listProjects.get(count).projectName);
                helper.setText(R.id.tv_item_detail_name, nowPos + "." + item.name);


                helper.setText(R.id.tv_item_detail_load_count, item.loadCount + item.unitTypeName);//数量 + 单位   10株
                helper.setText(R.id.tv_item_detail_price, "￥" + item.price);//采购 单价 49.5
                helper.setText(R.id.tv_item_detail_totalPrice, "￥" + item.totalPrice);
                helper.setText(R.id.tv_item_detail_spec_text, item.specText);
            }
        });

    }

    @Override
    public void initListener() {
//        getView(R.id.sptv_detail_close).setOnClickListener(view -> dismiss());
        loadingLayout.setOnReloadListener(v -> initData());//刷新
    }


    public String getProjectId() {
        Bundle bundle = getArguments();
        ToastUtil.showShortToast(bundle.getString("str"));
        return bundle.getString("str");
    }

    List<InvoiceCarDetailGsonBean.DataBeanX.DataBean.ProjectListBean> listProjects = new ArrayList();
    List<Integer> listSizes = new ArrayList<>();
    public int count = 0;

    private class MyPresenter extends BasePresenter {

        public void getDatas() {
            putParams("id", getProjectId());
            AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {
                    count = 0;
                    listProjects.clear();
                    listSizes.clear();
//                    ToastUtil.showShortToast("=====json======\n" + json);
                    InvoiceCarDetailGsonBean gsonBean = GsonUtil.formateJson2Bean(json, InvoiceCarDetailGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        setTitle(gsonBean.data.data.carNum + "装车明细");
                        int projectListSize = gsonBean.data.data.projectList.size();
                        if (projectListSize == 0) {
                            loadingLayout.setStatus(LoadingLayout.Empty);
                        } else {
                            loadingLayout.setStatus(LoadingLayout.Success);
                        }

                        for (int i = 0; i < projectListSize; i++) {
                            Log.e(TAG, "convert: i=" + i + " count=" + count);
                            listProjects.add(gsonBean.data.data.projectList.get(i));
                            listSizes.add(gsonBean.data.data.projectList.get(i).itemList.size());
                            count = i;
                            coreRecyclerView.getAdapter().addData(gsonBean.data.data.projectList.get(i).itemList);
                        }
                    } else {
                        loadingLayout.setErrorText(gsonBean.msg);
                        loadingLayout.setStatus(LoadingLayout.Error);
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    loadingLayout.setErrorText("网络请求失败，请检查网络连接！");
                    loadingLayout.setStatus(LoadingLayout.No_Network);
                }
            };
            doRequest("admin/project/invoiceCarDetail", true, ajaxCallBack);
//

        }


    }


}
