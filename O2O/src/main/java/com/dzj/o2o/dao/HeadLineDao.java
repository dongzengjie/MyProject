package com.dzj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.HeadLine;

public interface HeadLineDao {
	//查询头条信息
	List<HeadLine> queryHeadLines(@Param("headLine")HeadLine headLine);
}
