package com.hldj.hmyg.M;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  罗擦擦
 */

public class ProjectPageGsonBean implements Serializable {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"page":{"pageNo":0,"pageSize":10,"total":1,"data":[{"id":"adc19a7775104a25b4e33233b0f6a5cb","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-08 22:59:45","carNum":"豫A3666","driverName":"","driverPhone":"15670227722","carType":"type13H","carPrice":"2500","status":"receipted","createForCustomer":true,"loadCarDate":"2017-06-08","receiptDate":"2017-06-09 16:12","carTypeName":"13米高栏","statusName":"已到货","itemNames":"红豆杉10株、小叶榄仁2株、小叶榄仁3株、小叶栀子200株、鹅掌柴100株、黄金榕球2株、满天星150株、蒲葵150株、大花芦莉150株、红花继木100株、亮叶朱蕉500株","projectNames":"永鸿文化城后期补苗项目,莆田市区项目","invoiceNames":"FH0001242,FH0001241,FH0001240,FH0001243","customerName":"钟小华","carFirstItemName":"红豆杉","carItemsCount":11}],"firstResult":0,"maxResults":10}}
     */

    public String code;
    public String msg;
    public DataBeanX data;

    public static class DataBeanX {
        /**
         * page : {"pageNo":0,"pageSize":10,"total":1,"data":[{"id":"adc19a7775104a25b4e33233b0f6a5cb","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-08 22:59:45","carNum":"豫A3666","driverName":"","driverPhone":"15670227722","carType":"type13H","carPrice":"2500","status":"receipted","createForCustomer":true,"loadCarDate":"2017-06-08","receiptDate":"2017-06-09 16:12","carTypeName":"13米高栏","statusName":"已到货","itemNames":"红豆杉10株、小叶榄仁2株、小叶榄仁3株、小叶栀子200株、鹅掌柴100株、黄金榕球2株、满天星150株、蒲葵150株、大花芦莉150株、红花继木100株、亮叶朱蕉500株","projectNames":"永鸿文化城后期补苗项目,莆田市区项目","invoiceNames":"FH0001242,FH0001241,FH0001240,FH0001243","customerName":"钟小华","carFirstItemName":"红豆杉","carItemsCount":11}],"firstResult":0,"maxResults":10}
         */

        public PageBean page;

        public static class PageBean {
            /**
             * pageNo : 0
             * pageSize : 10
             * total : 1
             * data : [{"id":"adc19a7775104a25b4e33233b0f6a5cb","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-08 22:59:45","carNum":"豫A3666","driverName":"","driverPhone":"15670227722","carType":"type13H","carPrice":"2500","status":"receipted","createForCustomer":true,"loadCarDate":"2017-06-08","receiptDate":"2017-06-09 16:12","carTypeName":"13米高栏","statusName":"已到货","itemNames":"红豆杉10株、小叶榄仁2株、小叶榄仁3株、小叶栀子200株、鹅掌柴100株、黄金榕球2株、满天星150株、蒲葵150株、大花芦莉150株、红花继木100株、亮叶朱蕉500株","projectNames":"永鸿文化城后期补苗项目,莆田市区项目","invoiceNames":"FH0001242,FH0001241,FH0001240,FH0001243","customerName":"钟小华","carFirstItemName":"红豆杉","carItemsCount":11}]
             * firstResult : 0
             * maxResults : 10
             */
            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<InvoiceCarBean> data = new ArrayList<>();


        }
    }
}
