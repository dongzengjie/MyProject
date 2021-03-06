package com.dzj.o2o.entity;

import java.util.Date;

public class Area {
	private Integer areaId;//id
	private String areaName;//名称
	private Integer prioity;//权重
	private Date createTime;//创建时间
	private Date lastEditTime; //更新时间
	
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPrioity() {
		return prioity;
	}
	public void setPrioity(Integer prioity) {
		this.prioity = prioity;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
