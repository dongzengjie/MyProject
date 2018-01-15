package com.dzj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.o2o.dao.ProductCategoryDao;
import com.dzj.o2o.dao.ProductDao;
import com.dzj.o2o.dto.ProductCategoryExecution;
import com.dzj.o2o.entity.ProductCategory;
import com.dzj.o2o.enums.ProductCategoryStateEnum;
import com.dzj.o2o.exception.ProductOperationException;
import com.dzj.o2o.service.ProductCategoryService;
@Service
public class ProductCategoryServiceimpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	private ProductDao productDao;

	@Transactional
	public List<ProductCategory> queryProductCategoryList(long shopid) {
	
		return productCategoryDao.queryProductCategoryList(shopid);
	}
	//批量添加商品类别
	public ProductCategoryExecution batchInsetProductCategory(
			List<ProductCategory> productCategory) {
		
		if(productCategory !=null && productCategory.size()>0){
			
			try {
				int effer=productCategoryDao.batchInsetProductCategory(productCategory);
				if(effer<=0	){
					throw new ProductOperationException("插入失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new ProductOperationException("error"+e.getMessage());
			}
			
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
		
	
	}

	@Transactional
	public ProductCategoryExecution deleteProductCategory(
			long productCategoryId, long shopId) {
	
		//TODO 将此商品下的商品类别id值为空
		try {
			int effectNum=productDao.updataPrductCategoryIdToNull(productCategoryId);
			if(effectNum < 0){
				throw new RuntimeException("商品类别删除失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("商品类别删除失败"+e.toString());
		}
		
		try {
			int eff=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(eff<=0){
				throw new ProductOperationException("删除失败");
				
			}else {
				return new  ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductOperationException("deleteProductCategory error" +e.getMessage());
		}
	
	}

}
