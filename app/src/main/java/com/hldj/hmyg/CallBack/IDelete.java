package com.hldj.hmyg.CallBack;

/**
 * 删除接口  所有能够删除的数据  实现 这个接口
 */

public interface IDelete {


    // 要删除的id
//    public String id = "";

    String getResourceId();


    // 写一个  host  表示  要删除的接口

//    public String host = "";

    String getDomain();


}
