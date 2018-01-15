package com.dzj.o2o.service;

import java.util.List;

import com.dzj.o2o.entity.HeadLine;

public interface HeadLineService {
	//查询头条信息
	List<HeadLine> getHeadLine(HeadLine headLine) throws RuntimeException, Exception;
}
