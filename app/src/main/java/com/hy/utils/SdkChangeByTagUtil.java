package com.hy.utils;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class SdkChangeByTagUtil {

    public static String getVersionByLevel(String level) {

        if (level.contains("16")) {
            return "Android 4.1, 4.1.1";
        }
        if (level.contains("17")) {
            return "Android 4.2, 4.2.2";
        }
        if (level.contains("18")) {
            return "Android 4.3";
        }
        if (level.contains("19")) {
            return "Android 4.4";
        }
        if (level.contains("20")) {
            return "Android 4.4w";
        }
        if (level.contains("21")) {
            return "Android 5.0";
        }
        if (level.contains("22")) {
            return "Android 5.1";
        }
        if (level.contains("23")) {
            return "Android 6.0";
        }
        if (level.contains("24")) {
            return "Android 7.0";
        }
        if (level.contains("25")) {
            return "Android 7.0.1";
        }
        if (level.contains("26")) {
            return "Android 8.0";
        }
        if (level.contains("27")) {
            return "Android 8.1";
        }
//        return "不确定：" + level;
        return "9.0 up";

    }


}
