package com.hldj.hmyg.Ui.friend.presenter;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.saler.P.BasePresenter;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class FriendPresenter {

    /**
     * 朋友圈 帖子加入收藏
     */
    public static void doCollect(String id, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("sourceId", id)
                .putParams("type", "moment")
                .doRequest("admin/collect/save", true, ajaxCallBack);
    }


    /**
     * 朋友圈 帖子收藏  列表
     */
    public static void momentsCollect(String id, AjaxCallBack<String> ajaxCallBack) {
        new BasePresenter()
                .putParams("sourceId", id)
                .putParams("type", "moment")
                .doRequest("admin/collect/listMoment", true, ajaxCallBack);
    }


}
