package com.hldj.hmyg.M;

import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.weidet.entity.AbstractExpandableItem;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter.TYPE_LEVEL_0;

/**
 * Created by 罗擦擦   实现  多级列表   impl iexpanbale  on 2017/6/30 0030.
 */

public class ProgramPurchaseExpanBean extends AbstractExpandableItem<QuoteListJsonBean> implements Serializable, MultiItemEntity {

    /**
     * id : f52f8fd5e7e74c0fa97abd1f3cfd1228
     * remarks :
     * createBy : b9cef730fa6142eb80bbd7d646e40c66
     * createDate : 2017-06-01 08:53:20
     * prCode :
     * ciCode :
     * coCode :
     * twCode :
     * firstSeedlingTypeId : 74b0e9c44dd0499381a1adcadae6b4c9
     * secondSeedlingTypeId : 722ec58a-2394-44f4-84c4-772ed54ad9a9
     * dbh : 20
     * dbhType : size100
     * height : 850
     * crown : 400
     * plantType : container
     * unitType : plant
     * firstTypeName : 乔木
     * unitTypeName : 株
     * plantTypeName : 容器苗
     * diameterTypeName :
     * dbhTypeName : 1.0M量
     * thumbnailImageUrl :
     * smallImageUrl :
     * mediumImageUrl :
     * largeImageUrl :
     * seedlingParams : dbh,height,crown
     * paramsList : ["dbh","height","crown"]
     * specList : [{"name":"米径","value":"20CM"},{"name":"高度","value":"850CM"},{"name":"冠幅","value":"400CM"}]
     * specText : 米径:20 高度:850 冠幅:400
     * purchaseId : 60a426d34aa5443fa217ade0b6fdd242
     * name : 小叶榄仁
     * count : 2
     * status : quoting
     * sendFlag : true
     * quoteListJson : [{"id":"fe96fde19e1445c2955fb9155f6cd22c","remarks":"一株胸径19.8分左右，一株胸径23分","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-01 09:54:21","attrData":{"isSupplier":false,"servicePrice":"3520.00"},"cityCode":"3506","cityName":"福建 漳州","prCode":"35","ciCode":"3506","coCode":"","twCode":"","firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","secondSeedlingTypeId":"722ec58a-2394-44f4-84c4-772ed54ad9a9","dbh":20,"dbhType":"size100","plantType":"heelin","unitType":"plant","unitTypeName":"株","plantTypeName":"假植苗","diameterTypeName":"","dbhTypeName":"1.0M量","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","imagesJson":[{"id":"8fac814afa4748f8a3c3f5da55af6d63","imageType":"demandQuote","name":"19.8.jpg","url":"http://image.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg","sourceId":"fe96fde19e1445c2955fb9155f6cd22c","ossUrl":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/4ecb0104d1bc49c38ca2ba3243f15a92.jpg@!450_600"},{"id":"f7422846d3f942d8980d8cd88004639f","imageType":"demandQuote","name":"20.jpg","url":"http://image.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg","sourceId":"fe96fde19e1445c2955fb9155f6cd22c","ossUrl":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9abdedf0208548609749c17a6ae31f72.jpg@!450_600"}],"seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specList":[{"name":"米径","value":"20CM"}],"specText":"米径:20 ","purchaseId":"60a426d34aa5443fa217ade0b6fdd242","purchaseItemId":"f52f8fd5e7e74c0fa97abd1f3cfd1228","sellerId":"7a920b48-502e-4262-8e0a-81ed9d377861","price":"3200","isInvoice":true,"isUsed":true,"sendType":"hmeg","implementRemarks":"一株胸径19.8     一株胸径23","quoteImplementStatus":"qualified","statusName":"已中标","sellerName":"黄志强","sellerPhone":"15159672278","status":"used","purchaseItemStatus":"quoting"},{"id":"ef305ec6f2224dfd874d8fadae267769","remarks":"","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-02 10:44:21","attrData":{"isSupplier":true,"servicePrice":"4197.60"},"cityCode":"3506","cityName":"福建 漳州","prCode":"35","ciCode":"3506","coCode":"","twCode":"","firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","secondSeedlingTypeId":"722ec58a-2394-44f4-84c4-772ed54ad9a9","plantType":"container","unitType":"plant","unitTypeName":"株","plantTypeName":"容器苗","diameterTypeName":"","dbhTypeName":"","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","imagesJson":[{"id":"f3ac7263878e4f808a9efd33f2a936d9","imageType":"demandQuote","name":"小叶榄仁-1.jpg","url":"http://image.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg","sourceId":"ef305ec6f2224dfd874d8fadae267769","ossUrl":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/06c82354401444f1a6d04c4bc2ac5cb3.jpg@!450_600"}],"seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specText":"","purchaseId":"60a426d34aa5443fa217ade0b6fdd242","purchaseItemId":"f52f8fd5e7e74c0fa97abd1f3cfd1228","sellerId":"3a93ae86be844333a68079c700623433","price":"3816","isInvoice":true,"isUsed":false,"sellerName":"刘振城","sellerPhone":"15059088398","purchaseItemStatus":"quoting"},{"id":"3135b27d06d24aedaad19aa6ccfecc1e","remarks":"","createBy":"b9cef730fa6142eb80bbd7d646e40c66","createDate":"2017-06-01 09:43:46","attrData":{"isSupplier":false,"servicePrice":"4510.00"},"cityCode":"4420","cityName":"广东 中山","prCode":"44","ciCode":"4420","coCode":"","twCode":"","firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","secondSeedlingTypeId":"722ec58a-2394-44f4-84c4-772ed54ad9a9","dbh":20,"dbhType":"size100","height":900,"crown":350,"plantType":"heelin","unitType":"plant","unitTypeName":"株","plantTypeName":"假植苗","diameterTypeName":"","dbhTypeName":"1.0M量","thumbnailImageUrl":"","smallImageUrl":"","mediumImageUrl":"","largeImageUrl":"","imagesJson":[{"id":"44beeddf7b4b4da4b8855dae34b9ea05","imageType":"demandQuote","name":"12.JPG","url":"http://image.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG","sourceId":"3135b27d06d24aedaad19aa6ccfecc1e","ossUrl":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/b2182eb8735d4c6eb62e8e924f3d88c0.JPG@!450_600"},{"id":"cf0b6cb3ff59476a8a07f4e036b98721","imageType":"demandQuote","name":"12-1.JPG","url":"http://image.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG","sourceId":"3135b27d06d24aedaad19aa6ccfecc1e","ossUrl":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/9148bfdf99d44f3b9ff9906de651626f.JPG@!450_600"}],"seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specList":[{"name":"米径","value":"20CM"},{"name":"高度","value":"900CM"},{"name":"冠幅","value":"350CM"}],"specText":"米径:20 高度:900 冠幅:350 ","purchaseId":"60a426d34aa5443fa217ade0b6fdd242","purchaseItemId":"f52f8fd5e7e74c0fa97abd1f3cfd1228","sellerId":"bc07176b-ccb4-452e-b5ec-53594255344a","price":"4100","isInvoice":true,"sellerName":"姚良伟","sellerPhone":"15980866282","purchaseItemStatus":"quoting"},{"id":"9e37290d50634caab5e92d7636307ded","remarks":"","createBy":"563509f0-b19f-4257-ae01-aa416b54e550","createDate":"2017-06-01 10:44:54","attrData":{"isSupplier":false,"servicePrice":"5687.00"},"cityCode":"4420","cityName":"广东 中山","prCode":"44","ciCode":"4420","coCode":"","twCode":"","firstSeedlingTypeId":"74b0e9c44dd0499381a1adcadae6b4c9","secondSeedlingTypeId":"722ec58a-2394-44f4-84c4-772ed54ad9a9","dbh":20,"dbhType":"size100","height":900,"crown":450,"plantType":"heelin","unitType":"plant","imageUrl":"http://image.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg","ossUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!wt","unitTypeName":"株","plantTypeName":"假植苗","diameterTypeName":"","dbhTypeName":"1.0M量","thumbnailImageUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!65_87","smallImageUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!170_227","mediumImageUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!210_280","largeImageUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!300_400","imagesJson":[{"id":"1a029f6f818c44da97d81c30379bbdd7","imageType":"qoute","name":"image.jpeg","url":"http://image.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg","sourceId":"9e37290d50634caab5e92d7636307ded","ossUrl":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201706/db44669c313149d4b77a27a280677491.jpeg@!450_600"}],"seedlingParams":"dbh,height,crown","paramsList":["dbh","height","crown"],"specList":[{"name":"米径","value":"20CM"},{"name":"高度","value":"900CM"},{"name":"冠幅","value":"450CM"}],"specText":"米径:20 高度:900 冠幅:450 ","purchaseId":"60a426d34aa5443fa217ade0b6fdd242","purchaseItemId":"f52f8fd5e7e74c0fa97abd1f3cfd1228","sellerId":"563509f0-b19f-4257-ae01-aa416b54e550","price":"5170","isInvoice":false,"sellerName":"陈志聪","sellerPhone":"13703048185","purchaseItemStatus":"quoting"}]
     * statusName : 询价中
     * quoteCountJson : 4
     * quoteUsedCountJson : 1
     * orderBy :
     * seedlingCityCodeName : 不限制
     * source : admin
     * purchaseType : quoting
     * diameter : 5
     * diameterType : size10
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
    public int dbh;
    public String dbhType;
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
    public boolean sendFlag;
    public String statusName;
    public int quoteCountJson;
    public int quoteUsedCountJson;
    public String orderBy;
    public String seedlingCityCodeName;
    public String source;
    public String purchaseType;
    public int diameter;
    public String diameterType;
    public List<String> paramsList;
    public List<ItemBean.SpecListBean> specList;
    public List<QuoteListJsonBean> quoteListJson = new ArrayList<>();

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public List<QuoteListJsonBean> getSubItems() {
        return quoteListJson;
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_0;
    }
//
//    @Override
//    public int getLevel() {
//        return 1;
//    }


    public int index = 1;





}
