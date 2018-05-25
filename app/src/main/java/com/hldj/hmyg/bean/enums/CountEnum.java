package com.hldj.hmyg.bean.enums;

/**
 * 同意 数量 的 枚举
 */

public enum CountEnum {

    /**
     * "data":{
     * "uncoveredCount":0,
     * "preCount":0,
     * "quoteCount":75,
     * "useCount":31,
     * "itemQuoteCount":31,
     * "itemCount":31
     * },
     * <p>
     * itemCount=[countDic[@"itemCount"] intValue];//品种数量
     * itemQuoteCount=[countDic[@"itemQuoteCount"] intValue];//报价品种数
     * quoteCount=[countDic[@"quoteCount"] intValue];//报价条数
     * useCount=[countDic[@"useCount"] intValue];//采用数
     * preCount=[countDic[@"preCount"] intValue];//备选
     * uncoveredCount=[countDic[@"uncoveredCount"] intValue];//待落实
     */

    待落实("uncoveredCount", "待落实"),
    /**
     * 未提交
     */

    报价条数("quoteCount", "报价条数"),

    备选("preCount", "备选"),

    采用数("useCount", "采用数"),
    /**
     * 待审核
     */
    报价品种数("itemQuoteCount", "报价品种数"),


    已开标数("yesQuoteCount", "已开标数"),
    未开标数("unQuoteCount", "未开标数"),
    /**
     * 已上架
     */
    品种数量("itemCount", "报价条数");


    public String enumValue;
    public String enumText;

    CountEnum(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;

    }


}
