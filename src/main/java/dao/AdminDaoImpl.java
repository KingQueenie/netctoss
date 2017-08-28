package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Admin;
import util.DBUtils;

public class AdminDaoImpl implements AdminDao, Serializable {

	public Admin findByCode(String code) {
		Connection conn =null;
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT * FROM admin_info WHERE admin_code=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Admin a = new Admin();
				a.setAdminId(rs.getInt("admin_id"));
				a.setAdminCode(rs.getString("admin_code"));
				a.setPassword(rs.getString("password"));
				a.setName(rs.getString("name"));
				a.setTelephone(rs.getString("telephone"));
				a.setEmail(rs.getString("email"));
				return a;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return null;
	}
	public static void main(String[] args) {
		AdminDao dao = new AdminDaoImpl();
		Admin a = dao.findByCode("caocao");
		System.out.println(a.getAdminId());
		System.out.println(a.getTelephone());
	}
}
