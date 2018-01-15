package com.nizubuzu.androidService.entity;

import java.util.Date;

public class HouseResource {
	private Long houseResourceId;
	private String houseResourceName;
	private String houseResourceDesc;
	private String houseResourceAddr;
	private String phone;
	private String houseResourceImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;
	private String advice;
	private String areaMsg;
	
	public String getAreaMsg() {
		return areaMsg;
	}
	public void setAreaMsg(String areaMsg) {
		this.areaMsg = areaMsg;
	}
	private PersionInfo owner;
	private Area area;
	
	public Long getHouseResourceId() {
		return houseResourceId;
	}
	public void setHouseResourceId(Long houseResourceId) {
		this.houseResourceId = houseResourceId;
	}
	public String getHouseResourceName() {
		return houseResourceName;
	}
	public void setHouseResourceName(String houseResourceName) {
		this.houseResourceName = houseResourceName;
	}
	public String getHouseResourceDesc() {
		return houseResourceDesc;
	}
	public void setHouseResourceDesc(String houseResourceDesc) {
		this.houseResourceDesc = houseResourceDesc;
	}
	public String getHouseResourceAddr() {
		return houseResourceAddr;
	}
	public void setHouseResourceAddr(String houseResourceAddr) {
		this.houseResourceAddr = houseResourceAddr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHouseResourceImg() {
		return houseResourceImg;
	}
	public void setHouseResourceImg(String houseResourceImg) {
		this.houseResourceImg = houseResourceImg;
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
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public PersionInfo getOwner() {
		return owner;
	}
	public void setOwner(PersionInfo owner) {
		this.owner = owner;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	
}
