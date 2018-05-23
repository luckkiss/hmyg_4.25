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

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.ProgramPurchaseExpanBean;
import com.hldj.hmyg.M.QuoteListJsonBean;
import com.hldj.hmyg.M.QuoteUserGroup;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.Ui.WebViewDialogFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
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

import static com.hldj.hmyg.R.id.tv_program_purch_sub_price_cont_serv_pric;
import static com.hldj.hmyg.R.id.tv_program_purch_sub_use_state;
import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;
import static com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter.TYPE_LEVEL_0;


/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivity extends BaseMVPActivity<ProgramPurchasePresenter, ProgramPurchaseModel> implements ProgramPurchaseContract.View {

    private static final String TAG = "ProgramPurchaseActivity";
    protected CoreRecyclerView coreRecyclerView;
    private CoreRecyclerView recycle_program_purchase_gys;
    private LoadingLayout loadingLayout;
    private LinearLayout ll_head;
    private boolean showQuote = false; /* 表示是否开标 */
    private MyDownTime myDownTime;
    private boolean tureQuote;//可以选标
    private ExpandableItemAdapter itemAdapter;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_program_purchase;
    }


    //public int shouldOpenPos = 666;
    public String pareId = "";
    protected String searchKey = "";

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

                    /* 价格  +  名称    */


//                    String pri = !showQuote ? "*" : item.price + "";
                    /*暂时隐藏*/
                    if (showQuote) {//已开标
                        helper.setText(R.id.tv_program_purch_sub_price_type, filterColor("[" + FUtil.$_zero(item.plantTypeName) + "]" + item.price + "元/" + item.unitTypeName, item.price));// 3200/株
                    } else {
                        helper.setText(R.id.tv_program_purch_sub_price_type, filterColor("[" + FUtil.$_zero(item.plantTypeName) + "]" + " * " + "元/" + item.unitTypeName, " * "));// 3200/株
                    }

                    helper.setText(tv_program_purch_sub_price_cont_serv_pric, filterColor(item.attrData.servicePrice + "(含服务费)", item.attrData.servicePrice + "", R.color.price_orige));//￥3520(含服务费)

//                    helper.setVisible(tv_program_purch_sub_price_cont_serv_pric,false);


                    helper.setBackgroundRes(tv_program_purch_sub_use_state, R.drawable.round_rectangle_bg_btn);//初始化设置drawable
                    helper.setTextColorRes(tv_program_purch_sub_use_state, R.color.color_purch_sub);


                    helper.setVisible(R.id.imageView, item.isUsed);// 采用 未采用
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


                    if (tureQuote && showQuote) {
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
                            helper.setText(R.id.tv_program_purch_sub_use_state, "已采用")
                                    .addOnClickListener(tv_program_purch_sub_use_state, null)
                                    .setSelected(tv_program_purch_sub_use_state, true)
                                    .setTextColorRes(tv_program_purch_sub_use_state, R.color.text_login_type)
                                    .setSelected(R.id.imageView, true);
                        }


                    } else {
                        helper.setVisible(tv_program_purch_sub_use_state, false);
                    }

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
                    helper.setText(R.id.tv_program_purch_sub_space_text, "报价说明：" + item.specText + "; " + FUtil.$_zero_2_null(item.remarks) + "  可供数量:" + FUtil.$_zero(item.count));//
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

                    if (item.imagesJson.size() != 0) {
                        helper.setVisible(R.id.tv_program_purch_sub_images_count, true);
                        helper.setText(R.id.tv_bottom_left, filterColor("苗木图片：" + "有" + item.imagesJson.size() + "张图片", item.imagesJson.size() + ""));//
                    } else {
                        helper.getView(R.id.tv_program_purch_sub_images_count).setVisibility(View.INVISIBLE);

                        helper.setText(R.id.tv_bottom_left, "苗木图片：" + "未上传图片");//
                    }


                    Log.e(TAG, "TYPE_LEVEL_1" + helper.getAdapterPosition());

                } else if (mItem.getItemType() == TYPE_LEVEL_0) {

                    ProgramPurchaseExpanBean item = ((ProgramPurchaseExpanBean) mItem);
                    //default
//                    helper.setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "");

                    helper.setText(R.id.tv_program_purch_pos, item.index + "");

                    helper.setText(R.id.tv_program_purch_name, item.name);
                    helper.setText(R.id.tv_program_purch_num_type, item.count + item.unitTypeName);
                    helper.setText(R.id.tv_zzlx, "种植类型：" + item.plantTypeName);
                    helper.setText(R.id.tv_program_purch_space_text, "规格：" + item.specText + "；" + item.remarks);
                    Log.e(TAG, "TYPE_LEVEL_0" + helper.getAdapterPosition());

                    helper.setVisible(R.id.tv_program_purch_price_num, item.count != 0);


                    String wholeStr = "有" + item.quoteCountJson + "条报价";
                    StringFormatUtil formatUtil = new StringFormatUtil(mActivity, wholeStr, item.quoteCountJson + "", R.color.red).fillColor();
                    helper.setText(R.id.tv_program_purch_price_num, formatUtil.getResult());


                    helper.setText(R.id.tv_program_purch_alreay_order, "已采用" + item.quoteUsedCountJson + "条");

                    helper.setVisible(R.id.tv_program_purch_alreay_order, showQuote);

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
   /*    获取品种数据    */
        mPresenter.getData(0 + "", getExtral(), searchKey);
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

        mPresenter.getDatasGys("0", getExtral(), "");


    }

    private void xuanran(BaseViewHolder helper, QuoteUserGroup item) {

        /*  获取是否开标     */

        helper
                .setText(R.id.tv_program_purch_pos, (helper.getAdapterPosition() + 1) + "")
                .setText(R.id.title, "" + item.sellerName)
                .setText(R.id.tv_pzsl, filterColor("报价品种数量：" + item.quoteItemCount + "个", item.quoteItemCount + ""))
                .setText(R.id.tv_ycy, filterColor("已采用：" + item.usedCount + "个品种", item.usedCount + ""))
                .setText(R.id.tv_myd, item.cityNames == null ? "苗源地 ：-" : "苗源地：" + item.cityNames.toString().substring(1, item.cityNames.toString().length() - 1))
                .setText(R.id.tv_right_price, showQuote ? "￥\n" + item.quoteTotalPrice + "起" : "")
                .setBackgroundRes(R.id.tv_right_price, showQuote ? 0 : R.mipmap.wkb)
                .addOnClickListener(R.id.tv_detail, v -> {
//                    ToastUtil.showLongToast("-----------查看详情-----------" + item.sellerId);
                    ProgramPurchaseActivityOnly.title = "" + item.sellerName + "的报价";

                    /*  已开标 */
                    if (showQuote) {
                        ProgramPurchaseActivityOnly.totlePrice = "￥ " + item.quoteTotalPrice + " 起";
                    } else {
                        /* 未开标 */
                        ProgramPurchaseActivityOnly.totlePrice = "";
                    }

                    ProgramPurchaseActivityOnly.start(mActivity, getExtral(), item.sellerId);

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
        tv_program_pur_projectName_num.setText(purchaseBean.name + "(" + purchaseBean.num + ")");
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
            putParams("sellerId", searchKey);
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
        super.onDestroy();
        if (myDownTime != null) {
            myDownTime.cancel();
            myDownTime = null;
        }
    }
}
