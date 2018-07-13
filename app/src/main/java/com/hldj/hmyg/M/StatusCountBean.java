package com.hldj.hmyg.M;

import java.util.List;

/**
 *
 */

public class StatusCountBean {


    /**
     * code : 1
     * msg : 操作成功
     * data : {"nurseryId":"a3e7746dc9af4127b6838ed875c9ca5b","pendingCount":1,"statusList":["unaudit","published"],"sellingCount":1,"type":"selling"}
     * version : tomcat7.0.53
     */
    public String nurseryId;
    public int pendingCount;
    public int sellingCount;
    public int unauditCount;
    public String type;
    public List<String> statusList;
}
