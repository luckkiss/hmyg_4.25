package com.hldj.hmyg.M;

import java.math.BigDecimal;
import java.util.List;

public class QuoteUserGroup {

    /**
     * 供应商ID
     */
    public String sellerId;

    /**
     * 供应商名称：名字 + 手机号
     */
    public String sellerName;

    /**
     * 是否是客户自有供应商
     */
    public boolean selfSeller;

    /**
     * 报价条数
     */
    public int quoteCount;

    /**
     * 报价品种数量
     */
    public int quoteItemCount;

    /**
     * 已采用报价数量
     */
    public int usedCount;

    /**
     * 待落实数量
     */
    public int uncoveredCount;

    /**
     * 苗源地集合
     */
    public List<String> cityNames;

    /**
     * 报价总额 = (品种1最低的报价 * 采购数量) + (品种2最低的报价 * 采购数量) + (品种3最低的报价 * 采购数量) + ....
     */
    public BigDecimal quoteTotalPrice;


}
