package com.dzj.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{

	@Autowired
	private AreaService areaService;
	
	@Test
	public void test(){
		List<Area> list =areaService.getAreaList();
		for (Area area : list) {
			System.out.println(area.getAreaId());
		}
		
	}
}
