package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.MyFinalActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import static com.hldj.hmyg.util.ConstantState.PUBLIC_TMP_SUCCEED;

/**
 * 弹窗activity  打包报价 用    基于二次报价来实现的
 */

public class DialogActivityPackage extends DialogActivitySecond {



    public static void start2Activity(Activity activity, String tag, PurchaseItemBean_new purchaseItemBeanNew, SellerQuoteJsonBean jsonBean) {
        Intent intent = new Intent(activity, DialogActivityPackage.class);
        intent.putExtra("tag", tag);
        intent.putExtra(DialogActivitySecond.TAG, purchaseItemBeanNew);
        intent.putExtra("jsonBean", jsonBean);
//        ToastUtil.showLongToast(purchaseItemBeanNew.toString());

        activity.startActivityForResult(intent, 100);
    }

    public static void start2Activity(Activity activity, String tag, PurchaseItemBean_new purchaseItemBeanNew) {
        Intent intent = new Intent(activity, DialogActivityPackage.class);
        intent.putExtra("tag", tag);
        intent.putExtra(DialogActivitySecond.TAG, purchaseItemBeanNew);
//        ToastUtil.showLongToast(purchaseItemBeanNew.toString());

        activity.startActivityForResult(intent, 100);
    }


    @Override
    public void initView() {
        super.initView();
        MyFinalActivity.initInjectedView(mActivity);
        commit.setText("暂存");
        getView(R.id.tv_show_tip).setVisibility(View.VISIBLE);
    }

    @Override
    public void save() {

        uploadBean = getUploadBean();//获取上传  对象

        D.e("上传对象\n" + uploadBean.toString());


        setLoadCancle(false);
        new PurchaseDeatilP(new ResultCallBack<SellerQuoteJsonBean>() {
            @Override
            public void onSuccess(SellerQuoteJsonBean itemBean_new) {
                if (itemBean_new != null) {
                    hindLoading();
                    Intent intent = new Intent();
                    intent.putExtra("bean", itemBean_new);
                    setResult(ConstantState.PUBLIC_SUCCEED, intent);//发布成功
                    finish();
                } else {
                    hindLoading();
                    setResult(ConstantState.PUBLIC_SUCCEED);//发布成功
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                hindLoading();

                if (strMsg.equals("系统内部错误")) {
                    ToastUtil.showLongToast("请填写必填信息");
                } else {
                    ToastUtil.showLongToast(strMsg);


                }

            }
        })
                .quoteCommitTemp(mActivity, uploadBean);
    }


    @Override
    public void 报价吧() {
        String i = "";
        if (getBeanHistory() != null) {
            i = getBeanHistory().id;
        }


        if (TextUtils.isEmpty(plantType)) {
            ToastUtil.showLongToast("请先选择种植类型");
            return;
        }

        /**
         *        paramsPut(params, ConstantParams.diameter, bean.diameter);
         paramsPut(params, ConstantParams.offbarHeight, bean.offbarHeight);
         paramsPut(params, ConstantParams.length, bean.length);
         paramsPut(params, ConstantParams.diameterType, bean.diameterType);
         paramsPut(params, ConstantParams.plantType, bean.plantType);
         */

        new BasePresenter()
                .putParams(ConstantParams.id, i)//修改的id
                .putParams(ConstantParams.diameter, 获取参数(ConstantParams.diameter))
                .putParams(ConstantParams.diameterType, diameterType)
                .putParams(ConstantParams.plantType, plantType)
                .putParams(ConstantParams.length, 获取参数(ConstantParams.length))
                .putParams(ConstantParams.offbarHeight, 获取参数(ConstantParams.offbarHeight))
                .putParams(ConstantParams.price, 获取参数(ConstantParams.price))
                .putParams(ConstantParams.prePrice, 获取参数(ConstantParams.prePrice))
                .putParams(ConstantParams.count, 获取参数(ConstantParams.count))
                .putParams(ConstantParams.height, 获取参数(ConstantParams.height))
                .putParams(ConstantParams.crown, 获取参数(ConstantParams.crown))
                .putParams(ConstantParams.dbh, 获取参数(ConstantParams.dbh))
                .putParams(ConstantParams.dbhType, dbhType)
                .putParams(ConstantParams.remarks, 备注.getText().toString())
                .putParams(ConstantParams.cityCode, 获取地址code())
                .putParams(ConstantParams.imagesData, GsonUtil.Bean2Json(listPicsOnline))
                .putParams(ConstantParams.purchaseItemId, getPurchaseType()) // 传过来的  顶部 数据的  item id
                .putParams(ConstantParams.purchaseId, getData().purchaseId)
                .doRequest("admin/quote/package/saveTemp", true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
                        if (gsonBean.getData().quote != null) {


                            Intent intent = new Intent();
                            intent.putExtra("bean", gsonBean.getData().quote);
                            setResult(PUBLIC_TMP_SUCCEED, intent);//发布成功
                            hindLoading();
                            finish();
                        }


                    }
                });
    }



}
