package com.dzj.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.PersonInfo;

public class PersioninfoTest extends BaseTest {
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Test
	public void testinsertpersion(){
		PersonInfo personInfo=new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setEmail("1212");
		personInfo.setEnableStatus(1);
		personInfo.setName("dzwwwwjj");
		personInfo.setGender("男");
		personInfo.setUserType(1);
		personInfo.setProfileImg("asas");
		personInfo.setLastEditTime(new Date());
		int p=personInfoDao.insertPersonInfo(personInfo);
		System.out.println(personInfo.getUserId());
	}
	
	@Test
	public void testSelct(){
		
		PersonInfo personInfo=personInfoDao.queryPersionInfpById(3L);
		System.out.println(personInfo.getName());
	}
}
