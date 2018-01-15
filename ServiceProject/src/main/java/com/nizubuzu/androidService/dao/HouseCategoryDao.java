package com.nizubuzu.androidService.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nizubuzu.androidService.entity.HouseCategory;

public interface HouseCategoryDao {

	/**
	 * 批量添加房源种类
	 */
	int batchinserthouseCategory(List<HouseCategory> houseCategories);

	/**
	 * 根据房源id查询房源种类
	 * 
	 * @param houseResourceId
	 * @return
	 */
	List<HouseCategory> queryhouseCategoryByHouseResourceId(@Param("houseResourceId")Long houseResourceId);

	/**
	 * 根据房源id和房源种类id删除房源种类信息
	 * @param houseCategoryId
	 * @param houseResourceId
	 * @return
	 */
	int deleteByhouseResourceIdAndhouseCategoryId(
			@Param("houseCategoryId") Long houseCategoryId, @Param("houseResourceId")Long houseResourceId);
}
