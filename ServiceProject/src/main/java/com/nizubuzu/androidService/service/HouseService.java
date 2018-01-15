package com.nizubuzu.androidService.service;

import java.util.List;

import com.nizubuzu.androidService.dto.HouseExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.exception.HouseOperationException;

public interface HouseService {

	/**
	 * 添加房屋
	 * 
	 * @param house
	 * @param houseImg
	 *            房屋介绍图片
	 * @param houseDetailImg
	 *            房屋详情图片
	 * @return
	 */
	HouseExecution addHouse(House house, ImageHolder houseImg,
			List<ImageHolder> houseDetailImg) throws HouseOperationException;

	/**
	 * 更具id查询房屋详细信息
	 * 
	 * @param houseId
	 * @return
	 */
	House queryHouseById(Long houseId);

	/**
	 * 修改房屋信息
	 * 
	 * @param house
	 * @param houseImg
	 * @param houseDetailImg
	 * @return
	 */
	HouseExecution modifyHouseInfo(House house, ImageHolder houseImg,
			List<ImageHolder> houseDetailImg) throws HouseOperationException;

	/**
	 * 分页查询房屋信息
	 * @param house
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	HouseExecution queryHouseList(House house, int pageIndex, int pageSize) throws HouseOperationException;
}
