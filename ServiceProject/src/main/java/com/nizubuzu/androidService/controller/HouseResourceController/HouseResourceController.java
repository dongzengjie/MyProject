package com.nizubuzu.androidService.controller.HouseResourceController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/houseresourceadmin")
public class HouseResourceController {

	/**
	 * 跳转到房源列表
	 * @return
	 */
	@RequestMapping(value="/houseresourcelist",method=RequestMethod.GET)
	public String HouseResourceList(){
		return "houseResource/houseResourcelist";
		
	}
	
	/**
	 * 跳转到房源注册页面
	 * @return
	 */
	@RequestMapping(value="/toRegistHouseResource",method=RequestMethod.GET)
	public String toRegistHouseResource(){
		return "houseResource/houseResourceoperation";
		
	}
	/**
	 * 跳转房源信息操作列表
	 */
	
	@RequestMapping(value="/toHouseResourceManage",method=RequestMethod.GET)
	public String toHouseResourceManage(){
		return "houseResource/houseResourcemanage";
		
	}
	/**
	 * 修改房源信息页面
	 */
	
	@RequestMapping(value="/toupdateHouseResource",method=RequestMethod.GET)
	public String toupdateHouseResource(){
		return "houseResource/houseResourceUpdateoperation";
		
	}
	/**
	 * 房屋分类页面
	 */
	@RequestMapping(value="/tohousecategorylist",method=RequestMethod.GET)
	public String tohousecategorylist(){
		return "houseResource/houseCategorymanage";
		
	}
	
	@RequestMapping(value="/tohouselist",method=RequestMethod.GET)
	public String tohouselist(){
		return "houseResource/housemanage";
		
	}
	
	/**
	 * 房屋编辑页面
	 * @return
	 */
	@RequestMapping(value="/tohouseedit",method=RequestMethod.GET)
	public String tohouseedit(){
		return "houseResource/houseedit";
		
	}
	
	/**
	 * 房屋添加页面
	 * @return
	 */
	@RequestMapping(value="/tohouseadd",method=RequestMethod.GET)
	public String tohouseadd(){
		return "houseResource/houseadd";
		
	}
}
