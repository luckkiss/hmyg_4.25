package com.hldj.hmyg.Ui.friend.bean;

/**
 * 头部点击对象  详情
 */

public class HeadDetail {

        /**
         * phone : 17074990702
         * storeName : 最软0
         * agentScoreRank : {"id":"86581331376844d4a44e828bee1a4622","createDate":"2017-12-11 08:53:33",
         * "sellerId":"2876f7e0f51c4153aadc603b661fedfa","quoteCount":180,"quoteUsedCount":20,"starsScore":6,"agentGrade":"level1","orderBy":"agentGrade DESC"}
         * cityName : 福建 厦门
         * mainType : 花木墨家
         * momentsCount : 193
         * identity : false
         * level : level1
         * headImage : http://image.hmeg.cn/upload/image/201803/c21c9c443d1c45478c4ce7cba553181e.png
         * userId : 2876f7e0f51c4153aadc603b661fedfa
         * hasMoments : true
         * levelName : 合作供应商
         * isFollowed : false
         * displayName : 大傻么么哒
         * storeId : 789d30acffd74349ab816ece9bb312c6
         */

        public String phone;
        public String storeName;
        public AgentScoreRankBean agentScoreRank;
        public String cityName;
        public String mainType;
        public int momentsCount;
        public boolean identity;
        public String level;
        public String headImage;
        public String userId;
        public boolean hasMoments;
        public String levelName;
        public boolean isFollowed;
        public String displayName;
        public String storeId;

        public static class AgentScoreRankBean {
            /**
             * id : 86581331376844d4a44e828bee1a4622
             * createDate : 2017-12-11 08:53:33
             * sellerId : 2876f7e0f51c4153aadc603b661fedfa
             * quoteCount : 180
             * quoteUsedCount : 20
             * starsScore : 6
             * agentGrade : level1
             * orderBy : agentGrade DESC
             */

            public String id;
            public String createDate;
            public String sellerId;
            public int quoteCount;
            public int quoteUsedCount;
            public int starsScore;
            public String agentGrade;
            public String orderBy;
        }

}
