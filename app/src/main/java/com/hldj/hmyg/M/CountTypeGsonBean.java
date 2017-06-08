package com.hldj.hmyg.M;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class CountTypeGsonBean {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"countMap":{"backed":1,"unaudit":0,"outline":0,"published":0,"unsubmit":0,"all":1}}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * countMap : {"backed":1,"unaudit":0,"outline":0,"published":0,"unsubmit":0,"all":1}
         */

        public CountMapBean countMap;

        public static class CountMapBean {
            /**
             * backed : 1
             * unaudit : 0
             * outline : 0
             * published : 0
             * unsubmit : 0
             * all : 1
             */

            public int backed;
            public int unaudit;
            public int outline;
            public int published;
            public int unsubmit;
            public int all;
        }
    }
}
