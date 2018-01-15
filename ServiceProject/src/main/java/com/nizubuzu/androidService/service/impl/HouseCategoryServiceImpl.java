package com.nizubuzu.androidService.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nizubuzu.androidService.dao.HouseCategoryDao;
import com.nizubuzu.androidService.dao.HouseDao;
import com.nizubuzu.androidService.dto.HouseCategoryExecution;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.enums.HouseCategoryState;
import com.nizubuzu.androidService.exception.HouseCategoryOPerationException;
import com.nizubuzu.androidService.service.HouseCategoryService;

@Service
public class HouseCategoryServiceImpl implements HouseCategoryService {

	@Autowired
	private HouseCategoryDao houseCategoryDao;
	@Autowired
	private HouseDao houseDao;
	/**
	 * 更具房源id查询种类
	 */
	@Transactional
	public List<HouseCategory> queryhouseCategoryByHouseResourceId(
			Long houseResourceId) throws HouseCategoryOPerationException {
		List<HouseCategory> list=houseCategoryDao.queryhouseCategoryByHouseResourceId(houseResourceId);

		return list ;
	}

	@Transactional
	public HouseCategoryExecution batchinserthouseCategory(
			List<HouseCategory> houseCategorylist)
			throws HouseCategoryOPerationException {
		
		if(houseCategorylist !=null && houseCategorylist.size()>0){
		
			try {
				int effect=houseCategoryDao.batchinserthouseCategory(houseCategorylist);
				if(effect <0){
					throw new HouseCategoryOPerationException("添加房源种类失败");
				}else {
					return new HouseCategoryExecution(HouseCategoryState.SUCCESS, houseCategorylist);
				}
				
			} catch (Exception e) {
				throw new HouseCategoryOPerationException("error"+e.getMessage());
			}
			
		}else {
			return new HouseCategoryExecution(HouseCategoryState.INSERT_Less);
		}
		
		
		
	}

	@Transactional
	public HouseCategoryExecution deleteByhouseResourceIdAndhouseCategoryId(
			Long houseCategoryId, Long houseResourceId) {
	
		//TODO 将房屋中的房子种类值为空
		if(houseResourceId !=null && houseResourceId>0){
			int effect=houseDao.updateHouseCategoryIdToNull(houseCategoryId);
			if(effect <=0){
				throw new HouseCategoryOPerationException("删除失败");
			}
		}
		
		
		if(houseCategoryId !=null && houseCategoryId>0 && houseResourceId  !=null && houseResourceId >0){
			try {
				int effect=houseCategoryDao.deleteByhouseResourceIdAndhouseCategoryId(houseCategoryId, houseResourceId);
				if(effect <0){
					throw new HouseCategoryOPerationException("删除失败");
				}else {
					return new HouseCategoryExecution(HouseCategoryState.SUCCESS);
				}
			} catch (Exception e) {
				throw new HouseCategoryOPerationException("error"+e.getMessage());
			}
			
			
		}else {
			return new HouseCategoryExecution(HouseCategoryState.INSERT_ERROR);
		}
		
	}

}
