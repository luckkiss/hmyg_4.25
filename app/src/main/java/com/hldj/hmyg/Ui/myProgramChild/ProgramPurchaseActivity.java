package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseExpanBean;
import com.hldj.hmyg.M.QuoteListJsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.Ui.WebViewDialogFragment;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.IExpandable;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.contract.ProgramPurchaseContract;
import com.hldj.hmyg.model.ProgramPurchaseModel;
import com.hldj.hmyg.presenter.ProgramPurchasePresenter;
import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.DownTime;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.hldj.hmyg.R.id.tv_program_purch_sub_use_state;
import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;
import static com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter.TYPE_LEVEL_0;


/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivity extends BaseMVPActivity<ProgramPurchasePresenter, ProgramPurchaseModel> implements ProgramPurchaseContract.View {

    private static final String TAG = "ProgramPurchaseActivity";
    private CoreRecyclerView coreRecyclerView;
    private LoadingLayout loadingLayout;
    private LinearLayout ll_head;
    private boolean showQuote = false;
    private MyDownTime myDownTime;
    private boolean tureQuote;//可以选标

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_purchase;
    }


    //public int shouldOpenPos = 666;
    public String pareId = "";
    private String searchKey = "";

    @Override
    public void initView() {

        EditText editText = getView(R.id.et_program_serach_text);
        editText.setHint("请输入采购单名称");

        showLoading();

        getView(R.id.sptv_program_do_search).setOnClickListener(view -> {
            searchKey = getSerachText();
            coreRecyclerView.onRefresh();
        });

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

        ExpandableItemAdapter itemAdapter = new ExpandableItemAdapter(R.layout.item_program_purch, R.layout.item_program_purch_sub) {

            public void hah(BaseViewHolder helper, QuoteListJsonBean item, String tag) {
//                            //supplier  //item.sendType.equals("hmeg")


//                            coreRecyclerView.getRecyclerView().getAdapter().notifyItemChanged(helper.getAdapterPosition());
                MyPrestener myPrestener = new MyPrestener();
                myPrestener.addResultCallBack(new ResultCallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer countRsl) {
                        int posParent = coreRecyclerView.getAdapter().getParentPosition(item);

                        /*sub 某个项 进行修改*/
                        item.isUsed = !item.isUsed;
                        item.sendType = tag;

                        ProgramPurchaseExpanBean expanBean = (ProgramPurchaseExpanBean) coreRecyclerView.getAdapter().getData().get(posParent);
                        expanBean.quoteUsedCountJson = countRsl;
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
                        coreRecyclerView.getAdapter().notifyItemChanged(posParent, expanBean);
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


                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        ToastUtil.showShortToast("修改失败");
                    }
                });
                myPrestener.doUpData(item.id, tag);
            }

            @Override
            protected void convert(BaseViewHolder helper, MultiItemEntity mItem) {
                if (mItem.getItemType() == TYPE_LEVEL_1)//子布局
                {

//                    D.e("======TYPE_LEVEL_1========");
                    QuoteListJsonBean item = ((QuoteListJsonBean) mItem);
                    int layoid = R.layout.item_program_purch_sub;

                    helper.setText(R.id.tv_program_purch_sub_price_type, filterColor(item.price + "/" + item.unitTypeName, item.price));// 3200/株
                    helper.setText(R.id.tv_program_purch_sub_price_cont_serv_pric, filterColor(item.attrData.servicePrice + "(含服务费)", item.attrData.servicePrice + "", R.color.price_orige));//￥3520(含服务费)


                    helper.setBackgroundRes(tv_program_purch_sub_use_state, R.drawable.round_rectangle_bg_btn);//初始化设置drawable
                    helper.setTextColorRes(tv_program_purch_sub_use_state, R.color.color_purch_sub);


                    helper.setVisible(R.id.imageView, item.isUsed);// 采用 未采用


                    if (tureQuote) {
                        helper.setVisible(tv_program_purch_sub_use_state, true);
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
                                hah(helper, item, "");
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
                            helper.setText(tv_program_purch_sub_use_state, "取消");

                            helper.setBackgroundRes(tv_program_purch_sub_use_state, R.drawable.round_rectangle_bg_red);
                            helper.setTextColorRes(tv_program_purch_sub_use_state, R.color.price_orige);
//                        helper.setSelected(R.id.tv_program_purch_sub_use_state, false);
                            helper.addOnClickListener(tv_program_purch_sub_use_state, v -> {
                                hah(helper, item, "");
                            });

                     /*<!-- 已采用&&合格的显示已采用并且锁定不可修改 -->*/
                        } else if (item.isUsed && item.quoteImplementStatus.equals("covered")) {
                            helper.setText(tv_program_purch_sub_use_state, "已采用")
                                    .addOnClickListener(tv_program_purch_sub_use_state, null)
                                    .setSelected(tv_program_purch_sub_use_state, true)
                                    .setTextColorRes(tv_program_purch_sub_use_state, R.color.text_login_type)
                                    .setSelected(R.id.imageView, true);
                        } else {//兼容旧数据。。。默认 已采用
                            helper.setText(tv_program_purch_sub_use_state, "已采用")
                                    .addOnClickListener(tv_program_purch_sub_use_state, null)
                                    .setSelected(tv_program_purch_sub_use_state, true)
                                    .setTextColorRes(tv_program_purch_sub_use_state, R.color.text_login_type)
                                    .setSelected(R.id.imageView, true);
                        }

                     /*是花木易购  */
                        if (!item.attrData.isSupplier) {
                            helper
                                    .setText(R.id.tv_program_purch_sub_suplier, "花木易购供应商")
                                    .setTextColorRes(R.id.tv_program_purch_sub_suplier, R.color.main_color);

                        } else {
                        /*是供应商 字段*/
                            helper
                                    .setText(R.id.tv_program_purch_sub_suplier, "自有供应商" + item.sellerName)//供应商
                                    .setTextColorRes(R.id.tv_program_purch_sub_suplier, R.color.price_orige)
                                    .addOnClickListener(R.id.tv_program_purch_sub_suplier, v -> {
                                        FlowerDetailActivity.CallPhone(item.sellerPhone, mActivity);
                                    });
                        }


                    } else {
                        helper.setVisible(tv_program_purch_sub_use_state, false);
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

                    helper.setText(R.id.tv_program_purch_sub_plant_addt, "苗源地：" + item.cityName);//福建漳州
                    helper.setText(R.id.tv_program_purch_sub_space_text, "规格：" + item.specText + "; " + FUtil.$_zero_2_null(item.remarks));//
                    helper.setText(R.id.tv_program_purch_sub_remark, item.implementRemarks);//备注

//                    helper.setSelected(R.id.tv_program_purch_sub_is_true, item.quoteImplementStatus.equals("qualified"));
                    if (!item.quoteImplementStatus.equals("uncovered") && !TextUtils.isEmpty(item.quoteImplementStatus)) {
                        //已核实
                        helper.setText(R.id.tv_program_purch_sub_is_true, "已核实");//
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
                    D.e("=== \n" + item.toString());

                    helper.setText(R.id.tv_program_purch_sub_plant_type, "种植类型：" + item.plantTypeName);//容器苗

                    helper.setText(R.id.tv_program_purch_sub_images_count, filterColor("有" + item.imagesJson.size() + "张图片", item.imagesJson.size() + "", R.color.orange_color));//
                    PurchaseDetailActivity.setImgCounts(mActivity, helper.getView(R.id.tv_program_purch_sub_images_count), item.imagesJson);

                    Log.e(TAG, "TYPE_LEVEL_1" + helper.getAdapterPosition());

                } else if (mItem.getItemType() == TYPE_LEVEL_0) {

                    ProgramPurchaseExpanBean item = ((ProgramPurchaseExpanBean) mItem);
                    //default
//                    helper.setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "");

                    helper.setText(R.id.tv_program_purch_pos, item.index + "");

                    helper.setText(R.id.tv_program_purch_name, item.name);
                    helper.setText(R.id.tv_program_purch_num_type, item.count + item.unitTypeName);
                    helper.setText(R.id.tv_program_purch_space_text, "规格：" + item.specText + "；" + item.remarks);
                    Log.e(TAG, "TYPE_LEVEL_0" + helper.getAdapterPosition());

                    helper.setVisible(R.id.tv_program_purch_price_num, item.count != 0);


                    String wholeStr = "共有" + item.quoteCountJson + "条报价";
                    StringFormatUtil formatUtil = new StringFormatUtil(mActivity, wholeStr, item.quoteCountJson + "", R.color.red).fillColor();
                    helper.setText(R.id.tv_program_purch_price_num, formatUtil.getResult());

                    helper.setText(R.id.tv_program_purch_alreay_order, "已采用" + item.quoteUsedCountJson + "条");
                    helper.setVisible(R.id.iv_program_purch_right_top, item.quoteUsedCountJson != 0);

//                    GetServerUrl.isTest ||
                    helper.setVisible(R.id.tv_program_purch_see_detail, showQuote);//测试时必然显示
                    helper.setSelected(R.id.tv_program_purch_see_detail, item.isExpanded());//测试时必然显示
                    helper.setText(R.id.tv_program_purch_see_detail, !item.isExpanded() ? "查看报价" : "收起报价");

                    if (item.quoteListJson.size() != 0) {
                        item.setSubItems(item.quoteListJson);
                    }

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
                .openLoadMore(10, page -> {
                    showLoading();
                    if (page == 0) {
                        count = 1;
                    }
                    mPresenter.getData(page + "", getExtral(), searchKey);
                })
                .closeDefaultEmptyView()
                .openRefresh();

//        coreRecyclerView.removeAllFooterView();
//        coreRecyclerView.removeAllHeaderView();



        //加载 index 信息 head
        mPresenter.getIndexDatas(getExtral());

        Observable.just("hellow world")
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cus -> {
                    coreRecyclerView.onRefresh();
                });
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

    //传值开启界面
    public static void start(Activity mAct, String ext) {
        Intent intent = new Intent(mAct, ProgramPurchaseActivity.class);
        intent.putExtra(TAG, ext);
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
    public void initHeadDatas(PurchaseBean purchaseBean) {
        //初始化头部
        ll_head.setVisibility(View.VISIBLE);
        TextView tv_head_ser_po = getView(R.id.tv_head_ser_po);
        TextView tv_no_permision = getView(R.id.tv_no_permision);

        showQuote = purchaseBean.showQuote;
//        tureQuote = true;
        tureQuote = purchaseBean.tureQuote;
        /**/
        if (purchaseBean.showQuote) {
            tv_head_ser_po.setText("以下报价未包含 " + purchaseBean.servicePoint + "% 服务费");
        } else {
            tv_head_ser_po.setText("开标倒计时：" + purchaseBean.lastTime + "% 服务费");
            myDownTime = new MyDownTime(purchaseBean.lastTime, 1000l, tv_head_ser_po);
            myDownTime.start();
        }

        if (tureQuote) {
            // 可以报价权限
            // 什么都不用变
            tv_no_permision.setVisibility(View.GONE);
        } else {
            tv_no_permision.setVisibility(View.VISIBLE);
            //隐藏   ---    显示一个没有权限的提示
        }


        TextView tv_program_pur_projectName_num = getView(R.id.tv_program_pur_projectName_num);
        TextView tv_program_pur_close_time = getView(R.id.tv_program_pur_close_time);
        TextView tv_program_pur_city = getView(R.id.tv_program_pur_city);
        TextView tv_program_pur_phoner = getView(R.id.tv_program_pur_phoner);
        TextView tv_program_pur_phone_num = getView(R.id.tv_program_pur_phone_num);
        tv_program_pur_projectName_num.setText(purchaseBean.projectName + "(" + purchaseBean.num + ")");
        tv_program_pur_city.setText("用苗地：" + purchaseBean.cityName);
        tv_program_pur_phoner.setText("联系客服：" + purchaseBean.dispatchName);
        if (purchaseBean.num != null)
            ((TextView) getView(R.id.tv_activity_purchase_back)).setText(purchaseBean.num);
        tv_program_pur_phone_num.setText(purchaseBean.dispatchPhone);
        tv_program_pur_phone_num.setOnClickListener(view -> FlowerDetailActivity.CallPhone(purchaseBean.dispatchPhone, mActivity));

//        tv_program_pur_phone_num.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_program_pur_close_time.setText("报价截止时间：" + purchaseBean.closeDate);


        //报价要求
        getView(R.id.stv_1).setOnClickListener(v -> {
            WebViewDialogFragment.newInstance(purchaseBean.quoteDesc).show(getSupportFragmentManager(), "报价要求");
        });
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
            AjaxCallBack callBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {
                    SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                    if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        resultCallBack.onSuccess(simpleGsonBean.getData().quoteUsedCount);
                    } else {
                        ToastUtil.showShortToast(simpleGsonBean.msg);
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    resultCallBack.onFailure(t, errorNo, strMsg);
                }
            };

            doRequest("admin/project/usedQuote", true, callBack);
        }

    }


    public SpannableStringBuilder filterColor(String wholeStr, String hightLightStr) {

        return filterColor(wholeStr, hightLightStr, R.color.red);
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
                ProgramPurchaseActivity.start(mActivity, getExtral());
                finish();
            }, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDownTime != null) {
            myDownTime.cancel();
            myDownTime = null;
        }
    }
}
