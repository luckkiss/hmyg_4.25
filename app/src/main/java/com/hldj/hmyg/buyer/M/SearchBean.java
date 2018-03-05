package com.hldj.hmyg.buyer.M;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索 获取下来的  对象gson
 */

public class SearchBean implements Serializable {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"storeList":[{"id":"3c8d8c732c3d405e91ba3119d3d9cd7f","name":"花海绵","index_name":"hmeg_store"},{"id":"ec490a435fa0440fbdb811e644af9d2f","name":"桅子花开","index_name":"hmeg_store"},{"id":"f66841a7c79b4a2892fa2fdb95d586ef","name":"花概念园林","index_name":"hmeg_store"},{"id":"c512e5ccfc7c4883b8b2d1960480899a","name":"红花鸡蛋花","index_name":"hmeg_store"},{"id":"d1de55f624fb4b5ea89ed74e771525e1","name":"花丽雅花木场","index_name":"hmeg_store"},{"id":"d99641626f1f4c0c88857af1c18d3cd0","name":"漳平市花伯园艺","index_name":"hmeg_store"},{"id":"f0c9c618a6bc4bcdb8b360b7947b2bf0","name":"黄花风铃，紫花风铃，蓝花楹，檀香","index_name":"hmeg_store"},{"id":"d42e503cf0ac4d5b8a71b9aaa3973dfb","name":"石龙连心园林花卉","index_name":"hmeg_store"},{"id":"46b149e2c6a0439f88a96bc0d163cb6b","name":"黄坊花卉","index_name":"hmeg_store"},{"id":"e0b5e3effbbe4db1a6262c5417076199","name":"自然花卉世界","index_name":"hmeg_store"},{"id":"b25690b220414034ba529abfad4b464b","name":"乾坤花卉种植园","index_name":"hmeg_store"},{"id":"b0f79716aecc40439b52484329d103c2","name":"广业花木","index_name":"hmeg_store"},{"id":"d877d555250f4788bc3a6b45698fcbc1","name":"林丰花木","index_name":"hmeg_store"},{"id":"a646a9b0f8aa41ac9b3fd6620dfc9aa1","name":"花卉世界","index_name":"hmeg_store"},{"id":"0d39400b7d17406cbf7bc2a6a797c74e","name":"尚佳花木","index_name":"hmeg_store"},{"id":"0330db78db4747fe951a6d24b0c6af74","name":"泉兴花木","index_name":"hmeg_store"},{"id":"2158459bca174dd29de0adf23b733cf0","name":"俊景花木","index_name":"hmeg_store"},{"id":"6bb95e808ec64cee8edf63276607f740","name":"花木城","index_name":"hmeg_store"},{"id":"b3bc4c647c69418683600889e38d4cda","name":"花木","index_name":"hmeg_store"},{"id":"0a1108ea90734ae0bdcff17f551ee2c9","name":"宇帆花木","index_name":"hmeg_store"}],"seedlingList":[{"name":"花叶假连翘"},{"name":"花叶良姜"},{"name":"花叶芦竹"},{"name":"花叶美人蕉"},{"name":"花叶女贞"},{"name":"花叶垂榕"},{"name":"花叶鹅掌柴"},{"name":"花叶芦苇荻"},{"name":"花叶扶桑"},{"name":"花篮式同安红三角梅"}]}
     * version : v3.0.4
     */

    public String code;
    public String msg;
    public DataBean data;
    public String version;

    public static class DataBean {
        public List<StoreListBean> storeList;
        public List<SeedlingListBean> seedlingList;

        public static class StoreListBean {
            /**
             * id : 3c8d8c732c3d405e91ba3119d3d9cd7f
             * name : 花海绵
             * index_name : hmeg_store
             */

            public String id;
            public String name;
            public String index_name;
        }

        public static class SeedlingListBean {
            /**
             * name : 花叶假连翘
             */

            public String name;
        }
    }
}
