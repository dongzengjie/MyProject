package com.nizubuzu.androidService.dto;

import java.util.List;

import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseCategoryState;
import com.nizubuzu.androidService.enums.HouseResourceState;
import com.nizubuzu.androidService.enums.HouseState;

public class HouseExecution {

	private int state;
	private String stateInfo;
	
	private House house;
	private List<House> houselist;
	
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

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public List<House> getHouselist() {
		return houselist;
	}

	public void setHouselist(List<House> houselist) {
		this.houselist = houselist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	public HouseExecution(HouseState houseState){
		this.state=houseState.getState();
		this.stateInfo=houseState.getStateInfo();
	}
	
	public HouseExecution(HouseState houseState, House house){
		this.state=houseState.getState();
		this.stateInfo=houseState.getStateInfo();
		this.house=house;
	}
	
	public HouseExecution(HouseState houseState, List<House> houselist){
		this.state=houseState.getState();
		this.stateInfo=houseState.getStateInfo();
		this.houselist=houselist;
	}
	public HouseExecution(){
		
	}

}
