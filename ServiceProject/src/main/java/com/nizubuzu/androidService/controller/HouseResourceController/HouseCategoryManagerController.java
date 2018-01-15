package com.nizubuzu.androidService.controller.HouseResourceController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nizubuzu.androidService.dto.HouseCategoryExecution;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseCategoryState;
import com.nizubuzu.androidService.service.HouseCategoryService;
import com.nizubuzu.androidService.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/houseresourceadmin")
public class HouseCategoryManagerController {

	@Autowired
	private HouseCategoryService houseCategoryService;
	
	@RequestMapping(value="/queryhousecategorylist",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryhousecategorylist(HttpServletRequest request){
		
		HouseResource houseResource=(HouseResource) request.getSession().getAttribute("currentHouseResource");//从session中获取房源id
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(houseResource !=null && houseResource.getHouseResourceId() >0){
			List<HouseCategory> houseCategoryList=houseCategoryService.queryhouseCategoryByHouseResourceId(houseResource.getHouseResourceId());
			modelMap.put("success", true);
			modelMap.put("houseCategoryList",houseCategoryList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "没有房源id");
			
		}
		return modelMap;
	
		
	}
	
	
	
	/**
	 * 批量添加房屋种类
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addhousecategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addhousecategory(HttpServletRequest request,@RequestBody List<HouseCategory> houseCategoryList){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		
		HouseResource houseResource=(HouseResource) request.getSession().getAttribute("currentHouseResource");//从session中获取房源id
		if(houseCategoryList!=null && houseCategoryList.size()>0){
			for (HouseCategory houseCategory : houseCategoryList) {
				houseCategory.setHouseResourceId(houseResource.getHouseResourceId());
			}
			HouseCategoryExecution categoryExecution=houseCategoryService.batchinserthouseCategory(houseCategoryList);
			if(categoryExecution.getState()==HouseCategoryState.SUCCESS.getState()){
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errorMsg","error :"+ categoryExecution.getStateInfo());
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请添加至少一种商品");
		}
		
		return modelMap;	
	}
	
	
	/**
	 * 删除房屋种类
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deletehousecategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletehousecategory(HttpServletRequest request,Long houseCategoryId){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(houseCategoryId !=null && houseCategoryId>0){
			HouseResource houseResource=(HouseResource) request.getSession().getAttribute("currentHouseResource");//从session中获取房源id
			HouseCategoryExecution categoryExecution=houseCategoryService.deleteByhouseResourceIdAndhouseCategoryId(houseCategoryId, houseResource.getHouseResourceId());
			if(categoryExecution.getState()==HouseCategoryState.SUCCESS.getState()){
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
			}
		}else {
			modelMap.put("success", false);
			
		}
	
		
		return modelMap;	
	}
	
	
}
