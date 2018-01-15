package com.nizubuzu.androidService.entity;

import java.util.Date;
import java.util.List;

public class House {
	private Long houseId;
	private String houseName;
	private String houseDesc;
	private String imgAddr;
	private String price;
	private Integer priority;
	private Date create_time;
	private Date lastEditTime;
	private Integer enableStatus;
	
	private List<HouseDetailImg> houseDetailImgs;
	private HouseCategory houseCategory;
	private HouseResource houseResource;
	
	public List<HouseDetailImg> getHouseDetailImgs() {
		return houseDetailImgs;
	}
	public void setHouseDetailImgs(List<HouseDetailImg> houseDetailImgs) {
		this.houseDetailImgs = houseDetailImgs;
	}
	public Long getHouseId() {
		return houseId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHouseDesc() {
		return houseDesc;
	}
	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	public HouseCategory getHouseCategory() {
		return houseCategory;
	}
	public void setHouseCategory(HouseCategory houseCategory) {
		this.houseCategory = houseCategory;
	}
	public HouseResource getHouseResource() {
		return houseResource;
	}
	public void setHouseResource(HouseResource houseResource) {
		this.houseResource = houseResource;
	}
	
}
