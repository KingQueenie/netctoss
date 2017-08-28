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
//		��3��������Ҫ���ˣ��ų�����
		String[] paths = new String[]{"/toLogin.do","/login.do","/createImg.do"};
//		��ǰ·��
		String sp = request.getServletPath();
//		����ǰ·��������3��·��֮һ
		for(String p:paths){
			if(p.equals(sp)){
//				���ع��ˣ��������ִ��
				chain.doFilter(request, response);
				return;
			}
		}
//		��session�г��Ի�ȡ�˺�
		HttpSession session = request.getSession();
		String adminCode = (String)session.getAttribute("adminCode");
//		�����˺��ж��û��Ƿ��¼
		if(adminCode==null){
//			δ��¼�ض��򵽵�¼ҳ
			response.sendRedirect("/netctoss/toLogin.do");
		} else {
//			�ѵ�¼���������ִ��
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
