package com.nizubuzu.androidService.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nizubuzu.androidService.dao.HouseResourceDao;
import com.nizubuzu.androidService.dto.HouseResourceExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseResourceState;
import com.nizubuzu.androidService.exception.HouseResourceOperationException;
import com.nizubuzu.androidService.service.HouseResourceService;
import com.nizubuzu.androidService.util.ImageUtil;
import com.nizubuzu.androidService.util.PageCalculator;
import com.nizubuzu.androidService.util.PathUtil;

@Service
public class HouseResourceServiceImpl implements HouseResourceService {

	@Autowired
	private HouseResourceDao houseResourceDao;
	
	/**
	 * 插入房源信息
	 */
	@Transactional
	public HouseResourceExecution insertHouseResource(
			HouseResource houseResource, ImageHolder imageHolder) {
		if(houseResource ==null){
			return new HouseResourceExecution(HouseResourceState.NULL_HOUSERESOURCE);
		}
		
		try {
			houseResource.setEnableStatus(0);//审核状态
			houseResource.setCreateTime(new Date());
			houseResource.setLastEditTime(new Date());
			int successInfo=houseResourceDao.insertHouseResource(houseResource);
			if(successInfo<=0){
				throw new HouseResourceOperationException("添加房源失败");
			}else {
				try {
					String path= addimg(houseResource, imageHolder);
					houseResource.setHouseResourceImg(path);
				} catch (Exception e) {
					throw new HouseResourceOperationException("添加图片失败");
				}
				int info=houseResourceDao.updataHouseResource(houseResource);
				if(info<=0){
					throw new HouseResourceOperationException("添加房源失败");
				}
			}
			
		} catch (Exception e) {
			throw new HouseResourceOperationException("添加房源失败");
		}
		
		return new HouseResourceExecution(HouseResourceState.CHECK, houseResource);
	}
	
	private String addimg(HouseResource houseResource,ImageHolder holder){
		String destion=PathUtil.getHouseResourceImagePath(houseResource.getHouseResourceId());
		String path = ImageUtil.generateThumbnail(holder, destion);
		return path;
		
	}

	/**
	 * 分页查询房源信息
	 */
	@Transactional
	public HouseResourceExecution queryHouseResourceList(int Pageindex,
			int PageSize, HouseResource houseResource) {
		int rowIndex=PageCalculator.calculatorRowindex(Pageindex, PageSize);
		List<HouseResource> houseResourcesList=houseResourceDao.queryHouseResourceList(houseResource, Pageindex, PageSize);
		int count =houseResourceDao.queryHouseResouceCount(houseResource);
		HouseResourceExecution houseResourceExecution =new HouseResourceExecution();
		if(houseResourcesList !=null){
			houseResourceExecution.setCount(count);
			houseResourceExecution.setHouseResourceslist(houseResourcesList);
		}else {
			houseResourceExecution.setState(HouseResourceState.INSERT_ERROR.getState());
		}
		
		return houseResourceExecution;
	}

	/**
	 * 修改房源信息
	 */
	@Transactional
	public HouseResourceExecution modifyHouseResource(
			HouseResource houseResource,ImageHolder thumbnail) {
		if(houseResource !=null && houseResource.getHouseResourceId() !=null){
			try {
				//修改图片
				if(thumbnail !=null && thumbnail.getImage() !=null && thumbnail.getImageName() !=null && !"".equals(thumbnail.getImageName())){
					HouseResource houseResourceGetId=houseResourceDao.queryByHouseResourceId(houseResource.getHouseResourceId());
					String imgaddr=houseResourceGetId.getHouseResourceImg();
					ImageUtil.deleteFileorPath(imgaddr);
					String path=addimg(houseResource, thumbnail);
					houseResource.setHouseResourceImg(path);
				}
				//修改房源信息
				houseResource.setLastEditTime(new Date());
				int updateinfo=houseResourceDao.updataHouseResource(houseResource);
				if(updateinfo >0){
					houseResource=houseResourceDao.queryByHouseResourceId(houseResource.getHouseResourceId());
					return new HouseResourceExecution(HouseResourceState.SUCCESS, houseResource);
				}else {
					return new HouseResourceExecution(HouseResourceState.INSERT_ERROR);
				}
				
				
				
			} catch (Exception e) {
				throw new HouseResourceOperationException("modifyError"+e.getMessage());
			}
		}else {
			return new HouseResourceExecution(HouseResourceState.NULL_HOUSERESOURCEID);
		}
		
		
	}
	/**
	 * 根据id查询房源信息
	 */
	public HouseResource queryByHouseResourceId(Long houseResourceId) {
		
		return houseResourceDao.queryByHouseResourceId(houseResourceId);
	}
	
	
	
	

}
