package com.dzj.o2o.controller.shopAdminer;

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

import com.dzj.o2o.dto.ProductCategoryExecution;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.enums.ProductCategoryStateEnum;
import com.dzj.o2o.exception.ProductOperationException;
import com.dzj.o2o.service.ProductCategoryService;
import com.dzj.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	
	
	//更加商品类别id 和shopid 删除
	
		@RequestMapping(value = "removeprodutcategory",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> removeprodutcategory(Long productCategoryId,HttpServletRequest request){
			Map<String, Object> modelMap = new HashMap<String, Object>();
			if(productCategoryId !=null && productCategoryId>0){
				try {
					Shop currentshopShop=(Shop) request.getSession().getAttribute("currentShop");
					ProductCategoryExecution sExecution=productCategoryService.deleteProductCategory(productCategoryId, currentshopShop.getShopId());
					if(sExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
						modelMap.put("success", true);
					}else {
						modelMap.put("success", false);
						modelMap.put("errorMsg",
								sExecution.getStateInfo());
					}
					
				} catch (Exception e) {
					modelMap.put("success", false);
					modelMap.put("errorMsg", e.getMessage());
					return modelMap;
				}
			}
			return modelMap;
		}

	@RequestMapping(value = "getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getproductcategorylist(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentshop = (Shop) request.getSession().getAttribute(
				"currentShop");
		List<ProductCategory> list = null;
		if (currentshop != null && currentshop.getShopId() > 0) {
			list = productCategoryService.queryProductCategoryList(currentshop
					.getShopId());
			modelMap.put("success", true);
			modelMap.put("productcategorylist", list);

		} else {
			ProductCategoryExecution sExecution = new ProductCategoryExecution();
			modelMap.put("success", false);
			modelMap.put("errorMsg",
					ProductCategoryStateEnum.INNER_ERROR.getStateInfo());
			modelMap.put("errorCode",
					ProductCategoryStateEnum.INNER_ERROR.getState());
		}

		return modelMap;
	}

	@RequestMapping(value = "addproductcategorys")
	@ResponseBody
	public Map<String, Object> addproductcategorys(
			@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentshop = (Shop) request.getSession().getAttribute(
				"currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentshop.getShopId());
		}
		if(productCategoryList.size()>0 && productCategoryList !=null){
			try {
				ProductCategoryExecution ec=productCategoryService.batchInsetProductCategory(productCategoryList);
				if(ec.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errorMsg",ec.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
				return modelMap;
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请输入至少一种商品");
		}
				return modelMap;

	}

}
