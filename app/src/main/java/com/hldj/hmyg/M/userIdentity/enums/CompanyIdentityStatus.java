package com.hldj.hmyg.M.userIdentity.enums;


public enum CompanyIdentityStatus{
	
	/**
	 * 未审批
	 */
	unaudited("unaudited","未审批"),
	
	/**
	 * 审批中
	 */
	auditing("auditing","审批中"),
	
	/**
	 * 通过
	 */
	pass("pass","通过"),
	
	/**
	 * 退回
	 */
	back("back","退回");
	
	private String enumValue, enumText;
    private CompanyIdentityStatus(String enumValue, String enumText) {
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