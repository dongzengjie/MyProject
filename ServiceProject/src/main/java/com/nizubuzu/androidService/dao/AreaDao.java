package com.nizubuzu.androidService.dao;

import java.util.List;

import com.nizubuzu.androidService.entity.Area;

public interface AreaDao {

	/**
	 * 查询全部地区 
	 * @return
	 */
	public List<Area> queryAllArea();
	
	/**
	 * 添加地区
	 * @param area
	 * @return
	 */
	public int insertArea(Area area);
}
