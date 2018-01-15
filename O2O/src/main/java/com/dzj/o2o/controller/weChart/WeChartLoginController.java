package com.dzj.o2o.controller.weChart;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dzj.o2o.dto.WechatAuthExecution;
import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.WeChartAuth;
import com.dzj.o2o.entity.WeChart.UserAccessToken;
import com.dzj.o2o.entity.WeChart.WeChartUser;
import com.dzj.o2o.enums.WechatAuthStateEnum;
import com.dzj.o2o.service.PersionInfoService;
import com.dzj.o2o.service.WeChartAuthService;
import com.dzj.o2o.util.WeChart.WeChartUtil;

@Controller
public class WeChartLoginController {
	
	//	String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="wx47e92d9a7c3313b7"&redirect_uri="http://120.78.156.159/O2O/checkback"&response_type=code"+"&scope=snsapi_userinfo"+"&state=STATE#wechat_redirect";
	//370807bf.nat123.cc
	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx47e92d9a7c3313b7&redirect_uri=http://370807bf.nat123.cc/O2O/checkback&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx47e92d9a7c3313b7&redirect_uri=http://120.78.156.159/O2O/checkback&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
	private static final String FRONTEND="1";
	private static final String SHOPEND="2";
	
	@Autowired
	private PersionInfoService persionInfoService;
	
	@Autowired
	private WeChartAuthService weChartAuthService;
	
	@RequestMapping(value="checkback",method=RequestMethod.GET)
	public String doGet(HttpServletRequest request,HttpServletResponse response){
		
		String code=request.getParameter("code");
		String roleType=request.getParameter("state");
		WeChartUser user=null;
		String openId=null;
		String accessToken=null;
		WeChartAuth auth=null;
		if(code !=null){
			UserAccessToken token;
			try {
				token=WeChartUtil.getUserAccessToken(code);
				openId=token.getOpenId();
				accessToken=token.getAccessToken();
				
				user=WeChartUtil.getUserInfo(accessToken, openId);
			
				request.getSession().setAttribute("openId", openId);
				auth=weChartAuthService.getWechatAuthByOpenId(openId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		if(auth ==null){
			//PersonInfo personInfo =WeChartUtil.getPersonInfoFromRequest(user);
			PersonInfo personInfo=new PersonInfo();
			personInfo.setName(user.getNickname());
			personInfo.setProfileImg(user.getHeadimgurl());
			if(user.getSex().equals("1")){
				personInfo.setGender("男");
			}else {
				personInfo.setGender("女");
			}
			
			auth =new WeChartAuth();
			auth.setOpenId(openId);
			if(FRONTEND.equals(roleType)){
				personInfo.setUserType(1);
			}else {
				personInfo.setUserType(2);
			}
			auth.setPersonInfo(personInfo);
			WechatAuthExecution wechatAuthExecution=weChartAuthService.regist(auth);
			if(wechatAuthExecution.getState() != WechatAuthStateEnum.SUCCESS.getState()){
				return null;
			}else {
				personInfo=persionInfoService.getPersionById(auth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
			}
		}
		if(FRONTEND.equals(roleType)){
			return "frontend/index";
		}else {
			return "shop/shoplist";
		}
		
	
		
		
		
		
	}
}
