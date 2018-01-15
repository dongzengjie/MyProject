package com.dzj.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.WeChartAuth;

public class TestWechart extends BaseTest {
	@Autowired
	private WeChartAuthDao weChartAuthDao;
	
	@Test
	public void testinsert(){
		
		WeChartAuth weChartAuth =new WeChartAuth();
		weChartAuth.setOpenId("assa");
		weChartAuth.setCreateTime(new Date());
		PersonInfo info=new PersonInfo();
		info.setUserId(3L);
		weChartAuth.setPersonInfo(info);
	
		
		
		
		weChartAuthDao.insertWechartAuth(weChartAuth);
	}
	
	@Test 
	public void testselect(){
		
        WeChartAuth auth=weChartAuthDao.queryWeChartInfoByOpenId("assa");
       System.out.println( auth.getPersonInfo().getName());
       
	}
}
