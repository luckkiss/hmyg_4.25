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

        public static class HeadPurchaseBean {
            public String cityName = "";/*ok*/
            public String closeDate = "";/*ok*/
            public String displayPhone = "";/*ok*/
            public String dispatchPhone = "";/*ok*/
            public String dispatchName = "";/*ok*/
            public String displayName = "";/*ok*/
            public String blurProjectName = "";/*ok*/
            public String quoteDesc = "";/*ok*/
            public String num = "";/*ok*/
            public BuyerBean buyer = new BuyerBean();

            public static class BuyerBean {
                public String displayName = "";/*ok*/
            }
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
