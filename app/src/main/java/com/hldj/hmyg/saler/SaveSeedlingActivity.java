package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.D;
import com.yangfuhai.asimplecachedemo.lib.ACache;

/**
 * 发布苗木资源
 */
public class SaveSeedlingActivity extends SaveSeedlingActivityBase {


    ACache aCache;

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

        if (aCache.getAsObject("publish_data") != null) {
            SaveSeedingGsonBean saveSeedingGsonBean = (SaveSeedingGsonBean) aCache.getAsObject("publish_data");
            loadCache(saveSeedingGsonBean);
            D.e("========getAllData=========get from acache===");
            return;
        }

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
                D.e("============数据加载失败===========");


            }
        });


    }

    public void loadCache(SaveSeedingGsonBean saveSeedingGsonBean1) {
        SaveSeedlingActivity.this.saveSeedingGsonBean = saveSeedingGsonBean1;

        initAutoLayout(this.saveSeedingGsonBean.getData().getTypeList());

        initAutoLayout2(this.saveSeedingGsonBean.getData().getPlantTypeList());

        viewHolder.bottom_ll.setUnitTypeDatas(this.saveSeedingGsonBean.getData().unitTypeList);


        //地址对象
        AdressActivity.Address address = new AdressActivity.Address();
        address.addressId = this.saveSeedingGsonBean.getData().nursery.getId();
        address.contactPhone = this.saveSeedingGsonBean.getData().nursery.getPhone();
        address.contactName = this.saveSeedingGsonBean.getData().nursery.getRealName();
        address.cityName = this.saveSeedingGsonBean.getData().nursery.getCityName();
        address.isDefault = this.saveSeedingGsonBean.getData().nursery.isDefault;
        viewHolder.bottom_ll.setDefaultAddr(address);
    }

    public static void start2Activity(Activity context) {
        context.startActivityForResult(new Intent(context, SaveSeedlingActivity.class), 666);
    }


}
