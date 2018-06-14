package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.weidet.Purchase.PurchaseAutoAddLinearLayout;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hldj.hmyg.widget.AutoAddRelative;
import com.hy.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzy.common.widget.MeasureGridView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 采购详情  修改界面
 */
public class PurchaseDetailActivityChange extends BaseMVPActivity implements ChangeView {

    public static PurchaseDetailActivityChange instance;
    private static final String TAG = "PurchaseChange";
    private String flowerInfoPhotoPath = "";
    PurchaseDetailActivity.uploadBean uploadBean = new PurchaseDetailActivity.uploadBean();
    private MeasureGridView measureGridView;
    private ArrayList<Pic> listPicsOnline = new ArrayList<>();

    public static void start2Activity(Activity activity, String tag, SellerQuoteJsonBean item, SaveSeedingGsonBean bean) {
        Intent intent = new Intent(activity, PurchaseDetailActivityChange.class);
        intent.putExtra("tag", tag);
        intent.putExtra("bean", bean);
        intent.putExtra("item", item);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public void initData() {
        instance = this;

        if (getBean() == null | getBean().getData() == null || getBean().getData().getTypeList() == null) {
            ToastUtil.showLongToast("初始化失败");
            return;
        }

        if (ConstantParams.direct.equals(getPurchaseType())) {
            Log.i(TAG, "initData: 初始化直购");
            initDirect(getBean().getData().getTypeList());
        } else {
            Log.i(TAG, "initData: 初始化代购");
            initProtocol(getBean().getData().getTypeList());

        }

    }


    /**
     * 根据bean 来初始化布局
     *
     * @param bean
     */
    private void initBean(SaveSeedingGsonBean bean) {

        //直购 代购  之前  使用bean 来 初始化动态布局


    }


    public String getPurchaseType() {
        String tag = getIntent().getStringExtra("tag");
        Log.i(TAG, "PurchaseChange: " + tag);
        return tag;
    }

    public SellerQuoteJsonBean getItem() {
        if (getIntent().getSerializableExtra("item") != null && getIntent().getSerializableExtra("item") instanceof SellerQuoteJsonBean) {
            SellerQuoteJsonBean item = (SellerQuoteJsonBean) getIntent().getSerializableExtra("item");
            Log.i(TAG, "PurchaseChange: " + item.toString());
            return item;
        }
        return null;
    }

    public SaveSeedingGsonBean getBean() {
        if (getIntent().getSerializableExtra("bean") != null && getIntent().getSerializableExtra("bean") instanceof SaveSeedingGsonBean) {
            SaveSeedingGsonBean bean = (SaveSeedingGsonBean) getIntent().getSerializableExtra("bean");
            Log.i(TAG, "PurchaseChange: " + bean.toString());
            return bean;
        }
        return null;
    }


    public String cityCode = "";


    String plantType = "";
    int index = -1;

    //step 1  这一步是一样的
    public void initAutoLayout2(TagFlowLayout tagFlowLayout, List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> bean) {

        tagFlowLayout.setCanCancle(false);

        for (int i = 0; i < bean.size(); i++) {
            if (!TextUtils.isEmpty((getItem().plantType)) && bean.get(i).getValue().equals(getItem().plantType)) {
                plantType = getItem().plantType;
                index = i;
                D.e("plantType" + plantType);
                D.e("plantTypeName" + getItem().plantTypeName);
            }
        }

        SaveSeedlingPresenter.initAutoLayout2(tagFlowLayout, bean, index, mActivity, (view, position, parent) -> {
            plantType = bean.get(position).getValue();//上传值
            uploadBean.plantType = plantType;
            return true;
        });

    }


    @Override
    public void initView() {


        MeasureGridView gridView = getView(R.id.grid_img1);
        ViewGroup parent = (ViewGroup) getView(R.id.tv_purchase_add_pic).getParent();
        parent.setVisibility(View.GONE);



            initGvTop(gridView, getItem().imagesJson);

        TextView tv_title = getView(R.id.tv_change_title);
        tv_title.setText("修改报价信息");
        tv_title.setVisibility(View.GONE);

        tv_title.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));

        TextView remark = getView(R.id.et_purchase_remark);
        remark.setText(getItem().remarks);

        initAutoLayout2(getView(R.id.tfl_purchase_auto_add_plant), getBean().getData().getPlantTypeList());

        TextView tv_purchase_commit = getView(R.id.tv_purchase_commit);
        tv_purchase_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先上传图片后
                upLoadImgs();
//                save();
            }
        });


        //城市
        TextView tv_purchase_city_name = getView(R.id.tv_purchase_city_name);
        tv_purchase_city_name.setText(getItem().cityName);
        cityCode = getItem().cityCode;
        tv_purchase_city_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityWheelDialogF.instance()
                        .isShowCity(true)
                        .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                            @Override
                            public void onCitySelect(CityGsonBean.ChildBeans child) {
                                D.e("===========child==============" + child.toString());
                                tv_purchase_city_name.setText(child.fullName);
                                cityCode = child.cityCode;
                            }

                            @Override
                            public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                            }
                        })
                        .show(getSupportFragmentManager(), TAG);
            }
        });

    }

    //传入初始化 的图片资源  初始化顶部  图片列表控件
    public void initGvTop(MeasureGridView gridView, List<ImagesJsonBean> imagesJson) {
        measureGridView = gridView;
//            arrayList.add(new Pic("hellow", true, MeasureGridView.usrl, 1));
//            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));

        gridView.init(this, convert2Pic(imagesJson), getView(R.id.main), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
            @Override
            public void onTakePic() {
                D.e("===========onTakePic=============");
                if (TakePhotoUtil.toTakePic(mActivity))//检查 存储空间
                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(mActivity, TakePhotoUtil.TO_TAKE_PIC);//照相
            }

            @Override
            public void onChoosePic() {
                D.e("===========onChoosePic=============");
                //通过本界面 addPicUrls 方法回调
                TakePhotoUtil.toChoosePic(mActivity, gridView.getAdapter());
            }

            @Override
            public void onCancle() {
                D.e("===========onCancle=============");
            }
        });

    }

    private ArrayList<Pic> convert2Pic(List<ImagesJsonBean> imagesJson) {
        ArrayList<Pic> pics = new ArrayList<>();
        if (imagesJson != null && imagesJson.size() > 0) {
            for (int i = 0; i < imagesJson.size(); i++) {
                pics.add(new Pic(imagesJson.get(i).id, false, imagesJson.get(i).ossMediumImagePath, i));
            }
        }
        return pics;
    }


    List<PurchaseAutoAddLinearLayout> autoLayouts = new ArrayList<>();

    @Override
    public void initDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        //直购 代购
        TextView tv_purchase_add_pic = getView(R.id.tv_purchase_add_pic);
        LinearLayout ll_purc_auto_add = getView(R.id.ll_purc_auto_add);
        AutoAddRelative.isShowLeftAndRight(true, tv_purchase_add_pic, R.drawable.seller_redstar, R.drawable.ic_right_icon_new);

        autoLayouts.clear();
        ll_purc_auto_add.removeAllViews();//动态添加前先删除所有
        PurchaseAutoAddLinearLayout layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).
                setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "price", true));

        if (getItem()!=null)
            layout.setDefaultData(getItem().price);
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);
        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("数量", "count", false));
        if (getItem()!=null)
            layout.setDefaultData(getItem().count + "");
        autoLayouts.add(layout);
        View line = new View(mActivity);
        ll_purc_auto_add.addView(layout);


    }


    @Override
    public void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        LinearLayout ll_purc_auto_add = getView(R.id.ll_purc_auto_add);
        autoLayouts.clear();
//      viewHolder_pur.ll_purc_auto_add.addView(new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "dbh", true)));
        ll_purc_auto_add.removeAllViews();//动态添加前先删除所有
        PurchaseAutoAddLinearLayout layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "price", true));
        layout.setDefaultData(getItem().price);// 价格
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);


        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("到货价\n(预估)", "prePrice", false));
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);
        layout.setDefaultData(getItem().prePrice);// 默认到岸价

        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("数量", "count", false));
        layout.setDefaultData(getItem().count + "");// 数量
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);

        for (int i = 0; i < typeListBeen.size(); i++) {
            if (getBean().getData().getItem().firstSeedlingTypeId.equals(typeListBeen.get(i).getId())) {
                //胸径   高度  冠幅  通过代码来增加
                for (int j = 0; j < typeListBeen.get(i).getParamsList().size(); j++) {
                    //创建 一个plantBean
                    PurchaseAutoAddLinearLayout.PlantBean plantBean = new PurchaseAutoAddLinearLayout.PlantBean(typeListBeen.get(i).getParamsList().get(j).getName(),
                            typeListBeen.get(i).getParamsList().get(j).getValue(),
                            typeListBeen.get(i).getParamsList().get(j).isRequired()
                    ).setSizeList(getBean().getData().dbhTypeList, getBean().getData().diameterTypeList);
                    //给plant 赋值
                    layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(plantBean);
                    setDefayltMsg(layout, plantBean);
                    //保存当前的 viw 到list 列表  上传数据时需要获取其中的 内容
                    autoLayouts.add(layout);
                    ll_purc_auto_add.addView(layout);
                }


            }
            // "name": "地径",
            //"value": "diameter",
            //"required": true
        }

    }

    protected void setDefayltMsg(PurchaseAutoAddLinearLayout layout, PurchaseAutoAddLinearLayout.PlantBean plantBean) {
        D.e("===dbytype==" + getItem().dbhType);
        if (plantBean.value.equals(ConstantParams.dbh)) {
            layout.setDefaultData(FUtil.$_zero_2_null(getItem().minDbh));
            layout.setDefaultDataMax(FUtil.$_zero_2_null(getItem().maxDbh));
            layout.setDefaultSize(getItem().dbhType);

//            dbhType
        }
        if (plantBean.value.equals(ConstantParams.diameter)) {
            layout.setDefaultData(FUtil.$_zero_2_null(getItem().minDiameter));
            layout.setDefaultDataMax(FUtil.$_zero_2_null(getItem().maxDiameter));
            layout.setDefaultSize(getItem().diameterType);
//            dbhType
        }
//        if (plantBean.value.equals(ConstantParams.diameter)) {
//            layout.setDefaultData(FUtil.$_zero_2_null(getItem().diameter + ""));
//        }
        if (plantBean.value.equals(ConstantParams.height)) {
            layout.setDefaultData(FUtil.$_zero_2_null(getItem().minHeight));
            layout.setDefaultDataMax(FUtil.$_zero_2_null(getItem().maxHeight));
        }
        if (plantBean.value.equals(ConstantParams.crown)) {
            layout.setDefaultData(FUtil.$_zero_2_null(getItem().minCrown ));
            layout.setDefaultDataMax(FUtil.$_zero_2_null(getItem().maxCrown ));
        }

    }


    int a = 0;
    int errorNum = 0;

    public void upLoadImgs() {
        if (this.measureGridView.getAdapter().getDataList().size() == 0) {
            ToastUtil.showLongToast("请上传图片");
            return;
        }
        showLoading();
        setLoadCancle(false);
        //上传成功的结果保存在这里 网路路径集合
        listPicsOnline = new ArrayList<>();
        ArrayList<Pic> piclistLocal = this.measureGridView.getAdapter().getDataList();//本地路径集合

        int size = measureGridView.getAdapter().getDataList().size();
        new SaveSeedlingPresenter(mActivity).upLoad(this.measureGridView.getAdapter().getDataList(), new ResultCallBack<Pic>() {
            @Override
            public void onSuccess(Pic pic) {
                ++a;
                if (!TextUtils.isEmpty(pic.getUrl())) {
                    listPicsOnline.add(pic);
                } else {
                    errorNum++;
                    ToastUtil.showLongToast("有图片损坏，您可以修改后重新上传！");
                }
                UpdateLoading("正在上传第 (" + a + "/" + size + "张) 图片");


                if (listPicsOnline.size() + errorNum == piclistLocal.size()) {
//                        uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);
//                        save();//如果没有图片，直接上传数据
                    UpdateLoading("上传成功");
                    save();
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                UpdateLoading("上传失败：" + strMsg);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        hindLoading();
                    }
                }, 2000);
            }
        });

    }


    public void save() {

        uploadBean = getUploadBean();//获取上传  对象

        D.e("上传对象\n" + uploadBean.toString());


        setLoadCancle(false);
        new PurchaseDeatilP(new ResultCallBack<PurchaseItemBean_new>() {
            @Override
            public void onSuccess(PurchaseItemBean_new itemBean_new) {
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
                .quoteCommit(mActivity, uploadBean);
    }

    public PurchaseDetailActivity.uploadBean getUploadBean() {
        uploadBean.plantType = plantType;
        uploadBean.purchaseId = getItem().purchaseId;
        uploadBean.purchaseItemId = getItem().purchaseItemId;
        uploadBean.id = getItem().id;
        uploadBean.cityCode = cityCode;
        uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);
        uploadBean.remarks = getText(getView(R.id.et_purchase_remark));//备注
        for (int i = 0; i < autoLayouts.size(); i++) {
            PurchaseAutoAddLinearLayout.PlantBean plantBean = (PurchaseAutoAddLinearLayout.PlantBean) autoLayouts.get(i).getTag();
            String str = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();
            if (plantBean.value.equals(ConstantParams.price)) {
                uploadBean.price = str;
            } else if (plantBean.value.equals(ConstantParams.count)) {
                uploadBean.count = str;
            } else if (plantBean.value.equals(ConstantParams.dbh)) {
                uploadBean.dbh = str;
                uploadBean.dbhType = autoLayouts.get(i).getSelect_size();
            } else if (plantBean.value.equals(ConstantParams.diameter)) {
                uploadBean.diameter = str;
                uploadBean.diameter = autoLayouts.get(i).getSelect_size();
            } else if (plantBean.value.equals(ConstantParams.height)) {
                uploadBean.height = str;
            } else if (plantBean.value.equals(ConstantParams.crown)) {
                uploadBean.crown = str;
            } else if (plantBean.value.equals(ConstantParams.offbarHeight)) {
                uploadBean.offbarHeight = str;
            } else if (plantBean.value.equals(ConstantParams.prePrice)) {
                uploadBean.prePrice = str;
            } else {

            }
        }


        return uploadBean;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {
            try {
                measureGridView.addImageItem(flowerInfoPhotoPath);
                measureGridView.getAdapter().Faild2Gone(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        flowerInfoPhotoPath, flowerInfoPhotoPath, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            // Uri.parse("file://" + flowerInfoPhotoPath)));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addPicUrls(ArrayList<Pic> resultPathList) {
        measureGridView.getAdapter().addItems(resultPathList);
        measureGridView.getAdapter().Faild2Gone(true);
//        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
        D.e("=========addPicUrls=========" + resultPathList.toString());
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public String setTitle() {
        return getBean().getData().getItem().name + "采购详情";
//        return "修改报价";
    }

    @Override
    public int bindLayoutID() {
//      PurchaseDetailActivityChange  include_bottom_pur_detail
        return R.layout.activity_detail_change;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}

/**
 * 本页布局接口
 */
interface ChangeView {
    //初始化直购
    void initDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen);

    //初始化代购
    void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen);

}
