package com.nizubuzu.androidService.service;

import java.util.List;

import com.nizubuzu.androidService.dto.HouseCategoryExecution;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.exception.HouseCategoryOPerationException;

public interface HouseCategoryService {

	
	
	/**
	 * 根据房源的id查询房源
	 * @param houseResourceId
	 * @return
	 */
	List<HouseCategory> queryhouseCategoryByHouseResourceId(Long houseResourceId) throws HouseCategoryOPerationException;
	
	/**
	 * 批量添加
	 * @param houseCategorylist
	 * @return
	 * @throws HouseCategoryOPerationException
	 */
	HouseCategoryExecution batchinserthouseCategory(List<HouseCategory> houseCategorylist) throws HouseCategoryOPerationException;
	
	/**
	 * 根据房源id和房源种类id删除房源种类
	 * @param houseCategoryId
	 * @param houseResourceId
	 * @return
	 */
	HouseCategoryExecution deleteByhouseResourceIdAndhouseCategoryId( Long houseCategoryId, Long houseResourceId);
}
