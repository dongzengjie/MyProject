package com.dzj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.ShopCategory;

public interface ShopCategoryDao {

	List<ShopCategory> queryShopCategories(
			@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
	
	

}
