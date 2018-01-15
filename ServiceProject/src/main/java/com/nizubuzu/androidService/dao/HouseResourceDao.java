package com.nizubuzu.androidService.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nizubuzu.androidService.entity.HouseResource;

public interface HouseResourceDao {

	/**
	 * 添加房源
	 * 
	 * @param houseResource
	 * @return
	 */
	int insertHouseResource(HouseResource houseResource);

	/**
	 * 根据id查询
	 * 
	 * @param houseResourceId
	 * @return
	 */
	HouseResource queryByHouseResourceId(Long houseResourceId);

	/**
	 * 更新店铺信息
	 * 
	 * @param houseResource
	 * @return
	 */
	int updataHouseResource(HouseResource houseResource);

	/**
	 * 分页查询
	 * @param houseResource
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<HouseResource> queryHouseResourceList(
			@Param("houseResource") HouseResource houseResource,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 查询房源个数
	 * @param houseResource
	 * @return
	 */
	int queryHouseResouceCount(@Param("houseResource") HouseResource houseResource);
}
