package com.hldj.hmyg.saler.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class UsedQuoteListBean implements Serializable{
    /**
     * id : 98532f53c0444cbaac0fe200ba5eb88b
     * remarks :
     * createBy : 5f5f9c47-5363-4777-9445-f297b5e466af
     * createDate : 2017-04-22 09:25:53
     * cityCode : 1303
     * cityName : 河北 秦皇岛
     * prCode : 13
     * ciCode : 1303
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 74b0e9c44dd0499381a1adcadae6b4c9
     * secondSeedlingTypeId : 82c76986-d0c3-4ad1-9a89-2a384b02ca51
     * dbh : 55
     * dbhType : size30
     * height : 55
     * crown : 44
     * imageUrl : http://image.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg
     * ossUrl : http://images.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg@!wt
     * unitTypeName :
     * plantTypeName :
     * diameterTypeName :
     * dbhTypeName : 0.3M量
     * thumbnailImageUrl : http://images.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg@!65_87
     * smallImageUrl : http://images.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg@!170_227
     * mediumImageUrl : http://images.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg@!210_280
     * largeImageUrl : http://images.hmeg.cn/upload/image/201704/646857da7e6c4788ac7dc8b105c03b74.jpg@!300_400
     * seedlingParams : dbh,height,crown
     * paramsList : ["dbh","height","crown"]
     * specList : [{"name":"地径","value":"55CM"},{"name":"高度","value":"55CM"},{"name":"冠幅","value":"44CM"}]
     * specText : 地径:55 高度:55 冠幅:44
     * purchaseId : 76d9e45b904c4a84b2f782a63884edf6
     * purchaseItemId : 40dbf754f26142dca880d728395db196
     * sellerId : 5f5f9c47-5363-4777-9445-f297b5e466af
     * price : 1220
     * isInvoice : false
     * isUsed : true
     * implementRemarks :
     * quoteImplementStatus : qualified
     * statusName : 已中标
     * sellerName : 徐枫
     * sellerPhone : 18068780123
     * status : used
     * purchaseItemStatus : finished
     * plantType : planted
     */

    public String id;
    public String remarks;
    public String createBy;
    public String createDate;
    public String cityCode;
    public String cityName;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String firstSeedlingTypeId;
    public String secondSeedlingTypeId;
    public int dbh;
    public String dbhType;
    public int height;
    public int crown;
    public String imageUrl;
    public String ossUrl;
    public String unitTypeName;
    public String plantTypeName;
    public String diameterTypeName;
    public String dbhTypeName;
    public String thumbnailImageUrl;
    public String smallImageUrl;
    public String mediumImageUrl;
    public String largeImageUrl;
    public String seedlingParams;
    public String specText;
    public String purchaseId;
    public String purchaseItemId;
    public String sellerId;
    public int price;
    public boolean isInvoice;
    public boolean isUsed;
    public String implementRemarks;
    public String quoteImplementStatus;
    public String statusName;
    public String sellerName;
    public String sellerPhone;
    public String status;
    public String purchaseItemStatus;
    public String plantType;
    public List<String> paramsList;
    public List<SpecListBeanX> specList;

    public static class SpecListBeanX implements Serializable{
        /**
         * name : 地径
         * value : 55CM
         */

        public String name;
        public String value;
    }
}
