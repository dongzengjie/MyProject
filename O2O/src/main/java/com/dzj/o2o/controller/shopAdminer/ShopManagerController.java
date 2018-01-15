package com.dzj.o2o.controller.shopAdminer;

import java.io.IOException;
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

import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ShopExecution;
import com.dzj.o2o.entity.Area;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.enums.ShopStateEnum;
import com.dzj.o2o.exception.ShopOperationException;
import com.dzj.o2o.service.AreaService;
import com.dzj.o2o.service.ShopCategoryService;
import com.dzj.o2o.service.ShopService;
import com.dzj.o2o.util.CodeUtil;
import com.dzj.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagerController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService categoryService;
	
	
	
	//店铺管理判断是否有shopId没有就重定向到shopList页面
	@RequestMapping(value = "getshopmanagermentinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopManagermentInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId<=0){
			Object currentShopObj=request.getSession().getAttribute("currentShop");//如果请求中没有shopId 那么就从Session中获取当前的shop
			if(currentShopObj==null){
				modelMap.put("redirect", true);
				modelMap.put("url", "/O2O/shopadmin/shoplist");
				
			}else {
				Shop currentShop=(Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else {
			Shop currentShop=new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	
	//获取店铺列表
	@RequestMapping(value = "getshopList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getshopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		////////////////添加登陆功能后需要移除
/*		PersonInfo user=new PersonInfo();
		user.setUserId(2L);
		user.setName("test");
		request.getSession().setAttribute("user", user);*/
		//////////////////
		
		
		PersonInfo	user=(PersonInfo) request.getSession().getAttribute("user");//从session中获取当前用户
		
		long empolyeer=user.getUserId();
		
		try {
			Shop shopcondition = new Shop();
			shopcondition.setOwner(user);
			ShopExecution seExecution=shopService.queryShopList(shopcondition, 0, 100);//这里只传入了ownerid 所以更具ownerid查询tb_shop中的数据
			modelMap.put("shopList", seExecution.getShopList());//将查询到的shoplist数据存入map集合
			modelMap.put("user", user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
		
	}
	

	// 获取区域列表，商品种类列表
	@RequestMapping(value = "getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getshopinitinfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			areaList = areaService.getAreaList();//获取区域信息
			shopCategoryList = categoryService
					.queryShopCategories(new ShopCategory());//获取商铺类别信息

			modelMap.put("areaList", areaList);//区域信息放到map集合
			modelMap.put("shopCategoryList", shopCategoryList);//将商铺类别放到map集合中
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.getMessage());
		}
		return modelMap;
	}

	// 商铺注册
	@RequestMapping(value = "/registshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");//从request中获取shop的信息（json字符串）
		ObjectMapper objectMapper = new ObjectMapper();
		Shop shop = null;
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		try {
			shop = objectMapper.readValue(shopStr, Shop.class);//将shop信息的json字符串转化成shop对象
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//springMVC文件上传处理
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {//检查request中是否有文件上传流
			MultipartRequest multipartRequest = (MultipartRequest) request;

			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 注册商店
		if (shop != null && shopImg != null) {

			
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");//从session中获用户对象
		
			shop.setOwner(owner);

			ShopExecution se;
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				se = shopService.addShop(shop,imageHolder);
						
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//用户可操作的用户列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList ==null || shopList.size() ==0){
						shopList =new ArrayList<Shop>();
					
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {

			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	// 根据shopId查询商铺信息
	@RequestMapping(value = "getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");//从请求中获取shopid
		if (shopId > -1) {
			try {
				Shop shop = shopService.queryByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty ShopId");
		}
		return modelMap;

	}

	// 修改商铺信息
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyshop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper objectMapper = new ObjectMapper();
		Shop shop = null;
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		try {
			shop = objectMapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartRequest multipartRequest = (MultipartRequest) request;

			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		}
		// 修改店铺信息
		if (shop != null && shop.getShopId() != null) {


			ShopExecution se;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);
				}else {
					ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}

				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {

			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺ID");
			return modelMap;
		}

	}
}
