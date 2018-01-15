package com.dzj.o2o.controller.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzj.o2o.entity.HeadLine;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.service.HeadLineService;
import com.dzj.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	
	@Autowired
	private HeadLineService headLineService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@RequestMapping(value="getmainpageinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getmainpageinfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategories=new ArrayList<ShopCategory>();
		try {
			shopCategories=shopCategoryService.queryShopCategories(null);
			modelMap.put("shopCategoryList", shopCategories);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
		}
		List<HeadLine> headLineList=new ArrayList<HeadLine>();
		try {
			HeadLine headLine=new HeadLine();
			headLine.setEnableStatus(1);
			headLineList=headLineService.getHeadLine(headLine);
			modelMap.put("headLineList", headLineList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
		}
		
		return modelMap;
	}
}
