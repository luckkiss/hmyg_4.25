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

        public static class UserBean {


            @Override
            public String toString() {
                return "UserBean{" +
                        "id='" + id + '\'' +
                        ", createDate='" + createDate + '\'' +
                        ", prCode='" + prCode + '\'' +
                        ", ciCode='" + ciCode + '\'' +
                        ", coCode='" + coCode + '\'' +
                        ", twCode='" + twCode + '\'' +
                        ", userName='" + userName + '\'' +
                        ", plainPassword='" + plainPassword + '\'' +
                        ", phone='" + phone + '\'' +
                        ", status='" + status + '\'' +
                        ", isInvoices=" + isInvoices +
                        ", permissions='" + permissions + '\'' +
                        ", headImage='" + headImage + '\'' +
                        ", receiptMsg=" + receiptMsg +
                        ", isActivated=" + isActivated +
                        ", agentTypeName='" + agentTypeName + '\'' +
                        ", isDirectAgent=" + isDirectAgent +
                        ", displayName='" + displayName + '\'' +
                        ", adminDisplayName='" + adminDisplayName + '\'' +
                        ", displayPhone='" + displayPhone + '\'' +
                        ", permissionsName='" + permissionsName + '\'' +
                        '}';
            }

            /**
             * id : 2876f7e0f51c4153aadc603b661fedfa
             * createDate : 2017-03-28 14:09:18
             * prCode :
             * ciCode :
             * coCode :
             * twCode :
             * userName : u_17074990702
             * plainPassword : 123456aa
             * phone : 17074990702
             * status : enabled
             * isInvoices : false
             * permissions : PUBLISH_PURCHASE,ADD_TO_CART,PUBLISH_SEEDLING,QUOTE,QUOTE_BEFORE
             * headImage : http://image.hmeg.cn/upload/image/201703/84da1c9e8de04505b5e102a4f5987bb8.png
             * receiptMsg : true
             * isActivated : true
             * agentTypeName :
             * isDirectAgent : false
             * displayName : u_17074990702
             * adminDisplayName : 17074990702
             * displayPhone : 17074990702
             * permissionsName : 发布采购信息，添加购物车，发布苗木资源，采购报价，报价信息先发后审，
             */

            private String id;
            private String createDate;
            private String prCode;
            private String ciCode;
            private String coCode;
            private String twCode;
            private String userName;
            private String plainPassword;
            private String phone;
            private String status;
            private boolean isInvoices;
            private String permissions;
            private String headImage;
            private boolean receiptMsg;
            private boolean isActivated;
            private String agentTypeName;
            private boolean isDirectAgent;
            private String displayName;
            private String adminDisplayName;
            private String displayPhone;
            private String permissionsName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getPrCode() {
                return prCode;
            }

            public void setPrCode(String prCode) {
                this.prCode = prCode;
            }

            public String getCiCode() {
                return ciCode;
            }

            public void setCiCode(String ciCode) {
                this.ciCode = ciCode;
            }

            public String getCoCode() {
                return coCode;
            }

            public void setCoCode(String coCode) {
                this.coCode = coCode;
            }

            public String getTwCode() {
                return twCode;
            }

            public void setTwCode(String twCode) {
                this.twCode = twCode;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPlainPassword() {
                return plainPassword;
            }

            public void setPlainPassword(String plainPassword) {
                this.plainPassword = plainPassword;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public boolean isIsInvoices() {
                return isInvoices;
            }

            public void setIsInvoices(boolean isInvoices) {
                this.isInvoices = isInvoices;
            }

            public String getPermissions() {
                return permissions;
            }

            public void setPermissions(String permissions) {
                this.permissions = permissions;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public boolean isReceiptMsg() {
                return receiptMsg;
            }

            public void setReceiptMsg(boolean receiptMsg) {
                this.receiptMsg = receiptMsg;
            }

            public boolean isIsActivated() {
                return isActivated;
            }

            public void setIsActivated(boolean isActivated) {
                this.isActivated = isActivated;
            }

            public String getAgentTypeName() {
                return agentTypeName;
            }

            public void setAgentTypeName(String agentTypeName) {
                this.agentTypeName = agentTypeName;
            }

            public boolean isIsDirectAgent() {
                return isDirectAgent;
            }

            public void setIsDirectAgent(boolean isDirectAgent) {
                this.isDirectAgent = isDirectAgent;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public String getAdminDisplayName() {
                return adminDisplayName;
            }

            public void setAdminDisplayName(String adminDisplayName) {
                this.adminDisplayName = adminDisplayName;
            }

            public String getDisplayPhone() {
                return displayPhone;
            }

            public void setDisplayPhone(String displayPhone) {
                this.displayPhone = displayPhone;
            }

            public String getPermissionsName() {
                return permissionsName;
            }

            public void setPermissionsName(String permissionsName) {
                this.permissionsName = permissionsName;
            }
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
