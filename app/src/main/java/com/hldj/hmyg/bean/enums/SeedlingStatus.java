package com.hldj.hmyg.bean.enums;

public enum SeedlingStatus {


    nullstatu("", "全部"),
    /**
     * 未提交
     */
    unsubmit("unsubmit", "未提交"),
    /**
     * 待审核
     */
    unaudit("unaudit", "待审核"),
    /**
     * 已上架
     */
    published("published", "已上架"),
    /**
     * 已下架
     */
    outline("outline", "已下架"),
    /**
     * 已退回
     */
    backed("backed", "已退回");

    public String enumValue;
    public String enumText;//客服显示的状态名称

    SeedlingStatus(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;

    }


}