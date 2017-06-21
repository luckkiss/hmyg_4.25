package com.hldj.hmyg.buyer.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.Purchase.PurchaseAutoAddLinearLayout;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListItemDetail;
import com.hldj.hmyg.saler.UpdataImageActivity_bak;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 本届面提供 代购  采购详情
 */
@SuppressLint("NewApi")
public class PurchaseDetailActivity extends PurchaseDetailActivityBase {


    private String purchaseId;//采购id

    public String getPurchaseId() {
        return purchaseId;
    }

    private boolean isFinish = false;

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout_id = R.layout.purchase_detail_activity;//这个界面的布局
//      initGv();
        getDatas();

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.purchase_detail_activity);
    }


    @Override
    public void getDatas() {
        showLoading();
        new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                initDatas(saveSeedingGsonBean);
                hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                hindLoading();
            }
        }).getDatas(getIntent().getExtras().get(GOOD_ID).toString());//请求数据  进行排版
    }


    SellerQuoteJsonBean sellerQuoteJsonBean;// 如果已经报价  返回的报价数据

    @Override
    public void initItem(ItemBean item) {
        super.initItem(item);//返回给父类实现

        sellerQuoteJsonBean = item.sellerQuoteJson;


        //剥离 有效字段
        mProjectType = item.plantType;//直购 代沟
        mIsQuoted = item.isQuoted;//是否报过价
        purchaseId = item.purchaseId;//采购id


    }


    @Override
    protected void initDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        /**
         * 我是直购
         */
        if (mIsQuoted)//已经报价
        {
            D.e("=====已经报价=====");
            initRceycle(false); // 报价成功会获取 sellerQuoteJson  来显示recycle 列表
            findViewById(R.id.recycle_pur_one).setVisibility(View.VISIBLE);
            findViewById(R.id.include_bottom_pur_detail).setVisibility(View.GONE);

        } else//暂未报价
        {
            D.e("=====暂未报价=====");  //"isQuoted": false,
            initTypeListDirect(typeListBeen);
            findViewById(R.id.recycle_pur_one).setVisibility(View.GONE);
            findViewById(R.id.include_bottom_pur_detail).setVisibility(View.VISIBLE);
            //初始化后 设置点击事件
            getViewHolder_pur().tv_purchase_commit.setOnClickListener(commitListener);

            if (!MainActivity.province_loc.equals("")) {
                getViewHolder_pur().tv_purchase_city_name.setText(MainActivity.province_loc + " " + MainActivity.city_loc);
            } else {
                getViewHolder_pur().tv_purchase_city_name.setText("未选择");
            }
            getViewHolder_pur().tv_purchase_city_name.setOnClickListener(showCity);


        }
        getViewHolder_pur().tv_purchase_add_pic.setOnClickListener(choosePic);


    }


    /**
     * 直购只要添加一行  价格
     *
     * @param typeListBeen
     */
    private void initTypeListDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        autoLayouts.clear();
        getViewHolder_pur().ll_purc_auto_add.removeAllViews();//动态添加前先删除所有
        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "price", true));
        autoLayouts.add(layout);
        getViewHolder_pur().ll_purc_auto_add.addView(layout);
        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("数量", "count", true));
        autoLayouts.add(layout);
        View line = new View(mActivity);
        getViewHolder_pur().ll_purc_auto_add.addView(layout);


        //不需要地址栏

//        ViewGroup viewGroup = (ViewGroup) getViewHolder_pur().tv_purchase_city_name.getParent();
//        viewGroup.setVisibility(View.GONE);

    }


    @Override
    protected void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        /**
         * 我是代购
         */
        if (mIsQuoted)//已经报价
        {

            D.e("=====已经报价=====");
            initRceycle(true); // 报价成功会获取 sellerQuoteJson  来显示recycle 列表
            findViewById(R.id.recycle_pur_one).setVisibility(View.VISIBLE);
            findViewById(R.id.include_bottom_pur_detail).setVisibility(View.GONE);

        } else//暂未报价
        {
            D.e("=====暂未报价=====");  //"isQuoted": false,
            initTypeListProtocol(typeListBeen);
            findViewById(R.id.recycle_pur_one).setVisibility(View.GONE);
            findViewById(R.id.include_bottom_pur_detail).setVisibility(View.VISIBLE);


            //初始化后 设置点击事件
            getViewHolder_pur().tv_purchase_commit.setOnClickListener(commitListener);
            getViewHolder_pur().tv_purchase_city_name.setOnClickListener(showCity);

        }
//        getViewHolder_pur().tv_purchase_add_pic.setOnClickListener(showCity);
        getViewHolder_pur().tv_purchase_add_pic.setOnClickListener(choosePic);

    }


    @Override
    public void addPicUrls(ArrayList<Pic> resultPathList) {
//        measureGridView.getAdapter().addItems(resultPathList);
//        measureGridView.getAdapter().addItems(resultPathList);
//        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
        D.e("=========addPicUrls=========");
    }


    PurchaseAutoAddLinearLayout layout = null;
    List<PurchaseAutoAddLinearLayout> autoLayouts = new ArrayList<>();

    //发布成功后   刷新界面
    //讲数据进行  动态布局
    private void initTypeListProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        if (typeListBeen == null) {
            D.e("=====typeListBeen=====为空");
            return;
        }
        autoLayouts.clear();
//      viewHolder_pur.ll_purc_auto_add.addView(new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "dbh", true)));
        getViewHolder_pur().ll_purc_auto_add.removeAllViews();//动态添加前先删除所有
        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "price", true));
        autoLayouts.add(layout);
        getViewHolder_pur().ll_purc_auto_add.addView(layout);

        for (int i = 0; i < typeListBeen.size(); i++) {


            if (firstSeedlingTypeId.equals(typeListBeen.get(i).getId())) {
                for (int j = 0; j < typeListBeen.get(i).getParamsList().size(); j++) {
                    //创建 一个plantBean
                    PurchaseAutoAddLinearLayout.PlantBean plantBean = new PurchaseAutoAddLinearLayout.PlantBean(typeListBeen.get(i).getParamsList().get(j).getName(),
                            typeListBeen.get(i).getParamsList().get(j).getValue(),
                            typeListBeen.get(i).getParamsList().get(j).isRequired()
                    );
                    //给plant 赋值
                    layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(plantBean);
                    //保存当前的 viw 到list 列表  上传数据时需要获取其中的 内容
                    autoLayouts.add(layout);
                    getViewHolder_pur().ll_purc_auto_add.addView(layout);
                }


            }
            // "name": "地径",
            //"value": "diameter",
            //"required": true
        }


    }

    public CoreRecyclerView recyclerView;


    //请求一个报价列表
    public void initRceycle(boolean direce) {

        recyclerView = (CoreRecyclerView) findViewById(R.id.recycle_pur_one);
        recyclerView.initView(this)
                .init(new BaseQuickAdapter<SellerQuoteJsonBean, BaseViewHolder>(R.layout.item_quote_dir_po) {
                    @Override
                    protected void convert(BaseViewHolder helper, SellerQuoteJsonBean item) {
//                        helper.setText(R.id.tv_quote_item_sellerName, strFilter(item.sellerName).equals("") ? strFilter(item.sellerPhone) : strFilter(item.sellerName));//报价人

                        helper.setText(R.id.tv_quote_item_price, strFilter(item.price + ""));//价格
                        helper.setText(R.id.tv_quote_item_plantTypeName, strFilter(item.plantTypeName));//种植类型
                        helper.setText(R.id.tv_quote_item_declare, strFilter(item.remarks));//种植类型

//                        helper.setText(R.id.tv_show_is_quote, strFilter("已报价"));//种植类型
//                        helper.setText(R.id.tv_show_is_quote, strFilter("已报价"));//种植类型

                        ManagerQuoteListItemDetail.setStatus(helper.getView(R.id.tv_show_is_quote), getStatus());

                        helper.setText(R.id.tv_quote_item_cityName, strFilter(item.cityName));//苗源地址
                        if (direce) {//代购
                            helper.setText(R.id.tv_quote_item_specText, strFilter(item.specText));//要求规格

                        } else {//直购  参数比较少，需要隐藏部分
                            helper.setText(R.id.tv_quote_item_left, "数        量:");
                            helper.setText(R.id.tv_quote_item_specText, item.count + "");
//                          helper.setParentVisible(R.id.tv_quote_item_specText, false); // 规格 -》 数量
//                            helper.setParentVisible(R.id.tv_quote_item_cityName, false); // 地址继续显示
                        }
                        SuperTextView textView = helper.getView(R.id.tv_quote_item_photo_num);


                        setImgCounts(mActivity,textView, item.imagesJson);

                        if (getStatus().equals("")) {//""表示  已经报价  但是结果还没出来   允许删除
                            helper.setVisible(R.id.tv_delete_item, true);

                            helper.addOnClickListener(R.id.tv_delete_item, v -> {

                                new AlertDialog(PurchaseDetailActivity.this)
                                        .builder()
                                        .setTitle("提示")
                                        .setPositiveButton("确定", v1 -> {
                                            showLoading();
                                            new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
                                                @Override
                                                public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {

                                                    ToastUtil.showShortToast("删除成功");
                                                    //删除该项目 并且刷新界面
                                                    recyclerView.getAdapter().remove(0);
                                                    recyclerView.getAdapter().notifyItemRemoved(0);
                                                    getDatas();
                                                    setResult(-1);
                                                    hindLoading();
                                                    onDeleteFinish(true);
                                                }

                                                @Override
                                                public void onFailure(Throwable t, int errorNo, String strMsg) {
                                                    onDeleteFinish(false);
                                                    hindLoading();
                                                }
                                            })
                                                    .quoteDdel(item.id);


                                        })
                                        .setNegativeButton("取消", v2 -> {
                                        }).show();

                                //删除接口


                            });

                        } else {
                            helper.setVisible(R.id.tv_delete_item, false);
                        }


                    }
                });


        recyclerView.getAdapter().addData(sellerQuoteJsonBean);


    }

    public static void setImgCounts(Activity activity, SuperTextView textView, List<ImagesJsonBean> imagesJson) {
        if (imagesJson.size() != 0) {
            textView.setText(strFilter("有" + imagesJson.size() + "张图片"));//有多少张图片
        } else {
            textView.setShowState(false);
            textView.setText(strFilter("未上传图片"));//有多少张图片
        }
        if (imagesJson.size() != 0) {
            textView.setOnClickListener(view -> {
                //穿list pic 集合到新的activity 显示 所有的图片
                GalleryImageActivity.startGalleryImageActivity(activity, 0, getPicList(imagesJson));
            });

        }
    }

    public void onDeleteFinish(boolean isSucceed) {
        D.e("==========删除成功失败===========" + isSucceed);
    }

    //解析 数组数据
    public static ArrayList<Pic> getPicList(List<ImagesJsonBean> imagesJson) {

        ArrayList pics = new ArrayList<Pic>();
        for (int i = 0; i < imagesJson.size(); i++) {
            pics.add(new Pic(imagesJson.get(i).id, false, imagesJson.get(i).ossMediumImagePath, i));
        }
        return pics;
    }


    PurchaseAutoAddLinearLayout.PlantBean plantBean;

    uploadBean uploadBean = new uploadBean();


    View.OnClickListener commitListener = v -> {

        //点击上传   数据   讲采购信息全部放入

        PurchaseDetailActivity.this.list.clear();

        for (int i = 0; i < autoLayouts.size(); i++) {
            String str = autoLayouts.get(i).getTag().toString();

            //胸径就打印出选项  选了哪个
            plantBean = (PurchaseAutoAddLinearLayout.PlantBean) autoLayouts.get(i).getTag();
            if (plantBean.value.equals("dbh")) {
                String size = autoLayouts.get(i).getSelect_size();
                D.e("===size==" + size);
                uploadBean.dbhType = size;//胸径类型

            }
            if (plantBean.value.equals("diameter")) {
                String size = autoLayouts.get(i).getSelect_size();
                uploadBean.diameter = size;//地径类型
            }
            /**
             * put("dbhType", autoAddRelative_rd.getDiameterType());
             params.put("minDbh", viewHolder_rd.et_auto_add_min.getText().toString());//最小地径
             params.put("maxDbh", viewHolder_rd.et_auto_add_max.getText().toString());//最大地径
             } else {
             params.put("diameterType"
             */
            //                @property (copy, nonatomic) NSString *diameterType;//地径类型
//                @property (copy, nonatomic) NSString *dbhType;//胸径类型

            String content = "";
            boolean isOk = true;
            switch (plantBean.value) {
                case ConstantParams.price://价格
                    content = uploadBean.price = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.diameter://地径
                    content = uploadBean.diameter = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.dbh://胸径
                    content = uploadBean.dbh = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.height://高度
                    content = uploadBean.height = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.crown://冠幅
                    content = uploadBean.crown = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.offbarHeight://脱杆高
                    content = uploadBean.offbarHeight = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case ConstantParams.length://长度
                    content = uploadBean.length = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;
                case "count"://长度
                    content = uploadBean.count = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数
                    isOk = submit(plantBean.name, uploadBean.price, plantBean.required);
                    break;

            }

            if (!isOk) {
                return;
            }

//            Map map = new HashMap();
//            map.put(plantBean.value, autoLayouts.get(i).getViewHolder().et_params_03.getText());
//            PurchaseDetailActivity.this.list.add(map);
            D.e("=========autoLayouts============" + str);
            D.e("=========value============" + autoLayouts.get(i).getViewHolder().et_params_03.getText());


            uploadBean.remarks = getViewHolder_pur().et_purchase_remark.getText() + "";

//            uploadBean.price = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();//第三个参数


        }

        D.e("===city==" + getViewHolder_pur().tv_purchase_city_name.getText());

//        int size = PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList().size();
//        if (PurchaseDetailActivity.this.measureGridView != null || size != 0) {
//            D.e("======图片的地址数量====" + size);
//            D.e("======图片的地址====" + PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList().get(0).getUrl());
//            uploadBean.imagesData = PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList().get(0).getUrl();
//        }
        uploadBean.purchaseId = item.purchaseId;
        uploadBean.purchaseItemId = item.id;
        uploadBean.plantType = getPlantType();

        D.e("============上传报价===================");


        save();//如果没有图片，直接上传数据

        //如果是代购  就必须上传一张图片   参数多的那个
//        ArrayList<Pic> piclistLocal = PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList();//本地路径集合
//        List<Pic> listPicsOnline = new ArrayList<>();//上传成功的结果保存在这里 网路路径集合

//        if (piclistLocal.size() != 0) {//有图片，则先上传图片
//            //接口上传图片
//            new SaveSeedlingPresenter().upLoad(PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList(), new ResultCallBack<Pic>() {
//                @Override
//                public void onSuccess(Pic pic) {
//                    listPicsOnline.add(pic);
//                    if (listPicsOnline.size() == piclistLocal.size()) {
//                        uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);
//                        save();//如果没有图片，直接上传数据
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                }
//            });
//        } else {
//            save();//如果没有图片，直接上传数据
//        }


    };


    public void save() {
        new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                getDatas();
                setResult(1);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        })
                .quoteCommit(PurchaseDetailActivity.this, uploadBean)
        ;
    }


    List<Map<String, Integer>> list = new ArrayList<>();


    public static class uploadBean {
        //        String sellerQuoteJson_id = "";//空 提交   非空 取消某人的 发布消息
        public String cityCode = "";
        public String price = "";
        public String diameter = "";
        public String dbh = "";
        public String height = "";
        public String crown = "";
        public String offbarHeight = "";
        public String length = "";
        public String diameterType = "";
        public String dbhType = "";
        public String remarks = "";//备注
        public String imagesData = "";//备注

        public String count = "";

        public String purchaseId = "";//
        public String purchaseItemId = "";//

        public String id = "";
        public String plantType = "";
    }

    /**
     * @property (strong, nonatomic) NSNumber *price;//苗木单价
     * @property (strong, nonatomic) NSNumber *diameter;//地径
     * @property (strong, nonatomic) NSNumber *dbh;//胸径
     * @property (strong, nonatomic) NSNumber *height;//高度
     * @property (strong, nonatomic) NSNumber *crown;//冠幅
     * @property (strong, nonatomic) NSNumber *offbarHeight;//脱杆高
     * @property (strong, nonatomic) NSNumber *length;//长度
     * @property (copy, nonatomic) NSString *diameterType;//地径类型
     * @property (copy, nonatomic) NSString *dbhType;//胸径类型
     */


    ArrayList<Pic> listPicsOnline = new ArrayList<>();//上传成功的结果保存在这里 网路路径集合
    View.OnClickListener choosePic = v -> {
        Intent toUpdataImageActivity = new Intent(
                PurchaseDetailActivity.this, UpdataImageActivity_bak.class);
        Bundle bundleObject = new Bundle();
        final PicSerializableMaplist myMap = new PicSerializableMaplist();
        myMap.setMaplist(PurchaseDetailActivity.this.listPicsOnline);
        bundleObject.putSerializable("urlPaths", myMap);
        toUpdataImageActivity.putExtras(bundleObject);
        startActivityForResult(toUpdataImageActivity, 1);


    };


    View.OnClickListener showCity = v -> {
        CityWheelDialogF.instance()
                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                    @Override
                    public void onCitySelect(CityGsonBean.ChildBeans child) {
                        D.e("===========child==============" + child.toString());
                        getViewHolder_pur().tv_purchase_city_name.setText(child.fullName);
                        uploadBean.cityCode = child.cityCode;
                    }

                    @Override
                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                    }
                })
                .show(getSupportFragmentManager(), PurchaseDetailActivity.this.getClass().getName());
    };

//
//    private ArrayList<Pic> arrayList = new ArrayList<>();
//    private String flowerInfoPhotoPath = "";
//    MeasureGridView measureGridView;
//
//    public void initGv() {
//
//
//        measureGridView = (MeasureGridView) findViewById(R.id.gv_show_images);
//
//        arrayList.add(new Pic("hellow", true, MeasureGridView.usrl, 1));
////            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));
//
//        measureGridView.init(this, arrayList, getViewHolder_pur().ll_mainView_bottom, new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
//            @Override
//            public void onTakePic() {
//                D.e("===========onTakePic=============");
//                if (TakePhotoUtil.toTakePic(PurchaseDetailActivity.this))//检查 存储空间
//                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(PurchaseDetailActivity.this, TakePhotoUtil.TO_TAKE_PIC);//照相
//            }
//
//            @Override
//            public void onChoosePic() {
//                D.e("===========onChoosePic=============");
//                //通过本界面 addPicUrls 方法回调
//                TakePhotoUtil.toChoosePic(PurchaseDetailActivity.this, measureGridView.getAdapter());
//            }
//
//            @Override
//            public void onCancle() {
//                D.e("===========onCancle=============");
//            }
//        });
//
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {


            //接受 上传图片界面传过来的list《pic》
//            try {
//                measureGridView.addImageItem(flowerInfoPhotoPath);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 其次把文件插入到系统图库
//            try {
//                MediaStore.Images.Media.insertImage(getContentResolver(),
//                        flowerInfoPhotoPath, flowerInfoPhotoPath, null);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            // 最后通知图库更新
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            // Uri.parse("file://" + flowerInfoPhotoPath)));
        } else if (resultCode == 1) {

        } else if (resultCode == 2) {

        } else if (resultCode == 3) {

        } else if (resultCode == 4) {

        } else if (resultCode == 5) {
            listPicsOnline.clear();

            data.getExtras().get("urlPaths");

            PicSerializableMaplist myMap = (PicSerializableMaplist) data.getExtras().get("urlPaths");

            listPicsOnline.addAll(myMap.getMaplist());

            int size = listPicsOnline.size();

            getViewHolder_pur().tv_purchase_add_pic.setText("已经选择了" + size + "张图片");
            //设置 多少张图片
            uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * final PicSerializableMaplist myMap = new PicSerializableMaplist();
     * myMap.setMaplist(listPicsOnline);
     * bundleObject.putSerializable("urlPaths", myMap);
     */


    public boolean submit(String resome, String content, boolean isNeed) {
        if (!isNeed) {
            return true;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShortToast("请输入：" + resome);
            return false;
        } else {
            return true;
        }
    }

    public static void start2Activity(Activity activity, String good_id) {
        Intent intent = new Intent(activity, PurchaseDetailActivity.class);
        intent.putExtra(GOOD_ID, good_id);
        activity.startActivityForResult(intent, 100);
    }


//    @Override
//    public void getDatas() {
//        super.getDatas();
//    }

//    public View.OnClickListener clic2Del = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            {
//
//                showLoading();
//                new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
//                    @Override
//                    public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
//
//                        ToastUtil.showShortToast("删除成功");
//                        //删除该项目 并且刷新界面
//                        recyclerView.getAdapter().remove(0);
//                        recyclerView.getAdapter().notifyItemRemoved(0);
//                        getDatas();
//                        setResult(-1);
//                        hindLoading();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        hindLoading();
//                    }
//                })
//                        .quoteDdel(purchaseId);
//
//            }
//        }
//    };
}
