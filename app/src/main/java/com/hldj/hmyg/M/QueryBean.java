package com.hldj.hmyg.M;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class QueryBean implements Serializable {
    public String ownerId = "";
    public String storeId = "";
    public String orderBy = "";
    public String plantTypes = "";
    public String firstSeedlingTypeId = "";
    public String secondSeedlingTypeId = "";
    public int pageSize = 10;
    public String pageIndex = "0";

    @Override
    public String toString() {
        return "QueryBean{" +
                "ownerId='" + ownerId + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", plantTypes='" + plantTypes + '\'' +
                ", firstSeedlingTypeId='" + firstSeedlingTypeId + '\'' +
                ", pageSize=" + pageSize +
                ", pageIndex='" + pageIndex + '\'' +
                '}';
    }
}
