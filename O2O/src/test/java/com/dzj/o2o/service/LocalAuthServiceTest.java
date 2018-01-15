package com.dzj.o2o.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.LocalAuth;
import com.dzj.o2o.entity.PersonInfo;

public class LocalAuthServiceTest extends BaseTest {

	@Autowired
	private LocalAuthService authService;
	
	@Test
	public void modifytest(){
		
		authService.modifyLocalAuthinfo(3L, "aaa", "111", "123123");
	}
	@Test
	public void bintest(){
		LocalAuth localAuth=new LocalAuth();
		localAuth.setUsername("test");
		localAuth.setPassword("123");
		PersonInfo personInfo=new PersonInfo();
		personInfo.setUserId(2L);
		localAuth.setPersonInfo(personInfo);
		
		authService.bindLocalAuth(localAuth);
	}
	
}
