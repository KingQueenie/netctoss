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
//		��ȡ����·��
		String p = req.getServletPath();
//		���ݹ淶����·��
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
			throw new RuntimeException("���޴�ҳ");
		}	
	}
	/**
	 * ��ѯ�ʷ�
	 * @param req
	 * @param res
	 */
	protected void findCost(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		��ѯ�����ʷ�
		CostDao dao = new CostDaoImpl();
		List<Cost> list = dao.findAll();
//		ת����jsp
		req.setAttribute("costs", list);
//		��ǰ:/netctoss/findCost.do
//		Ŀ��:/netctoss/WEB-INF/cost/find.jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp")
								 .forward(req, res);
	}
	/**
	 * �����ʷ�ҳ��
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
//		���ղ���
		req.setCharacterEncoding("utf-8");
		Cost c = new Cost();
		c = setCost(c,req);
		CostDao dao = new CostDaoImpl();
		dao.save(c);
//		�ض��򵽲�ѯ����
//		��ǰ:/netctoss/addCost.do
//		Ŀ��:/netctoss/findCost.do
		res.sendRedirect("findCost.do");//�ض���
	}
	private Cost setCost(Cost c,HttpServletRequest req) {
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
//		������Щ����
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
//		���ղ���
		String id = req.getParameter("id");
//		��ѯҪ�޸ĵ���������
		CostDao dao = new CostDaoImpl();
		Cost cost = dao.findById(new Integer(id));
//		ת����jsp
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
//	�򿪵�¼ҳ��
	protected void toLogin(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp")
								 .forward(req, res);
	}
//	��¼���
	protected void login(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		1. ���ղ���
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		String code = req.getParameter("code");
//		�����֤��
		HttpSession session = req.getSession();
		String imgcode = (String)session.getAttribute("imgcode");
		if(code==null || !code.equalsIgnoreCase(imgcode)){
			req.setAttribute("error","��֤�����");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
			return;
		}
//		2. ����˺�����
		AdminDao dao = new AdminDaoImpl();
		Admin a = dao.findByCode(adminCode);
		if(a == null){
//			�˺Ŵ���
			req.setAttribute("error","�˺Ŵ���");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
		} else if(!a.getPassword().equals(password)){
//			�������
			req.setAttribute("error","�������");
			req.getRequestDispatcher("WEB-INF/main/login.jsp")
									 .forward(req, res);
		} else {
//			���˺Ŵ���cookie����������ҳ����Ҫ��
			Cookie c = new Cookie("adminCode",adminCode);
			res.addCookie(c);
//			���˺Ŵ���Session����������ҳ����Ҫ��
			session.setAttribute("adminCode", adminCode);
//			���ͨ��
			res.sendRedirect("toIndex.do");
		}
	}
//	����ҳ
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
//	������֤��
	protected void createImg(HttpServletRequest req, 
			   HttpServletResponse res) throws ServletException, IOException {
//		������֤�뼰ͼƬ
		Object[] objs = ImageUtil.createImage();
//		����֤�����session��
		HttpSession session = req.getSession();
		session.setAttribute("imgcode", objs[0]);
//		��ͼƬ����������
		res.setContentType("image/png");
//		��ȡ������������ɷ������Զ����������������Ŀ����ǵ�ǰ���ʵķ�����
		OutputStream os = res.getOutputStream();
		BufferedImage img = (BufferedImage) objs[1];
		ImageIO.write(img,"png",os);
		os.close();
	}
}
