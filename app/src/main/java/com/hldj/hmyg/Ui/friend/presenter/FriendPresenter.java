package com.hldj.hmyg.Ui.friend.presenter;

import android.support.v4.app.FragmentActivity;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.http.AjaxCallBack;

/**
 * 朋友圈公用类
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


    /**
     * 朋友圈 帖子收藏  列表   moment
     */
    public static void doDelete(String id, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("ids", id)
                .putParams("id", id)
                .doRequest("admin/moments/doDel", true, ajaxCallBack);
    }

    /**
     * 朋友圈 帖子收藏  列表   moment
     */
    public static void doDeleteReply(String id, String fromId, HandlerAjaxCallBack ajaxCallBack) {
        new BasePresenter()
                .putParams("ids", id)
                .putParams("id", id)
                .putParams("fromId", fromId)
                .doRequest("admin/momentsReply/doDel", true, ajaxCallBack);
    }


    /**
     * 执行分享方法
     */
    public static void share(FragmentActivity activity, Moments item) {
        D.e("分享");
        ComonShareDialogFragment.newInstance()
                .setShareBean(new ComonShareDialogFragment.ShareBean(
                        "花木易购苗木圈",
                        item.content,
                        "desc",
                        (item.imagesJson != null && item.imagesJson.size() > 0) ? item.imagesJson.get(0).ossMediumImagePath : GetServerUrl.ICON_PAHT,
                        GetServerUrl.getHtmlUrl() + "moments/detail/" + item.id + ".html"))
                .show(activity.getSupportFragmentManager(), activity.getClass().getName());
    }


}
