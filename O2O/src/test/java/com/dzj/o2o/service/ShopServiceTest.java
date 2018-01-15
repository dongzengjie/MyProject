package com.dzj.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.dto.ShopExecution;
import com.dzj.o2o.entity.Area;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {

	@Autowired 
	private ShopService shopService;
	@Autowired
	private ShopCategoryService categoryService;
	
	@Test
	public void test(){
		/*ShopCategory category=new ShopCategory();
		ShopCategory parent=new ShopCategory();
		parent.setShopCategoryId(1L);
		category.setParent(parent);
		
		ShopCategory category111=new ShopCategory();
		category111.setParent(parent);
		category111.setCreateTime(new Date());
		List<ShopCategory> list =categoryService.queryShopCategories(category111);
		for (ShopCategory shopCategory : list) {
			System.out.println(shopCategory.getShopCategoryName());
		}*/
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		shopCategoryList=categoryService.queryShopCategories(new ShopCategory());
		for (ShopCategory shopCategory : shopCategoryList) {
			System.out.println(shopCategory.getShopCategoryName());
		}
	}
	
/*	@Test
	public void testShopService() throws FileNotFoundException{
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(2L);
		area.setAreaId(4);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setShopImg("te3st");
		
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File file=new File("G:/aaa.png");
		InputStream inputStream=new FileInputStream(file);
		ShopExecution se=shopService.addShop(shop, inputStream, file.getName());
	
		System.out.println(se.getStateInfo());
		
	}*/
	
	
/*	@Test
	public void testModifyShopService() throws FileNotFoundException{
		Shop shop =shopService.queryByShopId(40L);
		shop.setShopName("修aaa改后");
		File file=new File("G:/bizhi/qwe.jpg");
		InputStream inputStream=new FileInputStream(file);
		ShopExecution se=shopService.modifyShop(shop, inputStream, file.getName());
		System.out.println(se.getShop().getShopImg());
		
		
		
	}*/
	@Test
	public void testservice(){
		Shop shop=new Shop();
		ShopCategory category=new ShopCategory();
		category.setShopCategoryId(1L);
		shop.setShopCategory(category);
		ShopExecution sExecution=shopService.queryShopList(shop, 0,3);
		System.out.println(sExecution.getShopList().size());
	}
}
