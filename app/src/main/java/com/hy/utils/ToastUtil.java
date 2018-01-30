package com.hy.utils;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.white.utils.AndroidUtil;


public class ToastUtil extends CommonToastUtil {


    public static void showPointAdd(String title, String msg) {

        if (AndroidUtil.isClientRunTop(MyApplication.getInstance())) {// 前台运行
            Toast tt = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
//            tt.set("每日登陆");

            View root = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_toast, null);
            TextView tv_title = (TextView) root.findViewById(R.id.title);
            tv_title.setText(title);
            TextView tv_msg = (TextView) root.findViewById(R.id.msg);
            tv_msg.setText(msg);
            tt.setView(root);
            tt.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            tt.show();
        }

    }

}
