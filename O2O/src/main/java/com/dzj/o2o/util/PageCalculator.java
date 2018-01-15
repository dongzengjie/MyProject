package com.dzj.o2o.util;

public class PageCalculator {

	public static int calculatorRowindex(int pageIndex,int pagesize){
		return (pageIndex>0)? (pageIndex-1)*pagesize:0;
	}
}
