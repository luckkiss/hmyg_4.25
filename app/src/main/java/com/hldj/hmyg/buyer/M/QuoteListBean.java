package com.hldj.hmyg.buyer.M;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class QuoteListBean {

    /**
     * id : 2c998ab17cd041218c87b742ca8ef774
     * remarks :
     * createBy : 2659ffd89e0146188b48ab8bdc66903f
     * createDate : 2017-04-25 10:10:58
     * cityCode : 3502
     * cityName : 福建 厦门
     * prCode : 35
     * ciCode : 3502
     * coCode :
     * twCode :
     * firstSeedlingTypeId : ce14746cde764456990f717a82ffb0c0
     * diameterType : size0
     * offbarHeight : 100
     * plantType : planted
     * imageUrl : http://image.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png
     * ossUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!wt
     * unitTypeName :
     * plantTypeName : 地栽苗
     * diameterTypeName : 出土量
     * dbhTypeName :
     * thumbnailImageUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!65_87
     * smallImageUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!170_227
     * mediumImageUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!210_280
     * largeImageUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!300_400
     * imagesJson : [{"id":"7d148d63ecb44732ac91e71574bd1769","imageType":"qoute","name":"花木易购广告语竖.png","url":"http://image.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png","sourceId":"2c998ab17cd041218c87b742ca8ef774","ossUrl":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!450_600"}]
     * seedlingParams : offbarHeight,diameter,crown
     * paramsList : ["offbarHeight","diameter","crown"]
     * specList : [{"name":"脱杆高","value":"100CM"}]
     * specText : 脱杆高:100
     * purchaseId : e6aa2517bbc841b49c2eeb75bb8ffc01
     * purchaseItemId : 54101356f7114c8286cac1e69b58a138
     * sellerId : 2659ffd89e0146188b48ab8bdc66903f
     * price : 110
     * sellerName : 张
     * sellerPhone : 13394058505
     * purchaseItemStatus : quoting
     */

    public String id;
    public String remarks = "";
    public String createBy;
    public String createDate;
    public String cityCode;
    public String cityName;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String firstSeedlingTypeId;
    public String diameterType;
    public String offbarHeight;
    public String plantType;
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
    public String price;
    public String prePrice;
    public String sellerName;
    public String sellerPhone;
    public String purchaseItemStatus;
    public List<ImagesJsonBean> imagesJson = new ArrayList<>();
    public List<String> paramsList;
    public List<SpecListBeanX> specList;

    public int count = 0 ;




    public static class SpecListBeanX {
        /**
         * name : 脱杆高
         * value : 100CM
         */

        public String name;
        public String value;
    }
}
