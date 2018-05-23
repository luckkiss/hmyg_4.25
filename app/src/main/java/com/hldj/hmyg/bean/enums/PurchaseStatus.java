package com.hldj.hmyg.bean.enums;

/**
 * 开标状态
 */

public enum PurchaseStatus {

    /**
     * 待发布
     */
    unpublished("unpublished","待发布",false),

    /**
     * 已开标
     */
    expired("expired","已开标",true),

    /**
     * 已发布
     */
    published("published","报价中",true),

    /**
     * 被退回
     */
//	backed("backed","被退回"),


    /**
     * 已结束  add by linzj  2018-4-21<br>
     * 新增加的采购单结束状态，由人工设置采购单结束，设置结束采购单后同时更新未中标的报价未“未中标”
     */
    finished("finished","已结束",true);

    //是否对客户显示这个状态
    private boolean consumerDisplay;

    public String enumValue;
    public String enumText;

    PurchaseStatus(String enumValue, String enumText   ,boolean consumerDisplay) {
        this.enumValue = enumValue;
        this.enumText = enumText;
        this.consumerDisplay = consumerDisplay ;
    }

}
