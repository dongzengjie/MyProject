package com.dzj.o2o.dao;

import com.dzj.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	
	PersonInfo queryPersionInfpById(long userId);
	
	int insertPersonInfo(PersonInfo personInfo);
}
