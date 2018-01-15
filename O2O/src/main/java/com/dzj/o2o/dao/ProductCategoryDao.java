package com.dzj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	//更加店铺id查询商品种类
	
		List<ProductCategory> queryProductCategoryList(long shopid);
		
		//批量添加商品类别
		int batchInsetProductCategory(List<ProductCategory> productCategory);
		//删除商品类别
		int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
		
		
}
