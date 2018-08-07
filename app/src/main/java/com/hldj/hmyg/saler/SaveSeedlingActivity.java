package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.Ui.AuthenticationActivity;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SpecTypeBean;
import com.hldj.hmyg.exception.NoUserIdentityException;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.D;
import com.yangfuhai.asimplecachedemo.lib.ACache;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布苗木资源
 */
public class SaveSeedlingActivity extends SaveSeedlingActivityBase {


    ACache aCache;

    public List<SpecTypeBean> dbhTypeList = new ArrayList<>();
    public List<SpecTypeBean> diameterTypeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aCache = ACache.get(this);
        //暂存草稿箱
//        viewHolder.iv_ready_save.setOnClickListener(onClickListener);//
        getAllData();
    }


    private void getAllData() {
//        aCache.put("publish_data",saveSeedingGsonBean);

//       获取所有数据  本页
        SaveSeedlingPresenter.getAllDatas(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(final SaveSeedingGsonBean saveSeedingGsonBean) {
                aCache.put("publish_data", saveSeedingGsonBean);
                loadCache(saveSeedingGsonBean);
                D.e("========getAllData=========get from intenet===");
                //传参数的时候   增加 一个 自动添加布局，不需要点击  （乔木  灌木  庄敬  地被  苏铁  。。。。。。）
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

                /* 1020身份证  未认证 错误码    */
                if (t instanceof NoUserIdentityException && errorNo == 1020) {
//                    new AlertDialog(mActivity).builder()
//                            .setCancelable(false)
//                            .setTitle("未实名认证")
//                            .setPositiveButton("实名认证", v1 -> {
////                              ToastUtil.showLongToast("实名认证");
//                                AuthenticationActivity.start(mActivity, AuthenticationActivity.no_auth, "");
//                                mActivity.finish();
//                            }).setNegativeButton("关闭", v2 -> {
//                        mActivity.finish();
//                    }).show();

                    if (!mActivity.isFinishing()) {
                        final com.flyco.dialog.widget.MaterialDialog dialog = new com.flyco.dialog.widget.MaterialDialog(
                                mActivity);
                        dialog.title("未实名认证")
//                            .content("应互联网安全法需求，从今日起，需要对发布苗木的人员进行实名认证，请麻烦走起一波，带动互联网安全氛围")//
                                .content(strMsg)//
                                .btnText("退出", "实名认证")//
                                .show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.setOnBtnClickL(() -> {
                                    dialog.dismiss();
                                    mActivity.finish();
                                }, () -> {
                                    AuthenticationActivity.start(mActivity, AuthenticationActivity.no_auth, "");
                                    dialog.dismiss();
                                    mActivity.finish();
                                }
                        );
                    }


                }


                D.e("============数据加载失败===========");
                if (aCache.getAsObject("publish_data") != null) {
                    SaveSeedingGsonBean saveSeedingGsonBean = (SaveSeedingGsonBean) aCache.getAsObject("publish_data");
                    loadCache(saveSeedingGsonBean);
                    D.e("========getAllData=========get from acache===");
                    return;
                }


            }
        });


    }

    public void loadCache(SaveSeedingGsonBean saveSeedingGsonBean1) {
        SaveSeedlingActivity.this.saveSeedingGsonBean = saveSeedingGsonBean1;

        //不再直接初始化。改为 点击后。选择 后 。进行初始化
//        initAutoLayout(this.saveSeedingGsonBean.getData().getTypeList());

        initAutoLayout2(this.saveSeedingGsonBean.getData().getPlantTypeList());

        viewHolder.bottom_ll.setUnitTypeDatas(this.saveSeedingGsonBean.getData().unitTypeList);


        //地址对象
        AdressActivity.Address address = new AdressActivity.Address();
        address.addressId = this.saveSeedingGsonBean.getData().nursery.getId();
        address.contactPhone = this.saveSeedingGsonBean.getData().nursery.contactPhone;
        address.contactName = this.saveSeedingGsonBean.getData().nursery.contactName;
        address.cityName = this.saveSeedingGsonBean.getData().nursery.getCityName();
        address.name = this.saveSeedingGsonBean.getData().nursery.getName();//苗圃名称
        address.fullAddress = this.saveSeedingGsonBean.getData().nursery.getFullAddress();//详细地址
        address.isDefault = this.saveSeedingGsonBean.getData().nursery.isDefault;

        viewHolder.bottom_ll.setDefaultAddr(address);
    }

    public static void start2Activity(Activity context) {
        context.startActivityForResult(new Intent(context, SaveSeedlingActivity.class), 666);
    }


}
