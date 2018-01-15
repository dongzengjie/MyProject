package com.dzj.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.o2o.dao.PersonInfoDao;
import com.dzj.o2o.dao.WeChartAuthDao;
import com.dzj.o2o.dto.WechatAuthExecution;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.WeChartAuth;
import com.dzj.o2o.enums.WechatAuthStateEnum;
import com.dzj.o2o.service.WeChartAuthService;

@Service
public class WeChartAuthServiceimpl implements WeChartAuthService {

	@Autowired
	private WeChartAuthDao chartAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	public WeChartAuth getWechatAuthByOpenId(String openId) {

		return chartAuthDao.queryWeChartInfoByOpenId(openId);
	}


	@Transactional
	public WechatAuthExecution regist(WeChartAuth weChartAuth)
			throws RuntimeException {
		if (weChartAuth == null || weChartAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}

		if (weChartAuth.getPersonInfo() != null
				&& weChartAuth.getPersonInfo().getUserId() == null) {
			try {
				PersonInfo personInfo = weChartAuth.getPersonInfo();
				personInfo.setCreateTime(new Date());
				personInfo.setEnableStatus(1);
				
				int effectNum = personInfoDao.insertPersonInfo(personInfo);
				weChartAuth.setPersonInfo(personInfo);
				if(effectNum <=0){
					throw new RuntimeException("添加失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("添加失败"+e.getMessage());
			}
		}
		weChartAuth.setCreateTime(new Date());
		int effectNum=chartAuthDao.insertWechartAuth(weChartAuth);
		if(effectNum<=0){
			throw new RuntimeException("添加失败");
		}else {
			return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, weChartAuth);
		}
		
		
	}

}
