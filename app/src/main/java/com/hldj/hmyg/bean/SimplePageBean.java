package com.hldj.hmyg.bean;

import java.io.Serializable;

/**
 * 用于接收  普通的 成功失败返回  的gson 笨啊、===
 */

public class SimplePageBean<T> implements Serializable {

    /**
     * =============json=========={"code":"1","msg":"操作成功"}
     */
    public Integer pageNo ;
    public Integer pageSize;
    public Integer total ;
    public T data;
  
}
