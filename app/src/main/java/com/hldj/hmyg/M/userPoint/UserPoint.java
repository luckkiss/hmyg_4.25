package com.hldj.hmyg.M.userPoint;


import com.hldj.hmyg.M.userPoint.enums.UserPointType;

import java.io.Serializable;

/**
 * 用户积分Entity
 *
 * @author linzj
 * @version 2017-7-5
 */
public class UserPoint implements Serializable {


    /**
     * 用户ID
     */
    public String userId;
    /**
     * 积分
     */
    public Integer point;
    /**
     * 积分类型（当天首次登陆、发布资源、浏览资源、报价）
     */
    public UserPointType type;


    /***********************************临时属性  start****************************************/

    /**
     * 积分类型名称
     */
    public String typeName;


    /***********************************临时属性  end******************************************/


}