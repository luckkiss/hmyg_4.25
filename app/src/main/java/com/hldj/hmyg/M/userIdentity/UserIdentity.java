package com.hldj.hmyg.M.userIdentity;

import com.hldj.hmyg.M.userIdentity.enums.UserIdentityStatus;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;

/**
 * 身份认证  实体
 *
 * @author linzj
 * @version 2017-02-16
 */
public class UserIdentity {



    /**
     *
     */
    public static final long serialVersionUID = -5964735140552357652L;

    public String id ;

    /**
     * 关联的用户id
     */
    public String userId;

    /**
     * 真实姓名
     */
    public String realName;

    /**
     * 身份证号
     */
    public String identityNum;

    /**
     * 状态
     */
    public UserIdentityStatus status;

    /**
     * 正面身份证
     */
    public String frontImageUrl;

    /**
     * 反面身份证
     */
    public String backImageUrl;

    /**
     * 正面身份证id
     */
    public String frontImageId;

    /**
     * 反面身份证id
     */
    public String backImageId;

    /**
     * 客服反馈
     */
    public String auditLog;

    /***********临时属性  开始*****************/
    public String statusName;
    public ImagesJsonBean frontImage;
    public ImagesJsonBean frontImageJson;

    public ImagesJsonBean backImage;
    public ImagesJsonBean backImageJson;


    @Override
    public String toString() {
        return "UserIdentity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", realName='" + realName + '\'' +
                ", identityNum='" + identityNum + '\'' +
                ", status=" + status +
                ", frontImageUrl='" + frontImageUrl + '\'' +
                ", backImageUrl='" + backImageUrl + '\'' +
                ", frontImageId='" + frontImageId + '\'' +
                ", backImageId='" + backImageId + '\'' +
                ", auditLog='" + auditLog + '\'' +
                ", statusName='" + statusName + '\'' +
                ", frontImage=" + frontImage +
                ", frontImageJson=" + frontImageJson +
                ", backImage=" + backImage +
                ", backImageJson=" + backImageJson +
                '}';
    }
}