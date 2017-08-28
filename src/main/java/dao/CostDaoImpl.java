package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtils;

public class CostDaoImpl implements CostDao, Serializable {
	public List<Cost> findAll() {
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT * FROM cost ORDER BY cost_id";
			Statement smt = conn.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			List<Cost> list = new ArrayList<Cost>();
			while(rs.next()){
				Cost c = createCost(rs);
				list.add(c);		
			}
			return list;
		} catch (Exception e) {
//			��¼��־
			e.printStackTrace();
//			�׳��쳣
			throw new RuntimeException("��ѯ�ʷ�ʧ��",e);
		} finally {
			DBUtils.close(conn);
		}
	}
	/*
	 * Alt+Shift+M ��ȡ�����Ŀ�ݼ�
	 */
	private Cost createCost(ResultSet rs) throws SQLException {
		Cost c = new Cost();
		c.setCostId(rs.getInt("cost_id"));
		c.setName(rs.getString("name"));
		c.setBaseDuration(rs.getInt("base_duration"));
		c.setBaseCost(rs.getDouble("base_cost"));
		c.setUnitCost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreatime(rs.getTimestamp("creatime"));
		c.setStartime(rs.getTimestamp("startime"));
		c.setCostType(rs.getString("cost_type"));
		return c;
	}
	public void save(Cost c){
		Connection conn = null;
		try {
			conn =  DBUtils.getConnection();
			String sql = "INSERT INTO cost VALUES(null,?,?,?,?,1,?,null,null,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getName());
//			setInt()/set.Double()��������null����ʵ��ҵ������Щ�ֶ�ȷʵ��null�����
//			����Ҳ֧��null����ʱ��Ҫ�����ǵ�������������
			ps.setObject(2, c.getBaseDuration());
			ps.setObject(3, c.getBaseCost());
			ps.setObject(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6, c.getCostType());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�����ʷ�ʧ��",e);
		} finally {
			DBUtils.close(conn);
		}
	}
	public Cost findById(int id) {
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT * FROM cost WHERE cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return createCost(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��ѯ�ʷ�ʧ��",e);
		} finally {
			DBUtils.close(conn);
		}
		return null;
	}
	public void update(Cost c) {
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "UPDATE cost SET name=?,base_duration=?,base_cost=?,unit_cost=?,descr=?,cost_type=? WHERE cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			ps.setInt(2, c.getBaseDuration());
			ps.setDouble(3, c.getBaseCost());
			ps.setDouble(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6, c.getCostType());
			ps.setInt(7, c.getCostId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�޸��ʷ�ʧ��",e);
		} finally {
			DBUtils.close(conn);
		}
	}
	public static void main(String[] args){
		CostDao dao = new CostDaoImpl();
//		�����ʷѲ�ѯ
//		List<Cost> list = dao.findAll();
//		for(Cost c:list){
//			System.out.println(c.getCostId()+","+c.getName());
//		}
//		�����ʷ�����
//		Cost c = new Cost();
//		c.setName("60Ԫ�ײ�");
//		c.setBaseDuration(600);
//		c.setBaseCost(60.0);
//		c.setUnitCost(0.6);
//		c.setDescr("60Ԫ�ײ�ͦʵ��");
//		c.setCostType("2");
//		dao.save(c);
//		���Բ���ĳһ�ʷ�
		Cost c = dao.findById(1);
//		System.out.println(c.getName());
//		System.out.println(c.getDescr());
//		�����޸��ʷ�
		c.setDescr(c.getName()+"������");
		dao.update(c);
	}
	
}
