package web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.AdminDaoImpl;
import dao.CostDao;
import dao.CostDaoImpl;
import entity.Admin;
import entity.Cost;
import util.ImageUtil;

public class MainServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, 
						   HttpServletResponse res) throws ServletException, IOException {
//		获取请求路径
		String p = req.getServletPath();
//		根据规范处理路径
		if("/findCost.do".equals(p)){
			findCost(req,res);
		} else if("/toAddCost.do".equals(p)){
			toAddCost(req,res);
		} else if("/addCost.do".equals(p)){
			addCost(req,res);
		}else if("/toUpdateCost.do".equals(p)){
			toUpdateCost(req,res);
		}else if("/UpdateCost.do".equals(p)){
			UpdateCost(req,res);
		}else if("/toLogin.do".equals(p)){
			toLogin(req,res);
		}else if("/toIndex.do".equals(p)){
			toIndex(req,res);
		}else if("/login.do".equals(p)){
			login(req,res);
		}else if("/logout.do".equals(p)){
			logout(req,res);
		}else if("/createImg.do".equals(p)){
			createImg(req,res);
		}else {
			throw new RuntimeException("查无此页");
		}	
	}
	/**
	 * 查询资费
	 * @param req
	 * @param res
	 */
	protected void findCost(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		查询所有资费
		CostDao dao = new CostDaoImpl();
		List<Cost> list = dao.findAll();
//		转发到jsp
		req.setAttribute("costs", list);
//		当前:/netctoss/findCost.do
//		目标:/netctoss/WEB-INF/cost/find.jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp")
								 .forward(req, res);
	}
	/**
	 * 增加资费页面
	 * @param req
	 * @param res
	 */
	protected void toAddCost(HttpServletRequest req, 
			   				 HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/cost/add.jsp")
		                         .forward(req, res);
	}
	protected void addCost(HttpServletRequest req, 
			   		       HttpServletResponse res) throws ServletException, IOException {
//		接收参数
		req.setCharacterEncoding("utf-8");
		Cost c = new Cost();
		c = setCost(c,req);
		CostDao dao = new CostDaoImpl();
		dao.save(c);
//		重定向到查询功能
//		当前:/netctoss/addCost.do
//		目标:/netctoss/findCost.do
		res.sendRedirect("findCost.do");//重定向
	}
	private Cost setCost(Cost c,HttpServletRequest req) {
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
//		保存这些数据
		c.setName(name);
		c.setCostType(costType);
		if(baseDuration!=null && !baseDuration.equals("")){
			c.setBaseDuration(new Integer(baseDuration));
		}
		if(baseCost!=null && !baseCost.equals("")){
			c.setBaseCost(new Double(baseCost));
		}
		if(unitCost!=null && !unitCost.equals("")){
			c.setUnitCost(new Double(unitCost));
		}
		c.setDescr(descr);
		return c;
	}
	protected void toUpdateCost(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		接收参数
		String id = req.getParameter("id");
//		查询要修改的这条数据
		CostDao dao = new CostDaoImpl();
		Cost cost = dao.findById(new Integer(id));
//		转发到jsp
		req.setAttribute("cost", cost);
		req.getRequestDispatcher("WEB-INF/cost/update.jsp")
								 .forward(req, res);
	}
	protected void UpdateCost(HttpServletRequest req, 
			   				  HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("costId");
		CostDao dao = new CostDaoImpl();
		Cost c = dao.findById(new Integer(id));
		c = setCost(c,req);
		dao.update(c);
		res.sendRedirect("findCost.do");
	}
//	打开登录页面
	protected void toLogin(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp")
								 .forward(req, res);
	}
//	登录检查
	protected void login(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		1. 接收参数
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		String code = req.getParameter("code");
//		检查验证码
		HttpSession session = req.getSession();
		String imgcode = (String)session.getAttribute("imgcode");
		if(code==null || !code.equalsIgnoreCase(imgcode)){
			req.setAttribute("error","验证码错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
			return;
		}
//		2. 检查账号密码
		AdminDao dao = new AdminDaoImpl();
		Admin a = dao.findByCode(adminCode);
		if(a == null){
//			账号错误
			req.setAttribute("error","账号错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
		} else if(!a.getPassword().equals(password)){
//			密码错误
			req.setAttribute("error","密码错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
		} else {
//			将账号存入cookie，后面其他页面上要用
			Cookie c = new Cookie("adminCode",adminCode);
			res.addCookie(c);
//			将账号存入Session，后面其他页面上要用
			session.setAttribute("adminCode", adminCode);
//			检查通过
			res.sendRedirect("toIndex.do");
		}
	}
//	打开主页
	protected void toIndex(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp")
		 					     .forward(req, res);
	}
	protected void logout(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		res.sendRedirect("toLogin.do");
	}
//	生成验证码
	protected void createImg(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		生成验证码及图片
		Object[] objs = ImageUtil.createImage();
//		将验证码存入session，
		HttpSession session = req.getSession();
		session.setAttribute("imgcode", objs[0]);
//		将图片输出给浏览器
		res.setContentType("image/png");
//		获取输出流，该流由服务器自动创建，它所输出的目标就是当前访问的服务器
		OutputStream os = res.getOutputStream();
		BufferedImage img = (BufferedImage) objs[1];
		ImageIO.write(img,"png",os);
		os.close();
	}
}
