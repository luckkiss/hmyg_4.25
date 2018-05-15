package com.hldj.hmyg.CallBack.impl;

import com.hldj.hmyg.CallBack.IFootMarkDelete;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;

/**
 * footmark 足迹清空类
 */

public class FootMarkDeleteImpl implements IFootMarkDelete {
    //请 emptyFootMark

    private String host = "admin/footmark/emptyFootMark";

    private FootMarkSourceType type;

    public void setType(FootMarkSourceType type) {
        this.type = type;
    }

    @Override
    public String getResourceId() {
        return null;
    }

    @Override
    public String getDomain() {
        return host;
    }

    @Override
    public FootMarkSourceType getType() {
        return type;
    }

    @Override
    public String getFootMarkId() {
        return null;
    }
}
