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


    public int unreadQuoteCountJson;


    /**
     * 报价图片集合
     */
    public List<ImagesJsonBean> images;
    public List<ImagesJsonBean> imagesJson = new ArrayList<>();

    /**
     * 是否被采购方设置不合适
     */
    public boolean isExclude ;


    public boolean isRead ;


    public String priceTypeName;


    public String closeDateStr;

    public AttrData attrData = new AttrData();




    public String createDate ;


    @Override
    public int getItemType() {
        return 1;
    }


    public static class AttrData {
        public String phone = "";
        public String storeName = "";
        public String headImage = "";
        public String count ;
        public String name ;

        public String unitTypeName = "";
        public String specText = "";
        public String displayName = "";
        public String cityName = "";

        /**
         *  UserQuote{id='ae7b275efdd545ca8b047cde75b3a947',
         *  sellerId='2876f7e0f51c4153aadc603b661fedfa',
         *  purchaseId='2a3d420100284c27a90ac5d56270adc9', price='123.00',
         *  priceType='shangche', ciCode='1403', cityName='山西 阳泉', cityBean=null,
         *  remarks='4564', images=null, imagesJson=[], isExclude=null, priceTypeName='上车价',
         *  closeDateStr='null', attrData=AttrData{phone='', storeName='', headImage='',
         *  count='77777', unitTypeName='株',
         *  specText='胸径：1-2 高度：3-4 冠幅：5-6', displayName='', cityName='内蒙古 巴彦淖尔'}}
         * @return
         */

        @Override
        public String toString() {
            return "AttrData{" +
                    "phone='" + phone + '\'' +
                    ", storeName='" + storeName + '\'' +
                    ", headImage='" + headImage + '\'' +
                    ", count='" + count + '\'' +
                    ", unitTypeName='" + unitTypeName + '\'' +
                    ", specText='" + specText + '\'' +
                    ", displayName='" + displayName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserQuote{" +
                "id='" + id + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                ", price='" + price + '\'' +
                ", priceType='" + priceType + '\'' +
                ", ciCode='" + ciCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityBean=" + cityBean +
                ", remarks='" + remarks + '\'' +
                ", images=" + images +
                ", imagesJson=" + imagesJson +
                ", isExclude=" + isExclude +
                ", priceTypeName='" + priceTypeName + '\'' +
                ", closeDateStr='" + closeDateStr + '\'' +
                ", attrData=" + attrData +
                '}';
    }
}
