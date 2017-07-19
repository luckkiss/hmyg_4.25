package com.hldj.hmyg.bean;

import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;

import java.io.Serializable;

/**
 * 用于接收  普通的 成功失败返回  的gson 笨啊、===
 */

public class SimpleGsonBean_test implements Serializable {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"purchaseItem":{"id":"784ed513a67141fdae0d9043c9ca7433","remarks":"","createBy":"6fcb68fcb995418da05cc867a9d3b175","createDate":"2017-06-14 15:01:22","prCode":"","ciCode":"","coCode":"","twCode":"","firstSeedlingTypeId":"4bf45dae8c8e4ad5a0bc4df7cdc3d294","secondSeedlingTypeId":"952f728e-4faf-43ed-8280-23368770ab6e","diameter":14,"diameterType":"size10","height":350,"crown":300,"plantType":"container","unitType":"plant","firstTypeName":"灌木","unitTypeName":"株","plantTypeName":"容器苗","diameterTypeName":"0.1M量","dbhTypeName":"","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","seedlingParams":"diameter,height,crown","paramsList":["diameter","height","crown"],"specList":[{"name":"地径","value":"14CM(0.1M量)"},{"name":"高度","value":"350CM"},{"name":"冠幅","value":"300CM"}],"specText":"地径:14(0.1M量) 高度:350 冠幅:300 ","purchaseId":"24574c63a035452ea9f515bd9d11abcf","name":"紫叶李","count":5,"status":"quoting","sendFlag":true,"purchaseJson":{"id":"24574c63a035452ea9f515bd9d11abcf","createBy":"6fcb68fcb995418da05cc867a9d3b175","createDate":"2017-06-14 15:00:11","cityCode":"3502","cityName":"厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"projectName":"五缘湾（金砖）","consumerId":"15f62b0a2fcf44d59e2c6bc0c2a1bfa3","consumerUserId":"","name":"五缘湾采购","num":"P0000589","projectId":"0504123e9a0241d0bdeda02ab8537016","receiptDate":"2017-06-17","validity":2,"publishDate":"2017-06-14 15:11:52","closeDate":"2017-09-22 14:57","needInvoice":true,"customerId":"6fcb68fcb995418da05cc867a9d3b175","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：2017年6月17日\n<\/p>\n<p>\n\t用苗地：厦门<br />\n报价要求：<span style=\"color:#E53333;\">含税价<\/span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />\n发票要求：附带发票<br />\n<span style=\"color:#E53333;\">质量要求：b+（接近房地产货）<\/span><br />\n<span style=\"color:#E53333;\">测量要求：详见品种要求<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：假植苗<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":true,"type":"quoting","dispatchPhone":"18350215927","dispatchName":"林巧欣","isPrivate":false,"buyer":{"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"},"statusName":"已发布","newQuoteCount":8,"lastDays":65,"purchaseFormId":"24574c63a035452ea9f515bd9d11abcf","typeName":"采购中","blurName":"五缘****"},"statusName":"询价中","quoteCountJson":0,"newQuoteCount":0,"orderBy":"","isQuoted":false,"seedlingCityCodeName":"不限制","source":"admin","purchaseType":"quoting","specHtml":"地径:14CM(0.1M量)<\/br>高度:350CM<\/br>冠幅:300CM"}}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {

        public PurchaseItemBean_new purchaseItem;

    }
}
