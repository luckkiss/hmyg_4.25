package com.hy.utils;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.white.utils.AndroidUtil;


public class ToastUtil extends CommonToastUtil {


    public static void showPointAdd(String msg) {

        if (AndroidUtil.isClientRunTop(MyApplication.getInstance())) {// 前台运行
            Toast tt = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
//            tt.setText("每日登陆");
            tt.setView(LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_toast, null));
            tt.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            tt.show();
        }

    }

}
