package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseExpanBean;
import com.hldj.hmyg.M.QuoteListJsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.model.ProgramPurchaseModel;
import com.hldj.hmyg.presenter.ProgramPurchasePresenter;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivity extends BaseMVPActivity<ProgramPurchasePresenter, ProgramPurchaseModel> implements ProgramPurchaseContract.View {

    private static final String TAG = "ProgramPurchaseActivity";

    private CoreRecyclerView coreRecyclerView;
    private LoadingLayout loadingLayout;
    private LinearLayout ll_head;
    private boolean showQuote = false;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_purchase;
    }

    @Override
    public void initView() {
        coreRecyclerView = getView(R.id.recycle_program_purchase);
        loadingLayout = getView(R.id.loading_layout_program_purchase);
        loadingLayout.setStatus(LoadingLayout.Loading);
        getView(R.id.tv_activity_purchase_back).setOnClickListener(view -> finish());
        ll_head = getView(R.id.ll_activity_purchase_head);

        ExpandableItemAdapter itemAdapter = new ExpandableItemAdapter(R.layout.item_program_purch, R.layout.item_program_purch_sub) {
            @Override
            protected void convert(BaseViewHolder helper, MultiItemEntity mItem) {
                if (mItem.getItemType() == TYPE_LEVEL_1)//子布局
                {
                    QuoteListJsonBean item = ((QuoteListJsonBean) mItem);
                    int layoid = R.layout.item_program_purch_sub;

                    helper.setText(R.id.tv_program_purch_sub_price_type, item.price + "/" + item.unitTypeName);// 3200/株
                    helper.setText(R.id.tv_program_purch_sub_price_cont_serv_pric, item.attrData.servicePrice + "(含服务费)");//￥3520(含服务费)
                    helper.setText(R.id.tv_program_purch_sub_use_state, item.isUsed ? "已使用" : "未采用");// 采用 未采用
                    helper.setText(R.id.tv_program_purch_sub_suplier, "花木易购供应商");//供应商
                    helper.setText(R.id.tv_program_purch_sub_plant_addt, item.cityName);//福建漳州
                    helper.setText(R.id.tv_program_purch_sub_space_text, item.specText);//
                    helper.setText(R.id.tv_program_purch_sub_remark, item.remarks);//备注
                    helper.setText(R.id.tv_program_purch_sub_is_true, "已核实----");//

                    helper.setText(R.id.tv_program_purch_sub_images_count, "已有" + item.imagesJson.size() + "张图片");//


                } else if (mItem.getItemType() == TYPE_LEVEL_0) {

                    ToastUtil.showShortToast("TYPE_LEVEL_0");
                    ProgramPurchaseExpanBean item = ((ProgramPurchaseExpanBean) mItem);
                    //default
                    helper.setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "");
                    helper.setText(R.id.tv_program_purch_name, item.name);
                    helper.setText(R.id.tv_program_purch_num_type, item.count + item.unitTypeName);
                    helper.setText(R.id.tv_program_purch_space_text, item.specText);

                    helper.setVisible(R.id.tv_program_purch_price_num, item.count != 0);
                    helper.setText(R.id.tv_program_purch_price_num, "共有" + item.quoteCountJson + "条报价");

                    helper.setText(R.id.tv_program_purch_alreay_order, "已采用" + item.quoteUsedCountJson + "条");


                    helper.setVisible(R.id.tv_program_purch_see_detail, GetServerUrl.isTest || showQuote);//测试时必然显示

                    if (item.quoteListJson.size() != 0) {
                        item.setSubItems(item.quoteListJson);
                    }

                    helper.addOnClickListener(R.id.tv_program_purch_see_detail, view -> {
                        ToastUtil.showShortToast("打开已采用" + item.quoteListJson.toString());
                        int pos = helper.getAdapterPosition();
                        if (item.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    });
                }
            }
        };

//        itemAdapter.setDefaultViewTypeLayout(R.layout.item_program_purch); //default

//R.layout.item_program_purch
        coreRecyclerView.init(itemAdapter)
                .openLoadMore(10, page -> {
                    loadingLayout.setStatus(LoadingLayout.Loading);
                    mPresenter.getData(page + "", getExtral());
                })
                .openRefresh();


        //加载 index 信息 head
        mPresenter.getIndexDatas(getExtral());


        Observable.just("hellow world")
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cus -> {
                    coreRecyclerView.onRefresh();
                    ToastUtil.showShortToast("" + cus);
                    loadingLayout.setStatus(LoadingLayout.Success);
                    ll_head.setVisibility(View.VISIBLE);
                });
    }

    @Override
    public void initVH() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public void showLoading() {
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    //接受传值  传过来  项目 id
    public String getExtral() {
        ToastUtil.showShortToast(getIntent().getStringExtra(TAG));
        return getIntent().getStringExtra(TAG);
    }

    //传值开启界面
    public static void start(Activity mAct, String ext) {
        Intent intent = new Intent(mAct, ProgramPurchaseActivity.class);
        intent.putExtra(TAG, ext);
        mAct.startActivity(intent);
    }


    @Override
    public void showErrir(String erMst) {
        loadingLayout.setOnReloadListener(v -> coreRecyclerView.onRefresh());
        loadingLayout.setErrorText(erMst);
        loadingLayout.setStatus(LoadingLayout.Error);
        coreRecyclerView.selfRefresh(false);
    }

    @Override
    public void initXRecycle(List<ProgramPurchaseExpanBean> gsonBean) {
        coreRecyclerView.getAdapter().addData(gsonBean);
        loadingLayout.setStatus(LoadingLayout.Success);
        coreRecyclerView.selfRefresh(false);
    }

    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {

    }

    @Override
    public void onDeled(boolean bo) {

    }

    @Override
    public void initHeadDatas(PurchaseBean purchaseBean) {
        //初始化头部
//        TextView tv_program_pur_projectName_num = getView(R.id.tv_program_pur_projectName_num);
//        TextView tv_program_pur_city = getView(R.id.tv_program_pur_city);
//        TextView tv_program_pur_phoner = getView(R.id.tv_program_pur_phoner);
//        TextView tv_program_pur_phone_num = getView(R.id.tv_program_pur_phone_num);
//        tv_program_pur_projectName_num.setText(purchaseBean.projectName + "(" + purchaseBean.num + ")");
//        tv_program_pur_city.setText("用苗地块：" + purchaseBean.cityName);
//        tv_program_pur_phoner.setText("联系客服：" + purchaseBean.dispatchName);
//        tv_program_pur_phone_num.setText(purchaseBean.dispatchPhone);
//        tv_program_pur_phone_num.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        ll_head.setVisibility(View.VISIBLE);

    }

    @Override
    public String getSearchText() {
        return null;
    }


    @Override
    public ViewGroup getContentView() {
        return null;
    }
}
