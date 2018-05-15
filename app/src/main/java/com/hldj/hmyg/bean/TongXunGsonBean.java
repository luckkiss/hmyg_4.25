package com.hldj.hmyg.bean;

import com.hldj.hmyg.util.ContactInfoParser;

import java.util.List;

public class TongXunGsonBean {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"userData":[{"py":"","userList":[{"id":"2659ffd89e0146188b48ab8bdc66903f","phone":"13394058505","headImage":"http://image.hmeg.cn/upload/image/201712/506d04f393214eada4713d2c319149f5.jpeg","name":"zh","isFollowed":true}]},{"py":"C","userList":[{"id":"2876f7e0f51c4153aadc603b661fedfa","phone":"17074990702","headImage":"http://image.hmeg.cn/upload/image/201803/c21c9c443d1c45478c4ce7cba553181e.png","name":"操作/luocaca","isFollowed":true}]}]}
     * version : v3.0.4
     */

    public String code;
    public String msg;
    public DataBean data;
    public String version;

    public static class DataBean {
        public List<UserDataBean> userData;

        public static class UserDataBean {
            /**
             * py :
             * userList : [{"id":"2659ffd89e0146188b48ab8bdc66903f","phone":"13394058505","headImage":"http://image.hmeg.cn/upload/image/201712/506d04f393214eada4713d2c319149f5.jpeg"
             * ,"name":"zh","isFollowed":true}]
             */
            public String py;
            public List<ContactInfoParser.ContactInfo> userList;

//            public static class ContactInfo {
//                /**
//                 * id : 2659ffd89e0146188b48ab8bdc66903f
//                 * phone : 13394058505
//                 * headImage : http://image.hmeg.cn/upload/image/201712/506d04f393214eada4713d2c319149f5.jpeg
//                 * name : zh
//                 * isFollowed : true
//                 */
//
//                public String id;
//                public String phone;
//                public String headImage;
//                public String name;
//                public boolean isFollowed;
//            }
        }
    }
}
