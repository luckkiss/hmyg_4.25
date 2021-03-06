package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.Ui.DialogActivitySecond;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.StringFormatUtil;

import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity.strFilter;

/**
 * 二轮报价   直接跳过 一轮报价  适配器
 */
@SuppressLint("ResourceAsColor")
public abstract class StorePurchaseListAdapter_new_along_second extends StorePurchaseListAdapter_new {


    public StorePurchaseListAdapter_new_along_second(Context context, List<PurchaseItemBean_new> data, int layoutId) {
        super(context, data, layoutId);


    }

//    @Override
//    protected void initListView(ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {
//        super.initListView(listView, context, purchaseItemBeanNew);
//    }


    @Override
    public void setConverView(ViewHolders myViewHolder, final PurchaseItemBean_new purchaseItemBeanNew, int position) {
        super.setConverView(myViewHolder, purchaseItemBeanNew, position);


        ((TextView) myViewHolder.getView(R.id.tv_10)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLongToast("被点击了");

                TextView me = ((TextView) v);


//                SpannableStringBuilder text = ((SpannableStringBuilder) me.getText());
//                if (v.isSelected()) {
//                    text.replace(2, 4, new SpannableString("张开"));
//                } else {
//                    text.replace(2, 4, new SpannableString("关闭"));
//                }


                Log.i("is selected", "onClick: " + me.isSelected());
//                setTv10(me, purchaseItemBeanNew, !v.isSelected());
                if (me.getText().toString().contains("隐藏")) {//如果  点击的是 已经展开。不需要请求网络。直接清除  item 列表
                    D.i("=======如果  点击的是 已经展开。不需要请求网络。直接清除  item 列表=======");

                    if (purchaseItemBeanNew.sellerQuoteListJson != null)
                        purchaseItemBeanNew.sellerQuoteListJson.clear();

//                    notifyDataSetChanged();

                    ListView listView = myViewHolder.getView(R.id.list);
                    GlobBaseAdapter globBaseAdapter = null;
                    if (listView.getAdapter() instanceof GlobBaseAdapter) {
                        globBaseAdapter = (GlobBaseAdapter) listView.getAdapter();
                    } else if (listView.getAdapter() instanceof WrapperListAdapter) {
                        globBaseAdapter = (GlobBaseAdapter) ((WrapperListAdapter) listView.getAdapter()).getWrappedAdapter();
                    }

//                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) listView.getAdapter();
                    globBaseAdapter.getDatas().clear();
                    globBaseAdapter.notifyDataSetChanged();
                    me.setSelected(true);
                    setTv10(me, purchaseItemBeanNew, true);
                    return;
                } else {
                    new BasePresenter()
                            .putParams("id", purchaseItemBeanNew.id)
                            .doRequest("admin/purchase/itemQuoteList", true, new HandlerAjaxCallBack((NeedSwipeBackActivity) context) {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                    ToastUtil.showLongToast(gsonBean.msg);
//                                    for (int i = 0; i < getDatas().size(); i++) {
//                                        if (getDatas().get(i).id.equals(purchaseItemBeanNew.id)) {
//                                            getDatas().set(i, gsonBean.getData().purchaseItem);
//                                            notifyDataSetChanged();
//                                            break;
//                                        }
//                                    }
                                    ListView listView = myViewHolder.getView(R.id.list);
//                                    GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) listView.getAdapter();

                                    GlobBaseAdapter globBaseAdapter = null;
                                    if (listView.getAdapter() instanceof GlobBaseAdapter) {
                                        globBaseAdapter = (GlobBaseAdapter) listView.getAdapter();
                                    } else if (listView.getAdapter() instanceof WrapperListAdapter) {
                                        globBaseAdapter = (GlobBaseAdapter) ((WrapperListAdapter) listView.getAdapter()).getWrappedAdapter();
                                    }


                                    globBaseAdapter.addData(gsonBean.getData().purchaseItem.sellerQuoteListJson);
                                    purchaseItemBeanNew.sellerQuoteListJson = gsonBean.getData().purchaseItem.sellerQuoteListJson;
                                    globBaseAdapter.notifyDataSetChanged();
                                    me.setSelected(false);
                                    setTv10(me, purchaseItemBeanNew, false);
                                }
                            });
                }


//                me.setText(text);


            }
        });


        myViewHolder.getView(R.id.tv_10).setVisibility(purchaseItemBeanNew.isQuoted ? View.VISIBLE : View.GONE);


          /*    去除点击   已经报价一次的 请情况   不允许 继续点击报价。。只能修改*/
        if (purchaseItemBeanNew.footSellerQuoteListJson != null) {
            myViewHolder.getConvertView().setOnClickListener(null);
        }

    }

    @Override
    protected void jump2Quote(Activity context, PurchaseItemBean_new purchaseItemBeanNew, ListView listView, ViewHolders parentHolders) {
//      super.jump2Quote(context, purchaseItemBeanNew);
        purchaseItemBeanNew.pid1 = getItemId();
        purchaseItemBeanNew.pid2 = getItemId();
        DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew);
    }

    @Override
    protected void initListView(ViewHolders parentHolders, ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {

        listView.setAdapter(new GlobBaseAdapter<SellerQuoteJsonBean>(context, purchaseItemBeanNew.sellerQuoteListJson, R.layout.item_purchase_second) {
            @Override
            public void setConverView(ViewHolders myViewHolder, SellerQuoteJsonBean jsonBean, int position) {
                D.e("=====setConverView======str=============" + jsonBean.toString());

                /*价格*/
                TextView tv = myViewHolder.getView(R.id.tv_quote_item_price);
                tv.setText("¥" + jsonBean.price + "/" + jsonBean.unitTypeName);
                //价格 变色
                StringFormatUtil fillColorPrice = new StringFormatUtil(context, "¥" + jsonBean.price + "/" + jsonBean.unitTypeName, "¥" + jsonBean.price, R.color.red)
                        .fillColor();
                tv.setText(fillColorPrice.getResult());



                     /*植物类型  [容器苗 假植苗   ---  ]*/
                TextView type = myViewHolder.getView(R.id.type);
                type.setText("[" + jsonBean.plantTypeName + "]");

                /*报价时间*/
                TextView time = myViewHolder.getView(R.id.tv_quote_item_time);
                time.setText(jsonBean.quoteDateStr);

               /*预估到货价*/
                TextView pre_price = myViewHolder.getView(R.id.tv_quote_item_pre_price);
                pre_price.setText(FUtil.$_head("¥", jsonBean.prePrice));

                 /*可供数量*/
                TextView count = myViewHolder.getView(R.id.tv_quote_item_count);
                count.setText(FUtil.$_zero(jsonBean.count + ""));

                     /*品种规格*/
                TextView specText = myViewHolder.getView(R.id.tv_quote_item_specText);
                specText.setText(jsonBean.specText);

                     /*品种规格*/
                TextView cityName = myViewHolder.getView(R.id.tv_quote_item_cityName);
                cityName.setText(jsonBean.cityName);

                   /*种植类型*/
                TextView plantTypeName = myViewHolder.getView(R.id.tv_quote_item_plantTypeName);
                plantTypeName.setText(jsonBean.plantTypeName);

                    /*报价说明*/
                TextView remark = myViewHolder.getView(R.id.tv_quote_item_declare);
//                remark.setText(FUtil.$_zero(jsonBean.remarks) + );
//                remark.setText(jsonBean.specText + " " + FUtil.$_zero(jsonBean.remarks) + "  可供数量" + FUtil.$_zero(jsonBean.count + ""));
                remark.setText(FUtil.$(" ", FUtil.$_head_no_("", jsonBean.specText), FUtil.$_head_no_("", jsonBean.remarks), FUtil.$_head_no_("可供数量", jsonBean.count + "")));

                      /*苗木图片*/
                SuperTextView photo_num = myViewHolder.getView(R.id.tv_quote_item_photo_num);
                photo_num.setText("有" + strFilter("1") + "张图片");//有多少张图片
                PurchaseDetailActivity.setImgCounts((Activity) context, photo_num, jsonBean.imagesJson);
                if (jsonBean.imagesJson.size() > 0) {
                    //有多少张图片
                    StringFormatUtil fillColor = new StringFormatUtil(context, "有" + jsonBean.imagesJson.size() + "张图片", jsonBean.imagesJson.size() + "", R.color.red)
                            .fillColor();
                    photo_num.setText(fillColor.getResult());
                    photo_num.setShowState(true);
                }else {
                    photo_num.setShowState(false);

                }

//                textView35  苗源地


                TextView city = myViewHolder.getView(R.id.tv_quote_item_cityName);
                city.setText(FUtil.$_zero(jsonBean.cityName));


//                TextView textView42 = myViewHolder.getView(R.id.tv_quote_item_declare);
//                textView42.setText(FUtil.$_zero(jsonBean.remarks));

                TextView state = myViewHolder.getView(R.id.tv_show_is_quote);

//              StringFormatUtil formatUtil = new StringFormatUtil(context, "当前报价状态：" + getStateName(jsonBean.status), getStateName(jsonBean.status), ContextCompat.getColor(context, R.color.orange)).fillColor();
                state.setText(jsonBean.createDate);
//                state.setText(getStateName(jsonBean.status));






//                if ("choosing".equals(jsonBean.status)) {
//                    //选中标   ---   全部显示
//
//                } else if ("preQuote".equals(jsonBean.status)) {
//                    // 隐藏不需要的东西
//                    ((ViewGroup) time.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
//                    photo_num.setVisibility(View.GONE);
//                    TextView abc = myViewHolder.getView(R.id.abc);
//                    abc.setVisibility(View.GONE);
//                    abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
//                    abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));
//                    myViewHolder.getView(R.id.tv_delete_item).setVisibility(View.GONE);
//                    myViewHolder.getView(R.id.tv_change_item).setVisibility(View.GONE);
//                }  /**
//                 * 已中标，条件：合格并且已被采用
//                 */
//
//                else if ("used".equals(jsonBean.status)) {
//                    //已中标
//
//                    state.setTextColor(ContextCompat.getColor(context, R.color.main_color));
//
//                    myViewHolder.getView(R.id.tv_delete_item).setVisibility(View.GONE);
//                    myViewHolder.getView(R.id.tv_change_item).setVisibility(View.GONE);
//                } else {
//                    // 隐藏不需要的东西
//                    ((ViewGroup) time.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
//                    ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
//                    photo_num.setVisibility(View.GONE);
//                    TextView abc = myViewHolder.getView(R.id.abc);
//                    abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
//                    abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));
//                }







                /*删除*/
                myViewHolder.getView(R.id.tv_delete_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        doDelete(v, purchaseItemBeanNew, position, jsonBean);


//                        ToastUtil.showLongToast("delete");
//                        purchaseItemBeanNew.sellerQuoteListJson.remove(position);
//                        notifyDataSetChanged();
                    }
                });

                /*编辑*/
                myViewHolder.getView(R.id.tv_change_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                      ToastUtil.showLongToast("马上报价");
//                      purchaseItemBeanNew.sellerQuoteListJson.set(position, null);
//                      notifyDataSetChanged();
//                        DialogActivity.start((Activity) context, purchaseItemBeanNew, jsonBean);


//                        purchaseItemBeanNew.pid1 = getItemId();
//                        purchaseItemBeanNew.pid2 = getItemId();
                        DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew, jsonBean);


                    }
                });


                processChildHolders(myViewHolder, jsonBean);


            }
        });


    }

    protected void processChildHolders(ViewHolders myViewHolder, SellerQuoteJsonBean jsonBean) {
//        执行   删除按钮 隐藏时使用。。子类调用     只有 package 打包报价时强制隐藏删除按钮
    }

    ;


}


