package com.dzj.o2o.service;

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
import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ProductExecution;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.Shop;

public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testimg() throws FileNotFoundException{
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
		product.setEnableStatus(1);
		File file=new File("G:/aaa.png");
		InputStream inputStream=new FileInputStream(file);
		ImageHolder holder=new ImageHolder(file.getName(),inputStream);
		File file1=new File("G:/bizhi/329397-106.jpg");
		File file2=new File("G:/bizhi/335853-106.jpg");
		InputStream inputStream1=new FileInputStream(file1);
		InputStream inputStream2=new FileInputStream(file2);
		List<ImageHolder> holders=new ArrayList<ImageHolder>();
		holders.add(new ImageHolder(file1.getName(), inputStream1));
		holders.add(new ImageHolder(file2.getName(), inputStream2));
		ProductExecution e=productService.addProduct(product, holder, holders);
		
		
	}
	@Test
	public void testModify() throws FileNotFoundException{
		Product product=new Product();
		Shop shop=new Shop();
		ProductCategory productCategory=new ProductCategory();
		shop.setShopId(28L);
		productCategory.setProductCategoryId(3L);
		product.setProductId(5L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setCreateTime(new Date());
		product.setProductName("珍珠奶茶updata");
		product.setNormalPrice("22");
		product.setPriority(22);
		product.setEnableStatus(1);
		File file=new File("G:/bizhi/347963-106.jpg");
		InputStream inputStream=new FileInputStream(file);
		ImageHolder holder=new ImageHolder(file.getName(),inputStream);
		File file1=new File("G:/bizhi/317978-106.jpg");
		File file2=new File("G:/bizhi/161517s4ebnehou6zm9ug9.jpg");
		InputStream inputStream1=new FileInputStream(file1);
		InputStream inputStream2=new FileInputStream(file2);
		List<ImageHolder> holders=new ArrayList<ImageHolder>();
		holders.add(new ImageHolder(file1.getName(), inputStream1));
		holders.add(new ImageHolder(file2.getName(), inputStream2));
		ProductExecution e=productService.modifyProduct(product, holder, holders);
	}
}
