package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
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

        /**
         * 初始化 地理位置
         */
        if (MainActivity.aMapLocation != null) {
            if (!TextUtils.isEmpty(MainActivity.cityCode)) {
                cityBeans = new CityGsonBean.ChildBeans();
                cityBeans.cityCode = MainActivity.cityCode;
                city.setRightText(MainActivity.province_loc + " " + MainActivity.city_loc);
            }
        }
    }

    private String 获取价格() {
        return 价格.getText().toString().trim();
    }

    private CityGsonBean.ChildBeans 获取地址对象() {
        if (cityBeans == null) {
            ToastUtil.showLongToast("请选择地址");
            return new CityGsonBean.ChildBeans();
        }
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

    private String 获取修改ID() {
        if (getBean() != null)
            return getBean().id;
        else
            return "";
    }

    /**
     * paramsPut(params, ConstantParams.purchaseId, bean.purchaseId);
     * paramsPut(params, ConstantParams.purchaseItemId, bean.purchaseItemId);
     */


    /**
     * new PurchaseDeatilP(new ResultCallBack<PurchaseItemBean_new>() {
     *
     * @Override public void onSuccess(PurchaseItemBean_new itemBean_new) {
     * if (itemBean_new != null) {
     * Intent intent = new Intent();
     * intent.putExtra("bean", itemBean_new);
     * setResult(ConstantState.PUBLIC_SUCCEED, intent);//发布成功
     * onSaveFinish(true);
     * } else {
     * hindLoading();
     * }
     * <p>
     * getDatas();
     * }
     * @Override public void onFailure(Throwable t, int errorNo, String strMsg) {
     * hindLoading();
     * }
     * })
     * .quoteCommit(PurchaseDetailActivity.this, uploadBean)
     * ;
     */

    private void 提交报价() {


        new BasePresenter()
                .putParams(ConstantParams.price, 获取价格())
                .putParams(ConstantParams.cityCode, 获取地址对象().cityCode)
                .putParams(ConstantParams.remarks, 获取报价说明())
                .putParams(ConstantParams.id, 获取修改ID())
                .putParams(ConstantParams.purchaseId, getData().pid1)
                .putParams(ConstantParams.purchaseItemId, 获取ID())
                .doRequest("admin/quote/saveSimple", true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        if (gsonBean.getData().purchaseItem != null) {
                            Intent intent = new Intent();
                            intent.putExtra("bean", gsonBean.getData().purchaseItem);
                            setResult(ConstantState.PUBLIC_SUCCEED, intent);//发布成功
                            hindLoading();
                            finish();
                        }
                    }
                });
    }


    @Override
    protected void initListener() {
        city.setOnClickListener(this::city);
        close_title.setOnClickListener(v -> finish());
        提交.setOnClickListener(v -> {
            D.i("---------提交报价-------");
            if (TextUtils.isEmpty(获取价格())) {
                ToastUtil.showLongToast("请填写价格");
                return;
            }
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

        fillHistory(getBean());


    }


    @ViewInject(id = R.id.price)
    TextView price;
    @ViewInject(id = R.id.descript)
    EditText descript;

    private void fillHistory(SellerQuoteJsonBean bean) {

        if (getBean() != null) {
            //填充 数据
            // 已填写的价格
            price.setText(bean.price);
            descript.setText(bean.remarks);
            city.setRightText(bean.cityName);
            cityBeans = new CityGsonBean.ChildBeans();
            cityBeans.cityCode = bean.cityCode;
            cityBeans.fullName = bean.cityName;
        }

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
//                        ToastUtil.showLongToast(childBeans.fullName);
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

    public static void start(Activity activity, PurchaseItemBean_new purchaseItemBeanNew) {



       /*没有同意协议，跳转协议界面 h5*/
        if (!MyApplication.getUserBean().supplierIsAgree) {
            supplierProtocol((FragmentActivity) activity, purchaseItemBeanNew);
            return;
        }

        Intent i = new Intent(activity, DialogActivity.class);
        i.putExtra(TAG, purchaseItemBeanNew);
        activity.startActivityForResult(i, 100);
//        ToastUtil.showLongToast(purchaseItemBeanNew.toString());
    }

    /*当  没有同意 供应商协议时 执行*/
    public static void supplierProtocol(FragmentActivity activity, PurchaseItemBean_new purchaseItemBeanNew) {
        WebViewDialogFragment3.newInstance(new WebViewDialogFragment3.OnAgreeListener() {
            @Override
            public void OnAgree(boolean b) {
                if (b) {
                    //true 同意协议
                    Intent i = new Intent(activity, DialogActivity.class);
                    i.putExtra(TAG, purchaseItemBeanNew);
                    activity.startActivityForResult(i, 100);
                    //false 不同意协议

                } else {

                }
            }
        }).show(activity.getSupportFragmentManager(), TAG);
//        Intent toMarketListActivity = new Intent(mActivity, SeedlingMarketPyMapActivity.class);
//        mActivity.startActivity(toMarketListActivity);
    }


    /**
     * 获取要修改的对象
     * 为空则为 发布新项目
     *
     * @return
     */
    public SellerQuoteJsonBean getBean() {
        Bundle b = getIntent().getExtras();
        if (b != null && b.get("jsonBean") instanceof SellerQuoteJsonBean) {
            return (SellerQuoteJsonBean) b.get("jsonBean");
        } else {
            return null;
        }
    }

    public static void start(Activity activity, PurchaseItemBean_new purchaseItemBeanNew, SellerQuoteJsonBean jsonBean) {

//        /*没有同意协议，跳转协议界面 h5*/
//        if (!MyApplication.getUserBean().supplierIsAgree) {
//            supplierProtocol((FragmentActivity) activity,purchaseItemBeanNew);
//            return;
//        }

        Intent i = new Intent(activity, DialogActivity.class);
        i.putExtra(TAG, purchaseItemBeanNew);
        i.putExtra("jsonBean", jsonBean);
        activity.startActivityForResult(i, 100);
//        ToastUtil.showLongToast(purchaseItemBeanNew.toString());
    }

    public static void start(Activity activity)

    {
        activity.startActivity(new Intent(activity, DialogActivity.class));

    }




    /*一比报价接口*/


}
