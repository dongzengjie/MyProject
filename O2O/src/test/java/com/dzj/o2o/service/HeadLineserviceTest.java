package com.dzj.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dzj.o2o.BaseTest;
import com.dzj.o2o.entity.HeadLine;

public class HeadLineserviceTest extends BaseTest {
	@Autowired
	private HeadLineService headLineService;
	
	@Test
	public void testheadline(){
		try {
			HeadLine headLinea=new HeadLine();
			List<HeadLine> headLines=headLineService.getHeadLine(headLinea);
			for (HeadLine headLine : headLines) {
				System.out.println(headLine.getLineImg());
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
