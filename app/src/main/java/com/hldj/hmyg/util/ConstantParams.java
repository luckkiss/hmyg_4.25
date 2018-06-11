package com.hldj.hmyg.util;

/**
 * 定义状态常量  服务器全局
 * Created by luocaca on 2017/4/12.
 */

public interface ConstantParams {


    /**
     * 到岸价
     */
    String pageIndex = "pageIndex";
    String pageSize = "pageSize";

    String searchKey = "searchKey";

    String name = "name";


    /**
     * 到岸价
     */
    String prePrice = "prePrice";
    /**
     * 苗木单价
     */
    String price = "price";
    /**
     * 地径
     */
    String diameter = "diameter";
    String minDiameter = "minDiameter";
    String maxDiameter = "maxDiameter";



    /**
     * 米径
     */
    String mijing = "mijing";
    String minMijing = "minMijing";
    String maxMijing = "maxMijing";


    /**
     * 胸径
     */
    String dbh = "dbh";
    /*最小 胸径*/
    String minDbh = "minDbh";
    /*最大 胸径*/
    String maxDbh = "maxDbh";

    /**
     * 高度
     */
    String height = "height";
    /*最小 高度*/
    String minHeight = "minHeight";
    /*最大 高度*/
    String maxHeight = "maxHeight";


    /**
     * 冠幅
     */
    String crown = "crown";
    /*最小 冠幅*/
    String minCrown = "minCrown";
    /*最大 冠幅*/
    String maxCrown = "maxCrown";


    /**
     * 脱杆高
     */
    String offbarHeight = "offbarHeight";
    String minOffbarHeight = "minOffbarHeight";
    String maxOffbarHeight = "maxOffbarHeight";


    /**
     * 长度
     */
    String length = "length";
    String minLength = "minLength";
    String maxLength = "maxLength";


    /**
     * 数量
     */
    String count = "count";
    /**
     * 地径类型
     */
    String diameterType = "diameterType";
    /**
     * 种植类型
     */
    String plantType = "plantType";
    /**
     * 胸径类型
     */
    String dbhType = "dbhType";
    /**
     * 备注
     */
    String remarks = "remarks";
    /**
     * 上传图片json
     */
    String imagesData = "imagesData";
    /**
     * 采购单 id
     */
    String purchaseId = "purchaseId";
    /**
     * 采购项目  id
     */
    String purchaseItemId = "purchaseItemId";
    /**
     * 城市的状态 标识码
     */
    String cityCode = "cityCode";
    /**
     * 人 的 唯一id  id
     */
    String id = "id";
    /**
     * 代购
     */
    String protocol = "protocol";


    /**
     * 地栽苗
     */
    String planted = "planted";
    /**
     * 容器苗
     */
    String container = "container";
    /**
     * 假植苗
     */
    String heelin = "heelin";
    /**
     * 移植苗
     */
    String transplant = "transplant";


    /**
     * 直购
     */
    String direct = "direct";


    /**
     * 真实姓名
     */
    String realName = "realName";

    /**
     * 身份证号
     */
    String identityNum = "identityNum";

    /**
     * 法人姓名
     */
    String legalPersonName = "legalPersonName";


    /**
     * 企业名
     */
    String companyName = "companyName";
    /**
     * 营业执照  号码
     */
    String licenceNum = "licenceNum";
    /**
     * 店铺id
     */
    String storeId = "storeId";
    /**
     * 营业执照
     */
    String licenceData = "licenceData";
    /**
     * 法人身份证照片
     */
    String legalPersonData = "legalPersonData";


    /**
     * 正面身份证  数据 json
     */
    String frontData = "frontData";

    /**
     * 反面身份证  数据 json
     */
    String backData = "backData";

    /**
     * projectType
     direct直购
     protocol代购    不同的布局不一样  提交内容不一样

     isQuoted
     //是否报过价

     /admin/quote/save
     保存报价  提交报价
     提交后重新获取item 数据会有一个sellerQuoteJson 来显示报价信息
     */


}
