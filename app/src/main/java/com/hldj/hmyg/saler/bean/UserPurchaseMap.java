package com.hldj.hmyg.saler.bean;

import com.hldj.hmyg.CallBack.IChangeData;

/**
 *
 */

public class UserPurchaseMap implements IChangeData {

    public String name = "";
    public String date = "";


    @Override
    public String getChangData() {
        return name + "[" + date + "]";
    }
}
