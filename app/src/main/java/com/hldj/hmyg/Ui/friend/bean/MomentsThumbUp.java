package com.hldj.hmyg.Ui.friend.bean;


import java.io.Serializable;

/**
 * 朋友圈评论Entity
 *
 * @author luow
 * @version 2017-11-24
 */
public class MomentsThumbUp implements Serializable {

    public static final long serialVersionUID = 4307497280064329128L;


    /**
     * 发布内容id
     */
    public String momentsId;

    /**
     * 时间戳
     */
    public long timeStamp;

    /**
     * 点赞人ID
     */
    public String ownerId;


    /***********临时属性  开始*****************/

    /**
     * 用户
     */
//    public User ownerUser;
//    public User ownerUserJson;

    /**
     * 时间戳格式化
     */
    public String timeStampStr;

    public AttrDataBean attrData = new AttrDataBean();

    /***********临时属性  结束*****************/


    public static class AttrDataBean implements Serializable {
        /**
         * fromDisplayPhone : 17074990702
         * fromDisplayName : 最软科技有限
         * fromHeadImage : http://image.hmeg.cn/upload/image/201711/a9c891b3d664460d97a983605025563f.png
         * fromUserId : 2876f7e0f51c4153aadc603b661fedfa
         */

        /**
         * momentsThumbUp.putData("userId", owner.getId());
         * momentsThumbUp.putData("displayName", owner.getDisplayName());
         * momentsThumbUp.putData("displayPhone", owner.getDisplayPhone());
         * momentsThumbUp.putData("headImage", owner.getHeadImage());
         * momentsThumbUp.putData("storeId", owner.getStoreId());
         */
        public String userId;
        public String displayName;
        public String displayPhone;
        public String headImage;
        public String storeId;
    }


}