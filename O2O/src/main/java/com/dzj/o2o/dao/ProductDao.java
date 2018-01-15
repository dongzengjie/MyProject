package com.dzj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.Product;

public interface ProductDao {

	// 插入商品
	int insertProduct(Product product);

	// 更具productid查询商品

	Product queryByProductId(Long productId);

	// 更新商品信息
	int updataProduct(Product product);

	// 分页查询商品
	List<Product> queryproductList(
			@Param("productCondition") Product productCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	//查询商品总书
	int querycount(@Param("productCondition") Product productCondition);
	
	//将关联的商品种类值为空
	
	/**
	 * 将房屋种类id至为空
	 * @param productCategoryId
	 * @return
	 */
	int updataPrductCategoryIdToNull(long productCategoryId);

}
