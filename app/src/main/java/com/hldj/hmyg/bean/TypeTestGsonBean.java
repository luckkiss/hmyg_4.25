package com.hldj.hmyg.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class TypeTestGsonBean {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"specRankList":[{"text":"不限","value":"none"},{"min":0,"text":"0-20","max":20,"value":"rank0to20"},{"min":20,"text":"20-40","max":40,"value":"rank20to40"},{"min":40,"text":"40-60","max":60,"value":"rank40to60"},{"min":60,"text":"60以上","value":"rank60ToMore"}],"specList":[{"text":"地径","value":"diameter"},{"text":"米径","value":"mijing"},{"text":"胸径","value":"dbh"}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}]}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public List<SpecListBean> specList;
        public List<PlantTypeListBean> plantTypeList;


        public static class SpecListBean {
            /**
             * text : 地径
             * value : diameter
             */

            public String text;
            public String value;
        }

        public static class PlantTypeListBean {
            /**
             * text : 地栽苗
             * value : planted
             */

            public String text;
            public String value;
        }
    }
}
