package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.V.PurchaseDeatilV;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.purchase.StoreDeteilDialog;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.zzy.common.widget.MeasureGridView.context;

/**
 * 采购详情  基础类
 */

public abstract class PurchaseDetailActivityBase extends NeedSwipeBackActivity implements PurchaseDeatilV {

    public boolean mIsQuoted;//是否报过价
    public String mProjectType = "";//直购 代沟
    public String firstSeedlingTypeId = "";
    //direct直购
    //protocol代购

    public ItemBean item ;


    public static PurchaseDetailActivityBase instance;
    private String status = "";//状态 用于标记 已报价，已结束 未报价 等等状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView();
        //初始化 holder  将其设置为private 不让外部访问  避免编译器提示


        instance = this;
        //提供跳转方式的定义。。规范开发
        if (null == getIntent()) {
            ToastUtil.showShortToast("用 start2activity来条状本届面");
            return;
        }

        //后退
        findViewById(R.id.toolbar_left_icon).setOnClickListener(v -> {
            finish();
        });


        InitViews();
        InitHolder();

        /**
         * 请求接口数据 获取数据之后 initDatas()  调用
         */
//        getDatas();


    }

    public abstract void setContentView();

    /*处理没有权限事件*/
    public abstract void desposeNoPermission();


    /*供应商 是否同意协议*/
    public abstract void supplierProtocol();

    /**
     * 接口请求到数据后 调用
     *
     * @param saveSeedingGsonBean
     */
    public void initDatas(SaveSeedingGsonBean saveSeedingGsonBean) {
        boolean canQuote = saveSeedingGsonBean.getData().canQuote;
        boolean isOneQuote = saveSeedingGsonBean.getData().isOneQuote;

       /*没有权限*/
        if (!canQuote) {
            //申请权限
            desposeNoPermission();
//            new Handler().postDelayed(() -> {
//                finish();
//            }, 2000);

            return;
        }

        /*没有同意协议，跳转协议界面 h5*/
        if (!MyApplication.getUserBean().supplierIsAgree) {
            supplierProtocol();
            return;
        }


        /**
         * obj[@"data"][@"typeList"]   //分类的list
         obj[@"data"][@"plantTypeList"]  //种植类型list
         obj[@"data"][@"item"]  //采购项
         obj[@"data"][@"canQuote"]  //能否报价
         */


        ItemBean item = saveSeedingGsonBean.getData().getItem();
        this.item = saveSeedingGsonBean.getData().getItem() ;


        this.setStatus(item.sellerQuoteJson == null ? "" : strFilter(item.sellerQuoteJson.status));

        initItem(item);
        mProjectType = item.purchaseJson.projectType;//直购 代购


        if (mProjectType.equals(ConstantParams.direct)) {//直购
            List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen = saveSeedingGsonBean.getData().getTypeList();
            initDirect(typeListBeen);

        } else { //代购
            List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen = saveSeedingGsonBean.getData().getTypeList();
            initProtocol(typeListBeen);
//          initAutoLayout(typeListBeen);
        }
        List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeListBeen = saveSeedingGsonBean.getData().getPlantTypeList();
        initAutoLayout2(plantTypeListBeen);
        onlyOneQuote(isOneQuote, mIsQuoted);

    }

    /**
     * public String cityCode = "4505";
     * public String price = "";
     * public String diameter = "";
     * public String dbh = "";
     * public String height = "";
     * public String crown = "";
     * public String offbarHeight = "";
     * public String length = "";
     * public String diameterType = "";
     * public String dbhType = "";
     * public String remarks = "";//备注
     * public String imageJson = "";//备注
     * <p>
     * public String purchaseId ="" ;//
     *
     * @param item
     */

    /**
     * 是否只能一次报价  --- 默认否，可多次报价
     *
     * @param isOneQuote
     */
    public abstract void onlyOneQuote(boolean isOneQuote, boolean isQuoted);

    //step 1
    public void initItem(ItemBean item) {
//        SaveSeedlingPresenter.initAutoLayout(, item);
//    direct直购
//    protocol代购

//        firstTypeName = "乔木"
//        name = "香樟"
//        this.item = item;

//        D.e("==count====="+item.sellerQuoteJson.count);

        //头部与底部是一样的    这里初始化头部
        StringFormatUtil fillColor = new StringFormatUtil(context, item.name + "  " + item.count + item.unitTypeName, item.count + item.unitTypeName, R.color.red).fillColor();
        getViewHolder_pur().tv_purchase_name.setText(strFilter(item.name));

//        头部与底部是一样的    这里初始化头部
//        getViewHolder_pur().tv_purchase_name.setText(strFilter( item.name));
        StorePurchaseListAdapter.setSpaceAndRemark(getViewHolder_pur().tv_purchase_size, item.specText, item.remarks);//苗木规格
        //种植类型
        getViewHolder_pur().tv_purchase_type.setText(strFilter(item.plantTypeArrayNames));

        getViewHolder_pur().tv_quote_num.setText(strFilter(item.count + item.unitTypeName));


        getViewHolder_pur().tv_purchase_address.setText(strFilter(item.purchaseJson.cityName));
        getViewHolder_pur().tv_purchase_close_date.setText(strFilter(item.purchaseJson.closeDate));
        String str = strFilter(item.purchaseJson.quoteDesc);//报价信息 弹窗显示
        getViewHolder_pur().tv_purchase_price_sug.setOnClickListener(v -> {
            // TODO: 2017/4/26   增加弹窗->
            showSug2DialogRight(str);
        });
        getViewHolder_pur().tv_purchase_store_detail.setOnClickListener(v -> {

            showSug2DialogLeft();// TODO: 2017/4/26   增加弹窗<-
        });

        getViewHolder_pur().tv_purchase_address.setText(strFilter(item.purchaseJson.cityName));

        firstSeedlingTypeId = item.firstSeedlingTypeId;
        D.e("==firstSeedlingTypeId==" + firstSeedlingTypeId);


    }


    //直购时调用
    protected abstract void initDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen);


    //代购时调用
    protected abstract void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen);


    /**
     * 弹窗显示
     *
     * @param
     */
    private void showSug2DialogLeft() {


//        if (item == null) {
//            ToastUtil.showShortToast("没有数据！");
//            return;
//        }
//        if (item.buyer == null) {
//            ToastUtil.showShortToast("没有数据！");
//            return;
//        }
        /**
         * buyerDatas.clear();
         buyerDatas.add("采购商家：" + companyName);
         buyerDatas.add("所在地区：" + address);
         buyerDatas.add("采购数量：" + count);
         buyerDatas.add("已有报价：" + quoteCountJson + "条");
         */


        ArrayList<String> buyerDatas = new ArrayList<String>();
        buyerDatas.clear();
        buyerDatas.add("采购商家：" + item.buyer.displayName);
        try {
            buyerDatas.add("所在地区：" + item.purchaseJson.ciCity.fullName);
        } catch (Exception e) {
            buyerDatas.add("所在地区：" + "-");
        }
        buyerDatas.add("采购数量：" + item.count);
        buyerDatas.add("已有报价：" + item.quoteCountJson + "条");

        WebViewDialogFragment2.StoreInfoBean storeInfoBean = new WebViewDialogFragment2.StoreInfoBean();
        storeInfoBean.sotreName = item.buyer.displayName;
        storeInfoBean.inTheCity = item.sellerQuoteJson == null ? "-" : item.sellerQuoteJson.cityName;
        storeInfoBean.phoneNum = item.buyer.displayPhone;
        storeInfoBean.itemNum = item.purchaseJson.num;
        storeInfoBean.priced = item.quoteCountJson + "";

        WebViewDialogFragment2 viewDialogFragment2 = WebViewDialogFragment2.newInstance(storeInfoBean);
        viewDialogFragment2.show(getSupportFragmentManager(), "hellow world");


//        viewDialogFragment2.setInfoBean(storeInfoBean);

        StoreDeteilDialog.Builder builder = new StoreDeteilDialog.Builder(
                PurchaseDetailActivityBase.this);
        builder.setTitle("商家信息");
        builder.setPrice("");
        builder.setCount("");
        builder.setAccountName("");
        builder.setAccountBank("");
        builder.setAccountNum("");
        builder.setData(buyerDatas);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        // 设置你的操作事项
                    }
                });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });

//        builder.create().show();

    }

    /**
     * 弹窗显示
     *
     * @param str
     */
    private void showSug2DialogRight(String str) {
        WebViewDialogFragment.newInstance(str).show(getSupportFragmentManager(), this.getClass().getName());
    }


    //step 1   2个界面不一样的实现
//    public abstract void initAutoLayout(List<SaveSeedingGsonBean.PurchaseItemBean_new.TypeListBean> typeListBeen);
//    {
//        getViewHolder_pur().ll_purc_auto_add.addView(new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "dbh", true)));
//
//        for (int i = 0; i < typeListBeen.size(); i++) {
//
//
//            if (firstSeedlingTypeId.equals(typeListBeen.get(i).getId())) {
//                for (int j = 0; j < typeListBeen.get(i).getParamsList().size(); j++) {
//                    PurchaseAutoAddLinearLayout.PlantBean plantBean = new PurchaseAutoAddLinearLayout.PlantBean(typeListBeen.get(i).getParamsList().get(j).getName(),
//                            typeListBeen.get(i).getParamsList().get(j).getValue(),
//                            typeListBeen.get(i).getParamsList().get(j).isRequired()
//                    );
//                    getViewHolder_pur().ll_purc_auto_add.addView(new PurchaseAutoAddLinearLayout(this).setData(plantBean));
//                }
//
//
//            }
//            // "name": "地径",
//            //"value": "diameter",
//            //"required": true
//        }
//    }

    //step 1  这一步是一样的
    private void initAutoLayout2(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> bean) {

        SaveSeedlingPresenter.initAutoLayout2(getViewHolder_pur().tfl_purchase_auto_add_plant, bean, -1, instance, (view, position, parent) -> {
            plantType = bean.get(position).getValue();//上传值
            return true;
        });

    }

    private String plantType = "";

    public String getPlantType() {

        return plantType;
    }


    private ViewHolder viewHolder_pur;

    public ViewHolder getViewHolder_pur() {
        return viewHolder_pur;
    }

    @Override
    public void InitHolder() {
        viewHolder_pur = new ViewHolder(this);
    }


    @Override
    public void InitOnclick() {

    }


    protected static final String GOOD_ID = "good_id";//本届面传过来的id


    public abstract void addPicUrls(ArrayList<Pic> resultPathList);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    public abstract View getBootomBiew();

    public abstract View getHeadView();


    protected class ViewHolder {

        //        public TextView tv_title;
        public ImageView btn_back;
        public SuperTextView tv_purchase_address;
        public SuperTextView tv_purchase_close_date;
        public Button tv_purchase_store_detail;
        public Button tv_purchase_price_sug;
        public TextView tv_purchase_city_name;
        public TagFlowLayout tfl_purchase_auto_add_plant;
        public TextView tv_purchase_add_pic;
        public TextView tv_purchase_remark;
        public TextView tv_purchase_commit;
        public TextView tv_purchase_name;
        public LinearLayout ll_purc_auto_add;
        public LinearLayout ll_mainView_bottom;//底部容器 根;
        public TextView tv_purchase_size;
        public TextView tv_quote_type;
        public TextView tv_purchase_type;
        public TextView tv_quote_num;
        public EditText et_purchase_remark;
        public TextView toolbar_title;

        public ViewHolder(Activity rootView) {
//            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);

            this.btn_back = (ImageView) rootView.findViewById(R.id.toolbar_left_icon);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.tv_purchase_name = (TextView) getHeadView().findViewById(R.id.tv_quote_name);
            this.tv_purchase_size = (TextView) getHeadView().findViewById(R.id.tv_quote_size);
            this.tv_purchase_type = (TextView) getHeadView().findViewById(R.id.tv_quote_type);
            this.tv_quote_num = (TextView) getHeadView().findViewById(R.id.tv_quote_num);
            this.tv_purchase_address = (SuperTextView) getHeadView().findViewById(R.id.tv_quote_address);
            this.tv_purchase_close_date = (SuperTextView) getHeadView().findViewById(R.id.tv_quote_close_date);
            this.tv_purchase_store_detail = (Button) getHeadView().findViewById(R.id.tv_quote_store_detail);//采购商家信息
            this.tv_purchase_price_sug = (Button) getHeadView().findViewById(R.id.tv_quote_price_sug);//报价要求
            this.tv_purchase_city_name = (TextView) getBootomBiew().findViewById(R.id.tv_purchase_city_name);
            this.tfl_purchase_auto_add_plant = (TagFlowLayout) getBootomBiew().findViewById(R.id.tfl_purchase_auto_add_plant);
            this.tv_purchase_add_pic = (TextView) getBootomBiew().findViewById(R.id.tv_purchase_add_pic);
            this.tv_purchase_remark = (TextView) getBootomBiew().findViewById(R.id.tv_purchase_remark);
            this.tv_purchase_commit = (TextView) getBootomBiew().findViewById(R.id.tv_purchase_commit);
            this.ll_purc_auto_add = (LinearLayout) getBootomBiew().findViewById(R.id.ll_purc_auto_add);//采购报价 动态加载
//            this.ll_mainView_bottom = (LinearLayout) rootView.findViewById(R.id.ll_mainView_bottom);//采购报价 动态加载
            this.et_purchase_remark = (EditText) getBootomBiew().findViewById(R.id.et_purchase_remark);//备注

            toolbar_title.setText("采购详情");
        }

    }


}
