package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseWebViewActivity;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

/**
 * 积分详情界面
 */

public class IntegralDetailsActivity extends BaseWebViewActivity {


    @Override
    public String BaseUrl() {
//        String head = "http://192.168.1.252:8090/";
        String head = GetServerUrl.getHtmlUrl();
        //http://192.168.1.252:8090
//         <linzj183@qq.com>  下午 4:31:05
//                /app/userPoint/detail

        String url = head + "app/userPoint/detail?uid=" + MyApplication.Userinfo.getString("id", "") + "&token=" + GetServerUrl.getKeyStr(System.currentTimeMillis());

        D.e("=======BaseUrl===url===\n" + url);

        return url;
    }
//uId=XXXX&token=XXXXX

    @Override
    public String setTitle() {
        return "积分明细";
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, IntegralDetailsActivity.class));
    }


}
