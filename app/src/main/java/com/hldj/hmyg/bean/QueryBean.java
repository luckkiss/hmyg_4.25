package com.hldj.hmyg.bean;

import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.application.MyApplication;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26.
 */
public class QueryBean implements Serializable {
    public String searchSpec = "";
    public String specMinValue = "";
    public String specMaxValue = "";
    public String searchKey = "";
    public String supportTradeType = "";
    public String firstSeedlingTypeId = "";
    public String secondSeedlingTypeId = "";
    public String cityCode = "";
    public String plantTypes = "";
    public String orderBy = "";
    public int pageSize = 20;
    public int pageIndex = 0;
    //    public String latitude = MyApplication.Userinfo.getString("latitude", "");
//    public String longitude = MyApplication.Userinfo.getString("longitude", "");
    public String latitude = MainActivity.latitude.equals("") ? MyApplication.Userinfo.getString("latitude", "") : MainActivity.latitude;
    public String longitude = MainActivity.longitude.equals("") ? MyApplication.Userinfo.getString("longitude", "") : MainActivity.longitude;

    /**
     * latitude=24.488284 longitude=118.09649
     *
     * @return
     */

    @Override
    public String toString() {
        return "QueryBean{" +
                "searchSpec='" + searchSpec + '\'' +
                ", specMinValue='" + specMinValue + '\'' +
                ", specMaxValue='" + specMaxValue + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", supportTradeType='" + supportTradeType + '\'' +
                ", firstSeedlingTypeId='" + firstSeedlingTypeId + '\'' +
                ", secondSeedlingTypeId='" + secondSeedlingTypeId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", plantTypes='" + plantTypes + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}