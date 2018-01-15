package com.nizubuzu.androidService.dao;

import java.util.List;

import com.nizubuzu.androidService.entity.HouseDetailImg;

public interface HouseImgDetailDao {

	/**
	 * 批量添加详情图片
	 * @param houseDetailImgs
	 * @return
	 */
	int batchinsertHouseDetailImg(List<HouseDetailImg> houseDetailImgs);
	
	/**
	 * 根据房屋id查询详情图片
	 * @param houseId
	 * @return
	 */
	List<HouseDetailImg> queryListHouseDetaiImg(long houseId);
	
	/**
	 * 更具房屋id删除图片
	 * @param houseId
	 * @return
	 */
	int deleteHouseDetailImg(long houseId);
}
