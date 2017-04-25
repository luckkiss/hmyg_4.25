//package com.hldj.hmyg.saler;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hldj.hmyg.CallBack.ResultCallBack;
//import com.hldj.hmyg.R;
//import com.hldj.hmyg.V.SaveSeedlingV;
//import com.hldj.hmyg.application.Data;
//import com.hldj.hmyg.application.MyApplication;
//import com.hldj.hmyg.bean.Pic;
//import com.hldj.hmyg.bean.SaveSeedingGsonBean;
//import com.hldj.hmyg.bean.SimpleGsonBean;
//import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
//import com.hldj.hmyg.util.D;
//import com.hldj.hmyg.util.GsonUtil;
//import com.hldj.hmyg.util.MyUtil;
//import com.hldj.hmyg.util.SPUtil;
//import com.hldj.hmyg.util.TakePhotoUtil;
//import com.hldj.hmyg.widget.AutoAddRelative;
//import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
//import com.hy.utils.GetServerUrl;
//import com.hy.utils.ToastUtil;
//import com.kaopiz.kprogresshud.KProgressHUD;
//import com.yangfuhai.asimplecachedemo.lib.ACache;
//import com.zhy.view.flowlayout.TagFlowLayout;
//import com.zzy.common.widget.MeasureGridView;
//
//import net.tsz.afinal.FinalHttp;
//import net.tsz.afinal.http.AjaxCallBack;
//import net.tsz.afinal.http.AjaxParams;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
//
//
///**
// * 发布苗木资源
// */
//public class SaveSeedlingActivity_bak extends NeedSwipeBackActivity implements SaveSeedlingV {
//    private ACache mCache;
//    public static SaveSeedlingActivity_bak instance;
//
//    SaveSeedlingPresenter saveSeedlingPresenter = new SaveSeedlingPresenter();
//    KProgressHUD hud_numHud; // 上传时显示的等待框
//
//    ViewHolder viewHolder;//控件管理类
//    private ArrayList<Pic> urlPaths = new ArrayList<>();
//
//    public SaveSeedingGsonBean saveSeedingGsonBean;
//    private ArrayList<Pic> arrayList2Adapter = new ArrayList(); // 传入 适配器的图片列表
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_save_seedling);
//
//
////step 1
//        {
//            //初始化 本届面固定 写死的界面
//            viewHolder = new ViewHolder();
//            initListener(viewHolder);
//            mCache = ACache.get(this);
//            hud_numHud = KProgressHUD.create(SaveSeedlingActivity_bak.this)
//                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
//                    .setLabel("上传中，请等待...").setMaxProgress(100)
//                    .setCancellable(true);
//            instance = this;
//
//
//            //暂存草稿箱
//            viewHolder.iv_ready_save.setOnClickListener(v -> {
//                SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = new SaveSeedingGsonBean.DataBean.SeedlingBean();
//                //step 1  图片如何插入
//                if (viewHolder.publish_flower_info_gv.getAdapter().getDataList().size() != 0) {//
//
//                    List<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean> list_imgs
//                            = new ArrayList<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean>();
//                    for (int i = 0; i < viewHolder.publish_flower_info_gv.getAdapter().getDataList().size(); i++) {
//                        SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean
//                                imagesJsonBean = new SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean().setLocal_url("");
//                        list_imgs.add(imagesJsonBean);
//                    }
//                    seedlingBean.setImagesJson(list_imgs);
//                }
//
//
//                //step 2 中间动态数据 保存
//                seedlingBean.setFirstSeedlingTypeId(tag_ID);
//                seedlingBean.setPlantType(tag_ID1);
//                seedlingBean.setName(viewHolder_top.tv_auto_add_name.getText().toString());
//
//                if (viewHolder_rd != null) {
//                    if (autoAddRelative_rd.getMTag().equals("dbh")) {
//                        seedlingBean.setMaxDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
//                        seedlingBean.setMinDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
//                        seedlingBean.setDbhType(autoAddRelative_rd.getDiameterType());
//                    } else {
//
//                        seedlingBean.setMaxDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
//                        seedlingBean.setMinDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
//                        seedlingBean.setDiameterType(autoAddRelative_rd.getDiameterType());
//                    }
//                }
//
//
//                //step 3  底部固定布局 数据保存
//
//
//                SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = viewHolder.bottom_ll.getUpLoadDatas();
//
//                if (MyUtil.formateString2Int(upLoadDatas.getPrice_min()) != 0) {
//                    seedlingBean.setMinPrice(MyUtil.formateString2Int(upLoadDatas.getPrice_min()));
//                }
//                if (MyUtil.formateString2Int(upLoadDatas.getPrice_max()) != 0) {
//                    seedlingBean.setMaxPrice(MyUtil.formateString2Int(upLoadDatas.getPrice_max()));
//                }
//                seedlingBean.setIsNego(upLoadDatas.isMeet());
//                if (MyUtil.formateString2Int(upLoadDatas.getRepertory_num()) != 0) {
//                    seedlingBean.setCount(MyUtil.formateString2Int(upLoadDatas.getRepertory_num()));
//                }
////                seedling.getUnitTypeName();
//                seedlingBean.setUnitTypeName(upLoadDatas.getUnit());//plant 需要根据  tag  来返回 name 来显示
//                seedlingBean.setValidity(MyUtil.formateString2Int(upLoadDatas.getValidity()));
//                seedlingBean.setRemarks(upLoadDatas.getRemark());
//
//
//                SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nurseryJsonBean
//                        = new SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean();
//
//                //地址对象
//                AdressListActivity.Address address = upLoadDatas.address;
//                seedlingBean.setNurseryId(address.addressId);
//                nurseryJsonBean.setPhone(address.contactPhone);
//                nurseryJsonBean.setRealName(address.contactName);
//                seedlingBean.setCityName(address.cityName);
//                seedlingBean.setDefault(address.isDefault);
//                seedlingBean.setNurseryJson(nurseryJsonBean);
//
//                SaveSeedlingActivity_bak.this.saveSeedingGsonBean.getData().setSeedling(seedlingBean);
//                String json = GsonUtil.Bean2Json(SaveSeedlingActivity_bak.this.saveSeedingGsonBean);
//
//
//                D.e("==json==" + json);
//                SPUtil.put(MyApplication.getInstance(), "save_sp", json);
//
//
///**
// * address.addressId = seedling.getNurseryId();
// address.contactPhone = seedling.getNurseryJson().getPhone();
// address.contactName = seedling.getNurseryJson().getRealName();
// address.cityName = seedling.getNurseryJson().getCityName();
// address.isDefault = seedling.isDefault();
// */
//
//
//            });
//
//
//        }
////step 2
//        {
//            initGvTop();
//        }
////step 2.5
//        {
//            //获取其他界面传过来的对象，，，用于修改信息并重新提交
//            Bundle bundle = getIntent().getExtras();
//            if (bundle != null) {
//                saveSeedingGsonBean = (SaveSeedingGsonBean) bundle.getSerializable("saveSeedingGsonBean");
//
//                initExtra(saveSeedingGsonBean);
//
//                return; //直接通过传过来的数据进行 绘制界面  不需要再 请求接口数据进行初始化
//
//            }
//
//
//            //初始化草稿箱
//            if (!SPUtil.get(this, "save_sp", "").equals("")) {
//                saveSeedingGsonBean = GsonUtil.formateJson2Bean(SPUtil.get(this, "save_sp", "").toString(), SaveSeedingGsonBean.class);
//                initExtra(saveSeedingGsonBean);
//                return;
//            }
//
//        }
//
////step 3
//        {
//            getAllData();//获取数据进行动态布局
//        }
//
//
//    }
//
//
//    //传入初始化 的图片资源  初始化顶部  图片列表控件
//    private void initGvTop() {
//        arrayList2Adapter.clear();
////            arrayList.add(new Pic("hellow", true, MeasureGridView.usrl, 1));
////            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));
//
//        viewHolder.publish_flower_info_gv.init(this, arrayList2Adapter, viewHolder, new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
//            @Override
//            public void onTakePic() {
//                D.e("===========onTakePic=============");
//                if (TakePhotoUtil.toTakePic(SaveSeedlingActivity_bak.this))//检查 存储空间
//                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(SaveSeedlingActivity_bak.this, TakePhotoUtil.TO_TAKE_PIC);//照相
//            }
//
//            @Override
//            public void onChoosePic() {
//                D.e("===========onChoosePic=============");
//                //通过本界面 addPicUrls 方法回调
//                TakePhotoUtil.toChoosePic(SaveSeedlingActivity_bak.this, viewHolder.publish_flower_info_gv.getAdapter());
//            }
//
//            @Override
//            public void onCancle() {
//                D.e("===========onCancle=============");
//            }
//        });
//
//    }
//
//    private void initExtra(SaveSeedingGsonBean saveSeedingGsonBean) {
//
//        List<SaveSeedingGsonBean.DataBean.SeedlingBean.ImagesJsonBean> imagesJsonBeans = saveSeedingGsonBean.getData().getSeedling().getImagesJson();
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
//        SaveSeedingGsonBean.DataBean.SeedlingBean seedling = saveSeedingGsonBean.getData().getSeedling();
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
//        viewHolder_top.tv_auto_add_name.setText(seedling.getName());
//        if (viewHolder_rd != null) {
//
//
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
//        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();
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
//        AdressListActivity.Address address = new AdressListActivity.Address();
//        address.addressId = seedling.getNurseryId();
//        address.contactPhone = seedling.getNurseryJson().getPhone();
//        address.contactName = seedling.getNurseryJson().getRealName();
//        address.cityName = seedling.getNurseryJson().getCityName();
//        address.isDefault = seedling.isDefault();
//
//        upLoadDatas.address = address;
//        upLoadDatas.validity = seedlingBean.getValidity() + "天";
//        upLoadDatas.remark = seedlingBean.getRemarks();
//
//
//        viewHolder.bottom_ll.setUpLoadDatas(upLoadDatas);
//
//
//    }
//
//
//    private String flowerInfoPhotoPath = "";
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {
//            try {
//                viewHolder.publish_flower_info_gv.addImageItem(flowerInfoPhotoPath);
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
//            // 最后通知图库更新
//            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//            // Uri.parse("file://" + flowerInfoPhotoPath)));
//        } else if (resultCode == 1) {
//
//        } else if (resultCode == 2) {
//
//        } else if (resultCode == 3) {
//
//        } else if (resultCode == 4) {
//
//        } else if (resultCode == 5) {
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void getAllData(SaveSeedingGsonBean saveSeedingGsonBean) {
//
//    }
//
//
//    private class RefreshHandler extends Handler {
//
//        public RefreshHandler(Looper looper) {
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case TakePhotoUtil.LOAD_PIC_FAILURE:
//                    ToastUtil.showShortToast(R.string.image_load_failed);
//                    break;
//                case TakePhotoUtil.ADD_NEW_PIC:
//                    // adapter.notifyDataSetChanged();
//                    viewHolder.publish_flower_info_gv.getAdapter().notify(urlPaths);
//
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//
//    private void getAllData() {
//
////       获取所有数据  本页
//        SaveSeedlingPresenter.getAllDatas(new ResultCallBack<SaveSeedingGsonBean>() {
//            @Override
//            public void onSuccess(final SaveSeedingGsonBean saveSeedingGsonBean) {
//
//                SaveSeedlingActivity_bak.this.saveSeedingGsonBean = saveSeedingGsonBean;
//
//                initAutoLayout(saveSeedingGsonBean.getData().getTypeList());
//
//                initAutoLayout2(saveSeedingGsonBean.getData().getPlantTypeList());
//
//                //传参数的时候   增加 一个 自动添加布局，不需要点击  （乔木  灌木  庄敬  地被  苏铁  。。。。。。）
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                D.e("============数据加载失败===========");
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public void initAutoLayout(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
//        D.e("===============");
//        for (int i = 0; i < typeListBeen.size(); i++) {
//            if (tag_ID.equals(typeListBeen.get(i).getId()) && !TextUtils.isEmpty(tag_ID)) {
//                index = i;
//            }
//        }
//        //设置默认
//        //动态添加标签
//        SaveSeedlingPresenter.initAutoLayout(viewHolder.id_flowlayout, typeListBeen, index, SaveSeedlingActivity_bak.this, (view, position, parent) -> {
//            tag_ID = typeListBeen.get(position).getId();
//            typeListBeen.get(position).getName();
//            paramsListBean = typeListBeen.get(position).getParamsList();
//            D.e("==tag=点击事件=" + paramsListBean.toString());
//            //根据参数来 配置布局
//            addParamViews(paramsListBean);
//
//
//            //
//            viewHolder.bottom_ll.getHolder().tv_save_seeding_useful.setText(typeListBeen.get(position).getDefaultValidity() + "");
//
//            return true;
//        });
//
//        if (index != -1)
//            addParamViews(typeListBeen.get(index).getParamsList());
//    }
//
//    /**
//     * 初始化 旧数据
//     *
//     * @param typeListBeen
//     * @param type_id
//     */
////    public void initAutoLayout(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen, String type_id) {
////
////        //设置默认
////        //动态添加标签
////        SaveSeedlingPresenter.initAutoLayout(viewHolder.id_flowlayout, typeListBeen, index, SaveSeedlingActivity.this, (view, position, parent) -> {
////            tag_ID = typeListBeen.get(position).getId();
////            typeListBeen.get(position).getName();
////            paramsListBean = typeListBeen.get(position).getParamsList();
////            D.e("==tag=点击事件=" + paramsListBean.toString());
////            //根据参数来 配置布局
////            addParamViews(paramsListBean);
////            return true;
////        });
////        if (index != -1)
////            addParamViews(typeListBeen.get(index).getParamsList());
////        tag_ID = type_id;
////        //初始化动态加载的布局内容
////    }
//
//
//    String tag_ID = "";//标签 1
//    String tag_ID1 = "";//标签 2
//
////    int old_index_2_tag_id1 = -1;
//
//    int index = -1;
//
//    @Override
//    public void initAutoLayout2(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList) {
//        //设置默认       初始化
//
//        for (int i = 0; i < plantTypeList.size(); i++) {
//            if (plantTypeList.get(i).getValue().equals(tag_ID1) && !TextUtils.isEmpty((tag_ID1))) {
//                index = i;
//            }
//        }
//        //动态添加标签
//        SaveSeedlingPresenter.initAutoLayout2(viewHolder.id_flowlayout_2, plantTypeList, index, SaveSeedlingActivity_bak.this, (view, position, parent) -> {
//            tag_ID1 = plantTypeList.get(position).getValue();//上传值
//            return true;
//        });
////        else
////        {
////            //动态添加标签
////            SaveSeedlingPresenter.initAutoLayout2(viewHolder.id_flowlayout_2, plantTypeList, SaveSeedlingActivity.this, (view, position, parent) -> {
////                tag_ID1 = plantTypeList.get(position).getValue();//上传值
////                return true;
////            });
////        }
//
//
//    }
//
//    //    //初始化
////    private void initAutoLayout2(SaveSeedingGsonBean saveSeedingGsonBean) {
////
////    }
//
//
////    public void initAutoLayout(final SaveSeedingGsonBean saveSeedingGsonBean) {
////        //设置默认
////        //动态添加标签
////        SaveSeedlingPresenter.initAutoLayout(viewHolder.id_flowlayout, saveSeedingGsonBean, SaveSeedlingActivity.this, (view, position, parent) -> {
////            tag_ID = saveSeedingGsonBean.getData().getTypeList().get(position).getId();
////            saveSeedingGsonBean.getData().getTypeList().get(position).getName();
////            paramsListBean = saveSeedingGsonBean.getData().getTypeList().get(position).getParamsList();
////            D.e("==tag=点击事件=" + paramsListBean.toString());
////            //根据参数来 配置布局
////            addParamViews(paramsListBean);
////            return true;
////        });
////    }
//
//
//    AutoAddRelative.ViewHolder_top viewHolder_top;
//    AutoAddRelative.ViewHolder_rd viewHolder_rd;
//    ArrayList<AutoAddRelative> arrayList_holders = new ArrayList();//共同的 holder 集合
//    AutoAddRelative autoAddRelative_rd;
//
//    //根据 参数来动态添加布局
//    private void addParamViews(List<SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean> paramsListBean) {
//        int size = paramsListBean.size();
//        viewHolder.ll_auto_add_layout.removeAllViews();
//        arrayList_holders.clear();
//
//        //添加品名 第一行
//        AutoAddRelative autoAddRelative_top = new AutoAddRelative(this)
//                .initView(R.layout.save_seeding_auto_add_top);
//        viewHolder.ll_auto_add_layout.addView(autoAddRelative_top);
//        viewHolder_top = autoAddRelative_top.getViewHolder_top();
//
//        for (int i = 0; i < size; i++) {
//            String name = paramsListBean.get(i).getName();
//            if (name.equals("地径") || name.equals("米径") || name.equals("胸径")) {//第一个添加带有radio button 选项的   地被不添加
//                autoAddRelative_rd = new AutoAddRelative(this)
//                        .initView(R.layout.save_seeding_auto_add_radio)
//                        .setDatas_rd(paramsListBean.get(i));
//                autoAddRelative_rd.setSizeWithTag(paramsListBean.get(i).getValue());
//                viewHolder.ll_auto_add_layout.addView(autoAddRelative_rd);
//                viewHolder_rd = autoAddRelative_rd.getViewHolder_rd();
//
//            } else {
//                AutoAddRelative autoAddRelative = new AutoAddRelative(this).initView(R.layout.save_seeding_auto_add);
//                autoAddRelative.setTag(name);
//                autoAddRelative.setDatas(paramsListBean.get(i));
//                viewHolder.ll_auto_add_layout.addView(autoAddRelative);
//                arrayList_holders.add(autoAddRelative);
//
//            }
//
//
//            // TODO: 2017/4/15    添加 布局  删除布局 动画
//        }
//
//    }
//
//    private List<SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean> paramsListBean;
//
//
//    public void removePicUrls(int currentPage) {
//        D.e("=========removePicUrls=========");
//    }
//
//
//    public void addPicUrls(ArrayList<Pic> resultPathList) {
//        viewHolder.publish_flower_info_gv.getAdapter().addItems(resultPathList);
////        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
//        D.e("=========addPicUrls=========");
//    }
//
//    public class ViewHolder {
//
//        public MeasureGridView publish_flower_info_gv;
//        public TagFlowLayout id_flowlayout;
//        public TagFlowLayout id_flowlayout_2;
//        public SaveSeedingBottomLinearLayout bottom_ll;//底部
//        public TextView iv_ready_save;
//        public Button save;
//        public LinearLayout ll_auto_add_layout;
//        public LinearLayout ll_mainView;
//
//        public ImageView btn_back;//后退
//        public TextView id_tv_edit_all;//清空
//
//
//        public ViewHolder() {
//
//            this.publish_flower_info_gv = (MeasureGridView) findViewById(R.id.publish_flower_info_gv);
//            this.id_flowlayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
//            this.id_flowlayout_2 = (TagFlowLayout) findViewById(R.id.id_flowlayout_2);
//            this.bottom_ll = (SaveSeedingBottomLinearLayout) findViewById(R.id.bottom_ll);
//            this.iv_ready_save = (TextView) findViewById(R.id.iv_ready_save);
//            this.save = (Button) findViewById(R.id.save);
//            this.btn_back = (ImageView) findViewById(R.id.btn_back);
//            this.id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);
//            this.ll_auto_add_layout = (LinearLayout) findViewById(R.id.ll_auto_add_layout);
//            this.ll_mainView = (LinearLayout) findViewById(R.id.ll_mainView);
//
//
//        }
//
//    }
//
//
//    int a = 0;
//
//    public void initListener(ViewHolder holder) {
//
////        public ImageView btn_back ;//后退
////        public TextView id_tv_edit_all ;//清空
//        holder.btn_back.setOnClickListener(v ->
//                {
//                    onBackPressed();//后退
//                }
//        );
//        holder.id_tv_edit_all.setOnClickListener(v ->
//                {
//                    mCache.remove("saveseedling"); // 清空缓存
//                    finish();
//                    startActivity(new Intent(SaveSeedlingActivity_bak.this, SaveSeedlingActivity3_0.class));
//                }
//        );
//
//        //保存
//        holder.save.setOnClickListener(v ->
//                {
//
//
//                    urlPaths.clear();//点击的时候需要初始化
//                    a = 0;//上传开始后需要初始化
//                    if (getParames() == null) {
//                        //检查参数  本地检查
//                        return;
//                    }
//
//
//                    //通过检测  上传图片
//                    hud_numHud.show();
////                    D.e("======== tag============" + tag_name);
////                    D.e("======== tab1============" + tag_name1);
////                    SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = holder.bottom_ll.getUpLoadDatas();
////                    D.e("======== ============" + upLoadDatas.getPrice_max());
////                    D.e("======== ============" + upLoadDatas.getPrice_min());
////                    D.e("======== ============" + upLoadDatas.getRemark());
////                    D.e("======== ============" + upLoadDatas.getRepertory_num());
////                    D.e("======== ============" + upLoadDatas.getUnit());
////                    D.e("======== ============" + upLoadDatas.getValidity());
////                    D.e("======== ============" + upLoadDatas.isMeet());
////
////
////                    D.e("======== top============" + viewHolder_top.tv_auto_add_name.getText());
////
////
////                    if (viewHolder_rd != null) {
////                        D.e("========rb ============" + viewHolder_rd.et_auto_add_max.getText());
////                        D.e("======== rb============" + viewHolder_rd.et_auto_add_min.getText());
////
////                        D.e("======== rb============" + viewHolder_rd.rb_auto_add_left.isChecked());
////                        D.e("======== rb============" + viewHolder_rd.rb_auto_add_center.isChecked());
////                        D.e("======== rb============" + viewHolder_rd.rb_auto_add_right.isChecked());
////                    }
////
////
////                    for (int i = 0; i < arrayList_holders.size(); i++) {
////                        D.e("======== other============" + i + arrayList_holders.get(i).et_auto_add_min.getText());
////                        D.e("======== other============" + i + arrayList_holders.get(i).et_auto_add_max.getText());
////
////                    }
////                    upLoad(holder.publish_flower_info_gv.getAdapter().getDataList());
//                    //   上传图片  可能多图片
//                    saveSeedlingPresenter.upLoad(holder.publish_flower_info_gv.getAdapter().getDataList(), new ResultCallBack<Pic>() {
//                        @Override
////                        public void onSuccess(UpImageBackGsonBean imageBackGsonBean) {//
//                        public void onSuccess(Pic pic) {//
//
//                            urlPaths.add(pic);
//                            a = pic.getSort();
//                            a++;
//                            hudProgress();
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t, int errorNo, String strMsg) {
//                            ToastUtil.showShortToast("上传图片失败");
//                        }
//                    });
//
//                }
//
//        );
//    }
//
////
////    private void upLoad(ArrayList<Pic> dataList) {
////        int list_size = viewHolder.publish_flower_info_gv.getAdapter().getDataList().size();
////        urlPaths.clear();
////        FinalHttp finalHttp = new FinalHttp();
////        a = 0;
////        for (int i = 0; i < list_size; i++) {
////            if (!StringUtil.isHttpUrlPicPath(dataList.get(i).getUrl())) {
////                GetServerUrl.addHeaders(finalHttp, true);
////                finalHttp.addHeader("Content-Type", "application/octet-stream");
////                AjaxParams params1 = new AjaxParams();
////                params1.put("sourceId", "");
////                File file1 = new File(dataList.get(i).getUrl());
////                try {
////                    params1.put("file", file1);
////                } catch (FileNotFoundException e1) {
////                    e1.printStackTrace();
////                }
////                params1.put("imagType", "seedling");
////                finalHttp.post(GetServerUrl.getUrl() + "admin/file/image", params1, new AjaxCallBack<String>() {
////                    @Override
////                    public void onSuccess(String json) {
////                        D.e("===========json=====上传图片成功==========" + json);
////                        UpImageBackGsonBean imageBackGsonBean = GsonUtil.formateJson2Bean(json, UpImageBackGsonBean.class);
////                        urlPaths.add(a, new Pic(imageBackGsonBean.getData().getImage().getId(), false, imageBackGsonBean.getData().getImage().getOssMediumImagePath(), a));
////                        D.e("==========暂时使用中等大小图片==============");
////                        a++;
////                        hudProgress();
////                    }
////
////                    @Override
////                    public void onFailure(Throwable t, int errorNo, String strMsg) {
////                        D.e("===========json=====失败==========" + errorNo + "  " + strMsg + " " + t.getMessage());
////
////                    }
////                });
////
////
////            }
////        }
////
////
////    }
//
//    public void hudProgress() {
//
//        if (hud_numHud != null && !SaveSeedlingActivity_bak.this.isFinishing()) {
//            hud_numHud.setProgress(a * 100 / urlPaths.size());
//            hud_numHud.setProgressText("上传中(" + a + "/" + urlPaths.size()
//                    + "张)");
//        }
//
//        if (a == viewHolder.publish_flower_info_gv.getAdapter().getDataList().size()) {
//            if (urlPaths.size() > 0) {
//                if (urlPaths.size() > 0) {
//                    Data.pics1.clear();
//                    for (int i = 0; i < urlPaths.size(); i++) {
//                        Data.pics1.add(urlPaths.get(i));
//                    }
//                    D.e("============上传保存结果==============");
//                    seedlingSave();
//                } else {
//                }
//            }
//        }
//    }
//
//    private void seedlingSave() {
//        FinalHttp finalHttp = new FinalHttp();
//        GetServerUrl.addHeaders(finalHttp, true);
//        AjaxParams params = getParames();
//        if (params == null) {
//            return;
//        }
//
//        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/save", getParames(), new AjaxCallBack<String>() {
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                // TODO Auto-generated method stub
//                Toast.makeText(SaveSeedlingActivity_bak.this,
//                        R.string.error_net, Toast.LENGTH_SHORT).show();
//                if (hud_numHud != null) {
//                    hud_numHud.dismiss();
//                }
//            }
//
//            @Override
//            public void onSuccess(String json) {
//                D.e("=============json==========" + json);
//
//
//                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);
//
//                if (simpleGsonBean.code.equals("1")) {
//                    //成功
//                    ToastUtil.showShortToast("提交完毕");
//                    finish();
//                } else {
//                    ToastUtil.showShortToast(simpleGsonBean.msg);
//                }
//
//
//                hud_numHud.dismiss();
//
//            }
//        });
//
//    }
//
//
//    //判断选填还是必填  true 通过验证
//    private boolean checkParames(AutoAddRelative autoAddRelative, String tag) {
//
//        if (autoAddRelative == null) {
//            ToastUtil.showShortToast("请先选择种类");
//            return false;
//        }
//
//        switch (tag) {
//            case "1":
//                //若是必填
//                if (autoAddRelative.isRequiredis()) {
//                    if (TextUtils.isEmpty(autoAddRelative.getViewHolder_rd().et_auto_add_min.getText().toString()) || TextUtils.isEmpty(viewHolder_rd.et_auto_add_min.getText().toString())) {
//                        ToastUtil.showShortToast(autoAddRelative.getViewHolder_rd().tv_auto_add_left1.getText() + " 最大值或最小值必须填写!");
//                        D.e("=============checkParames===================" + viewHolder_rd.tv_auto_add_left1.getText() + " 最大值或最小值必须填写!");
//                        return false;
//                    }
//                }
//                break;
//            case "2":
//                //若是必填
//                if (autoAddRelative.isRequiredis()) {
//                    if (TextUtils.isEmpty(autoAddRelative.getViewHolder().et_auto_add_min.getText().toString()) || TextUtils.isEmpty(viewHolder_rd.et_auto_add_min.getText().toString())) {
//                        ToastUtil.showShortToast(autoAddRelative.getViewHolder().tv_auto_add_left1.getText() + " 最大值或最小值必须填写!");
//                        D.e("=============checkParames===================" + viewHolder_rd.tv_auto_add_left1.getText() + " 最大值或最小值必须填写!");
//                        return false;
//                    }
//                }
//        }
//
//        return true;
//    }
//
//    //在此处内部检测     是否为空判断
//    public AjaxParams getParames() {
//
//        //检查url   tag 标签是否选了  品名是否输入  ←
//        if (!check_url_type_plantype_name() || !check_bottom_UpLoadDatas(viewHolder.bottom_ll.getUpLoadDatas())) {
//            return null;
//        }
//        //检查底部布局 bottom_ll   是否为空 返回true  通过检查，，，返回false  return  →
//
//        D.e("========发布=======上传信息===于图片集合==");
//        SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = viewHolder.bottom_ll.getUpLoadDatas();
//
//        AjaxParams params = new AjaxParams();
//
//        params.put("firstSeedlingTypeId", tag_ID);//乔木大类的id\
//        params.put("name", viewHolder_top.tv_auto_add_name.getText().toString());//品名
//        params.put("isNego", upLoadDatas.isMeet() + "");//是否面议
//        params.put("minPrice", upLoadDatas.getPrice_min());//最小价格
//        params.put("maxPrice", upLoadDatas.getPrice_max());//最大价格
//        params.put("validity", upLoadDatas.getValidity());//发布有效期
//        params.put("nurseryId", upLoadDatas.address.addressId);
//        params.put("count", upLoadDatas.getRepertory_num());
//
//        D.e("=========checkParames1=========");
//        if (!checkParames(autoAddRelative_rd, "1")) {
//            D.e("=========null======1===");
//            return null;
//        }
//        if (autoAddRelative_rd.getMTag().equals("dbh")) {
//            params.put("dbhType", autoAddRelative_rd.getDiameterType());
//            params.put("minDbh", viewHolder_rd.et_auto_add_min.getText().toString());//最小地径
//            params.put("maxDbh", viewHolder_rd.et_auto_add_max.getText().toString());//最大地径
//        } else {
//            params.put("diameterType", autoAddRelative_rd.getDiameterType());
//            params.put("minDiameter", viewHolder_rd.et_auto_add_min.getText().toString());//最小地径
//            params.put("maxDiameter", viewHolder_rd.et_auto_add_max.getText().toString());//最大地径
//        }
//
//
//        for (int i = 0; i < arrayList_holders.size(); i++) {
//            D.e("=========checkParames2=========");
//            if (!checkParames(arrayList_holders.get(i), "2")) {
//                D.e("=========null=====2===");
//                return null;
//            }
//
//            if (arrayList_holders.get(i).getTag().equals("高度")) {
//                //有高度 参数
//                params.put("minHeight", arrayList_holders.get(i).getViewHolder().et_auto_add_min.getText().toString());
//                params.put("maxHeight", arrayList_holders.get(i).getViewHolder().et_auto_add_max.getText().toString());
//            }
//            ;
//            if (arrayList_holders.get(i).getTag().equals("冠幅")) {
//                params.put("minCrown", arrayList_holders.get(i).getViewHolder().et_auto_add_min.getText().toString());
//                params.put("maxCrown", arrayList_holders.get(i).getViewHolder().et_auto_add_max.getText().toString());
//
//            }
//            if (arrayList_holders.get(i).getTag().equals("脱杆高")) {
//                params.put("minOffbarHeight", arrayList_holders.get(i).getViewHolder().et_auto_add_min.getText().toString());
//                params.put("maxOffbarHeight", arrayList_holders.get(i).getViewHolder().et_auto_add_max.getText().toString());
//
//            }
//            if (arrayList_holders.get(i).getTag().equals("长度")) {
//                params.put("minLength", arrayList_holders.get(i).getViewHolder().et_auto_add_min.getText().toString());
//                params.put("maxLength", arrayList_holders.get(i).getViewHolder().et_auto_add_max.getText().toString());
//            }
//
//        }
//        params.put("plantType", tag_ID1);//plantType 种类地址秒，移植苗，假植苗，容器苗 对应的type
//
//        params.put("unitType", upLoadDatas.getUnit());
//
//
//        params.put("remarks", upLoadDatas.getRemark());
//
//        params.put("imagesData", GsonUtil.Bean2Json(urlPaths));
//
//        return params;
//    }
//
//    private boolean check_url_type_plantype_name() {
//        //判断 上传图片不能为空
//        if (viewHolder.publish_flower_info_gv.getAdapter().getDataList().size() == 0) {
//            ToastUtil.showShortToast("至少上传一张图片");
//            return false;
//        }
//        if (tag_ID.equals("")) {
//            ToastUtil.showShortToast("请先选择分类1");
//            return false;
//        }
//        if (tag_ID1.equals("")) {
//            ToastUtil.showShortToast("请先选择分类2");
//            return false;
//        }
//        if (TextUtils.isEmpty(viewHolder_top.tv_auto_add_name.getText())) {
//            ToastUtil.showShortToast("请先输入品名");
//            return false;
//        }
//        return true;
//
//    }
//
//    private boolean check_bottom_UpLoadDatas(SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas) {
//        if (viewHolder.bottom_ll.getUpLoadDatas().isMeet()) {
//            //如果面议就不判断价格
//        } else {
//            //如果不是面议  就判断价格不能为空
//            if (TextUtils.isEmpty(viewHolder.bottom_ll.getUpLoadDatas().getPrice_min()) || TextUtils.isEmpty(viewHolder.bottom_ll.getUpLoadDatas().getPrice_max())) {
//                ToastUtil.showShortToast("请输入最大价格于最小价格");
//                return false;
//            }
//        }
//        //库存
//        if (TextUtils.isEmpty(viewHolder.bottom_ll.getUpLoadDatas().getRepertory_num())) {
//            ToastUtil.showShortToast("请输入库存数量");
//            return false;
//        }
//        //地址
//        if (viewHolder.bottom_ll.getUpLoadDatas().address.addressId.equals("")) {
//            ToastUtil.showShortToast("请填写地址");
//            return false;
//        }
//        return true;
//    }
//
//
//    String id = "";
//
//
//    public interface onSelectList {
//        void onAddPicUrls(ArrayList<Pic> resultPathList);
//
//        void onRemovePicUrls(int currentPage);
//    }
//
//
//}
