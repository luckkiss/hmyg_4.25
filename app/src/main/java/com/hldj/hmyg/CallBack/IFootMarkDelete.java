package com.hldj.hmyg.CallBack;

import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

/**
 * del  的 某一 子类
 */

public interface IFootMarkDelete extends IDelete {

    // 写一个类型  enmu  表示要删除的   类型

//    public String type;

    FootMarkSourceType getType();


    String getFootMarkId();

}
