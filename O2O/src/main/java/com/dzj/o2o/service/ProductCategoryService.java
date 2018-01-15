package com.dzj.o2o.service;

import java.util.List;

import com.dzj.o2o.dto.ProductCategoryExecution;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.exception.ProductOperationException;

public interface ProductCategoryService {

	//更具shopid查询商品
	List<ProductCategory> queryProductCategoryList(long shopid);
	//批量添加
	ProductCategoryExecution batchInsetProductCategory(List<ProductCategory> ProductCategory) throws ProductOperationException;
	
	//更加商品类别id 和shopid 删除
	
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductOperationException;
}
