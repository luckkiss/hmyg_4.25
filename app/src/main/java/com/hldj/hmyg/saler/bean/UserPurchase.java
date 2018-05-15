package com.hldj.hmyg.saler.bean;

import com.hldj.hmyg.CallBack.IFootMarkDelete;
import com.hldj.hmyg.buyer.M.PurchaseJsonBean;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

import java.util.List;

/**
 * 用户求购  实体对象
 */

public class UserPurchase implements MultiItemEntity, IFootMarkDelete {

    /**
     * id : 94a61b8865654112a48ce03f3c6b49eb
     * remarks : Ggggg
     * createBy : bc8ee23e-cd3d-446d-8dce-747c6d32daed
     * createDate : 2018-04-26 09:39:15
     * cityCode : 3502
     * cityName : 福建 厦门
     * prCode : 35
     * ciCode : 3502
     * coCode :
     * twCode :
     * ciCity : {"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2}
     * firstSeedlingTypeId : 74b0e9c44dd0499381a1adcadae6b4c9
     * dbhType : size120
     * unitType : plant
     * minDbh : 55
     * minHeight : 22
     * unitTypeName : 株
     * plantTypeName :
     * diameterTypeName :
     * dbhTypeName : 1.2M量
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : dbh,        			height,        			crown
     * paramsList : ["dbh","height","crown"]
     * specText : 胸径：55 高度：22
     * ownerId : bc8ee23e-cd3d-446d-8dce-747c6d32daed
     * count : 655
     * validity : day1
     * publishDate : 1524706754000
     * closeDate : 1524793154000
     * needImage : false
     * isClose : false
     * isTop : false
     * closeDateStr : 2018-04-27 09:39
     */

    public int unreadQuoteCountJson ;
    public String name;
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
    public PurchaseJsonBean.CiCityBean ciCity;
    public String firstSeedlingTypeId;
    public String secondSeedlingTypeId;
    public String dbhType;
    public String unitType;


    public String minRod; // 杆径
    public String maxRod; // 杆径
    public String minDiameter; // 地径
    public String maxDiameter; // 地径
    public String minDbh; // 米径
    public String maxDbh; // 米径
    public String minHeight; // 高度
    public String maxHeight; // 高度
    public String minCrown; // 冠幅
    public String maxCrown; // 冠幅
    public String minOffbarHeight; // 脱杆高
    public String maxOffbarHeight; // 脱杆高
    public String minLength; // 长度
    public String maxLength; // 长度


    public String unitTypeName;
    public String plantTypeName;
    public String diameterTypeName;
    public String diameterType;
    public String dbhTypeName;
    public String thumbnailImageUrl;
    public String smallImageUrl;
    public String mediumImageUrl;
    public String largeImageUrl;
    public String seedlingParams;
    public String specText;
    public String ownerId;
    public String count;
    public String validity;
    public long publishDate;
    public long closeDate;
    public boolean needImage;
    public boolean isClose;
    public boolean isTop;
    public String closeDateStr;

    public List<String> paramsList;

    public String quoteCount;
    public String quoteCountJson;
    public boolean isUserQuoted;

    public String price;

    public AttData attrData = new AttData();

    @Override
    public String getResourceId() {
        return id;
    }

    @Override
    public String getDomain() {
        return "admin/footmark/userDel";
    }

    @Override
    public FootMarkSourceType getType() {
        return FootMarkSourceType.userPurchase;
    }

    @Override
    public String getFootMarkId() {
        return attrData.footMarkId;
    }


    public static class AttData {
        public String dateStr;
        public String footMarkId;
        public boolean isUserQuoted;
        public boolean isExclude;//不采用
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
}
