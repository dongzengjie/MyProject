package com.dzj.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.o2o.dao.LocalAuthDao;
import com.dzj.o2o.dto.LocalAuthExecution;
import com.dzj.o2o.entity.LocalAuth;
import com.dzj.o2o.enums.LocalAuthStateEnum;
import com.dzj.o2o.exception.LocalAuthException;
import com.dzj.o2o.service.LocalAuthService;
import com.dzj.o2o.util.MD5;

@Service
public class LocalAuthServiceimpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalAuthByUserandPassword(String username,
			String password) {

		return localAuthDao.queryByUserAndPassword(username,
				MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByuserId(long userId) {
		// TODO Auto-generated method stub
		return localAuthDao.queryByUserId(userId);
	}

	//绑定账号
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUsername() == null
				|| localAuth.getPersonInfo() == null
				|| localAuth.getPersonInfo().getUserId() == null) {

			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		LocalAuth local = localAuthDao.queryByUserId(localAuth.getPersonInfo()
				.getUserId());
		if (local != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectNumber = localAuthDao.insertLocalAuth(localAuth);
			if (effectNumber <= 0) {
				throw new LocalAuthException("绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthException("绑定失败" + e.getMessage());
		}

	}

	@Transactional
	public LocalAuthExecution modifyLocalAuthinfo(Long userId, String username,
			String password, String newpassword) {

		if (userId != null && username != null && password != null
				&& !password.equals(newpassword) && newpassword != null) {
			try {
				int effectNum = localAuthDao.updataLocalAuth(userId, username,
						MD5.getMd5(password), MD5.getMd5(newpassword), new Date());
				if(effectNum <=0){
					throw new LocalAuthException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new LocalAuthException("更新密码失败"+e.getMessage());
			}
		}else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}

		
	}

}
