package com.hldj.hmyg.saler.bean.enums;

/**
 * @author linzj<br>
 *         项目报价类型：上车价、到岸价
 */
public enum PriceType {

    /**
     * 上车价
     */
    shangche("shangche", "上车价"),

    /**
     * 到岸价
     */
    daoan("daoan", "到岸价");

    public String enumValue;
    public String enumText;

    PriceType(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;
    }


}