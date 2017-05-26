package com.hldj.hmyg.bean;

import com.hldj.hmyg.application.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
public class QueryBean implements Serializable {
    public String searchSpec;
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
    public String latitude = MyApplication.Userinfo.getString("latitude", "");
    public String longitude = MyApplication.Userinfo.getString("longitude", "");
    public List<String> listTags = new ArrayList<>();
}