package com.hldj.hmyg.M;

import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.weidet.entity.IExpandable;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.saler.M.PurchaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter.TYPE_LEVEL_1;

/**
 * Created by 罗擦擦  实现  多级菜单 数据 on 2017/6/30 0030.
 */

public class QuoteListJsonBean implements Serializable, MultiItemEntity, IExpandable {


    public static final int QuoteListJsonBean_Type = 666;
    /**
     * id : fe96fde19e1445c2955fb9155f6cd22c
     * remarks : 一株胸径19.8分左右，一株胸径23分
     * createBy : b9cef730fa6142eb80bbd7d646e40c66
     * createDate : 2017-06-01 09:54:21
     * attrData : {"isSupplier":false,"servicePrice":"3520.00"}
     * cityCode : 3506
     * cityName : 福建 漳州
     * prCode : 35
     * ciCode : 3506
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 74b0e9c44dd0499381a1adcadae6b4c9
     * secondSeedlingTypeId : 722ec58a-2394-44f4-84c4-772ed54ad9a9
     * dbh : 20
     * dbhType : size100
     * plantType : heelin
     * unitType : plant
     * unitTypeName : 株
     * plantTypeName : 假植苗
     * diameterTypeName :
     * dbhTypeName : 1.0M量
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * imagesJson : [{"id":"8fac814afa4748f8a3c3f5da55af6d63","imageType":"demandQuote","name":"19.8.jpg","url":"http://image.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg","sourceId":"fe96fde19e1445c2955fb9155f6cd22c","ossUrl":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!450_600"},{"id":"f7422846d3f942d8980d8cd88004639f","imageType":"demandQuote","name":"20.jpg","url":"http://image.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg","sourceId":"fe96fde19e1445c2955fb9155f6cd22c","ossUrl":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!450_600"}]
     * seedlingParams : dbh,height,crown
     * paramsList : ["dbh","height","crown"]
     * specList : [{"name":"米径","value":"20CM"}]
     * specText : 米径:20
     * purchaseId : 60a426d34aa5443fa217ade0b6fdd242
     * purchaseItemId : f52f8fd5e7e74c0fa97abd1f3cfd1228
     * sellerId : 7a920b48-502e-4262-8e0a-81ed9d377861
     * price : 3200
     * isInvoice : true
     * isUsed : true
     * sendType : hmeg
     * implementRemarks : 一株胸径19.8     一株胸径23
     * quoteImplementStatus : qualified
     * statusName : 已中标
     * sellerName : 黄志强
     * sellerPhone : 15159672278
     * status : used
     * purchaseItemStatus : quoting
     * height : 900
     * crown : 350
     * imageUrl : http://image.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg
     * ossUrl : http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!wt
     */

    public String id;
    public String remarks;
    public String createBy;
    public String createDate;
    public PurchaseBean.AttrDataBean attrData;
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
    public String plantType;
    public String unitType;
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
    public String price;
    public String prePrice = "-";
    public boolean isInvoice;
    public boolean isUsed;
    public String sendType = "";
    public String implementRemarks;
    public String quoteImplementStatus = "";
    public String statusName;
    public String sellerName;
    public String sellerPhone;
    public String status;
    public String purchaseItemStatus;
    public int height;
    public int crown;
    public String imageUrl;
    public String ossUrl;
    public List<ImagesJsonBean> imagesJson = new ArrayList<>();
    public List<String> paramsList;
    public List<ItemBean.SpecListBean> specList;


    @Override
    public String toString() {
        return "QuoteListJsonBean{" +
                "id='" + id + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate='" + createDate + '\'' +
                ", attrData=" + attrData +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", prCode='" + prCode + '\'' +
                ", ciCode='" + ciCode + '\'' +
                ", coCode='" + coCode + '\'' +
                ", twCode='" + twCode + '\'' +
                ", firstSeedlingTypeId='" + firstSeedlingTypeId + '\'' +
                ", secondSeedlingTypeId='" + secondSeedlingTypeId + '\'' +
                ", dbh=" + dbh +
                ", dbhType='" + dbhType + '\'' +
                ", plantType='" + plantType + '\'' +
                ", unitType='" + unitType + '\'' +
                ", unitTypeName='" + unitTypeName + '\'' +
                ", plantTypeName='" + plantTypeName + '\'' +
                ", diameterTypeName='" + diameterTypeName + '\'' +
                ", dbhTypeName='" + dbhTypeName + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", mediumImageUrl='" + mediumImageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                ", seedlingParams='" + seedlingParams + '\'' +
                ", specText='" + specText + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                ", purchaseItemId='" + purchaseItemId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", price='" + price + '\'' +
                ", isInvoice=" + isInvoice +
                ", isUsed=" + isUsed +
                ", sendType='" + sendType + '\'' +
                ", implementRemarks='" + implementRemarks + '\'' +
                ", quoteImplementStatus='" + quoteImplementStatus + '\'' +
                ", statusName='" + statusName + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", status='" + status + '\'' +
                ", purchaseItemStatus='" + purchaseItemStatus + '\'' +
                ", height=" + height +
                ", crown=" + crown +
                ", imageUrl='" + imageUrl + '\'' +
                ", ossUrl='" + ossUrl + '\'' +
                ", imagesJson=" + imagesJson +
                ", paramsList=" + paramsList +
                ", specList=" + specList +
                '}';
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_1;
    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void setExpanded(boolean expanded) {

    }

    @Override
    public List getSubItems() {
        return null;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
