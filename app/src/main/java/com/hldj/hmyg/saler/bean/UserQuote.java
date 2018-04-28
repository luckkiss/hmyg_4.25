package com.hldj.hmyg.saler.bean;

import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseJsonBean;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户报价 实体
 */

public class UserQuote implements MultiItemEntity {


    public String id;

    /**
     * 报价人ID
     */
    public String sellerId;

    /**
     * 用户求购单ID
     */
    public String purchaseId;

    /**
     * 报价价格
     */
    public String price;

    /**
     * 价格类型
     */
    public String priceType;
//    public PriceType priceType;

    public String ciCode;
    public String cityName;
    public PurchaseJsonBean.CiCityBean cityBean;

    /**
     * 备注
     */
    public String remarks;


    /**
     * 报价图片集合
     */
    public List<ImagesJsonBean> images;
    public List<ImagesJsonBean> imagesJson = new ArrayList<>();

    /**
     * 是否被采购方设置不合适
     */
    public Boolean isExclude;


    public String priceTypeName;


    public String closeDateStr;

    public AttrData attrData = new AttrData();

    @Override
    public int getItemType() {
        return 1;
    }


    public static class AttrData {
        public String phone = "";
        public String storeName = "";
        public String headImage = "";
        public String unitTypeName = "";
        public String displayName = "";
/**
 *  "attrData": {
 "phone": "13394058505",
 "storeName": "测试店铺名称",
 "headImage": "http://image.hmeg.cn/upload/image/201712/506d04f393214eada4713d2c319149f5.jpeg",
 "unitTypeName": "斤",
 "displayName": "闽南一路"
 },
 */
    }
}
