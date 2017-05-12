package com.hldj.hmyg.saler.M;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class PurchaseListGsonBean {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"page":{"pageNo":0,"pageSize":10,"total":1,"data":[{"id":"21fdc65b66d7408ca0f546518efb9081","createBy":"1","createDate":"2017-03-09 15:58:54","cityCode":"3502","cityName":"厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","projectName":"代购模式测试项目","projectType":"protocol","consumerId":"a6e6c779fefb4a41b0188dfc5ec76ad5","consumerUserId":"5315ff5c-e118-4a5d-abb7-338c90d8ce73","consumerName":"新模式测试客户","name":"测试代购项目","num":"P0000318","projectId":"2c08b5b58e5d45a0b299bdeb44b72f37","receiptDate":"2017-03-31","publishDate":"2017-03-09 16:26:12","closeDate":"2017-07-20 15:57","needInvoice":false,"customerId":"1","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：XXXX年XX月XX日\n<\/p>\n<p>\n\t用苗地：XXXX&nbsp;<br />\n报价要求：<span style=\"color:#E53333;\">XXX<\/span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />\n发票要求：XXXX&nbsp;<br />\n<span style=\"color:#E53333;\">质量要求：XXXX（接近房地产货）<\/span><br />\n<span style=\"color:#E53333;\">测量要求：XXXXX。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：XXXX<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"18250876026","dispatchName":"叶珊珊","isPrivate":true,"authcPhone":"18616394226","buyer":{"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"},"statusName":"已发布","quoteCountJson":15,"itemCountJson":4,"lastDays":68,"itemNameList":["白蜡","红花锦鸡儿","红钻","白花鸡蛋花"],"purchaseFormId":"21fdc65b66d7408ca0f546518efb9081","typeName":"采购中","blurProjectName":"代购模式测试项目","blurName":"测试****"}],"firstResult":0,"maxResults":10}}
     */

    public String code;
    public String msg;
    public DataBeanX data;

    public static class DataBeanX {
        /**
         * page : {"pageNo":0,"pageSize":10,"total":1,"data":[{"id":"21fdc65b66d7408ca0f546518efb9081","createBy":"1","createDate":"2017-03-09 15:58:54","cityCode":"3502","cityName":"厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","projectName":"代购模式测试项目","projectType":"protocol","consumerId":"a6e6c779fefb4a41b0188dfc5ec76ad5","consumerUserId":"5315ff5c-e118-4a5d-abb7-338c90d8ce73","consumerName":"新模式测试客户","name":"测试代购项目","num":"P0000318","projectId":"2c08b5b58e5d45a0b299bdeb44b72f37","receiptDate":"2017-03-31","publishDate":"2017-03-09 16:26:12","closeDate":"2017-07-20 15:57","needInvoice":false,"customerId":"1","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：XXXX年XX月XX日\n<\/p>\n<p>\n\t用苗地：XXXX&nbsp;<br />\n报价要求：<span style=\"color:#E53333;\">XXX<\/span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />\n发票要求：XXXX&nbsp;<br />\n<span style=\"color:#E53333;\">质量要求：XXXX（接近房地产货）<\/span><br />\n<span style=\"color:#E53333;\">测量要求：XXXXX。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：XXXX<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"18250876026","dispatchName":"叶珊珊","isPrivate":true,"authcPhone":"18616394226","buyer":{"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"},"statusName":"已发布","quoteCountJson":15,"itemCountJson":4,"lastDays":68,"itemNameList":["白蜡","红花锦鸡儿","红钻","白花鸡蛋花"],"purchaseFormId":"21fdc65b66d7408ca0f546518efb9081","typeName":"采购中","blurProjectName":"代购模式测试项目","blurName":"测试****"}],"firstResult":0,"maxResults":10}
         */

        public PageBean page;

        public static class PageBean {
            /**
             * pageNo : 0
             * pageSize : 10
             * total : 1
             * data : [{"id":"21fdc65b66d7408ca0f546518efb9081","createBy":"1","createDate":"2017-03-09 15:58:54","cityCode":"3502","cityName":"厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","projectName":"代购模式测试项目","projectType":"protocol","consumerId":"a6e6c779fefb4a41b0188dfc5ec76ad5","consumerUserId":"5315ff5c-e118-4a5d-abb7-338c90d8ce73","consumerName":"新模式测试客户","name":"测试代购项目","num":"P0000318","projectId":"2c08b5b58e5d45a0b299bdeb44b72f37","receiptDate":"2017-03-31","publishDate":"2017-03-09 16:26:12","closeDate":"2017-07-20 15:57","needInvoice":false,"customerId":"1","status":"published","source":"admin","quoteDesc":"<p>\n\t报价截止时间：XXXX年XX月XX日\n<\/p>\n<p>\n\t用苗地：XXXX&nbsp;<br />\n报价要求：<span style=\"color:#E53333;\">XXX<\/span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />\n发票要求：XXXX&nbsp;<br />\n<span style=\"color:#E53333;\">质量要求：XXXX（接近房地产货）<\/span><br />\n<span style=\"color:#E53333;\">测量要求：XXXXX。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：XXXX<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"18250876026","dispatchName":"叶珊珊","isPrivate":true,"authcPhone":"18616394226","buyer":{"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"},"statusName":"已发布","quoteCountJson":15,"itemCountJson":4,"lastDays":68,"itemNameList":["白蜡","红花锦鸡儿","红钻","白花鸡蛋花"],"purchaseFormId":"21fdc65b66d7408ca0f546518efb9081","typeName":"采购中","blurProjectName":"代购模式测试项目","blurName":"测试****"}]
             * firstResult : 0
             * maxResults : 10
             */

            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<PurchaseBean> data;


        }
    }
}
