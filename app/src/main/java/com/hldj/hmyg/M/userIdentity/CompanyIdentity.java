package com.hldj.hmyg.M.userIdentity;


import com.hldj.hmyg.M.userIdentity.enums.CompanyIdentityStatus;

/**
 * 企业认证Entity
 * 
 * @author linzj
 * @version 2017-02-16
 */
public class CompanyIdentity {

	/**
	 * UUID
	 */
	public static final long serialVersionUID = -5964735140552357652L;

	
	public String id ;
	
	/**
	 * 关联的店铺id
	 */
	public String storeId;

	/**
	 * 企业名称
	 */
	public String companyName;
	
	/**
	 * 法人姓名
	 */
	public String legalPersonName;

	/**
	 * 营业执照注册代码
	 */
	public String licenceNum;

	/**
	 * 状态
	 */
	public CompanyIdentityStatus status;

	/**
	 * 营业执照注册代码照片
	 */
	public String licenceImageUrl;

	/**
	 * 法人身份证照片
	 */
	public String legalPersonImageUrl;

	/**
	 * 营业执照注册代码照片id
	 */
	public String licenceImageId;

	/**
	 * 法人身份证照片id
	 */
	public String legalPersonImageId;

	/**
	 * 客服反馈
	 */
	public String auditLog;

	/*********** 临时属性 开始 *****************/
	@SuppressWarnings("unused")
	public String statusName;
//	public ImagesJsonBean licenceImage;
//	public ImagesJsonBean licenceImageJson;

//	public ImagesJsonBean legalPersonImage;
//	public ImagesJsonBean legalPersonImageJson;

	/*********** 临时属性 结束 *****************/



}