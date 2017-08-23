package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.hldj.hmyg.M.InvoiceCarDetailGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗擦擦  ProgramFragment2 ----  child
 */

public class ProgramFragment2DetailActivity extends BaseMVPActivity {

    private static final String TAG = "ProgramFragment2DetailA";
    MyPresenter myPresenter;
    private CoreRecyclerView coreRecyclerView;
    LoadingLayout loadingLayout;

    @Override
    public void initView() {
        coreRecyclerView = getView(R.id.recycle_dialog_fragment_detail);
        loadingLayout = getView(R.id.loading_dialog_fragment_detail);
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
                int index = item.index;
                helper.setVisible(R.id.tv_item_detail_project_name, index == 1);
//                helper.setVisible(R.id.tv_item_detail_project_name, nowPos == 1);
                helper.setVisible(R.id.textView7, index == 1);
//                helper.setVisible(R.id.textView7, nowPos == 1);
                helper.setText(R.id.tv_item_detail_project_name, item.projectName);
//                helper.setText(R.id.tv_item_detail_project_name, listProjects.get(count).projectName);
                helper.setText(R.id.tv_item_detail_name, index + "." + item.name);


                helper.setText(R.id.tv_item_detail_load_count, item.loadCount + item.unitTypeName);//数量 + 单位   10株
                helper.setText(R.id.tv_item_detail_price, "￥" + item.price);//采购 单价 49.5
                helper.setText(R.id.tv_item_detail_totalPrice, "￥" + item.totalPrice);
                helper.setText(R.id.tv_item_detail_spec_text, item.specText);
            }
        });

    }


    @Override
    public void initData() {
        //初始化数据与刷新数据分开
//        loadingLayout.setStatus(LoadingLayout.Loading);
        showLoading();
        //初始化使用 loading layout   刷新 不出现 loadinglayout
        myPresenter = new MyPresenter();
        myPresenter.getDatas();
    }

    @Override
    public String setTitle() {
        return "装车明细";
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.dialog_fragment_detail;
    }


    @Override
    public void initListener() {
        loadingLayout.setOnReloadListener(v -> {
            myPresenter.getDatas();
        });//刷新
    }

    public String getProjectId() {
        return getIntent().getStringExtra(TAG);
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

                            int childSize = gsonBean.data.data.projectList.get(i).itemList.size();
                            listSizes.add(childSize);

                            //结果添加过滤条件
                            for (int i1 = 0; i1 < childSize; i1++) {
                                gsonBean.data.data.projectList.get(i).itemList.get(i1).projectName = gsonBean.data.data.projectList.get(i).projectName;
                                gsonBean.data.data.projectList.get(i).itemList.get(i1).index = i1 + 1;
                            }
                            count = i;
                            coreRecyclerView.getAdapter().addData(gsonBean.data.data.projectList.get(i).itemList);
                        }
                    } else {
                        loadingLayout.setErrorText(gsonBean.msg);
                        loadingLayout.setStatus(LoadingLayout.Error);
                    }
                    hindLoading();
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    loadingLayout.setErrorText("网络请求失败，请检查网络连接！");
                    loadingLayout.setStatus(LoadingLayout.No_Network);
                    hindLoading();
                }
            };
            doRequest("admin/project/invoiceCarDetail", true, ajaxCallBack);
//

        }


    }


    public static void start(Activity activity, String ext) {
        Intent intent = new Intent(activity, ProgramFragment2DetailActivity.class);
        intent.putExtra(TAG, ext);
        activity.startActivity(intent);
    }

}
