package com.dzj.o2o.service;

import java.io.InputStream;

import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ShopExecution;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.exception.ShopOperationException;

public interface ShopService {

	ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
	
	Shop queryByShopId(Long shopid) throws ShopOperationException;
	

	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
	
	
	ShopExecution queryShopList(Shop shop, int pageindex ,int pagesize)throws ShopOperationException;
	
	
}
