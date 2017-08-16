package com.hldj.hmyg.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class CityGsonBean {

    public String code ;
    public String msg ;
    public DataBean data;
    public static class DataBean {
        public List<ChildBeans> bannerList;
        public List<ChildBeans> cityList ;

    }


    public static class ChildBeans {
        public String id = "";
        public String name = "";
        public String fullName = "";
        public String cityCode = "";
        public String parentCode = "";
        public String level = "";
        public   List<ChildBeans> childs ;

        @Override
        public String toString() {
            return "ChildBeans{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", cityCode='" + cityCode + '\'' +
                    ", parentCode='" + parentCode + '\'' +
                    ", level='" + level + '\'' +
                    ", childs=" + childs +
                    '}';
        }
    }




//    public List<ProvinceModel> bannerList;


}
