package com.hy.utils;

import android.content.Context;
import android.widget.Toast;

import com.hldj.hmyg.application.MyApplication;
import com.white.utils.AndroidUtil;

public class CommonToastUtil {

    static Toast t = null;

    public static void showShortToast(Context context, String text) {
        if (AndroidUtil.isClientRunTop(context)) {// 前台运行
            if (t == null) {
                t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                t.setText(text);
            }
            t.show();
        }
    }

    public static void showShortToast(String text) {
        if (AndroidUtil.isClientRunTop(MyApplication.getInstance())) {// 前台运行
            if (t == null) {
                t = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                t.setText(text);
            }
            t.show();
        }
    }

    public static void showLongToast(String text) {
        if (AndroidUtil.isClientRunTop(MyApplication.getInstance())) {// 前台运行
            if (t == null) {
                t = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_LONG);
            } else {
                t.setText(text);
            }
            t.show();
        }
    }


    /**
     * Toast.makeText(MainActivity.this,"我是一个屏蔽通知我也是可以显示的Toast",Toast.LENGTH_SHORT).show();
     * @param resId
     */
    public static void showShortToast(int resId) {
        if (AndroidUtil.isClientRunTop(MyApplication.getInstance())) {// 前台运行
            if (t == null) {
                t = Toast.makeText(MyApplication.getInstance(), MyApplication.getInstance().getString(resId), Toast.LENGTH_SHORT);
            } else {
                t.setText(MyApplication.getInstance().getString(resId));
            }
            t.show();
        }
    }

    public static void showShortToast_All(Context context, String text) {
        if (t == null) {
            t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            t.setText(text);
        }
        t.show();
    }

    public static void showShortToast(Context context, int textRid) {
        if (AndroidUtil.isClientRunTop(context)) {// 前台运行
            if (t == null) {
                t = Toast.makeText(context, textRid, Toast.LENGTH_SHORT);
            } else {
                t.setText(textRid);
            }
            t.show();
        }
    }

    public static void showShortToast_All(Context context, int textRid) {
        if (t == null) {
            t = Toast.makeText(context, textRid, Toast.LENGTH_SHORT);
        } else {
            t.setText(textRid);
        }
        t.show();
    }

    public static void cancelToast() {
        if (t != null) {
            t.cancel();
        }
    }
}
