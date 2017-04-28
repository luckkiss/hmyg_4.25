package com.hldj.hmyg.buyer.M;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26.
 */

public class PurchaseJsonBean implements Serializable {

    /**
     * id : e6aa2517bbc841b49c2eeb75bb8ffc01
     * createBy : e6ae79aad8254ffcb0cfe8fd11844c89
     * createDate : 2017-04-20 16:51:24
     * cityCode : 4505
     * cityName : 北海
     * prCode : 45
     * ciCode : 4505
     * coCode :
     * twCode :
     * ciCity : {"id":"31595","name":"北海","fullName":"广西 北海","cityCode":"4505","parentCode":"45","level":2}
     * projectName : 大都·金沙湾（商业街）绿化
     * consumerId : 6bd86d86db9a4a91be917a188d79a0a8
     * consumerUserId :
     * name : 大都·金沙湾（商业街）绿化
     * num : P0000506
     * projectId : 5def53c9a18a48e5869fc2864e2cdf5e
     * receiptDate : 2017-04-24
     * publishDate : 2017-04-20 16:53:45
     * closeDate : 2017-04-26 23:00
     * needInvoice : true
     * customerId : e6ae79aad8254ffcb0cfe8fd11844c89
     * status : published
     * source : admin
     * quoteDesc : <p>
     * 报价截止时间：2017年04月26日
     * </p>
     * <p>
     * 用苗地：广西北海
     * </p>
     * <p>
     * 报价要求：（包含以下费用：1、上车费用。2、税金。<span style="color:#E53333;"></span>）&nbsp;
     * </p>
     * <p>
     * 发票要求：增值税普通发票或增值税专用发票<br />
     * <span style="color:#E53333;line-height:1.5;"></span>
     * </p>
     * <p>
     * <span style="color:#E53333;line-height:1.5;">种植要求：假植苗</span>
     * </p>
     * <p>
     * <span style="color:#E53333;line-height:1.5;">质量要求：A货<br />
     * </span>
     * </p>
     * <p>
     * <span style="color:#E53333;line-height:1.5;">测量要求：地径离地10公分处量。</span>
     * </p>
     * <p>
     * <span style="color:#E53333;line-height:1.5;"></span>报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）<br />
     * 到场规格，质量不合格做退货处理。
     * </p>
     * isCooper : false
     * type : quoting
     * dispatchPhone : 18350218809
     * dispatchName : 黄志雄
     * isPrivate : false
     * authcPhone :
     * statusName : 已发布
     * lastDays : 1
     * purchaseFormId : e6aa2517bbc841b49c2eeb75bb8ffc01
     * typeName : 采购中
     * blurName : 大都****
     */

    public String id;
    public String createBy;
    public String createDate;
    public String cityCode;
    public String cityName;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public CiCityBean ciCity;
    public String projectName;
    public String consumerId;
    public String consumerUserId;
    public String name;
    public String num;
    public String projectId;
    public String receiptDate;
    public String publishDate;
    public String closeDate;
    public boolean needInvoice;
    public String customerId;
    public String status;
    public String source;
    public String quoteDesc;
    public boolean isCooper;
    public String type;
    public String dispatchPhone;
    public String dispatchName;
    public boolean isPrivate;
    public String authcPhone;
    public String statusName;
    public int lastDays;
    public String purchaseFormId;
    public String typeName;
    public String blurName;
    public String projectType = ""; //直购与代沟

    public static class CiCityBean {
        /**
         * id : 31595
         * name : 北海
         * fullName : 广西 北海
         * cityCode : 4505
         * parentCode : 45
         * level : 2
         */

        public String id;
        public String name;
        public String fullName;
        public String cityCode;
        public String parentCode;
        public int level;
    }


}
