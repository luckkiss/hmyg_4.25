package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.AutoAddRelative;
import com.yangfuhai.asimplecachedemo.lib.ACache;

import java.util.ArrayList;

/**
 * 发布苗木资源  ---- 从快速记苗。跳过来。进行快速发布
 */
public class SaveSeedlingActivity_pubsh_quick extends SaveSeedlingActivityBase {

    @Keep
    public static final int SEEDING_REFRESH = 100;
    ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //测试   rx bus  能够正常通信。。。。最终是由
        /**
         * {@link SaveSeedlingActivityBase#seedlingSave} 来发送  刷新通知  发送一个   id  ，列表通过便利id  来确定哪个 item 需要进行刷新
         */

//      RxBus.getInstance().post(SEEDING_REFRESH, new PostObj<>(getSeedlingNoteId()));

        aCache = ACache.get(this);
        //暂存草稿箱
//        viewHolder.iv_ready_save.setOnClickListener(onClickListener);//
        showLoading();
        getAllData();
    }


    private void getAllData() {
//        aCache.put("publish_data",saveSeedingGsonBean);

//       获取所有数据  本页
        SaveSeedlingPresenter.getAllDatas_toSeed(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(final SaveSeedingGsonBean saveSeedingGsonBean) {
                aCache.put("publish_data", saveSeedingGsonBean);
                loadCache(saveSeedingGsonBean);
                D.e("========getAllData=========get from intenet===");
                //传参数的时候   增加 一个 自动添加布局，不需要点击  （乔木  灌木  庄敬  地被  苏铁  。。。。。。）
                hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                hindLoading();
                D.e("============数据加载失败===========");
                if (aCache.getAsObject("publish_data") != null) {
                    SaveSeedingGsonBean saveSeedingGsonBean = (SaveSeedingGsonBean) aCache.getAsObject("publish_data");
                    loadCache(saveSeedingGsonBean);
                    D.e("========getAllData=========get from acache===");
                    return;
                }


            }
        }, getExtraID());


    }

    public void loadCache(SaveSeedingGsonBean saveSeedingGsonBean1) {
        SaveSeedlingActivity_pubsh_quick.this.saveSeedingGsonBean = saveSeedingGsonBean1;

        initAutoLayout(this.saveSeedingGsonBean.getData().getTypeList());

        initAutoLayout2(this.saveSeedingGsonBean.getData().getPlantTypeList());

        viewHolder.bottom_ll.setUnitTypeDatas(this.saveSeedingGsonBean.getData().unitTypeList);


        //1
        initAddress(saveSeedingGsonBean1.getData().getSeedling().getNurseryJson());
        //2
        initGvTop(getImages(saveSeedingGsonBean1));

        //底部的 价格   库存
        initBottom(saveSeedingGsonBean1.getData().getSeedling());

        initAuto(saveSeedingGsonBean);

    }

    @Override
    public void onAutoChanged() {
        super.onAutoChanged();
        //点击事件触发后    动态添加结束之后  调用此方法
        initAuto(SaveSeedlingActivity_pubsh_quick.this.saveSeedingGsonBean);
    }

    private void initAuto(SaveSeedingGsonBean saveSeedingGsonBean) {

        if (saveSeedingGsonBean == null || saveSeedingGsonBean.getData() == null || saveSeedingGsonBean.getData().getSeedling() == null) {
            return;
        }
        SaveSeedingGsonBean.DataBean.SeedlingBean seedling = saveSeedingGsonBean.getData().getSeedling();

        String plantName = seedling.getName();//品名
        String space_min = seedling.minSpec;//规格 大
        String space_max = seedling.maxSpec;//规格 小

        String min_height = FormateInt2Str(seedling.getMinHeight());//高度 大
        String max_height = FormateInt2Str(seedling.getMaxHeight());//高度 小

        String min_crown = FormateInt2Str(seedling.getMinCrown());//冠幅 大
        String max_crown = FormateInt2Str(seedling.getMaxCrown());//冠幅 小


        //初始化品名
        autoAddRelative_top.getViewHolder_top().tv_auto_add_name.setText(plantName);

        if (null != autoAddRelative_rd) {
            autoAddRelative_rd.getViewHolder_rd().et_auto_add_min.setText(space_min);
            autoAddRelative_rd.getViewHolder_rd().et_auto_add_max.setText(space_max);
        }


        if (null != arrayList_holders && arrayList_holders.size() != 0) {

            for (int i = 0; i < arrayList_holders.size(); i++) {
                AutoAddRelative autoAddRelative = arrayList_holders.get(i);
                D.e("==========tag==========" + autoAddRelative.getTag());
                if (autoAddRelative.getTag().equals("高度")) {
                    autoAddRelative.getViewHolder().et_auto_add_min.setText(min_height);
                    autoAddRelative.getViewHolder().et_auto_add_max.setText(max_height);
                } else if (autoAddRelative.getTag().equals("冠幅")) {
                    autoAddRelative.getViewHolder().et_auto_add_min.setText(min_crown);
                    autoAddRelative.getViewHolder().et_auto_add_max.setText(max_crown);
                } else if (autoAddRelative.getTag().equals("脱肛高")) {
                    autoAddRelative.getViewHolder().et_auto_add_min.setText("");
                    autoAddRelative.getViewHolder().et_auto_add_max.setText("");
                }

            }

//            arrayList_holders.forEach(autoAddRelative -> {
//
//            });
        }


    }

    private void initBottom(SaveSeedingGsonBean.DataBean.SeedlingBean bean) {

        String price = bean.price;
        String count = bean.getCount() + "";
        String remarks = bean.getRemarks() + "";
        viewHolder.bottom_ll.getHolder().tv_save_seeding_price_min.setText(price);
        viewHolder.bottom_ll.getHolder().et_repertory_num.setText(count);
        viewHolder.bottom_ll.getHolder().et_remark.setText(remarks);
    }

    int index = 0;

    public ArrayList<Pic> getImages(SaveSeedingGsonBean saveSeedingGsonBean1) {

        ArrayList<Pic> pics = new ArrayList<>();
        index = 0;
        for (int i = 0; i < saveSeedingGsonBean1.getData().seedlingImage.size(); i++) {
            SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean imagesJson = saveSeedingGsonBean1.getData().seedlingImage.get(i);
            if (imagesJson != null)
                pics.add(new Pic(imagesJson.getId(), true, imagesJson.getOssMediumImagePath(), index));
        }

//        saveSeedingGsonBean1.getData().seedlingImage.forEach(imagesJson -> {
//
//        });

        return pics;
//        return (ArrayList<Pic>) getIntent().getSerializableExtra(IMAGES);
    }


    /*初始化地址*/
    private void initAddress(SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nurseryJson) {
        if (nurseryJson == null) {
            return;
        }
//        private AdressActivity.Address gegAddress() {
//
//            if (selectAddress != null) {
//                return selectAddress;
//            } else {
//                AdressActivity.Address address = new AdressActivity.Address();
//                address.addressId = addressId;//地址id
//                address.contactPhone = contactPhone;//联系电话
//                address.contactName = contactName;//联系人
//                address.name = getIntent().getStringExtra("nurseryJson_name");//苗圃名称:
//                address.fullAddress = fullAddress;
//                address.isDefault = isDefault;//是否默认
//                D.e("===========获取的地址==========" + address.toString());
//
//                return address;
//            }
//
//        }


        //地址对象
//        Bundle bundle = getIntent().getExtras();
        AdressActivity.Address address = new AdressActivity.Address();
        address.addressId = nurseryJson.id;//地址id
        address.contactPhone = nurseryJson.contactPhone;//联系电话
        address.contactName = nurseryJson.contactName;//联系人
        address.name = nurseryJson.name;//苗圃名称:
        address.fullAddress = nurseryJson.fullAddress;
        address.isDefault = nurseryJson.isDefault;//是否默认
//                = (AdressActivity.Address) bundle.getSerializable(ADDRESS);
        viewHolder.bottom_ll.setDefaultAddr(address);
    }

    private static final String DETAIL_ID = "detail_id";


    /**
     * 传递过来的id
     *
     * @param context
     * @param detail_id
     */
    public static void start2Activity(Activity context, String detail_id) {
        assert detail_id != null;
        Intent intent = new Intent(context, SaveSeedlingActivity_pubsh_quick.class);
        intent.putExtra(DETAIL_ID, detail_id);
        context.startActivityForResult(intent, 666);
    }

    public String getExtraID() {
        return getIntent().getStringExtra(DETAIL_ID);
    }


    /**
     * 为顶部 gv 添加默认的图片
     */

    public void initGvTop(ArrayList<Pic> arrayList) {
        D.e("=======添加图片s========");


        viewHolder.publish_flower_info_gv.getAdapter().notify(arrayList);
    }


    public static String FormateInt2Str(int i) {
        String result = "";
        if (i == 0) {
            return result;
        } else {
            return i + "";
        }
    }


    @Override
    public String getSeedlingNoteId() {
        D.e("==========getSeedlingNoteId=======获取记苗本id======" + getExtraID());
        return getExtraID();
    }
}
