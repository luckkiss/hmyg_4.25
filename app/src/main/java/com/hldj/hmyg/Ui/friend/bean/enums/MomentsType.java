package com.hldj.hmyg.Ui.friend.bean.enums;


public enum MomentsType {

    /**
     * 供应
     */
    supply("supply", "供应"),

    /**
     * 求购
     */
    purchase("purchase", "求购"),


    collect("collect", "收藏"),

    all("", "全部");

    private String enumValue, enumText;

    private MomentsType(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumText() {
        return enumText;
    }

    public void setEnumText(String enumText) {
        this.enumText = enumText;
    }

}