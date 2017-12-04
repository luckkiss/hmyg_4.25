package com.hldj.hmyg.bean;

import com.hldj.hmyg.util.ConstantState;

/**
 * 用于接收  普通的 成功失败返回  的gson 笨啊、===
 */

public class SimpleGsonBean_new<T> {

    /**
     * =============json=========={"code":"1","msg":"操作成功"}
     */
    public String code = "";
    public String msg = "失败";
    public Data<T> data;
    public SimplePageBean page;


    public boolean isSucceed() {
        return code.equals(ConstantState.SUCCEED_CODE);
    }

    public class Data<T> {
        public T purchaseItem  ;
        public T page ;
    }


    @Override
    public String toString() {
        return "SimpleGsonBean_new{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", page=" + page +
                '}';
    }
}
