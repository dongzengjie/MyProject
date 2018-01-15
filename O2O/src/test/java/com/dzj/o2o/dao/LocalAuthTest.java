package com.dzj.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.LocalAuth;
import com.mysql.fabric.xmlrpc.base.Data;

public class LocalAuthTest extends BaseTest {
	
	@Autowired
	private LocalAuthDao localAuthDao;
	
	@Test
	public void testQueryBynameandpass(){
		
		LocalAuth aa=localAuthDao.queryByUserAndPassword("aaa", "123123");
		System.out.println(aa.getPersonInfo().getName());;
	}
	
	@Test
	public void testByuserId(){
		
		LocalAuth aa=localAuthDao.queryByUserId(3L);
		System.out.println(aa.getPersonInfo().getName());
	}
	
	@Test
	public void insertinfoTest(){
		
		LocalAuth localAuth=new LocalAuth();
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		localAuth.setUserId(3L);
		localAuth.setUsername("aaa");
		localAuth.setPassword("123123");
		
		localAuthDao.insertLocalAuth(localAuth);
	}
	
	@Test
	public void updataTest(){
		
		int a=localAuthDao.updataLocalAuth(3L, "aaa", "111", "1211", new Date());
		System.out.println(a);
	}
}
