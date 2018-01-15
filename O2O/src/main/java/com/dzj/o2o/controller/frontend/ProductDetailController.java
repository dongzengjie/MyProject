package com.dzj.o2o.controller.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzj.o2o.entity.Product;
import com.dzj.o2o.service.ProductService;
import com.dzj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/getproductdetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getproductdetail(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		long productId=HttpServletRequestUtil.getLong(request, "productId");
		Product product=null;
	
		if(productId !=-1){
			try {
				product=productService.getProductById(productId);
				modelMap.put("success", true);
				modelMap.put("product", product);
				
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "productId is null");
		}
		
		return modelMap;
	}
}
