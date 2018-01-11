package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.Ui.DialogActivitySecond;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;

import java.util.List;

import static com.hldj.hmyg.R.id.tv_change_item;
import static me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity.strFilter;

/**
 * 一轮报价  适配器
 */
@SuppressLint("ResourceAsColor")
public abstract class StorePurchaseListAdapter_new_second extends StorePurchaseListAdapter_new {


    public StorePurchaseListAdapter_new_second(Context context, List<PurchaseItemBean_new> data, int layoutId) {
        super(context, data, layoutId);
    }

//    @Override
//    protected void initListView(ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {
//        super.initListView(listView, context, purchaseItemBeanNew);
//    }


    @Override
    protected void jump2Quote(Activity context, PurchaseItemBean_new purchaseItemBeanNew) {
//      super.jump2Quote(context, purchaseItemBeanNew);
        purchaseItemBeanNew.pid1 = getItemId();
        purchaseItemBeanNew.pid2 = getItemId();
        DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew);
    }

    protected void initListView(ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {


        listView.setAdapter(new GlobBaseAdapter<SellerQuoteJsonBean>(context, purchaseItemBeanNew.sellerQuoteListJson, R.layout.item_purchase_second) {

            public View getView(int position, View convertView, ViewGroup parent) {
                if (data.get(position).status.equals("choosing")) {

                    D.i("=-===========选标中===布局========");
                    return super.getView(position, convertView, parent);
                } else {
//                    ViewHolders myViewHolder = new ViewHolders(context, convertView, R.layout.item_purchase_first_cons, parent, position);
//                    setConverView(myViewHolder, data.get(position), position);

                    D.i("=-===========非选标中===布局========");
                    return super.getView(position, convertView, parent);
                }

            }

            @Override
            public void setConverView(ViewHolders myViewHolder, SellerQuoteJsonBean jsonBean, int position) {
                D.e("=====setConverView======str=============" + jsonBean.toString());

                /*价格*/
                TextView tv = myViewHolder.getView(R.id.tv_quote_item_price);
                tv.setText("¥" + jsonBean.price);

                /*报价时间*/
                TextView time = myViewHolder.getView(R.id.tv_quote_item_time);
                time.setText(jsonBean.quoteDateStr);

               /*预估到货价*/
                TextView pre_price = myViewHolder.getView(R.id.tv_quote_item_pre_price);
                pre_price.setText(jsonBean.prePrice);

                 /*可供数量*/
                TextView count = myViewHolder.getView(R.id.tv_quote_item_count);
                count.setText(jsonBean.count + "");

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
                remark.setText(jsonBean.remarks);


                      /*苗木图片*/
                SuperTextView photo_num = myViewHolder.getView(R.id.tv_quote_item_photo_num);
                photo_num.setText("有" + strFilter("1") + "张图片");//有多少张图片
                PurchaseDetailActivity.setImgCounts((Activity) context, photo_num, jsonBean.imagesJson);

//                textView35  苗源地


                TextView city = myViewHolder.getView(R.id.tv_quote_item_cityName);
                city.setText(FUtil.$_zero(jsonBean.cityName));


                TextView textView42 = myViewHolder.getView(R.id.tv_quote_item_declare);
                textView42.setText(FUtil.$_zero(jsonBean.remarks));

                TextView state = myViewHolder.getView(R.id.tv_show_is_quote);

//                StringFormatUtil formatUtil = new StringFormatUtil(context, "当前报价状态：" + getStateName(jsonBean.status), getStateName(jsonBean.status), ContextCompat.getColor(context, R.color.orange)).fillColor();
                state.setText(getStateName(jsonBean.status));

                if (jsonBean.status.equals("choosing")) {
                    //选中标   ---   全部显示

                } else if (jsonBean.status.equals("preQuote")) {
                    // 隐藏不需要的东西
                    ((ViewGroup) time.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
                    photo_num.setVisibility(View.GONE);
                    TextView abc = myViewHolder.getView(R.id.abc);
                    abc.setVisibility(View.GONE);
                    abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
                    abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));
                    myViewHolder.getView(R.id.tv_delete_item).setVisibility(View.GONE);
                    myViewHolder.getView(R.id.tv_change_item).setVisibility(View.GONE);
                }  /**
                 * 已中标，条件：合格并且已被采用
                 */

                else if (jsonBean.status.equals("used")) {
                    //已中标

                    state.setTextColor(ContextCompat.getColor(context, R.color.main_color));

                    myViewHolder.getView(R.id.tv_delete_item).setVisibility(View.GONE);
                    myViewHolder.getView(R.id.tv_change_item).setVisibility(View.GONE);
                } else {
                    // 隐藏不需要的东西
                    ((ViewGroup) time.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) count.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
                    ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
                    photo_num.setVisibility(View.GONE);
                    TextView abc = myViewHolder.getView(R.id.abc);
                    abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
                    abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));



                }




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
                myViewHolder.getView(tv_change_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtil.showLongToast("马上报价");
//                      purchaseItemBeanNew.sellerQuoteListJson.set(position, null);
//                      notifyDataSetChanged();
//                        DialogActivity.start((Activity) context, purchaseItemBeanNew, jsonBean);


                        purchaseItemBeanNew.pid1 = StorePurchaseListAdapter_new_second.this.getItemId();
                        purchaseItemBeanNew.pid2 = StorePurchaseListAdapter_new_second.this.getItemId();

                        DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew, jsonBean);


                    }
                });


            }
        });

    }


}


