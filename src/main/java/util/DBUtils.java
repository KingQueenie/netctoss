package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * ���ӳذ汾�����ݿ����ӹ����ߣ��ʺ��ڲ�������
 */
public class DBUtils {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static int initSize;
	private static int maxActive;
	private static BasicDataSource ds;
	static{
		/*
		 * <bean id="ds" class="BasicDataSource...">
		 * 	<property name="username" value="#{...}" />
		 * 	<property name="..." value="#{...}" />
		 * </bean>
		 * <bean id="" class="c3p0...">
		 * </bean>
		 */
		ds = new BasicDataSource();
		Properties cfg = new Properties();
		try {
			InputStream in = DBUtils.class
					.getClassLoader()
					.getResourceAsStream("db.properties");
			cfg.load(in);
			
//			��ʼ������
			driver = cfg.getProperty("jdbc.driver");
			url = cfg.getProperty("jdbc.url");
			username = cfg.getProperty("jdbc.username");
			password = cfg.getProperty("jdbc.password");
			initSize = Integer.parseInt(cfg.getProperty("initSize"));
			maxActive = Integer.parseInt(cfg.getProperty("maxActive"));
			in.close();
//			��ʼ�����ӳ�
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);
			ds.setInitialSize(initSize);
			ds.setMaxActive(maxActive);		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static Connection getConnection(){
		try {
//			getConnection()�����ӳ��л�ȡ���õ����ӣ�������ӳ����ˣ���ȴ�
//			��������ӹ黹�����ȡ���õ�����
			Connection conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	public static void close(Connection conn){
		if(conn != null){
			try {
//				���ù������ӹ黹�����ӳ�
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void rollback(Connection conn){
		if(conn!=null){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		Connection conn = DBUtils.getConnection();
		System.out.println(conn);
		DBUtils.close(conn);
	}
}
