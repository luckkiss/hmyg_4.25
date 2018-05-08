package com.hldj.hmyg.Ui.friend.util;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBackSimpaleData;
import com.hldj.hmyg.saler.P.BasePresenter;

/**
 * 记录日志工具类
 */

public class CallLogUtil {



    public static void log_head_detail()
    {

        new BasePresenter()
                .putParams("","")
                .putParams("","")
                .putParams("","")
                .putParams("","")
                .putParams("","")
                .putParams("","")

                .doRequest("admin/callLog/detail", new HandlerAjaxCallBackSimpaleData() {
                    @Override
                    public void onRealSuccess(Object result) {

                    }
                });
    }


















}
