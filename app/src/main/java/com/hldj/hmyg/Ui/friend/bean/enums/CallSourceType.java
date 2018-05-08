package com.hldj.hmyg.Ui.friend.bean.enums;


public enum CallSourceType{
	
	/**
	 * 苗木
	 */
	seedling("seedling","苗木信息"),
	/**
	 * 苗木圈
	 */
	moments("moments","苗木圈"),
	
	/**
	 * 用户求购
	 */
	userPurchase("userPurchase","个人求购"),
	
	/**
	 * 个人资料
	 */
	user("user","个人资料"),
	
	/**
	 * 记苗本
	 */
	seedlingNote("seedlingNote","记苗本");
	
	public String enumValue, enumText;
     CallSourceType(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;
    }

}