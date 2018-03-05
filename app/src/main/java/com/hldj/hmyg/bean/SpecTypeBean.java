package com.hldj.hmyg.bean;

import java.io.Serializable;

/**
 *
 */

public class SpecTypeBean implements Serializable{


    public String text = "";
    public String value = "";

    @Override
    public String toString() {
        return "SpecTypeBean{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    /**
     *    "text": "出土量",
     "value": "size0"
     */

}
