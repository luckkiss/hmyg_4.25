package com.hldj.hmyg.M;

/**
 * 电话日志对象
 */

public class CallLog {

    /**
     * private String callSourceType;//seedling、seedlingNote
     * <p>
     * private String callSourceId;//资源ID
     * <p>
     * private String userId;//当前用户ID
     * <p>
     * private String callPhone;//被呼叫号码
     * <p>
     * private String callTargetType;//被呼叫号码类型：owner(发布人)、nursery(苗圃)
     * /callLog/save
     */
    private String callSourceType = "";
    private String callSourceId = "";
    private String callPhone = "";
    private String callTargetType = "";

    CallLog(String sourceType, String id, String phone, String targetTarget) {
        this.callSourceType = sourceType;
        this.callSourceId = id;
        this.callPhone = phone;
        this.callTargetType = targetTarget;
    }




}


