package com.hldj.hmyg.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/17.
 */

public class UnitTypeBean implements Serializable {
    public String text = "";
    public String value = "";//默认值

    public UnitTypeBean(String text, String value) {

        this.value = value;
        this.text = text;
    }

    public static UnitTypeBean unitDefault = new UnitTypeBean("株", "plant");
/**
 *  "text": "株",
 "value": "plant"
 */


}
