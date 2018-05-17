package com.hldj.hmyg.Ui.friend.bean.tipNum;

import java.io.Serializable;

/**
 * 菜单通知封装实体
 *
 * @author linzj
 *         2018-5-3
 **/
public class TipNum implements Serializable {


    /**
     * 菜单类型
     */
    public TipNumType type;

    /**
     * 是否显示红点
     */
    public boolean showPoint;

    /**
     * 提示数量
     */
    public Integer count;


    @Override
    public String toString() {
        return "TipNum{" +
                "type=" + type +
                ", showPoint=" + showPoint +
                ", count=" + count +
                '}';
    }
}
