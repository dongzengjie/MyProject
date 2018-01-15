package com.dzj.o2o.controller.shopAdminer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopOperation {

	@RequestMapping(value ="/shopoperation" ,method=RequestMethod.GET)
	public String shopoperation(){
		return "shop/shopoperation";
	}
	

	@RequestMapping(value ="/shoplist" ,method=RequestMethod.GET)
	public String shoplist(){
		return "shop/shoplist";
	}
	
	@RequestMapping(value ="/shopmanagement" ,method=RequestMethod.GET)
	public String shopManagement(){
		return "shop/shopmanage";
	}
	
	
	//跳转到商品类别管理页面
	@RequestMapping(value ="/productcategorymanage" ,method=RequestMethod.GET)
	public String productcategorymanage(){
		return "shop/productcategorymanage";
	}
	
	@RequestMapping(value ="/productoperation" ,method=RequestMethod.GET)
	public String productoperation(){
		return "shop/productedit";
	}
	//跳转到商品列表页面
	@RequestMapping(value ="/productmanagement" ,method=RequestMethod.GET)
	public String productmanagement(){
		return "shop/productmanage";
	}
}
