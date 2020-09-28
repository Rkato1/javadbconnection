package model.vo;

public class Member {
	int m_no;
	String m_id;
	String m_pw;
	String m_name;
	String phone;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(int m_no, String m_id, String m_pw, String m_name, String phone) {
		super();
		this.m_no = m_no;
		this.m_id = m_id;
		this.m_pw = m_pw;
		this.m_name = m_name;
		this.phone = phone;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
