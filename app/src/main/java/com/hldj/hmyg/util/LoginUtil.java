package com.hldj.hmyg.util;

import android.app.Activity;
import android.util.Log;

import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.application.MyApplication;

/**
 * 登录 工具类
 */

public class LoginUtil {



    public static boolean isLogin() {

        boolean isLogin = MyApplication.Userinfo.getBoolean("isLogin", false);
        Log.i("LOGIN", "判断是否登录: \n" + isLogin);
        return isLogin;
    }
    public static boolean toLogin(Activity mActivity) {
        /**
         * 判断是否需要登录
         *
         * @return
         */
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start2Activity(mActivity);
//            ToastUtil.showLongToast("请先登录 ^_^ ");
            return false;
        }
    }

}
