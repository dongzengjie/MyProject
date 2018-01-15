package com.dzj.o2o.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ProductExecution;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.exception.ProductOperationException;


public interface ProductService {
	// 添加商品信息及图片处理
	ProductExecution addProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productimgList) throws ProductOperationException;
	
	//根据商品id查询商品信息
	Product getProductById(Long productId)throws ProductOperationException;
	
	//修改商品信息
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productimgList) throws ProductOperationException;
	
	//分页查询商品信息
	ProductExecution queryProductList(Product productCondition,int pageIndex,int pageSize);
	
	
}
