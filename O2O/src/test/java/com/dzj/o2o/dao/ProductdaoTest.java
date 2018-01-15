package com.dzj.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.ProductImg;
import com.dzj.o2o.entity.Shop;

public class ProductdaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	
	/*@Test
	public void testCategort(){
		List<ProductCategory> list=categoryDao.queryProductCategoryList(28L);
		System.out.println(list.size());
		
	}*/
	@Test
	public void testdao(){
		ProductCategory category=new ProductCategory();
		category.setCreateTime(new Date());
		category.setPriority(1);
		category.setShopId(28L);
		category.setProductCategoryName("asasas");
		
		ProductCategory category2=new ProductCategory();
		category2.setCreateTime(new Date());
		category2.setPriority(31);
		category2.setShopId(28L);
		category2.setProductCategoryName("hhhh");
		ProductCategory category3=new ProductCategory();
		category3.setCreateTime(new Date());
		category3.setPriority(12);
		category3.setShopId(40L);
		category3.setProductCategoryName("yyyy");
		
		List<ProductCategory> categories=new ArrayList<ProductCategory>();
		categories.add(category);
		categories.add(category2);
		categories.add(category3);
		
		int se=categoryDao.batchInsetProductCategory(categories);
		System.out.println(se);
		
 	}
	
	@Test
	public void testdelete(){
		int oo=categoryDao.deleteProductCategory(1L, 28L);
		System.out.println(oo);
	}
	
	@Test
	public void insertProduct(){
		Product product=new Product();
		Shop shop=new Shop();
		ProductCategory productCategory=new ProductCategory();
		shop.setShopId(28L);
		productCategory.setProductCategoryId(3L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setCreateTime(new Date());
		product.setProductName("珍珠奶茶");
		product.setNormalPrice("20");
		product.setPriority(20);
		productDao.insertProduct(product);
	}
	
	@Test
	public void testquery(){
		Product product=productDao.queryByProductId(3L);

		System.out.println(product.getProductCategory().getProductCategoryName());
		/*List<ProductImg> imgs=product.getProductImgList();
		for (ProductImg productImg : imgs) {
			System.out.println(productImg.getImgAddr());
		}*/
	}
	@Test
	public void testupdataproduct(){
		Shop shop=new Shop();
		shop.setShopId(28L);
		Product product=new Product();
		product.setShop(shop);
		product.setProductId(1L);
		product.setNormalPrice("11111");
		product.setLastEditTime(new Date());
		
		int w=productDao.updataProduct(product);
		System.out.println(w);
	}
	
	@Test
	public void querylist(){
		Product productCondition=new Product();
		Shop shop =new Shop();
		shop.setShopId(28L);
		productCondition.setShop(shop);
		List<Product> list=productDao.queryproductList(productCondition, 0, 3);
		System.out.println(list.size());
		int q=productDao.querycount(productCondition);
		System.out.println(q);
	}
}
