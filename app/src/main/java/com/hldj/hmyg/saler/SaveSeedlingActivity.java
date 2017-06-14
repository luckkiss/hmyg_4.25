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


    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        D.e("=====");
//        super.onCreate(savedInstanceState);
//        DBOpenHelper dbOpenHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
//        try {
//            db = dbOpenHelper.getWritableDatabase();
//        } catch (SQLiteException ex) {
//            db = dbOpenHelper.getReadableDatabase();
//        }
//        initDao();
//        getAllData();
//    }


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
        context.startActivityForResult(new Intent(context, SaveSeedlingActivity.class),666);
    }


//
//    View.OnClickListener onClickListener = v -> {
//        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean1 = GsonUtil.formateJson2Bean("", SaveSeedingGsonBean.DataBean.SeedlingBean.class);
//
//
//        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = new SaveSeedingGsonBean.DataBean.SeedlingBean();
//        //step 1  图片如何插入
//        if (viewHolder.publish_flower_info_gv.getAdapter().getDataList().size() != 0) {//
//
//            List<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean> list_imgs
//                    = new ArrayList<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean>();
//            for (int i = 0; i < viewHolder.publish_flower_info_gv.getAdapter().getDataList().size(); i++) {
//                SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean
//                        imagesJsonBean = new SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean()
//                        .setLocal_url(viewHolder.publish_flower_info_gv.getAdapter()
//                                .getDataList()
//                                .get(i).getUrl());
//
//                list_imgs.add(imagesJsonBean);
//            }
//            seedlingBean.setImagesJson(list_imgs);
//        }
//
//
//        //step 2 中间动态数据 保存
//        seedlingBean.setFirstSeedlingTypeId(tag_ID);
//        seedlingBean.setPlantType(tag_ID1);
//
//        if (viewHolder_rd != null) {
//            seedlingBean.setName(viewHolder_top.tv_auto_add_name.getText().toString());
//            if (autoAddRelative_rd.getMTag().equals("dbh")) {
//                seedlingBean.setMaxDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
//                seedlingBean.setMinDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
//                seedlingBean.setDbhType(autoAddRelative_rd.getDiameterType());
//            } else {
//
//                seedlingBean.setMaxDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
//                seedlingBean.setMinDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
//                seedlingBean.setDiameterType(autoAddRelative_rd.getDiameterType());
//            }
//        }
//
//
//        //step 3  底部固定布局 数据保存
//
//
//        SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = viewHolder.bottom_ll.getUpLoadDatas();
//
//        if (MyUtil.formateString2Int(upLoadDatas.getPrice_min()) != 0) {
//            seedlingBean.setMinPrice(MyUtil.formateString2Int(upLoadDatas.getPrice_min()));
//        }
//        if (MyUtil.formateString2Int(upLoadDatas.getPrice_max()) != 0) {
//            seedlingBean.setMaxPrice(MyUtil.formateString2Int(upLoadDatas.getPrice_max()));
//        }
//        seedlingBean.setIsNego(upLoadDatas.isMeet());
//        if (MyUtil.formateString2Int(upLoadDatas.getRepertory_num()) != 0) {
//            seedlingBean.setCount(MyUtil.formateString2Int(upLoadDatas.getRepertory_num()));
//        }
////                seedling.getUnitTypeName();
//        seedlingBean.setUnitTypeName(upLoadDatas.getUnit());//plant 需要根据  tag  来返回 name 来显示
//        seedlingBean.setValidity(MyUtil.formateString2Int(upLoadDatas.getValidity()));
//        seedlingBean.setRemarks(upLoadDatas.getRemark());
//
//
//        SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nurseryJsonBean
//                = new SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean();
//
//        //地址对象
//        AdressActivity.Address address = upLoadDatas.address;
//        seedlingBean.setNurseryId(address.addressId);
//        nurseryJsonBean.setPhone(address.contactPhone);
//        nurseryJsonBean.setRealName(address.contactName);
//        nurseryJsonBean.setCityName(address.cityName);
//        seedlingBean.setCityName(address.cityName);
//        seedlingBean.setDefault(address.isDefault);
//        seedlingBean.setNurseryJson(nurseryJsonBean);
//
//        SaveSeedlingActivity.this.saveSeedingGsonBean.getData().setSeedling(seedlingBean);
//        String json = GsonUtil.Bean2Json(SaveSeedlingActivity.this.saveSeedingGsonBean);
//
//
//        D.e("==json==" + json);
//        SPUtil.put(MyApplication.getInstance(), "save_sp", json);
//
//        insertDao(json);
//
//    };
//
//
//    SavaBeanDao savaBeanDao;
//
//    private void insertDao(String json) {
//        /**
//         * User user = new User(null, "zhangsan" + random.nextInt(9999),"张三");
//         userDao.insert(user);
//         */
//        savaBeanDao = MyApplication.getInstance().getDaoSession().getSavaBeanDao();
//        SavaBean savaBean = new SavaBean();
//        savaBean.setJson(json);
//
//        D.e("======添加毫秒数==========" + savaBeanDao.insert(savaBean));
//
//        ToastUtil.showShortToast("已存入草稿箱");
//        setResult(ConstantState.SAVE_SUCCEED);
//        finish();
//
////        List<SavaBean> list = savaBeanDao.queryBuilder()
////                .where(SavaBeanDao.Properties.Id.between(2, 13))
////                .limit(5)
////                .build()
////                .list();
//
//
//    }


}
