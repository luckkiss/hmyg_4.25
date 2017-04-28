package com.hldj.hmyg.bean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class UserInfoGsonBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"user":{"id":"2876f7e0f51c4153aadc603b661fedfa","createDate":"2017-03-28 14:09:18","prCode":"","ciCode":"","coCode":"","twCode":"","userName":"u_17074990702","plainPassword":"123456aa","phone":"17074990702","status":"enabled","isInvoices":false,"permissions":"PUBLISH_PURCHASE,ADD_TO_CART,PUBLISH_SEEDLING,QUOTE,QUOTE_BEFORE","headImage":"http://image.hmeg.cn/upload/image/201703/84da1c9e8de04505b5e102a4f5987bb8.png","receiptMsg":true,"isActivated":true,"agentTypeName":"","isDirectAgent":false,"displayName":"u_17074990702","adminDisplayName":"17074990702","displayPhone":"17074990702","permissionsName":"发布采购信息，添加购物车，发布苗木资源，采购报价，报价信息先发后审，"}}
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

    public static class DataBean {
        /**
         * user : {"id":"2876f7e0f51c4153aadc603b661fedfa","createDate":"2017-03-28 14:09:18","prCode":"","ciCode":"","coCode":"","twCode":"","userName":"u_17074990702","plainPassword":"123456aa","phone":"17074990702","status":"enabled","isInvoices":false,"permissions":"PUBLISH_PURCHASE,ADD_TO_CART,PUBLISH_SEEDLING,QUOTE,QUOTE_BEFORE","headImage":"http://image.hmeg.cn/upload/image/201703/84da1c9e8de04505b5e102a4f5987bb8.png","receiptMsg":true,"isActivated":true,"agentTypeName":"","isDirectAgent":false,"displayName":"u_17074990702","adminDisplayName":"17074990702","displayPhone":"17074990702","permissionsName":"发布采购信息，添加购物车，发布苗木资源，采购报价，报价信息先发后审，"}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }


    }


    @Override
    public String toString() {
        return "UserInfoGsonBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
