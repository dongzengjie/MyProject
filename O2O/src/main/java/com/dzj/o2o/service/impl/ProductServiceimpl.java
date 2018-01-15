package com.dzj.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.text.normalizer.IntTrie;

import com.dzj.o2o.dao.ProductDao;
import com.dzj.o2o.dao.ProductImgDao;
import com.dzj.o2o.dto.ImageHolder;
import com.dzj.o2o.dto.ProductExecution;
import com.dzj.o2o.entity.Product;
import com.dzj.o2o.entity.ProductImg;
import com.dzj.o2o.enums.ProductStateEnum;
import com.dzj.o2o.exception.ProductOperationException;
import com.dzj.o2o.service.ProductService;
import com.dzj.o2o.util.ImageUtil;
import com.dzj.o2o.util.PageCalculator;
import com.dzj.o2o.util.PathUtil;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productimgList) throws ProductOperationException {

		if (product != null && product.getShop() != null
				&& product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);// 默认是上架
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new ProductOperationException("创建商品失败" + e.toString());
			}

			if (productimgList != null && productimgList.size() > 0) {
				addProuctimgList(product, productimgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}

	// 添加商品详情图
	private void addProuctimgList(Product product,
			List<ImageHolder> productimgList) {
		String destion = PathUtil.getShopImagePath(product.getShop()
				.getShopId());
		List<ProductImg> productlist = new ArrayList<ProductImg>();
		for (ImageHolder productImgholder : productimgList) {
			String path = ImageUtil
					.generateNormalImg(productImgholder, destion);
			ProductImg img = new ProductImg();
			img.setImgAddr(path);
			img.setProductId(product.getProductId());
			img.setCreateTime(new Date());
			productlist.add(img);
		}
		if (productlist.size() > 0) {
			try {
				int effectedNum = productImgDao
						.batchinsertProductimg(productlist);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图失败" + e.toString());
			}
		}

	}

	// 添加商品缩略图
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String destion = PathUtil.getShopImagePath(product.getShop()
				.getShopId());
		String path = ImageUtil.generateThumbnail(thumbnail, destion);
		product.setImgAddr(path);

	}

	// 根据商品id获取商品信息
	public Product getProductById(Long productId)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		return productDao.queryByProductId(productId);
	}

	// 更新商品信息
	@Transactional
	public ProductExecution modifyProduct(Product product,
			ImageHolder thumbnail, List<ImageHolder> productimgList)
			throws ProductOperationException {
		if (product != null && product.getShop() != null
				&& product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct = productDao.queryByProductId(product
						.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileorPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);

			}
			if (productimgList != null && productimgList.size() > 0) {
				deleteProductImg(product.getProductId());
			}
			addProuctimgList(product, productimgList);

			try {
				int effectedNum = productDao.updataProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品失败" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}

	private void deleteProductImg(Long productid) {
		List<ProductImg> productImgs = productImgDao
				.queryProductImgList(productid);
		for (ProductImg productImg : productImgs) {
			ImageUtil.deleteFileorPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productid);

	}

	// 分页查询商品信息
	@Transactional
	public ProductExecution queryProductList(Product productCondition,
			int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculatorRowindex(pageIndex, pageSize);
		List<Product> productList = productDao.queryproductList(
				productCondition, rowIndex, pageSize);
		int count=productDao.querycount(productCondition);
		ProductExecution execution =new ProductExecution();
		if(productList !=null){
			execution.setProductList(productList);
			execution.setCount(count);
			execution.setState(ProductStateEnum.SUCCESS.getState());
		}else {
			execution.setState(ProductStateEnum.EMPTY.getState());
			
		}
		return execution;
	}

}
