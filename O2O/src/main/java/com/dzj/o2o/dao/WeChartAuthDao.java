package com.dzj.o2o.dao;

import com.dzj.o2o.entity.WeChartAuth;

public interface WeChartAuthDao {
	
	WeChartAuth queryWeChartInfoByOpenId(String openId);
	
	int insertWechartAuth(WeChartAuth weChartAuth);
	
}
