package com.nizubuzu.androidService.controller.HouseResourceController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nizubuzu.androidService.dto.HouseExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseState;
import com.nizubuzu.androidService.service.HouseCategoryService;
import com.nizubuzu.androidService.service.HouseService;
import com.nizubuzu.androidService.util.CodeUtil;
import com.nizubuzu.androidService.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/houseresourceadmin")
public class HouseManagerController {

	@Autowired
	private HouseService houseService;
	@Autowired
	private HouseCategoryService houseCategoryService;

	// 房屋详情图片最大上传量
	private static final int IMAFEMAXCOUNT = 6;

	/**
	 * 根本HouseResourceId分页查询House信息,需要从前端获取HouseResourceId，pageSize,pageIndex
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/gethouselistbyhouseresourceid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> gethouselistbyhouseresourceid(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		HouseResource currentHouseResource = (HouseResource) request
				.getSession().getAttribute("currentHouseResource");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		if (pageSize > -1 && pageIndex > -1 && currentHouseResource != null
				&& currentHouseResource.getHouseResourceId() != null) {
			long houseCategoryId = HttpServletRequestUtil.getLong(request,
					"houseCategoryId");
			String houseName = HttpServletRequestUtil.getString(request,
					"houseName");
			House houseCondation = compactHouseCondition(
					currentHouseResource.getHouseResourceId(), houseCategoryId,
					houseName);

			HouseExecution houseExecution = houseService.queryHouseList(
					houseCondation, pageIndex, pageSize);
			if (houseExecution.getState() == HouseState.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("houseList", houseExecution.getHouselist());
				modelMap.put("count", houseExecution.getCount());
			} else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", houseExecution.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "pageSize or pageIndex are NULL");
		}

		return modelMap;

	}

	private House compactHouseCondition(Long houseResourceId,
			Long houseCategoryId, String houseName) {
		House houseCondition = new House();
		HouseResource houseResource = new HouseResource();
		houseResource.setHouseResourceId(houseResourceId);
		houseCondition.setHouseResource(houseResource);

		if (houseCategoryId > -1L) {
			HouseCategory houseCategory = new HouseCategory();
			houseCategory.setHouseCategoryId(houseCategoryId);
		}
		if (houseName != null) {
			houseCondition.setHouseName(houseName);
		}

		return houseCondition;

	}
	/**
	 * 修改信息
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/modifyhouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyhouse(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");

		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		ImageHolder houseimg = null;
		List<ImageHolder> houseListImg = new ArrayList<ImageHolder>();
		House house = null;
		String houseStr = HttpServletRequestUtil.getString(request, "houseStr");
		try {
			house = objectMapper.readValue(houseStr, House.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}

		MultipartRequest multipartRequest = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartRequest) request;
				CommonsMultipartFile houseimgFile = (CommonsMultipartFile) multipartRequest
						.getFile("houseimg");
				if (houseimgFile != null) {
					houseimg = new ImageHolder(
							houseimgFile.getOriginalFilename(),
							houseimgFile.getInputStream());
				}

				for (int i = 0; i < IMAFEMAXCOUNT; i++) {
					CommonsMultipartFile houseListImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("houseListImg" + i);
					if (houseListImgFile != null) {
						ImageHolder holder = new ImageHolder(
								houseListImgFile.getOriginalFilename(),
								houseListImgFile.getInputStream());
						houseListImg.add(holder);
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}

		if (house != null) {
			HouseResource currentHouseResource = (HouseResource) request
					.getSession().getAttribute("currentHouseResource");
			house.setHouseResource(currentHouseResource);
			try {
			/*	if (houseimg == null && houseListImg != null) {
					houseExecution = houseService.modifyHouseInfo(house, null,
							houseListImg);
				} else if (houseListImg == null && houseimg != null) {
					houseExecution = houseService.modifyHouseInfo(house,
							houseimg, null);
				} else if (houseimg == null && houseListImg == null) {
					houseExecution = houseService.modifyHouseInfo(house, null,
							null);
				} else {
					houseExecution = houseService.modifyHouseInfo(house,
							houseimg, houseListImg);
				}*/
				HouseExecution houseExecution = houseService.modifyHouseInfo(
						house, houseimg, houseListImg);
				if (houseExecution.getState() == HouseState.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errorMsg", houseExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
				return modelMap;
			}
		}

		return modelMap;

	}

	/**
	 * 添加房源信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addhouse")
	@ResponseBody
	public Map<String, Object> addhouse(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ImageHolder houseimg = null;
		List<ImageHolder> houseListImg = new ArrayList<ImageHolder>();
		House house = null;
		String houseStr = HttpServletRequestUtil.getString(request, "houseStr");
		try {
			house = objectMapper.readValue(houseStr, House.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}

		MultipartRequest multipartRequest = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartRequest) request;
				CommonsMultipartFile houseimgFile = (CommonsMultipartFile) multipartRequest
						.getFile("houseimg");
				if (houseimgFile != null) {
					houseimg = new ImageHolder(
							houseimgFile.getOriginalFilename(),
							houseimgFile.getInputStream());
				}
				for (int i = 0; i < IMAFEMAXCOUNT; i++) {
					CommonsMultipartFile houseListImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("houseListImg" + i);
					if (houseListImgFile != null) {
						ImageHolder holder = new ImageHolder(
								houseListImgFile.getOriginalFilename(),
								houseListImgFile.getInputStream());
						houseListImg.add(holder);
					} else {
						break;
					}
				}

			} else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}

		if (house != null && houseimg != null && houseListImg.size() > 0) {
			HouseResource currentHouseResource = (HouseResource) request
					.getSession().getAttribute("currentHouseResource");
			house.setHouseResource(currentHouseResource);
			HouseExecution houseExecution = houseService.addHouse(house,
					houseimg, houseListImg);
			if (houseExecution.getState() == HouseState.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", houseExecution.getStateInfo());
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "上传项为空");
			return modelMap;
		}

		return modelMap;

	}

	/**
	 * 根据房屋id查询房屋信息
	 * 
	 * @param houseId
	 * @return
	 */
	@RequestMapping(value="/gethousebyid")
	@ResponseBody
	public Map<String, Object> gethousebyid(Long houseId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (houseId > -1L) {
			try {
				House house = houseService.queryHouseById(houseId);
				List<HouseCategory> houseCategoryList = houseCategoryService
						.queryhouseCategoryByHouseResourceId(house
								.getHouseResource().getHouseResourceId());
				modelMap.put("success", true);
				modelMap.put("house", house);
				modelMap.put("houseCategoryList", houseCategoryList);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("house", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "房屋id为空");

		}

		return modelMap;

	}

}
