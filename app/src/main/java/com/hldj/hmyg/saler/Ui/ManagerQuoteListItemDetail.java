package com.hldj.hmyg.saler.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseJsonBean;
import com.hldj.hmyg.buyer.Ui.WebViewDialogFragment;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.bean.ManagerQuoteItemDetailGsonBean;
import com.hldj.hmyg.saler.bean.PurchaseItemJsonBean;
import com.hldj.hmyg.saler.bean.UsedQuoteListBean;
import com.hldj.hmyg.saler.purchase.StoreDeteilDialog;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


/**
 * Created by Administrator on 2017/5/3.
 */

public class ManagerQuoteListItemDetail extends NeedSwipeBackActivity {


    private SaveSeedingGsonBean.DataBean.SeedlingBean item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.manager_quote_list_detail);
        InitHolder();


        item = (SaveSeedingGsonBean.DataBean.SeedlingBean) getIntent().getExtras().get("item");

        if (item == null) {
            ToastUtil.showShortToast("没有东西");
            return;
        } else {

            initItem(item);

            WebViewDialogFragment.newInstance(item.purchaseJson.quoteDesc).show(getSupportFragmentManager(), ManagerQuoteListItemDetail.class.getName());//弹窗显示

            initPurcs(item.purchaseJson);
            initPurcsItem(item.purchaseItemJsonBean);

            requestDetail();
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            finish();
        });

    }

    private void requestDetail() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", item.getId());
        params.put("userId", MyApplication.getUserBean().id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/detail", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {

                        ManagerQuoteItemDetailGsonBean gsonBean = GsonUtil.formateJson2Bean(json, ManagerQuoteItemDetailGsonBean.class);
                        D.e("========requestDetail=========" + json);
                        if (gsonBean.data.usedQuoteList.size() != 0) {
                            initBottomItems(gsonBean.data.usedQuoteList);
                        }


                        super.onSuccess(json);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                });

//        admin/quote/detail
    }

    private void initBottomItems(List<UsedQuoteListBean> usedQuoteList) {

        CoreRecyclerView recyclerView = (CoreRecyclerView) findViewById(R.id.recycle_detail_bottom);
        recyclerView.initView(this).init(new BaseQuickAdapter<UsedQuoteListBean, BaseViewHolder>(R.layout.item_manager_bottom) {
            @Override
            protected void convert(BaseViewHolder helper, UsedQuoteListBean item) {
                D.e("==========item=============" + item.toString());
                helper.setText(R.id.tv_recycle_detail_bottom, strFilter("价格：￥" + item.price + ""));
            }
        });

        recyclerView.getAdapter().addData(usedQuoteList);


    }


    public void setStatus(TextView textView, String status) {

        if (TextUtils.isEmpty(status)) {
        } else if (status.equals("unused")) {
            textView.setText("未中标");
            textView.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.red));
        } else if (status.equals("used")) {
            textView.setText("已中标");
            textView.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.main_color));
        } else {

        }

    }

    private void initPurcs(PurchaseJsonBean purchaseItemJsonBean) {

        TextView textView = (TextView) findViewById(R.id.tv_quote_item_price);
        textView.setText(strFilter(item.price));

        TextView tv_quote_item_plantTypeName = (TextView) findViewById(R.id.tv_quote_item_plantTypeName);
        tv_quote_item_plantTypeName.setText(strFilter(item.getPlantTypeName()));

        TextView tv_quote_item_declare = (TextView) findViewById(R.id.tv_quote_item_declare);
        tv_quote_item_declare.setText(strFilter(item.getRemarks()));//报价说明


        TextView tv_show_is_quote = (TextView) findViewById(R.id.tv_show_is_quote);
        setStatus(tv_show_is_quote, item.getStatus());//中标状态

        TextView tv_quote_item_photo_num = (TextView) findViewById(R.id.tv_quote_item_photo_num);
        tv_quote_item_photo_num.setText(strFilter("有" + item.getImagesJson().size() + "张图片"));//有多少张图片
        tv_quote_item_photo_num.setOnClickListener(v -> {

            if (item.getImagesJson().size() != 0) {
                //穿list pic 集合到新的activity 显示 所有的图片
                GalleryImageActivity.startGalleryImageActivity(ManagerQuoteListItemDetail.this, 0, getPicList(item.getImagesJson()));
            } else {
                D.e("==没有图片===");
            }

        });


        ((ViewGroup) findViewById(R.id.tv_quote_item_specText).getParent()).setVisibility(View.GONE);
        ((ViewGroup) findViewById(R.id.tv_quote_item_cityName).getParent()).setVisibility(View.GONE);
        findViewById(R.id.tv_delete_item).setVisibility(View.GONE);//删除按钮 隐藏

//        helper.setText(R.id.tv_quote_item_price, strFilter(item.price + ""));//价格
//        helper.setText(R.id.tv_quote_item_plantTypeName, strFilter(item.plantTypeName));//种植类型
//        helper.setText(R.id.tv_quote_item_declare, strFilter(item.remarks));//种植类型
//
//
//        if (direce) {//代购
//            helper.setText(R.id.tv_quote_item_specText, strFilter(item.specText));//要求规格
//            helper.setText(R.id.tv_quote_item_cityName, strFilter(item.cityName));//苗源地址
//        } else {//直购  参数比较少，需要隐藏部分
//            helper.setParentVisible(R.id.tv_quote_item_specText, false);
//            helper.setParentVisible(R.id.tv_quote_item_cityName, false);
//        }
//
//        helper.setText(R.id.tv_quote_item_photo_num, strFilter("有" + item.imagesJson.size() + "张图片"));//有多少张图片
//        if (item.imagesJson.size() != 0) {
//            helper.addOnClickListener(R.id.tv_quote_item_photo_num, v -> {
//                //穿list pic 集合到新的activity 显示 所有的图片
//                GalleryImageActivity.startGalleryImageActivity(PurchaseDetailActivity.this, 0, getPicList(item.imagesJson));
//            });
//        }


    }

    private ArrayList<Pic> getPicList(List<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean> imagesJson) {
        ArrayList pics = new ArrayList<Pic>();
        for (int i = 0; i < imagesJson.size(); i++) {
            pics.add(new Pic(imagesJson.get(i).getId(), false, imagesJson.get(i).getOssMediumImagePath(), i));
        }
        return pics;
    }


    private void initPurcsItem(PurchaseItemJsonBean purchaseJson) {
    }

    private void initItem(SaveSeedingGsonBean.DataBean.SeedlingBean item) {
        //头部与底部是一样的    这里初始化头部
        getViewHolder_pur().tv_purchase_name.setText(strFilter("[" + item.purchaseItemJsonBean.firstTypeName + "]" + item.purchaseItemJsonBean.name));
        getViewHolder_pur().tv_purchase_size.setText(strFilter(item.getSpecText()));
        getViewHolder_pur().tv_purchase_type.setText(strFilter(item.getPlantTypeName()));
        getViewHolder_pur().tv_quote_num.setText(strFilter(item.getCount() + item.getUnitTypeName()));

        getViewHolder_pur().tv_guige_left.setText("苗木规格:");

        getViewHolder_pur().tv_purchase_address.setText(strFilter(item.purchaseJson.cityName));
        getViewHolder_pur().tv_purchase_close_date.setText(strFilter(item.purchaseJson.closeDate));
        String str = strFilter(item.purchaseJson.quoteDesc);//报价信息 弹窗显示
        getViewHolder_pur().tv_purchase_price_sug.setOnClickListener(v -> {
            showSug2DialogLeft(item);// TODO: 2017/4/26   增加弹窗<-


        });
        getViewHolder_pur().tv_purchase_store_detail.setOnClickListener(v -> {
            // TODO: 2017/4/26   增加弹窗->
            showSug2DialogRight(str);
        });

        getViewHolder_pur().tv_purchase_address.setText(strFilter(item.purchaseJson.cityName));


    }

    /**
     * 弹窗显示
     *
     * @param
     */
    public static void showSug2DialogLeft(SaveSeedingGsonBean.DataBean.SeedlingBean item) {
        /**
         * buyerDatas.clear();
         buyerDatas.add("采购商家：" + companyName);
         buyerDatas.add("所在地区：" + address);
         buyerDatas.add("采购数量：" + count);
         buyerDatas.add("已有报价：" + quoteCountJson + "条");
         */
        ArrayList<String> buyerDatas = new ArrayList<String>();
        buyerDatas.clear();
        buyerDatas.add("采购商家：" + item.purchaseJson.buyer.displayName);
        buyerDatas.add("所在地区：" + item.purchaseJson.cityName);
        buyerDatas.add("采购数量：" + item.purchaseItemJsonBean.count);
        buyerDatas.add("已有报价：" + item.purchaseItemJsonBean.quoteCountJson + "条");

        StoreDeteilDialog.Builder builder = new StoreDeteilDialog.Builder(MyApplication.getInstance());
        builder.setTitle("商家信息");
        builder.setPrice("");
        builder.setCount("");
        builder.setAccountName("");
        builder.setAccountBank("");
        builder.setAccountNum("");
        builder.setData(buyerDatas);
        builder.setPositiveButton("确定",
                (dialog, which) -> {
                    dialog.dismiss();
                    // 设置你的操作事项
                });

        builder.setNegativeButton("取消",
                (dialog, which) -> dialog.dismiss());

        builder.create().show();

    }


    /**
     * 弹窗显示
     *
     * @param str
     */
    private void showSug2DialogRight(String str) {
        WebViewDialogFragment.newInstance(str).show(getSupportFragmentManager(), this.getClass().getName());
    }

    public static void start2Activity(Context context, SaveSeedingGsonBean.DataBean.SeedlingBean item) {

        Intent intent = new Intent(context, ManagerQuoteListItemDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    private ViewHolder viewHolder_pur;

    public ViewHolder getViewHolder_pur() {
        return viewHolder_pur;
    }


    public void InitHolder() {
        viewHolder_pur = new ViewHolder(this);
    }


    protected class ViewHolder {

        public TextView tv_title;
        public ImageView btn_back;
        public SuperTextView tv_purchase_address;
        public SuperTextView tv_purchase_close_date;
        public Button tv_purchase_store_detail;
        public Button tv_purchase_price_sug;
        public TextView tv_purchase_city_name;

        public TextView tv_purchase_name;
        public TextView tv_purchase_size;
        public TextView tv_purchase_type;
        public TextView tv_quote_num;
        public TextView tv_guige_left;

        public ViewHolder(Activity rootView) {
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_guige_left = (TextView) rootView.findViewById(R.id.tv_guige_left);
            this.btn_back = (ImageView) rootView.findViewById(R.id.btn_back);
            this.tv_purchase_name = (TextView) rootView.findViewById(R.id.tv_quote_name);
            this.tv_purchase_size = (TextView) rootView.findViewById(R.id.tv_quote_size);
            this.tv_purchase_type = (TextView) rootView.findViewById(R.id.tv_quote_type);
            this.tv_quote_num = (TextView) rootView.findViewById(R.id.tv_quote_num);
            this.tv_purchase_address = (SuperTextView) rootView.findViewById(R.id.tv_quote_address);
            this.tv_purchase_close_date = (SuperTextView) rootView.findViewById(R.id.tv_quote_close_date);
            this.tv_purchase_store_detail = (Button) rootView.findViewById(R.id.tv_quote_store_detail);//采购商家信息
            this.tv_purchase_price_sug = (Button) rootView.findViewById(R.id.tv_quote_price_sug);//报价要求
            this.tv_purchase_city_name = (TextView) rootView.findViewById(R.id.tv_purchase_city_name);


        }

    }

}
