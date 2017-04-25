package com.hldj.hmyg.bean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class LoginGsonBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"userId":"2876f7e0f51c4153aadc603b661fedfa"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginGsonBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * userId : 2876f7e0f51c4153aadc603b661fedfa
         */

        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }
}
