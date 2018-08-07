package com.hldj.hmyg.saler.bean;

import com.hldj.hmyg.CallBack.IChangeData;

/**
 *
 */

public class PurchaseMap implements IChangeData {

    public String name = "";
    public String num = "";


    @Override
    public String getChangData() {
        return name + "[" + num + "]";
    }
}
