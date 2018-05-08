package com.hldj.hmyg.saler.bean.enums;


public enum FootMarkSourceType{
	/**
	 * 苗木资源
	 */
	seedling("seedling","苗木信息"),
	
	/**
	 * 苗木圈
	 */
	moments("moments","苗木圈"),
	
	/**
	 * 用户求购
	 */
	userPurchase("userPurchase","用户求购");
	
	private String enumValue, enumText;
    private FootMarkSourceType(String enumValue, String enumText) {
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