package com.hldj.hmyg.buyer.M;


/**
 * 报价状态，用于前端用户查询时候的状态
 *
 * @author linzj
 * @date 2017-4-20
 */
public enum QuoteStatus {


    /**
     * 预报价
     */
    preQuote("preQuote", "预报价"),

    /**
     * 预中标
     */
    preBid("preBid", "预中标"),

    /**
     * 选标中
     */
    choosing("choosing", "选标中"),


    /**
     * 已中标，条件：合格并且已被采用
     */
    used("used", "已中标"),

    /**
     * 未中标，条件：采购品种结束或关闭采购并且未被采用的、或者报价不合格
     */
    unused("unused", "未中标");

    private String enumValue, enumText;

    private QuoteStatus(String enumValue, String enumText) {
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