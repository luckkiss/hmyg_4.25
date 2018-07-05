package com.hldj.hmyg.buyer.M;

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

public class SellerQuoteJsonBean implements Serializable, MultiItemEntity, IExpandable {
    /**
     * id : 95566496cd934b1da84f986db30e6a30
     * remarks : 备注
     * createBy : 2876f7e0f51c4153aadc603b661fedfa
     * createDate : 2017-04-27 15:39:04
     * cityCode : 4505
     * cityName : 广西 北海
     * prCode : 45
     * ciCode : 4505
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 74b0e9c44dd0499381a1adcadae6b4c9
     * dbh : 58
     * height : 84
     * crown : 45
     * plantType : transplant
     * unitTypeName :
     * plantTypeName : 移植苗
     * diameterTypeName :
     * dbhTypeName :
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : dbh,height,crown
     * paramsList : ["dbh","height","crown"]
     * specList : [{"name":"胸径","value":"58CM"},{"name":"高度","value":"84CM"},{"name":"冠幅","value":"45CM"}]
     * specText : 胸径:58 高度:84 冠幅:45
     * purchaseId : 76d9e45b904c4a84b2f782a63884edf6
     * purchaseItemId : 40dbf754f26142dca880d728395db196
     * sellerId : 2876f7e0f51c4153aadc603b661fedfa
     * price : 15
     * sellerName :
     * sellerPhone : 17074990702
     * purchaseItemStatus : quoting
     */

    public String id;
    public String remarks;
    public String createBy;
    public String createDate = "-";
    public String quoteDateStr;
    public String cityCode;
    public String cityName;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String firstSeedlingTypeId;

    public String dbh;
    public String maxDbh;
    public String minDbh;

    public String diameter;
    public String maxDiameter;
    public String minDiameter;


    public String height;
    public String maxHeight;
    public String minHeight;


    public String crown;
    public String maxCrown;
    public String minCrown;


    public String plantType;
    public String unitTypeName;
    public String plantTypeName;
    public String diameterTypeName;
    public String dbhTypeName;
    public String dbhType = "";
    public String diameterType = "";
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
    public String prePrice;
    public String status = "";
    public boolean isExclude = false;
    public String sellerName;
    public String sellerPhone;
    public String purchaseItemStatus;
    public List<String> paramsList;
    public List<SpecListBean> specList;
    public int count;

    public boolean isAlternative;
    public boolean isUsed;


    public String sendType = "";

    public String unuseReason = "";
    public String quoteImplementStatus = "";
    public String implementRemarks = "";


    public AttrData attrData = new AttrData();


    public static class AttrData extends PurchaseBean.AttrDataBean implements Serializable {
        public int quoteUsedCountJson;
        public int quotePreUsedCountJson;
    }


    public List<ImagesJsonBean> imagesJson = new ArrayList<>();
//     public ImagesJsonBean imagesJson ;


    public static class SpecListBean implements Serializable {
        /**
         * name : 胸径
         * value : 58CM
         */

        public String name;
        public String value;
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
