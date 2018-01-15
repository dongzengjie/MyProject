package com.dzj.o2o.service;

import com.dzj.o2o.dto.WechatAuthExecution;
import com.dzj.o2o.entity.WeChartAuth;

public interface WeChartAuthService {
	
		WeChartAuth getWechatAuthByOpenId(String openId);
		
		WechatAuthExecution regist(WeChartAuth weChartAuth) throws RuntimeException;
}
