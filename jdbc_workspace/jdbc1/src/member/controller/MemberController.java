package member.controller;

import java.util.ArrayList;
import java.util.Scanner;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberController {
	Scanner sc;
	MemberDao dao;
	public MemberController() {
		super();
		sc = new Scanner(System.in);
		dao = new MemberDao();
	}
	
	public void main() {
		while(true) {
			System.out.println("=== 회원 관리 프로그램 ===");
			System.out.println("1. 전체회원 정보 출력");
			System.out.println("2. 회원 정보 출력(1명)");
			System.out.println("3. 회원 등록");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 정보 삭제");
			System.out.println("6. 회원 정보 검색(이름+포함되면 전부 다 조회)");
			System.out.print("선택 > ");
			int sel = sc.nextInt();
			
			switch(sel) {
			case 1:
				selectAllMember();
				break;
			case 2:
				selectMember();
				break;
			case 3:
				insertMember();
				break;
			case 4:
				break;
			case 5:
				deleteMember();
				break;
			case 6:
				selectMembercontain();
				break;
			default:
				break;
			}
		}
	}

	public void selectAllMember() {
		// TODO Auto-generated method stub
		ArrayList<Member> list = dao.selectAllMember();
		//같은 뜻
		//if(list.size()==0) {
		if(list.isEmpty()) {
			System.out.println("회원이 아직 없음");
		}else {
			System.out.println("=== 전체 회원 정보 ===");
			System.out.println("번호\t아이디\t비밀번호\t이름\t나이\t성별\t번호\t\t날짜");
			for(Member m : list) {
				System.out.println(m.getMemberNo()+"\t"+m.getMemberId()+"\t"+m.getMemberPw()+"\t"+m.getMemberName()+"\t"+m.getAge()+"\t"+m.getGender()+"\t"+m.getPhone()+"\t"+m.getEnroolDate());
			}
		}
	}
	
	public void selectMember() {
		System.out.print("조회할 id 입력 : ");
		String str = sc.next();
		Member m = dao.selectMember(str);
		if(m.getMemberId() == null) {
			System.out.println("조회 불가");
		}else {
			System.out.println("조회 결과");
			System.out.println("번호\t아이디\t비밀번호\t이름\t나이\t성별\t번호\t\t날짜");
			System.out.println(m.getMemberNo()+"\t"+m.getMemberId()+"\t"+m.getMemberPw()+"\t"+m.getMemberName()+"\t"+m.getAge()+"\t"+m.getGender()+"\t"+m.getPhone()+"\t"+m.getEnroolDate());
		}
	}
	
	public void selectMembercontain() {
		// TODO Auto-generated method stub
		System.out.print("조회할 이름 입력(검색문자포함) : ");
		String str = sc.next();
		Member m = dao.selectMembercontain(str);
		if(m.getMemberId() == null) {
			System.out.println("조회 불가");
		}else {
			System.out.println("조회 결과");
			System.out.println("번호\t아이디\t비밀번호\t이름\t나이\t성별\t번호\t\t날짜");
			System.out.println(m.getMemberNo()+"\t"+m.getMemberId()+"\t"+m.getMemberPw()+"\t"+m.getMemberName()+"\t"+m.getAge()+"\t"+m.getGender()+"\t"+m.getPhone()+"\t"+m.getEnroolDate());
		}
	}

	public void insertMember() {
		// TODO Auto-generated method stub
		System.out.println("=== 회원 등록 ===");
		Member m = new Member();
		System.out.print("id 입력 : ");
		m.setMemberId(sc.next());
		System.out.print("pw 입력 : ");
		m.setMemberPw(sc.next());
		System.out.print("이름 입력 : ");
		m.setMemberName(sc.next());
		System.out.print("번호 입력 : ");
		m.setPhone(sc.next());
		System.out.print("나이 입력 : ");
		m.setAge(sc.nextInt());
		System.out.print("성별 입력 : ");
		m.setGender(sc.next().charAt(0));
		int result = dao.insertMember(m);
		if(result>0) {
			System.out.println("등록성공");
		}else {
			System.out.println("등록실패");
		}
		System.out.println(result+"개 행에 적용됨");
	}

	public void deleteMember() {
		// TODO Auto-generated method stub
		System.out.print("삭제할 이름 입력 : ");
		String str = sc.next();
		int result = dao.deleteMember(str);
		if(result>0) {
			System.out.println("삭제성공");
		}else {
			System.out.println("삭제실패");
		}
		System.out.println(result+"개 행에 적용됨");
	}
}
