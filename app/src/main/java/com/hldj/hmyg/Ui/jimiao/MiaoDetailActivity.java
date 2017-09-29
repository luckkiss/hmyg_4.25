package com.hldj.hmyg.Ui.jimiao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
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

    private EditText et_name;

    private EditText et_price;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao_detail);
        mCache = ACache.get(this);


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
        photoGv.setOnItemClickListener(itemClickListener);


        LinearLayout ll_02 = (LinearLayout) findViewById(R.id.ll_02);
        LinearLayout ll_03 = (LinearLayout) findViewById(R.id.ll_03);
        ll_05 = (LinearLayout) findViewById(R.id.ll_05);
        list_item_adress = (LinearLayout) findViewById(R.id.list_item_adress);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setVisibility(View.INVISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //苗圃名称
        tv_contanct_name = (TextView) findViewById(R.id.tv_name);
        /*联系人电话  名称*/
        tv_con_name_phone = (TextView) findViewById(R.id.tv_con_name_phone);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_is_defoloat = (TextView) findViewById(R.id.tv_is_defoloat);
        id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);


        tv_address = (TextView) findViewById(R.id.tv_address);
        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);

        et_count = (EditText) findViewById(R.id.et_count);
        et_height = (EditText) findViewById(R.id.et_height);
        et_maxHeight = (EditText) findViewById(R.id.et_maxHeight);
        et_crown = (EditText) findViewById(R.id.et_crown);
        et_maxCrown = (EditText) findViewById(R.id.et_maxCrown);
//        et_minSpec = (EditText) findViewById(R.id.et_minSpec);
        et_max_Spec = (EditText) findViewById(R.id.et_max_Spec);
        et_min_Spec = (EditText) findViewById(R.id.et_min_Spec);
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
            et_name.setText(getIntent().getStringExtra("name"));
            et_price.setText(getIntent().getStringExtra("price"));
            et_count.setText(count);
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
                list_item_adress.setVisibility(View.VISIBLE);
                ll_05.setVisibility(View.GONE);


                /*苗圃名称*/
                tv_contanct_name.setText("苗圃名称:" + nurseryJson_name);

                tv_con_name_phone.setText("联系人: " + contactName);
//                tv_con_name_phone.setText("联系人: " + contactName + "\u0020" + contactPhone);


                tv_address_name.setText(fullAddress);
                if (isDefault) {
                    tv_is_defoloat.setVisibility(View.GONE);
                } else {
                    tv_is_defoloat.setVisibility(View.GONE);
                }
            }

        } else {

        }

        btn_back.setOnClickListener(multipleClickProcess);
        ll_02.setOnClickListener(multipleClickProcess);
        ll_03.setOnClickListener(multipleClickProcess);
        ll_05.setOnClickListener(multipleClickProcess);

        list_item_adress.setOnClickListener(multipleClickProcess);
        id_tv_edit_all.setOnClickListener(multipleClickProcess);

    }


    private LinearLayout list_item_adress;

    private ImageView iv_edit;

    private TextView tv_contanct_name;//苗圃名称
    private TextView tv_con_name_phone;//苗圃名称

    private TextView tv_address_name;

    private TextView tv_is_defoloat;

    private LinearLayout ll_05;

    private EditText et_count;
    private EditText et_maxCrown;

    private ACache mCache;

    private TextView id_tv_edit_all;
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


    public void CallPhone(View view) {
        postWhoPhone(id,contactPhone , ConstantState.TYPE_NURSERY);
        FlowerDetailActivity.CallPhone(contactPhone, mActivity);
    }

    public void CallPhone1(View view) {
        postWhoPhone(id,ownerPhone , ConstantState.TYPE_OWNER);
        FlowerDetailActivity.CallPhone(ownerPhone, mActivity);
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
                        Log.e("接口failure",strMsg);
                    }
                });
    }


}
