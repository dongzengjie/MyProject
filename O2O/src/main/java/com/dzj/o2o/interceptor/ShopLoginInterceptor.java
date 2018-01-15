package com.dzj.o2o.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dzj.o2o.dao.PersioninfoTest;
import com.dzj.o2o.entity.PersonInfo;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Object userobj=request.getSession().getAttribute("user");
		if(userobj !=null){
			PersonInfo user=(PersonInfo) userobj;
			if(user !=null && user.getUserId() !=null && user.getUserId()>0 && user.getEnableStatus()==1){
				return true;
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath()
				+ "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}
}
