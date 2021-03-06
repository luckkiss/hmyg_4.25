package com.hldj.hmyg.buyer.M;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/28.
 */

public class ImagesJsonBean implements Serializable {
    /**
     * id : 7d148d63ecb44732ac91e71574bd1769
     * imageType : qoute
     * name : 花木易购广告语竖.png
     * url : http://image.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png
     * sourceId : 2c998ab17cd041218c87b742ca8ef774
     * ossUrl : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!wt
     * ossThumbnailImagePath : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!65_87
     * ossSmallImagePath : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!170_227
     * ossMediumImagePath : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!210_280
     * ossLargeImagePath : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!300_400
     * ossAppLargeImagePath : http://images.hmeg.cn/upload/image/201704/6ad38c024360493f9195ecc28b174a95.png@!450_600
     */

    public String id;
    public String imageType;
    public String name;
    public String url;
    public String sourceId;
    public String ossUrl;
    public String ossThumbnailImagePath;
    public String ossSmallImagePath;
    public String ossMediumImagePath;
    public String ossLargeImagePath;
    public String ossAppLargeImagePath;


    public int sort ;

    public String local_url= "";


    public boolean isIsCover ;

    public ImagesJsonBean setId(String id) {
        this.id = id;
        return this;

    }

    public String getId()
    {
        return id;
    }

    public String getLocal_url() {
        return local_url;
    }

    public ImagesJsonBean setLocal_url(String url) {
        this.local_url = url;
        return this;
    }


    @Override
    public String toString() {
        return "ImagesJsonBean{" +
                "id='" + id + '\'' +
                ", imageType='" + imageType + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", ossUrl='" + ossUrl + '\'' +
                ", ossThumbnailImagePath='" + ossThumbnailImagePath + '\'' +
                ", ossSmallImagePath='" + ossSmallImagePath + '\'' +
                ", ossMediumImagePath='" + ossMediumImagePath + '\'' +
                ", ossLargeImagePath='" + ossLargeImagePath + '\'' +
                ", ossAppLargeImagePath='" + ossAppLargeImagePath + '\'' +
                '}';
    }

    public boolean isIsCover() {
            return isIsCover;
    }

    public String getUrl() {
        return url;
    }

    public int getSort() {
        return sort;
    }

    public String getOssMediumImagePath() {
        return ossMediumImagePath;
    }

    public String getOssUrl() {
        return ossUrl;
    }
}
