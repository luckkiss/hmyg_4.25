package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.StorePurchaseListAdapter;
import com.hldj.hmyg.application.PermissionUtils;
import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.M.QuoteGsonBean;
import com.hldj.hmyg.buyer.M.QuoteListBean;
import com.hldj.hmyg.buyer.P.QuoteListP;
import com.hldj.hmyg.buyer.V.PurchaseDeatilV;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


public class QuoteListActivity_bak extends NeedSwipeBackActivity implements PurchaseDeatilV {


    QuoteGsonBean saveSeedingGsonBean;
    private CoreRecyclerView recycle_quit;
    private ViewHolder viewHolder_quote;//其他界面要访问就  写一个getviewholder 方法来调用 避免提示器提示很多个viewholdeer 类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.quote_detail_activity);
        InitHolder();

        viewHolder_quote.btn_back.setOnClickListener(v -> {
            finish();//后退按钮
        });

        InitViews();
        getDatas();


    }

    private static int pageNum = 1;//每次加载8条数据

    @Override
    public void InitViews() {

        ((TextView) findViewById(R.id.toolbar_title)).setText("报价列表");

        recycle_quit = (CoreRecyclerView) findViewById(R.id.recycle_quit);

        recycle_quit.initView(this)
                .init(new BaseQuickAdapter<QuoteListBean, BaseViewHolder>(R.layout.item_quote) {
                    @Override
                    protected void convert(BaseViewHolder helper, QuoteListBean item) {
                        D.e("==hellow world==");
                        initRecycleItem(helper, item);
                    }
                })
                .openLoadMore(1000, page -> {
                    D.e("==hellow world==");
                    getDatas();
                })
                .addRefreshListener(() -> {
                    D.e("==refresh==");
                })
                .openRefresh();

    }


    /**
     * 初始化 recycle  的每个布局
     *
     * @param helper
     * @param item
     */
    private void initRecycleItem(BaseViewHolder helper, QuoteListBean item) {

        helper.setText(R.id.tv_quote_item_sellerName, strFilter(item.sellerName).equals("") ? strFilter(item.sellerPhone) : strFilter(item.sellerName));//报价人
        helper.setText(R.id.tv_quote_item_cityName, strFilter(item.cityName));//苗源地址
        helper.setText(R.id.tv_quote_item_price, strFilter(item.price + ""));//价格
        helper.setText(R.id.tv_quote_item_plantTypeName, strFilter(item.plantTypeName));//种植类型
        helper.setText(R.id.tv_quote_item_specText, strFilter(item.specText));//要求规格
        helper.setText(R.id.tv_quote_item_count, strFilter(item.count + ""));// 可供数量
        helper.setText(R.id.tv_quote_item_remark, strFilter(item.remarks + ""));//  备注信息
//
//        StorePurchaseListAdapter.setSpaceAndRemark(helper.getView(R.id.tv_quote_item_specText), item.specText, item.remarks);


        helper.setText(R.id.tv_quote_item_photo_num, strFilter("1"));//有多少张图片

        helper.addOnClickListener(R.id.cv_root, v -> {
            boolean requesCallPhonePermissions = new PermissionUtils(QuoteListActivity_bak.this).requesCallPhonePermissions(200);
            if (requesCallPhonePermissions) {
                CallPhone(item.sellerPhone);
            }
        });


//        recycle_quit.addOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
////                ToastUtil.showShortToast("电话号码：" + item.sellerPhone);
//                boolean requesCallPhonePermissions = new PermissionUtils(QuoteListActivity_bak.this).requesCallPhonePermissions(200);
//                if (requesCallPhonePermissions) {
//                    CallPhone(item.sellerPhone);
//                }
//                return;
//
//            }
//        });
//        helper.setOnItemClickListener(R.id.tv_quote_item_photo_num, (parent, view, position, id) -> {
//            ToastUtil.showShortToast("电话号码：" + item.sellerPhone);
//        });

    }


    private void initItem(ViewHolder viewHolder_quote, ItemBean itemBean) {


        //获取到 item 数据  通过viewholder  来显示

        viewHolder_quote.tv_quote_name.setText(strFilter(itemBean.name));
//        viewHolder_quote.tv_quote_size.setText(strFilter(itemBean.specText));

        StorePurchaseListAdapter.setSpaceAndRemark(viewHolder_quote.tv_quote_size, itemBean.specText, itemBean.remarks);

        viewHolder_quote.tv_quote_type.setText(strFilter(itemBean.plantTypeName));
        viewHolder_quote.tv_quote_num.setText(strFilter(itemBean.count + itemBean.unitTypeName));
        viewHolder_quote.tv_quote_close_date.setText(strFilter(itemBean.purchaseJson.closeDate));
        viewHolder_quote.tv_quote_address.setText(strFilter(itemBean.purchaseJson.cityName));

    }


    @Override
    public void InitHolder() {
        viewHolder_quote = new ViewHolder(this);

    }

    @Override
    public void InitOnclick() {

    }


    private static final String GOOD_ID = "good_id";

    @Override
    public void getDatas() {


        if (null == getIntent().getExtras().get(GOOD_ID)) {
            ToastUtil.showShortToast("请用start2Activity方法来跳转到本届面");
            return;
        }

        new QuoteListP(new ResultCallBack<QuoteGsonBean>() {
            @Override
            public void onSuccess(QuoteGsonBean saveSeedingGsonBean) {

                QuoteListActivity_bak.this.saveSeedingGsonBean = saveSeedingGsonBean;

                initItem(viewHolder_quote, saveSeedingGsonBean.data.item);
//              quoteList  recycle_quit
                recycle_quit.getAdapter().addData(saveSeedingGsonBean.data.quoteList);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        })
                .getDatas(getIntent().getExtras().get(GOOD_ID).toString())
        ;


    }


    /**
     * str 过滤器  避免null 程序错误
     *
     * @param activity
     * @param good_id
     */
    public static void start2Activity(Activity activity, String good_id) {
        Intent intent = new Intent(activity, QuoteListActivity_bak.class);
        intent.putExtra(GOOD_ID, good_id);
        activity.startActivityForResult(intent, 100);
    }


    private class ViewHolder {
        public View rootView;
        public TextView tv_title;
        public ImageView btn_back;
        public TextView tv_quote_name;
        public SuperTextView tv_quote_size;
        public SuperTextView tv_quote_type;
        public SuperTextView tv_left;
        public SuperTextView tv_quote_num;
        public SuperTextView tv_quote_address;
        public SuperTextView tv_quote_close_date;
        public SuperTextView tv_quote_store_detail;
        public SuperTextView tv_quote_price_sug;

        public ViewHolder(Activity rootView) {
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.btn_back = (ImageView) rootView.findViewById(R.id.toolbar_left_icon);
            this.tv_quote_name = (TextView) rootView.findViewById(R.id.tv_quote_name);
            this.tv_quote_size = (SuperTextView) rootView.findViewById(R.id.tv_quote_size);
            this.tv_quote_type = (SuperTextView) rootView.findViewById(R.id.tv_quote_type);
            this.tv_left = (SuperTextView) rootView.findViewById(R.id.tv_left);
            this.tv_quote_num = (SuperTextView) rootView.findViewById(R.id.tv_quote_num);
            this.tv_quote_address = (SuperTextView) rootView.findViewById(R.id.tv_quote_address);
            this.tv_quote_close_date = (SuperTextView) rootView.findViewById(R.id.tv_quote_close_date);
//            this.tv_quote_store_detail = (SuperTextView) rootView.findViewById(R.id.tv_quote_store_detail);
//            this.tv_quote_price_sug = (SuperTextView) rootView.findViewById(R.id.tv_quote_price_sug);
        }

    }


    private void CallPhone(final String displayPhone) {
        // TODO Auto-generated method stub
        if (!"".equals(displayPhone)) {
            new AlertDialog(this).builder()
                    .setTitle(displayPhone)
                    .setPositiveButton("呼叫", v -> {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                .parse("tel:" + displayPhone));
                        // Intent intent = new Intent(Intent.ACTION_DIAL,
                        // Uri
                        // .parse("tel:" + displayPhone));
                        if (ActivityCompat.checkSelfPermission(QuoteListActivity_bak.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            return;
                        }
                        startActivity(intent);
                    }).setNegativeButton("取消", v -> {

            }).show();
        }
    }


}
