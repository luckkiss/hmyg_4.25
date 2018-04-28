package com.hldj.hmyg.saler.M.enums;

public enum ValidityEnum{
	
	/**
	 * 1天
	 */
	day1("day1","1天",1),
	
	/**
	 * 3天
	 */
	day3("day3","3天",3),
	
	/**
	 * 7天
	 */
	day7("day7","7天",7),
	
	/**
	 * 15天
	 */
	day15("day15","15天",15),
	
	/**
	 * 30天
	 */
	day30("day30","30天",30);
	
	private String enumValue, enumText;
	private int days;
    private ValidityEnum(String enumValue, String enumText,int days) {
        this.enumValue = enumValue;
        this.enumText = enumText;
        this.days = days;
    }
    public String getEnumValue() {
        return enumValue;
    }
    public String getEnumText() {
        return enumText;
    }
    
    public int getDays() {
		return days;
	}



//	public static String toJson() {
//        return EnumUtils.toJson(ValidityEnum.class);
//    }
//	public static List<Map<String,String>> toMap(){
//    	List<Map<String,String>> list = Lists.newArrayList();
//    	for(ValidityEnum type : ValidityEnum.values()){
//    		Map<String,String> map = new HashMap<String,String>();
//    		map.put("value",type.getEnumValue());
//    		map.put("text", type.getEnumText());
//    		list.add(map);
//    	}
//    	return list;
//    }
}