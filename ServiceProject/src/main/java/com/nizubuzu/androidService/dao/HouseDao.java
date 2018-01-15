package com.nizubuzu.androidService.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nizubuzu.androidService.entity.House;

public interface HouseDao {
	
	/**
	 * 添加房屋
	 * @param house
	 * @return
	 */
	int insertHouse(House house);
	
	/**
	 * 根据id查询房屋
	 * @param houseId
	 * @return
	 */
	House queryHouseByHouseId(Long houseId);
	
	/**
	 * 分页查询房屋信息
	 * @param house
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<House> queryHouseList(@Param("house")House house,@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 更新房屋信息
	 * @param house
	 * @return
	 */
	int updateHouse(House house);
	
	/**
	 * 查询房屋数量
	 * @param house
	 * @return
	 */
	int queryCount(@Param("house")House house);
	
	/**
	 * 将关联的房屋种类id职位空
	 * @param houseCategoryId
	 * @return
	 */
	int updateHouseCategoryIdToNull(Long houseCategoryId);
}
