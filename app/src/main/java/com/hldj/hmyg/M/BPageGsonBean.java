package com.hldj.hmyg.M;

import com.hldj.hmyg.CallBack.IFootMarkDelete;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class BPageGsonBean {


    public String code;
    public String msg;
    public DatabeanX data;

    public static class DatabeanX {

        public Pagebean page;

        public static class Pagebean {


            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<Databean> data = new ArrayList<>();

            public static class Databean implements IFootMarkDelete {

                public int inputNum = -1;
                public boolean isChecked = false;

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
                public CiCitybean ciCity = new CiCitybean();
                public String firstSeedlingTypeId;
                public String secondSeedlingTypeId;
                public String dbhType;
                public String plantType = "";
                public String unitType;
                public String imageUrl;

                public String minHeight;
                public String minCrown;

                public String ossUrl;
                public String firstTypeName;

                public String unitTypeName = "";
                public String plantTypeName;
                public String diameterTypeName;
                public String dbhTypeName;
                public String thumbnailImageUrl;
                public String smallImageUrl;
                public String mediumImageUrl;
                public String largeImageUrl;
                public String seedlingParams;
                public String specText;
                public String name = "";

                public boolean isNego;

                public int validity;
                public String nurseryId;
                public String count = "";

                public String status;

                public String publishDate;
                public String closeDate = "";

                public String customerId;

                public OwnerJsonbean ownerJson;
                public long lastTime;

                public String orderBy;
                public String statusName;

                public boolean cashOnDelivery;

                public String diameterType;

                public List<String> paramsList;
                public List<SpecListbean> specList;
                public List<TagListbean> tagList;


                public String minPrice = "";
                public String maxPrice = "";
                public String priceStr = "-";

                public SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nurseryJson = new SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean();

                public SaveSeedingGsonBean.DataBean.SeedlingBean.AttrDataBean attrData = new SaveSeedingGsonBean.DataBean.SeedlingBean.AttrDataBean();

                @Override
                public String getResourceId() {
                    return id;
                }

//                @Override
//                public FootMarkSourceType getType() {
//                    return FootMarkSourceType.seedling;
//                }

                @Override
                public String getDomain() {
                    return "admin/footmark/userDel";//收藏 的  删除   host地址
                }

                @Override
                public FootMarkSourceType getType() {
                    return FootMarkSourceType.seedling;
                }

                @Override
                public String getFootMarkId() {
                    return attrData.footMarkId;
                }




                public static class CiCitybean {
                    /**
                     * id : 24234
                     * name : 信阳
                     * fullName : 河南 信阳
                     * cityCode : 4115
                     * parentCode : 41
                     * level : 2
                     */

                    public String id;
                    public String name;
                    public String fullName;
                    public String cityCode;
                    public String parentCode;
                    public int level;
                }

                public static class OwnerJsonbean {


                    public String id;
                    public String createDate;
                    public String cityCode;
                    public String cityName;
                    public String prCode;
                    public String ciCode;
                    public String coCode;
                    public String twCode;
                    public String userName;
                    public String realName = "";
                    public String publicName = "";
                    public String publicPhone;
                    public String plainPassword;
                    public String sex;
                    public String phone;
                    public String email;
                    public String address;
                    public String companyName = "";
                    public String status;
                    public boolean isInvoices;
                    public String permissions;
                    public String storeId;
                    public String headImage;
                    public boolean isActivated;
                    public boolean isPartner;
                    public String agentTypeName;
                    public boolean isDirectAgent;
                    public String displayName;
                    public String adminDisplayName;
                    public String displayPhone;
                    public String permissionsName;
                }

                public static class SpecListbean {
                    /**
                     * name : 米径
                     * value : 15CM
                     */

                    public String name;
                    public String value;
                }

                public static class TagListbean {
                    /**
                     * title : 服务覆盖
                     * enumText : 服务覆盖
                     * enumValue : fuwu
                     */

                    public String title;
                    public String enumText;
                    public String enumValue;
                }
            }
        }
    }
}
