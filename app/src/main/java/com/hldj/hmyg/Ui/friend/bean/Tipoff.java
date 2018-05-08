package com.hldj.hmyg.Ui.friend.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 举报Entity
 *
 * @author linzj
 * @version 2018-4-21
 */
public class Tipoff implements Serializable {


    /**
     *
     */
    public static final long serialVersionUID = 4665200326793792246L;

    /**
     * 举报人ID
     */
    public String tipoffUserId;


    /**
     * 被举报人ID
     */
    public String beTipoffUserId;

    /**
     * 被举报资源ID
     */
    public String sourceId;

    /**
     * 被举报资源类型
     */
    public String sourceType;


    public String sourceTypeName;


    /**
     * 举报标题，如：虚假宣传/图片与描述不符等，详细信息在remarks体现
     */
    public String title;

    /**
     * 是否受理
     */
    public Boolean isAccept;

    /**
     * 受理的客服ID
     */
    public String customerId;

    /**
     * 受理结果
     */
    public String acceptRemarks;

    /*************临时属性，不在数据库中 开始**********************/

    public static List<String> TIPOFF_TITLE_LIST = new ArrayList<>();

    static {
        TIPOFF_TITLE_LIST.add("违法信息");
        TIPOFF_TITLE_LIST.add("图片与描述不符");
        TIPOFF_TITLE_LIST.add("虚假宣传");
        TIPOFF_TITLE_LIST.add("内容抄袭");
        TIPOFF_TITLE_LIST.add("其他");
    }

}