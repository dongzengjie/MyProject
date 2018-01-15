package com.dzj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.entity.Shop;

public interface ShopDao {

	int insertShop(Shop shop);// 增加店铺

	int updateShop(Shop shop);// 更新店铺

	Shop queryByShopId(Long shopId);// 更加shopid查询店铺

	// 分页查询
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	//返回查询个数
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	

}
