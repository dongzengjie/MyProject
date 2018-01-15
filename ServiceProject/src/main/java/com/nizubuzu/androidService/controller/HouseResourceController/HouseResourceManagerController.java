package com.nizubuzu.androidService.controller.HouseResourceController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nizubuzu.androidService.dto.HouseResourceExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.entity.PersionInfo;
import com.nizubuzu.androidService.enums.HouseResourceState;
import com.nizubuzu.androidService.service.HouseResourceService;
import com.nizubuzu.androidService.util.CodeUtil;
import com.nizubuzu.androidService.util.HttpServletRequestUtil;
import com.sun.xml.internal.bind.v2.TODO;


@Controller
@RequestMapping("/houseresourceadmin")
public class HouseResourceManagerController {

	@Autowired
	private HouseResourceService houseResourceService;
	
	@RequestMapping(value="/getHouseResourceInfoById",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHouseResourceInfoById(HttpServletRequest request){
		Long houseResourceId=HttpServletRequestUtil.getLong(request, "houseResourceId");
		Map<String, Object> modelMap=new HashMap<String, Object>();
		
		if(houseResourceId>-1){
			HouseResource houseResource =houseResourceService.queryByHouseResourceId(houseResourceId);
			modelMap.put("houseResource", houseResource);
			modelMap.put("success", true);
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty id");
		}
		return modelMap;
		
	}
	
	/**
	 * 判断是否有Id存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHouseManageInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHouseManageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long houseResourceId=HttpServletRequestUtil.getLong(request, "houseResourceId");
		if(houseResourceId<=0){
			Object currentHouseResource=request.getSession().getAttribute("currentHouseResource");
			if(currentHouseResource ==null){
				modelMap.put("redirect", true);
				modelMap.put("url", "/ServiceProject/houseresourceadmin/houseresourcelist");
			}else {
				HouseResource houseresource=(HouseResource) currentHouseResource;
				modelMap.put("redirect", false);
				modelMap.put("houseResourceId", houseresource.getHouseResourceId());
			}
		}else {
			HouseResource currentHouseResource=new HouseResource();
			currentHouseResource.setHouseResourceId(houseResourceId);
			request.getSession().setAttribute("currentHouseResource", currentHouseResource);
			modelMap.put("redirect", false);
		}
		
		return modelMap;
		
	}
	/**
	 * 查询管理员所拥有的房源
	 * 
	 * @author DZJ
	 * 
	 */
	@RequestMapping(value = "/getHouseResourceList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHouseResourceList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// TODO 将来需要移除
		PersionInfo user = new PersionInfo();
		user.setUserId(1L);
		user.setName("dzj");
		//
		request.getSession().setAttribute("user", user);
		PersionInfo userinfo = (PersionInfo) request.getSession().getAttribute(
				"user");

		try {
			HouseResource houseResource = new HouseResource();
			houseResource.setOwner(userinfo);
			HouseResourceExecution houseresource = houseResourceService
					.queryHouseResourceList(0, 100, houseResource);
			modelMap.put("HouseResourceList",
					houseresource.getHouseResourceslist());
			request.getSession().setAttribute("houseResourceList", houseresource.getHouseResourceslist());
			modelMap.put("success", true);
			modelMap.put("user", userinfo);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("msg", e.getMessage());
		}
		return modelMap;

	}

	/**
	 * 添加房源(注册房源)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addHouseResouce", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHouseResouce(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String houseRseourcestr = HttpServletRequestUtil.getString(request,
				"houseResourceStr");
		ObjectMapper objectMapper = new ObjectMapper();
		HouseResource houseResource = null;
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		try {
			houseResource = objectMapper.readValue(houseRseourcestr,
					HouseResource.class);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// springMVC文件上传处理
		CommonsMultipartFile houseResourceImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			houseResourceImg = (CommonsMultipartFile) multipartRequest
					.getFile("houseResourceImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}

		// 注册房源信息
		if (houseResource != null && houseResourceImg != null) {
			HouseResourceExecution houseResourceExecution = null;
			PersionInfo owner = (PersionInfo) request.getSession()
					.getAttribute("user");
			houseResource.setOwner(owner);

			try {
				ImageHolder imageHolder = new ImageHolder(
						houseResourceImg.getOriginalFilename(),
						houseResourceImg.getInputStream());

				houseResourceExecution = houseResourceService
						.insertHouseResource(houseResource, imageHolder);
				if(houseResourceExecution.getState()==HouseResourceState.CHECK.getState()){
					//添加用户可操作的房源列表
					modelMap.put("success", true);
					List<HouseResource> houseResourcesList=(List<HouseResource>) request.getSession().getAttribute("houseResourceList");
					if(houseResourcesList !=null && houseResourcesList.size()>0){
						houseResourcesList=new ArrayList<HouseResource>();
					}
					houseResourcesList.add(houseResourceExecution.getHouseResource());
					request.getSession().setAttribute("houseResourceList", houseResourcesList);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", houseResourceExecution.getStateInfo());
					
				}
				return modelMap;
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "注册失败");
			return modelMap;
		}
		

	}
	
	@RequestMapping(value="/modifyHouseResource",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyHouseResource(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String houseRseourcestr = HttpServletRequestUtil.getString(request,
				"houseResourceStr");
		ObjectMapper objectMapper = new ObjectMapper();
		HouseResource houseResource = null;
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		try {
			houseResource = objectMapper.readValue(houseRseourcestr,
					HouseResource.class);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// springMVC文件上传处理
		CommonsMultipartFile houseResourceImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			houseResourceImg = (CommonsMultipartFile) multipartRequest
					.getFile("houseResourceImg");
		} 
		
		
		// 修改房源信息
		if (houseResource != null ) {
			HouseResourceExecution houseResourceExecution ;
			

			try {
				
				if(houseResourceImg == null){
					houseResourceExecution=houseResourceService.modifyHouseResource(houseResource, null);
				}else {
					ImageHolder thumbnail=new ImageHolder(houseResourceImg.getOriginalFilename(),houseResourceImg.getInputStream());
					houseResourceExecution=houseResourceService.modifyHouseResource(houseResource, thumbnail);
					
					
				}
				if(houseResourceExecution.getState()==HouseResourceState.SUCCESS.getState()){
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", houseResourceExecution.getStateInfo());
				}
				return modelMap;
				
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "注册失败");
			return modelMap;
		}
		
		//return null; 
		
	}

}
