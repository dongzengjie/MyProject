package com.nizubuzu.androidService.dto;

import java.util.List;

import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseCategoryState;
import com.nizubuzu.androidService.enums.HouseResourceState;

public class HouseCategoryExecution {

	private int state;
	private String stateInfo;
	
	private HouseCategory houseCategory;
	private List<HouseCategory> houseCategorylist;
	
	private int count;

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


	public HouseCategory getHouseCategory() {
		return houseCategory;
	}

	public void setHouseCategory(HouseCategory houseCategory) {
		this.houseCategory = houseCategory;
	}

	public List<HouseCategory> getHouseCategorylist() {
		return houseCategorylist;
	}

	public void setHouseCategorylist(List<HouseCategory> houseCategorylist) {
		this.houseCategorylist = houseCategorylist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public HouseCategoryExecution(){
		
	}
	
	public HouseCategoryExecution(HouseCategoryState houseCategoryState){
		this.state=houseCategoryState.getState();
		this.stateInfo=houseCategoryState.getStateInfo();
	}
	
	public HouseCategoryExecution(HouseCategoryState houseCategoryState, HouseCategory houseCategory){
		this.state=houseCategoryState.getState();
		this.stateInfo=houseCategoryState.getStateInfo();
		this.houseCategory=houseCategory;
	}
	
	public HouseCategoryExecution(HouseCategoryState houseCategoryState, List<HouseCategory> houseCategorylist){
		this.state=houseCategoryState.getState();
		this.stateInfo=houseCategoryState.getStateInfo();
		this.houseCategorylist=houseCategorylist;
	}
}
