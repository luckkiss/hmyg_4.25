package com.hldj.hmyg.CallBack;

import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

/**
 * 本接口用于  footmark  用户足迹 清空使用
 */

public interface  IFootMarkEmpty extends IEmpty {

        FootMarkSourceType sourceType();

        void doEmpty();



}
