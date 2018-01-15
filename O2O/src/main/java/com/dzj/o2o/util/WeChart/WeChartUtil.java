package com.dzj.o2o.util.WeChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.dzj.o2o.entity.PersonInfo;
import com.dzj.o2o.entity.WeChart.UserAccessToken;
import com.dzj.o2o.entity.WeChart.WeChartUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class WeChartUtil {
	
	
	public static PersonInfo getPersonInfoFromRequest(WeChartUser weChartUser){
		PersonInfo personInfo =new PersonInfo();
		personInfo.setName(weChartUser.getNickname());
		personInfo.setProfileImg(weChartUser.getHeadimgurl());
		personInfo.setGender(weChartUser.getSex());
		personInfo.setEnableStatus(1);
		return personInfo;
	}
	public static UserAccessToken getUserAccessToken(String code){
		String appId= "wx47e92d9a7c3313b7";
		String  appsecret="c5b653a98df057920b84eb50ae70000b";
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +appId
				+"&secret=" +appsecret
				+"&code=" +code
				+"&grant_type=authorization_code";
		String tokenStr=httpsRequest(url, "GET", null);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		UserAccessToken accessToken =new UserAccessToken();
		try {
			accessToken=objectMapper.readValue(tokenStr, UserAccessToken.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(accessToken ==null){
			return null;
		}
		
		return accessToken;
	}
	
	public static WeChartUser getUserInfo(String accessToken,String openId){
		WeChartUser user=new WeChartUser();
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		String userStr=httpsRequest(infoUrl, "GET", null);
		
		ObjectMapper objectMapper =new ObjectMapper();
		try {
			user=objectMapper.readValue(userStr, WeChartUser.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user ==null){
			return null;
		}
		
		return user;
	}
	
	public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
		StringBuffer buffer=new StringBuffer();
		try {
			
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			String str=null;
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			while((str=bufferedReader.readLine()) != null){
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return buffer.toString();
		
	}
}
