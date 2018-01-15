package com.dzj.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.ProductImg;

public class ProductImgTest extends BaseTest {

	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void testProductImg(){
		List<ProductImg> imgs=new ArrayList<ProductImg>();
		
		ProductImg productImg1=new ProductImg();
		productImg1.setCreateTime(new Date());
		productImg1.setImgDesc("asassa");
		productImg1.setPriority(1);
		productImg1.setProductId(1L);
		productImg1.setImgAddr("asas");
		
		
		ProductImg productImg2=new ProductImg();
		productImg2.setCreateTime(new Date());
		productImg2.setImgDesc("test");
		productImg2.setPriority(2);
		productImg2.setProductId(1L);
		productImg2.setImgAddr("test");
		imgs.add(productImg2);
		imgs.add(productImg1);
		
		productImgDao.batchinsertProductimg(imgs);
	}
	
	@Test
	public void delete(){
		int e=productImgDao.deleteProductImgByProductId(1L);
		System.out.println(e);
	}
}
