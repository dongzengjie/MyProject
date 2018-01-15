package com.dzj.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.cache.JedisUtil;
import com.dzj.o2o.entity.HeadLine;

public class HeadLineTest extends BaseTest{

	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	private static String AREALISTKEY = "headlist";
	
	@Test
	public void testqueryHeadLine(){
		List<HeadLine>headLines=headLineDao.queryHeadLines(new HeadLine());
		System.out.println(headLines.size());
	}
}
