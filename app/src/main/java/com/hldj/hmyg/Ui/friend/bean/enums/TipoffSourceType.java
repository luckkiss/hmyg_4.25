package com.hldj.hmyg.Ui.friend.bean.enums;


import com.hldj.hmyg.Ui.friend.util.EnumUtils;

/**
 * 举报资源类型
 * @author linzj
 * @date 2018-4-21
 */
public enum TipoffSourceType{

	/**
	 * 苗木资源
	 */
	seedling("seedling","苗木资源"),
	
	/**
	 * 用户求购
	 */
	userPurchase("userPurchase","用户求购"),
	
	/**
	 * 苗木圈
	 */
	moments("moments","苗木圈");
	
	private String enumValue, enumText;
    private TipoffSourceType(String enumValue, String enumText) {
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
        return EnumUtils.toJson(TipoffSourceType.class);
    }
    
}