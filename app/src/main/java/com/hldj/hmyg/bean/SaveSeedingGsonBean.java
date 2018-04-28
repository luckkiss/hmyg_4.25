package com.hldj.hmyg.bean;

import com.hldj.hmyg.M.QuoteListJsonBean;
import com.hldj.hmyg.buyer.M.ItemBean;
import com.hldj.hmyg.buyer.M.PurchaseJsonBean;
import com.hldj.hmyg.saler.bean.PurchaseItemJsonBean;
import com.hldj.hmyg.saler.bean.UsedQuoteListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class SaveSeedingGsonBean implements Serializable {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"typeList":[{"id":"74b0e9c44dd0499381a1adcadae6b4c9","createBy":"1","createDate":"2016-02-29 15:06:37","name":"乔木","aliasName":"","parentId":"","level":0,"firstPinyin":"qm","fullPinyin":"qiaomu","seedlingParams":"dbh,height,crown","sort":1,"defaultUnit":"plant","defaultValidity":180,"paramsList":[{"value":"dbh","required":true},{"value":"height","required":false},{"value":"crown","required":false}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}],"qualityTypeList":[{"text":"全冠苗","value":"quanguan"},{"text":"大骨架","value":"dagujia"},{"text":"小骨架","value":"xiaogujia"},{"text":"截杆苗","value":"jiegan"}],"qualityGradeList":[{"text":"A货","value":"A"},{"text":"B货","value":"B"}],"mainSpec":"dbh"},{"id":"4bf45dae8c8e4ad5a0bc4df7cdc3d294","createBy":"1","createDate":"2016-02-29 15:06:37","name":"灌木","aliasName":"","parentId":"","level":0,"firstPinyin":"gm","fullPinyin":"guanmu","seedlingParams":"diameter,height,crown","sort":2,"defaultUnit":"plant","defaultValidity":90,"paramsList":[{"value":"diameter","required":true},{"value":"height","required":false},{"value":"crown","required":false}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}],"qualityGradeList":[{"text":"A货","value":"A"},{"text":"B货","value":"B"}],"mainSpec":"diameter"},{"id":"dcdce5c0eea74f3e8357aa58ae74651b","createBy":"1","createDate":"2016-02-29 15:06:37","name":"桩景","aliasName":"","parentId":"","level":0,"firstPinyin":"zj","fullPinyin":"zhuangjing","seedlingParams":"diameter,height,crown","sort":3,"defaultUnit":"plant","defaultValidity":180,"paramsList":[{"value":"diameter","required":true},{"value":"height","required":false},{"value":"crown","required":false}],"plantTypeList":[{"text":"移植苗","value":"transplant"},{"text":"容器苗","value":"container"},{"text":"假植苗","value":"heelin"}],"qualityGradeList":[{"text":"A货","value":"A"},{"text":"B货","value":"B"}],"mainSpec":"diameter"},{"id":"4d80082e024d4e778e131c2d47c37400","createBy":"1","createDate":"2016-02-29 15:06:37","name":"地被","aliasName":"","level":0,"firstPinyin":"db","fullPinyin":"dibei","seedlingParams":"height,crown","sort":4,"defaultValidity":30,"paramsList":[{"value":"height","required":false},{"value":"crown","required":true}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"容器苗","value":"container"}],"mainSpec":"crown"},{"id":"ce14746cde764456990f717a82ffb0c0","createBy":"1","createDate":"2016-02-29 15:06:37","name":"棕榈/苏铁","aliasName":"","parentId":"","level":0,"firstPinyin":"zl","fullPinyin":"zonglv","seedlingParams":"diameter,crown,offbarHeight","sort":6,"defaultUnit":"plant","defaultValidity":180,"paramsList":[{"value":"diameter","required":false},{"value":"crown","required":false},{"value":"offbarHeight","required":true}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}],"qualityGradeList":[{"text":"A货","value":"A"},{"text":"B货","value":"B"}],"mainSpec":"offbarHeight"}],"plantTypeList":[{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}]}
     */

    private String code;
    private String msg;
    private DataBean data = new DataBean();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        public ArrayList<SeedlingBean.ImagesJsonBean> seedlingImage = new ArrayList<>();
        public boolean canQuote = false;
        public boolean isOneQuote = false;
        private SeedlingBean seedling = new SeedlingBean();
        //      private SeedlingBean seedlingNote = new SeedlingBean();
        private ItemBean item;
        public List<UsedQuoteListBean> usedQuoteList = new ArrayList<>();
        private List<TypeListBean> typeList;
        private List<TypeListBean.PlantTypeListBean> plantTypeList;

        public List<UnitTypeBean> unitTypeList = new ArrayList<>();

        public List<SpecTypeBean> dbhTypeList = new ArrayList<>();
        public List<SpecTypeBean> diameterTypeList = new ArrayList<>();


        public SeedlingBean.NurseryJsonBean nursery = new SeedlingBean.NurseryJsonBean();


        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public List<TypeListBean.PlantTypeListBean> getPlantTypeList() {
            return plantTypeList;
        }

        public void setPlantTypeList(List<TypeListBean.PlantTypeListBean> plantTypeList) {
            this.plantTypeList = plantTypeList;
        }

        public SeedlingBean getSeedling() {
            return seedling;
        }

        public void setSeedling(SeedlingBean seedling) {
            this.seedling = seedling;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }


        public static class SeedlingBean implements Serializable {


            private String id;
            private String createBy;
            private String createDate;
            private String cityCode;
            private String cityName;
            private String prCode;
            private String ciCode;
            private String coCode;
            private String twCode;
            private PrCityBean prCity;
            private CiCityBean ciCity;
            private CoCityBean coCity;
            private TwCityBean twCity;
            private String remarks;
            private String firstSeedlingTypeId;
            private String diameterType;
            private String plantType;
            private String unitType;
            private String imageUrl;
            private int minDiameter;
            private int maxDiameter;
            private int minDbh;
            private int maxDbh;
            private int minOffbarHeight;
            private int maxOffbarHeight;

            public String minSpec = "";
            public String maxSpec = "";


            private int minLength;
            private int maxLength;
            private int minHeight;
            private int maxHeight;
            private int minCrown;
            private int maxCrown;
            private String ossUrl;
            private String firstTypeName;
            private String unitTypeName;
            private String plantTypeName;
            private String diameterTypeName;
            private String dbhTypeName;
            private String dbhType;
            private String thumbnailImageUrl;
            private String smallImageUrl;
            private String mediumImageUrl;
            private String largeImageUrl;
            private String seedlingParams;
            private String specText;
            private String name;
            private String seedlingNum;
            private boolean isNego = false;
            private String minPrice = "";
            private String maxPrice = "";

            public List<QuoteListJsonBean> quoteListJson = new ArrayList<>();

            public int getQuoteUsedCountJson() {
                return quoteUsedCountJson;
            }

            public void setQuoteUsedCountJson(int quoteUsedCountJson) {
                this.quoteUsedCountJson = quoteUsedCountJson;
            }

            private int quoteUsedCountJson;

            public int getQuoteCountJson() {
                return quoteCountJson;
            }

            public void setQuoteCountJson(int quoteCountJson) {
                this.quoteCountJson = quoteCountJson;
            }

            private int quoteCountJson;


            public boolean isQuoted;
            public String price = "";
            public String prePrice = "";


            private String validity;
            private String nurseryId;
            private int count;
            private int saleCount;
            private String status = "";
            private boolean isAudit;
            private String closeDate;
            private String ownerId;
            private String createUserType;
            private String updateUserType;
            private String sourceType;
            private int visitsCount;
            private boolean isSelfSupport;
            private NurseryJsonBean nurseryJson;

            private OwnerJsonBean ownerJson;
            private long lastTime;
            private int lastDay;
            private int stock;
            private String orderBy;
            private String statusName;
            private String standardName;
            private String distanceStr;
            private String seedlingLabel;
            private boolean isPartners;
            private boolean cashOnDelivery;
            private boolean isServiceCovered;

            private boolean isDefault;
            private String priceStr = "";

            private String diameterStr;
            private String dbhStr;
            private String heightStr;
            private String crownStr;
            private String offbarHeightStr;
            private String lengthStr;
            private List<ImagesJsonBean> imagesJson = new ArrayList<>();
            private List<String> paramsList;
            private List<SpecListBean> specList;
            private List<TagListBean> tagList;

            public AttrDataBean attrData = new AttrDataBean();


            public PurchaseJsonBean purchaseJson = new PurchaseJsonBean();
            //            public PurchaseItemJsonBean purchaseItemJsonBean = new PurchaseItemJsonBean();
            public PurchaseItemJsonBean purchaseItemJson = new PurchaseItemJsonBean();

//            public PurchaseJsonBean purchaseJsonBean =new PurchaseJsonBean();

            public int getMinOffbarHeight() {
                return minOffbarHeight;
            }

            public void setMinOffbarHeight(int minOffbarHeight) {
                this.minOffbarHeight = minOffbarHeight;
            }

            public int getMaxOffbarHeight() {
                return maxOffbarHeight;
            }

            public void setMaxOffbarHeight(int maxOffbarHeight) {
                this.maxOffbarHeight = maxOffbarHeight;
            }

            public int getMinLength() {
                return minLength;
            }

            public void setMinLength(int minLength) {
                this.minLength = minLength;
            }

            public int getMaxLength() {
                return maxLength;
            }

            public void setMaxLength(int maxLength) {
                this.maxLength = maxLength;
            }

            public int getMinDiameter() {
                return minDiameter;
            }

            public void setMinDiameter(int minDiameter) {
                this.minDiameter = minDiameter;
            }

            public int getMinHeight() {
                return minHeight;
            }

            public void setMinHeight(int minHeight) {
                this.minHeight = minHeight;
            }

            public int getMaxHeight() {
                return maxHeight;
            }

            public void setMaxHeight(int maxHeight) {
                this.maxHeight = maxHeight;
            }

            public int getMinCrown() {
                return minCrown;
            }

            public void setMinCrown(int minCrown) {
                this.minCrown = minCrown;
            }

            public int getMaxCrown() {
                return maxCrown;
            }

            public void setMaxCrown(int maxCrown) {
                this.maxCrown = maxCrown;
            }

            public boolean isNego() {
                return isNego;
            }

            public void setNego(boolean nego) {
                isNego = nego;
            }

            public String getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(String minPrice) {
                this.minPrice = minPrice;
            }

            public String getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(String maxPrice) {
                this.maxPrice = maxPrice;
            }

            public boolean isAudit() {
                return isAudit;
            }

            public void setAudit(boolean audit) {
                isAudit = audit;
            }

            public boolean isSelfSupport() {
                return isSelfSupport;
            }

            public void setSelfSupport(boolean selfSupport) {
                isSelfSupport = selfSupport;
            }

            public boolean isPartners() {
                return isPartners;
            }

            public void setPartners(boolean partners) {
                isPartners = partners;
            }

            public boolean isServiceCovered() {
                return isServiceCovered;
            }

            public void setServiceCovered(boolean serviceCovered) {
                isServiceCovered = serviceCovered;
            }


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreateBy() {
                return createBy;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getPrCode() {
                return prCode;
            }

            public void setPrCode(String prCode) {
                this.prCode = prCode;
            }

            public String getCiCode() {
                return ciCode;
            }

            public void setCiCode(String ciCode) {
                this.ciCode = ciCode;
            }

            public String getCoCode() {
                return coCode;
            }

            public void setCoCode(String coCode) {
                this.coCode = coCode;
            }

            public String getTwCode() {
                return twCode;
            }

            public void setTwCode(String twCode) {
                this.twCode = twCode;
            }

            public PrCityBean getPrCity() {
                return prCity;
            }

            public void setPrCity(PrCityBean prCity) {
                this.prCity = prCity;
            }

            public CiCityBean getCiCity() {
                return ciCity;
            }

            public void setCiCity(CiCityBean ciCity) {
                this.ciCity = ciCity;
            }

            public CoCityBean getCoCity() {
                return coCity;
            }

            public void setCoCity(CoCityBean coCity) {
                this.coCity = coCity;
            }

            public TwCityBean getTwCity() {
                return twCity;
            }

            public void setTwCity(TwCityBean twCity) {
                this.twCity = twCity;
            }

            public String getFirstSeedlingTypeId() {
                return firstSeedlingTypeId;
            }

            public void setFirstSeedlingTypeId(String firstSeedlingTypeId) {
                this.firstSeedlingTypeId = firstSeedlingTypeId;
            }

            public String getDiameterType() {
                return diameterType;
            }

            public void setDiameterType(String diameterType) {
                this.diameterType = diameterType;
            }

            public String getPlantType() {
                return plantType;
            }

            public void setPlantType(String plantType) {
                this.plantType = plantType;
            }

            public String getUnitType() {
                return unitType;
            }

            public void setUnitType(String unitType) {
                this.unitType = unitType;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getMaxDiameter() {
                return maxDiameter;
            }

            public void setMaxDiameter(int maxDiameter) {
                this.maxDiameter = maxDiameter;
            }

            public String getOssUrl() {
                return ossUrl;
            }

            public void setOssUrl(String ossUrl) {
                this.ossUrl = ossUrl;
            }

            public String getFirstTypeName() {
                return firstTypeName;
            }

            public void setFirstTypeName(String firstTypeName) {
                this.firstTypeName = firstTypeName;
            }

            public String getUnitTypeName() {
                return unitTypeName;
            }

            public void setUnitTypeName(String unitTypeName) {
                this.unitTypeName = unitTypeName;
            }

            public String getPlantTypeName() {
                return plantTypeName;
            }

            public void setPlantTypeName(String plantTypeName) {
                this.plantTypeName = plantTypeName;
            }

            public String getDiameterTypeName() {
                return diameterTypeName;
            }

            public void setDiameterTypeName(String diameterTypeName) {
                this.diameterTypeName = diameterTypeName;
            }

            public String getDbhTypeName() {
                return dbhTypeName;
            }

            public void setDbhTypeName(String dbhTypeName) {
                this.dbhTypeName = dbhTypeName;
            }

            public String getThumbnailImageUrl() {
                return thumbnailImageUrl;
            }

            public void setThumbnailImageUrl(String thumbnailImageUrl) {
                this.thumbnailImageUrl = thumbnailImageUrl;
            }

            public String getSmallImageUrl() {
                return smallImageUrl;
            }

            public void setSmallImageUrl(String smallImageUrl) {
                this.smallImageUrl = smallImageUrl;
            }

            public String getMediumImageUrl() {
                return mediumImageUrl;
            }

            public void setMediumImageUrl(String mediumImageUrl) {
                this.mediumImageUrl = mediumImageUrl;
            }

            public String getLargeImageUrl() {
                return largeImageUrl;
            }

            public void setLargeImageUrl(String largeImageUrl) {
                this.largeImageUrl = largeImageUrl;
            }

            public String getSeedlingParams() {
                return seedlingParams;
            }

            public void setSeedlingParams(String seedlingParams) {
                this.seedlingParams = seedlingParams;
            }

            public String getSpecText() {
                return specText;
            }

            public void setSpecText(String specText) {
                this.specText = specText;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSeedlingNum() {
                return seedlingNum;
            }

            public void setSeedlingNum(String seedlingNum) {
                this.seedlingNum = seedlingNum;
            }

            public boolean isIsNego() {
                return isNego;
            }

            public void setIsNego(boolean isNego) {
                this.isNego = isNego;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getNurseryId() {
                return nurseryId;
            }

            public void setNurseryId(String nurseryId) {
                this.nurseryId = nurseryId;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getSaleCount() {
                return saleCount;
            }

            public void setSaleCount(int saleCount) {
                this.saleCount = saleCount;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public boolean isIsAudit() {
                return isAudit;
            }

            public void setIsAudit(boolean isAudit) {
                this.isAudit = isAudit;
            }

            public String getCloseDate() {
                return closeDate;
            }

            public void setCloseDate(String closeDate) {
                this.closeDate = closeDate;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }

            public String getCreateUserType() {
                return createUserType;
            }

            public void setCreateUserType(String createUserType) {
                this.createUserType = createUserType;
            }

            public String getUpdateUserType() {
                return updateUserType;
            }

            public void setUpdateUserType(String updateUserType) {
                this.updateUserType = updateUserType;
            }

            public String getSourceType() {
                return sourceType;
            }

            public void setSourceType(String sourceType) {
                this.sourceType = sourceType;
            }

            public int getVisitsCount() {
                return visitsCount;
            }

            public void setVisitsCount(int visitsCount) {
                this.visitsCount = visitsCount;
            }

            public boolean isIsSelfSupport() {
                return isSelfSupport;
            }

            public void setIsSelfSupport(boolean isSelfSupport) {
                this.isSelfSupport = isSelfSupport;
            }

            public NurseryJsonBean getNurseryJson() {
                return nurseryJson;
            }

            public void setNurseryJson(NurseryJsonBean nurseryJson) {
                this.nurseryJson = nurseryJson;
            }

            public OwnerJsonBean getOwnerJson() {
                return ownerJson;
            }

            public void setOwnerJson(OwnerJsonBean ownerJson) {
                this.ownerJson = ownerJson;
            }

            public long getLastTime() {
                return lastTime;
            }

            public void setLastTime(long lastTime) {
                this.lastTime = lastTime;
            }

            public int getLastDay() {
                return lastDay;
            }

            public void setLastDay(int lastDay) {
                this.lastDay = lastDay;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public String getStandardName() {
                return standardName;
            }

            public void setStandardName(String standardName) {
                this.standardName = standardName;
            }

            public String getDistanceStr() {
                return distanceStr;
            }

            public void setDistanceStr(String distanceStr) {
                this.distanceStr = distanceStr;
            }

            public String getSeedlingLabel() {
                return seedlingLabel;
            }

            public void setSeedlingLabel(String seedlingLabel) {
                this.seedlingLabel = seedlingLabel;
            }

            public boolean isIsPartners() {
                return isPartners;
            }

            public void setIsPartners(boolean isPartners) {
                this.isPartners = isPartners;
            }

            public boolean isCashOnDelivery() {
                return cashOnDelivery;
            }

            public void setCashOnDelivery(boolean cashOnDelivery) {
                this.cashOnDelivery = cashOnDelivery;
            }

            public boolean isIsServiceCovered() {
                return isServiceCovered;
            }

            public void setIsServiceCovered(boolean isServiceCovered) {
                this.isServiceCovered = isServiceCovered;
            }

            public String getPriceStr() {
                return priceStr;
            }

            public void setPriceStr(String priceStr) {
                this.priceStr = priceStr;
            }

            public String getDiameterStr() {
                return diameterStr;
            }

            public void setDiameterStr(String diameterStr) {
                this.diameterStr = diameterStr;
            }

            public String getDbhStr() {
                return dbhStr;
            }

            public void setDbhStr(String dbhStr) {
                this.dbhStr = dbhStr;
            }

            public String getHeightStr() {
                return heightStr;
            }

            public void setHeightStr(String heightStr) {
                this.heightStr = heightStr;
            }

            public String getCrownStr() {
                return crownStr;
            }

            public void setCrownStr(String crownStr) {
                this.crownStr = crownStr;
            }

            public String getOffbarHeightStr() {
                return offbarHeightStr;
            }

            public void setOffbarHeightStr(String offbarHeightStr) {
                this.offbarHeightStr = offbarHeightStr;
            }

            public String getLengthStr() {
                return lengthStr;
            }

            public void setLengthStr(String lengthStr) {
                this.lengthStr = lengthStr;
            }

            public List<ImagesJsonBean> getImagesJson() {
                return imagesJson;
            }

            public void setImagesJson(List<ImagesJsonBean> imagesJson) {
                this.imagesJson = imagesJson;
            }

            public List<String> getParamsList() {
                return paramsList;
            }

            public void setParamsList(List<String> paramsList) {
                this.paramsList = paramsList;
            }

            public List<SpecListBean> getSpecList() {
                return specList;
            }

            public void setSpecList(List<SpecListBean> specList) {
                this.specList = specList;
            }

            public List<TagListBean> getTagList() {
                return tagList;
            }

            public void setTagList(List<TagListBean> tagList) {
                this.tagList = tagList;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public boolean isDefault() {
                return isDefault;
            }

            public void setDefault(boolean aDefault) {
                isDefault = aDefault;
            }

            public int getMinDbh() {
                return minDbh;
            }

            public void setMinDbh(int minDbh) {
                this.minDbh = minDbh;
            }

            public int getMaxDbh() {
                return maxDbh;
            }

            public void setMaxDbh(int maxDbh) {
                this.maxDbh = maxDbh;
            }

            public String getDbhType() {
                return dbhType;
            }

            public void setDbhType(String dbhType) {
                this.dbhType = dbhType;
            }


            public static class PrCityBean implements Serializable {
                /**
                 * id : 16654
                 * name : 福建
                 * fullName : 福建省
                 * cityCode : 35
                 * parentCode : 0
                 * level : 1
                 */

                private String id;
                private String name;
                private String fullName;
                private String cityCode;
                private String parentCode;
                private int level;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getParentCode() {
                    return parentCode;
                }

                public void setParentCode(String parentCode) {
                    this.parentCode = parentCode;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }
            }

            public static class CiCityBean implements Serializable {
                /**
                 * id : 16850
                 * name : 厦门
                 * fullName : 福建 厦门
                 * cityCode : 3502
                 * parentCode : 35
                 * level : 2
                 */

                private String id;
                private String name;
                private String fullName;
                private String cityCode;
                private String parentCode;
                private int level;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getParentCode() {
                    return parentCode;
                }

                public void setParentCode(String parentCode) {
                    this.parentCode = parentCode;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }
            }

            public static class CoCityBean implements Serializable {
                /**
                 * id : 16851
                 * name : 思明
                 * fullName : 福建省厦门市思明区
                 * cityCode : 350203
                 * parentCode : 3502
                 * level : 3
                 */

                private String id;
                private String name;
                private String fullName;
                private String cityCode;
                private String parentCode;
                private int level;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getParentCode() {
                    return parentCode;
                }

                public void setParentCode(String parentCode) {
                    this.parentCode = parentCode;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }
            }

            public static class TwCityBean implements Serializable {
                /**
                 * id : 16857
                 * name : 梧村街道
                 * fullName : 福建省厦门市思明区梧村街道
                 * cityCode : 350203008
                 * parentCode : 350203
                 * level : 4
                 */

                private String id;
                private String name;
                private String fullName;
                private String cityCode;
                private String parentCode;
                private int level;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getParentCode() {
                    return parentCode;
                }

                public void setParentCode(String parentCode) {
                    this.parentCode = parentCode;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }
            }

            public static class NurseryJsonBean implements Serializable {
                /**
                 * id : a1a84128b7f04f28abc19dab0ad14597
                 * createBy : 3378ca2d-b09b-411b-a3d0-28766e314685
                 * createDate : 2016-07-18 12:04:41
                 * cityCode : 350203008
                 * cityName : 福建省厦门市思明区梧村街道
                 * prCode : 35
                 * ciCode : 3502
                 * coCode : 350203
                 * twCode : 350203008
                 * prCity : {"id":"16654","name":"福建","fullName":"福建省","cityCode":"35","parentCode":"0","level":1}
                 * ciCity : {"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2}
                 * coCity : {"id":"16851","name":"思明","fullName":"福建省厦门市思明区","cityCode":"350203","parentCode":"3502","level":3}
                 * twCity : {"id":"16857","name":"梧村街道","fullName":"福建省厦门市思明区梧村街道","cityCode":"350203008","parentCode":"350203","level":4}
                 * name : lw的苗圃
                 * detailAddress : 无
                 * nurseryArea : 30
                 * type : self
                 * typeName : 自有
                 * longitude : 118.11701
                 * latitude : 24.471788
                 * companyName : 花木易购
                 * publicName : 花木易购客服
                 * realName : 罗伟
                 * phone : 18750215634
                 * fullAddress : 福建省厦门市思明区梧村街道无
                 */

                public String id = "";
                private String createBy;
                private String createDate;
                private String cityCode;
                private String cityName = "-";
                private String prCode;
                private String ciCode;
                private String coCode;
                private String twCode;
                private PrCityBeanX prCity;
                private CiCityBeanX ciCity;
                private CoCityBeanX coCity;
                private TwCityBeanX twCity;
                private String detailAddress = "-";
                private int nurseryArea;
                private String type;
                private String typeName;
                private double longitude;
                private double latitude;
                private String companyName = "";
                private String publicName = "";
                private String realName = "";
                private String phone = "";

                public String name = ""; // 苗圃名称
                public String fullAddress = "";//详细地址
                public String contactPhone = "";//联系电话
                public String contactName = "";//联系人姓名
                public Boolean isDefault = false;//是否默认地址


                @Override
                public String toString() {
                    return "NurseryJsonBean{" +
                            "id='" + id + '\'' +
                            ", createBy='" + createBy + '\'' +
                            ", createDate='" + createDate + '\'' +
                            ", cityCode='" + cityCode + '\'' +
                            ", cityName='" + cityName + '\'' +
                            ", prCode='" + prCode + '\'' +
                            ", ciCode='" + ciCode + '\'' +
                            ", coCode='" + coCode + '\'' +
                            ", twCode='" + twCode + '\'' +
                            ", prCity=" + prCity +
                            ", ciCity=" + ciCity +
                            ", coCity=" + coCity +
                            ", twCity=" + twCity +
                            ", name='" + name + '\'' +
                            ", detailAddress='" + detailAddress + '\'' +
                            ", nurseryArea=" + nurseryArea +
                            ", type='" + type + '\'' +
                            ", typeName='" + typeName + '\'' +
                            ", longitude=" + longitude +
                            ", latitude=" + latitude +
                            ", companyName='" + companyName + '\'' +
                            ", publicName='" + publicName + '\'' +
                            ", realName='" + realName + '\'' +
                            ", phone='" + phone + '\'' +
                            ", fullAddress='" + fullAddress + '\'' +
                            '}';
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(String createBy) {
                    this.createBy = createBy;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public String getPrCode() {
                    return prCode;
                }

                public void setPrCode(String prCode) {
                    this.prCode = prCode;
                }

                public String getCiCode() {
                    return ciCode;
                }

                public void setCiCode(String ciCode) {
                    this.ciCode = ciCode;
                }

                public String getCoCode() {
                    return coCode;
                }

                public void setCoCode(String coCode) {
                    this.coCode = coCode;
                }

                public String getTwCode() {
                    return twCode;
                }

                public void setTwCode(String twCode) {
                    this.twCode = twCode;
                }

                public PrCityBeanX getPrCity() {
                    return prCity;
                }

                public void setPrCity(PrCityBeanX prCity) {
                    this.prCity = prCity;
                }

                public CiCityBeanX getCiCity() {
                    return ciCity;
                }

                public void setCiCity(CiCityBeanX ciCity) {
                    this.ciCity = ciCity;
                }

                public CoCityBeanX getCoCity() {
                    return coCity;
                }

                public void setCoCity(CoCityBeanX coCity) {
                    this.coCity = coCity;
                }

                public TwCityBeanX getTwCity() {
                    return twCity;
                }

                public void setTwCity(TwCityBeanX twCity) {
                    this.twCity = twCity;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDetailAddress() {
                    return detailAddress;
                }

                public void setDetailAddress(String detailAddress) {
                    this.detailAddress = detailAddress;
                }

                public int getNurseryArea() {
                    return nurseryArea;
                }

                public void setNurseryArea(int nurseryArea) {
                    this.nurseryArea = nurseryArea;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public String getCompanyName() {
                    return companyName;
                }

                public void setCompanyName(String companyName) {
                    this.companyName = companyName;
                }

                public String getPublicName() {
                    return publicName;
                }

                public void setPublicName(String publicName) {
                    this.publicName = publicName;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getFullAddress() {
                    return fullAddress;
                }

                public void setFullAddress(String fullAddress) {
                    this.fullAddress = fullAddress;
                }

                public static class PrCityBeanX implements Serializable {
                    /**
                     * id : 16654
                     * name : 福建
                     * fullName : 福建省
                     * cityCode : 35
                     * parentCode : 0
                     * level : 1
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class CiCityBeanX implements Serializable {
                    /**
                     * id : 16850
                     * name : 厦门
                     * fullName : 福建 厦门
                     * cityCode : 3502
                     * parentCode : 35
                     * level : 2
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class CoCityBeanX implements Serializable {
                    /**
                     * id : 16851
                     * name : 思明
                     * fullName : 福建省厦门市思明区
                     * cityCode : 350203
                     * parentCode : 3502
                     * level : 3
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class TwCityBeanX implements Serializable {
                    /**
                     * id : 16857
                     * name : 梧村街道
                     * fullName : 福建省厦门市思明区梧村街道
                     * cityCode : 350203008
                     * parentCode : 350203
                     * level : 4
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }
            }

            public static class OwnerJsonBean implements Serializable {
                /**
                 * id : 3378ca2d-b09b-411b-a3d0-28766e314685
                 * remarks : 罗伟2
                 * createBy : 99
                 * createDate : 2016-07-14 15:59:27
                 * cityCode : 350203008
                 * cityName :
                 * prCode : 35
                 * ciCode : 3502
                 * coCode : 350203
                 * twCode : 350203008
                 * prCity : {"id":"16654","name":"福建","fullName":"福建省","cityCode":"35","parentCode":"0","level":1}
                 * ciCity : {"id":"16850","name":"厦门","fullName":"福建 厦门","cityCode":"3502","parentCode":"35","level":2}
                 * coCity : {"id":"16851","name":"思明","fullName":"福建省厦门市思明区","cityCode":"350203","parentCode":"3502","level":3}
                 * twCity : {"id":"16857","name":"梧村街道","fullName":"福建省厦门市思明区梧村街道","cityCode":"350203008","parentCode":"350203","level":4}
                 * userName : lw123456
                 * realName : 罗伟
                 * publicName : 花木易购客服
                 * publicPhone : 4006-579-888
                 * plainPassword : lw2137887
                 * sex : 1
                 * position :
                 * tel :
                 * phone : 18750215634
                 * email :
                 * address :
                 * variety :
                 * companyName : 花木易购
                 * status : enabled
                 * isAgent : true
                 * isClerk : false
                 * agentType : direct
                 * isInvoices : false
                 * permissions : PUBLISH_PURCHASE;ADD_TO_CART;PUBLISH_SEEDLINGS;PRICE_APPLY
                 * storeId : d55e06b24d854d7587a3461230865e91
                 * headImage : http://image.hmeg.cn/upload/image/201608/7ac4a2eb4db344a681fb7f2696eadd3c.jpeg
                 * isPartners : false
                 * cashOnDelivery : false
                 * receiptMsg : false
                 * isQuote : true
                 * showQuote : true
                 * showQuoteCount : true
                 * supplierIsAgree : false
                 * tradeType : bangwo
                 * isActivated : true
                 * agentTypeName : 直属经纪
                 * isDirectAgent : true
                 * displayName : 花木易购
                 * adminDisplayName : 罗伟
                 * displayPhone : 4006-579-888
                 * permissionsName :
                 */

                private String id;
                private String remarks;
                private String createBy;
                private String createDate;
                private String cityCode;
                private String cityName;
                private String prCode;
                private String ciCode;
                private String coCode;
                private String twCode;
                private PrCityBeanXX prCity;
                private CiCityBeanXX ciCity;
                private CoCityBeanXX coCity;
                private TwCityBeanXX twCity;
                private String userName;
                private String realName;
                private String publicName;
                private String publicPhone;
                private String plainPassword;
                private String sex;
                private String position;
                private String tel;
                private String phone;
                private String email;
                private String address;
                private String variety;
                private String companyName;
                private String status;
                private boolean isAgent;
                private boolean isClerk;
                private String agentType;
                private boolean isInvoices;
                private String permissions;
                private String storeId;
                private String headImage;
                private boolean isPartners;
                private boolean cashOnDelivery;
                private boolean receiptMsg;
                private boolean isQuote;
                private boolean showQuote;
                private boolean showQuoteCount;
                private boolean supplierIsAgree;
                private String tradeType;
                private boolean isActivated;
                private String agentTypeName;
                private boolean isDirectAgent;
                private String displayName;
                private String adminDisplayName;
                private String displayPhone;
                private String permissionsName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }

                public String getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(String createBy) {
                    this.createBy = createBy;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public String getPrCode() {
                    return prCode;
                }

                public void setPrCode(String prCode) {
                    this.prCode = prCode;
                }

                public String getCiCode() {
                    return ciCode;
                }

                public void setCiCode(String ciCode) {
                    this.ciCode = ciCode;
                }

                public String getCoCode() {
                    return coCode;
                }

                public void setCoCode(String coCode) {
                    this.coCode = coCode;
                }

                public String getTwCode() {
                    return twCode;
                }

                public void setTwCode(String twCode) {
                    this.twCode = twCode;
                }

                public PrCityBeanXX getPrCity() {
                    return prCity;
                }

                public void setPrCity(PrCityBeanXX prCity) {
                    this.prCity = prCity;
                }

                public CiCityBeanXX getCiCity() {
                    return ciCity;
                }

                public void setCiCity(CiCityBeanXX ciCity) {
                    this.ciCity = ciCity;
                }

                public CoCityBeanXX getCoCity() {
                    return coCity;
                }

                public void setCoCity(CoCityBeanXX coCity) {
                    this.coCity = coCity;
                }

                public TwCityBeanXX getTwCity() {
                    return twCity;
                }

                public void setTwCity(TwCityBeanXX twCity) {
                    this.twCity = twCity;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getPublicName() {
                    return publicName;
                }

                public void setPublicName(String publicName) {
                    this.publicName = publicName;
                }

                public String getPublicPhone() {
                    return publicPhone;
                }

                public void setPublicPhone(String publicPhone) {
                    this.publicPhone = publicPhone;
                }

                public String getPlainPassword() {
                    return plainPassword;
                }

                public void setPlainPassword(String plainPassword) {
                    this.plainPassword = plainPassword;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getPosition() {
                    return position;
                }

                public void setPosition(String position) {
                    this.position = position;
                }

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getVariety() {
                    return variety;
                }

                public void setVariety(String variety) {
                    this.variety = variety;
                }

                public String getCompanyName() {
                    return companyName;
                }

                public void setCompanyName(String companyName) {
                    this.companyName = companyName;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public boolean isIsAgent() {
                    return isAgent;
                }

                public void setIsAgent(boolean isAgent) {
                    this.isAgent = isAgent;
                }

                public boolean isIsClerk() {
                    return isClerk;
                }

                public void setIsClerk(boolean isClerk) {
                    this.isClerk = isClerk;
                }

                public String getAgentType() {
                    return agentType;
                }

                public void setAgentType(String agentType) {
                    this.agentType = agentType;
                }

                public boolean isIsInvoices() {
                    return isInvoices;
                }

                public void setIsInvoices(boolean isInvoices) {
                    this.isInvoices = isInvoices;
                }

                public String getPermissions() {
                    return permissions;
                }

                public void setPermissions(String permissions) {
                    this.permissions = permissions;
                }

                public String getStoreId() {
                    return storeId;
                }

                public void setStoreId(String storeId) {
                    this.storeId = storeId;
                }

                public String getHeadImage() {
                    return headImage;
                }

                public void setHeadImage(String headImage) {
                    this.headImage = headImage;
                }

                public boolean isIsPartners() {
                    return isPartners;
                }

                public void setIsPartners(boolean isPartners) {
                    this.isPartners = isPartners;
                }

                public boolean isCashOnDelivery() {
                    return cashOnDelivery;
                }

                public void setCashOnDelivery(boolean cashOnDelivery) {
                    this.cashOnDelivery = cashOnDelivery;
                }

                public boolean isReceiptMsg() {
                    return receiptMsg;
                }

                public void setReceiptMsg(boolean receiptMsg) {
                    this.receiptMsg = receiptMsg;
                }

                public boolean isIsQuote() {
                    return isQuote;
                }

                public void setIsQuote(boolean isQuote) {
                    this.isQuote = isQuote;
                }

                public boolean isShowQuote() {
                    return showQuote;
                }

                public void setShowQuote(boolean showQuote) {
                    this.showQuote = showQuote;
                }

                public boolean isShowQuoteCount() {
                    return showQuoteCount;
                }

                public void setShowQuoteCount(boolean showQuoteCount) {
                    this.showQuoteCount = showQuoteCount;
                }

                public boolean isSupplierIsAgree() {
                    return supplierIsAgree;
                }

                public void setSupplierIsAgree(boolean supplierIsAgree) {
                    this.supplierIsAgree = supplierIsAgree;
                }

                public String getTradeType() {
                    return tradeType;
                }

                public void setTradeType(String tradeType) {
                    this.tradeType = tradeType;
                }

                public boolean isIsActivated() {
                    return isActivated;
                }

                public void setIsActivated(boolean isActivated) {
                    this.isActivated = isActivated;
                }

                public String getAgentTypeName() {
                    return agentTypeName;
                }

                public void setAgentTypeName(String agentTypeName) {
                    this.agentTypeName = agentTypeName;
                }

                public boolean isIsDirectAgent() {
                    return isDirectAgent;
                }

                public void setIsDirectAgent(boolean isDirectAgent) {
                    this.isDirectAgent = isDirectAgent;
                }

                public String getDisplayName() {
                    return displayName;
                }

                public void setDisplayName(String displayName) {
                    this.displayName = displayName;
                }

                public String getAdminDisplayName() {
                    return adminDisplayName;
                }

                public void setAdminDisplayName(String adminDisplayName) {
                    this.adminDisplayName = adminDisplayName;
                }

                public String getDisplayPhone() {
                    return displayPhone;
                }

                public void setDisplayPhone(String displayPhone) {
                    this.displayPhone = displayPhone;
                }

                public String getPermissionsName() {
                    return permissionsName;
                }

                public void setPermissionsName(String permissionsName) {
                    this.permissionsName = permissionsName;
                }

                public static class PrCityBeanXX implements Serializable {
                    /**
                     * id : 16654
                     * name : 福建
                     * fullName : 福建省
                     * cityCode : 35
                     * parentCode : 0
                     * level : 1
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class CiCityBeanXX implements Serializable {
                    /**
                     * id : 16850
                     * name : 厦门
                     * fullName : 福建 厦门
                     * cityCode : 3502
                     * parentCode : 35
                     * level : 2
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class CoCityBeanXX implements Serializable {
                    /**
                     * id : 16851
                     * name : 思明
                     * fullName : 福建省厦门市思明区
                     * cityCode : 350203
                     * parentCode : 3502
                     * level : 3
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }

                public static class TwCityBeanXX implements Serializable {
                    /**
                     * id : 16857
                     * name : 梧村街道
                     * fullName : 福建省厦门市思明区梧村街道
                     * cityCode : 350203008
                     * parentCode : 350203
                     * level : 4
                     */

                    private String id;
                    private String name;
                    private String fullName;
                    private String cityCode;
                    private String parentCode;
                    private int level;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFullName() {
                        return fullName;
                    }

                    public void setFullName(String fullName) {
                        this.fullName = fullName;
                    }

                    public String getCityCode() {
                        return cityCode;
                    }

                    public void setCityCode(String cityCode) {
                        this.cityCode = cityCode;
                    }

                    public String getParentCode() {
                        return parentCode;
                    }

                    public void setParentCode(String parentCode) {
                        this.parentCode = parentCode;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }
                }
            }

            public static class ImagesJsonBean implements Serializable {
                /**
                 * id : 005c473a5cbc49309857062fe27c8aea
                 * imageType : seedling
                 * name : image_20170420122040_0.jpg
                 * url : http://image.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg
                 * sourceId : 3fb1328e1e8f469aac8bf4f3ed8dabb5
                 * isCover : false
                 * sort : 0
                 * ossUrl : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!wt
                 * ossThumbnailImagePath : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!65_87
                 * ossSmallImagePath : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!170_227
                 * ossMediumImagePath : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!210_280
                 * ossLargeImagePath : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!300_400
                 * ossAppLargeImagePath : http://images.hmeg.cn/upload/image/201704/645e35ff421940158333f9b8b627ad1b.jpg@!450_600
                 */

                private String local_url = "";
                private String id;
                private String imageType;
                private String name;
                private String url;
                private String sourceId;
                private boolean isCover;
                private int sort;
                private String ossUrl;
                private String ossThumbnailImagePath;
                private String ossSmallImagePath;
                private String ossMediumImagePath;
                private String ossLargeImagePath;
                private String ossAppLargeImagePath;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getImageType() {
                    return imageType;
                }

                public void setImageType(String imageType) {
                    this.imageType = imageType;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getSourceId() {
                    return sourceId;
                }

                public void setSourceId(String sourceId) {
                    this.sourceId = sourceId;
                }

                public boolean isIsCover() {
                    return isCover;
                }

                public void setIsCover(boolean isCover) {
                    this.isCover = isCover;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getOssUrl() {
                    return ossUrl;
                }

                public void setOssUrl(String ossUrl) {
                    this.ossUrl = ossUrl;
                }

                public String getOssThumbnailImagePath() {
                    return ossThumbnailImagePath;
                }

                public void setOssThumbnailImagePath(String ossThumbnailImagePath) {
                    this.ossThumbnailImagePath = ossThumbnailImagePath;
                }

                public String getOssSmallImagePath() {
                    return ossSmallImagePath;
                }

                public void setOssSmallImagePath(String ossSmallImagePath) {
                    this.ossSmallImagePath = ossSmallImagePath;
                }

                public String getOssMediumImagePath() {
                    return ossMediumImagePath;
                }

                public void setOssMediumImagePath(String ossMediumImagePath) {
                    this.ossMediumImagePath = ossMediumImagePath;
                }

                public String getOssLargeImagePath() {
                    return ossLargeImagePath;
                }

                public void setOssLargeImagePath(String ossLargeImagePath) {
                    this.ossLargeImagePath = ossLargeImagePath;
                }

                public String getOssAppLargeImagePath() {
                    return ossAppLargeImagePath;
                }

                public void setOssAppLargeImagePath(String ossAppLargeImagePath) {
                    this.ossAppLargeImagePath = ossAppLargeImagePath;
                }

                public String getLocal_url() {
                    return local_url;
                }

                public ImagesJsonBean setLocal_url(String local_url) {
                    this.local_url = local_url;
                    return this;
                }


            }


//            	 "attrData": [
//                         "ziying": true
//                         ],

            public static class AttrDataBean implements Serializable {
                public boolean ziying = false;
                public String createDate = "";
                public String ziyingRemarks = "";

            }

            public static class SpecListBean implements Serializable {
                /**
                 * name : 地径
                 * value : 20CM(0.1M量)
                 */

                private String name;
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class TagListBean implements Serializable {
                /**
                 * title : 自营商品
                 * enumText : 自营
                 * enumValue : ziying
                 */

                private String title;
                private String enumText;
                private String enumValue;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getEnumText() {
                    return enumText;
                }

                public void setEnumText(String enumText) {
                    this.enumText = enumText;
                }

                public String getEnumValue() {
                    return enumValue;
                }

                public void setEnumValue(String enumValue) {
                    this.enumValue = enumValue;
                }
            }
        }


        public static class TypeListBean implements Serializable {
            /**
             * id : 74b0e9c44dd0499381a1adcadae6b4c9
             * createBy : 1
             * createDate : 2016-02-29 15:06:37
             * name : 乔木
             * aliasName :
             * parentId :
             * level : 0
             * firstPinyin : qm
             * fullPinyin : qiaomu
             * seedlingParams : dbh,height,crown
             * sort : 1
             * defaultUnit : plant
             * defaultValidity : 180
             * paramsList : [{"value":"dbh","required":true},{"value":"height","required":false},{"value":"crown","required":false}]
             * plantTypeList : [{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}]
             * qualityTypeList : [{"text":"全冠苗","value":"quanguan"},{"text":"大骨架","value":"dagujia"},{"text":"小骨架","value":"xiaogujia"},{"text":"截杆苗","value":"jiegan"}]
             * qualityGradeList : [{"text":"A货","value":"A"},{"text":"B货","value":"B"}]
             * mainSpec : dbh
             */


            private String id;
            private String createBy;
            private String createDate;
            private String name;
            private String aliasName;
            private String parentId;
            private int level;
            private String firstPinyin;
            private String fullPinyin;
            private String seedlingParams;
            private int sort;
            private String defaultUnit;
            private int defaultValidity;
            private String mainSpec;
            private List<ParamsListBean> paramsList;
            private List<PlantTypeListBean> plantTypeList;
            private List<QualityTypeListBean> qualityTypeList;
            private List<QualityGradeListBean> qualityGradeList;

            @Override
            public String toString() {
                return "TypeListBean{" +
                        "id='" + id + '\'' +
                        ", createBy='" + createBy + '\'' +
                        ", createDate='" + createDate + '\'' +
                        ", name='" + name + '\'' +
                        ", aliasName='" + aliasName + '\'' +
                        ", parentId='" + parentId + '\'' +
                        ", level=" + level +
                        ", firstPinyin='" + firstPinyin + '\'' +
                        ", fullPinyin='" + fullPinyin + '\'' +
                        ", seedlingParams='" + seedlingParams + '\'' +
                        ", sort=" + sort +
                        ", defaultUnit='" + defaultUnit + '\'' +
                        ", defaultValidity=" + defaultValidity +
                        ", mainSpec='" + mainSpec + '\'' +
                        ", paramsList=" + paramsList +
                        ", plantTypeList=" + plantTypeList +
                        ", qualityTypeList=" + qualityTypeList +
                        ", qualityGradeList=" + qualityGradeList +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreateBy() {
                return createBy;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAliasName() {
                return aliasName;
            }

            public void setAliasName(String aliasName) {
                this.aliasName = aliasName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getFirstPinyin() {
                return firstPinyin;
            }

            public void setFirstPinyin(String firstPinyin) {
                this.firstPinyin = firstPinyin;
            }

            public String getFullPinyin() {
                return fullPinyin;
            }

            public void setFullPinyin(String fullPinyin) {
                this.fullPinyin = fullPinyin;
            }

            public String getSeedlingParams() {
                return seedlingParams;
            }

            public void setSeedlingParams(String seedlingParams) {
                this.seedlingParams = seedlingParams;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getDefaultUnit() {
                return defaultUnit;
            }

            public void setDefaultUnit(String defaultUnit) {
                this.defaultUnit = defaultUnit;
            }

            public int getDefaultValidity() {
                return defaultValidity;
            }

            public void setDefaultValidity(int defaultValidity) {
                this.defaultValidity = defaultValidity;
            }

            public String getMainSpec() {
                return mainSpec;
            }

            public void setMainSpec(String mainSpec) {
                this.mainSpec = mainSpec;
            }

            public List<ParamsListBean> getParamsList() {
                return paramsList;
            }

            public void setParamsList(List<ParamsListBean> paramsList) {
                this.paramsList = paramsList;
            }

            public List<PlantTypeListBean> getPlantTypeList() {
                return plantTypeList;
            }

            public void setPlantTypeList(List<PlantTypeListBean> plantTypeList) {
                this.plantTypeList = plantTypeList;
            }

            public List<QualityTypeListBean> getQualityTypeList() {
                return qualityTypeList;
            }

            public void setQualityTypeList(List<QualityTypeListBean> qualityTypeList) {
                this.qualityTypeList = qualityTypeList;
            }

            public List<QualityGradeListBean> getQualityGradeList() {
                return qualityGradeList;
            }

            public void setQualityGradeList(List<QualityGradeListBean> qualityGradeList) {
                this.qualityGradeList = qualityGradeList;
            }

            public static class ParamsListBean implements Serializable {
                /**
                 * name : "胸径"
                 * value : dbh
                 * required : true
                 * <p>
                 * "name": "高度",
                 * "value": "height",
                 * "required": false
                 * <p>
                 * "name": "冠幅",
                 * "value": "crown",
                 * "required": false
                 */

                private String value;
                private String name;
                private boolean required;


                @Override
                public String toString() {
                    return "ParamsListBean{" +
                            "value='" + value + '\'' +
                            ", name='" + name + '\'' +
                            ", required=" + required +
                            '}';
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public boolean isRequired() {
                    return required;
                }

                public void setRequired(boolean required) {
                    this.required = required;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class PlantTypeListBean implements Serializable {
                /**
                 * text : 地栽苗
                 * value : planted
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class QualityTypeListBean implements Serializable {
                /**
                 * text : 全冠苗
                 * value : quanguan
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class QualityGradeListBean implements Serializable {
                /**
                 * text : A货
                 * value : A
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

//        public static class PlantTypeListBeanX {
//            /**
//             * text : 地栽苗
//             * value : planted
//             */
//
//            private String text;
//            private String value;
//
//            public String getText() {
//                return text;
//            }
//
//            public void setText(String text) {
//                this.text = text;
//            }
//
//            public String getValue() {
//                return value;
//            }
//
//            public void setValue(String value) {
//                this.value = value;
//            }
//        }
    }
}
