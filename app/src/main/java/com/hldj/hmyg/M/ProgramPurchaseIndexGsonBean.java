package com.hldj.hmyg.M;

import com.hldj.hmyg.saler.M.PurchaseBean;

/**
 * Created by 罗擦擦
 */

public class ProgramPurchaseIndexGsonBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"showQuote":true,"servicePoint":"10.00","purchase":{"id":"60a426d34aa5443fa217ade0b6fdd242","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-01 08:44:34","cityCode":"3503","cityName":"莆田","prCode":"35","ciCode":"3503","coCode":"","twCode":"","projectName":"永鸿文化城后期补苗项目","projectType":"protocol","consumerId":"2bfc5fbdb02d42df8eb457d34ee7a7c0","consumerUserId":"6d2d9c272eb545b499d2a235f0f8bb94","consumerName":"莆田一鼎","name":"永鸿文化城后期补苗项目","num":"P0000576","projectId":"827aff9272fe429eb0e8b0513b6200a8","receiptDate":"2017-06-05","publishDate":"2017-06-01 08:45:34","closeDate":"2017-06-14 11:00","needInvoice":true,"customerId":"b9cef730fa6142eb80bbd7d646e40c66","status":"expired","source":"admin","quoteDesc":"<p>\n\t报价截止时间：2017年06月04日\n<\/p>\n<p>\n\t用苗地：福建莆田 <br />\n报价要求：<span style=\"color:#E53333;\">上车价<\/span>（包含以下费用：1、上车费用。2、税金。） <br />\n发票要求：<span>增值税专用发票、增值税普通发票<\/span><br />\n<span style=\"color:#E53333;\">质量要求：房地产质量<\/span><br />\n<span style=\"color:#E53333;\">测量要求：米径，离地100cm处量。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：容器苗<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"15860762202","dispatchName":"钟小华","isPrivate":true,"authcPhone":"","statusName":"已过期","lastDays":0,"purchaseFormId":"60a426d34aa5443fa217ade0b6fdd242","typeName":"采购中","blurProjectName":"永鸿文化城后期补苗项目","blurName":"永鸿****"}}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * showQuote : true
         * servicePoint : 10.00
         * purchase : {"id":"60a426d34aa5443fa217ade0b6fdd242","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-01 08:44:34","cityCode":"3503","cityName":"莆田","prCode":"35","ciCode":"3503","coCode":"","twCode":"","projectName":"永鸿文化城后期补苗项目","projectType":"protocol","consumerId":"2bfc5fbdb02d42df8eb457d34ee7a7c0","consumerUserId":"6d2d9c272eb545b499d2a235f0f8bb94","consumerName":"莆田一鼎","name":"永鸿文化城后期补苗项目","num":"P0000576","projectId":"827aff9272fe429eb0e8b0513b6200a8","receiptDate":"2017-06-05","publishDate":"2017-06-01 08:45:34","closeDate":"2017-06-14 11:00","needInvoice":true,"customerId":"b9cef730fa6142eb80bbd7d646e40c66","status":"expired","source":"admin","quoteDesc":"<p>\n\t报价截止时间：2017年06月04日\n<\/p>\n<p>\n\t用苗地：福建莆田 <br />\n报价要求：<span style=\"color:#E53333;\">上车价<\/span>（包含以下费用：1、上车费用。2、税金。） <br />\n发票要求：<span>增值税专用发票、增值税普通发票<\/span><br />\n<span style=\"color:#E53333;\">质量要求：房地产质量<\/span><br />\n<span style=\"color:#E53333;\">测量要求：米径，离地100cm处量。<\/span> \n<\/p>\n<p>\n\t<span style=\"color:#E53333;line-height:1.5;\">种植要求：容器苗<\/span> \n<\/p>\n<p>\n\t报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）\n<\/p>","isCooper":false,"type":"quoting","dispatchPhone":"15860762202","dispatchName":"钟小华","isPrivate":true,"authcPhone":"","statusName":"已过期","lastDays":0,"purchaseFormId":"60a426d34aa5443fa217ade0b6fdd242","typeName":"采购中","blurProjectName":"永鸿文化城后期补苗项目","blurName":"永鸿****"}
         */

        public boolean showQuote;
        public long lastTime;
        public String servicePoint;
        public PurchaseBean purchase;


    }
}
