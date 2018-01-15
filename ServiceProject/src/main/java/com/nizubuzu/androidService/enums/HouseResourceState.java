package com.nizubuzu.androidService.enums;

public enum HouseResourceState {

	SUCCESS(1,"操作成功"),CHECK(0, "审核中"),OFFLINE(-1, "非法房源"),NULL_HOUSERESOURCEID(-1002, "HouseRseourceId为空"),NULL_HOUSERESOURCE(-1003,"HouseRseource信息为空"),INSERT_ERROR(-1000,"添加失败");
	
	private int state;
	private String stateInfo;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	private HouseResourceState(int state,String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static HouseResourceState stateof(int state){
		for (HouseResourceState stateEnum : values()) {
			if(stateEnum.getState()==state){
				return stateEnum;
			}
		}
		return null;
	}
	
	


	
}
