package com.hldj.hmyg.buyer.M;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class PurchaseListPageGsonBean {


    public String code;
    public String msg;
    public DataBeanX data = new DataBeanX();

    public static class DataBeanX {

        public HeadPurchaseBean headPurchase;
        public boolean expired;
        public PageBean page;
        public int subscribeUserCount;

        // "attrData": {"consumerRemarks": "厦门海石景观股份有限公司"},
        public static class HeadPurchaseBean {
            public String cityName = "";/*ok*/
            public String ciCityFullName = "";/*ok*/
            public String closeDate = "";/*ok*/
            public String preCloseDate = "";/*ok*/
            public String displayPhone = "";/*ok*/
            public String dispatchPhone = "";/*ok*/
            public String dispatchName = "";/*ok*/
            public String displayName = "";/*ok*/
            public String blurProjectName = "";/*ok*/
            public String name = "";/*ok*/
            public String quoteDesc = "";/*ok*/
            public String status = "";/*ok*/
            public String num = "";/*ok*/
            public AttrDataBean attrData = new AttrDataBean(); /*ok  单位详细信息*/
            public boolean showConsumerName = false; // 是否显示 单位信息

            public boolean needPreQuote = false; // 是否需要首轮报价

            public String consumerFullName = "";/*ok 用苗单位*/
            public BuyerBean buyer = new BuyerBean();

            public PurchaseJsonBean.CiCityBean ciCity = new PurchaseJsonBean.CiCityBean();

            public static class BuyerBean {
                public String displayName = "";/*ok*/
            }
        }

        public static class AttrDataBean {
            public String consumerRemarks = "暂无信息";//厦门海石景观股份有限公司
            public String closeDateStr = "-";//厦门海石景观股份有限公司
        }

        public static class PageBean {

            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<PurchaseItemBean_new> data = new ArrayList<>();
        }
    }
}
