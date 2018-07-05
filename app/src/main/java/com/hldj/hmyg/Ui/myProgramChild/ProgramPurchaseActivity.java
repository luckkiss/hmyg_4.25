package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseExpanBean;
import com.hldj.hmyg.M.ProgramPurchaseIndexGsonBean;
import com.hldj.hmyg.M.QuoteUserGroup;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.enums.CountEnum;
import com.hldj.hmyg.bean.enums.PurchaseStatus;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.Ui.WebViewDialogFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.IExpandable;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.model.ProgramPurchaseModel;
import com.hldj.hmyg.presenter.ProgramPurchasePresenter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.AlertUtil;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.DownTime;
import com.hy.utils.GetServerUrl;
import com.hy.utils.SpanUtils;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.hldj.hmyg.R.id.tv_program_purch_sub_price_cont_serv_pric;
import static com.hldj.hmyg.R.id.tv_program_purch_sub_use_state;
import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;
import static com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter.TYPE_LEVEL_0;


/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivity extends BaseMVPActivity<ProgramPurchasePresenter, ProgramPurchaseModel> implements ProgramPurchaseContract.View {

    private static final String TAG = "ProgramPurchaseActivity";
    public static final String STATE = "state";
    public static final String HAS_PERMISION = "hasPermision";

    protected CoreRecyclerView coreRecyclerView;
    private CoreRecyclerView recycle_program_purchase_gys;
    private LoadingLayout loadingLayout;
    private LinearLayout ll_head;
    private boolean showQuote = false; /* 表示是否开标 */
    private MyDownTime myDownTime;
    private boolean tureQuote;//可以选标
    private ExpandableItemAdapter itemAdapter;

    public void setTrueQutoe(boolean permision) {
        this.tureQuote = permision;
    }

    public boolean getTrueQutoe() {
        return tureQuote;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_purchase;
    }


    //public int shouldOpenPos = 666;
    public String pareId = "";
    protected String searchKey = "";// 本界面search key  是 传 sallerid 用的。。。。字段设计错误。懒得改了

    @Override
    public void initView() {

        EditText editText = getView(R.id.et_program_serach_text);
        editText.setHint("请输入采购单名称");

        showLoading();

        getView(R.id.sptv_program_do_search).setOnClickListener(view -> {
            searchKey = getSerachText();
            coreRecyclerView.onRefresh();
        });

        recycle_program_purchase_gys = getView(R.id.recycle_program_purchase_gys);
        initAndRequestGysData();


        coreRecyclerView = getView(R.id.recycle_program_purchase);
        coreRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 0);
            }
        });
        loadingLayout = getView(R.id.loading_layout_program_purchase);
//        showLoading();
//      loadingLayout.setStatus(LoadingLayout.Loading);
        getView(R.id.tv_activity_purchase_back).setOnClickListener(view -> finish());


        ll_head = getView(R.id.ll_activity_purchase_head);

        //                            //supplier  //item.sendType.equals("hmeg")
//                            coreRecyclerView.getRecyclerView().getAdapter().notifyItemChanged(helper.getAdapterPosition());
/*sub 某个项 进行修改*///
//item 改变 sub 的值
//棒极了
//                                    for (int i = 0; i < expanBean.quoteListJson.size(); i++) {
//                                        if (expanBean.quoteListJson.get(i).id.equals(item.id)) {
//                                            expanBean.quoteListJson.set(i, item);
//
//                                        }
//                                    }
//                                    collapse(posParent);
//                                    expand(posParent);
//                                    ToastUtil.showShortToast("修改成功，准备刷新" + json);
//成功后刷新
//                                    coreRecyclerView.onRefresh();
//子布局
//                    D.e("======TYPE_LEVEL_1========");
// 3200/株
//￥3520(含服务费)
//初始化设置drawable
// 采用 未采用
/*未采用&&是花木易购供应商&&未落实或者合格的显示采用按钮*///未采用
//--  self.usedQuoteBtn.userInteractionEnabled=YES;
//--  不显示 imageview
//--  显示采用按钮
//--  绿色 色调 边界 跟 字体颜色
//--  显示 采用 字
//hmeg
// 花木易购 采用
/* 未采用&&是自有供应商&&未落实或者合格的显示采用按钮*///                    else if (!item.isUsed && item.attrData.isSupplier && (TextUtils.isEmpty(item.quoteImplementStatus) || item.quoteImplementStatus.equals("uncovered") || item.quoteImplementStatus.equals("qualified"))) {
//
//                        helper.setText(R.id.tv_program_purch_sub_use_state, "采用")
//                                .setSelected(R.id.tv_program_purch_sub_use_state, false); // 供应商 采用
//
//                        helper.addOnClickListener(R.id.tv_program_purch_sub_use_state, view -> {
//                            hah(helper, item, "");
//                        });
//                    }
/* <!-- 已采用&&未落实显示取消按钮 -->*///(TextUtils.isEmpty(item.quoteImplementStatus)) ||
//    显示取消 按钮
//                        helper.setSelected(R.id.tv_program_purch_sub_use_state, false);
/*<!-- 已采用&&合格的显示已采用并且锁定不可修改 -->*///兼容旧数据。。。默认 已采用
/*是花木易购  *//*是供应商 字段*///供应商
/*由花木易购 显示文字  tv_program_purch_sub_who_send  通过 sendType  进行修改*///                    if (item.sendType.equals("hmeg")) {
//                        helper
//                                .setText(R.id.tv_program_purch_sub_who_send, "由花木易购发货");
//
//                    } else if (item.sendType.equals("supplier")) {
//                        helper.setText(R.id.tv_program_purch_sub_who_send, "由供应商发货");
//                    } else {
//                        helper.setText(R.id.tv_program_purch_sub_who_send, "");
//                    }
//福建漳州
//
//备注
//                    helper.setSelected(R.id.tv_program_purch_sub_is_true, item.quoteImplementStatus.equals("qualified"));
//已核实
//
//                    else  {
//                        helper.setText(R.id.tv_program_purch_sub_is_true, "未核实");//
//                        helper.setVisible(R.id.tv_program_purch_sub_is_true, true);
//                        helper.setVisible(R.id.tv_program_purch_sub_remark, true);
//                        helper.setVisible(R.id.imageView, false);
//                        helper.setVisible(R.id.tv_program_purch_sub_use_state, false);
//
//                    }
//容器苗
//
//default
//                    helper.setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "");
//                    GetServerUrl.isTest ||
//测试时必然显示
//测试时必然显示
//                    expand(helper.getAdapterPosition(), false, true);
//                        ToastUtil.showShortToast("打开已采用" + item.quoteListJson.toString());
//测试时必然显示
//全部刷新 不行
//                    if (item.id.equals(pareId)) {
//                        new Handler().postDelayed(() -> {
//                            ToastUtil.showShortToast("pareId" + pareId);
//                            expand(helper.getAdapterPosition(), true, true);
//                            pareId = "";
//                        }, 30);
//                    }
        itemAdapter = new ExpandableItemAdapter(R.layout.item_program_purch, R.layout.item_program_purch_sub) {


            @Override
            protected void convert(BaseViewHolder helper, MultiItemEntity mItem) {


                if (mItem.getItemType() == TYPE_LEVEL_1)//子布局
                {
                    doConvert1(helper, mItem);
                } else if (mItem.getItemType() == TYPE_LEVEL_0) {

                    ProgramPurchaseExpanBean item = ((ProgramPurchaseExpanBean) mItem);
                    //default
//                    helper.setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "");

                    helper.setText(R.id.tv_program_purch_pos, item.index + "");

                    helper.setText(R.id.tv_program_purch_name, item.name);
                    helper.setText(R.id.tv_program_purch_num_type, item.count + item.unitTypeName);
                    helper.setText(R.id.tv_zzlx, "种植类型：" + item.plantTypeArrayNames);
                    helper.setText(R.id.tv_program_purch_space_text, "规格：" + item.specText + "；" + item.remarks);
                    Log.e(TAG, "TYPE_LEVEL_0" + helper.getAdapterPosition());

                    helper.setVisible(R.id.tv_program_purch_price_num, item.quoteCountJson != 0);


                    String wholeStr = "有" + item.quoteCountJson + "条报价";

                    if (TextUtils.isEmpty(searchKey))//非供应商
                    {
                        wholeStr = "有" + item.quoteCountJson + "条报价";
                    } else {//供应商
                        wholeStr = "该品种" + "有" + item.quoteCountJson + "条报价";
                    }


                    StringFormatUtil formatUtil = new StringFormatUtil(mActivity, wholeStr, item.quoteCountJson + "", R.color.red).fillColor();
                    helper.setText(R.id.tv_program_purch_price_num, formatUtil.getResult());


                    helper.setText(R.id.tv_program_purch_alreay_order, "采用" + item.quoteUsedCountJson + "条");

                    helper.setText(R.id.tv_bak_num, "备选" + item.quotePreUsedCountJson + "条");


                    helper.setVisible(R.id.tv_bak_num, item.quotePreUsedCountJson != 0);


                    helper.setVisible(R.id.tv_program_purch_alreay_order, item.quoteUsedCountJson != 0);

                    helper.setVisible(R.id.iv_program_purch_right_top, item.quoteUsedCountJson != 0);

//                    GetServerUrl.isTest ||
                    helper.setVisible(R.id.tv_program_purch_see_detail, true);//测试时必然显示
//                    helper.setVisible(R.id.tv_program_purch_see_detail, showQuote);//测试时必然显示
                    helper.setSelected(R.id.tv_program_purch_see_detail, item.isExpanded());//测试时必然显示
                    helper.setText(R.id.tv_program_purch_see_detail, !item.isExpanded() ? "查看报价" : "收起报价");

                    if (item.quoteListJson.size() != 0) {
                        item.setSubItems(item.quoteListJson);
                    }

//                    expand(helper.getAdapterPosition(), false, true);

                    helper.addOnClickListener(R.id.tv_program_purch_see_detail, view -> {
//                        ToastUtil.showShortToast("打开已采用" + item.quoteListJson.toString());
                        helper.setSelected(R.id.tv_program_purch_see_detail, !item.isExpanded());//测试时必然显示
                        if (item.isExpanded()) {
                            collapse(helper.getAdapterPosition(), false, true);
                            Log.d(TAG, "pos: " + helper.getAdapterPosition());

                        } else {
                            expand(helper.getAdapterPosition(), false, true);
                            Log.d(TAG, "pos: " + helper.getAdapterPosition());


                        }
                    });

                    //全部刷新 不行
//                    if (item.id.equals(pareId)) {
//                        new Handler().postDelayed(() -> {
//                            ToastUtil.showShortToast("pareId" + pareId);
//                            expand(helper.getAdapterPosition(), true, true);
//                            pareId = "";
//                        }, 30);
//                    }
                }
            }
        };


//        itemAdapter.setDefaultViewTypeLayout(R.layout.item_program_purch); //default

//R.layout.item_program_purch
        coreRecyclerView.init(itemAdapter)
                .closeDefaultEmptyView();
//                .openRefresh();
        showLoading();

        mPresenter.getIndexDatas(getExtral(), getExtralState());

   /*    获取品种数据    */
//        coreRecyclerView.removeAllFooterView();
//        coreRecyclerView.removeAllHeaderView();

//        mPresenter.getData(0 + "", getExtral(), searchKey, getExtralState());

        //加载 index 信息 head

        Observable.just("hellow world")
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cus -> {
                    coreRecyclerView.onRefresh();
                });

        itemAdapter.expandAll();

        //        sptv_pz  品种与供应商切换
        switchGys_pz(0);
    }


    int count = 1;

    @Override
    public void initVH() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //接受传值  传过来  项目 id
    public String getExtral() {
        return getIntent().getStringExtra(TAG);
    }

    /* 现在根据 状态 来 进行搜索   */
    public String getExtralState() {
        return getIntent().getStringExtra(STATE);

    }

    //传值开启界面
    public static void start(Activity mAct, String ext, String state) {
        Intent intent = new Intent(mAct, ProgramPurchaseActivity.class);
        intent.putExtra(TAG, ext);
        intent.putExtra(STATE, state);
        D.i("======STATE======" + state);
        mAct.startActivity(intent);
    }


    @Override
    public void showErrir(String erMst) {
        hindLoading();
        loadingLayout.setOnReloadListener(v -> coreRecyclerView.onRefresh());
        loadingLayout.setErrorText(erMst);
        loadingLayout.setStatus(LoadingLayout.Error);
        coreRecyclerView.selfRefresh(false);
    }


    /*  初始化供应商 recycleview 并且请求数据 */
    protected void initAndRequestGysData() {

        recycle_program_purchase_gys.init(new BaseQuickAdapter<QuoteUserGroup, BaseViewHolder>(R.layout.item_program_purch_gys) {
            @Override
            protected void convert(BaseViewHolder helper, QuoteUserGroup item) {
                Log.i(TAG, "convert: " + item);

                /*  渲染把 皮卡丘 */
                xuanran(helper, item);

            }
        });

        mPresenter.getDatasGys("0", getExtral(), searchKey, getExtralState());


    }

    private void xuanran(BaseViewHolder helper, QuoteUserGroup item) {

        /*  获取是否开标     */

        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("已采用：")
                .append(item.usedCount + "").setForegroundColor(getColorByRes(R.color.red))
                .append("个品种    ").setForegroundColor(getColorByRes(R.color.text_color333))
                .append(item.uncoveredCount + "").setForegroundColor(getColorByRes(R.color.red))
                .append("个品种待落实").setForegroundColor(getColorByRes(R.color.text_color333));
/**
 * quoteCount=9, quoteItemCount=9, usedCount=1, uncoveredCount=2,
 */

        helper
                .setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "")
                .setText(R.id.title, "" + item.sellerName)
                .setText(R.id.tv_pzsl,
                        new SpanUtils()
                                .append("报价品种数量：")
                                .append("" + item.quoteItemCount).setForegroundColor(getColorByRes(R.color.red))
                                .append("个").setForegroundColor(getColorByRes(R.color.text_color333))
                                .create()
                )
                //                        filterColor("报价品种数量：" + item.quoteItemCount + "个", item.quoteItemCount + "")

//                .setText(R.id.tv_ycy, filterColor("已采用：" + item.usedCount + "个品种", item.usedCount + ""))
                .setText(R.id.tv_ycy, spanUtils.create())
                .setText(R.id.tv_myd, item.cityNames == null ? "苗源地 ：-" :
                        new SpanUtils()
                                .append("苗源地：")
                                .append(item.cityNames.toString().substring(1, item.cityNames.toString().length() - 1)).setForegroundColor(getColorByRes(R.color.text_color333))
                                .create()
                )
                //  "苗源地：" + item.cityNames.toString().substring(1, item.cityNames.toString().length() - 1)
//                .setText(R.id.tv_right_price, showQuote ? "￥\n" + item.quoteTotalPrice + "起" : "")
//                .setBackgroundRes(R.id.tv_right_price, showQuote ? 0 : R.mipmap.wkb)
                .addOnClickListener(R.id.content, v -> {
//                    ToastUtil.showLongToast("-----------查看详情-----------" + item.sellerId);
                    ProgramPurchaseActivityOnly.title = "" + item.sellerName + "的报价";
                    Log.i(TAG, "xuanran: " + item.toString());
                    /*  已开标 */
                    if (showQuote) {
                        ProgramPurchaseActivityOnly.totlePrice =
                                new SpanUtils()
                                        .append("供应商名称：").setForegroundColor(getColorByRes(R.color.text_color666))
                                        .append(item.sellerName + "\n").setForegroundColor(getColorByRes(R.color.text_color333))
                                        .append("联系电话：").setForegroundColor(getColorByRes(R.color.text_color666))
                                        .append(item.sellerPhone).setForegroundColor(getColorByRes(R.color.text_color333))
                                        .create()

//                                String.format("供应商名称：%s\n联系电话：%s", item.sellerName, item.sellerPhone)

                        ;
//                        textView1.setText("供应商名称：花木易购供应商1\n联系电话：17074990702" + "");
                    } else {
                        /* 未开标 */
//                        ProgramPurchaseActivityOnly.totlePrice = "";
//                        ProgramPurchaseActivityOnly.totlePrice = "￥ " + item.quoteTotalPrice + " 起";
                        ProgramPurchaseActivityOnly.totlePrice =
                                new SpanUtils()
                                        .append("供应商名称：").setForegroundColor(getColorByRes(R.color.text_color666))
                                        .append(" item.sellerName").setForegroundColor(getColorByRes(R.color.text_color333))
                                        .appendLine("联系电话：").setForegroundColor(getColorByRes(R.color.text_color666))
                                        .append(item.sellerPhone).setForegroundColor(getColorByRes(R.color.text_color333))
                                        .create()
                        ;
                    }
                    //"sellerName":"刘俊贤-华苑供应商","sellerPhone":"13501505325",

//                    ToastUtil.showLongToast("showQutte=" + showQuote);
                    ProgramPurchaseActivityOnly.start(mActivity, getExtral(), item.sellerId, getExtralState(), getTrueQutoe());

                });


    }

    @Override
    public void initXRecycleGys(List<QuoteUserGroup> gsonBean) {
        /*  供应商   报价 列表     */

        recycle_program_purchase_gys.getAdapter().addData(gsonBean);


    }

    @Override
    public void initXRecycle(List<ProgramPurchaseExpanBean> gsonBean) {
        if (gsonBean != null) {
            List<IExpandable> list = coreRecyclerView.getAdapter().getData();

            int parentItemCount = getParentCount(list);//filter

            for (int i = 0; i < gsonBean.size(); i++) {
                if (coreRecyclerView.getAdapter().getDatasState() == REFRESH) {
                    gsonBean.get(i).index = 0 + i + 1;
                } else {
                    gsonBean.get(i).index = parentItemCount + i + 1;
                }
            }
        }
        coreRecyclerView.getAdapter().addData(gsonBean);
        loadingLayout.setStatus(LoadingLayout.Success);
        if (coreRecyclerView.isDataNull()) coreRecyclerView.setNoData("");
        hindLoading();
        coreRecyclerView.selfRefresh(false);
        itemAdapter.expandAll();


    }

    private int getParentCount(List<IExpandable> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLevel() == TYPE_LEVEL_0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {

    }

    @Override
    public void onDeled(boolean bo) {

    }

    private boolean c;


    @Override
    public void initHeadDatas(ProgramPurchaseIndexGsonBean.DataBean purchaseBean) {
        //初始化头部
        ll_head.setVisibility(View.VISIBLE);
        TextView tv_head_ser_po = getView(R.id.tv_head_ser_po);
        TextView tv_no_permision = getView(R.id.tv_no_permision);



        /* 项目名称 + 项目编号 */
        TextView tv_program_pur_projectName_num = getView(R.id.tv_program_pur_projectName_num);
        tv_program_pur_projectName_num.setText(purchaseBean.purchase.name + "(" + purchaseBean.purchase.num + ")");

        setText(getView(R.id.tv_program_name), "项目名称：" + purchaseBean.purchase.projectName);

        setText(getView(R.id.tv_program_order_place), "用苗单位：" + purchaseBean.purchase.consumerFullName);

        setText(getView(R.id.tv_program_pur_city), "用苗地：" + purchaseBean.purchase.ciCity.fullName);

        setText(getView(R.id.tv_program_pur_phoner), "联系人：" + purchaseBean.purchase.dispatchName);


        if (purchaseBean.purchase.num != null) {
                /* 联系电话 */
            setText(getView(R.id.tv_program_pur_phone_num), purchaseBean.purchase.dispatchPhone);
            getView(R.id.tv_program_pur_phone_num).setOnClickListener(view -> FlowerDetailActivity.CallPhone(purchaseBean.purchase.dispatchPhone, mActivity));
            TextView tb = getView(R.id.tv_program_pur_phone_num);
            tb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            setText(getView(R.id.tv_program_pur_phone_num), "未填写");
        }

        String point = TextUtils.isEmpty(purchaseBean.servicePoint) ? "0%" : purchaseBean.servicePoint + "%";
        setText(getView(R.id.tv_program_service_price), "代购服务费：" + point);

        setText(getView(R.id.tv_program_pur_close_time), "开标时间：" + purchaseBean.purchase.closeDate);


//        if (purchaseBean.purchase.num != null)
//            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(purchaseBean.purchase.num);


        if (getExtralState().equals(PurchaseStatus.expired.enumValue)) {
            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(PurchaseStatus.expired.enumText);
        } else if (getExtralState().equals(PurchaseStatus.published.enumValue)) {
            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(PurchaseStatus.published.enumText);

        } else if (getExtralState().equals(PurchaseStatus.finished.enumValue)) {
            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(PurchaseStatus.finished.enumText);

        } else {
            if (purchaseBean.purchase.num != null) ;
            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(purchaseBean.purchase.num);
        }


        TextView tv_program_purchase_tip = getView(R.id.tv_program_purchase_tip);
        tv_program_purchase_tip.setText(
                new SpanUtils()
                        .append("报价要求：")
                        .append("点击查看").setForegroundColor(getColorByRes(R.color.tip_yellow)).setUnderline()
                        .create()
        );

        //报价要求
        getView(R.id.tv_program_purchase_tip).setOnClickListener(v -> {
            WebViewDialogFragment.newInstance(purchaseBean.purchase.quoteDesc).show(getSupportFragmentManager(), "报价要求");
        });


        setStyleByStates(purchaseBean.status, purchaseBean.statusName, getView(R.id.tv_head_ser_po_title), getView(R.id.tv_head_ser_po), purchaseBean);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        mPresenter.getData(0 + "", getExtral(), searchKey, getExtralState());
//            }
//        }, 300);


    }

    private void setStyleByStates(String status, String statusName, TextView title, TextView right, ProgramPurchaseIndexGsonBean.DataBean purchaseBean) {


//        showQuote = purchaseBean.showQuote;
//        tureQuote = true;
        /* 显示 是否有选标权限 */
        tureQuote = purchaseBean.hasChoosePermission;
        if (tureQuote) {
            // 可以报价权限
            // 什么都不用变
            getView(R.id.tv_no_permision).setVisibility(View.GONE);
        } else {
            getView(R.id.tv_no_permision).setVisibility(View.VISIBLE);
            //隐藏   ---    显示一个没有权限的提示
        }


        /**/
//        if (purchaseBean.showQuote) {
//            right.setText("以下报价未包含 " + purchaseBean.servicePoint + "% 服务费");
//        } else {
//
//        }

        View.OnClickListener openDetail = v -> {

            new BasePresenter()
                    .putParams("id", getExtral())
                    .doRequest("admin/purchase/selectionDetail", new HandlerAjaxCallBack(mActivity) {
                        @Override
                        public void onRealSuccess(SimpleGsonBean gsonBean) {


                            CommonDialogFragment.newInstance(context -> {
                                Dialog dialog1 = new Dialog(context, R.style.DialogTheme);
                                dialog1.setContentView(R.layout.consumer_detail);


//                                dialog1.getWindow().getDecorView().setBackgroundColor(Color.WHITE);

                                LinearLayout conten = (LinearLayout) dialog1.findViewById(R.id.conten);
                                conten.setBackgroundColor(Color.WHITE);


                                TextView tv1 = (TextView) dialog1.findViewById(R.id.tv1);
                                setTextWithNum(tv1, CountEnum.品种数量.enumText, gsonBean.getData().itemCount);

                                TextView tv2 = (TextView) dialog1.findViewById(R.id.tv2);
                                setTextWithNum(tv2, CountEnum.报价条数.enumText, gsonBean.getData().quoteCount);

                                TextView tv3 = (TextView) dialog1.findViewById(R.id.tv3);
                                setTextWithNum(tv3, CountEnum.已开标数.enumText, gsonBean.getData().openCount);

                                TextView tv4 = (TextView) dialog1.findViewById(R.id.tv4);
                                setTextWithNum(tv4, CountEnum.采用数.enumText, gsonBean.getData().useCount);

                                TextView tv5 = (TextView) dialog1.findViewById(R.id.tv5);
                                setTextWithNum(tv5, CountEnum.未开标数.enumText, gsonBean.getData().unOpenCount);

                                TextView tv6 = (TextView) dialog1.findViewById(R.id.tv6);
                                setTextWithNum(tv6, CountEnum.备选.enumText, gsonBean.getData().preCount);

                                TextView tv7 = (TextView) dialog1.findViewById(R.id.tv7);
                                setTextWithNum(tv7, CountEnum.报价品种数.enumText, gsonBean.getData().itemQuoteCount);

                                TextView tv8 = (TextView) dialog1.findViewById(R.id.tv8);
                                setTextWithNum(tv8, CountEnum.待落实.enumText, gsonBean.getData().uncoveredCount);


                                dialog1.findViewById(R.id.tv_show_title).setOnClickListener(v -> dialog1.dismiss());
                                dialog1.findViewById(R.id.tv_ok_to_close).setOnClickListener(v -> dialog1.dismiss());


                                return dialog1;
                            }, true).show(getSupportFragmentManager(), TAG);


                        }

                        void setTextWithNum(TextView text, String enumName, int count) {


                            text.setText(
                                    new SpanUtils()
                                            .append(enumName + "：")
                                            .append(count + "").setForegroundColor(getColorByRes(R.color.price_orige))
                                            .append("个")
                                            .create());


                        }

                    });


        };

        if (PurchaseStatus.expired.enumValue.equals(status))//已开标
        {
            showQuote = true;
            //显示 查看选标情况
            title.setVisibility(View.GONE);
            right.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            right.setText("查看选标情况>>>");
            //:tvTest.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
            right.setOnClickListener(openDetail);
        } else if (PurchaseStatus.published.enumValue.equals(status))//报价中
        {
            //显示  倒计时
            title.setVisibility(View.VISIBLE);
            right.setText("开标倒计时：" + "-");
            myDownTime = new MyDownTime(purchaseBean.lastTime, 1000l, right);
            myDownTime.start();
        } else if (PurchaseStatus.finished.enumValue.equals(status))//已结束
        {
            //显示 查看选标情况
            title.setVisibility(View.GONE);
            right.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            right.setText("查看选标情况>>>");
            right.setOnClickListener(openDetail);


        } else {
            title.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
            ((ViewGroup) title.getParent()).setVisibility(View.GONE);
        }


    }

    @Override
    public String getSearchText() {
        return null;
    }


    @Override
    public ViewGroup getContentView() {
        return null;
    }

    public String getSerachText() {
        D.e("===getSerachText====    " + ((EditText) getView(R.id.et_program_serach_text)).getText().toString());
        return ((EditText) getView(R.id.et_program_serach_text)).getText().toString();
    }


    public class MyPrestener extends BasePresenter {

        public void doUpData(String id, String sendType) {
            putParams("id", id);
            putParams("sendType", sendType);
            putParams("sellerId", searchKey);
            putParams("isSeller", !TextUtils.isEmpty(searchKey) + "");
            AjaxCallBack callBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {
                    SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                    if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        resultCallBack.onSuccess(simpleGsonBean);
//                        resultCallBack.onSuccess(simpleGsonBean.getData().quoteUsedCount);
                    } else {
                        ToastUtil.showShortToast(simpleGsonBean.msg);
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    resultCallBack.onFailure(t, errorNo, strMsg);
                }
            };

            doRequest("admin/quote/usedQuote", true, callBack);
        }

    }


    public SpannableStringBuilder filterColor(String wholeStr, String hightLightStr) {

        return filterColor(wholeStr, hightLightStr, R.color.price_orige);
    }

    public SpannableStringBuilder filterColor(String wholeStr, String hightLightStr, int color) {
        StringFormatUtil formatUtil = new StringFormatUtil(mActivity, wholeStr, hightLightStr, color).fillColor();
        return formatUtil.getResult();
    }


    class MyDownTime extends DownTime {

        TextView tv_down_time;

        public MyDownTime(long millisInFuture, long countDownInterval, TextView tv_down_time) {
            super(millisInFuture, countDownInterval);
            this.tv_down_time = tv_down_time;
        }

        @Override
        public void onTick(long millisUntilFinished, int percent) {
            int day = (int) (((millisUntilFinished / 1000) / 3600) / 24);
            int myhour = (int) (((millisUntilFinished / 1000) / 3600) % 24);
            long myminute = ((millisUntilFinished / 1000) - day * 24 * 3600 - myhour * 3600) / 60;
            long mysecond = millisUntilFinished / 1000 - myhour * 3600 - day * 24 * 3600 - myminute * 60;
            tv_down_time.setText("距开标还剩下" + day + "天" + myhour + "小时" + myminute + "分钟" + mysecond + "秒");
        }

        @Override
        public void onFinish() {
            tv_down_time.setText("开标啦。。。");
            new Handler().postDelayed(() -> {
//                ProgramPurchaseActivity.start(mActivity, getExtral());
//                finish();
            }, 3000);
        }
    }


    /*  品种 供应商   互相切换   并且 显示隐藏  start   */


    //    android:id="@+id/loading_layout_program_purchase_gys"
//
//    android:id="@+id/recycle_program_purchase_gys"
    LoadingLayout loadingLayoutGys;
    SuperTextView sptv_pz;
    SuperTextView sptv_gys;
    View sptv_line_gys;
    View sptv_line_pz;

    protected void switchGys_pz(int tag) {// 0  供应商 显示    1    品种   显示

        /*loadingLayoutGys  ----  > 供应商 列表*/
        loadingLayoutGys = getView(R.id.loading_layout_program_purchase_gys);




        /* 按钮 */
        sptv_gys = getView(R.id.sptv_gys);
        sptv_pz = getView(R.id.sptv_pz);

        /* line */
        sptv_line_pz = getView(R.id.sptv_line_pz);
        sptv_line_gys = getView(R.id.sptv_line_gys);

        sptv_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLongToast("pz");
                switchGys_pz(0);
            }
        });
        sptv_gys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLongToast("gys");
                switchGys_pz(1);
            }
        });

        if (tag == 1) {// 供应 商显示

            loadingLayoutGys.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            sptv_gys.setTextColor(getColorByRes(R.color.main_color));
            sptv_pz.setTextColor(getColorByRes(R.color.text_color666));
            sptv_line_pz.setVisibility(View.GONE);
            sptv_line_gys.setVisibility(View.VISIBLE);


        } else {// 品种 显示
//            ToastUtil.showLongToast("品种");
            loadingLayoutGys.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            sptv_gys.setTextColor(getColorByRes(R.color.text_color666));
            sptv_pz.setTextColor(getColorByRes(R.color.main_color));
            sptv_line_pz.setVisibility(View.VISIBLE);
            sptv_line_gys.setVisibility(View.GONE);
        }

       /*loadingLayout  ----  >   品种 列表*/


    }



        /*  品种 供应商   互相切换   并且 显示隐藏  start   */


    @Override
    protected void onDestroy() {

        if (myDownTime != null) {
            myDownTime.cancel();
            myDownTime = null;
        }

        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }


    public void hah(BaseViewHolder helper, SellerQuoteJsonBean item, String tag) {
//                            //supplier  //item.sendType.equals("hmeg")


        showLoading();
//                            coreRecyclerView.getRecyclerView().getAdapter().notifyItemChanged(helper.getAdapterPosition());
        MyPrestener myPrestener = new MyPrestener();
        myPrestener.addResultCallBack(new ResultCallBack<SimpleGsonBean>() {
            @Override
            public void onSuccess(SimpleGsonBean gsonBean) {
                int posParent = coreRecyclerView.getAdapter().getParentPosition(item);


                        /*sub 某个项 进行修改*/

                if (gsonBean.getData().quote != null) {
                    item.isUsed = gsonBean.getData().quote.isUsed;
                    item.isAlternative = gsonBean.getData().quote.isAlternative;
                } else {
                    item.isUsed = !item.isUsed;
                }

//                        item.isUsed = !item.isUsed;
                item.sendType = tag;

                ProgramPurchaseExpanBean expanBean = (ProgramPurchaseExpanBean) coreRecyclerView.getAdapter().getData().get(posParent);
                expanBean.quoteUsedCountJson = gsonBean.getData().quote.attrData.quoteUsedCountJson;
//                        expanBean.quoteUsedCountJson = gsonBean.getData().quoteUsedCountJson;
                expanBean.quotePreUsedCountJson = gsonBean.getData().quote.attrData.quotePreUsedCountJson;
                pareId = expanBean.id;
//
                //item 改变 sub 的值
                for (int i = 0; i < expanBean.quoteListJson.size(); i++) {
                    if (expanBean.quoteListJson.get(i).id.equals(item.id)) {
                        expanBean.quoteListJson.set(i, item);
                    }
                }
                //棒极了
                coreRecyclerView.getAdapter().notifyItemChanged(helper.getAdapterPosition(), item);
                coreRecyclerView.getAdapter().notifyItemChanged(posParent + coreRecyclerView.getAdapter().getHeaderLayoutCount(), expanBean);
//                                    for (int i = 0; i < expanBean.quoteListJson.size(); i++) {
//                                        if (expanBean.quoteListJson.get(i).id.equals(item.id)) {
//                                            expanBean.quoteListJson.set(i, item);
//
//                                        }
//                                    }


//                                    collapse(posParent);
//                                    expand(posParent);
//                                    ToastUtil.showShortToast("修改成功，准备刷新" + json);

                //成功后刷新
//                                    coreRecyclerView.onRefresh();

                hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                ToastUtil.showShortToast("修改失败");
                hindLoading();
            }
        });
        myPrestener.doUpData(item.id, tag);
    }

    // 设为备选
    public void bak(BaseViewHolder helper, SellerQuoteJsonBean item, String tag) {
        // tag = doBak   ||  unBak

//                if (tag.equals("doBak")) {
//                    admin/quote/alternativeQuote
        new BasePresenter()
                .putParams("id", item.id)
                .putParams("isSeller", !TextUtils.isEmpty(searchKey) + "")
                .doRequest("admin/quote/alternativeQuote", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        int posParent = coreRecyclerView.getAdapter().getParentPosition(item);

                                 /*sub 某个项 进行修改*/

                        if (gsonBean.getData().quote != null) {
                            item.isAlternative = gsonBean.getData().quote.isAlternative;
                        } else {
                            item.isAlternative = !item.isAlternative;
                        }
//                                item.isAlternative = !item.isAlternative;
//                                    item.sendType = tag;

                        ProgramPurchaseExpanBean expanBean = (ProgramPurchaseExpanBean) coreRecyclerView.getAdapter().getData().get(posParent);
                        expanBean.quoteUsedCountJson = gsonBean.getData().quote.attrData.quoteUsedCountJson;
                        expanBean.quotePreUsedCountJson = gsonBean.getData().quote.attrData.quotePreUsedCountJson;
                        pareId = expanBean.id;
//
                        //item 改变 sub 的值
                        for (int i = 0; i < expanBean.quoteListJson.size(); i++) {
                            if (expanBean.quoteListJson.get(i).id.equals(item.id)) {
                                expanBean.quoteListJson.set(i, item);
                            }
                        }
                        //棒极了
                        coreRecyclerView.getAdapter().notifyItemChanged(helper.getAdapterPosition(), item);
                        coreRecyclerView.getAdapter().notifyItemChanged(posParent + coreRecyclerView.getAdapter().getHeaderLayoutCount(), expanBean);
                    }
                });


//                } else {
//                    tag.equals("unBak")
//                }


    }


    private void doConvert1(BaseViewHolder helper, MultiItemEntity mItem) {

//                    D.e("======TYPE_LEVEL_1========");
        SellerQuoteJsonBean item = ((SellerQuoteJsonBean) mItem);
        int layoid = R.layout.item_program_purch_sub;
        boolean isConverted = !item.quoteImplementStatus.equals("uncovered") && !TextUtils.isEmpty(item.quoteImplementStatus);
                    /* 价格  +  名称    */


        helper.addOnClickListener(R.id.reason, new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (GetServerUrl.isTest)
                    ToastUtil.showLongToast("测试显示\n  查看原因\n" + item.unuseReason + " 是否现场核实 \n  " + isConverted + "  covered name = " + item.quoteImplementStatus);

                AlertUtil.showCommonEditDialog(getSupportFragmentManager(), new AlertUtil.DoConvertView() {
                    @Override
                    public void onConvert(Dialog viewRoot) {

                         /* 已落实状态 */
//                        boolean isConverted = !item.quoteImplementStatus.equals("uncovered") && !TextUtils.isEmpty(item.quoteImplementStatus);

                        EditText editText1 = viewRoot.findViewById(R.id.edit_content);

                        RadioButton yes = viewRoot.findViewById(R.id.yes);
                        RadioButton no = viewRoot.findViewById(R.id.no);

                        if (isConverted) {
                            yes.setChecked(true);
                            no.setVisibility(View.GONE);
                        } else {
                            no.setChecked(true);
                            yes.setVisibility(View.GONE);
                        }

                        editText1.setText(item.unuseReason);
                        editText1.setEnabled(false);


                        TextView save = viewRoot.findViewById(R.id.save);
                        save.setText("关闭");
                        save.setOnClickListener(v1 -> {
//                          dialog.dismiss();
                            viewRoot.dismiss();
                        });

                        View cancel = viewRoot.findViewById(R.id.cancel);
                        cancel.setOnClickListener(v1 -> {
                            viewRoot.dismiss();
                        });

                        cancel.setVisibility(View.GONE);

                    }
                });


            }
        });

        helper.setVisible(R.id.reason, false);
        helper.setVisible(R.id.bad, true);

        View.OnClickListener badClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertUtil.showCommonEditDialog(getSupportFragmentManager(), new AlertUtil.DoConvertView() {
                    @Override
                    public void onConvert(Dialog viewRoot) {

                         /* 已落实状态 */
                        boolean isConverted = !item.quoteImplementStatus.equals("uncovered") && !TextUtils.isEmpty(item.quoteImplementStatus);

                        EditText editText1 = viewRoot.findViewById(R.id.edit_content);

                        RadioButton yes = viewRoot.findViewById(R.id.yes);
                        RadioButton no = viewRoot.findViewById(R.id.no);

                        if (isConverted) {
                            yes.setChecked(true);
                            no.setVisibility(View.GONE);
                        } else {
                            no.setVisibility(View.VISIBLE);
                        }


                        View save = viewRoot.findViewById(R.id.save);
                        save.setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("save" + editText1.getText());

                            if (!yes.isChecked() && !no.isChecked()) {
                                ToastUtil.showLongToast("请确认是否现场合适");
                                return;
                            }
                            if (TextUtils.isEmpty(editText1.getText())) {
                                ToastUtil.showLongToast("请填写不合适原因");
                                return;
                            }


                            //String id,String isCovered,String unUseReason
                            new BasePresenter()
                                    .putParams("id", item.id)
                                    .putParams("isCovered", yes.isChecked() + "")
                                    .putParams("unUseReason", editText1.getText().toString())
                                    .doRequest("admin/quote/saveUnUsed", new HandlerAjaxCallBack(mActivity) {
                                        @Override
                                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                                            if (gsonBean.isSucceed()) {

//                                              item =   gsonBean.getData().quote;

//                                                recycle_program_purchase_gys.getAdapter().getData()
                                                int posParent = coreRecyclerView.getAdapter().getParentPosition(item);

                        /*sub 某个项 进行修改*/



//                        item.isUsed = !item.isUsed;

                                                ProgramPurchaseExpanBean expanBean = (ProgramPurchaseExpanBean) coreRecyclerView.getAdapter().getData().get(posParent);

//
                                                //item 改变 sub 的值
                                                for (int i = 0; i < expanBean.quoteListJson.size(); i++) {
                                                    if (expanBean.quoteListJson.get(i).id.equals(gsonBean.getData().quote.id)) {
//                                                        SellerQuoteJsonBean sellerQuoteJsonBean =   expanBean.quoteListJson.get(i);
//                                                        coreRecyclerView.getAdapter().getData().set(helper.getAdapterPosition(),sellerQuoteJsonBean );
//                                                        sellerQuoteJsonBean = gsonBean.getData().quote;
                                                        coreRecyclerView.getAdapter().getData().set(helper.getAdapterPosition()- coreRecyclerView.getAdapter().getHeaderLayoutCount(),gsonBean.getData().quote );
                                                        expanBean.quoteListJson.set(i, gsonBean.getData().quote);
                                                    }
                                                }
                                                //棒极了
                                                coreRecyclerView.getAdapter().notifyItemChanged(helper.getAdapterPosition(), gsonBean.getData().quote);


//                                                helper.setVisible(R.id.reason, true);
//                                                helper.setVisible(R.id.bad, false);
//                                                helper.setVisible(R.id.bak, false);
//                                                helper.setVisible(R.id.tv_program_purch_sub_use_state, false);
//
//                                                helper.setVisible(R.id.imageView, true)
//                                                        .setImageResource(R.id.imageView, R.mipmap.weizhongbiao)
//                                                ;
//                                                item.unuseReason = editText1.getText().toString();
//                                                item.status = "unused";
//
//                                                if (yes.isChecked()) {
//                                                    item.quoteImplementStatus = "covered";
//                                                } else {
//                                                    item.quoteImplementStatus = "uncovered";
//                                                }
                                                viewRoot.dismiss();
                                            }
                                        }
                                    });
                        });

                        View cancel = viewRoot.findViewById(R.id.cancel);
                        cancel.setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("cancel" + editText1.getText());
                            viewRoot.dismiss();
                        });


                    }
                });


            }
        };

        helper.addOnClickListener(R.id.bad, badClick);


        helper.setVisible(R.id.gai, item.attrData.priceChange);
        helper.addOnClickListener(R.id.gai, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertUtil.showCommonDialog(R.layout.item_show_price_change, getSupportFragmentManager(), viewRoot -> {

                    TextView textView = viewRoot.findViewById(R.id.content);


                    textView.setText(
                            new SpanUtils()
                                    .append("原报价：")
                                    .append(item.attrData.oldPrice).setForegroundColor(getColorByRes(R.color.price_orige))
                                    .append("/" + item.unitTypeName)
                                    .appendLine()
                                    .append("修改后报价：")
                                    .append(item.attrData.priceStr).setForegroundColor(getColorByRes(R.color.price_orige))
                                    .append("/" + item.unitTypeName)
                                    .appendLine()
                                    .append("操作人：" + item.attrData.updateUserName)
                                    .appendLine()
                                    .append("修改时间：" + item.attrData.priceChangeDate)
                                    .appendLine()
                                    .append("修改原因：" + item.attrData.reason)
                                    .create()
                    );
                    TextView close = viewRoot.findViewById(R.id.left);
                    close.setOnClickListener(v1 -> {
                        viewRoot.dismiss();
                    });


                }, true);


            }
        });

//                    String pri = !showQuote ? "*" : item.price + "";
                    /*暂时隐藏*/
//                    if (showQuote) {//已开标
//                        helper.setText(R.id.tv_program_purch_sub_price_type, filterColor("[" + FUtil.$_zero(item.plantTypeName) + "]" + item.price + "元/" + item.unitTypeName, item.price));// 3200/株
//                    } else {
//                        helper.setText(R.id.tv_program_purch_sub_price_type, filterColor("[" + FUtil.$_zero(item.plantTypeName) + "]" + " * " + "元/" + item.unitTypeName, " * "));// 3200/株
//                    }

                    /* 显示 成交单价：￥1.85 */
        helper.setText(R.id.tv_program_purch_sub_price_type,
                filterColor("[" + FUtil.$_null_to_you_say(item.plantTypeName, "未填写") + "]  "
                        + item.attrData.priceStr + "/" + item.unitTypeName, item.attrData.priceStr));// 3200/株


        helper.setText(R.id.tv_program_purch_sub_price_cont_serv_pric,
                new SpanUtils()
                        .append("成交单价：").setForegroundColor(getColorByRes(R.color.text_color666))
                        .append(item.attrData.finalPrice + "").setForegroundColor(getColorByRes(R.color.price_orige))
                        .create()
        );//￥3520(含服务费)
        // helper.setText(R.id.tv_program_purch_sub_price_cont_serv_pric,
//                            filterColor("成交单价:" + item.attrData.finalPrice + "",
//                                    item.attrData.finalPrice + "",
//                                    R.color.price_orige));//￥3520(含服务费)


        helper.setVisible(tv_program_purch_sub_price_cont_serv_pric, item.attrData.showFinalPrice);

        helper.setVisible(R.id.final_price, item.attrData.showFinalPrice);

        helper.setText(R.id.final_price,
                new SpanUtils()
                        .append("小计：").setForegroundColor(getColorByRes(R.color.text_color666))
                        .append(item.attrData.totalFinalPrice + "").setForegroundColor(getColorByRes(R.color.price_orige))
                        .create()
        );//￥3520(含服务费)

//                    helper.setText(R.id.final_price,
//                            filterColor("小计:" + item.attrData.totalFinalPrice + "",
//                                    item.attrData.totalFinalPrice + "",
//                                    R.color.price_orige));//￥3520(含服务费)


        helper.setBackgroundRes(tv_program_purch_sub_use_state, R.drawable.round_rectangle_bg_btn);//初始化设置drawable
        helper.setTextColorRes(tv_program_purch_sub_use_state, R.color.color_purch_sub);


        helper.setVisible(R.id.imageView, item.isUsed || item.isAlternative);// 采用  或者  备选
        helper.
                setText(R.id.tv_ygdhj, filterColor("预估到货价：" + FUtil.$_head("￥", item.prePrice), FUtil.$_head("￥", item.prePrice), R.color.orange));// 3200/株
//                    if (FUtil.$_zero_2_null(item.prePrice).equals("")) {
//
//                        helper.
//                                setText(R.id.tv_ygdhj, filterColor("预估到货价：" + "￥" + item.prePrice, "￥" + item.prePrice, R.color.orange));// 3200/株
//
//                    }

//                    TextView textView = helper.getView(R.id.tv_ygdhj);
//                    textView.append(filterColor("------预估到货价：" + item.prePrice, item.prePrice, R.color.fabu_yellow));


        //有报价权限   并且  已开标  --- 显示  按钮
        //!item.status.equals("unused")
        if (tureQuote && getExtralState().equals(PurchaseStatus.expired.enumValue) && !item.isExclude ) {
            helper.setVisible(tv_program_purch_sub_use_state, true);


            // 已采用的时候  设为备选  隐藏


            if (item.isUsed) { //已采用
                D.i("======isAlternative===是否备选=====" + item.isAlternative);

                helper.setVisible(R.id.bak, false);
                helper.setImageResource(R.id.imageView, R.drawable.yicaiyong_selector);

                // 已采用 隐藏 bad 不合适 按钮
//                helper.addOnClickListener(R.id.bad,badClick);
                helper.setVisible(R.id.bad, false);


            } else {//未采用
                helper.setVisible(R.id.bad, true);
                //备选 显示
                //没有采用   备选按钮显示
                helper.addOnClickListener(R.id.bak, v -> {
//                                ToastUtil.showLongToast("备选");

//                              helper.setText(R.id.bak, "取消备选");
                    if (item.isAlternative) {
                        AlertUtil.showAlert("确定取消备选?", mActivity, v1 -> bak(helper, item, ""));
                    } else {
                        AlertUtil.showAlert("确定设置报价为备选?", mActivity, v1 -> bak(helper, item, ""));
                    }
                });


                //备选按钮隐藏
                if (item.isAlternative) {
                    //是否备选.
                    helper.setText(R.id.bak, "取消备选");
                    helper.setVisible(R.id.bak, !isConverted);
                    helper.setImageResource(R.id.imageView, R.mipmap.beixuan);

                    helper.setBackgroundRes(R.id.bak, R.drawable.round_rectangle_bg_red);
                    helper.setTextColorRes(R.id.bak, R.color.price_orige);


                    helper.setVisible(R.id.bad, false);

                } else {
                    helper.setVisible(R.id.bak, !isConverted);
                    helper.setText(R.id.bak, "设为备选");
                    helper.setImageResource(R.id.imageView, R.drawable.yicaiyong_selector);
                    helper.setBackgroundRes(R.id.bak, R.drawable.round_rectangle_bg_btn);
                    helper.setTextColorRes(R.id.bak, R.color.main_color);
                }

                    /* 已落实状态  。 隐藏  备选按钮 */


            }




                    /*未采用&&是花木易购供应商&&未落实或者合格的显示采用按钮*/


            if (!item.isUsed) {
                //未采用


                //--  self.usedQuoteBtn.userInteractionEnabled=YES;
                //--  不显示 imageview

                //--  显示采用按钮
                helper.setText(tv_program_purch_sub_use_state, "采用");
                //--  绿色 色调 边界 跟 字体颜色
                //--  显示 采用 字
                helper.setSelected(tv_program_purch_sub_use_state, false);

                helper.addOnClickListener(tv_program_purch_sub_use_state, view -> {
                    if (!item.isUsed) {
                        AlertUtil.showAlert("确定设置报价为采用?", mActivity, v -> hah(helper, item, ""));
                    } else {
                        AlertUtil.showAlert("确定取消采用?", mActivity, v -> hah(helper, item, ""));
                    }
                    //hmeg

                });// 花木易购 采用




                    /* 未采用&&是自有供应商&&未落实或者合格的显示采用按钮*/
            }
//                    else if (!item.isUsed && item.attrData.isSupplier && (TextUtils.isEmpty(item.quoteImplementStatus) || item.quoteImplementStatus.equals("uncovered") || item.quoteImplementStatus.equals("qualified"))) {
//
//                        helper.setText(R.id.tv_program_purch_sub_use_state, "采用")
//                                .setSelected(R.id.tv_program_purch_sub_use_state, false); // 供应商 采用
//
//                        helper.addOnClickListener(R.id.tv_program_purch_sub_use_state, view -> {
//                            hah(helper, item, "");
//                        });
//                    }

                    /* <!-- 已采用&&未落实显示取消按钮 -->*/
            //(TextUtils.isEmpty(item.quoteImplementStatus)) ||
            else if (item.isUsed && (TextUtils.isEmpty(item.quoteImplementStatus)) || item.quoteImplementStatus.equals("uncovered")) {
                //    显示取消 按钮
                helper.setText(tv_program_purch_sub_use_state, "取消采用");

                helper.setBackgroundRes(tv_program_purch_sub_use_state, R.drawable.round_rectangle_bg_red);
                helper.setTextColorRes(tv_program_purch_sub_use_state, R.color.price_orige);
//                        helper.setSelected(R.id.tv_program_purch_sub_use_state, false);
                helper.addOnClickListener(tv_program_purch_sub_use_state, v -> {
                    if (!item.isUsed) {
                        AlertUtil.showAlert("确定设置报价为采用?", mActivity, v1 -> hah(helper, item, ""));
                    } else {
                        AlertUtil.showAlert("确定取消采用?", mActivity, v1 -> hah(helper, item, ""));
                    }
                });

                     /*<!-- 已采用&&合格的显示已采用并且锁定不可修改 -->*/
            } else if (item.isUsed && item.quoteImplementStatus.equals("covered")) {
                helper.setText(tv_program_purch_sub_use_state, "已采用")
                        .addOnClickListener(tv_program_purch_sub_use_state, null)
                        .setSelected(tv_program_purch_sub_use_state, true)
                        .setTextColorRes(tv_program_purch_sub_use_state, R.color.text_login_type)
                        .setVisible(R.id.tv_program_purch_sub_use_state, false)
                        .setSelected(R.id.imageView, true);
            } else {//兼容旧数据。。。默认 已采用
                helper.setText(R.id.tv_program_purch_sub_use_state, "已采用")
                        .addOnClickListener(tv_program_purch_sub_use_state, null)
                        .setSelected(tv_program_purch_sub_use_state, true)
                        .setTextColorRes(tv_program_purch_sub_use_state, R.color.text_login_type)
                        .setSelected(R.id.imageView, true);
            }


        } else {
            helper.setVisible(tv_program_purch_sub_use_state, false);
            helper.setVisible(R.id.bak, false);
            helper.setVisible(R.id.bad, false);


            // 未中标  item.status.equals("unused")  !
            if (!TextUtils.isEmpty(item.unuseReason) && item.isExclude) {
//                unused
                helper.setVisible(R.id.reason, true);
                helper.setVisible(R.id.imageView, true)
                        .setImageResource(R.id.imageView, R.mipmap.buheshi_gray)
                ;
            } else {
                helper.setVisible(R.id.reason, false);
                helper.setVisible(R.id.imageView, true)
                        .setImageResource(R.id.imageView, R.mipmap.buheshi_gray)
                ;
//                helper.setImageResource(R.id.imageView,)
            }


        }


        //供应商点击的时候

        if (searchKey.equals("") && ((getExtralState().equals(PurchaseStatus.expired.enumValue) || (getExtralState().equals(PurchaseStatus.finished.enumValue))))) {



                               /*是花木易购  */
            if (!item.attrData.isSupplier) {
                helper
//                                    .setText(R.id.tv_program_purch_sub_suplier, "花木易购供应商")
                        .setText(R.id.tv_program_purch_sub_suplier, item.attrData.sellerName)
                        .setTextColorRes(R.id.tv_program_purch_sub_suplier, R.color.main_color);

            } else {
                        /*是供应商 字段*/
                helper
                        .setText(R.id.tv_program_purch_sub_suplier, item.attrData.sellerName)//供应商
//                                    .setText(R.id.tv_program_purch_sub_suplier, "自有供应商" + item.sellerName)//供应商
                        .setTextColorRes(R.id.tv_program_purch_sub_suplier, R.color.price_orige)
                        .addOnClickListener(R.id.tv_program_purch_sub_suplier, v -> {
                            FlowerDetailActivity.CallPhone(item.sellerPhone, mActivity);
                        });
            }
            helper.setVisible(R.id.tv_program_purch_sub_suplier, true);

        } else {
            helper.setVisible(R.id.tv_program_purch_sub_suplier, false);
        }







                    /*由花木易购 显示文字  tv_program_purch_sub_who_send  通过 sendType  进行修改*/
//                    if (item.sendType.equals("hmeg")) {
//                        helper
//                                .setText(R.id.tv_program_purch_sub_who_send, "由花木易购发货");
//
//                    } else if (item.sendType.equals("supplier")) {
//                        helper.setText(R.id.tv_program_purch_sub_who_send, "由供应商发货");
//                    } else {
//                        helper.setText(R.id.tv_program_purch_sub_who_send, "");
//                    }

        helper.setText(R.id.tv_program_purch_sub_plant_addt,
                new SpanUtils()
                        .append("苗源地：").setForegroundColor(getColorByRes(R.color.text_color666))
                        .append(item.cityName).setForegroundColor(getColorByRes(R.color.text_color333))
                        .create()
        );//福建漳州

        helper.setText(R.id.tv_program_purch_sub_space_text,
                new SpanUtils()
                        .append("报价说明：").setForegroundColor(getColorByRes(R.color.text_color666))
                        .append(item.specText).setForegroundColor(getColorByRes(R.color.text_color333))
                        .append("  可供数量:").setForegroundColor(getColorByRes(R.color.text_color333))
                        .append(FUtil.$_zero(item.count + "")).setForegroundColor(getColorByRes(R.color.text_color333))
                        .create()

        );//
        helper.setText(R.id.tv_program_purch_sub_remark, item.implementRemarks);//备注

//                    helper.setSelected(R.id.tv_program_purch_sub_is_true, item.quoteImplementStatus.equals("qualified"));
        if (!item.quoteImplementStatus.equals("uncovered") && !TextUtils.isEmpty(item.quoteImplementStatus)) {
            //已核实
            helper.setText(R.id.tv_program_purch_sub_is_true, "已落实");//
            helper.setVisible(R.id.tv_program_purch_sub_is_true, true);
            helper.setSelected(R.id.tv_program_purch_sub_is_true, true);
            helper.setVisible(R.id.tv_program_purch_sub_remark, true);
        }
//                    else  {
//                        helper.setText(R.id.tv_program_purch_sub_is_true, "未核实");//
//                        helper.setVisible(R.id.tv_program_purch_sub_is_true, true);
//                        helper.setVisible(R.id.tv_program_purch_sub_remark, true);
//                        helper.setVisible(R.id.imageView, false);
//                        helper.setVisible(R.id.tv_program_purch_sub_use_state, false);
//
//                    }
        else {
            helper.setVisible(R.id.tv_program_purch_sub_is_true, false);
            helper.setVisible(R.id.tv_program_purch_sub_remark, false);
        }

//                    helper.setText(R.id.tv_program_purch_sub_is_true, "已核实");//
//                    helper.setVisible(R.id.tv_program_purch_sub_is_true, true);
//                    helper.setSelected(R.id.tv_program_purch_sub_is_true, true);
//                    helper.setVisible(R.id.tv_program_purch_sub_remark, true);


        D.e("=== \n" + item.toString());

        helper.setText(R.id.tv_program_purch_sub_plant_type, "种植类型：" + FUtil.$_zero(item.plantTypeName));//容器苗

//                    helper.setText(R.id.tv_bottom_left, filterColor("苗木图片：" + "有" + item.imagesJson.size() + "张图片", item.imagesJson.size() + ""));//
//                    helper.setText(R.id.tv_program_purch_sub_images_count, filterColor("有" + item.imagesJson.size() + "张图片", item.imagesJson.size() + "", R.color.orange_color));//
        PurchaseDetailActivity.setImgCounts(mActivity, helper.getView(R.id.tv_bottom_left), item.imagesJson);

//                    helper.setVisible(R.id.tv_bottom_left,item.imagesJson.size() != 0);

        if (item.imagesJson.size() != 0) {
            helper.setVisible(R.id.tv_program_purch_sub_images_count, true);
            helper.setText(R.id.tv_bottom_left,
                    new SpanUtils()
                            .append("苗木图片：有").setForegroundColor(getColorByRes(R.color.text_color666))
                            .append(item.imagesJson.size() + "").setForegroundColor(getColorByRes(R.color.price_orige))
                            .append("张图片").setForegroundColor(getColorByRes(R.color.text_color333))
                            .create()

            );//
            // filterColor("苗木图片：" + "有" + item.imagesJson.size() + "张图片", item.imagesJson.size() + "")
        } else {
            helper.getView(R.id.tv_program_purch_sub_images_count).setVisibility(View.INVISIBLE);
//"苗木图片：" + "未上传图片"
            helper.setText(R.id.tv_bottom_left,

                    new SpanUtils()
                            .append("苗木图片： ").setForegroundColor(getColorByRes(R.color.text_color666))
                            .append("未上传图片").setForegroundColor(getColorByRes(R.color.text_color333))
                            .create()
            );//
        }


        Log.e(TAG, "TYPE_LEVEL_1" + helper.getAdapterPosition());

    }

}
