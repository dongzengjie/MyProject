package com.nizubuzu.androidService.dto;

import java.util.List;

import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseResourceState;

public class HouseResourceExecution {

	private int state;
	private String stateInfo;
	
	private HouseResource houseResource;
	private List<HouseResource> houseResourceslist;
	
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

	public HouseResource getHouseResource() {
		return houseResource;
	}

	public void setHouseResource(HouseResource houseResource) {
		this.houseResource = houseResource;
	}

	public List<HouseResource> getHouseResourceslist() {
		return houseResourceslist;
	}

	public void setHouseResourceslist(List<HouseResource> houseResourceslist) {
		this.houseResourceslist = houseResourceslist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public HouseResourceExecution(){
		
	}
	
	public HouseResourceExecution(HouseResourceState houseResourceState){
		this.state=houseResourceState.getState();
		this.stateInfo=houseResourceState.getStateInfo();
	}
	
	public HouseResourceExecution(HouseResourceState houseResourceState, HouseResource houseResource){
		this.state=houseResourceState.getState();
		this.stateInfo=houseResourceState.getStateInfo();
		this.houseResource=houseResource;
	}
	
	public HouseResourceExecution(HouseResourceState houseResourceState, List<HouseResource> houseResourcelist){
		this.state=houseResourceState.getState();
		this.stateInfo=houseResourceState.getStateInfo();
		this.houseResourceslist=houseResourcelist;
	}
}
