package com.hldj.hmyg.M;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class UserFollow {


    public String id = "";

    /**
     * 关注人ID
     */
    public String userId = "";

    /**
     * 本关注人ID
     */
    public String beFollowUserId = "";


    public String createBy = "";

    public String createDate = "";
    public AttData attrData = new AttData();

    /**
     * {"code":"1","msg":"操作成功","data":{"page":{"pageNo":0,
     * "pageSize":20,"total":3,"data":
     *
     * [{"id":"881cb47bb62e4f1db7087b9514482fa0",
     * "createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","createDate":
     * "2018-05-02 09:31:39",
     * "attrData":{"headImage":"http://image.hmeg.cn/upload/image/201803/c21c9c443d1c45478c4ce7cba553181e.png"
     * ,"cityName":"福建 厦门","followCount":1,"displayName":"大傻么么哒",
     * "mainType":"花木墨家"},"userId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed",
     * "beFollowUserId":"2876f7e0f51c4153aadc603b661fedfa"},{"id":"8491f360fcc4468caf7a5fa743d93249",
     * "createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","createDate":"2018-04-26 18:06:16","
     * attrData":{"headImage":"http://image.hmeg.cn/upload/image/201712/506d04f393214eada4713d2c319149f5.jpeg",
     * "cityName":"福建 厦门","followCount":1,"displayName":"闽南一路","mainType":"测试经营品种哈哈哈哈"},
     * "userId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","beFollowUserId":"2659ffd89e0146188b48ab8bdc66903f"}
     *
     *
     *
     * ,{"id":"4639984573d543129ddef499c1541a91"
     * ,"createBy":"bc8ee23e-cd3d-446d-8dce-747c6d32daed",
     * "createDate":"2018-04-26 14:41:00"
     * ,"attrData":{"headImage":"http://image.hmeg.cn/upload/image/201801/cbe641e75c444103b369f24fb65afcd2.jpeg","cityName":"江西 南昌","followCount":1,"displayName":"罗小黑","mainType":"无"},"userId":"bc8ee23e-cd3d-446d-8dce-747c6d32daed","beFollowUserId":"3378ca2d-b09b-411b-a3d0-28766e314685"}],"
     * maxResults":20,"firstResult":0}},"version":"tomcat7.0.53"}
     */

    public static class AttData {
        public String headImage = "";
        public String cityName = "";
        public String followCount = "";
        public String displayName = "";
        public String mainType = "";


        public String timeStampStr = "";
        public boolean isFollowed ;

    }


}
