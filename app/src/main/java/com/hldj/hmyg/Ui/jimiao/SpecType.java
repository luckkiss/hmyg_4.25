package com.hldj.hmyg.Ui.jimiao;


import com.google.gson.annotations.SerializedName;

/**
 * 规格类型
 *
 * @author linzj
 */
public enum SpecType {

    empty("",""),

    /**
     * 出土量
     */
    size0("size0", "出土量"),//地径出土量

    /**
     * 10公分量
     */
    size10("size10", "0.1M量"),//地径0.1M量

    /**
     * 30公分量
     */
    size30("size30", "0.3M量"),//地径0.3M量

    /**
     * 1米量
     */
    size100("size100", "1.0M量"),//米径

    /**
     * 1.2米量
     */
    size120("size120", "1.2M量"),//1.2米量

    /**
     * 1.3M量
     */
    size130("size130", "1.3M量");//胸径



    @SerializedName("value")
    private String enumValue ;
    @SerializedName("text")
    private String enumText ;

    private SpecType(String enumValue, String enumText) {
        this.enumValue = enumValue;
        this.enumText = enumText;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumText() {
        return enumText;
    }

    public void setEnumText(String enumText) {
        this.enumText = enumText;
    }


    @Override
    public String toString() {
        return "SpecType{" +
                "enumValue='" + enumValue + '\'' +
                ", enumText='" + enumText + '\'' +
                '}';
    }
}