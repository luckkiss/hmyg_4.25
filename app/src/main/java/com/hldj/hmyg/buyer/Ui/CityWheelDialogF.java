package com.hldj.hmyg.buyer.Ui;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter_new;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CityWheelDialogF extends DialogFragment implements OnWheelChangedListener {

    static CityWheelDialogF wheelDialogF;


    private OnCitySelectListener selectListener;
    private CityGsonBean gsonBean;
    private ArrayWheelAdapter_new arrayWheelAdapterNew;
    private ArrayWheelAdapter_new arrayWheelAdapterNew_dir;

    public CityWheelDialogF addSelectListener(OnCitySelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }

    public static CityWheelDialogF instance() {

        wheelDialogF = new CityWheelDialogF();

        return wheelDialogF;
    }


    boolean isShow全国 = false;
    boolean isShowCity = false;
    boolean isShowDistrict = false;

    public CityWheelDialogF setNoShowCity() {
        isShowCity = false;
        return this;
    }

    public CityWheelDialogF isShowCity(boolean flag) {
        isShowCity = flag;
        return this;
    }

    public CityWheelDialogF isShow全国(boolean flag) {
        this.isShow全国 = flag;
        return this;
    }

    public CityWheelDialogF isShowDistrict(boolean flag) {
        isShowDistrict = flag;
        return this;
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
        lp.width = getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }


    private void initView(View dialog) {
        dialog.findViewById(R.id.ll_wheel_bottom).setBackgroundColor(Color.WHITE);
//        View view = dialog.inflate(R.layout.whell_city,null);
        mViewProvince = (WheelView) dialog.findViewById(R.id.id_province);

        mViewCity = (WheelView) dialog.findViewById(R.id.id_city);
        mViewCity.setVisibility(isShowCity ? View.VISIBLE : View.GONE);
        mViewDistrict = (WheelView) dialog.findViewById(R.id.id_district);
        mViewDistrict.setVisibility(isShowDistrict ? View.VISIBLE : View.GONE);

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        setUpData();

        TextView tv_sure = (TextView) dialog.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(v -> {

            if (selectListener != null) {
                selectListener.onCitySelect(childBeans);
                selectListener.onProvinceSelect(privBeans);
            }
            if (selectListener instanceof OnCitySelectListenerWrap) {
                ((OnCitySelectListenerWrap) selectListener).onDistrectSelect(disBeans);
            }
            this.dismiss();
        });


    }

    private WheelView mViewCity;
    private WheelView mViewProvince;
    private WheelView mViewDistrict;


    protected void initProvinceDatas() {
        arrayWheelAdapterNew = new ArrayWheelAdapter_new(getActivity(), null);
//        arrayWheelAdapterNew.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.black_de));
//        mViewCity.setWheelForeground(R.drawable.white_radius);
//        mViewCity.setWheelBackground(R.drawable.bg_white);
//        mViewCity.setBackgroundColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.white));
        mViewCity.setViewAdapter(arrayWheelAdapterNew);

        arrayWheelAdapterNew_dir = new ArrayWheelAdapter_new(getActivity(), null);


        mViewDistrict.setViewAdapter(arrayWheelAdapterNew_dir);
        arrayWheelAdapterNew_dir.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.black_de));

        /**
         *  // InputStream input = asset.open("city_json2.rtf");
         InputStream input = asset.open("document.json");
         byte[] buffer = new byte[input.available()];
         input.read(buffer);
         String json = new String(buffer, "utf-8");
         input.close();
         */
        long now_time = System.currentTimeMillis();
        AssetManager asset = getActivity().getAssets();
        try {
            InputStream input = asset.open("document.json");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            String json = new String(buffer, "utf-8");
            input.close();
            gsonBean = GsonUtil.formateJson2Bean(json, CityGsonBean.class);

            D.e("======gsonBean=====" + gsonBean);

            D.e("==json解析时长==========" + (System.currentTimeMillis() - now_time) + " 毫秒");

//            BufferedReader bufferedReader = new BufferedReader(new FileReader(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * "id": "1",
     * "name": "北京市",
     * "fullName": "北京市",
     * "cityCode": "11",
     * "parentCode": "0",
     * "level": 1,
     */
    public CityGsonBean.ChildBeans 全国(int level) {
        if (level > 3) {
            return null;
        }
        CityGsonBean.ChildBeans childBeans = new CityGsonBean.ChildBeans();
        childBeans.id = "0";
        childBeans.name = "全国";
        childBeans.fullName = "全国";
        childBeans.cityCode = "";
        childBeans.parentCode = "";
        childBeans.level = level + "";
        List<CityGsonBean.ChildBeans> childBeans1 = new ArrayList<>();
        childBeans1.add(全国(++level));
        childBeans.childs = childBeans1;
        return childBeans;
    }

    private void setUpData() {
        initProvinceDatas();//获取省集合  与省集合代表的code 集合

        if (isShow全国) {
            gsonBean.data.bannerList.add(0, 全国(1));
        }

        mViewProvince.setViewAdapter(new ArrayWheelAdapter_new(getActivity(), gsonBean.data.bannerList));
        if (isShowCity) {
            arrayWheelAdapterNew.notifyAllDatas1(gsonBean.data.bannerList.get(0).childs);

            childBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent);
        }

        if (isShowDistrict) {
            arrayWheelAdapterNew_dir.notifyAllDatas1(gsonBean.data.bannerList.get(0).childs.get(0).childs);
            disBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent).childs.get(dCurrent);
        }


        privBeans = gsonBean.data.bannerList.get(pCurrent);

        if (isShowCity) {
            gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent);
        }

        if (isShowDistrict) {
            disBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent).childs.get(0);
        }

    }

    int pCurrent = 0;
    int cCurrent = 0;
    int dCurrent = 0;

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            pCurrent = mViewProvince.getCurrentItem();
            arrayWheelAdapterNew.notifyAllDatas1(gsonBean.data.bannerList.get(pCurrent).childs);
            mViewCity.setCurrentItem(0);
            arrayWheelAdapterNew_dir.notifyAllDatas1(gsonBean.data.bannerList.get(pCurrent).childs.get(0).childs);
            mViewDistrict.setCurrentItem(0);
            privBeans = gsonBean.data.bannerList.get(pCurrent);
            if (isShowCity) {
                childBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent);
                D.e("==childBeans==" + childBeans.toString());
            }
            if (isShowDistrict) {
                disBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent).childs.get(0);
            }


        } else if (wheel == mViewCity) {

            if (isShowCity) {
                cCurrent = mViewCity.getCurrentItem();
                childBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent);

                arrayWheelAdapterNew_dir.notifyAllDatas1(childBeans.childs);


                try {
                    disBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent).childs.get(0);
                } catch (Exception e) {
                    disBeans = childBeans;
                    arrayWheelAdapterNew_dir.notifyAllDatas1(new ArrayList<>());
                    e.printStackTrace();
                }
            }

            /**
             *  private WheelView mViewCity;
             private WheelView mViewProvince;
             private WheelView mViewDistrict;
             */
            D.e("==childBeans==" + childBeans.toString());
        } else if (wheel == mViewDistrict) {
            if (isShowDistrict) {
                dCurrent = mViewDistrict.getCurrentItem();
                disBeans = gsonBean.data.bannerList.get(pCurrent).childs.get(cCurrent).childs.get(dCurrent);
            }

        }
    }


    public CityGsonBean.ChildBeans disBeans = null;
    public CityGsonBean.ChildBeans childBeans = null;
    public CityGsonBean.ChildBeans privBeans = null;


    public interface OnCitySelectListener {
        void onCitySelect(CityGsonBean.ChildBeans childBeans);

        void onProvinceSelect(CityGsonBean.ChildBeans childBeans);
    }


    /**
     * 包装类 ，，，提出没用接口
     */
    public abstract static class OnCitySelectListenerWrap implements OnCitySelectListener {

        @Override
        public void onCitySelect(CityGsonBean.ChildBeans childBeans) {

        }

        @Override
        public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

        }

        public abstract void onDistrectSelect(CityGsonBean.ChildBeans distrect);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        wheelDialogF = null;
    }
}
