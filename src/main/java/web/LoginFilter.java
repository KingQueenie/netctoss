package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
		

	}

	public void doFilter(ServletRequest req, 
						 ServletResponse res, 
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
//		有3个请求不需要过滤，排除它们
		String[] paths = new String[]{"/toLogin.do","/login.do","/createImg.do"};
//		当前路径
		String sp = request.getServletPath();
//		若当前路径是上述3个路径之一
		for(String p:paths){
			if(p.equals(sp)){
//				不必过滤，请求继续执行
				chain.doFilter(request, response);
				return;
			}
		}
//		从session中尝试获取账号
		HttpSession session = request.getSession();
		String adminCode = (String)session.getAttribute("adminCode");
//		根据账号判断用户是否登录
		if(adminCode==null){
//			未登录重定向到登录页
			response.sendRedirect("/netctoss/toLogin.do");
		} else {
//			已登录，请求继续执行
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
