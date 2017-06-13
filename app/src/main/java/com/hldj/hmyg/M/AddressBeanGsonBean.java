package com.hldj.hmyg.M;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class AddressBeanGsonBean {
    public String code;
    public String msg;
    public DataBeanX data;

    public static class DataBeanX {


        public PageBean page;

        public static class PageBean {

            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<AddressBean> data;


        }
    }


}
