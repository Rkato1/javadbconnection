package kh.shopping.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kh.shopping.common.Template;
import kh.shopping.model.vo.CustomerInfo;
import kh.shopping.model.vo.Shop;
import kh.shopping.model.vo.Storage;

public class ShoppingDao {

	//1. 로그인
	public int loginCustomer(Connection conn, String id, String pw) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "select * from customer where c_id =? and c_pw = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  id);
			pstmt.setString(2,  pw);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return result;
	}
	
	//2. 회원가입
	public int registerCustomer(Connection conn, String id, String pw, String name, String phone) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into customer values (? ,? ,? ,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2,  pw);
			pstmt.setString(3,  name);
			pstmt.setString(4,  phone);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return result;
	}

	//3. 아이디/비번 찾기
	public CustomerInfo findIdCustomer(Connection conn, String name, String phone) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from customer where c_name = ? and c_phone = ? ";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  name);
			pstmt.setString(2,  phone);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				CustomerInfo customer = new CustomerInfo();
				customer.setcId(rset.getString("c_id"));
				customer.setcPw(rset.getString("c_pw"));
				return customer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
			Template.close(rset);
		}
		return null;
	}

	//상품 전체 조회
	public ArrayList<Shop> seeProductAll(Connection conn, ArrayList<Shop> arls) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from shop";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				Shop s = new Shop();
				s.setP_no(rset.getInt("p_no"));
				s.setP_name(rset.getString("p_name"));
				s.setPrice(rset.getInt("price"));
				s.setP_count(rset.getInt("p_count"));
				arls.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
			Template.close(rset);
		}
		return arls;
	}

	//상품 정보 삽입
	public int insertProduct(Connection conn, Shop s) {
		PreparedStatement pstmt = null;
		int itemp=0;
		String query = "insert into shop values(P_NO_SEQ.NEXTVAL, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, s.getC_id());
			pstmt.setInt(2, s.getPrice());
			pstmt.setString(3, s.getP_name());
			pstmt.setInt(4, s.getP_count());
			itemp = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return itemp;
	}

	//상품 정보 갱신
	public int updateProduct(Connection conn, Shop s, int i) {
		PreparedStatement pstmt = null;
		int itemp=0;
		String query = "UPDATE shop SET p_name=?, PRICE=?, p_count=? where p_no = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, s.getP_name());
			pstmt.setInt(2, s.getPrice());
			pstmt.setInt(3, s.getP_count());
			pstmt.setInt(4, i);
			itemp = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return itemp;
	}

	public ArrayList<Storage> showStorageList(Connection conn, ArrayList<Storage> arls, String id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from storage where c_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				Storage s = new Storage();
				s.setS_no(rset.getInt("s_no"));
				s.setP_no(rset.getInt("p_no"));
				s.setC_id(rset.getString("c_id"));
				arls.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
			Template.close(rset);
		}
		return arls;
	}

	public int addStorage(Connection conn, int i, String id) {
		PreparedStatement pstmt = null;
		int itemp=0;
		String query = "insert into storage values(s_NO_SEQ.NEXTVAL, ?, ?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, i);
			pstmt.setString(2, id);
			itemp = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return itemp;
	}

	public int buyProduct(Connection conn, int i, String id) {
		PreparedStatement pstmt = null;
		int itemp=0;
		String query = "delete from storage where p_no=? and c_id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, i);
			pstmt.setString(2, id);
			itemp = pstmt.executeUpdate();
			if(itemp!=0) {
				Template.close(pstmt);
				query = "select p_count from shop where p_no = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, i);
				ResultSet rset = null;
				rset = pstmt.executeQuery();
				int j = 0;
				while(rset.next()) {
					j = rset.getInt("p_count");
				}
				if(j!=0) {
					Template.close(pstmt);
					query = "UPDATE shop SET p_count=? where p_no = ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, j-1);
					pstmt.setInt(2, i);
					itemp = pstmt.executeUpdate();
				}else {
					itemp = 0;
				}
			}else {
				itemp = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Template.close(pstmt);
		}
		return itemp;
	}
}
