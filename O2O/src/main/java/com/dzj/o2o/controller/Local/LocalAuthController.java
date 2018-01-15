package com.dzj.o2o.controller.Local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzj.o2o.dto.LocalAuthExecution;
import com.dzj.o2o.entity.LocalAuth;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.enums.LocalAuthStateEnum;
import com.dzj.o2o.exception.LocalAuthException;
import com.dzj.o2o.service.LocalAuthService;
import com.dzj.o2o.util.CodeUtil;
import com.dzj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value="local",method={RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {

	@Autowired
	private LocalAuthService localAuthService;
	
	
	//登出  注销session
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
	

	//登陆
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checklogin(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean needVerify=HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "验证码错误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		if(userName !=null && password !=null){
			LocalAuth localAuth=localAuthService.getLocalAuthByUserandPassword(userName, password);
			if(localAuth !=null){
				modelMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			}else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", "账号或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请输入账号密码");
		}
		
		return modelMap;
	}
	
	
	// 修改密码
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changepassword(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "验证码错误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newpassword = HttpServletRequestUtil.getString(request,
				"newpassword");
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (userName != null && password != null && user != null
				&& user.getUserId() != null && !password.equals(newpassword)
				&& newpassword != null) {
			try {
				LocalAuth localAuth=localAuthService.getLocalAuthByuserId(user.getUserId());
				if(localAuth ==null && !localAuth.getUsername().equals(userName)){
					modelMap.put("success", false);
					modelMap.put("errorMsg", "输入的账号不是本次登陆的账号");
					return modelMap;
				}
				LocalAuthExecution execution=localAuthService.modifyLocalAuthinfo(user.getUserId(), userName, password, newpassword);
				if(execution.getState()==LocalAuthStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errorMsg",execution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errorMsg",e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "请输入密码");
		}
		return modelMap;
	}

	// 绑定账号
	@RequestMapping(value = "/bindlocalauth",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "验证码错误");
			return modelMap;
		}
		
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (userName != null && password != null && user != null
				&& user.getUserId() != null) {
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			LocalAuthExecution execution = localAuthService
					.bindLocalAuth(localAuth);
			if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errorMsg", execution.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errorMsg", "用户名密码不能为空");
		}

		return modelMap;

	}
}
