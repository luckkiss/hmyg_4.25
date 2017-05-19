package com.hldj.hmyg.buyer.Ui;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.JsonParserHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CityWheelDialogF extends DialogFragment implements OnWheelChangedListener {

    static CityWheelDialogF wheelDialogF;

    private OnCitySelectListener selectListener;

    public CityWheelDialogF addSelectListener(OnCitySelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }

    public static CityWheelDialogF instance() {

        if (wheelDialogF == null) {

            wheelDialogF = new CityWheelDialogF();
        }
        return wheelDialogF;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialog);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        getDialog().setCanceledOnTouchOutside(true);// 点击边际可消失
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dia_choose_city_new, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = (int) (getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth());
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }


    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
//        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
////          View dia_choose_share = inflater.inflate(R.layout.dia_choose_city, null);
//        dialog.setContentView(R.layout.dia_choose_city);
//        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
//
//        // 设置宽度为屏宽, 靠近屏幕底部。
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM; // 紧贴底部
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        window.setAttributes(lp);
//
////        ButterKnife.bind(this, dialog); // Dialog即View
//
////        initClickTypes();
//
//        return dialog;
//    }

    private void initView(View dialog) {
        ((ViewGroup) dialog.findViewById(R.id.ll_wheel_bottom)).setBackgroundColor(Color.WHITE);
//        View view = dialog.inflate(R.layout.whell_city,null);
        mViewProvince = (WheelView) dialog.findViewById(R.id.id_province);
        mViewCity = (WheelView) dialog.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) dialog.findViewById(R.id.id_district);
        mViewDistrict.setVisibility(View.GONE);

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        setUpData();

        TextView tv_sure = (TextView) dialog.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(v -> {
            selectListener.onCitySelect(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName, mCurrentZipCode);
            this.dismiss();
        });

    }

    private WheelView mViewCity;
    private WheelView mViewProvince;
    private WheelView mViewDistrict;

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        super.onActivityCreated(savedInstanceState);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//
//
//    }

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private JSONObject jObject;

    /**
     * 解析省市区的XML数据
     */

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getActivity().getAssets();
        try {
            // InputStream input = asset.open("city_json2.rtf");
            InputStream input = asset.open("document.json");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            String json = new String(buffer, "utf-8");
            input.close();
            jObject = new JSONObject(json);
            JSONArray array = jObject.getJSONObject("data").getJSONArray(
                    "bannerList");
            JsonParserHandler handler = new JsonParserHandler(array);
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            // */ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0)
                            .getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            // */
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
//        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(cityNames[j] + districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    private void setUpData() {
        initProvinceDatas();//获取省集合  与省集合代表的code 集合
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
                getActivity(), mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
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
            // twCityfullName = "";
            // twCitycityCode = "";
            // tv_twcity.setText(twCityfullName);
        } else if (wheel == mViewCity) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentCityName
                    + mCurrentDistrictName);
            // twCityfullName = "";
            // twCitycityCode = "";
            // tv_twcity.setText(twCityfullName);
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentCityName
                    + mCurrentDistrictName);
            // twCityfullName = "";
            // twCitycityCode = "";
            // tv_twcity.setText(twCityfullName);
        } else if (wheel == id_Childs) {
            twCityfullName = mStreetDatas[newValue];
            twCitycityCode = mStreetIdDatas[newValue];
        }
    }

    private WheelView id_Childs;
    protected String[] mStreetDatas;
    protected String[] mStreetIdDatas;
    private String twCityfullName = "";
    private String twCitycityCode = "";

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
                .setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), areas));
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
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void showSelectedResult() {
        Toast.makeText(
                getActivity(),
                "当前选中:" + mCurrentProviceName + "," + mCurrentCityName + ","
                        + mCurrentDistrictName + "," + mCurrentZipCode,
                Toast.LENGTH_SHORT).show();


    }

    public interface OnCitySelectListener {
        void onCitySelect(String province, String city, String distrect, String cityCode);
    }

}
