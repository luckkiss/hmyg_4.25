package com.hldj.hmyg.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.hldj.hmyg.application.MyApplication;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MLocationManager implements LocationSource {

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mLocationClient;
    private AMapLocationListener mListener;

    private static MLocationManager manager;


    private MLocationManager() {
//        initLoaction();//初始化定位的  各种参数
    }


    public static MLocationManager getInstance() {
        synchronized (new Object()) {
            if (manager == null) {
                manager = new MLocationManager();
            }
            return manager;
        }

    }


    private void initLoaction() {


        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(MyApplication.getInstance());


        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//       mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);


        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);


        /**
         * //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
         mLocationOption.setInterval(1000);
         */


        /**
         * 设置是否返回地址信息（默认返回地址信息）
         mLocationOption.setNeedAddress(true);
         */


        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

//单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);


        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);//默认是开启


        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mListener = location -> {

            if (onAddrResultListener != null) {
                onAddrResultListener.onAddrResult(location);
            }
        };
        mLocationClient.setLocationListener(mListener);
//启动定位
        mLocationClient.startLocation();


    }


    /**
     * 开启定位
     */
    public MLocationManager startLoaction(OnAddrResultListener onAddrResultListener) {

        this.onAddrResultListener = onAddrResultListener;
        activate(null);
        return this;
    }

    /**
     * 关闭定位
     */
    public MLocationManager stopLoaction() {
        deactivate();
        return this;
    }


    //开启定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        initLoaction();//激活定位
    }

    //结束定位
    @Override
    public void deactivate() {

        //停止定位
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    public interface OnAddrResultListener {
        void onAddrResult(AMapLocation result);
    }


    OnAddrResultListener onAddrResultListener;

    public void setOnAddrResultListener(OnAddrResultListener onAddrResultListener) {

        this.onAddrResultListener = onAddrResultListener;
    }
}
