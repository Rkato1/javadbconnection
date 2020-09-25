package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vo.Member;

//statement/preparedstatement
//캐시메모리 사용
//ps -> 객체 생성시 sql문법검사완료(파싱)
//문법에 문제가 없는 경우 캐시메모리에 sql query 저장
//같은 query를 다시 수행하는 경우 캐시에 있는 데이터를 이용하여
//바로 수행하기 때문에 속도가 빠름
//보안성
//ps -> 인자값이 없는 상태로 문법검사를 완료하고 인자를 대입하기때문에 
//sql query의 노출이 없고 보안성이 더 높다.
//'or'1'='1 치면 다 뚫림
public class MemberDao {
	public ArrayList selectAllMember() {
		// TODO Auto-generated method stub
		ArrayList<Member> list = new ArrayList<Member>();
		//;넣지 말기
		String query = "select * from member";
		//dbms연결용 객체
		Connection conn = null;
		//sql구문을 사용하기 위한 객체
		PreparedStatement pstmt = null;
		//sql결과를 받기위한 객체(select인지 아닌지 고려)
		ResultSet rset = null;		
		
		try {
			//안써도 되지만 오류찾기 용이하기 위해 기입
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public Member selectMember(String str) {
		Member m = null;		
		Connection conn = null;
		//Statement stmt = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		//쿼리에 위치홀더('?') - 부분에 인자값을 넣어주기 위한 상태
		String query = "select * from member where member_id=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, str);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				//컬럼이름의 값을 데이터 형식에 맞춰서 받아옴(컬럼의 숫자여도 가능)
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				//6.자원반환
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public ArrayList selectMembercontain(String str) {
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member where member_name like ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//타ip접근
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.23:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				//6.자원반환
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public int insertMember(Member m) {
		//insert/update/delete는 결과값이 정수형
		int result = 0;
		String query = "insert into member values(member_seq.nextval, ?, ?, ?, ?, ?, ?, sysdate)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getPhone());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, Character.toString(m.getGender()));
			//수행된 행의 개수
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				//6.자원반환
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteMember(String str) {
		int result = 0;
		String query = "delete from member where member_name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, str);
			//수행된 행의 개수
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				//6.자원반환
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateMember(Member m) {
		int result = 0;
		String query = "update member set member_pw = ?, gender = ?, phone = ? where member_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberPw());			
			pstmt.setString(2, String.valueOf(m.getGender()));
			//같은 내용
			//pstmt.setString(2, Character.toString(m.getGender()));
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getMemberId());
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			try {
				//6.자원반환
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
