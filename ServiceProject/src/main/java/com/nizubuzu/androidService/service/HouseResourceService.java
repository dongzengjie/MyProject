package com.nizubuzu.androidService.service;

import java.util.List;

import com.nizubuzu.androidService.dto.HouseResourceExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.HouseResource;

public interface HouseResourceService {

	/*HouseResource queryByOwnerId*/
	
	HouseResourceExecution insertHouseResource(HouseResource houseResource,ImageHolder imageHolder);
	
	/**
	 * 分页查询房源信息
	 * @param Pageindex
	 * @param PageSize
	 * @param houseResource
	 * @return
	 */
	HouseResourceExecution queryHouseResourceList(int Pageindex,int PageSize,HouseResource houseResource);
	
	/**
	 * 修改信息
	 */
	HouseResourceExecution modifyHouseResource(HouseResource houseResource,ImageHolder thumbnail);
	
	/**
	 * 根据id查询房源信息
	 */
	HouseResource queryByHouseResourceId(Long houseResourceId);
}
