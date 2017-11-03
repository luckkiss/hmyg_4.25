package com.hldj.hmyg.Ui.jimiao;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.AlphaTitleScrollView;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.widget.AutoAdd2DetailLinearLayout;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;
import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.white.utils.SystemSetting;
import com.yangfuhai.asimplecachedemo.lib.ACache;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

public class MiaoDetailActivity extends NeedSwipeBackActivity {

    private TextView tv_address;
    private String addressId = "";
    private String fullAddress = "";
    private String contactName = "";
    private String contactPhone = "";//苗圃电话
    private String ownerPhone = "";//发布人电话
    private String companyName = "";
    private boolean isDefault;

    protected String nurseryJson_name;

    private TextView tv_name;

    private TextView tv_price;
    private TextView tv_remarks;
    private TextView tv_city;

    private String count = "";


    private String height = "";

    private String crown = "";

    private String maxHeight = "";

    private String maxCrown = "";


    private String minSpec = "";
    private String maxSpec = "";


    private String id = "";
    private ArrayList<Pic> urlPaths = new ArrayList<Pic>();


    /**
     * 照片列表
     */
    private MeasureGridView photoGv;


    FinalHttp finalHttp = new FinalHttp();
    public int a = 0;
    private AutoAdd2DetailLinearLayout.UploadDatas uploadDatas;
    private AutoAdd2DetailLinearLayout autoAdd2DetailLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao_detail);
        mCache = ACache.get(this);
        setar();
        setToolBarAlfaScr();

        SystemSetting.getInstance(MiaoDetailActivity.this).choosePhotoDirId = "";


        Data.pics1.clear();
        Data.photoList.clear();
        Data.microBmList.clear();
//        Data.paramsDatas.clear();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        photoGv = (MeasureGridView) findViewById(R.id.publish_flower_info_gv);
//        adapter = new PublishFlowerInfoPhotoAdapter3(MiaoDetailActivity.this,
//                urlPaths);
//        photoGv.setAdapter(adapter);
        PhotoGvOnItemClickListener itemClickListener = new PhotoGvOnItemClickListener();
        if (photoGv != null)
            photoGv.setOnItemClickListener(itemClickListener);


//        LinearLayout ll_02 = (LinearLayout) findViewById(R.id.ll_02);
        ll_05 = (LinearLayout) findViewById(R.id.ll_05);
//        list_item_adress = (LinearLayout) findViewById(R.id.list_item_adress);
//        iv_edit = (ImageView) findViewById(R.id.iv_edit);
//        iv_edit.setVisibility(View.INVISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //苗圃名称
        tv_contanct_name = (TextView) findViewById(R.id.tv_name);
        /*联系人电话  名称*/
        tv_con_name_phone = (TextView) findViewById(R.id.tv_con_name_phone);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
//        tv_is_defoloat = (TextView) findViewById(R.id.tv_is_defoloat);
//        id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);


        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_name = (TextView) findViewById(R.id.tv_name_top);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_remarks = (TextView) findViewById(R.id.tv_remarks);
        tv_city = (TextView) findViewById(R.id.tv_city);

        tv_count = (TextView) findViewById(R.id.tv_count);
        et_height = (EditText) findViewById(R.id.et_height);
        et_maxHeight = (EditText) findViewById(R.id.et_maxHeight);
        et_crown = (EditText) findViewById(R.id.et_crown);
        et_maxCrown = (EditText) findViewById(R.id.et_maxCrown);
//        et_minSpec = (EditText) findViewById(R.id.et_minSpec);
        et_max_Spec = (EditText) findViewById(R.id.et_max_Spec);
        et_min_Spec = (EditText) findViewById(R.id.et_min_Spec);

//         <com.hldj.hmyg.widget.AutoAdd2DetailLinearLayout
//        android:id="@+id/auto_add"


        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
            Bundle bundle = getIntent().getExtras();
            urlPaths = ((PicSerializableMaplist) bundle.get("urlPaths")).getMaplist();
//            adapter.notify(urlPaths);
//            initGvTop(photoGv,urlPaths);

            GetServerUrl.addHeaders(finalHttp, true);
            AjaxParams params1 = new AjaxParams();
            params1.put("id", id);
            finalHttp.post(GetServerUrl.getUrl() + "admin/seedlingNote/detail",
                    params1, new AjaxCallBack<Object>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onSuccess(Object t) {
                            try {
                                JSONObject jsonObject = new JSONObject(t
                                        .toString());
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
//                                    tv_id_num.setText("编号：" + num);
//                                    uploadDatas.seedlingNum = "资源编号：" + num;
                                    autoAdd2DetailLinearLayout.changeText(num);

                                    for (int i = 0; i < imagesJson.length(); i++) {
                                        JSONObject image = imagesJson
                                                .getJSONObject(i);
                                        urlPaths.add(new Pic(JsonGetInfo
                                                .getJsonString(image, "id"),
                                                false, JsonGetInfo
                                                .getJsonString(image,
                                                        "url"), i));
                                    }
//                                  adapter.notify(urlPaths);

                                    initGvTop(photoGv, urlPaths);


                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch
                                // block
                                e.printStackTrace();
                            }

                            super.onSuccess(t);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo,
                                              String strMsg) {
                            // TODO Auto-generated method
                            // stub
                            super.onFailure(t, errorNo, strMsg);

                        }
                    });

            addressId = getIntent().getStringExtra("addressId");
            count = getIntent().getStringExtra("count");
            height = getIntent().getStringExtra("height");
            crown = getIntent().getStringExtra("crown");
            maxHeight = getIntent().getStringExtra("maxHeight");
            maxCrown = getIntent().getStringExtra("maxCrown");
            contactName = getIntent().getStringExtra("contactName");
            contactPhone = getIntent().getStringExtra("contactPhone");
            ownerPhone = getIntent().getStringExtra("ownerPhone");
            fullAddress = getIntent().getStringExtra("address");
            isDefault = getIntent().getBooleanExtra("isDefault", false);
            minSpec = getIntent().getStringExtra("minSpec");
            maxSpec = getIntent().getStringExtra("maxSpec");
            nurseryJson_name = getIntent().getStringExtra("nurseryJson_name");
            tv_name.setText(getIntent().getStringExtra("name"));
            tv_city.setText(FUtil.$_zero(getIntent().getStringExtra("fullName")));
            tv_price.setText("¥ " + FUtil.$_zero(getIntent().getStringExtra("price")));
            tv_remarks.setText(getIntent().getStringExtra("remarks"));
            tv_remarks.setText("备注：" + getIntent().getStringExtra("remarks"));


            uploadDatas = new AutoAdd2DetailLinearLayout.UploadDatas();
            uploadDatas.remarks = FUtil.$_zero(getIntent().getStringExtra("remarks"));

            JSONArray jsonArray = new JSONArray();

//            JSONObject object = new JSONObject();
//            JSONObject object1 = new JSONObject();
//            JSONObject object2 = new JSONObject();
//            JSONObject object3 = new JSONObject();

            /**
             *  jsonArray = {JSONArray@9219} "
             [{"name":"高度","value":"20-25CM"},
             {"name":"冠幅","value":"20-25CM"}]"

             values = {ArrayList@9222}
             size = 2
             0 = {JSONObject@9225}
             "{"name":"高度","value":"20-25CM"}"

             1 =
             {JSONObject@9226}
             "{"name":"冠幅","value":"20-25CM"}"
             */


            try {
//
                String staceType = getIntent().getStringExtra("specType");
//
//                RadioButton button = (RadioButton) findViewById(R.id.space_type);
//
//                if (TextUtils.isEmpty(getTextByKey(staceType))) {
//                    ((ViewGroup) button.getParent()).setVisibility(View.GONE);

                JSONObject innerjObject = new JSONObject();
                if (!TextUtils.isEmpty(FUtil.$("-", minSpec, maxSpec))) {
                    innerjObject = new JSONObject();
                    innerjObject.put("name", "规格");
                    innerjObject.put("value", FUtil.$(" - ", minSpec, maxSpec) + "CM   " + getTextByKey(staceType));
                    jsonArray.put(innerjObject);
                }

                if (!TextUtils.isEmpty(FUtil.$("-", height, maxHeight))) {
                    innerjObject = new JSONObject();
                    innerjObject.put("name", "高度");
                    innerjObject.put("value", FUtil.$("-", height, maxHeight) + "CM");
                    jsonArray.put(innerjObject);
                }
                if (!TextUtils.isEmpty(FUtil.$("-", crown, maxCrown))) {
                    innerjObject = new JSONObject();
                    innerjObject.put("name", "冠幅");
                    innerjObject.put("value", FUtil.$("-", crown, maxCrown) + "CM");
                    jsonArray.put(innerjObject);
                }
//
//                innerjObject = new JSONObject();
//                innerjObject.put("name", "备注name");
//                innerjObject.put("value", "备注value");
//                jsonArray.put(innerjObject);

//                object.put("规格", map);
//                object1.put("高度", FUtil.$(" - ", height, maxHeight));
//                object1.put("高度", map);
//                object2.put("冠幅", map);
//                object3.put("备注", map);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            jsonArray.put(object);
//            jsonArray.put(object1);
//            jsonArray.put(object2);
//            jsonArray.put(object3);
            uploadDatas.seedlingNum = "获取中....";
            uploadDatas.jsonArray = jsonArray;
//            object.put("name","abc");


            String staceType = getIntent().getStringExtra("specType");

            RadioButton button = (RadioButton) findViewById(R.id.space_type);

            if (TextUtils.isEmpty(getTextByKey(staceType))) {
                ((ViewGroup) button.getParent()).setVisibility(View.GONE);
            } else {
                ((ViewGroup) button.getParent()).setVisibility(View.GONE);
                button.setText(getTextByKey(staceType));
            }

            autoAdd2DetailLinearLayout = (AutoAdd2DetailLinearLayout) ((AutoAdd2DetailLinearLayout) findViewById(R.id.auto_add)).setDatas(uploadDatas);


//            if ("0".equals(count)) {
//                count = "-";
//            }
            tv_count.setText("数量：" + FUtil.$_zero(count));
            if ("0".equals(height)) {
                height = "";
            }
            if ("0".equals(maxHeight)) {
                maxHeight = "";
            }
            if ("0".equals(crown)) {
                crown = "";
            }
            if ("0".equals(maxCrown)) {
                maxCrown = "";
            }
//            et_height.setText(height + "-" + maxHeight);
            et_height.setText(height);
            et_maxHeight.setText(maxHeight);
//            et_crown.setText(crown + "-" + maxCrown);
            et_crown.setText(crown);
            et_maxCrown.setText(maxCrown);
//            et_maxSpec.setText(minSpec);
//            et_maxSpec.setText(maxSpec);
            et_min_Spec.setText(minSpec);
            et_max_Spec.setText(maxSpec);


            tv_title.setText(getIntent().getStringExtra("name"));
            if (!"".equals(addressId)) {
//                list_item_adress.setVisibility(View.VISIBLE);
                ll_05.setVisibility(View.GONE);


                /*苗圃名称*/
                tv_contanct_name.setText(FUtil.$_zero(nurseryJson_name));

                tv_con_name_phone.setText(FUtil.$_zero(contactName));
//                tv_con_name_phone.setText("联系人: " + contactName + "\u0020" + contactPhone);


                tv_address_name.setText(fullAddress);
//                if (isDefault) {
//                    tv_is_defoloat.setVisibility(View.GONE);
//                } else {
//                    tv_is_defoloat.setVisibility(View.GONE);
//                }
            }

        } else {

        }

        btn_back.setOnClickListener(multipleClickProcess);
//        ll_02.setOnClickListener(multipleClickProcess);
        ll_05.setOnClickListener(multipleClickProcess);

//        list_item_adress.setOnClickListener(multipleClickProcess);
//        id_tv_edit_all.setOnClickListener(multipleClickProcess);

    }


    /**
     * 设置状态栏 黑色 并且图片置顶
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarAlpha(0.0f);
            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
        }




//		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//		{
//			// 透明状态栏
//			getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//		}
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }




    public void setToolBarAlfaScr() {
        AlphaTitleScrollView   scroll = (AlphaTitleScrollView) findViewById(R.id.alfa_scroll);
        LinearLayout title = (LinearLayout) findViewById(R.id.ll_detail_toolbar);
        TextView tv_title = getView(R.id.tv_title);
        View head = findViewById(R.id.view_detail_top);
        ImageView btn_back = getView(R.id.btn_back);
        btn_back.setSelected(false);
        scroll.setTitleAndHead(title, head, new AlphaTitleScrollView.OnStateChange() {
            @Override
            public void onShow() {
                tv_title.setVisibility(View.VISIBLE);
                btn_back.setSelected(false);
            }

            @Override
            public void onHiden() {
                tv_title.setVisibility(View.GONE);
                btn_back.setSelected(true);
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                scroll.smoothScrollTo(0, 10);
//            }
//        }, 100);

    }












    private String getTextByKey(String staceType) {

        if (SpecType.size0.getEnumValue().equals(staceType)) {
            return SpecType.size0.getEnumText();
        }
        if (SpecType.size10.getEnumValue().equals(staceType)) {
            return SpecType.size10.getEnumText();
        }
        if (SpecType.size30.getEnumValue().equals(staceType)) {
            return SpecType.size30.getEnumText();
        }
        if (SpecType.size100.getEnumValue().equals(staceType)) {
            return SpecType.size100.getEnumText();
        }
        if (SpecType.size120.getEnumValue().equals(staceType)) {
            return SpecType.size120.getEnumText();
        }
        if (SpecType.size130.getEnumValue().equals(staceType)) {
            return SpecType.size130.getEnumText();
        } else {
            return "";
        }

    }


//    private LinearLayout list_item_adress;

//    private ImageView iv_edit;

    private TextView tv_contanct_name;//苗圃名称
    private TextView tv_con_name_phone;//苗圃名称

    private TextView tv_address_name;

//    private TextView tv_is_defoloat;

    private LinearLayout ll_05;

    private TextView tv_count;
    private EditText et_maxCrown;

    private ACache mCache;

//    private TextView id_tv_edit_all;
    private EditText et_crown;
    private EditText et_height;
    private EditText et_maxHeight;
    //    private EditText et_minSpec;

    private EditText et_max_Spec;
    private EditText et_min_Spec;
    private TextView tv_title;


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                if (view.getId() == R.id.btn_back) {
                    onBackPressed();
                } else if (view.getId() == R.id.list_item_adress) {

                } else if (view.getId() == R.id.ll_05) {

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


    private class PhotoGvOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            GalleryImageActivity.startGalleryImageActivity(
                    MiaoDetailActivity.this, position, photoGv.getAdapter().getDataList());

        }
    }


    //传入初始化 的图片资源  初始化顶部  图片列表控件
    private void initGvTop(MeasureGridView photoGv, ArrayList<Pic> arrayList2Adapter) {
//        arrayList2Adapter.clear();
//            arrayList.add(new Pic("hellow", true, MeasureGridView.usrl, 1));
//            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));
        if (photoGv != null) {
            photoGv.init(this, urlPaths, (ViewGroup) findViewById(R.id.ll_mainView), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
                @Override
                public void onTakePic() {

                }

                @Override
                public void onChoosePic() {

                }

                @Override
                public void onCancle() {
                    D.e("===========onCancle=============");
                }
            });

            photoGv.getAdapter().closeAll(true);
            photoGv.getAdapter().notifyDataSetChanged();

        }


        AbSlidingPlayView viewPager = (AbSlidingPlayView) findViewById(R.id.viewPager_menu);
        if (viewPager != null) {
            // 设置播放方式为顺序播放
            viewPager.setPlayType(1);
            // 设置播放间隔时间
            viewPager.setSleepTime(3000);
//            LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            WindowManager wm = this.getWindowManager();
//            l_params.height = wm.getDefaultDisplay().getWidth();
//            viewPager.setLayoutParams(l_params);
            initViewPager(viewPager, urlPaths);
        }


    }














    private void initViewPager(AbSlidingPlayView viewPager, ArrayList<Pic> urlPaths) {


        for (int i = 0; i < urlPaths.size(); i++) {
            // 导入ViewPager的布局
            View view = LayoutInflater.from(this).inflate(R.layout.pic_item,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            // fb.display(imageView, banners.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(urlPaths.get(i).getUrl(), imageView);
            viewPager.addView(view);
        }
        // 开始轮播
        if (!viewPager.isPlaying()) {
            viewPager.startPlay();
        }


        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
            @Override
            public void onClick(int position) {

                GalleryImageActivity.startGalleryImageActivity(
                        mActivity, position, urlPaths);
            }
        });
    }


    public void CallPhone(View view) {
        postWhoPhone(id, contactPhone, ConstantState.TYPE_NURSERY);
        FlowerDetailActivity.CallPhone(contactPhone, mActivity);
        if (TextUtils.isEmpty(contactPhone)) {
            ToastUtil.showShortToast("电话号码未填写");
        }
    }

    public void CallPhone1(View view) {
        postWhoPhone(id, ownerPhone, ConstantState.TYPE_OWNER);
        FlowerDetailActivity.CallPhone(ownerPhone, mActivity);
        if (TextUtils.isEmpty(ownerPhone)) {
            ToastUtil.showShortToast("电话号码未填写");
        }
    }


    /**
     * private String callSourceType;//seedling、seedlingNote
     * <p>
     * private String callSourceId;//资源ID
     * <p>
     * private String userId;//当前用户ID
     * <p>
     * private String callPhone;//被呼叫号码
     * <p>
     * private String callTargetType;//被呼叫号码类型：owner(发布人)、nursery(苗圃)
     * /callLog/save
     */

    public static void postWhoPhone(String callSourceId, String callPhone, String callTargetType) {
        new BasePresenter()
                .putParams("callSourceType", "seedlingNote")
                .putParams("callSourceId", callSourceId)
                .putParams("userId", MyApplication.Userinfo.getString("id", ""))
                .putParams("callPhone", callPhone)
                .putParams("callTargetType", callTargetType)
                .doRequest("callLog/save", true, new AjaxCallBack() {
                    @Override
                    public void onStart() {
                        Log.e("postWhoPhone", "onStart: -------------拨打电话记录日志");
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.e("接口", "json" + o.toString());
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        Log.e("接口failure", strMsg);
                    }
                });
    }




}
