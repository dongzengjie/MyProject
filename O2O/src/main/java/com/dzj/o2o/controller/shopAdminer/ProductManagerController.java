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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ProductExecution;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.enums.ProductStateEnum;
import com.dzj.o2o.exception.ProductOperationException;
import com.dzj.o2o.service.ProductCategoryService;
import com.dzj.o2o.service.ProductService;
import com.dzj.o2o.util.CodeUtil;
import com.dzj.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagerController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	// 商品详情图片最大上传量
	private static final int IMAFEMAXCOUNT = 6;

	// 添加商品信息
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addproduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ImageHolder thumbnail = null;
		List<ImageHolder> productimgList = new ArrayList<ImageHolder>();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		try {
			product = objectMapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}
		MultipartRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartRequest) request;
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
						.getFile("thumbnail");
				if (thumbnailFile != null) {
					thumbnail = new ImageHolder(
							thumbnailFile.getOriginalFilename(),
							thumbnailFile.getInputStream());
				}

				for (int i = 0; i < IMAFEMAXCOUNT; i++) {
					CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImg != null) {
						ImageHolder holder = new ImageHolder(
								productImg.getOriginalFilename(),
								productImg.getInputStream());
						productimgList.add(holder);
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

		if (product != null && productimgList.size() > 0 && thumbnail != null) {
			try {
				Shop shop = (Shop) request.getSession().getAttribute(
						"currentShop");
			
				product.setShop(shop);

				ProductExecution se = productService.addProduct(product,
						thumbnail, productimgList);
				if (se.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errorMsg", se.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请输入商品信息");
		}
		return modelMap;
	}

	// 根据商品id获取商品信息
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getproductbyid(Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productId > -1) {
			try {
				Product product = productService.getProductById(productId);
				List<ProductCategory> productCategoryList = productCategoryService
						.queryProductCategoryList(product.getShop().getShopId());
				modelMap.put("success", true);
				modelMap.put("product", product);
				modelMap.put("productCategoryList", productCategoryList);
				return modelMap;
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "非法操作");
			return modelMap;
		}

	}

	// 更新商品信息
	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyproduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");

		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ImageHolder thumbnail = null;
		List<ImageHolder> productimgList = new ArrayList<ImageHolder>();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		try {
			product = objectMapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.toString());
			return modelMap;
		}
		MultipartRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartRequest) request;
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
						.getFile("thumbnail");
				if (thumbnailFile != null) {
					thumbnail = new ImageHolder(
							thumbnailFile.getOriginalFilename(),
							thumbnailFile.getInputStream());
				}

				for (int i = 0; i < IMAFEMAXCOUNT; i++) {
					CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImg != null) {
						ImageHolder holder = new ImageHolder(
								productImg.getOriginalFilename(),
								productImg.getInputStream());
						productimgList.add(holder);
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

		if (product != null) {
			try {
				Shop shop = (Shop) request.getSession().getAttribute(
						"currentShop");
			
				product.setShop(shop);

				ProductExecution se = productService.modifyProduct(product,
						thumbnail, productimgList);

				if (se.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errorMsg", se.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请输入商品信息");
		}
		return modelMap;

	}
	
	//分页查询商品信息
	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getproductlistbyshop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize =HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
		if((pageIndex>-1) && (pageSize>-1) && currentShop !=null && currentShop.getShopId() !=null){
			
			long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName=HttpServletRequestUtil.getString(request, "productName");

			Product productCondition=compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
			ProductExecution execution=productService.queryProductList(productCondition, pageIndex, pageSize);
	
			if(execution.getState() ==ProductStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
				modelMap.put("productList", execution.getProductList());
				modelMap.put("count", execution.getCount());
			}else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", execution.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "pagesize or pageindex are empty");
		}
		
		return modelMap;
	}

	private Product compactProductCondition(Long shopId,
			long productCategoryId, String productName) {
		Product productCondition=new Product();
		Shop shop=new Shop();
		
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if(productCategoryId >-1L){
			ProductCategory category=new ProductCategory();
			category.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(category);
		}
		if(productName !=null){
			productCondition.setProductName(productName);
		}
		
		return productCondition;
	}
	
}
