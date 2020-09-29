package kh.shopping.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import kh.shopping.common.Template;
import kh.shopping.model.dao.ShoppingDao;
import kh.shopping.model.vo.CustomerInfo;
import kh.shopping.model.vo.Shop;
import kh.shopping.model.vo.Storage;

public class ShoppingController {

	Scanner sc;
	ShoppingDao dao;  //데베 쿼리문 실행 클래스
	
	public ShoppingController() {
		super();
		sc = new Scanner(System.in);
		dao = new ShoppingDao();
	}
	
	public void main() {
		while(true) {
			System.out.println("\n-------티몬에 오신 것을 환영합니다~-------");
			System.out.println("1. 로그인 하기");
			System.out.println("2. 회원가입");
			System.out.println("3. 아이디/비번 찾기");
			System.out.println("0. 프로그램 종료");
			System.out.print("선택 > ");
			int sel = sc.nextInt();
			switch(sel) {
			case 1:
				loginCustomer();
				break;
			case 2:
				registerCustomer();
				break;
			case 3:
				findIdCustomer();
				break;
			case 0:
				return;
			}
		}
	}
	
	//1. 로그인 하기
	public void loginCustomer() {
		Connection conn = Template.getConnection();
		System.out.println("\n---------로그인---------");
		System.out.print("아이디 입력 : ");
		String id = sc.next();
		System.out.print("비밀번호 입력 : ");
		String pw = sc.next();
		int result = dao.loginCustomer(conn, id, pw);
		if(result > 0) {
			System.out.println("로그인 되었습니다. 즐거운 쇼핑 되세요.");
			loginMenu(id);
		}else {
			System.out.println("아이디 또는 비밀번호가 맞지 않습니다. 다시 로그인 해주세요.");
		}
	}

	//2. 회원가입
	public void registerCustomer() {
		Connection conn = Template.getConnection();
		System.out.println("\n---------회원 가입 창---------");
		System.out.print("아이디 입력 : ");
		String id = sc.next();
		System.out.print("비밀번호 입력 : ");
		String pw = sc.next();
		System.out.print("이름 입력 : ");
		String name = sc.next();
		System.out.print("번호 입력(ex 01012341234) : ");
		String phone = sc.next();
		
		int result = dao.registerCustomer(conn, id, pw, name, phone);
		if(result > 0) {
			System.out.println("회원가입 완료! 환영합니다~");
			Template.commit(conn);
		}else {
			System.out.println("회원가입 실패! 다시 한번 시도해주세요.");
			Template.rollback(conn);
		}
		Template.close(conn);
	}
	
	//3. 아이디 찾기
	public void findIdCustomer() {
		Connection conn = Template.getConnection();
		System.out.println("\n---------아이디  찾기---------");
		System.out.print("이름 입력 : ");
		String name = sc.next();
		System.out.print("전화번호 입력 : ");
		String phone = sc.next();
		CustomerInfo customer = dao.findIdCustomer(conn, name, phone);
		if(customer != null) {
			System.out.println("회원님의 아이디는 ["+customer.getcId()+"], 비밀번호는 ["+customer.getcPw()+"] 입니다.");
		} else {
			System.out.println("일치한 회원 정보가 없습니다.");
		}
		Template.close(conn);
	}
	
	//로그인 되었을때의 메뉴
	private void loginMenu(String id) {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("\n-------티몬에 오신 것을 환영합니다~-------");
			System.out.println("1. 물건 목록보기");
			System.out.println("2. 물건 구매");
			System.out.println("3. 상품 수정 및 재고 수정");
			System.out.println("4. 상품 추가");
			System.out.println("5. 장바구니");
			System.out.println("0. 로그아웃");
			System.out.print("선택 > ");
			int sel = sc.nextInt();
			switch(sel) {
			case 1:
				showProductList();
				break;
			case 2:
				buyProduct(id);
				break;
			case 3:
				updateProduct(id);
				break;
			case 4:
				insertProduct(id);
				break;
			case 5:
				storage(id);
				break;
			case 0:
				main();
				break;
			}
		}
	}

	private void insertProduct(String id) {
		// TODO Auto-generated method stub
		if(id.equals("admin")) {
			Connection conn = Template.getConnection();
			int itemp = 0;
			Shop s = new Shop();
			System.out.print("상품명 입력 : ");
			s.setP_name(sc.next());
			System.out.print("상품가격 입력 : ");
			s.setPrice(sc.nextInt());
			System.out.print("상품 갯수 입력 : ");
			s.setP_count(sc.nextInt());
			s.setC_id(id);
			itemp = dao.insertProduct(conn, s);
			if(itemp>0) {
				Template.commit(conn);
			}else {
				Template.rollback(conn);
			}
			Template.close(conn);
			System.out.println(itemp+"개 행 삽입");
		}else {
			System.out.println("관리자만 접근 가능한 메뉴");
		}
	}

	private void storage(String id) {
		// TODO Auto-generated method stub
		Connection conn = Template.getConnection();
		int itemp = 0;
		showStorageList(id);
		System.out.print("구매할 상품의 번호 입력 : (0번시 돌아감)");
		int i = sc.nextInt();
		if(i!=0) {
			//구매진행(장바구니에서 삭제)
			itemp = dao.buyProduct(conn, i, id);
			if(itemp>0) {
				Template.commit(conn);
				System.out.println("구매 성공");
			}else {
				Template.rollback(conn);
				System.out.println("구매 실패");
			}
			Template.close(conn);
			System.out.println(itemp+"개 행 변화");
		}else {
			loginMenu(id);
		}
	}

	private void showStorageList(String id) {
		ArrayList<Storage> arls = new ArrayList<Storage>();
		Connection conn = Template.getConnection();
		arls = dao.showStorageList(conn,arls,id);
		System.out.println("---------- 장바구니 목록 ----------");
		if(!arls.isEmpty()) {
			for(Storage s : arls) {
				System.out.println("장바구니번호\t상품번호");
				System.out.println(s.getS_no()+"\t"+s.getP_no());
			}
		}else {
			System.out.println("장바구니가 비어있습니다.");
		}
		Template.close(conn);
	}

	private void updateProduct(String id) {
		// TODO Auto-generated method stub
		if(id.equals("admin")) {
			Connection conn = Template.getConnection();
			int itemp = 0;
			Shop s = new Shop();
			System.out.print("상품명 입력 : ");
			s.setP_name(sc.next());
			System.out.print("상품가격 입력 : ");
			s.setPrice(sc.nextInt());
			System.out.print("상품 갯수 입력 : ");
			s.setP_count(sc.nextInt());
			s.setC_id(id);
			System.out.print("바꿀 상품의 번호 입력 : ");
			int i = sc.nextInt();
			itemp = dao.updateProduct(conn, s, i);
			if(itemp>0) {
				Template.commit(conn);
			}else {
				Template.rollback(conn);
			}
			Template.close(conn);
			System.out.println(itemp+"개 행 변화");
		}else {
			System.out.println("관리자만 접근 가능한 메뉴");
		}
	}

	private void buyProduct(String id) {
		Connection conn = Template.getConnection();
		int itemp = 0;
		showStorageList(id);
		System.out.print("구매할 상품의 번호 입력 : (0번시 돌아감)");
		int i = sc.nextInt();
		System.out.print("결제여부\n1.장바구니에 저장하기\n 2.바로결제");
		int j = sc.nextInt();
		if(i!=0 && j!=1) {
			//구매진행(장바구니에서 삭제)
			itemp = dao.buyProduct(conn, i, id);
			if(itemp>0) {
				Template.commit(conn);
				System.out.println("구매 완료");
			}else {
				Template.rollback(conn);
				System.out.println("구매 실패");
			}
			Template.close(conn);
			System.out.println(itemp+"개 행 변화");
		}else if(i!=0 && j==1) {
			itemp = dao.addStorage(conn, i, id);
			if(itemp>0) {
				Template.commit(conn);
				System.out.println("장바구니에 저장되었습니다.");
			}else {
				Template.rollback(conn);
				System.out.println("장바구니에 저장실패");
			}
			Template.close(conn);
			System.out.println(itemp+"개 행 변화");
		}else {
			loginMenu(id);
		}
	}

	private void showProductList() {
		// TODO Auto-generated method stub
		ArrayList<Shop> arls = new ArrayList<Shop>();
		Connection conn = Template.getConnection();
		arls = dao.seeProductAll(conn,arls);
		System.out.println("---------- 상품 목록 ----------");
		for(Shop s : arls) {
			System.out.println("상품번호\t상품이름\t상품가격\t상품재고");
			System.out.println(s.getP_no()+"\t"+s.getP_name()+"\t"+s.getPrice()+"\t"+s.getP_count());
		}
		Template.close(conn);
	}
}
