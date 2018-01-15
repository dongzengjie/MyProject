package com.dzj.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzj.o2o.cache.JedisUtil;
import com.dzj.o2o.dao.AreaDao;
import com.dzj.o2o.entity.Area;
import com.dzj.o2o.service.AreaService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AreaSerciveimpl implements AreaService {
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	private static String AREALISTKEY = "arealist";
	
	
	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList() {
		List<Area> listAreas=null;
		String key = AREALISTKEY;
		ObjectMapper mapper = new ObjectMapper();
		if(!jedisKeys.exists(key)){
			listAreas =areaDao.queryArea();
			try {
				String jsonstring=mapper.writeValueAsString(listAreas);
				jedisStrings.set(key, jsonstring);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String jsonstring=jedisStrings.get(key);
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				listAreas=mapper.readValue(jsonstring, javaType);
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
		
		return listAreas;
	}

}
