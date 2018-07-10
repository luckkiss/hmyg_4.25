package com.hldj.hmyg.M;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class AddressBean implements Serializable{
    public String id = "";
    public String createBy= "";
    public String createDate= "";
    public String cityCode= "";
    public String cityName= "";
    public String prCode= "";
    public String ciCode= "";
    public String coCode= "";
    public String twCode= "";
    public String name= "";
    public String detailAddress = "";
    public String contactName= "";
    public String contactPhone= "";
    public boolean isDefault;
    public String mainType= "";
    public String typeName= "";
    public double longitude;
    public double latitude;
    public String companyName= "";
    public String publicName= "";
    public String realName= "";
    public String phone= "";
    public String fullAddress= "";
    public String rePublishDate= "";
    public int nurseryArea;


    /**
     * 真实苗场主联系人（不对外展示）
     */
    public String nurseryContactName;
    /**
     * 真实苗场主联系电话（不对外展示）
     */
    public String nurseryContactPhone;


    /**
     * 苗圃内已上架的苗木数量
     */
    public int onShelf;
    public int onShelfJson;
    public int unauditJson;


    /**
     * 苗圃内已下架的资源数量
     */
    public int downShelf;
    public int downShelfJson;


    @Override
    public String toString() {
        return "AddressBean{" +
                "id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate='" + createDate + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", prCode='" + prCode + '\'' +
                ", ciCode='" + ciCode + '\'' +
                ", coCode='" + coCode + '\'' +
                ", twCode='" + twCode + '\'' +
                ", name='" + name + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", isDefault=" + isDefault +
                ", mainType='" + mainType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", companyName='" + companyName + '\'' +
                ", publicName='" + publicName + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", nurseryArea=" + nurseryArea +
                '}';
    }
}
