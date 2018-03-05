package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.saler.P.BasePresenter;

/**
 * 请求  消息
 */

public class AActivityPresenter {


    public static boolean isShowRead = false;


    //notice/unReadCount

    /**
     * 大部分公用的接口   请求未读消息列表
     *
     * @param callBack
     */
    public static void requestUnReadCount(HandlerAjaxCallBack callBack) {
        if (MyApplication.Userinfo.getBoolean("isLogin", false))
        new BasePresenter()
                .doRequest("admin/notice/unReadCount", true, callBack);
    }


    /**
     * 清除异地登录
     *
     * @param callBack
     */
    public static void otherUserGetOut(HandlerAjaxCallBack callBack) {
        new BasePresenter()
                .doRequest("admin/user/otherUserGetOut", true, callBack);
    }






}
