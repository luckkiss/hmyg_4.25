package com.hldj.hmyg.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;


/**
 * SharedPreferences工具
 *
 * @author admin
 */
public class SPUtil {

    /**
     * 常用的sp参数管理
     */


    public static final String Json_LoginGsonBean = "Json_LoginGsonBean";
    public static final String USER_ID = "UserID";
    public static final String Json_UserInfoGsonBean = "Json_UserInfoGsonBean";


    public static final String FROM_WHERE = "fromwhere";
    public static final String TO_WHERE = "towhere";
    public static final String MAIN = "main";

    // 个人信息保存到本地
    public static final String PER_INFO = "PER_INFO";

    /**
     * fragment 的tag。。，每个界面的跳转携带参数，记录返回的位置。
     */
    public static final String FRAGMENT_TAG = "fragment_tag";

    /**
     * 保存在手机里的文件名
     */
    public static final String FILE_NAME = "WetekLogistic";
    public static final String ADD_FRIEND = "WetekLogistic_addfriend";
    public static final String FRIENDS = "WetekLogistic_FRIENDS";
    public static final String NAVID = "WetekLogistic_NAVID";
    public static final String GETNAVID = "GETNAVID";

    /**
     * 保存数据
     *
     * @param context 上下文
     * @param key     key
     * @param obj     value
     */
    public static void put(Context context, String key, Object obj) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();

        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        editor.commit();
    }

    public static void put(String spname, Context context, String key,
                           Object obj) {
        SharedPreferences sp = context.getSharedPreferences(spname,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();

        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        editor.commit();
    }

    /**
     * 获取指定的数据
     *
     * @param context
     * @param key
     * @param defaultObj
     * @return
     */
    public static Object get(Context context, String key, Object defaultObj) {
        if (null == context)
            return null;
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                context.MODE_PRIVATE);
        if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        } else if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        }
        return null;
    }




    public static Object get(String spname, Context context, String key,
                             Object defaultObj) {
        SharedPreferences sp = context.getSharedPreferences(spname,
                context.MODE_PRIVATE);

        if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        } else if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (null == defaultObj) {
            return sp.getString(key, (String) defaultObj);
        }
        return null;
    }

    /**
     * 删除指定的数据
     *
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void remove(String spname, Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(spname,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                context.MODE_PRIVATE);
        Map<String, ?> map = sp.getAll();
        return map;
    }

    public static Map<String, ?> getAll(String spname, Context context) {
        SharedPreferences sp = context.getSharedPreferences(spname,
                context.MODE_PRIVATE);
        Map<String, ?> map = sp.getAll();
        return map;
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void clear(String spname, Context context) {
        SharedPreferences sp = context.getSharedPreferences(spname,
                context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


    public static SharedPreferences getShare(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Office",
                Context.MODE_PRIVATE);
        return sp;
    }

    public static Editor getEditor(Context context) {
        Editor sp = context.getSharedPreferences("Office",
                Context.MODE_PRIVATE).edit();

        return sp;
    }

}
