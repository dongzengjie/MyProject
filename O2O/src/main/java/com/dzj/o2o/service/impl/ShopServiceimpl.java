package com.dzj.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.o2o.dao.ShopDao;
import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ShopExecution;
import com.dzj.o2o.entity.Shop;
import com.dzj.o2o.enums.ShopStateEnum;
import com.dzj.o2o.exception.ShopOperationException;
import com.dzj.o2o.service.ShopService;
import com.dzj.o2o.util.ImageUtil;
import com.dzj.o2o.util.PageCalculator;
import com.dzj.o2o.util.PathUtil;
import com.mysql.fabric.xmlrpc.base.Data;

@Service
public class ShopServiceimpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int insertinfo = shopDao.insertShop(shop);
			if (insertinfo <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (thumbnail.getImage() != null) {
					try {
						addimg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("添加图片失败");
					}
					int info = shopDao.updateShop(shop);
					if (info <= 0) {
						throw new ShopOperationException("更新失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("添加店铺失败");
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addimg(Shop shop, ImageHolder thumbnail) {
		String destion = PathUtil.getShopImagePath(shop.getShopId());
		String path = ImageUtil.generateThumbnail(thumbnail,destion);
				
		shop.setShopImg(path);

	}

	@Transactional
	public Shop queryByShopId(Long shopid) {

		return shopDao.queryByShopId(shopid);
	}

	@Transactional
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);

		} else {
			// 判断是否要处理图片
			try {
				if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileorPath(tempShop.getShopImg());
					}
					addimg(shop, thumbnail);
				}
				// 更新店铺信息
				shop.setLastEditTime(new Date());
				int eff = shopDao.updateShop(shop);
				if (eff <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
			
				throw new ShopOperationException("modifyerror"+e.getMessage());
			}
		
		}

	}

	
	//分页查询
	@Transactional
	public ShopExecution queryShopList(Shop shop, int pageindex, int pagesize)
			throws ShopOperationException {
		int rowIndex=PageCalculator.calculatorRowindex(pageindex, pagesize);
		List<Shop> shopList=shopDao.queryShopList(shop, rowIndex, pagesize);
		int count=shopDao.queryShopCount(shop);
		ShopExecution seExecution=new ShopExecution();
		if(shopList !=null){
			seExecution.setShopList(shopList);
			seExecution.setCount(count);
		}else {
			seExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return seExecution;
	}

}
