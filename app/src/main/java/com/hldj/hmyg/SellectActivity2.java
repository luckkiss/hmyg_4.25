package com.hldj.hmyg;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.mrwujay.cascade.activity.BaseSecondActivity;
import com.mrwujay.cascade.activity.GetCodeByName;
import com.yangfuhai.asimplecachedemo.lib.ACache;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import me.drakeet.materialdialog.MaterialDialog;

public class SellectActivity2 extends BaseSecondActivity implements OnWheelChangedListener {
    MaterialDialog mMaterialDialog;
    private static String type01 = ""; // planted,
    private String type02 = ""; // transplant,
    private String type03 = ""; // heelin,
    private String type04 = ""; // container,

    private LinearLayout ll_area;
    private LinearLayout ll_price;
    private Dialog dialog;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView tv_area;
    private String cityCode = "";
    private String cityName = "";

    private ArrayList<String> danwei_names = new ArrayList<String>();
    private ArrayList<String> danwei_ids = new ArrayList<String>();
    private ArrayList<String> planttype_names = new ArrayList<String>();
    private ArrayList<String> planttype_ids = new ArrayList<String>();

    //    private TagFlowLayout mFlowLayout1;
    private TagFlowLayout mFlowLayout2;
    private TagFlowLayout mFlowLayout3;

    private ACache mCache;

    private EditText et_min_guige;//最大规格
    private EditText et_max_guige;//最小规格

    private EditText et_pinming;
    private String searchSpec = "";
    private String plantTypes = "";
    private ArrayList<String> planttype_has_ids = new ArrayList<String>();
    private String searchKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect2);
        mCache = ACache.get(this);
        mMaterialDialog = new MaterialDialog(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        TextView iv_reset = (TextView) findViewById(R.id.iv_reset);
        ll_area = (LinearLayout) findViewById(R.id.ll_area);
        ll_price = (LinearLayout) findViewById(R.id.ll_price);
        et_pinming = (EditText) findViewById(R.id.et_pinming);
        et_min_guige = (EditText) findViewById(R.id.et_min_guige);
        et_max_guige = (EditText) findViewById(R.id.et_max_guige);


        tv_area = (TextView) findViewById(R.id.tv_area);

        mFlowLayout2 = (TagFlowLayout) findViewById(R.id.id_flowlayout2);
        mFlowLayout2.setMaxSelectCount(1);
        mFlowLayout3 = (TagFlowLayout) findViewById(R.id.id_flowlayout3);
        TextView sure = (TextView) findViewById(R.id.sure);
        initData();
        initSearch();

        btn_back.setOnClickListener(multipleClickProcess);
        iv_reset.setOnClickListener(multipleClickProcess);
        ll_area.setOnClickListener(multipleClickProcess);

        sure.setOnClickListener(multipleClickProcess);
    }

    private void initSearch() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("searchKey", searchKey);
        finalHttp.post(GetServerUrl.getUrl() + "seedling/initSearch", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                mCache.remove("initSearch");
                mCache.put("initSearch", t.toString());
                LoadCache(t.toString());
                super.onSuccess(t);
            }

            @Override
            public void onFailure(Throwable t, int errorNo,
                                  String strMsg) {
                // TODO Auto-generated method stub
                if (mCache.getAsString("initSearch") != null && !"".equals(mCache.getAsString("initSearch"))) {
                    LoadCache(mCache.getAsString("initSearch"));
                }
                super.onFailure(t, errorNo, strMsg);
            }

            private void LoadCache(String t) {
                // TODO Auto-generated method stub

                TypesBean typesBean = GsonUtil.formateJson2Bean(t, TypesBean.class);
                if (!typesBean.code.equals(ConstantState.SUCCEED_CODE)) {
                    ToastUtil.showShortToast(typesBean.msg);
                    return;
                }
                if (typesBean.data.plantTypeList != null) {
                    initPlantTypeList(typesBean.data.plantTypeList);
                }
                if (typesBean.data.specList != null) {
                    initSpecList(typesBean.data.specList);
                }

            }


        });
    }


    private void initSpecList(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> specList) {
        {
            for (int i = 0; i < specList.size(); i++) {
                danwei_names.add(specList.get(i).getText());
                danwei_ids.add(specList.get(i).getValue());
            }
            if (danwei_names.size() > 0) {
                SaveSeedlingPresenter.initAutoLayout2(mFlowLayout2, specList, 1, SellectActivity2.this, (view, position, parent) ->
                        {
                            D.e("==view被点击了===" + view.isSelected());
                            D.e("==parent被点击了===" + parent.isSelected());
                            return false;
                        }
                );

//                for (int i = 0; i < danwei_ids.size(); i++) {
//                    if (searchSpec.equals(danwei_ids.get(i))) {
//                        int a = i;
//                        adapter2.setSelectedList(a);
//                    }
//                }
            }
        }
    }

    /**
     * 初始化  植物选项
     *
     * @param plantTypeList 种植类型  集合
     */
    private void initPlantTypeList(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList) {
        if (plantTypeList.size() > 0) {

            for (int i = 0; i < plantTypeList.size(); i++) {
                planttype_names.add(plantTypeList.get(i).getText());
                planttype_ids.add(plantTypeList.get(i).getValue());
            }

            if (planttype_names.size() > 0) {

                SaveSeedlingPresenter.initAutoLayout2(mFlowLayout3, plantTypeList, 1, SellectActivity2.this, (view, position, parent) ->
                        {
                            D.e("==view被点击了===" + view.isSelected());
                            D.e("==parent被点击了===" + parent.isSelected());
                            return false;
                        }
                );

                mFlowLayout3.setMaxSelectCount(4);
                mFlowLayout3.getAdapter().setSelectedList(0, 1, 3);
                mFlowLayout3.setOnSelectListener(selectPosSet -> {
                    for (Integer setItem : selectPosSet) {
                        D.e("===================" + setItem);
                    }
                });

            }


        }
    }

    public void initData() {
        String from = getIntent().getStringExtra("from");
        if ("StorePurchaseListActivity".equals(from)) {
            ll_price.setVisibility(View.GONE);
        }
        cityCode = getIntent().getStringExtra("cityCode");
        cityName = getIntent().getStringExtra("cityName");

        planttype_has_ids = getIntent().getStringArrayListExtra(
                "planttype_has_ids");
        searchSpec = getIntent().getStringExtra("searchSpec");
        String specMinValue = getIntent().getStringExtra("specMinValue");
        String specMaxValue = getIntent().getStringExtra("specMaxValue");
        searchKey = getIntent().getStringExtra("searchKey");


        et_max_guige.setText(specMaxValue);//最小厘米
        et_min_guige.setText(specMinValue);//最大厘米
        if (et_min_guige.getText().toString().length() > 0
                || et_max_guige.getText().toString().length() > 0) {
            mFlowLayout2.setVisibility(View.VISIBLE);
        } else {
            mFlowLayout2.setVisibility(View.VISIBLE);
        }


        et_pinming.setText(searchKey);
        mCurrentZipCode = cityCode;
        tv_area.setText(cityName);


    }

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.ll_area:
                        showCitys();
                        initWheelView(cityName);//根据省市名字 滚动到相应位置
                        break;
                    case R.id.iv_reset:
                        searchSpec = "";
//                        guige_has_click_names.clear();
                        et_pinming.setText("");
                        et_min_guige.setText("");
                        et_max_guige.setText("");
                        planttype_has_ids.clear();
//                        mFlowLayout1.setAdapter(adapter1);
                        mFlowLayout2.getAdapter().resetList();
                        mFlowLayout3.getAdapter().resetList();
                        cityCode = "";
                        cityName = "全国";
                        tv_area.setText(cityName);
                        type01 = "";
                        type02 = "";
                        type03 = "";
                        type04 = "";

                        break;

                    case R.id.sure:
                        Intent intent = new Intent();
                        String str = GetCodeByName.initProvinceDatas(SellectActivity2.this, mCurrentProviceName, mCurrentCityName);
                        if (TextUtils.isEmpty(str)) {
                            intent.putExtra("cityCode", "");
                        } else {
                            intent.putExtra("cityCode", str);
                        }
                        intent.putExtra("cityName", cityName);
                        intent.putExtra("plantTypes", type01 + type02 + type03 + type04);
                        intent.putStringArrayListExtra("planttype_has_ids", planttype_has_ids);
                        intent.putExtra("searchSpec", searchSpec);
                        intent.putExtra("specMinValue", et_min_guige.getText().toString());
                        intent.putExtra("specMaxValue", et_max_guige.getText().toString());
                        intent.putExtra("searchKey", et_pinming.getText().toString());
                        setResult(9, intent);
                        finish();
                        break;

                    default:
                        break;
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
                    Thread.sleep(200);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showCitys() {
        View dia_choose_share = getLayoutInflater().inflate(
                R.layout.dia_choose_city, null);
        TextView tv_sure = (TextView) dia_choose_share
                .findViewById(R.id.tv_sure);
        mViewProvince = (WheelView) dia_choose_share
                .findViewById(R.id.id_province);
        mViewCity = (WheelView) dia_choose_share.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) dia_choose_share
                .findViewById(R.id.id_district);
        mViewDistrict.setVisibility(View.GONE);
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        setUpData();

        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(dia_choose_share, new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        tv_sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cityName = mCurrentProviceName + "\u0020" + mCurrentCityName
                        + "\u0020" + mCurrentDistrictName + "\u0020";
                cityCode = mCurrentZipCode;
                tv_area.setText(cityName);

                if (!SellectActivity2.this.isFinishing() && dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    } else {
                        dialog.show();
                    }
                }

            }
        });

        if (!SellectActivity2.this.isFinishing() && dialog.isShowing()) {
            dialog.cancel();
        } else if (!SellectActivity2.this.isFinishing() && dialog != null
                && !dialog.isShowing()) {
            dialog.show();
        }

    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(SellectActivity2.this, mProvinceDatas));


        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();


    }

    /**
     * 根据名字  滚动到选中的位置
     *
     * @param cityName
     */
    private void initWheelView(String cityName) {
        int oldItem = getItemByName(cityName, mProvinceDatas);
        mViewProvince.setCurrentItem(oldItem);
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        oldItem = getItemByName(cityName, cities);
        mViewCity.setCurrentItem(oldItem);
    }

    private int getItemByName(String cityName, String[] mProvinceDatas) {
        int count = 0;
        for (String iten : mProvinceDatas) {
            if (cityName.contains(iten)) {
                return count;
            }
            count++;
        }
        return 0;
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            // mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentCityName
                    + mCurrentDistrictName);
        } else if (wheel == mViewCity) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentCityName
                    + mCurrentDistrictName);
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentCityName
                    + mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict
                .setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void showSelectedResult() {
        Toast.makeText(
                SellectActivity2.this,
                "当前选中:" + mCurrentProviceName + "," + mCurrentCityName + ","
                        + mCurrentDistrictName + "," + mCurrentZipCode,
                Toast.LENGTH_SHORT).show();
    }


    private static class TypesBean {

        public String code = "";
        public String msg;//测试gson 为空时 会不会报错
        public String msgTest;


        public DataBean data;

        public static class DataBean {

            public List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList;

            public List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> specList;
        }

    }
}
