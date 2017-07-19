//package com.hldj.hmyg.saler;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.hldj.hmyg.bean.Pic;
//import com.hldj.hmyg.bean.SaveSeedingGsonBean;
//import com.hldj.hmyg.util.D;
//import com.hldj.hmyg.widget.AutoAddRelative;
//import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
//import com.hy.utils.ToastUtil;
//
//import java.util.List;
//
///**
// * 用于展示草稿箱
// */
//public class SaveSeedlingActivity_show_history extends SaveSeedlingActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
////            //初始化草稿箱
////            if (!SPUtil.get(this, "save_sp", "").equals("")) {
////                saveSeedingGsonBean = GsonUtil.formateJson2Bean(SPUtil.get(this, "save_sp", "").toString(), SaveSeedingGsonBean.class);
////                initExtra(saveSeedingGsonBean);
////                return;
////            }
//        //step 2.5
//        {
//            //获取其他界面传过来的对象，，，用于修改信息并重新提交
//            Bundle bundle = getIntent().getExtras();
//            if (bundle != null) {
//                saveSeedingGsonBean = (SaveSeedingGsonBean) bundle.getSerializable(SEEDLING_KEY);
//
//
//                initAutoLayout(saveSeedingGsonBean.getData().getTypeList());
//                initAutoLayout2(saveSeedingGsonBean.getData().getPlantTypeList());
//
//
//                initExtra(saveSeedingGsonBean);
//
//                return; //直接通过传过来的数据进行 绘制界面  不需要再 请求接口数据进行初始化
//
//            } else {
//                ToastUtil.showShortToast("数据初始化失败");
//            }
//        }
//
//    }
//
//
//    public static final String SEEDLING_KEY = "seedlin_key";
//
//    /**
//     * 跳转到本届面必须携带  seedling bean
//     *
//     * @param activity
//     * @param saveSeedingGsonBean
//     */
//    public static void start2Activity(Activity activity, SaveSeedingGsonBean saveSeedingGsonBean, int request_code) {
//        Intent intent = new Intent(activity, SaveSeedlingActivity_show_history.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(SEEDLING_KEY, saveSeedingGsonBean);
//        intent.putExtras(bundle);
//        activity.startActivityForResult(intent, request_code);
//    }
//
//
//    private void initExtra(SaveSeedingGsonBean saveSeedingGsonBean) {
//
//        List<SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean.ImagesJsonBean> imagesJsonBeans = saveSeedingGsonBean.getData().getSeedling().getImagesJson();
//
//        if (null != imagesJsonBeans) {
//
//
//            for (int i = 0; i < imagesJsonBeans.size(); i++) {
//
//                if (!imagesJsonBeans.get(i).getLocal_url().equals("")) {
//                    arrayList2Adapter.add(new Pic(imagesJsonBeans.get(i).getId(), imagesJsonBeans.get(i).isIsCover(), imagesJsonBeans.get(i).getLocal_url(), imagesJsonBeans.get(i).getSort()));//本地文件获取
//                } else {
//                    arrayList2Adapter.add(new Pic(imagesJsonBeans.get(i).getId(), imagesJsonBeans.get(i).isIsCover(), imagesJsonBeans.get(i).getUrl(), imagesJsonBeans.get(i).getSort()));//网络图片获取
//                }
//
//            }
//        }
//
//        viewHolder.publish_flower_info_gv.getAdapter().notifyDataSetChanged();
//        urlPaths.addAll(arrayList2Adapter);
//
////        initAutoLayout(saveSeedingGsonBean.getData().getTypeList());
//
//        SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean seedling = saveSeedingGsonBean.getData().getSeedling();
//
//        tag_ID = seedling.getFirstSeedlingTypeId();
//        initAutoLayout(saveSeedingGsonBean.getData().getTypeList());
//
//        tag_ID1 = seedling.getPlantType();
//        initAutoLayout2(saveSeedingGsonBean.getData().getPlantTypeList());
//
//
//        /**
//         *   /**
//         *   AutoAddRelative.ViewHolder_top viewHolder_top;
//         AutoAddRelative.ViewHolder_rd viewHolder_rd;
//         ArrayList<AutoAddRelative> arrayList_holders = new ArrayList();//共同的 holder 集合
//         AutoAddRelative autoAddRelative_rd;
//         */
//
//        if (viewHolder_rd != null) {
//
//            viewHolder_top.tv_auto_add_name.setText(seedling.getName());
//            //根据种类选择 0.3  1.0  1.3  哪个被选中
//            if (autoAddRelative_rd.getMTag().equals("dbh")) {
//                viewHolder_rd.et_auto_add_min.setText(seedling.getMinDbh() + "");
//                viewHolder_rd.et_auto_add_max.setText(seedling.getMaxDbh() + "");
//                autoAddRelative_rd.setDiameterTypeWithSize(seedling.getDbhType() + "");
//            } else {
//                viewHolder_rd.et_auto_add_min.setText(seedling.getMinDiameter() + "");
//                viewHolder_rd.et_auto_add_max.setText(seedling.getMaxDiameter() + "");
//                autoAddRelative_rd.setDiameterTypeWithSize(seedling.getDiameterType() + "");
//            }
//
//
//        }
//
//        for (int i = 0; i < arrayList_holders.size(); i++) {
//            AutoAddRelative autoAddRelative = arrayList_holders.get(i);
//
//            if (arrayList_holders.get(i).getTag().equals("高度")) {
//                //有高度 参数
//                autoAddRelative.getViewHolder().et_auto_add_min.setText(seedling.getMinHeight() + "");
//                autoAddRelative.getViewHolder().et_auto_add_max.setText(seedling.getMaxHeight() + "");
//            }
//
//            if (arrayList_holders.get(i).getTag().equals("冠幅")) {
//                autoAddRelative.getViewHolder().et_auto_add_min.setText(seedling.getMinCrown() + "");
//                autoAddRelative.getViewHolder().et_auto_add_max.setText(seedling.getMaxCrown() + "");
//            }
//            if (arrayList_holders.get(i).getTag().equals("脱杆高")) {
//                autoAddRelative.getViewHolder().et_auto_add_min.setText(seedling.getMinOffbarHeight() + "");
//                autoAddRelative.getViewHolder().et_auto_add_max.setText(seedling.getMaxOffbarHeight() + "");
//            }
//            if (arrayList_holders.get(i).getTag().equals("长度")) {
//                autoAddRelative.getViewHolder().et_auto_add_min.setText(seedling.getMinLength() + "");
//                autoAddRelative.getViewHolder().et_auto_add_max.setText(seedling.getMaxLength() + "");
//            }
//
//
//        }
//
//
//        D.e("===========继续完成其他==========" + seedling);
//        D.e("===========先完成底部区域===价格  库存  单位 苗原地  备注等等======" + seedling);
//
//
//        SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();
//
//        SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = new SaveSeedingBottomLinearLayout.upLoadDatas();
////        upLoadDatas.price_max =seedlingBean.get ;
//        upLoadDatas.price_min = seedling.getMinPrice() + "";
//        upLoadDatas.price_max = seedling.getMaxPrice() + "";
//        upLoadDatas.isMeet = seedling.isIsNego();
////plant 需要根据  tag  来返回 name 来显示
//
//        upLoadDatas.repertory_num = seedling.getCount() + "";
//        String nuit = seedling.getUnitTypeName();
//        upLoadDatas.unit = nuit;
//
//
//        viewHolder.bottom_ll.getHolder().rl_save_seeding_unit.setTag(viewHolder.bottom_ll.getTagByName(nuit));
//
//        //地址对象
//        AdressActivity.Address address = new AdressActivity.Address();
//        address.addressId = seedling.getNurseryId();
//        address.contactPhone = seedling.getNurseryJson().getPhone();
//        address.contactName = seedling.getNurseryJson().getRealName();
//        address.cityName = seedling.getNurseryJson().getCityName();
//        address.isDefault = seedling.isDefault();
//
//        upLoadDatas.address = address;
//        upLoadDatas.validity = seedlingBean.getValidity() + "";
//        upLoadDatas.remark = seedlingBean.getRemarks();
//
//
//        viewHolder.bottom_ll.setUpLoadDatas(upLoadDatas);
//
//
//    }
//}
