package com.hldj.hmyg.bean;

import com.hldj.hmyg.util.ConstantState;

import java.io.Serializable;

/**
 * 用于接收  普通的 成功失败返回  的gson 笨啊、===
 */

public class SimpleGsonBean implements Serializable {

    /**
     * =============json=========={"code":"1","msg":"操作成功"}
     */
    public String code = "";
    public String msg = "失败";
    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        public String headImage = "";

        public boolean hasProjectManage = false;

        private boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public int quoteUsedCount;

    }


    public boolean isSucceed()
    {
        return code.equals(ConstantState.SUCCEED_CODE);
    }


}
