package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import member.model.vo.Member;

public class MemberDao {

	public ArrayList selectAllMember() {
		// TODO Auto-generated method stub
		ArrayList<Member> list = new ArrayList<Member>();
		//;넣지 말기
		String query = "select * from member";
		//dbms연결용 객체
		Connection conn = null;
		//sql구문을 사용하기 위한 객체
		Statement stmt = null;
		//sql결과를 받기위한 객체(select인지 아닌지 고려)
		ResultSet rset = null;		
		
		try {
			//1.driver등록(사용할 db에 대한 드라이버 설정)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2.driver를 이용해서 db연결(connection객체 생성)
			//어떤 db 연결방식 ip주소 port번호, id, 비번
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//접속실패시 null,성공시 객체생성
			//System.out.println("conn : "+conn);
			//3.쿼리문을 실행할 객체 생성(statement객체 생성)
			stmt = conn.createStatement();
			//4.쿼리문 전송하고 결과 받기
			rset = stmt.executeQuery(query);
			//결과처리
			//끝날때까지 읽기
			while(rset.next()) {
				//컬럼이름의 값을 데이터 형식에 맞춰서 받아옴(컬럼의 숫자여도 가능)				
				int memberNo =	rset.getInt("member_no");
				String memberId = rset.getString("member_id");
				String memberPw = rset.getString("member_pw");
				String memberName = rset.getString("member_name");
				int age = rset.getInt("age");
				char gender = rset.getString("gender").charAt(0);
				String phone = rset.getString("phone");
				Date enrollDate = rset.getDate("enroll_date");
				//5.값넣기
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
				//6.자원반환
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public Member selectMember(String str) {
		Member m = new Member();
		String query = "select * from member where member_id='"+str+"'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {
				//컬럼이름의 값을 데이터 형식에 맞춰서 받아옴(컬럼의 숫자여도 가능)				
				int memberNo =	rset.getInt(1);
				String memberId = rset.getString(2);
				String memberPw = rset.getString(3);
				String memberName = rset.getString(4);
				String phone = rset.getString(5);
				int age = rset.getInt(6);
				char gender = rset.getString(7).charAt(0);
				Date enrollDate = rset.getDate(8);				
				//5.값넣기
				m = new Member(memberNo,memberId,memberPw,memberName,age,gender,phone,enrollDate);
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public Member selectMembercontain(String str) {
		Member m = new Member();
		String query = "select * from member where member_name like '%"+str+"%'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//타ip접근
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.23:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {
				int memberNo =	rset.getInt(1);
				String memberId = rset.getString(2);
				String memberPw = rset.getString(3);
				String memberName = rset.getString(4);
				String phone = rset.getString(5);
				int age = rset.getInt(6);
				char gender = rset.getString(7).charAt(0);
				Date enrollDate = rset.getDate(8);				
				//5.값넣기
				m = new Member(memberNo,memberId,memberPw,memberName,age,gender,phone,enrollDate);
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public int insertMember(Member m) {
		//insert/update/delete는 결과값이 정수형
		int result = 0;
		String query = "insert into member values(member_seq.nextval,'"+m.getMemberId()+"','"+m.getMemberPw()+"','"+m.getMemberName()+"','"+m.getPhone()+"',"+m.getAge()+",'"+m.getGender()+"', sysdate)";
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			stmt = conn.createStatement();
			//수행된 행의 개수
			result = stmt.executeUpdate(query);
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
				stmt.close();
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
		String query = "delete from member where member_name = '"+str+"'";
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			stmt = conn.createStatement();
			//수행된 행의 개수
			result = stmt.executeUpdate(query);
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
				stmt.close();
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
		String query = "update member set member_pw = '"+m.getMemberPw()+"', gender = '"+m.getGender()+"', phone = '"+m.getPhone()+"' where member_id = '"+m.getMemberId()+"'";
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
			//System.out.println("conn : "+conn);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
