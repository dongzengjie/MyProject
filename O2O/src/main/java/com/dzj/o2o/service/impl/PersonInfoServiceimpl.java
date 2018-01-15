package com.dzj.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzj.o2o.dao.PersonInfoDao;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.service.PersionInfoService;

@Service
public class PersonInfoServiceimpl implements PersionInfoService {
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo getPersionById(Long userId) {
		// TODO Auto-generated method stub
		return personInfoDao.queryPersionInfpById(userId);
	}

}
