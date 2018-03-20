package com.hldj.hmyg.M.userIdentity.enums;

public enum UserIdentityStatus {


    /**
     * 未审批
     */
    unaudited("unaudited", "未实名认证"),

    /**
     * 审批中
     */
    auditing("auditing", "审核中"),

    /**
     * 通过
     */
    pass("pass", "已实名认证"),

    /**
     * 退回
     */
    back("back", "认证失败");

    public String enumValue, enumText;

    UserIdentityStatus(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;
    }

}