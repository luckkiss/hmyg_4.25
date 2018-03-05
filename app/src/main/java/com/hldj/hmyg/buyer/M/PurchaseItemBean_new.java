package com.hldj.hmyg.buyer.M;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class PurchaseItemBean_new implements Serializable {
    /**
     * id : dd48777f6d6e4071af0c3cd833ed41a9
     * remarks :
     * createBy : 6fcb68fcb995418da05cc867a9d3b175
     * createDate : 2017-06-14 17:22:30
     * prCode :
     * ciCode :
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 4bf45dae8c8e4ad5a0bc4df7cdc3d294
     * secondSeedlingTypeId : f2824614f3144184bfad59a61daf172c
     * height : 120
     * plantType : container
     * unitType : plant
     * firstTypeName : 灌木
     * unitTypeName : 株
     * plantTypeName : 容器苗
     * diameterTypeName :
     * dbhTypeName :
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : diameter,height,crown
     * paramsList : ["diameter","height","crown"]
     * specList : [{"name":"高度","value":"120CM"}]
     * specText : 高度:120
     * purchaseId : 24574c63a035452ea9f515bd9d11abcf
     * name : 三裂树藤
     * count : 13
     * status : quoting
     * sendFlag : true
     * purchaseJson : {"id":"24574c63a035452ea9f515bd9d11abcf","createBy":"6fcb68fcb995418da05cc867a9d3b175","createDate":"2017-06-14 15:00:11","cityCode":"3502","cityName":"厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"projectName":"五缘湾（金砖）","consumerId":"15f62b0a2fcf44d59e2c6bc0c2a1bfa3","consumerUserId":"","name":"五缘湾采购","num":"P0000589","projectId":"0504123e9a0241d0bdeda02ab8537016","receiptDate":"2017-06-17","validity":2,"publishDate":"2017-06-14 15:11:52","closeDate":"2017-09-22 14:57","needInvoice":true,"customerId":"6fcb68fcb995418da05cc867a9d3b175","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：2017年6月17日\n<\/p>\n<p>\n\t用苗地：厦门<br />\n报价要求：<span style=\"color:#E53333;\">含税价<\/span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />\n发票要求：附带发票<br />\n<span style=\"color:#E53333;\">质量要求：b+（接近房地产货）<\/span><br />\n<span style=\"color:#E53333;\">测量要求：详见品种要求<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：假植苗<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":true,"type":"quoting","dispatchPhone":"18350215927","dispatchName":"林巧欣","isPrivate":false,"buyer":{"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"},"statusName":"已发布","newQuoteCount":8,"lastDays":66,"purchaseFormId":"24574c63a035452ea9f515bd9d11abcf","typeName":"采购中","blurName":"五缘****"}
     * statusName : 询价中
     * quoteCountJson : 1
     * newQuoteCount : 0
     * orderBy :
     * isQuoted : false
     * seedlingCityCodeName : 不限制
     * source : admin
     * purchaseType : quoting
     * specHtml : 高度:120CM
     * crown : 150
     * diameter : 8
     * diameterType : size10
     * offbarHeight : 200
     */

    /**
     * 编辑类型----是否可以编辑  0  可以  1  不可用
     */
    public boolean editAble = true;


    public String id;/**/
    public String purchaseId;/*采购单id*/
    public String plantTypeArrayNames = "-";/**/
    public String remarks;/**/
    public String unitTypeName;/**/
    public String specText;/**/
    public String name;/**/
    public int count;/**/
    public PurchaseJsonBeanX purchaseJson = new PurchaseJsonBeanX();/**/
    public int quoteCountJson;/**/
    public boolean isQuoted;/**/


    public String status = "";/**/


    public String pid1 = "";
    public String pid2 = "";

    public List<SellerQuoteJsonBean> sellerQuoteListJson;

    public SellerQuoteJsonBean footSellerQuoteListJson; /* 底部临时保存的   quote 报价 对象 */


    public static class PurchaseJsonBeanX implements Serializable {
        public String cityName = "";/**/
    }


    @Override
    public String toString() {
        return "PurchaseItemBean_new{" +
                "id='" + id + '\'' +
                ", remarks='" + remarks + '\'' +
                ", unitTypeName='" + unitTypeName + '\'' +
                ", specText='" + specText + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", purchaseJson=" + purchaseJson +
                ", quoteCountJson=" + quoteCountJson +
                ", isQuoted=" + isQuoted +
                '}';
    }
}