package com.hldj.hmyg.Ui.myProgramChild.childensFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hldj.hmyg.M.InvoiceCarBean;
import com.hldj.hmyg.M.ProjectPageGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramDirctActivity;
import com.hldj.hmyg.Ui.myProgramChild.ProgramFragment2DetailActivity;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.StringFormatUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import static android.content.ContentValues.TAG;

/**
 * 装车列表
 */

public class ProgramFragment2 extends BaseFragment implements View.OnClickListener {

    private CoreRecyclerView coreRecyclerView;
    private boolean mIsPrepared = false;
    private LoadingLayout loadingLayout;
    MyPresenter myPresenter;
    private int pageSize = 10;
    private boolean isFirst = true;


    private static final String SENDED = "sended";//已装车
    private static final String RECEIPED = "receipted";//已到货
    private String status = SENDED;//默认显示已装车

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    protected void initView(View rootView) {

        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loading_program2);
        loadingLayout.setStatus(LoadingLayout.Loading);
        initListener(rootView);
        coreRecyclerView = (CoreRecyclerView) rootView.findViewById(R.id.recycle_program2);

        coreRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //设定底部边距为1px
                outRect.set(0, 0, 0, 4);
            }
        });
        myPresenter = new MyPresenter();

        coreRecyclerView.init(new BaseQuickAdapter<InvoiceCarBean, BaseViewHolder>(R.layout.item_program_two) {
            @Override
            protected void convert(BaseViewHolder helper, InvoiceCarBean item) {
                isFirst = false;
                helper.setText(R.id.item_program_car_card, item.carNum);//车牌号码
                helper.setText(R.id.item_program_time, item.receiptDate);//创建时间
                helper.setText(R.id.item_program_car_type, item.carTypeName);// 车型
                helper.setText(R.id.item_program_trans_price, "￥" + item.carPrice);//  运费
                helper.setText(R.id.item_program_project_name, item.projectNames);//  项目名称

                helper.addOnClickListener(R.id.item_program_confirmation, view1 ->
//                        ToastUtil.showShortToast("确认收货")
                        myPresenter.deCommit(item.id, new AjaxCallBack<String>() {
                            @Override
                            public void onLoading(long count, long current) {
                                coreRecyclerView.selfRefresh(true);
                            }

                            @Override
                            public void onSuccess(String s) {
                                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(s, SimpleGsonBean.class);
                                if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                                    coreRecyclerView.onRefresh();
                                } else {
                                    loadingLayout.setErrorText(simpleGsonBean.msg);
                                    loadingLayout.setStatus(LoadingLayout.Error);
                                }
                                coreRecyclerView.selfRefresh(false);
                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                loadingLayout.setStatus(LoadingLayout.No_Network);
                                coreRecyclerView.selfRefresh(false);
                            }
                        }));


                helper.addOnClickListener(R.id.item_program_detail, view1 ->
                        {
//                            DetailDialogFragment.instance(item.id).show(getChildFragmentManager(), "葫芦娃");
                            ProgramFragment2DetailActivity.start(mActivity, item.id);
                        }

                );
                helper.setVisible(R.id.item_program_confirmation, getStatus().equals(SENDED));//待收货 显示 确认收货按钮

                String str = item.carFirstItemName + "等" + item.carItemsCount + "个品种";
                StringFormatUtil stringFormatUtil = new StringFormatUtil(mActivity, str, item.carFirstItemName, item.carItemsCount + "", R.color.main_color).fillColor();
//                StringFormatUtil stringFormatUtil2 = new StringFormatUtil(mActivity, stringFormatUtil.getResult() + "", item.carItemsCount + "", R.color.main_color).fillColor();
                helper.setText(R.id.item_program_plant_type, stringFormatUtil.getResult());//


                /**
                 *  "id": "adc19a7775104a25b4e33233b0f6a5cb",
                 "createBy": "b9cef730fa6142eb80bbd7d646e40c66",
                 "createDate": "2017-06-08 22:59:45",
                 "carNum": "豫A3666",
                 "driverName": "",
                 "driverPhone": "15670227722",
                 "carType": "type13H",
                 "carPrice": "2500",
                 "status": "receipted",
                 "createForCustomer": true,
                 "loadCarDate": "2017-06-08",
                 "receiptDate": "2017-06-09 16:12",
                 "carTypeName": "13米高栏",
                 "statusName": "已到货",
                 "itemNames": "红豆杉10株、小叶榄仁2株、小叶榄仁3株、小叶栀子200株、鹅掌柴100株、黄金榕球2株、满天星150株、蒲葵150株、大花芦莉150株、红花继木100株、亮叶朱蕉500株",
                 "projectNames": "永鸿文化城后期补苗项目,莆田市区项目",
                 "invoiceNames": "FH0001242,FH0001241,FH0001240,FH0001243",
                 "customerName": "钟小华",
                 "carFirstItemName": "红豆杉",
                 "carItemsCount": 11
                 */

            }
        }, false)
                .openLoadMore(pageSize, page -> {
                    myPresenter.getDatas(page + "");
                });
    }


    public void initListener(View rootView) {
        rootView.findViewById(R.id.tv_yes_get).setOnClickListener(this);
        rootView.findViewById(R.id.tv_no_get).setOnClickListener(this);
    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_program2;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }


    @Override
    protected void loadData() {
        if (!mIsVisible || !mIsPrepared || !isFirst) {
            Log.e(TAG, "不加载数据 mIsVisible=" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
            return;
        }
        coreRecyclerView.onRefresh();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_no_get) {
            //未收货
//            ToastUtil.showShortToast("已发车");
            setStatus(SENDED);
        } else if (view.getId() == R.id.tv_yes_get) {
//            ToastUtil.showShortToast("已收货");
            setStatus(RECEIPED);
        }
        coreRecyclerView.onRefresh();
//        /admin/project/purchaseItemlist
    }


    public String getProjectId() {
        return ((ProgramDirctActivity) mActivity).getExtralID();
    }

    private class MyPresenter extends BasePresenter {


        public void deCommit(String carId, AjaxCallBack<String> callBack) {
            putParams("carId", carId);
            doRequest("admin/project/invoiceCarReceipted", true, callBack);
        }

        public void getDatas(String page) {
            putParams("projectId", getProjectId());
            putParams("status", getStatus());
            putParams("pageIndex", page);
            putParams("pageSize", pageSize + "");
            AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {

//                    ToastUtil.showShortToast("json" + json);
                    Log.i(TAG, "onSuccess: " + json.toString());
                    ProjectPageGsonBean gsonBean = GsonUtil.formateJson2Bean(json, ProjectPageGsonBean.class);

                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        coreRecyclerView.getAdapter().addData(gsonBean.data.page.data);
                        if (coreRecyclerView.getAdapter().getData().size() != 0) {
                            loadingLayout.setStatus(LoadingLayout.Success);
                        } else {
                            loadingLayout.setStatus(LoadingLayout.Empty);
                        }

                    } else {
                        loadingLayout.setErrorText(gsonBean.msg);
                        loadingLayout.setStatus(LoadingLayout.Error);
                    }
//                    ToastUtil.showShortToast(json);
                    coreRecyclerView.selfRefresh(false);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    loadingLayout.setStatus(LoadingLayout.No_Network);
                }
            };


            doRequest("admin/project/invoiceCarList", true, ajaxCallBack);
//            doRequest("admin/project/purchaseItemlist", true, ajaxCallBack);

        }


    }


}
