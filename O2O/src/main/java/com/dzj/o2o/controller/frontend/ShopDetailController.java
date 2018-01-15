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

import com.dzj.o2o.dto.ProductExecution;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.service.ProductCategoryService;
import com.dzj.o2o.service.ProductService;
import com.dzj.o2o.service.ShopService;
import com.dzj.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ShopService shopService;

	/**
	 * 获取店铺下的商铺类别
	 */
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listshopdetailpageinfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;

		try {
			if (shopId != -1) {
				shop = shopService.queryByShopId(shopId);
				productCategoryList = productCategoryService
						.queryProductCategoryList(shopId);
				modelMap.put("productCategoryList", productCategoryList);
				modelMap.put("shop", shop);
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "shopId is null");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}

		return modelMap;
	}

	/**
	 * 获取店铺下的商铺信息
	 */
	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");// 获取当前页
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");// 获取页数
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex != -1) && (pageSize != -1)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request,
					"productCategoryId");
			String productName = HttpServletRequestUtil.getString(request,
					"productName");
			Product productCondition = compactProductCondition4Search(shopId,
					productCategoryId, productName);
			try {
				ProductExecution se = productService.queryProductList(
						productCondition, pageIndex, pageSize);
				modelMap.put("productList", se.getProductList());
				modelMap.put("count", se.getCount());
				modelMap.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageindex or pagesize are empty");
		}
		return modelMap;

	}

	private Product compactProductCondition4Search(long shopId,
			long productCategoryId, String productName) {
		Product productCondition = new Product();

		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);

		if (productCategoryId != -1) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
