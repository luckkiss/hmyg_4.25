package com.hldj.hmyg.Ui.friend.bean;


import com.hldj.hmyg.bean.UserBean;

import java.io.Serializable;

/**
 * 朋友圈评论Entity
 *
 * @author luow
 * @version 2017-11-24
 */
public class MomentsReply implements Serializable {

    public static final long serialVersionUID = 4307497280064329128L;


    /**
     * 发布内容id
     */
    public String momentsId;

    /**
     * 回复内容
     */
    public String reply;

    /**
     * 时间戳
     */
    public long timeStamp;

    /**
     * 回复人ID
     */
    public String fromId;

    /**
     * 被回复ID
     */
    public String toId;

    /**
     * 是否是隐藏的
     */
    public Boolean isHidden;

    /**
     * 是否是垃圾内容或者被拉黑的
     */
    public Boolean isRubbish;

    /***********临时属性  开始*****************/

    /**
     * 回复人
     */
    public UserBean fromUser;
    public UserBean fromUserJson;
    /**
     * 被回复人
     */
    public UserBean toUser;
    public UserBean toUserJson;

    /**
     * 时间戳格式化
     */

    /***********临时属性  结束*****************/


    public String id;
    public String createBy;
    public String createDate;

    public boolean isOwner;

    public AttrDataBean attrData;

    public static class AttrDataBean implements Serializable {

        /**
         * fromDisplayPhone : 17074990702
         * fromDisplayName : 最软科技有限
         * fromHeadImage : http://image.hmeg.cn/upload/image/201711/a9c891b3d664460d97a983605025563f.png
         * fromUserId : 2876f7e0f51c4153aadc603b661fedfa
         */

        public String fromDisplayPhone;
        public String fromDisplayName;
        public String fromHeadImage;
        public String fromUserId;

        public String toUserId;
        public String toDisplayName;
        public String toDisplayPhone;
        public String toHeadImage;
        public boolean isOwner;
    }

}