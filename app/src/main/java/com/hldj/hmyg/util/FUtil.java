package com.hldj.hmyg.util;

import android.text.TextUtils;

/**
 *
 */

public class FUtil {


    public static String choseOne(String str1, String str2) {
        if (!TextUtils.isEmpty(str1)) {
            return str1;
        }

        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        return "";
    }


    public static String $(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }


    /**
     * 为  str 添加一个头
     *
     * @param head
     * @param str
     * @return
     */
    public static String $_head(String head, String str) {
        if (TextUtils.isEmpty(str)) {
            return "-";
        }
        return head + str;
    }

    /**
     * 为  str 添加一个头
     *
     * @param head
     * @param str
     * @return
     */
    public static String $_head_no_(String head, String str) {
        if (TextUtils.isEmpty(str) || str.equals("0")) {
            return "";
        }
        return head + str;
    }


    public static String $_zero(String str) {
        if (TextUtils.isEmpty(str) || "0".equals(str)) {
            return "-";
        }
        return str;
    }

    public static String $_null_to_you_say(String str, String youSay) {
        if (TextUtils.isEmpty(str) || "0".equals(str)) {
            return youSay;
        }
        return str;
    }


    public static String $_zero_2_null(String str) {
        if ("0".equals(str) || TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    /**
     * 过滤 一个数组
     *
     * @param
     * @param strs 传进来的  string 数组  根据每个 的顺序 凭借 split unit  来组成新的数组
     * @return
     */


    public static String $(String split, String... strs) {

        int newSize = 0;
        StringBuffer buffer = new StringBuffer();

        //先过滤掉 strs 数组中 为空的 字段
        for (int i = 0; i < strs.length; i++) {
            if (!TextUtils.isEmpty(strs[i]) && !"0".equals(strs[i])) {
                newSize++;//便利 查找非空长度
            }
        }
        String[] newStrs = new String[newSize];
        newSize = 0;

        for (int i = 0; i < strs.length; i++) {
            if (!TextUtils.isEmpty(strs[i]) && !"0".equals(strs[i])) {
                newStrs[newSize] = strs[i];
                newSize++;//便利 查找非空长度
            }
        }
        for (int i = 0; i < newStrs.length; i++) {
            if (i == 0) {
                buffer.append(newStrs[i]);
            } else {
                buffer.append(" " + split + " " + newStrs[i]);
            }
        }

//        for (String str : strs) {
//            if (!TextUtils.isEmpty(str)) {
//                buffer.append(str);
//                buffer.append(split);
//            }
//        }

        return buffer.toString();
    }


}
