package com.hldj.hmyg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.Message;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivitySecond;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;

import java.util.ArrayList;


@SuppressLint("ResourceAsColor")
public class MessageListDetailAdapter extends BaseAdapter {
    private static final String TAG = "MessageListDetailAdapter";

    private ArrayList<Message> data = null;

    private Context context = null;
    private boolean isShow = false;

    public MessageListDetailAdapter(Context context, ArrayList<Message> data) {
        this.data = data;
        this.context = context;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.data.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(
                R.layout.list_item_message_detail, null);
        CheckBox id_cb_select_child = (CheckBox) inflate
                .findViewById(R.id.id_cb_select_child);
        TextView tv_name = (TextView) inflate.findViewById(R.id.tv_name);
        TextView tv_02 = (TextView) inflate.findViewById(R.id.tv_02);
        TextView tv_03 = (TextView) inflate.findViewById(R.id.tv_03);
//        id_cb_select_child.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MessageListDetailActivity.id_cb_select_all.setChecked(false);
//            }
//        });
        id_cb_select_child
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        data.get(position).setChecked(isChecked);
                    }
                });

        id_cb_select_child.setChecked(data.get(position).isChecked());
        if (data.get(position).isRead()) {
            tv_02.setTextColor(Color.GRAY);
            tv_03.setTextColor(Color.GRAY);
        } else {
            tv_02.setTextColor(Color.BLACK);
            tv_03.setTextColor(Color.BLACK);
        }
        tv_02.setText(data.get(position).getCreateDate());
        tv_03.setText(data.get(position).getMessage());
        tv_name.setText(data.get(position).getContentTypeName());

        if (isShow) {
            id_cb_select_child.setVisibility(View.VISIBLE);
        } else {
            id_cb_select_child.setVisibility(View.GONE);
        }
        inflate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if ("buyer".equals(data.get(position).getTargetUserType())) {

                } else if ("seller".equals(data.get(position)
                        .getTargetUserType())) {

                } else if ("agent".equals(data.get(position)
                        .getTargetUserType())) {

                }

                if ("buyer".equals(data.get(position).getTargetUserType())) {
                    // 验苗申请受理，跳转到验苗申请的“验苗中”列表

                    // 经纪提交验苗结果，跳转到验苗申请详情
                    if ("validateResultAudit".equals(data.get(position)
                            .getContentType())) {
                        Intent toValidateDetailActivity = new Intent(context, FlowerDetailActivity.class);
                        toValidateDetailActivity.putExtra("id", data.get(position).getSourceId());
                        toValidateDetailActivity.putExtra("status", "verifyed");
                        toValidateDetailActivity.putExtra("tag", "buyer");
                        context.startActivity(toValidateDetailActivity);
                        ((Activity) context).overridePendingTransition(
                                R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                    // 经纪发货通知，跳转到发车信息列表
//                    else if ("sendLoadCar".equals(data.get(position) .getContentType())) {
//                        Intent sendLoadCar = new Intent(context,
//                                LoadCarListActivity2.class);
//                        sendLoadCar.putExtra("status", "unreceipt");
//                        sendLoadCar.putExtra("loadItems", true);
//                        context.startActivity(sendLoadCar);
//                    }

                    // 审核采购单，跳转到采购详情
//                    else if ("auditPurchase".equals(data.get(position) .getContentType())) {
//                        Intent toManagerPurchaseActivity = new Intent(context,  ManagerPurchaseActivity.class);
//                        context  .startActivity(toManagerPurchaseActivity);
//                        ((Activity) context).overridePendingTransition(   R.anim.slide_in_left, R.anim.slide_out_right);
//                    }


                }
                // 卖家
                else if ("seller".equals(data.get(position).getTargetUserType())) {
                    // 买家申请验苗，不跳转（卖家没有相关验苗的订单）
                    if ("validateSeedling".equals(data.get(position)
                            .getContentType())) {
                        return;
                    }
                    // 苗木资源审核，跳转到苗木详情
                    else if ("seedlingAudit".equals(data.get(position)
                            .getContentType())) {
                        Intent seedlingAudit = new Intent(context,
                                FlowerDetailActivity.class);
                        seedlingAudit.putExtra("id", data.get(position)
                                .getSourceId());
                        seedlingAudit.putExtra("show_type", "manage_list");
                        context.startActivity(seedlingAudit);
                    }

                    // 审核报价，跳转到报价详情
                    else if ("auditQuote".equals(data.get(position)
                            .getContentType())) {
                        Intent auditQuote = new Intent(context,
                                ManagerQuoteListActivity_new.class);
                        auditQuote.putExtra("name", "审核中");
                        auditQuote.putExtra("status", "unaudit");
                        context.startActivity(auditQuote);
                    }
                    // 采用报价，跳转到报价详情
                    else if ("useQuote".equals(data.get(position)
                            .getContentType())) {
                        Intent useQuote = new Intent(context,
                                ManagerQuoteListActivity_new.class);
                        useQuote.putExtra("name", "已报价");
                        useQuote.putExtra("status", "quoted");
                        context.startActivity(useQuote);
                    } else if ("secondQuote".equals(data.get(position).getContentType())) {


                        Intent intent1 = new Intent(context, StorePurchaseListActivitySecond.class);
//                        intent1.putExtra("purchaseFormId", "2bb0859e7d094314a1a162a82a4fa408");
                        intent1.putExtra("purchaseFormId", data.get(position).getSourceId());
//                intent1.putExtra("purchaseFormId", "8e5aa65a2c374de99662dcf6e7e399a9");
//                intent.putExtra("title", item.num);
                        context.startActivity(intent1);
//                        useQuote.putExtra("name", "二次报价");
//                        useQuote.putExtra("status", "quoted");

                    }

//                    else if ([messageModel.contentType isEqualToString:@"secondQuote"]){
//                        //二次报价
//                        PurchaseMallSecondVC *purchaseMallVC=[PurchaseMallSecondVC new];
//                        purchaseMallVC.mallModel.purchaseId=messageModel.sourceId;
//                        purchaseMallVC.titleName=messageModel.conditionParam;
//            [self.navigationController pushViewController:purchaseMallVC animated:NO];
//                    }


                    // 订单创建，跳转销售订单的详情
//                    else if ("orderCreated".equals(data.get(position)
//                            .getContentType())) {
//                        Intent orderCreated = new Intent(context,
//                                OrderDetailActivity.class);
//                        orderCreated.putExtra("id", data.get(position)
//                                .getSourceId());
//                        context.startActivity(orderCreated);
//                    }
                    // 订单付款，跳转销售订单的详情
//                    else if ("orderAssurePay".equals(data.get(position)
//                            .getContentType())) {
//                        Intent orderAssurePay = new Intent(context,
//                                OrderDetailActivity.class);
//                        orderAssurePay.putExtra("id", data.get(position)
//                                .getSourceId());
//                        context.startActivity(orderAssurePay);
//                    }
                    // 订单创建，跳转到销售订单详情
//                    else if ("orderCreated".equals(data.get(position)
//                            .getContentType())) {
//                        Intent orderCreated = new Intent(context,
//                                OrderDetailActivity.class);
//                        orderCreated.putExtra("id", data.get(position)
//                                .getSourceId());
//                        context.startActivity(orderCreated);
//                    }
                    // 买家付款，跳转到销售订单详情
//                    else if ("orderPayed".equals(data.get(position)
//                            .getContentType())) {
//                        Intent orderPayed = new Intent(context,
//                                OrderDetailActivity.class);
//                        orderPayed.putExtra("id", data.get(position)
//                                .getSourceId());
//                        context.startActivity(orderPayed);
//                    }
                    // 买家确认收货（担保购，放心购的订单），跳转到销售订单详情
//                    else if ("orderTradeType".equals(data.get(position)
//                            .getContentType())) {
//                        Intent orderTradeType = new Intent(context,
//                                OrderDetailActivity.class);
//                        orderTradeType.putExtra("id", data.get(position)
//                                .getSourceId());
//                        context.startActivity(orderTradeType);
//                    }
                }
                // 经纪
                else if ("agent".equals(data.get(position).getTargetUserType())) {
                    // 验苗请求，跳转到验苗处理的“新验苗”列表
                    if ("validateSeedling".equals(data.get(position).getContentType())) {
//						Intent validateSeedling = new Intent(context, BrokerListActivity.class);
//						validateSeedling.putExtra("acceptStatus", "accepted");
//						validateSeedling.putExtra("loadItems", true);
//						context.startActivity(validateSeedling);
                    }
                    // 验苗结果审核，跳转到验苗处理详情
                    else if ("validateResultAudit".equals(data.get(position)
                            .getContentType())) {
                        Intent validateResultAudit = new Intent(context,
                                FlowerDetailActivity.class);
                        validateResultAudit.putExtra("id", data.get(position)
                                .getSourceId());
                        validateResultAudit.putExtra("tag", "broker");
                        context.startActivity(validateResultAudit);
                    }


                }

            }
        });
        return inflate;
    }

    public void notify(ArrayList<Message> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}
