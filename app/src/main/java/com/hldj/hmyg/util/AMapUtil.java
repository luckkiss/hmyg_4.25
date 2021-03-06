package com.hldj.hmyg.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hy.utils.ToastUtil;

import java.io.File;

/**
 * 高德工具类
 */

public class AMapUtil {


    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void goToNaviActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }


    public static void goToNaviBaiDuActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        Intent i1 = new Intent();

// 驾车导航
//latitude, longitude
//        ToastUtil.showLongToast("baidumap://map/navi?location=" + lat +","+  lon);
        i1.setData(Uri.parse("baidumap://map/navi?location=" + lat +","+  lon));

        context.startActivity(i1);
    }


    public static void goToNaviTencentActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        Intent i1 = new Intent();


        /**
         * //调起腾讯地图APP，显示由清华大学到怡和世家小区的驾车路线
         qqmap://map/routeplan?type=drive&from=清华&fromcoord=39.994745,116.247282&to=怡和世家&tocoord=39.867192,116.493187&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
         */
// 驾车导航
//latitude, longitude
//        ToastUtil.showLongToast("baidumap://map/navi?location=" + lat +","+  lon);
        i1.setData(Uri.parse("qqmap://map/routeplan?type=drive&tocoord=" + lat +","+  lon));

        context.startActivity(i1);
    }





    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


}
