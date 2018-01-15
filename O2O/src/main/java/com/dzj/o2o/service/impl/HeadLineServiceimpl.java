package com.dzj.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzj.o2o.cache.JedisUtil;
import com.dzj.o2o.dao.HeadLineDao;
import com.dzj.o2o.entity.HeadLine;
import com.dzj.o2o.service.HeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HeadLineServiceimpl implements HeadLineService{

	@Autowired
	private HeadLineDao headLineDao;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	private static String AREALISTKEY = "headlist";
	
	
	
	@Override
	public List<HeadLine> getHeadLine(HeadLine headLine)
			throws Exception{
		List<HeadLine> headLines=null;
		String key=AREALISTKEY;
		ObjectMapper mapper=new ObjectMapper();
		if(!jedisKeys.exists(key)){
			headLines=headLineDao.queryHeadLines(headLine);
			String jsonString =mapper.writeValueAsString(headLines);
			jedisStrings.set(key, jsonString);
		}else {
			String jsonstring=jedisStrings.get(key);
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			headLines=mapper.readValue(jsonstring, javaType);
		}
		
		return headLines;
	}

}
