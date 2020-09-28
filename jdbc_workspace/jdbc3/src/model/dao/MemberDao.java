package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import model.vo.Member;

public class MemberDao {
	
	public ArrayList selectAllMember(Connection conn) {
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int memberNo =	rset.getInt("member_no");
				String memberId = rset.getString("member_id");
				String memberPw = rset.getString("member_pw");
				String memberName = rset.getString("member_name");
				int age = rset.getInt("age");
				char gender = rset.getString("gender").charAt(0);
				String phone = rset.getString("phone");
				Date enrollDate = rset.getDate("enroll_date");
				Member mem = new Member(memberNo,memberId,memberPw,memberName,age,gender,phone,enrollDate);
				list.add(mem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);			
		}
		return list;
	}

	public Member selectMember(Connection conn, String str) {		
		Member m = null;
		ResultSet rset = null;
		String query = "select * from member where member_id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, str);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				m = new Member();
				m.setMemberNo(rset.getInt(1));
				m.setMemberId(rset.getString(2));
				m.setMemberPw(rset.getString(3));
				m.setMemberName(rset.getString(4));
				m.setPhone(rset.getString(5));
				m.setAge(rset.getInt(6));
				m.setGender(rset.getString(7).charAt(0));
				m.setEnroolDate(rset.getDate(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}

	public ArrayList selectMembercontain(Connection conn, String str) {
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member where member_name like ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+str+"%");
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int memberNo =	rset.getInt(1);
				String memberId = rset.getString(2);
				String memberPw = rset.getString(3);
				String memberName = rset.getString(4);
				String phone = rset.getString(5);
				int age = rset.getInt(6);
				char gender = rset.getString(7).charAt(0);
				Date enrollDate = rset.getDate(8);
				Member m = new Member(memberNo,memberId,memberPw,memberName,age,gender,phone,enrollDate);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int insertMember(Connection conn, Member m) {
		int result = 0;
		String query = "insert into member values(member_seq.nextval, ?, ?, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = null;		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getPhone());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, Character.toString(m.getGender()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String str) {
		int result = 0;
		String query = "delete from member where member_name = ?";
		PreparedStatement pstmt = null;		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, str);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {}
		return result;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		String query = "update member set member_pw = ?, gender = ?, phone = ? where member_id = ?";		
		PreparedStatement pstmt = null;		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberPw());			
			pstmt.setString(2, String.valueOf(m.getGender()));
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getMemberId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int insertDelMember(Connection conn, String str) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into del_member values(del_seq.nextval,?,sysdate)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, str);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}		
		return result;
	}
}
