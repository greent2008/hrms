package com.lrm.config;

public enum UserTypeEnum {
	ADMIN(0),
	MANAGER(1),
	GROUPLEADER(2),
	OUTSOURCING(3);
	
	private Integer userType;
	
	private UserTypeEnum(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
