package com.dzj.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.Area;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.entity.ShopCategory;

public class ShopTest extends BaseTest{

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopCategoryDao categoryDao;
	
	@Test
	public void queryshopcategory(){
		List<ShopCategory> categories=categoryDao.queryShopCategories(null);
		System.out.println(categories.size());
	}
	
/*	@Test
	public void shopinsertTest(){
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
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
		
	}
	*/
/*	@Test
	public void shopupdataTest(){
		Shop shop = new Shop();
		shop.setShopId(28L);		
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum=shopDao.updateShop(shop);
 		assertEquals(1, effectedNum);
	}*/
	@Test
	public void testQueryall(){
		Shop shop=shopDao.queryByShopId(40L);
		System.out.println(shop.getShopName());
	
	}
	
	@Test
	public void testaa(){
		Shop shopcondition=new Shop();
		PersonInfo info=new PersonInfo();
		info.setUserId(2L);
		shopcondition.setOwner(info);
		List<Shop> list=shopDao.queryShopList(shopcondition,0,7);
		for (Shop shop : list) {
			System.out.println(shop.getShopId());
		}
		int p=shopDao.queryShopCount(shopcondition);
		System.out.println("个数"+p);
		
	}
	
	@Test
	public void testcategory(){
		Shop shop=new Shop();
		Shop shop3=new Shop();
		ShopCategory shopCategory=new ShopCategory();
		ShopCategory parentCategory=new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		shopCategory.setParent(parentCategory);
		shop.setShopCategory(shopCategory);
		List<Shop> list=shopDao.queryShopList(shop3, 0, 999);
		for (Shop shop2 : list) {
			System.out.println(shop2.getShopName());
		}
	}
}
