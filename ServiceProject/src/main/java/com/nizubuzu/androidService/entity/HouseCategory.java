package com.nizubuzu.androidService.entity;

import java.util.Date;

public class HouseCategory {
	private Long houseCategoryId;
	private String houseCategoryName;
	private Integer priority;
	private Date createTime;
	private Long houseResourceId;
	public Long getHouseCategoryId() {
		return houseCategoryId;
	}
	public void setHouseCategoryId(Long houseCategoryId) {
		this.houseCategoryId = houseCategoryId;
	}
	public String getHouseCategoryName() {
		return houseCategoryName;
	}
	public void setHouseCategoryName(String houseCategoryName) {
		this.houseCategoryName = houseCategoryName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getHouseResourceId() {
		return houseResourceId;
	}
	public void setHouseResourceId(Long houseResourceId) {
		this.houseResourceId = houseResourceId;
	}
	
}
