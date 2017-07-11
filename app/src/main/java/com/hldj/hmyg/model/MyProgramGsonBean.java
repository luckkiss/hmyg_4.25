package com.hldj.hmyg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 罗擦擦
 */

public class MyProgramGsonBean {
    public String code;
    public String msg;
    public DataBeanX data = new DataBeanX();

    public static class DataBeanX {


        public PageBean page = new PageBean();

        public static class PageBean {


            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<DataBean> data = new ArrayList<>();

            public static class DataBean {

                public String id;
                public String createBy;
                public String createDate;
                public String cityCode;
                public String cityName;
                public String prCode;
                public String ciCode;
                public String coCode;
                public String twCode;
                public String num;
                public String consumerId;
                public String consumerUserId;
                public String clerkId;
                public String address;
                public String type;
                public double servicePoint;
                public String projectName;
                public String projectFullName;
                public ClerkBean clerk = new ClerkBean();
                public String consumerName;
                public String consumerUserName;
                public String consumerUserPhone;
                public String consumerInfo;
                public String clerkInfo;
                public int loadCarCount;
                public int unReceiptCarCount;
                public String totalAmount;
                public String typeName;

                public static class ClerkBean {
                    public String id;
                    public String createDate;
                    public String cityCode;
                    public String cityName;
                    public String prCode;
                    public String ciCode;
                    public String coCode;
                    public String twCode;
                    public String userName;
                    public String realName;
                    public String publicName;
                    public String publicPhone;
                    public String plainPassword;
                    public String sex;
                    public String position;
                    public String tel;
                    public String phone;
                    public String email;
                    public String address;
                    public String variety;
                    public String companyName;
                    public String status;
                    public boolean isAgent;
                    public boolean isClerk;
                    public String agentType;
                    public boolean isInvoices;
                    public boolean isPurchaseConfirm;
                    public String consumerId;
                    public String agentId;
                    public String customerId;
                    public boolean isPartners;
                    public boolean cashOnDelivery;
                    public boolean receiptMsg;
                    public boolean isQuote;
                    public String tradeType;
                    public boolean isActivated;
                    public boolean isPartner;
                    public boolean appointQuote;
                    public String agentTypeName;
                    public boolean isDirectAgent;
                    public String displayName = "-";
                    public String adminDisplayName;
                    public String displayPhone;
                }
            }
        }
    }
}
