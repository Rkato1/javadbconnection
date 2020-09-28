package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import model.vo.Board;
import model.vo.Member;

public class ExamDao {

	public int createMember(Connection conn, Member m) {		
		int result = 0;
		String query = "insert into exam_member values(m_no_seq.nextval, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getM_id());
			pstmt.setString(2, m.getM_pw());
			pstmt.setString(3, m.getM_name());
			pstmt.setString(4, m.getPhone());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public String findMemberId(Connection conn, Member m) {		
		String query = "select m_id from exam_member where m_name=? and phone=?";
		String str = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getM_name());
			pstmt.setString(2, m.getPhone());
			rset = pstmt.executeQuery();
			while(rset.next()) {
				str = rset.getString("m_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		if(str==null) {
			return null;
		}else {
			return str;
		}
	}

	public boolean login(Connection conn, Member m) {
		String query = "select m_pw from exam_member where m_id=? and m_pw=?";
		String pw = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getM_id());
			pstmt.setString(2, m.getM_pw());
			rset = pstmt.executeQuery();
			while(rset.next()) {
				pw = rset.getString("m_pw");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		//if(pw==null || !pw.equals(m.getM_pw())) {
		if(pw==null) {
			return false;
		}else {
			return true;
		}
	}

	public Member seemyInfo(Connection conn, Member m) {
		String query = "select * from exam_member where m_id=?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getM_id());
			rset = pstmt.executeQuery();
			while(rset.next()) {
				m.setM_no(rset.getInt("m_no"));
				m.setM_id(rset.getString("m_id"));
				m.setM_pw(rset.getString("m_pw"));
				m.setM_name(rset.getString("m_name"));
				m.setPhone(rset.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}

	public int updatemyInfo(Connection conn, Member m) {
		String query = "update exam_member set m_pw=?, phone=? where m_id=?";
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getM_pw());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getM_id());
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int createBoard(Connection conn, Board b) {
		int result = 0;
		String query = "insert into exam_board values(b_no_seq.nextval, ?, ?, ?, 0, sysdate)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.getB_title());
			pstmt.setString(2, b.getB_content());
			pstmt.setString(3, b.getB_writer());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Board> seeBoardAll(Connection conn, ArrayList<Board> arlB) {
		String query = "select b.*,m.M_NAME from exam_board b join EXAM_MEMBER m on(b.B_WRITER = m.m_id)";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int b_no = rset.getInt("b_no");
				String b_title = rset.getString("b_title");
				String b_content = rset.getString("b_content");
				String b_writer = rset.getString("m_name");
				int read_count = rset.getInt("read_count");
				Date write_date = rset.getDate("write_date");
				Board b = new Board(b_no,b_title,b_content,b_writer,read_count,write_date);
//				b.setB_no(rset.getInt("b_no"));
//				b.setB_title(rset.getString("b_title"));
//				b.setB_content(rset.getString("b_content"));
//				b.setB_writer(rset.getString("b_writer"));
//				b.setRead_count(rset.getInt("read_count"));
//				b.setWrite_date(rset.getDate("write_date"));
				arlB.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arlB;
	}

	public Board seeBoard(Connection conn, Board b, int i) {
		String query = "select b.*,m.M_NAME from exam_board b join EXAM_MEMBER m on(b.B_WRITER = m.m_id) where b.b_no=?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, i);
			rset = pstmt.executeQuery();
			while(rset.next()) {				
				int b_no = rset.getInt("b_no");
				String b_title = rset.getString("b_title");
				String b_content = rset.getString("b_content");
				String b_writer = rset.getString("m_name");
				int read_count = rset.getInt("read_count")+1;
				Date write_date = rset.getDate("write_date");
				b = new Board(b_no,b_title,b_content,b_writer,read_count,write_date);
				viewUp(conn,rset.getInt("b_no"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return b;
	}
	void viewUp(Connection conn, int i) {
		String query = "update exam_board set read_count=read_count+1 where b_no=?";
		int itemp=0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, i);
			itemp = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(itemp>0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
			JDBCTemplate.close(pstmt);
		}
	}

	public boolean boardCheck(Connection conn, int i, String str) {
		String query = "select b_writer from exam_board where b_no=?";
		String stemp = null;
		ResultSet rset=null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, i);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				stemp = rset.getString("b_writer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);			
		}
		if(rset==null || !str.equals(stemp)) {
			return false;
		}else {
			return true;
		}
	}

	public int updateBoard(Connection conn, Board b, String str) {
		String query = "update exam_board set b_title=?, b_content=? where b_writer=?";
		int itemp=0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.getB_title());
			pstmt.setString(2, b.getB_content());
			pstmt.setString(3, str);
			itemp = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(pstmt);
		}
		return itemp;
	}

	public int deleteBoard(Connection conn, int i) {
		String query = "delete from exam_board where b_no=?";
		int itemp=0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,i);
			itemp = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(pstmt);
		}
		return itemp;
	}

	public int deleteUser(Connection conn, String str) {
		String query = "delete from exam_member where m_id=?";
		int itemp=0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,str);
			itemp = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return itemp;
	}

	public int updateDelUserBoard(Connection conn, String str) {
		String query = "update exam_board set b_writer='탈퇴회원' where b_writer is null";
		int itemp=0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			itemp = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(pstmt);
		}
		return itemp;
	}

}
