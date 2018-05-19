package com.hldj.hmyg.saler.bean;

import java.io.Serializable;


/**
 * 访客
 */
public class FootMark implements Serializable {


    /**
     * 访问者ID
     */
    public String userId;

    /**
     * 被访问者ID
     */
    public String beUserId;

    /**
     * 访问资源所属店铺ID
     */
    public String storeId;

    /**
     * 被访问资源ID
     */
    public String sourceId;

    /**
     * 被访问资源类型
     */
    public String sourceType;
//    public FootMarkSourceType sourceType;


    /**
     * 用户IP地址
     */
    public String ip;

    /**
     * 新访客信息是否被已读
     */
    public Boolean isRead;

    public String ipCity ;


    public AttrData attrData = new AttrData() ;

    /* 头像*/
//    public String userHeadImage;


    public static class AttrData {
        public String userCiCity="";
        public String timeStamp="";
        public String userId="";
        public String cityName="";
        public String userName="";
        public String userHeadImage="";
    }

    /**
     *   "userCiCity": "",
     "timeStamp": "16分钟前",
     "userId": "7e0b0b7b8c9e42788870cf758e223946",
     "userName": "15750702168",
     "userHeadImage": ""
     */

}
