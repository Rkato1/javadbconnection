package member.model.vo;

import java.sql.Date;

public class Member {
	int memberNo;
	String memberId;
	String memberPw;
	String memberName;
	int age;
	char gender;
	String phone;
	Date enroolDate;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(int memberNo, String memberId, String memberPw, String memberName, int age, char gender, String phone,
			Date enroolDate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.enroolDate = enroolDate;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getEnroolDate() {
		return enroolDate;
	}
	public void setEnroolDate(Date enroolDate) {
		this.enroolDate = enroolDate;
	}
	
}
