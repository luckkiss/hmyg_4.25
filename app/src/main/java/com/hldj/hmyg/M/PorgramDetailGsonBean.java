package com.hldj.hmyg.M;

import com.hldj.hmyg.model.MyProgramGsonBean;

/**
 * Created  罗擦擦
 */

public class PorgramDetailGsonBean {

    public String code = "";
    public String msg = "";

    public DataBeanX data;

    public static class DataBeanX {
        public MyProgramGsonBean.DataBeanX.PageBean.DataBean project = new MyProgramGsonBean.DataBeanX.PageBean.DataBean();
    }


}
