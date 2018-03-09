package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.QuoteStatus;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.Ui.DialogActivity;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.R.id.textView41;
import static com.hldj.hmyg.R.id.tv_caozuo01;

/**
 * 一轮报价  适配器
 */
@SuppressLint("ResourceAsColor")
public abstract class StorePurchaseListAdapter_new extends GlobBaseAdapter<PurchaseItemBean_new> {
    private static final String TAG = "StorePurchaseL";

//    private ArrayList<HashMap<String, Object>> data = null;

    public abstract String setCityName();

    public abstract Boolean isExpired();

    public abstract String getItemId();

    public abstract boolean isNeedPreQuote();


    public StorePurchaseListAdapter_new(Context context, List<PurchaseItemBean_new> data, int layoutId) {
        super(context, data, layoutId);
    }


    public static boolean isShow = true;

    @Override
    public void setConverView(ViewHolders myViewHolder, PurchaseItemBean_new purchaseItemBeanNew, int position) {


        if (purchaseItemBeanNew.id.equals("-1")) return;
        int viewRootId = R.layout.list_item_store_purchase;

        Log.e(TAG, "setConverView: " + position);
        myViewHolder.getView(R.id.iv_img2).setVisibility(purchaseItemBeanNew.isQuoted ? View.VISIBLE : View.GONE);

        if (isExpired()) {
            myViewHolder.getView(R.id.iv_img2).setVisibility(View.GONE);
        }


        ((TextView) myViewHolder.getView(R.id.tv_01)).setText(purchaseItemBeanNew.name);
        ((TextView) myViewHolder.getView(R.id.tv_pos)).setText((position + 1) + "");
        ((TextView) myViewHolder.getView(R.id.tv_pos)).setVisibility(View.VISIBLE);


        /* 参考图片  按钮  */


        SuperTextView tv_cankao = myViewHolder.getView(R.id.show_cankao);
        if (purchaseItemBeanNew.imagesJson != null && purchaseItemBeanNew.imagesJson.size() > 0) {
            tv_cankao.setVisibility(View.VISIBLE);
            PurchaseDetailActivity.setImgCounts((Activity) context, tv_cankao, purchaseItemBeanNew.imagesJson);
            tv_cankao.setText("参考图片");
        } else {
            tv_cankao.setVisibility(View.GONE);
            tv_cankao.setOnClickListener(null);
        }

//        ((TextView) myViewHolder.getView(R.id.show_cankao)).setOnClickListener(v -> {
//            ToastUtil.showLongToast("参考图片");
//
//            ToastUtil.showLongToast(purchaseItemBeanNew.imagesJson + "");
//
//            D.i("==参考图片===");
//        });





        /*种植类型*/
        ((TextView) myViewHolder.getView(R.id.tv_021)).setText("种植类型: " + purchaseItemBeanNew.plantTypeArrayNames);

        ((TextView) myViewHolder.getView(R.id.tv_ac)).setText(purchaseItemBeanNew.count + purchaseItemBeanNew.unitTypeName);

//        setCityName()
        ((TextView) myViewHolder.getView(R.id.tv_03)).setText(purchaseItemBeanNew.purchaseJson.cityName);
        myViewHolder.getView(R.id.tv_03).setVisibility(isShow ? View.VISIBLE : View.GONE);

        setSpaceAndRemark(myViewHolder.getView(R.id.tv_05), purchaseItemBeanNew.specText, purchaseItemBeanNew.remarks);

        TextView tv_10 = myViewHolder.getView(R.id.tv_10);
        Log.i(TAG, "setConverView: " + tv_10.isSelected());
        setTv10(tv_10, purchaseItemBeanNew, true);//点击查看   ↓

        if (isExpired()) {
            tv_10.setVisibility(View.GONE);
        }

        setTv_isloagin(myViewHolder.getView(tv_caozuo01), purchaseItemBeanNew);


        ListView listView = myViewHolder.getView(R.id.list);

//        List list = new ArrayList();
//        list.add("1");
//        list.add("1");
//        list.add("1");
        if (isExpired()) {
            purchaseItemBeanNew.sellerQuoteListJson = null;
        }

        initListView(myViewHolder, listView, context, purchaseItemBeanNew);


//      setTv_isloagin(myViewHolder.getView(tv_caozuo01), purchaseItemBeanNew);

        if (!purchaseItemBeanNew.editAble) {
            TextView tv_caozuo01 = myViewHolder.getView(R.id.tv_caozuo01);
            tv_caozuo01.setText("采购已关闭");
            tv_caozuo01.setTextColor(ContextCompat.getColor(context, R.color.orange));
            tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.trans_bg));
            return;
        }


        myViewHolder.getConvertView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.Userinfo.getBoolean("isLogin", false)) {
//                    if (purchaseItemBeanNew.status .equals("expired")) {
                    if (isExpired()) {
                        ToastUtil.showShortToast("采购已关闭");
                        return;
                    }
//                    PurchaseDetailActivity.start2Activity((Activity) context, purchaseItemBeanNew.id);//采购详情 界面
                    ((NeedSwipeBackActivity) context).showLoading();
                    new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
                        @Override
                        public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                            ((NeedSwipeBackActivity) context).hindLoading();
                            boolean canQuote = saveSeedingGsonBean.getData().canQuote;
                            if (!canQuote) {
                                ToastUtil.showShortToast("您没有报价权限");
                                PurchaseDeatilP.requestPer(((NeedSwipeBackActivity) context));
                            } else {
//                                purchaseItemBeanNew.pid1 = getItemId();
//                                purchaseItemBeanNew.pid2 = getItemId();
//                                DialogActivity.start((Activity) context, purchaseItemBeanNew);

                                jump2Quote((Activity) context, purchaseItemBeanNew, listView, myViewHolder);
//                                processListView(listView);

//                    DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id ,purchaseItemBeanNew);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            ToastUtil.showShortToast("您没有报价权限" + strMsg);
                            ((NeedSwipeBackActivity) context).hindLoading();
                        }
                    }).getDatas(purchaseItemBeanNew.id);//请求数据  进行排版


//                    //一次报价
//                    purchaseItemBeanNew.pid1 = getItemId();
//                    purchaseItemBeanNew.pid2 = getItemId();
//                    DialogActivity.start((Activity) context, purchaseItemBeanNew);
////                    DialogActivitySecond.start2Activity((Activity) context, purchaseItemBeanNew.id ,purchaseItemBeanNew);


                } else {
                    LoginActivity.start2Activity((Activity) context);
                }
            }
        });


    }

    protected void jump2Quote(Activity context, PurchaseItemBean_new purchaseItemBeanNew, ListView list, ViewHolders parentHolders) {

        purchaseItemBeanNew.pid1 = getItemId();
        purchaseItemBeanNew.pid2 = getItemId();
        DialogActivity.start((Activity) context, purchaseItemBeanNew);

    }

//    protected void processListView(ListView listview) {
//
//
//    }


    protected void initListView(ViewHolders parentHolders, ListView listView, Context context, PurchaseItemBean_new purchaseItemBeanNew) {

        listView.setAdapter(new GlobBaseAdapter<SellerQuoteJsonBean>(context, purchaseItemBeanNew.sellerQuoteListJson, R.layout.item_purchase_first_cons) {

//            @Override
//            public int getItemViewType(int position) {
//
//                return super.getItemViewType(position);
//            }


            //     ViewHolders myViewHolder = new ViewHolders(context, convertView, layoutId, parent, position);
//            setConverView(myViewHolder, data.get(position), position);
//        return myViewHolder.getConvertView();
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (data.get(position).status.equals("选标中"))
//                    return super.getView(position, convertView, parent);
//                else {
//                    ViewHolders myViewHolder = new ViewHolders(context, convertView, R.layout.item_purchase_first_cons, parent, position);
//                    setConverView(myViewHolder, data.get(position), position);
//                    return myViewHolder.getConvertView();
//                }
//
//            }

            @Override
            public void setConverView(ViewHolders myViewHolder, SellerQuoteJsonBean jsonBean, int position) {

//                if (!((MeasureListView) listView).isOnMeasure) {
//                    return;
//                }

                D.e("=====setConverView======str=============" + jsonBean.toString());

                TextView tv = myViewHolder.getView(R.id.textView32);
                tv.setText("¥" + jsonBean.price);

//                textView35  苗源地


                TextView city = myViewHolder.getView(R.id.textView35);
                city.setText(FUtil.$_zero(jsonBean.cityName));


                TextView textView42 = myViewHolder.getView(R.id.textView42);
                textView42.setText(FUtil.$_zero(jsonBean.remarks));

                TextView state = myViewHolder.getView(R.id.state);

//                StringFormatUtil formatUtil = new StringFormatUtil(context, "当前报价状态：" + getStateName(jsonBean.status), getStateName(jsonBean.status), ContextCompat.getColor(context, R.color.orange)).fillColor();
                state.setText(getStateName(jsonBean.status));
                //选标中 ---- 正常显示二次报价




                /*删除*/
                myViewHolder.getView(R.id.textView40).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        doDelete(v, purchaseItemBeanNew, position, jsonBean);


//                        ToastUtil.showLongToast("delete");
//                        purchaseItemBeanNew.sellerQuoteListJson.remove(position);
//                        notifyDataSetChanged();
                    }
                });

                /*编辑*/
                myViewHolder.getView(textView41).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtil.showLongToast("马上报价");
//                      purchaseItemBeanNew.sellerQuoteListJson.set(position, null);
//                      notifyDataSetChanged();
                        DialogActivity.start((Activity) context, purchaseItemBeanNew, jsonBean);

                    }
                });


            }
        });

    }

    protected void doDelete(View v, PurchaseItemBean_new purchaseItemBeanNew, int position, SellerQuoteJsonBean jsonBean) {

        new AlertDialog(context)
                .builder()
                .setTitle("确定删除")
                .setPositiveButton("确定", v1 -> {
                    new PurchaseDeatilP(new ResultCallBack<PurchaseItemBean_new>() {
                        @Override
                        public void onSuccess(PurchaseItemBean_new itemBean_new) {
                            ToastUtil.showShortToast("删除成功");
//                            ToastUtil.showLongToast("delete");
//                            data.set(position, itemBean_new);
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).id.equals(itemBean_new.id)) {
                                    data.set(i, itemBean_new);
                                }
                            }
//                          purchaseItemBeanNew = itemBean_new ;
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            ToastUtil.showShortToast("删除失败" + strMsg);
                        }
                    })
                            .quoteDdel(jsonBean.id);
                })
                .setNegativeButton("取消", v2 -> {
                }).show();
        //删除接口


    }

    private void setTv_isloagin(TextView tv_caozuo01, PurchaseItemBean_new purchaseItemBeanNew) {
//        if (!purchaseItemBeanNew.status .equals("expired")) {
        if (!isExpired())//false 未过期
        {

            tv_caozuo01.setText("新增报价");
            tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.green_btn_selector));
        } else

        {//已过期
            tv_caozuo01.setText("采购已关闭");
            tv_caozuo01.setTextColor(ContextCompat.getColor(context, R.color.orange));
            tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.trans_bg));
        }

        /*  第三种状态出现   变成灰色   */

//        tv_caozuo01.setText("已填写");
//        tv_caozuo01.setTextColor(ContextCompat.getColor(context, R.color.text_login_type));
//        tv_caozuo01.setBackground(ContextCompat.getDrawable(context, R.drawable.gray_out_white_in__bg));


    }

    //, boolean isOpen
    public void setTv10(TextView tv_10, PurchaseItemBean_new purchaseItemBeanNew, boolean isOpen) {

        tv_10.setVisibility(View.VISIBLE);
        if (isOpen) {
//            StringFormatUtil fillColor = new StringFormatUtil(context, "点击查看"
//                    + purchaseItemBeanNew.quoteCountJson + "条报价", purchaseItemBeanNew.quoteCountJson + "",
//                    R.color.red).fillColor();
//            tv_10.setText(fillColor.getResult());
            tv_10.setText("查看历史报价");

//                tv_10.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
            tv_10.setSelected(false); //下 ↓
//                    }
//                },100);

        } else {
//            StringFormatUtil fillColor = new StringFormatUtil(context, "点击收起"
//                    + purchaseItemBeanNew.quoteCountJson + "条报价", purchaseItemBeanNew.quoteCountJson + "",
//                    R.color.red).fillColor();
//            tv_10.setText(fillColor.getResult());
            tv_10.setText("隐藏历史报价");
            tv_10.setSelected(true);
        }


//            if (purchaseItemBeanNew.quoteCountJson == 0) {
//                //跳转到报价列表详情
//                tv_10.setOnClickListener(v -> ToastUtil.showShortToast("暂无报价"));
//            } else {
//                //跳转到报价列表详情
//                tv_10.setOnClickListener(v -> QuoteListActivity_bak.start2Activity((Activity) context, purchaseItemBeanNew.id));
//            }

    }


    public static void setSpaceAndRemark(TextView tv_05, String specText, String remarks) {

        if (!TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + specText + "   " + remarks);
        } else if (!TextUtils.isEmpty(specText) && TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + specText);
        } else if (TextUtils.isEmpty(specText) && !TextUtils.isEmpty(specText)) {
            tv_05.setText("规格: " + remarks);
        } else {
            tv_05.setText("规格: - ");
        }
    }


    /*外面传进来的 地区名称*/
//    String cityName;

    /*赋值，是否判断是否过期*/
//    private boolean expired = false;


    public static String getStateName(String status) {
        if (status == null) {
            return "-";
        }
        if (status.equals(QuoteStatus.preQuote.getEnumValue())) {
            /* 预报价*/
            return QuoteStatus.preQuote.getEnumText();
        } else if (status.equals(QuoteStatus.preBid.getEnumValue())) {
              /* 预中标*/
            return QuoteStatus.preBid.getEnumText();
        } else if (status.equals(QuoteStatus.choosing.getEnumValue())) {
              /* 选标中*/
            return QuoteStatus.choosing.getEnumText();
        } else if (status.equals(QuoteStatus.used.getEnumValue())) {
              /* 已中标*/
            return QuoteStatus.used.getEnumText();
        } else if (status.equals(QuoteStatus.unused.getEnumValue())) {
              /* 未中标*/
            return QuoteStatus.unused.getEnumText();
        } else {
            return "-";
        }


    }

}


