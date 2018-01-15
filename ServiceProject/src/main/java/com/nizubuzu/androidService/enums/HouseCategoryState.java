package com.nizubuzu.androidService.enums;

public enum HouseCategoryState {

	SUCCESS(1,"操作成功"),INSERT_ERROR(-1000,"添加失败"),INSERT_Less(-2,"添加数小于1");
	
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
	
	private HouseCategoryState(int state,String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static HouseCategoryState stateof(int state){
		for (HouseCategoryState stateEnum : values()) {
			if(stateEnum.getState()==state){
				return stateEnum;
			}
		}
		return null;
	}
	
	


	
}
