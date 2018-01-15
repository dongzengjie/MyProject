package com.dzj.o2o.dao;

import java.util.List;

import com.dzj.o2o.entity.ProductImg;

public interface ProductImgDao {

	//批量添加商品图片
	int batchinsertProductimg(List<ProductImg> productImgs);
	
	//删除指定商品下的所有图片
	int deleteProductImgByProductId(long productid);
	
	//更具商品id查询所有图片
	List<ProductImg> queryProductImgList(long productid);
}
