package com.dzj.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dzj.o2o.entity.LocalAuth;

public interface LocalAuthDao {
	// 根据账号密码查询

	LocalAuth queryByUserAndPassword(@Param("userName") String userName,
			@Param("password") String password);

	// 根据用户id查询
	LocalAuth queryByUserId(@Param("userId") long userId);

	// 注册
	int insertLocalAuth(LocalAuth localAuth);

	// 修改密码
	int updataLocalAuth(@Param("userId") Long userId,
			@Param("userName") String userName,
			@Param("password") String password,
			@Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
