package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.UnitTypeBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.AutoAddRelative;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.ToastUtil;

import java.util.List;

/**
 * 用于展示草稿箱
 */
public class SaveSeedlingActivity_change_data extends SaveSeedlingActivityBase {


    public static final String SEEDLING_KEY = "seedlin_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //step 2.5
        {
            //获取其他界面传过来的对象，，，用于修改信息并重新提交
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                saveSeedingGsonBean = (SaveSeedingGsonBean) bundle.getSerializable(SEEDLING_KEY);

                String proId = bundle.getString("proId");
                setProId(proId);
                D.e("=========proId=========" + proId);


                initAutoLayout(saveSeedingGsonBean.getData().getTypeList());
                initAutoLayout2(saveSeedingGsonBean.getData().getPlantTypeList());


                initExtra(saveSeedingGsonBean);

                viewHolder.bottom_ll.setUnitTypeDatas(saveSeedingGsonBean.getData().unitTypeList);


            } else {
                ToastUtil.showShortToast("数据初始化失败");
            }
        }

    }

    @Override
    public void initAutoLayout2(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList) {
        super.initAutoLayout2(plantTypeList);
        if (autoAddRelative_top != null) {
            autoAddRelative_top.getViewHolder_top().tv_auto_add_name.setText(saveSeedingGsonBean.getData().getSeedling().getName());
        }

    }

    private void initExtra(SaveSeedingGsonBean saveSeedingGsonBean) {

        List<ImagesJsonBean> imagesJsonBeans = saveSeedingGsonBean.getData().getSeedling().getImagesJson();

        if (null != imagesJsonBeans && imagesJsonBeans.size() != 0) {


            for (int i = 0; i < imagesJsonBeans.size(); i++) {

                if (!imagesJsonBeans.get(i).getLocal_url().equals("")) {
                    arrayList2Adapter.add(new Pic(imagesJsonBeans.get(i).getId(), imagesJsonBeans.get(i).isIsCover(), imagesJsonBeans.get(i).getLocal_url(), imagesJsonBeans.get(i).getSort()));//本地文件获取
                } else {
                    arrayList2Adapter.add(new Pic(imagesJsonBeans.get(i).getId(), imagesJsonBeans.get(i).isIsCover(), imagesJsonBeans.get(i).getUrl(), imagesJsonBeans.get(i).getSort()));//网络图片获取
                }

            }
        } else {
            if (saveSeedingGsonBean.getData().getSeedling().getImageUrl() != null) {
                arrayList2Adapter.add(new Pic(saveSeedingGsonBean.getData().getSeedling().getId(), false, saveSeedingGsonBean.getData().getSeedling().getImageUrl(), 0));//网络图片获取
            }

        }

        viewHolder.publish_flower_info_gv.getAdapter().notifyDataSetChanged();
        urlPaths.addAll(arrayList2Adapter);

//        initAutoLayout(saveSeedingGsonBean.getData().getTypeList());

        SaveSeedingGsonBean.DataBean.SeedlingBean seedling = saveSeedingGsonBean.getData().getSeedling();

        tag_ID = seedling.getFirstSeedlingTypeId();
        initAutoLayout(saveSeedingGsonBean.getData().getTypeList());

        tag_ID1 = seedling.getPlantType();
        initAutoLayout2(saveSeedingGsonBean.getData().getPlantTypeList());


        /**
         *   /**
         *   AutoAddRelative.ViewHolder_top viewHolder_top;
         AutoAddRelative.ViewHolder_rd viewHolder_rd;
         ArrayList<AutoAddRelative> arrayList_holders = new ArrayList();//共同的 holder 集合
         AutoAddRelative autoAddRelative_rd;
         */

        if (viewHolder_rd != null) {
            viewHolder_top.tv_auto_add_name.setText(seedling.getName());
            //根据种类选择 0.3  1.0  1.3  哪个被选中
            if (autoAddRelative_rd.getMTag().equals("dbh")) {
                viewHolder_rd.et_auto_add_min.setText(int_to_str(seedling.getMinDbh()));
                viewHolder_rd.et_auto_add_max.setText(int_to_str(seedling.getMaxDbh()));
                autoAddRelative_rd.setDiameterTypeWithSize(seedling.getDbhType() + "");
            } else {
                viewHolder_rd.et_auto_add_min.setText(int_to_str(seedling.getMinDiameter()));
                viewHolder_rd.et_auto_add_max.setText(int_to_str(seedling.getMaxDiameter()));
                autoAddRelative_rd.setDiameterTypeWithSize(seedling.getDiameterType() + "");
            }
        }

        for (int i = 0; i < arrayList_holders.size(); i++) {
            AutoAddRelative autoAddRelative = arrayList_holders.get(i);

            if (arrayList_holders.get(i).getTag().equals("高度")) {
                //有高度 参数
                autoAddRelative.getViewHolder().et_auto_add_min.setText(int_to_str(seedling.getMinHeight()));
                autoAddRelative.getViewHolder().et_auto_add_max.setText(int_to_str(seedling.getMaxHeight()));
            }

            if (arrayList_holders.get(i).getTag().equals("冠幅")) {
                autoAddRelative.getViewHolder().et_auto_add_min.setText(int_to_str(seedling.getMinCrown()));
                autoAddRelative.getViewHolder().et_auto_add_max.setText(int_to_str(seedling.getMaxCrown()));
            }
            if (arrayList_holders.get(i).getTag().equals("脱杆高")) {
                autoAddRelative.getViewHolder().et_auto_add_min.setText(int_to_str(seedling.getMinOffbarHeight()));
                autoAddRelative.getViewHolder().et_auto_add_max.setText(int_to_str(seedling.getMaxOffbarHeight()));
            }
            if (arrayList_holders.get(i).getTag().equals("长度")) {
                autoAddRelative.getViewHolder().et_auto_add_min.setText(int_to_str(seedling.getMinLength()));
                autoAddRelative.getViewHolder().et_auto_add_max.setText(int_to_str(seedling.getMaxLength()));


            }


        }


        D.e("===========继续完成其他==========" + seedling);
        D.e("===========先完成底部区域===价格  库存  单位 苗原地  备注等等======" + seedling);


        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();

        if (seedlingBean.getNurseryJson() == null) {
            seedlingBean.setNurseryJson(saveSeedingGsonBean.getData().nursery);
        }

//        SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = new SaveSeedingBottomLinearLayout.upLoadDatas();
        SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = viewHolder.bottom_ll.getUpLoadDatas();
//        upLoadDatas.price_max =seedlingBean.get ;
        upLoadDatas.price_min = seedling.getMinPrice() + "";
        upLoadDatas.price_max = seedling.getMaxPrice() + "";
        upLoadDatas.isMeet = seedling.isIsNego();
//plant 需要根据  tag  来返回 name 来显示

        upLoadDatas.repertory_num = seedling.getCount() + "";
        String nuit = seedling.getUnitTypeName();//中文名用key
        String value = seedling.getUnitType();

        upLoadDatas.setUnit(new UnitTypeBean(nuit, value));

        //地址对象
        AdressActivity.Address address = new AdressActivity.Address();
        address.addressId = seedling.getNurseryId();
        address.contactPhone = seedling.getNurseryJson().contactPhone;
        address.contactName = seedling.getNurseryJson().contactName;
        address.cityName = seedling.getNurseryJson().getCityName();
        address.name = seedling.getNurseryJson().getName();
        address.fullAddress = seedling.getNurseryJson().getFullAddress();
        address.isDefault = seedling.isDefault();

        upLoadDatas.address = address;
        upLoadDatas.validity = seedlingBean.getValidity() + "";
        upLoadDatas.remark = seedlingBean.getRemarks();


        viewHolder.bottom_ll.setUpLoadDatas(upLoadDatas);


    }

    /**
     * 跳转到本届面必须携带  seedling bean
     *
     * @param activity
     * @param saveSeedingGsonBean
     */
    public static void start2Activity(Activity activity, SaveSeedingGsonBean saveSeedingGsonBean, int request_code) {
        Intent intent = new Intent(activity, SaveSeedlingActivity_change_data.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEEDLING_KEY, saveSeedingGsonBean);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, request_code);
    }

    /**
     * 跳转到本届面必须携带  seedling bean  修改就数据，需要携带订单id 过来
     *
     * @param activity
     * @param saveSeedingGsonBean
     */
    public static void start2Activity(Activity activity, SaveSeedingGsonBean saveSeedingGsonBean, int request_code, String proId) {
        Intent intent = new Intent(activity, SaveSeedlingActivity_change_data.class);
        Bundle bundle = new Bundle();
        bundle.putString("proId", proId);
        bundle.putSerializable(SEEDLING_KEY, saveSeedingGsonBean);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, request_code);
    }


    /**
     * 传入 int  转换成string
     *
     * @param str
     * @return
     */
    public static String int_to_str(int str) {
        String result = "";

        if (str == 0) {
            result = "";
        } else {
            result = str + "";
        }
        return result;
    }
}
