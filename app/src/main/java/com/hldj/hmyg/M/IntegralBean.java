package com.hldj.hmyg.M;

/**
 * 积分对象
 */

public class IntegralBean {

    public int leftRes;

    public String topText;

    public String bottomText;


    public boolean rightState;

    public IntegralBean(int leftRes, String topText, String bottomText,boolean rightState) {
        this.leftRes = leftRes;
        this.topText = topText;
        this.bottomText = bottomText;
        this.rightState = rightState;
    }


}
