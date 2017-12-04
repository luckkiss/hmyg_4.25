package com.hldj.hmyg.Ui.friend.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class test {

    public String code;
    public String msg;
    public DataBeanX data;
    public String version;

    public static class DataBeanX {

        public PageBean page;

        public static class PageBean {

            public int pageNo;
            public int pageSize;
            public int total;
            public int firstResult;
            public int maxResults;
            public List<DataBean> data;

            public static class DataBean {

                public String id;
                public String createBy;
                public String createDate;
                public AttrDataBean attrData;
                public String cityCode;
                public String prCode;
                public String ciCode;
                public String coCode;
                public String twCode;
                public CiCityBean ciCity;
                public String content;
                public String momentsType;
                public long timeStamp;
                public String ownerId;
                public int thumbUpCount;
                public boolean isFavour;
                public String timeStampStr;
                public boolean isOwner;
                public String num;
                public List<ItemListJsonBean> itemListJson;
                public List<ImagesJsonBean> imagesJson;

                public static class AttrDataBean {
                    /**
                     * headImage : http://image.hmeg.cn/upload/image/201708/d83a64a1099544cebc3aca9be64a43e0.jpeg
                     * userId : 3378ca2d-b09b-411b-a3d0-28766e314685
                     * displayName : 花木易购
                     * storeId : d55e06b24d854d7587a3461230865e91
                     * displayPhone : 18750215634
                     */

                    public String headImage;
                    public String userId;
                    public String displayName;
                    public String storeId;
                    public String displayPhone;
                }

                public static class CiCityBean {
                    /**
                     * id : 16850
                     * name : 厦门
                     * fullName : 福建 厦门
                     * cityCode : 3502
                     * parentCode : 35
                     * level : 2
                     */

                    public String id;
                    public String name;
                    public String fullName;
                    public String cityCode;
                    public String parentCode;
                    public int level;
                }

                public static class ItemListJsonBean {
                    /**
                     * id : dd3bedf1266c45e6b4cd969fd4ff352b
                     * createBy : 2876f7e0f51c4153aadc603b661fedfa
                     * createDate : 2017-12-04 14:59:33
                     * attrData : {"fromDisplayPhone":"17074990702","fromDisplayName":"最软科技有限","fromHeadImage":"http://image.hmeg.cn/upload/image/201711/a9c891b3d664460d97a983605025563f.png","fromUserId":"2876f7e0f51c4153aadc603b661fedfa"}
                     * momentsId : b18e9596bc264d0e974580b54adc9257
                     * reply : 3sha
                     * timeStamp : 1512370772840
                     * fromId : 2876f7e0f51c4153aadc603b661fedfa
                     * timeStampStr : 1小时前
                     * isOwner : false
                     * toId :
                     */

                    public String id;
                    public String createBy;
                    public String createDate;
                    public AttrDataBeanX attrData;
                    public String momentsId;
                    public String reply;
                    public long timeStamp;
                    public String fromId;
                    public String timeStampStr;
                    public boolean isOwner;
                    public String toId;

                    public static class AttrDataBeanX {
                        /**
                         * fromDisplayPhone : 17074990702
                         * fromDisplayName : 最软科技有限
                         * fromHeadImage : http://image.hmeg.cn/upload/image/201711/a9c891b3d664460d97a983605025563f.png
                         * fromUserId : 2876f7e0f51c4153aadc603b661fedfa
                         */

                        public String fromDisplayPhone;
                        public String fromDisplayName;
                        public String fromHeadImage;
                        public String fromUserId;
                    }
                }

                public static class ImagesJsonBean {
                    /**
                     * id : b85ce510105f4ca088bb307e2b0fa1c6
                     * imageType : moments
                     * name : image.jpeg
                     * url : http://image.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg
                     * sourceId : b18e9596bc264d0e974580b54adc9257
                     * isCover : false
                     * sort : 0
                     * ossUrl : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!wt
                     * ossThumbnailImagePath : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!65_87
                     * ossSmallImagePath : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!170_227
                     * ossMediumImagePath : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!210_280
                     * ossLargeImagePath : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!300_400
                     * ossAppLargeImagePath : http://images.hmeg.cn/upload/image/201712/4780b09805ab4e7a80e68aa3a4767cf7.jpeg@!450_600
                     */

                    public String id;
                    public String imageType;
                    public String name;
                    public String url;
                    public String sourceId;
                    public boolean isCover;
                    public int sort;
                    public String ossUrl;
                    public String ossThumbnailImagePath;
                    public String ossSmallImagePath;
                    public String ossMediumImagePath;
                    public String ossLargeImagePath;
                    public String ossAppLargeImagePath;
                }
            }
        }
    }
}
