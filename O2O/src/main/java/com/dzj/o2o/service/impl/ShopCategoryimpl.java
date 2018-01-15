package com.dzj.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.o2o.cache.JedisUtil;
import com.dzj.o2o.dao.ShopCategoryDao;
import com.dzj.o2o.entity.ShopCategory;
import com.dzj.o2o.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ShopCategoryimpl implements ShopCategoryService {

	/*@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;
*/
	@Autowired
	private ShopCategoryDao shopCategoryDao;

/*	private static String SCLISTKEY = "shopcategorylist";*/

	public List<ShopCategory> queryShopCategories(ShopCategory category) {

		//String key = null;
	//	List<ShopCategory> shopCategoryList = null;
		//ObjectMapper mapper = new ObjectMapper();
		
		/*if (category == null) {
			key = SCLISTKEY;
		}
		if (category != null) {
			key = SCLISTKEY + "PARENTIDNOTNULL";
		}
		if (category != null
				&& category.getParent().getShopCategoryId() != null) {
			key = SCLISTKEY + category.getParent().getShopCategoryId();
		}
		if (!jedisKeys.exists(key)) {
			shopCategoryList = shopCategoryDao.queryShopCategories(category);
			try {
				String json = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, json);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory()
					.constructParametricType(ArrayList.class,
							ShopCategory.class);
			try {
				shopCategoryList=mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/
		return shopCategoryDao.queryShopCategories(category);
	}

}
