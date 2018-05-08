package com.hldj.hmyg.Ui.friend.bean.enums;


import com.hldj.hmyg.Ui.friend.util.EnumUtils;

public enum CallTargetType{
	
	/**
	 * 发布人
	 */
	owner("owner","发布人"),
	/**
	 * 苗圃
	 */
	nursery("nursery","苗圃");
	
	private String enumValue, enumText;
    private CallTargetType(String enumValue, String enumText) {
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
    public static String toJson() {
        return EnumUtils.toJson(CallTargetType.class);
    }
}