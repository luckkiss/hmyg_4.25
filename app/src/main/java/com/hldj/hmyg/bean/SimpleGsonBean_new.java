package com.hldj.hmyg.bean;

import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
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
        public PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase;
        public T purchaseItem;
        public T page;
        public T userPointTypeList;
        public int point;
        public VideoData video;

        public T list;
        public T quoteList;

//        bean_new = {SimpleGsonBean_new@8730} "SimpleGsonBean_new{code='1', msg='操作成功', data=com.hldj.hmyg.bean.SimpleGsonBean_new$Data@3a14767, page=null}"
//        code = "1"
//        msg = "操作成功"
//        data = {SimpleGsonBean_new$Data@8759}
//        headPurchase = null
//        purchaseItem = null
//        page = null
//        this$0 = null
//        shadow$_klass_ = {Class@3349} "class com.hldj.hmyg.bean.SimpleGsonBean_new$Data"
//        shadow$_monitor_ = -2086582425
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
