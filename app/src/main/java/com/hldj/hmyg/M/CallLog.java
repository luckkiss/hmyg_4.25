package com.hldj.hmyg.M;

import com.hldj.hmyg.Ui.friend.bean.enums.CallSourceType;

import java.io.Serializable;

/**
 * 电话日志对象
 */

public class CallLog implements Serializable {

    public CallSourceType callSourceType;//seedling、seedlingNote


    public String callSourceTypeName;//seedling、seedlingNote

    public String callSourceId;//资源ID

    public String callStoreId;//苗木资源所在店铺ID

    /**
     * 被叫资源拥有者ID
     */
    public String ownerId;

    public String userId;//当前用户ID

    public String callPhone;//被呼叫号码

    public String userType;//系统类型 ：admin mall

    public String callTargetType;//被呼叫号码类型：owner(发布人)、nursery(苗圃)

    public AttrData attrData = new AttrData();

    public String callTargetTypeText() {


        return callTargetType;
//        if (callTargetType != null) {
//            return callTargetType.getEnumText();
//        } else {
//            return "";
//        }
    }


    public static class AttrData {
        public String cityName = "";
        public String headImage = "";
        public String displayName = "";
        public String timeStampStr = "";

    }


}


