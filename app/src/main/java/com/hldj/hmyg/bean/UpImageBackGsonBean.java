package com.hldj.hmyg.bean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class UpImageBackGsonBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"image":{"id":"618709fcde9446fe925c299027810a78","createBy":"2876f7e0f51c4153aadc603b661fedfa","createDate":"2017-04-18 13:15:02","name":"wx_camera_1492170878634.jpg","url":"http://image.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg","ossUrl":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!450_600"}}
     */

    private String code;
    private String msg;
    private DataBean data;

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

    public static class DataBean {
        /**
         * image : {"id":"618709fcde9446fe925c299027810a78","createBy":"2876f7e0f51c4153aadc603b661fedfa","createDate":"2017-04-18 13:15:02","name":"wx_camera_1492170878634.jpg","url":"http://image.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg","ossUrl":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!wt","ossThumbnailImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!65_87","ossSmallImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!170_227","ossMediumImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!210_280","ossLargeImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!300_400","ossAppLargeImagePath":"http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!450_600"}
         */

        private ImageBean image;

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
        }

        public static class ImageBean {
            /**
             * id : 618709fcde9446fe925c299027810a78
             * createBy : 2876f7e0f51c4153aadc603b661fedfa
             * createDate : 2017-04-18 13:15:02
             * name : wx_camera_1492170878634.jpg
             * url : http://image.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg
             * ossUrl : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!wt
             * ossThumbnailImagePath : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!65_87
             * ossSmallImagePath : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!170_227
             * ossMediumImagePath : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!210_280
             * ossLargeImagePath : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!300_400
             * ossAppLargeImagePath : http://images.hmeg.cn/upload/image/201704/f1187fa9ee11479894886d4533bfc123.jpg@!450_600
             */
            private String id;
            private String createBy;
            private String createDate;
            private String name;
            private String url;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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
        }
    }
}
