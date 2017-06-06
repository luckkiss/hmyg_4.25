package com.hldj.hmyg.bean;

import java.io.Serializable;

public class HomeStore implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	private String logoUrl;
	String index_type;

	String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIndex_type() {
		return index_type;
	}
	public void setIndex_type(String index_type) {
		this.index_type = index_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HomeStore(String id,String code, String img, String index_type, String name) {
		super();
		this.id = id;
		this.code = code;
		this.logoUrl = img;
		this.index_type = index_type;
		this.name = name;
	}
}
