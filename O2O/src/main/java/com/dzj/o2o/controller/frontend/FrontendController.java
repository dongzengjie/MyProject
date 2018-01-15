package com.dzj.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	@RequestMapping(value ="/index" ,method=RequestMethod.GET)
	public String index(){
		return "frontend/index";
		
	}
	
	@RequestMapping(value ="/shoplist" ,method=RequestMethod.GET)
	public String shoplist(){
		return "frontend/shoplist";
		
	}
	@RequestMapping(value ="/shopdetail" ,method=RequestMethod.GET)
	public String shopdetail(){
		return "frontend/shopdetail";
		
	}
	
	@RequestMapping(value ="/productdetail" ,method=RequestMethod.GET)
	public String productdetail(){
		return "frontend/productdetail";
		
	}
}
