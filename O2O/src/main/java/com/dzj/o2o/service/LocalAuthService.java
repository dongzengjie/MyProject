package com.dzj.o2o.service;

import com.dzj.o2o.dto.LocalAuthExecution;
import com.dzj.o2o.entity.LocalAuth;
import com.dzj.o2o.exception.LocalAuthException;

public interface LocalAuthService {

	LocalAuth getLocalAuthByUserandPassword(String username, String password);

	LocalAuth getLocalAuthByuserId(long userId);

	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthException;

	LocalAuthExecution modifyLocalAuthinfo(Long userId, String username,
			String password, String newpassword);

}
