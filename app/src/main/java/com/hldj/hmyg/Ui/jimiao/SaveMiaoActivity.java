package com.hldj.hmyg.Ui.jimiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.AdressActivity;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BaseRxPresenter;
import com.hldj.hmyg.saler.SaveSeedlingActivity_pubsh_quick;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;
import com.white.utils.SystemSetting;
import com.yangfuhai.asimplecachedemo.lib.ACache;
import com.yunpay.app.KeyBordStateListener;
import com.yunpay.app.KeyboardLayout3;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.next.tagview.TagCloudView.OnTagClickListener;

import static com.hldj.hmyg.R.id.rb_auto_add_center0_1;
import static com.hldj.hmyg.R.id.rb_auto_add_center0_3;
import static com.hldj.hmyg.R.id.rb_auto_add_center1_0;
import static com.hldj.hmyg.R.id.rb_auto_add_center1_2;
import static com.hldj.hmyg.R.id.rb_auto_add_left_ctl;
import static com.hldj.hmyg.R.id.rb_auto_add_right;

/**
 * 家里修改。做个记号
 */
public class SaveMiaoActivity extends NeedSwipeBackActivity implements OnTagClickListener, KeyBordStateListener {


    private SpecType specType = SpecType.empty;

    private String firstSeedlingTypeId = "";
    private String addressId = "";
    private String fullAddress = "";

    private String contactName = "";
    private String contactPhone = "";

    private boolean isDefault;

    private String seedlingParams = "";
    private String nurseryJson_name = "";//苗圃名称

    private EditText et_name;

    private EditText et_price;
    private EditText et_remarks; //备注
    private EditText et_FloorPrice;

    private String count = "";

    private String diameter = "";
    private String specType_text = "";

    private String diameterType = "";

    private String dbh = "";

    private String dbhType = "";

    private String height = "";

    private String crown = "";

    private String maxHeight = "";

    private String maxCrown = "";

    private String offbarHeight = "";

    private String length = "";

    private String plantType = "";

    private String paramsData = "";

    private String minSpec = "";
    private String maxSpec = "";

    private LinearLayout ll_save;
    private Button save;
    private Gson gson;
    //    private KProgressHUD hud;
    private String id = "";
    private ArrayList<Pic> urlPaths = new ArrayList<Pic>();
    private ArrayList<Pic> urlPathsOnline = new ArrayList<Pic>();

    /**
     * 加载图片失败
     */
    public static final int LOAD_PIC_FAILURE = 4;
    /**
     * 添加图片
     */
    public static final int ADD_NEW_PIC = 5;
    /**
     * 照片列表
     */
    private MeasureGridView photoGv;

    public static SaveMiaoActivity instance;

    FinalHttp finalHttp = new FinalHttp();
    public int a = 0;
    private int BACK_TYPE = 0;

    private String flowerInfoPhotoPath = "";


    ArrayList<Pic> arrayList2Adapter = new ArrayList(); // 传入 适配器的图片列表
    private AdressActivity.Address selectAddress;

    //传入初始化 的图片资源  初始化顶部  图片列表控件
    private void initGvTop(MeasureGridView photoGv) {
        arrayList2Adapter.clear();
//            arrayList.add(new Pic("hellow", true, MeasureGridView.usrl, 1));
//            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));

        photoGv.init(this, urlPaths, (ViewGroup) findViewById(R.id.ll_mainView), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
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
                TakePhotoUtil.toChoosePic(mActivity, photoGv.getAdapter());
            }

            @Override
            public void onCancle() {
                D.e("===========onCancle=============");
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_miao);
        mCache = ACache.get(this);

        instance = this;
        SystemSetting.getInstance(SaveMiaoActivity.this).choosePhotoDirId = "";


        Data.pics1.clear();
        Data.photoList.clear();
        Data.microBmList.clear();
        gson = new Gson();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);


        photoGv = (MeasureGridView) findViewById(R.id.publish_flower_info_gv);

        initGvTop(photoGv);

//        adapter = new PublishFlowerInfoPhotoAdapter(SaveMiaoActivity.this,
//                urlPaths);
//        photoGv.setAdapter(adapter);
//        photoGv.setOnItemClickListener(itemClickListener);

        KeyboardLayout3 resizeLayout = (KeyboardLayout3) findViewById(R.id.ll_mainView);
        // 获得要控制隐藏和显示的区域
        resizeLayout.setKeyBordStateListener(this);// 设置回调方法

        LinearLayout ll_02 = (LinearLayout) findViewById(R.id.ll_02);
        LinearLayout ll_03 = (LinearLayout) findViewById(R.id.ll_03);
        ll_05 = (LinearLayout) findViewById(R.id.ll_05);
        list_item_adress = (LinearLayout) findViewById(R.id.list_item_adress);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        tv_id_num = (TextView) findViewById(R.id.tv_id_num);//苗木资源编号
        tv_contanct_name = (TextView) findViewById(R.id.tv_name);
        iv_publish_quick = (TextView) findViewById(R.id.iv_publish_quick);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_con_name_phone = (TextView) findViewById(R.id.tv_con_name_phone);
        tv_is_defoloat = (TextView) findViewById(R.id.tv_is_defoloat);
        id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);
        iv_ready_save = (TextView) findViewById(R.id.iv_ready_save);
        save = (Button) findViewById(R.id.save);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);

        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_remarks = (EditText) findViewById(R.id.et_remark);
        et_FloorPrice = (EditText) findViewById(R.id.et_FloorPrice);
        et_count = (EditText) findViewById(R.id.et_count);
        et_height = (EditText) findViewById(R.id.et_height);
        et_crown = (EditText) findViewById(R.id.et_crown);
        et_maxHeight = (EditText) findViewById(R.id.et_maxHeight);
        et_maxCrown = (EditText) findViewById(R.id.et_maxCrown);
        et_minSpec = (EditText) findViewById(R.id.et_minSpec);
        et_maxSpec = (EditText) findViewById(R.id.et_maxSpec);


        if (getIntent().getStringExtra("id") != null) {
            tv_id_num.setVisibility(View.VISIBLE);
        } else {
            tv_id_num.setVisibility(View.GONE);
        }

        if (getIntent().getStringExtra("id") != null) {

            id = getIntent().getStringExtra("id");

//            tv_id_num.setText("编号：" + id);
//            getDetail(id);

//            Observable.just(id)
//            Observable.create(new ObservableOnSubscribe<String>() {
//                @Override
//                public void subscribe(ObservableEmitter<String> Emitter) throws Exception {
//
//                }
//            })


            getDetail(id)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String json) throws Exception {
                            D.e("=====accept==接收请求到的json =========" + json);
                            initJson(json);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            D.e("accept---Throwable");
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            D.e("complete---Action");
                        }
                    });


            Bundle bundle = getIntent().getExtras();
            urlPaths = ((PicSerializableMaplist) bundle.get("urlPaths")).getMaplist();
//            adapter.notify(urlPaths);

//            GetServerUrl.addHeaders(finalHttp, true);
//            AjaxParams params1 = new AjaxParams();
//            params1.put("id", id);
//            finalHttp.post(GetServerUrl.getUrl() + "admin/seedlingNote/detail",
//                    params1, new AjaxCallBack<Object>() {
//
//                        @Override
//                        public void onStart() {
//                            super.onStart();
//                        }
//
//                        @Override
//                        public void onSuccess(Object t) {
//
//                            initJson(t);
//
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t, int errorNo,
//                                              String strMsg) {
//                            // TODO Auto-generated method
//                            // stub
//                            super.onFailure(t, errorNo, strMsg);
//
//                        }
//                    });

            addressId = getIntent().getStringExtra("addressId");
            count = getIntent().getStringExtra("count");
            height = getIntent().getStringExtra("height");
            crown = getIntent().getStringExtra("crown");
            maxHeight = getIntent().getStringExtra("maxHeight");
            maxCrown = getIntent().getStringExtra("maxCrown");
            contactName = getIntent().getStringExtra("contactName");
            contactPhone = getIntent().getStringExtra("contactPhone");
            fullAddress = getIntent().getStringExtra("address");
            isDefault = getIntent().getBooleanExtra("isDefault", false);
            minSpec = getIntent().getStringExtra("minSpec");
            maxSpec = getIntent().getStringExtra("maxSpec");
            et_name.setText(getIntent().getStringExtra("name"));

            specType_text = (getIntent().getStringExtra("specType"));


            //备注
            et_remarks.setText(getIntent().getStringExtra("remarks"));
            if (!"0".equals(getIntent().getStringExtra("price"))
                    && !"0.0".equals(getIntent().getStringExtra("price"))) {
                et_price.setText(getIntent().getStringExtra("price"));
            }
            if (!"0".equals(height) && !"0.0".equals(height)) {
                et_height.setText(height);
            }
            if (!"0".equals(maxHeight) && !"0.0".equals(maxHeight)) {
                et_maxHeight.setText(maxHeight);
            }
            if (!"0".equals(crown) && !"0.0".equals(crown)) {
                et_crown.setText(crown);
            }
            if (!"0".equals(maxCrown) && !"0.0".equals(maxCrown)) {
                et_maxCrown.setText(maxCrown);
            }
            if (!"0".equals(minSpec) && !"0.0".equals(minSpec)) {
                et_minSpec.setText(minSpec);
            }
            if (!"0".equals(maxSpec) && !"0.0".equals(maxSpec)) {
                et_maxSpec.setText(maxSpec);
            }
            if (!"0".equals(count) && !"0.0".equals(count)) {
                et_count.setText(count);
            }

            if (!"".equals(addressId)) {
                list_item_adress.setVisibility(View.VISIBLE);
                ll_05.setVisibility(View.GONE);

                //地址
                tv_address_name.setText(fullAddress);

                /*联系人*/
                tv_con_name_phone.setText("联系人:" + contactName + "\u0020" + contactPhone);

                //苗圃名称
                tv_contanct_name.setText("苗圃名称:" + getIntent().getStringExtra("nurseryJson_name"));


                if (isDefault) {
                    tv_is_defoloat.setVisibility(View.VISIBLE);
                } else {
                    tv_is_defoloat.setVisibility(View.GONE);
                }
            }

        }

        if (specType_text.equals("size0")) {
            ((RadioButton) findViewById(rb_auto_add_left_ctl)).setChecked(true);
            specType = SpecType.size0;
        } else if (specType_text.equals("size10")) {
            ((RadioButton) findViewById(rb_auto_add_center0_1)).setChecked(true);
            specType = SpecType.size10;
        } else if (specType_text.equals("size30")) {
            ((RadioButton) findViewById(rb_auto_add_center0_3)).setChecked(true);
            specType = SpecType.size30;
        } else if (specType_text.equals("size100")) {
            ((RadioButton) findViewById(rb_auto_add_center1_0)).setChecked(true);
            specType = SpecType.size100;
        } else if (specType_text.equals("size120")) {
            ((RadioButton) findViewById(rb_auto_add_center1_2)).setChecked(true);
            specType = SpecType.size120;
        } else if (specType_text.equals("size130")) {
            ((RadioButton) findViewById(rb_auto_add_right)).setChecked(true);
            specType = SpecType.size130;
        } else {
//            ((RadioButton) findViewById(rb_auto_add_left_ctl)).setChecked(true);
//            specType = SpecType.size0;
        }


        findViewById(rb_auto_add_left_ctl).setOnClickListener(multipleClickProcess);//出土量
        findViewById(rb_auto_add_center0_1).setOnClickListener(multipleClickProcess);//0.1M量
        findViewById(R.id.rb_auto_add_center0_3).setOnClickListener(multipleClickProcess);//0.3
        findViewById(R.id.rb_auto_add_center1_0).setOnClickListener(multipleClickProcess);//1.0
        findViewById(rb_auto_add_center1_2).setOnClickListener(multipleClickProcess);//1.2
        findViewById(rb_auto_add_right).setOnClickListener(multipleClickProcess);//1.3


        btn_back.setOnClickListener(multipleClickProcess);
        ll_02.setOnClickListener(multipleClickProcess);
        ll_03.setOnClickListener(multipleClickProcess);
        ll_05.setOnClickListener(multipleClickProcess);
        //发布到商城
//        iv_publish_quick.setOnClickListener(multipleClickProcess);
        ll_save.setOnClickListener(multipleClickProcess);
        list_item_adress.setOnClickListener(multipleClickProcess);
        id_tv_edit_all.setOnClickListener(multipleClickProcess);
        iv_ready_save.setOnClickListener(multipleClickProcess);
        save.setOnClickListener(multipleClickProcess);


        iv_publish_quick.setVisibility(isIdNull() ? View.GONE : View.VISIBLE);
        D.e("==是否显示发布到商城===" + isIdNull());

    }

    private void initJson(@android.support.annotation.NonNull String json) {
        D.e("=========初始化 头像=============" + json + "");
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                JSONObject seedling = JsonGetInfo.getJSONObject(
                        JsonGetInfo.getJSONObject(
                                jsonObject, "data"),
                        "seedling");
                JSONArray imagesJson = JsonGetInfo
                        .getJsonArray(seedling,
                                "imagesJson");

                String num = JsonGetInfo.getJsonString(seedling, "num");
                tv_id_num.setText("编号：" + num);


                for (int i = 0; i < imagesJson.length(); i++) {
                    JSONObject image = imagesJson
                            .getJSONObject(i);
                    urlPaths.add(new Pic(JsonGetInfo
                            .getJsonString(image, "id"),
                            false, JsonGetInfo
                            .getJsonString(image,
                                    "url"), i));
                }

                photoGv.getAdapter().addItems(urlPaths);


            }
        } catch (JSONException e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }

    }


    /*获取 网络数据*/
    private Observable<String> getDetail(String id) {
        D.e("id");
        D.e("" + id);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                new BaseRxPresenter()
                        .putParams("id", id)
                        .doRequest("admin/seedlingNote/detail", true, new AjaxCallBack<String>() {
                            @Override
                            public void onSuccess(String json) {
                                D.e("==========json=获取成功=================");
                                D.e(json);
                                D.e("==发射器===将json 传递给rx 观察者========\n" + json);
                                emitter.onNext(json);//
                                D.e("==发射器  onComplete ");
                                emitter.onComplete();
//                                Observable.create(new ObservableOnSubscribe<String>() {
//                                    @Override
//                                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//                                        D.e("==发射器===将json 传递给rx 观察者========\n" + json);
//                                        emitter.onNext(json);//
//                                        D.e("==发射器  onComplete ");
//                                        emitter.onComplete();
//                                    }
//                                });
                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                ToastUtil.showShortToast("获取资源信息错误");
                                D.e("onFailure");
                                emitter.onError(t);
                            }
                        });

            }
        });
    }


    @Override
    public void stateChange(int state) {
        // TODO Auto-generated method stub
        switch (state) {
            case KeyboardLayout3.KEYBORAD_HIDE:
                ll_save.setVisibility(View.VISIBLE);
                break;
            case KeyboardLayout3.KEYBORAD_SHOW:
                ll_save.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    private LinearLayout list_item_adress;

    private ImageView iv_edit;
    private TextView tv_id_num;

    private TextView tv_contanct_name;
    private TextView iv_publish_quick;//发布到商城

    private TextView tv_address_name;
    private TextView tv_con_name_phone;

    private TextView tv_is_defoloat;

    private LinearLayout ll_05;

    private EditText et_count;

    private ACache mCache;

    private TextView id_tv_edit_all;
    private EditText et_crown;
    private EditText et_height;
    private EditText et_minSpec;
    private EditText et_maxSpec;
    private TextView iv_ready_save;
    private EditText et_maxHeight;
    private EditText et_maxCrown;

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                if (view.getId() == R.id.btn_back) {
                    onBackPressed();
                } else if (view.getId() == R.id.rb_auto_add_left_ctl) {//出土量
                    specType = SpecType.size0;
                } else if (view.getId() == rb_auto_add_center0_1) {//
                    specType = SpecType.size10;
                } else if (view.getId() == R.id.rb_auto_add_center0_3) {//0.1
                    specType = SpecType.size30;
                } else if (view.getId() == R.id.rb_auto_add_center1_0) {//0.3
                    specType = SpecType.size100;
                } else if (view.getId() == rb_auto_add_center1_2) {//1.0
                    specType = SpecType.size120;
                } else if (view.getId() == rb_auto_add_right) {//1.3
                    specType = SpecType.size130;
                } else if (view.getId() == R.id.iv_publish_quick) {

                    D.e("=========快速发布到商城===========");
                    pulishQuick();


                } else if (view.getId() == R.id.list_item_adress || view.getId() == R.id.ll_05) {
                    D.e("=========苗原地点击===========");
                    AdressActivity.start2AdressListActivity(mActivity, addressId, "", address -> {
                        D.e("===========返回的地址==========" + address.toString());
                        list_item_adress.setVisibility(View.VISIBLE);
                        ll_05.setVisibility(View.GONE);
                        addressId = address.addressId;

                        selectAddress = address;

                        //苗圃名称
                        tv_contanct_name.setText("苗圃名称:" + address.name);
                        tv_address_name.setText(address.fullAddress);

                        tv_con_name_phone.setText("联系人:" + address.contactName + "\u0020" + address.contactPhone);


                        if (address.isDefault) {
                            tv_is_defoloat.setVisibility(View.VISIBLE);
                        } else {
                            tv_is_defoloat.setVisibility(View.GONE);
                        }
                    });
                } else if (view.getId() == R.id.save || view.getId() == R.id.iv_ready_save) {
                    if (view.getId() == R.id.save) {
                        BACK_TYPE = 0;
                    } else if (view.getId() == R.id.iv_ready_save) {
                        BACK_TYPE = 1;//保存并退出
                    }

                    if (photoGv.getAdapter().getDataList().size() == 0) {
                        ToastUtil.showShortToast("图片必须上传");
                        return;
                    }

                    if ("".equals(et_name.getText().toString())) {
                        Toast.makeText(SaveMiaoActivity.this, "请输入品名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if ("".equals(addressId)) {
                        Toast.makeText(SaveMiaoActivity.this, "请先选择苗源地址",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!"".equals(et_minSpec.getText().toString())
                            && !"".equals(et_maxSpec.getText().toString())
                            && Double.parseDouble(et_minSpec.getText()
                            .toString()) >= Double
                            .parseDouble(et_maxSpec.getText()
                                    .toString())) {
                        Toast.makeText(SaveMiaoActivity.this, "最大规格必须大于最小规格",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    if (photoGv.getAdapter().getDataList().size() == 0) {
//                        seedlingSave();
//                        ToastUtil.showShortToast("图片必须上传");
//                    } else {
                    upLoadImgs();//上传图片
//                    }

                } else if (view.getId() == R.id.id_tv_edit_all) {
                    mCache.remove("savemiao");
                    resetView();
//                    finish();
//                    startActivity(new Intent(SaveMiaoActivity.this,
//                            SaveMiaoActivity.class));
                    //
                    // 清空
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(Data.loading_time);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void hudProgress() {

        if (!mActivity.isFinishing()) {
            int size = photoGv.getAdapter().getDataList().size();
//            hud_numHud.setProgress(a * 100 / urlPaths.size());
//            hud_numHud.setLabel("上传(" + a + "/" + urlPaths.size() + "张)");
//            D.e("=======a * 100 / urlPaths.size()===========");
//            D.e("上传(" + a + "/" + urlPaths.size() + "张)");
            UpdateLoading("正在上传第 (" + a + "/" + size + "张) 图片");
//            hud_numHud.updateLable("正在上传第 (" + a + "/" + size + "张) 图片");
//            hud_numHud.show();
        }

        if (a == photoGv.getAdapter().getDataList().size()) {
            if (urlPathsOnline.size() > 0) {
                if (urlPathsOnline.size() > 0) {
                    Data.pics1.clear();
                    for (int i = 0; i < urlPathsOnline.size(); i++) {
                        Data.pics1.add(urlPathsOnline.get(i));
                    }
                    D.e("============上传保存结果==============");

                    photoGv.getAdapter().notify(Data.pics1);
                    seedlingSave();
                } else {
                }
            }
        }
    }


    private void seedlingSave() {
        // TODO Auto-generated method stub

        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        if (!"".equals(id)) {
            params.put("id", id);
        }
        params.put("firstSeedlingTypeId", firstSeedlingTypeId);
        params.put("name", et_name.getText().toString());
        params.put("price", et_price.getText().toString());
        params.put("remarks", et_remarks.getText().toString());
        params.put("nurseryId", addressId);
        params.put("count", et_count.getText().toString());
        params.put("minHeight", et_height.getText().toString());
        params.put("minCrown", et_crown.getText().toString());
        params.put("maxHeight", et_maxHeight.getText().toString());
        params.put("maxCrown", et_maxCrown.getText().toString());
        params.put("minSpec", et_minSpec.getText().toString());
        params.put("maxSpec", et_maxSpec.getText().toString());


        // size00("size00", "贴地量 "),//贴地量
        params.put("specType", specType.getEnumValue());

        D.e("==========specType============" + specType.toString());
//        specType

//        params.put("imagesData", gson.toJson(urlPaths));
        D.e("=====photoGv========" + photoGv.getAdapter().getDataList().toString());
        D.e("=====photurlPathsOnline========" + urlPathsOnline.toString());

        params.put("imagesData", gson.toJson(photoGv.getAdapter().getDataList()));
//        params.put("imagesData", gson.toJson(urlPathsOnline));
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedlingNote/save",
                params, new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        save.setClickable(true);
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                Toast.makeText(SaveMiaoActivity.this, msg,
                                        Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(code)) {

                                if (BACK_TYPE == 0) {
                                    if (!"".equals(id)) {
//                                        mCache.remove("savemiao");
//                                        StorageSave mStorageSave = new StorageSave();
//                                        mStorageSave.setNurseryId(addressId);
//                                        mStorageSave
//                                                .setContactName(contactName);
//                                        mStorageSave
//                                                .setContactPhone(contactPhone);
//                                        mStorageSave.setDefault(isDefault);
//                                        // 额外数据
//                                        mStorageSave.setAddress(fullAddress);
//                                        // 保存缓存
//                                        mCache.put("savemiao",
//                                                gson.toJson(mStorageSave));
                                        setResult(8);
//                                        finish();
                                        resetView();
                                        id = "";
//                                        startActivity(new Intent(SaveMiaoActivity.this, SaveMiaoActivity.class));
//                                        finish();
//                                        Intent toSaveMiaoActivity = new Intent(
//                                                SaveMiaoActivity.this,
//                                                SaveMiaoActivity.class);
//                                        startActivity(toSaveMiaoActivity);
//                                        overridePendingTransition(
//                                                R.anim.slide_in_left,
//                                                R.anim.slide_out_right);
                                    } else {
                                        //新增   保存并继续
                                        Data.pics1.clear();
                                        Data.photoList.clear();
                                        Data.microBmList.clear();

//                                        mCache.remove("savemiao");
                                        setResult(8);
                                        resetView();
                                        id = "";
//                                        finish();
//                                        instance = SaveMiaoActivity.this;


//                                        Intent toManagerListActivity = new Intent(
//                                                SaveMiaoActivity.this,
//                                                MiaoNoteListActivity.class);
//                                        startActivity(toManagerListActivity);
//                                        overridePendingTransition(
//                                                R.anim.slide_in_left,
//                                                R.anim.slide_out_right);
                                    }
                                } else if (BACK_TYPE == 1) {

                                    // BACK_TYPE = 1;//保存并退出
//                                  mCache.remove("savemiao");
//                                    Intent toMiaoNoteListActivity = new Intent(SaveMiaoActivity.this, MiaoNoteListActivity.class);
//                                    startActivity(toMiaoNoteListActivity);
                                    setResult(8);
                                    finish();
                                    instance = SaveMiaoActivity.this;

                                }

                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);

                        try {
                            hindLoading();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SaveMiaoActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                        hindLoading();
                    }

                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {
            try {
                photoGv.addImageItem(flowerInfoPhotoPath);
                photoGv.getAdapter().Faild2Gone(true);
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
        } else if (resultCode == 3) {
            count = data.getStringExtra("count");
            diameter = data.getStringExtra("diameter");
            diameterType = data.getStringExtra("diameterType");
            dbh = data.getStringExtra("dbh");
            dbhType = data.getStringExtra("dbhType");
            height = data.getStringExtra("height");
            crown = data.getStringExtra("crown");
            maxHeight = data.getStringExtra("maxHeight");
            maxCrown = data.getStringExtra("maxCrown");
            offbarHeight = data.getStringExtra("offbarHeight");
            length = data.getStringExtra("length");
            plantType = data.getStringExtra("plantType");
            paramsData = data.getStringExtra("paramsData");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onTagClick(int position) {
        // TODO Auto-generated method stub
        if (position == -1) {
            Toast.makeText(getApplicationContext(), "点击末尾文字",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "点击 position : " + position, Toast.LENGTH_SHORT).show();
        }
    }


    public void upLoadImgs() {
        showLoading();
        setLoadCancle(false);
        a = 0;
//        urlPaths.clear();
        urlPathsOnline.clear();

        SaveSeedlingPresenter saveSeedlingPresenter = new SaveSeedlingPresenter();
        //   上传图片  可能多图片
        saveSeedlingPresenter.upLoad(photoGv.getAdapter().getDataList(), new ResultCallBack<Pic>() {
            @Override
//                        public void onSuccess(UpImageBackGsonBean imageBackGsonBean) {//
            public void onSuccess(Pic pic) {//

//                urlPaths.add(pic);
                urlPathsOnline.add(pic);
//                          urlPaths.replaceAll(,pic);
                a = pic.getSort();
                a++;
                D.e("===========a==============" + a);
                D.e("===========size==============" + photoGv.getAdapter().getDataList().size());
                hudProgress();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                            hud_numHud.dismiss();
                hindLoading();
                ToastUtil.showShortToast("上传图片失败,请稍后尝试！");
            }
        });

    }

    private static final String TAG = "SaveMiaoActivity";


    /**
     * 添加图片
     */
    public void addPicUrls(ArrayList<Pic> resultPathList) {
        photoGv.getAdapter().addItems(resultPathList);
        photoGv.getAdapter().Faild2Gone(true);
//        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
        D.e("=========addPicUrls=========" + resultPathList.toString());
    }


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SaveMiaoActivity.class);
        activity.startActivityForResult(intent, 110);
    }

    public void resetView() {
        photoGv.getAdapter().notify(new ArrayList<>());
        resetEdit(et_name);
        resetEdit(et_price);
        resetEdit(et_count);
        resetEdit(et_remarks);

        resetEdit(et_crown);
        resetEdit(et_maxCrown);


        resetEdit(et_height);
        resetEdit(et_maxHeight);

        resetEdit(et_maxSpec);
        resetEdit(et_minSpec);


    }

    public void resetEdit(EditText editText) {
        editText.setText("");
    }


    public boolean isIdNull() {
//        return TextUtils.isEmpty(getIntent().getStringExtra("id"));
        return true;
    }


    /*快速发布到商城*/
    private void pulishQuick() {
        //1  传递  图片
        Intent intent = new Intent(mActivity, SaveSeedlingActivity_pubsh_quick.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGES, photoGv.getAdapter().getDataList());
        bundle.putSerializable(ADDRESS, gegAddress());
        bundle.putString(PRICE, et_price.getText().toString());
        bundle.putString(COUNT, et_count.getText().toString());
        bundle.putString(PLANT_NAME, et_name.getText().toString());//品名

        bundle.putString(SPACE_MIN, et_minSpec.getText().toString());//规格
        bundle.putString(SPACE_MAX, et_maxSpec.getText().toString());//规格

        bundle.putString(MIN_HEIGHT, et_height.getText().toString());//高度
        bundle.putString(MAX_HEIGHT, et_maxHeight.getText().toString());//高度

        bundle.putString(MIN_CROWN, et_crown.getText().toString());// 冠幅
        bundle.putString(MAX_CROWN, et_maxCrown.getText().toString());// 冠幅


        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 110);
/**
 *   params.put("firstSeedlingTypeId", firstSeedlingTypeId);
 params.put("name", et_name.getText().toString());
 params.put("price", et_price.getText().toString());
 params.put("nurseryId", addressId);
 params.put("count", et_count.getText().toString());
 params.put("minHeight", et_height.getText().toString());
 params.put("minCrown", et_crown.getText().toString());
 params.put("maxHeight", et_maxHeight.getText().toString());
 params.put("maxCrown", et_maxCrown.getText().toString());
 params.put("minSpec", et_minSpec.getText().toString());
 params.put("maxSpec", et_maxSpec.getText().toString());
 */

    }

    private AdressActivity.Address gegAddress() {

        if (selectAddress != null) {
            return selectAddress;
        } else {
            AdressActivity.Address address = new AdressActivity.Address();
            address.addressId = addressId;//地址id
            address.contactPhone = contactPhone;//联系电话
            address.contactName = contactName;//联系人
            address.name = getIntent().getStringExtra("nurseryJson_name");//苗圃名称:
            address.fullAddress = fullAddress;
            address.isDefault = isDefault;//是否默认
            D.e("===========获取的地址==========" + address.toString());

            return address;
        }

    }


    public static final String IMAGES = "images";//图片
    public static final String ADDRESS = "address";// 地址
    public static final String PRICE = "price";// 价格
    public static final String COUNT = "count";// 价格
    public static final String PLANT_NAME = "plantName";// 品名
    public static final String SPACE_MIN = "space_min";// 规格
    public static final String SPACE_MAX = "space_max";// 规格

    public static final String MIN_HEIGHT = "minHeight";//  最小高度
    public static final String MAX_HEIGHT = "maxHeight";// 最大高度

    public static final String MIN_CROWN = "min_crown";//  最小冠幅
    public static final String MAX_CROWN = "max_crown";// 最大冠幅


/**
 *   //地址对象
 AdressActivity.Address address = new AdressActivity.Address();
 address.addressId = this.saveSeedingGsonBean.getData().nursery.getId();
 address.contactPhone = this.saveSeedingGsonBean.getData().nursery.contactPhone;
 address.contactName = this.saveSeedingGsonBean.getData().nursery.contactName;
 address.cityName = this.saveSeedingGsonBean.getData().nursery.getCityName();
 address.name = this.saveSeedingGsonBean.getData().nursery.getName();//苗圃名称
 address.fullAddress = this.saveSeedingGsonBean.getData().nursery.getFullAddress();//详细地址
 address.isDefault = this.saveSeedingGsonBean.getData().nursery.isDefault;
 */


}
