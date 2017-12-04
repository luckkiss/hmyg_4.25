package com.hldj.hmyg.Ui.friend.bean;


import com.hldj.hmyg.bean.UserBean;

import java.io.Serializable;

/**
 * 朋友圈评论Entity
 *
 * @author luow
 * @version 2017-11-24
 */
public class MomentsThumbUp implements Serializable {

    private static final long serialVersionUID = 4307497280064329128L;


    /**
     * 发布内容id
     */
    private String momentsId;

    /**
     * 时间戳
     */
    private long timeStamp;

    /**
     * 点赞人ID
     */
    private String ownerId;


    /***********临时属性  开始*****************/

    /**
     * 用户
     */
//	private User ownerUser;
    public UserBean ownerUserJson;


}