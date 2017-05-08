package com.hldj.hmyg.buyer.M;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ItemBean {

    /**
     * id : 54101356f7114c8286cac1e69b58a138
     * remarks : 高度250，净杆高50
     * createBy : e6ae79aad8254ffcb0cfe8fd11844c89
     * createDate : 2017-04-20 20:35:26
     * prCode :
     * ciCode :
     * coCode :
     * twCode :
     * firstSeedlingTypeId : ce14746cde764456990f717a82ffb0c0
     * secondSeedlingTypeId : f14f934afc6540ad989fd9d5b4bb7049
     * diameter : 40
     * diameterType : size10
     * crown : 200
     * plantType : heelin
     * unitType : plant
     * firstTypeName : 棕榈/苏铁
     * unitTypeName : 株
     * plantTypeName : 假植苗
     * diameterTypeName : 0.1M量
     * dbhTypeName :
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : offbarHeight,diameter,crown
     * paramsList : ["offbarHeight","diameter","crown"]
     * specList : [{"name":"地径","value":"40CM(0.1M量)"},{"name":"冠幅","value":"200CM"}]
     * specText : 地径:40(0.1M量) 冠幅:200
     * purchaseId : e6aa2517bbc841b49c2eeb75bb8ffc01
     * name : 银海枣
     * count : 12
     * status : quoting
     * purchaseJson : {"id":"e6aa2517bbc841b49c2eeb75bb8ffc01","createBy":"e6ae79aad8254ffcb0cfe8fd11844c89","createDate":"2017-04-20 16:51:24","cityCode":"4505","cityName":"北海","prCode":"45","ciCode":"4505","coCode":"","twCode":"","ciCity":{"id":"31595","name":"北海","fullName":"广西 北海","cityCode":"4505","parentCode":"45","level":2},"projectName":"大都·金沙湾（商业街）绿化","consumerId":"6bd86d86db9a4a91be917a188d79a0a8","consumerUserId":"","name":"大都·金沙湾（商业街）绿化","num":"P0000506","projectId":"5def53c9a18a48e5869fc2864e2cdf5e","receiptDate":"2017-04-24","publishDate":"2017-04-20 16:53:45","closeDate":"2017-04-26 23:00","needInvoice":true,"customerId":"e6ae79aad8254ffcb0cfe8fd11844c89","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：2017年04月26日\n<\/p>\n<p>\n\t用苗地：广西北海\n<\/p>\n<p>\n\t报价要求：（包含以下费用：1、上车费用。2、税金。<span style=\"color:#E53333;\"><\/span>）&nbsp;\n<\/p>\n<p>\n\t发票要求：增值税普通发票或增值税专用发票<br />\n<span style=\"color:#E53333;line-height:1.5;\"><\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：假植苗<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">质量要求：A货<br />\n<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">测量要求：地径离地10公分处量。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\"><\/span>报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）<br />\n到场规格，质量不合格做退货处理。\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"18350218809","dispatchName":"黄志雄","isPrivate":false,"authcPhone":"","statusName":"已发布","lastDays":1,"purchaseFormId":"e6aa2517bbc841b49c2eeb75bb8ffc01","typeName":"采购中","blurName":"大都****"}
     * statusName : 询价中
     * quoteCountJson : 0
     * orderBy :
     * seedlingCityCodeName : 不限制
     * source : admin
     * purchaseType : quoting
     */

    public String id;
    public String remarks;
    public String createBy;
    public String createDate;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String firstSeedlingTypeId;
    public String secondSeedlingTypeId;
    public int diameter;
    public String diameterType;
    public int crown;
    public String plantType;
    public String unitType;
    public String firstTypeName;
    public String unitTypeName;
    public String plantTypeName;
    public String diameterTypeName;
    public String dbhTypeName;
    public String thumbnailImageUrl;
    public String smallImageUrl;
    public String mediumImageUrl;
    public String largeImageUrl;
    public String seedlingParams;
    public String specText;
    public String purchaseId;
    public String name;
    public int count;
    public String status = "";
    public PurchaseJsonBean purchaseJson;
    public String statusName;
    public int quoteCountJson;
    public String orderBy;
    public String seedlingCityCodeName;
    public String source;
    public String purchaseType;
    public SellerQuoteJsonBean sellerQuoteJson;
    public List<String> paramsList;
    public List<SpecListBean> specList;
    public BuyerBean buyer = new BuyerBean();



    public boolean isQuoted;


    public static class SpecListBean {
        /**
         * name : 地径
         * value : 40CM(0.1M量)
         */

        public String name;
        public String value;
    }


    /**
     *  "buyer": {
     "id": "-1",
     "prCode": "",
     "ciCode": "",
     "coCode": "",
     "twCode": "",
     "phone": "4006-579-888",
     "companyName": "厦门花木易购电子商务有限公司",
     "isInvoices": false,
     "agentTypeName": "",
     "isDirectAgent": false,
     "displayName": "厦门花木易购电子商务有限公司",
     "adminDisplayName": "厦门花木易购电子商务有限公司",
     "displayPhone": "4006-579-888"
     },
     */

}
