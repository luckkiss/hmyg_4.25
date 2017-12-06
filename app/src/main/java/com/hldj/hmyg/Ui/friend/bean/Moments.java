package com.hldj.hmyg.Ui.friend.bean;

import com.hldj.hmyg.bean.UserBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseJsonBean;

import java.io.Serializable;
import java.util.List;


/**
 * 朋友圈Entity
 *
 * @author luow
 * @version 2017-11-24
 */
public class Moments implements Serializable {

    public static final long serialVersionUID = 4307497280064329128L;


    public String id;

    /**
     * 省级代码
     */
    public String prCode;
    /**
     * 市级代码
     */
    public String ciCode;

    /**
     * 发布内容
     */
    public String content;

    /**
     * 发布类型
     */
    public String momentsType = "";

    /**
     * 时间戳
     */
    public long timeStamp;

    public String createDate;

    /**
     * 拥有者ID
     */
    public String ownerId;

    /**
     * 城市code
     */
    public String cityCode;

    /**
     * 点赞数量
     */
    public int thumbUpCount;

    public List<MomentsThumbUp> thumbUpListJson ;


    public PurchaseJsonBean.CiCityBean ciCity;


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
     * 评论集合
     */
    public List<MomentsReply> itemList;
    public List<MomentsReply> itemListJson ;

    /**
     * 图片集合
     */
    public String images;


    public List<ImagesJsonBean> imagesJson;

    public String imagesData;

    /**
     * 用户
     */
    public UserBean ownerUser;
    public UserBean ownerUserJson;


    public AttrDataBean attrData;
    public String timeStampStr;
    public boolean isOwner;
    public int replyCount;
    public boolean isFavour;

//     "isFavour": true,
//             "timeStampStr": "1小时前",
//             "isOwner": false,
//             "replyCount": 0




    public static class AttrDataBean implements Serializable {
        /**
         * headImage : http://image.hmeg.cn/upload/image/201708/d83a64a1099544cebc3aca9be64a43e0.jpeg
         * userId : 3378ca2d-b09b-411b-a3d0-28766e314685
         * displayName : 花木易购
         * storeId : d55e06b24d854d7587a3461230865e91
         * displayPhone : 18750215634
         */

        public String headImage;
        public String userId;
        public String displayName;
        public String storeId;
        public String displayPhone;


    }

}