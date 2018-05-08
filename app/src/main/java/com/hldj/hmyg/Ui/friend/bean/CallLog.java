package com.hldj.hmyg.Ui.friend.bean;

import com.hldj.hmyg.Ui.friend.bean.enums.CallSourceType;
import com.hldj.hmyg.Ui.friend.bean.enums.CallTargetType;

import java.io.Serializable;

/**
 * 车辆信息管理Entity
 * @author linzj
 * @version 2017-02-16
 */
public class CallLog  implements Serializable {
	


	public String id = "";
	/**
	 * 
	 */
	private static final long serialVersionUID = -5964735140569341652L;

//	private CallSourceType callSourceType;//seedling、seedlingNote
	private CallSourceType callSourceType;//seedling、seedlingNote

	private String callSourceId;//资源ID
	
	private String callStoreId;//苗木资源所在店铺ID
	
	/**
	 * 被叫资源拥有者ID
	 */
	private String ownerId;
	
	private String userId;//当前用户ID
	
	private String callPhone;//被呼叫号码
	
	private String userType;//系统类型 ：admin mall 
	
	private CallTargetType callTargetType;//被呼叫号码类型：owner(发布人)、nursery(苗圃)

	
	public String getCallSourceId() {
		return callSourceId;
	}

	public void setCallSourceId(String callSourceId) {
		this.callSourceId = callSourceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public CallSourceType getCallSourceType() {
		return callSourceType;
	}

	public void setCallSourceType(CallSourceType callSourceType) {
		this.callSourceType = callSourceType;
	}

	public CallTargetType getCallTargetType() {
		return callTargetType;
	}

	public void setCallTargetType(CallTargetType callTargetType) {
		this.callTargetType = callTargetType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 获取苗木资源所在店铺ID
	 * @return callStoreId
	 */
	public String getCallStoreId() {
		return callStoreId;
	}

	/**
	 * 设置苗木资源所在店铺ID
	 * @param callStoreId
	 */
	public void setCallStoreId(String callStoreId) {
		this.callStoreId = callStoreId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
}