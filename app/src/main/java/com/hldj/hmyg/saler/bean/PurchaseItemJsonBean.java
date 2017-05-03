package com.hldj.hmyg.saler.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class PurchaseItemJsonBean implements Serializable{
    /**
     * id : e79ee15b81834a2f889476339e376323
     * remarks :
     * createBy : 1
     * createDate : 2017-03-09 16:05:28
     * prCode :
     * ciCode :
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 4bf45dae8c8e4ad5a0bc4df7cdc3d294
     * secondSeedlingTypeId : 81b1c342-4475-4dcb-8277-8014f8a05de1
     * diameter : 203
     * diameterType : size10
     * height : 356
     * crown : 543
     * plantType : heelin
     * unitType : plant
     * firstTypeName : 灌木
     * unitTypeName : 株
     * plantTypeName : 假植苗
     * diameterTypeName : 0.1M量
     * dbhTypeName :
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : diameter,height,crown
     * paramsList : ["diameter","height","crown"]
     * specList : [{"name":"地径","value":"203CM(0.1M量)"},{"name":"高度","value":"356CM"},{"name":"冠幅","value":"543CM"}]
     * specText : 地径:203(0.1M量) 高度:356 冠幅:543
     * purchaseId : 21fdc65b66d7408ca0f546518efb9081
     * name : 红花锦鸡儿
     * count : 300
     * status : quoting
     * statusName : 询价中
     * quoteCountJson : 0
     * orderBy :
     * seedlingCityCodeName : 不限制
     * source : admin
     * purchaseType : quoting
     */

    public String id;
    public String remarks;
    public String createBy;
    public String createDate;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String firstSeedlingTypeId;
    public String secondSeedlingTypeId;
    public int diameter;
    public String diameterType;
    public int height;
    public int crown;
    public String plantType;
    public String unitType;
    public String firstTypeName;
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
    public String name;
    public int count;
    public String status;
    public String statusName;
    public int quoteCountJson;
    public String orderBy;
    public String seedlingCityCodeName;
    public String source;
    public String purchaseType;
    public List<String> paramsList;
    public List<SpecListBean> specList;

    public static class SpecListBean implements Serializable{
        /**
         * name : 地径
         * value : 203CM(0.1M量)
         */

        public String name;
        public String value;
    }
}

