package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.Ui.DialogActivityPackage;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity.strFilter;

/**
 * 整包 报价 适配器  适配器
 */
@SuppressLint("ResourceAsColor")
public abstract class StorePurchaseListAdapter_new_package extends StorePurchaseListAdapter_new_along_second {


    public StorePurchaseListAdapter_new_package(Context context, List<PurchaseItemBean_new> data, int layoutId) {
        super(context, data, layoutId);
    }

//    @Override
//    protected void initListView(ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {
//        super.initListView(listView, context, purchaseItemBeanNew);
//    }


//    @Override
//    public int getItemViewType(int position) {
//        return 2;
//    }


    @Override
    public void setConverView(ViewHolders myViewHolder, PurchaseItemBean_new purchaseItemBeanNew, int position) {
        super.setConverView(myViewHolder, purchaseItemBeanNew, position);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getDatas().get(position).id.equals("-1")) {
//            ToastUtil.showLongToast("第" + position + "个数据是分割线");

//            ToastUtil.showLongToast("-1");
            Log.i("getView", "以下为一轮未中标或未报价: ");

            TextView textView = new TextView(context);
            configureTextView(textView);
            textView.setText("--------- 以下为一轮未中标或未报价 ---------");

            ViewHolders myViewHolder = new ViewHolders(context, convertView, textView, parent, position);
            setConverView(myViewHolder, data.get(position), position);
            return myViewHolder.getConvertView();
        } else {
            Log.i("getView", "other: ");
            return super.getView(position, convertView, parent);
        }


    }


    /**
     * Configures text view. Is called for the TEXT_VIEW_ITEM_RESOURCE views.
     *
     * @param view the text view to be configured
     */
    protected void configureTextView(TextView view) {
//        view.setTextColor();
        view.setGravity(Gravity.CENTER);
        view.setTextSize(14);
        view.setEllipsize(TextUtils.TruncateAt.END);
        view.setLines(1);
        view.setHeight(MyApplication.dp2px(context, 30));
//        view.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_bg_ed));
        view.setBackgroundColor(Color.parseColor("#EFEFEF"));
//        view.setCompoundDrawablePadding(20);
        view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }


    @Override
    protected void jump2Quote(Activity context, PurchaseItemBean_new purchaseItemBeanNew, ListView listView) {
//      super.jump2Quote(context, purchaseItemBeanNew);
//        purchaseItemBeanNew.pid1 = getItemId();
//        purchaseItemBeanNew.pid2 = getItemId();
        DialogActivityPackage.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew);
        ToastUtil.showLongToast("整包报价");
//        processListView(listView);
        this.listview = listView;

    }

    private ListView listview;

    public void processListView(ListView ll,SellerQuoteJsonBean jsonBean) {
//        if (listview.getFooterViewsCount() > 0) {
//            listview.removeFooterView(listFootViews.get(0));
//            listFootViews.remove(0);
//            return;
//        }

        if (listview == null) return;


        int viewRootId = R.layout.list_item_store_purchase;

        TextView textView = new TextView(context);
        configureTextView(textView);
        textView.setText("--------- 报价成功，，，，，\n -----     红车的报价   \n      价格 200   \n  苗源地址  福建厦门  \n   报价说名   因为 少时诵诗书    \n   苗木图片    10   ----");
        View view = LayoutInflater.from(context).inflate(R.layout.item_purchase_second, null);


//        SellerQuoteJsonBean jsonBean = mokeBean();
        initFootView(view, jsonBean, textView);
        listview.addFooterView(view);
        listFootViews.add(view);

    }

    protected SellerQuoteJsonBean mokeBean() {
        SellerQuoteJsonBean sellerQuoteJsonBean = new SellerQuoteJsonBean();


        sellerQuoteJsonBean.id = "95566496cd934b1da84f986db30e6a30";
        sellerQuoteJsonBean.remarks = "remarks";
        sellerQuoteJsonBean.createBy = "createBy";
        sellerQuoteJsonBean.createDate = "createDate";
        sellerQuoteJsonBean.cityCode = "cityCode";
        sellerQuoteJsonBean.cityName = "cityName";
        sellerQuoteJsonBean.prCode = "prCode";
        sellerQuoteJsonBean.ciCode = "ciCode";
        sellerQuoteJsonBean.firstSeedlingTypeId = "firstSeedlingTypeId";
        sellerQuoteJsonBean.dbh = 12;
        sellerQuoteJsonBean.height = 12;
        sellerQuoteJsonBean.crown = 12;
        sellerQuoteJsonBean.plantType = "plantType";
        sellerQuoteJsonBean.plantTypeName = "plantTypeName";
        sellerQuoteJsonBean.paramsList = new ArrayList<>();
        sellerQuoteJsonBean.specList = new ArrayList<>();
        sellerQuoteJsonBean.specText = "specText";
        sellerQuoteJsonBean.purchaseId = "purchaseId";
        sellerQuoteJsonBean.purchaseItemId = "purchaseItemId";
        sellerQuoteJsonBean.sellerId = "sellerId";
        sellerQuoteJsonBean.price = "999";
        sellerQuoteJsonBean.sellerName = "sellerName";
        sellerQuoteJsonBean.sellerPhone = "17074990702";
        sellerQuoteJsonBean.purchaseItemStatus = "choosing";


        return sellerQuoteJsonBean;
    }


    private void initFootView(View view, SellerQuoteJsonBean jsonBean, TextView textView) {

//        ViewGroup viewGroup = ((ViewGroup) view);
//        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
//        marginLayoutParams.setMargins(50,50,50,0);
//
//        viewGroup.setLayoutParams(marginLayoutParams);
//        setViewMargin(view,false,80,80,80,0);

//        view.setPadding(80,80,80,0);


//        TextView bj = (TextView) view.findViewById(R.id.tv_caozuo01);
//        bj.setText("编辑");
//        bj.setOnClickListener(v -> ToastUtil.showLongToast("编辑"));
//
        ImageView wtj = (ImageView) view.findViewById(R.id.iv_status);
        wtj.setImageResource(R.drawable.weitijiao_gray);
        wtj.setVisibility(View.VISIBLE);
//

        LinearLayout content = (LinearLayout) view.findViewById(R.id.content);
        setViewMargin(content, false, 40, 40, 25, 0);


        textView.setText("红车的报价");
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.divider_color_bg));
        content.addView(textView, 0);

        content.setBackgroundColor(Color.WHITE);

//        /*价格*/
//        TextView tv_01 = (TextView) view.findViewById(R.id.tv_01);
//        tv_01.setText("价格: ￥200");


        D.e("=====setConverView======str=============" + jsonBean.toString());

                /*价格*/
        TextView tv = (TextView) view.findViewById(R.id.tv_quote_item_price);
        tv.setText("¥" + jsonBean.price);

                /*报价时间*/
        TextView time = (TextView) view.findViewById(R.id.tv_quote_item_time);
        time.setText(jsonBean.quoteDateStr);

               /*预估到货价*/
        TextView pre_price = (TextView) view.findViewById(R.id.tv_quote_item_pre_price);
        pre_price.setText(FUtil.$_zero(jsonBean.prePrice));

                 /*可供数量*/
        TextView count = (TextView) view.findViewById(R.id.tv_quote_item_count);
        count.setText(FUtil.$_zero(jsonBean.count + ""));

                     /*品种规格*/
        TextView specText = (TextView) view.findViewById(R.id.tv_quote_item_specText);
        specText.setText(jsonBean.specText);

                     /*品种规格*/
        TextView cityName = (TextView) view.findViewById(R.id.tv_quote_item_cityName);
        cityName.setText(jsonBean.cityName);

                   /*种植类型*/
        TextView plantTypeName = (TextView) view.findViewById(R.id.tv_quote_item_plantTypeName);
        plantTypeName.setText(jsonBean.plantTypeName);

                    /*报价说明*/
        TextView remark = (TextView) view.findViewById(R.id.tv_quote_item_declare);
        remark.setText(FUtil.$_zero(jsonBean.remarks));


                      /*苗木图片*/
        SuperTextView photo_num = (SuperTextView) view.findViewById(R.id.tv_quote_item_photo_num);
        photo_num.setText("有" + strFilter("1") + "张图片");//有多少张图片
        PurchaseDetailActivity.setImgCounts((Activity) context, photo_num, jsonBean.imagesJson);

//                textView35  苗源地


        TextView city = (TextView) view.findViewById(R.id.tv_quote_item_cityName);
        city.setText(FUtil.$_zero(jsonBean.cityName));


        TextView textView42 = (TextView) view.findViewById(R.id.tv_quote_item_declare);
        textView42.setText(FUtil.$_zero(jsonBean.remarks));

        TextView state = (TextView) view.findViewById(R.id.tv_show_is_quote);

//                StringFormatUtil formatUtil = new StringFormatUtil(context, "当前报价状态：" + getStateName(jsonBean.status), getStateName(jsonBean.status), ContextCompat.getColor(context, R.color.orange)).fillColor();
        state.setText(getStateName(jsonBean.status));


        if ("choosing".equals(jsonBean.status)) {
            //选中标   ---   全部显示

        } else if ("preQuote".equals(jsonBean.status)) {
            // 隐藏不需要的东西
            ((ViewGroup) time.getParent()).setVisibility(View.GONE);
            ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
            ((ViewGroup) count.getParent()).setVisibility(View.GONE);
            ((ViewGroup) count.getParent()).setVisibility(View.GONE);
            ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
            ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
            photo_num.setVisibility(View.GONE);
            TextView abc = (TextView) view.findViewById(R.id.abc);
            abc.setVisibility(View.GONE);
            abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
            abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));
            view.findViewById(R.id.tv_delete_item).setVisibility(View.GONE);
            view.findViewById(R.id.tv_change_item).setVisibility(View.GONE);
        }  /**
         * 已中标，条件：合格并且已被采用
         */

        else if ("used".equals(jsonBean.status)) {
            //已中标

            state.setTextColor(ContextCompat.getColor(context, R.color.main_color));

            view.findViewById(R.id.tv_delete_item).setVisibility(View.GONE);
            view.findViewById(R.id.tv_change_item).setVisibility(View.GONE);
        } else {
            // 隐藏不需要的东西
            ((ViewGroup) time.getParent()).setVisibility(View.GONE);
            ((ViewGroup) pre_price.getParent()).setVisibility(View.GONE);
            ((ViewGroup) count.getParent()).setVisibility(View.GONE);
            ((ViewGroup) count.getParent()).setVisibility(View.GONE);
            ((ViewGroup) specText.getParent()).setVisibility(View.GONE);
            ((ViewGroup) plantTypeName.getParent()).setVisibility(View.GONE);
            photo_num.setVisibility(View.GONE);
            TextView abc = (TextView) view.findViewById(R.id.abc);
            abc.setText("第一轮报价预中标，点击编辑按钮，补充报价信息");
            abc.setTextColor(ContextCompat.getColor(context, R.color.price_orige));
        }





                /*删除*/
        view.findViewById(R.id.tv_delete_item).setVisibility(View.GONE);

                /*编辑*/
        view.findViewById(R.id.tv_change_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                      ToastUtil.showLongToast("马上报价");
//                      purchaseItemBeanNew.sellerQuoteListJson.set(position, null);
//                      notifyDataSetChanged();
//                        DialogActivity.start((Activity) context, purchaseItemBeanNew, jsonBean);


//                        purchaseItemBeanNew.pid1 = getItemId();
//                        purchaseItemBeanNew.pid2 = getItemId();


                ToastUtil.showLongToast("编辑");
//                DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id, purchaseItemBeanNew, jsonBean);


            }
        });


    }

    private ArrayList<View> listFootViews = new ArrayList<>();


    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
//        if (isDp) {
//            leftPx = getPxFromDpi(left);
//            rightPx = getPxFromDpi(right);
//            topPx = getPxFromDpi(top);
//            bottomPx = getPxFromDpi(bottom);
//        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }
}


