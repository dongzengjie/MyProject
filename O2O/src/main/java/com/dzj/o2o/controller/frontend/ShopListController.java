package com.dzj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzj.o2o.dto.ShopExecution;
import com.dzj.o2o.entity.Area;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.service.AreaService;
import com.dzj.o2o.service.ShopCategoryService;
import com.dzj.o2o.service.ShopService;
import com.dzj.o2o.util.HttpServletRequestUtil;


@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	// 获取商铺分类
	@RequestMapping(value = "listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1L) {
			try {
				//根据parentId获取二级店铺类别信息
				ShopCategory shopCategory = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategory.setParent(parent);
				shopCategoryList = shopCategoryService
						.queryShopCategories(shopCategory);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			//获取一级商铺类别
			try {
				shopCategoryList = shopCategoryService
						.queryShopCategories(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			//获取地区信息
			areaList = areaService.getAreaList();
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		modelMap.put("areaList", areaList);
		modelMap.put("success", true);
		return modelMap;

	}

	// 根据条件查询商铺信息
	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");//获取当前页
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");//获取页数
		
		if ((pageIndex > -1) && (pageSize > -1)) {
			
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");//获取parentId
			
			long shopCategoryId = HttpServletRequestUtil.getLong(request,
					"shopCategoryId");//获取商铺类别id
			
			String shopName = HttpServletRequestUtil.getString(request,
					"shopName");//获取商铺名称
			
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");//获取地区id
			
			Shop shopCondition = compactShopCondition4Search(parentId,
					shopCategoryId, areaId, shopName);
			
			ShopExecution  se=shopService.queryShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageindex or pagesize are empty");
		}
		return modelMap;
	}
	
	/**
	 * 多条件组合查询返回shop
	 * @param parentId
	 * @param shopCategoryId
	 * @param areaId
	 * @param shopName
	 * @return
	 */
	
	
	private Shop compactShopCondition4Search(long parentId,
			long shopCategoryId, int areaId, String shopName) {
		Shop shop=new Shop();
		shop.setEnableStatus(1);
		if(parentId != -1){
			ShopCategory shopCategory=new ShopCategory();
			ShopCategory parent=new ShopCategory();
			parent.setShopCategoryId(parentId);
			shopCategory.setParent(parent);
			shop.setShopCategory(shopCategory);
		}
		if(shopCategoryId !=-1){
			ShopCategory shopCategory=new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shop.setShopCategory(shopCategory);
		}
		if(areaId != -1){
			Area area=new Area();
			area.setAreaId(areaId);
			shop.setArea(area);
		}
		if(shopName !=null){
			shop.setShopName(shopName);
		}
		
		return shop;
	}

}
