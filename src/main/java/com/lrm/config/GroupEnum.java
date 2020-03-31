package com.lrm.config;

public enum GroupEnum {
	SUPER("super"),
	MANAGER("manager"),
	AC("ac"),
	FRONT("front"),
	ETF("etf"),
	CORE("core"),
	BASE("base");
	
	private String groupName;

	private GroupEnum(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
