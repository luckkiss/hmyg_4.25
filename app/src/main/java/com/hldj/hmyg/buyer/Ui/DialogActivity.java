package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 弹窗activity
 */

public class DialogActivity extends BaseMVPActivity {
    private static final String TAG = "DialogActivity";

    @ViewInject(id = R.id.city)
    OptionItemView city;

    @ViewInject(id = R.id.close_title)
    TextView close_title;

    @ViewInject(id = R.id.tv_title)
    TextView tv_title;

    @ViewInject(id = R.id.space_text)
    TextView space_text;

    @ViewInject(id = R.id.guige)
    TextView 规格;

    @ViewInject(id = R.id.price)
    EditText 价格;

    @ViewInject(id = R.id.descript)
    EditText 报价说明;

    @ViewInject(id = R.id.commit)
    Button 提交;

    private CityGsonBean.ChildBeans cityBeans;


    /**
     * 位界面头部添加几条数据
     */
    private void fillData(PurchaseItemBean_new data) {
        if (data == null) {
            return;
        }
        tv_title.setText(filterColor(data.name + "  " + data.count + data.unitTypeName, data.count + data.unitTypeName));
        space_text.setText("种植类型: " + data.plantTypeArrayNames);
        规格.setText("规格: " + FUtil.$_zero(data.specText));
    }

    private String 获取价格() {
        return 价格.getText().toString().trim();
    }

    private CityGsonBean.ChildBeans 获取地址对象() {
        return cityBeans;
    }

    private String 获取报价说明() {
        return 报价说明.getText().toString().trim();
    }

    private String 获取ID() {
        if (getData() != null)
            return getData().id;
        else
            return "";
    }

    /**
     * paramsPut(params, ConstantParams.purchaseId, bean.purchaseId);
     * paramsPut(params, ConstantParams.purchaseItemId, bean.purchaseItemId);
     */

    private void 提交报价() {
        new BasePresenter()
                .putParams(ConstantParams.price, 获取价格())
                .putParams(ConstantParams.cityCode, 获取地址对象().cityCode)
                .putParams(ConstantParams.remarks, 获取报价说明())
//              .putParams(ConstantParams.id, 获取ID())
                .putParams(ConstantParams.purchaseId, getData().pid1)
                .putParams(ConstantParams.purchaseItemId,获取ID())
                .doRequest("admin/quote/saveSimple", true, new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
                    }
                });
    }


    @Override
    protected void initListener() {
        city.setOnClickListener(this::city);
        close_title.setOnClickListener(v -> finish());
        提交.setOnClickListener(v -> {
            D.i("---------提交报价-------");
            提交报价();
        });
    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        win.setAttributes(lp);


        fillData(getData());

    }


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_dialog;
    }


    public void city(View view) {
        CityWheelDialogF.instance()
                .isShowCity(true)
                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                    @Override
                    public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                        ToastUtil.showLongToast(childBeans.fullName);
                        cityBeans = childBeans;
                        city.setRightText(childBeans.fullName);
                    }

                    @Override
                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                    }
                })
                .show(getSupportFragmentManager(), TAG);
    }


    private PurchaseItemBean_new getData() {
        Bundle b = getIntent().getExtras();
//        PurchaseItemBean_new itemBean_new =
        if (b != null && b.get(TAG) instanceof PurchaseItemBean_new) {

            return (PurchaseItemBean_new) b.get(TAG);
        } else {
            return new PurchaseItemBean_new();
        }
    }

    public static void start(Activity activity, PurchaseItemBean_new purchaseItemBeanNew)

    {
        Intent i = new Intent(activity, DialogActivity.class);
        i.putExtra(TAG, purchaseItemBeanNew);
        activity.startActivity(i);
        ToastUtil.showLongToast(purchaseItemBeanNew.toString());
    }

    public static void start(Activity activity)

    {
        activity.startActivity(new Intent(activity, DialogActivity.class));

    }




    /*一比报价接口*/


}
