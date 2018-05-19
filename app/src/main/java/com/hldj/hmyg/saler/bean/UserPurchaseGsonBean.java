package com.hldj.hmyg.saler.bean;

import com.hldj.hmyg.util.ConstantState;

import java.util.List;

/**
 * 用户报价
 */

public class UserPurchaseGsonBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"storeName":"林则金的店铺","headImage":"http://image.hmeg.cn/upload/image/201801/1afe35815b074420aa2b79e9a62386ed.jpeg","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","priceType":["shangche","daoan"],"userPurchase":{"id":"94a61b8865654112a48ce03f3c6b49eb","remarks":"Ggggg","createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","createDate":"2018-04-26 09:39:15","cityCode":"3502","cityName":"福建 厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","dbhType":"size120","unitType":"plant","minDbh":55,"minHeight":22,"unitTypeName":"株","plantTypeName":"","diameterTypeName":"","dbhTypeName":"1.2M量","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","seedlingParams":"dbh,        \t\t\theight,        \t\t\tcrown","paramsList":["dbh","height","crown"],"specText":"胸径：55 高度：22","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","count":655,"validity":"day1","publishDate":1524706754000,"closeDate":1524793154000,"needImage":false,"isClose":false,"isTop":false,"closeDateStr":"2018-04-27 09:39"},"displayName":"林则金11"}
     * version : tomcat7.0.53
     */

    public String code;
    public String msg;
    public DataBean data;
    public String version;


    public boolean isClose ;
    /**
     * code : 1
     * msg : 操作成功
     * data : {"userQuote":{"id":"d1548d3bdc3943c09771e0720dbbe69c","remarks":"Hhh","createBy":"3378ca2d-b09b-411b-a3d0-28766e314685","createDate":"2018-04-27 10:45","cityCode":"3502","cityName":"福建 厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"sellerId":"3378ca2d-b09b-411b-a3d0-28766e314685","purchaseId":"fc6a62e0a52449ba9d7977256ec8414d","price":"22.00","priceType":"shangche","isExclude":false,"priceTypeName":"上车价"},"priceType":[{"text":"上车价","value":"shangche"},{"text":"到岸价","value":"daoan"}],"headerMap":{"storeName":"林则金的店铺","headImage":"http://image.hmeg.cn/upload/image/201801/1afe35815b074420aa2b79e9a62386ed.jpeg","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","displayName":"林则金11"},"userPurchase":{"id":"fc6a62e0a52449ba9d7977256ec8414d","remarks":"","createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","createDate":"2018-04-25 17:15:55","cityCode":"3502","cityName":"福建 厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","dbhType":"size130","unitType":"crowd","minDbh":11,"minHeight":11,"minCrown":11,"unitTypeName":"丛","plantTypeName":"","diameterTypeName":"","dbhTypeName":"1.3M量","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specText":"胸径：11 高度：11 冠幅：11","name":"香樟","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","count":11,"validity":"day3","publishDate":1524647755000,"closeDate":1524906955000,"needImage":true,"isClose":false,"closeDateStr":"2018-04-28 17:15"}}
     * version : tomcat7.0.53
     */

//    public String code;
//    public String msg;
//    public DataBean data;
//    public String version;
    public boolean isSucceed() {
        return code.equals(ConstantState.SUCCEED_CODE);
    }


    public static class DataBean {
        /**
         * userQuote : {"id":"d1548d3bdc3943c09771e0720dbbe69c","remarks":"Hhh","createBy":"3378ca2d-b09b-411b-a3d0-28766e314685","createDate":"2018-04-27 10:45","cityCode":"3502","cityName":"福建 厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"sellerId":"3378ca2d-b09b-411b-a3d0-28766e314685","purchaseId":"fc6a62e0a52449ba9d7977256ec8414d","price":"22.00","priceType":"shangche","isExclude":false,"priceTypeName":"上车价"}
         * priceType : [{"text":"上车价","value":"shangche"},{"text":"到岸价","value":"daoan"}]
         * headerMap : {"storeName":"林则金的店铺","headImage":"http://image.hmeg.cn/upload/image/201801/1afe35815b074420aa2b79e9a62386ed.jpeg","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","displayName":"林则金11"}
         * userPurchase : {"id":"fc6a62e0a52449ba9d7977256ec8414d","remarks":"","createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","createDate":"2018-04-25 17:15:55","cityCode":"3502","cityName":"福建 厦门","prCode":"35","ciCode":"3502","coCode":"","twCode":"","ciCity":{"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2},"firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","dbhType":"size130","unitType":"crowd","minDbh":11,"minHeight":11,"minCrown":11,"unitTypeName":"丛","plantTypeName":"","diameterTypeName":"","dbhTypeName":"1.3M量","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specText":"胸径：11 高度：11 冠幅：11","name":"香樟","ownerId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","count":11,"validity":"day3","publishDate":1524647755000,"closeDate":1524906955000,"needImage":true,"isClose":false,"closeDateStr":"2018-04-28 17:15"}
         */

        public UserQuote userQuote;
        public HeaderMapBean headerMap;
        public UserPurchase userPurchase;
        public List<PriceTypeBean> priceType;

        public static class HeaderMapBean {
            /**
             * storeName : 林则金的店铺
             * headImage : http://image.hmeg.cn/upload/image/201801/1afe35815b074420aa2b79e9a62386ed.jpeg
             * ownerId : bc8ee23e-cd3d-446d-8dce-747c6d32daed
             * displayName : 林则金11
             */

            public String storeName;
            public boolean isClose;
            public String headImage;
            public String ownerId;
            public String displayName;
        }

        public static class PriceTypeBean {
            /**
             * text : 上车价
             * value : shangche
             */

            public String text;
            public String value;
        }
    }
}
