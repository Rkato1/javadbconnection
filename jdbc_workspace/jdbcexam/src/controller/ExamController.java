package controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import common.JDBCTemplate;
import model.dao.ExamDao;
import model.vo.Board;
import model.vo.Member;

public class ExamController {
	Scanner sc;
	Member loginMember;
	ExamDao dao;
	public ExamController() {
		sc = new Scanner(System.in);
		dao = new ExamDao();
	}
	public void main() {		
		while(true) {
			System.out.println("---------- KH커뮤니티 ----------");
			System.out.println("1. 로그인하기");
			System.out.println("2. 회원가입");
			System.out.println("3. 아이디 찾기");
			System.out.println("0. 프로그램 종료");
			System.out.print("선택  > ");
			int sel = sc.nextInt();
			
			switch(sel) {
			case 1:
				login();
				break;
			case 2:
				createMember();
				break;
			case 3:
				findId();
				break;
			case 0:
				return;
			}
			
		}
	}
	private void createMember() {
		// TODO Auto-generated method stub
		Member m = new Member();
		int itemp = 0;
		System.out.println("---------- 회원가입 ----------");
		System.out.print("ID 입력 : ");
		m.setM_id(sc.next());
		System.out.print("PW 입력 : ");
		m.setM_pw(sc.next());
		System.out.print("이름 입력 : ");
		m.setM_name(sc.next());
		System.out.print("전화번호 입력(ex.01011112222) : ");
		m.setPhone(sc.next());
		//System.out.println(m.getM_id()+" "+m.getM_pw()+" "+m.getM_name()+" "+m.getPhone());
		Connection conn = JDBCTemplate.getConnection();
		itemp = dao.createMember(conn, m);
		if(itemp>0) {
			JDBCTemplate.commit(conn);			
		}else {
			JDBCTemplate.rollback(conn);
		}
		System.out.println(itemp+"개 행 삽입");
		System.out.println("회원가입 성공");
		JDBCTemplate.close(conn);		
	}
	
	private void findId() {
		// TODO Auto-generated method stub
		Member m = new Member();
		String stemp = null;
		System.out.println("---------- 아이디 찾기 ----------");
		System.out.print("이름 입력 : ");
		m.setM_name(sc.next());
		System.out.print("전화번호 입력 : ");
		m.setPhone(sc.next());
		Connection conn = JDBCTemplate.getConnection();
		//System.out.println(m.getM_name()+","+m.getPhone());
		stemp = dao.findMemberId(conn, m);
		if(stemp==null) {
			JDBCTemplate.rollback(conn);
			System.out.println("일치하는 정보가 없습니다.");
		}else {
			JDBCTemplate.commit(conn);
			System.out.println("아이디는 ["+stemp+"] 입니다.");
		}
		JDBCTemplate.close(conn);
	}

	private void login() {
		// TODO Auto-generated method stub
		Member m = new Member();
		boolean btemp = false;
		System.out.println("---------- 로그인 ----------");
		System.out.print("ID 입력 : ");
		m.setM_id(sc.next());
		System.out.print("PW 입력 : ");
		m.setM_pw(sc.next());
		Connection conn = JDBCTemplate.getConnection();
		btemp = dao.login(conn, m);
		if(btemp==true) {
			System.out.println("로그인 성공!!");
			loginMenu(m.getM_id());
		}else {
			System.out.println("아이디 또는 비밀번호를 확인하세요.");
		}
		JDBCTemplate.close(conn);
	}
	private void loginMenu(String str) {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("---------- KH커뮤니티 ----------");
			System.out.println("1. 게시물 목록 보기");
			System.out.println("2. 게시물 상세 보기");
			System.out.println("3. 게시물 등록");
			System.out.println("4. 게시물 수정");
			System.out.println("5. 게시물 삭제");
			System.out.println("6. 내 정보 보기");
			System.out.println("7. 내 정보 변경");
			System.out.println("8. 회원 탈퇴");
			System.out.println("0. 로그아웃");
			System.out.print("선택  > ");
			int sel = sc.nextInt();
			
			switch(sel) {
			case 1:
				seeBoardAll();
				break;
			case 2:
				seeBoard();
				break;
			case 3:
				writeBoard(str);
				break;
			case 4:
				updateBoard(str);
				break;
			case 5:
				deleteBoard(str);
				break;
			case 6:
				seemyInfo(str);
				break;
			case 7:
				updatemyInfo(str);
				break;
			case 8:
				deleteAccount(str);
				break;
			case 0:
				main();
				break;
			}
			
		}
	}
	
	private void seemyInfo(String str) {
		// TODO Auto-generated method stub
		Member m = new Member();
		System.out.println("---------- 내 정보 보기 ----------");
		Connection conn = JDBCTemplate.getConnection();
		m.setM_id(str);
		m = dao.seemyInfo(conn, m);
		if(m==null) {
			System.out.println("오류발생");
		}else {
			System.out.println("회원번호 :"+m.getM_no());
			System.out.println("아이디 :"+m.getM_id());
			System.out.println("비밀번호 :"+m.getM_pw());
			System.out.println("이름 :"+m.getM_name());
			System.out.println("전화번호 :"+m.getPhone());
		}
		JDBCTemplate.close(conn);
	}

	private void updatemyInfo(String str) {
		// TODO Auto-generated method stub
		Member m = new Member();
		int itemp = 0;
		System.out.println("---------- 내 정보 보기 ----------");
		Connection conn = JDBCTemplate.getConnection();
		System.out.print("PW 입력 : ");
		m.setM_pw(sc.next());
		System.out.print("전화번호 입력(ex.01011112222) : ");
		m.setPhone(sc.next());
		m.setM_id(str);
		itemp = dao.updatemyInfo(conn, m);
		if(itemp>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		System.out.println(itemp+"개 행 수정");
		System.out.println("정보 수정 성공");
		JDBCTemplate.close(conn);
	}

	private void writeBoard(String str) {
		// TODO Auto-generated method stub
		int itemp = 0;
		Board b = new Board();
		System.out.print("제목 입력 : ");
		b.setB_title(sc.next());
		System.out.print("내용 입력 : ");
		b.setB_content(sc.next());
		b.setB_writer(str);
		Connection conn = JDBCTemplate.getConnection();
		itemp = dao.createBoard(conn, b);
		if(itemp>0) {
			JDBCTemplate.commit(conn);			
		}else {
			JDBCTemplate.rollback(conn);
		}
		System.out.println(itemp+"개 행 삽입");
		System.out.println("게시글 등록 성공");
		JDBCTemplate.close(conn);
	}
	

	private void seeBoardAll() {		
		ArrayList<Board> arlB = new ArrayList<Board>();
		Connection conn = JDBCTemplate.getConnection();
		arlB = dao.seeBoardAll(conn,arlB);
		System.out.println("---------- 게시물 목록 ----------");
		for(Board b : arlB) {
			System.out.println(b.getB_no()+"\t"+b.getB_title()+"\t"+b.getB_content()+"\t\t"+b.getB_writer()+"\t"+b.getRead_count()+"\t"+b.getWrite_date());
		}
		JDBCTemplate.close(conn);
	}	

	private void seeBoard() {
		Board b = new Board();
		Connection conn = JDBCTemplate.getConnection();
		System.out.print("게시물 번호 입력 : ");
		int i = sc.nextInt();
		b = dao.seeBoard(conn,b,i);
		System.out.println("---------- 게시글 정보 ----------");
		System.out.println("게시물 번호 : "+b.getB_no());
		System.out.println("게시물 제목 : "+b.getB_title());
		System.out.println("게시물 내용 : "+b.getB_content());
		System.out.println("게시물 작성자 : "+b.getB_writer());
		System.out.println("게시물 조회수 : "+b.getRead_count());
		System.out.println("게시물 작성일 : "+b.getWrite_date());		
		JDBCTemplate.close(conn);		
	}
	
	private void updateBoard(String str) {		
		boolean btemp = false;
		Connection conn = JDBCTemplate.getConnection();
		System.out.print("게시물 번호 입력 : ");
		int i = sc.nextInt();
		btemp = dao.boardCheck(conn,i,str);
		if(btemp==true) {
			Board b = new Board();
			System.out.print("제목 입력 : ");
			b.setB_title(sc.next());
			System.out.print("내용 입력 : ");
			b.setB_content(sc.next());
			i = dao.updateBoard(conn, b, str);
			if(i>0) {
				JDBCTemplate.commit(conn);
				System.out.println("게시글 수정 성공");
			}else {
				JDBCTemplate.rollback(conn);
			}
		}else {
			System.out.println("작성자만 수정이 가능합니다.");
		}		
		JDBCTemplate.close(conn);
	}
	
	void deleteBoard(String str) {
		boolean btemp = false;
		Connection conn = JDBCTemplate.getConnection();
		System.out.print("게시물 번호 입력 : ");
		int i = sc.nextInt();
		btemp = dao.boardCheck(conn,i,str);
		if(btemp==true) {
			i = dao.deleteBoard(conn, i);
			if(i>0) {
				JDBCTemplate.commit(conn);
				System.out.println("게시글 삭제 성공!!");
			}else {
				JDBCTemplate.rollback(conn);
			}
		}else {
			System.out.println("작성자만 삭제가 가능합니다.");
		}		
		JDBCTemplate.close(conn);
	}
	
	private void deleteAccount(String str) {
		// TODO Auto-generated method stub
		System.out.print("정말 탈퇴하시겠습니까?(1.YES / 2.NO)");
		int itemp = sc.nextInt();
		if(itemp%2==0) {
			System.out.println("돌아갑니다.");
		}else {
			Connection conn = JDBCTemplate.getConnection();
			int i = dao.deleteUser(conn,str);
			int j = dao.updateDelUserBoard(conn, str);
			if(i>0 && j>0) {
				JDBCTemplate.commit(conn);
				System.out.println("삭제성공");
			}else {
				JDBCTemplate.rollback(conn);
				System.out.println("응 안됐어");
				System.out.println(i+","+j);
			}
			JDBCTemplate.close(conn);
			System.out.println("Bye~Bye~");
			main();
		}
	}
}
